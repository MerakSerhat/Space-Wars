package com.serhatmerak.spacewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.gameplay_objects.MiniMap;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.Effects;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.helpers.MissionController;
import com.serhatmerak.spacewars.huds.GamePlayHuds;
import com.serhatmerak.spacewars.huds.PvPWarScreenHuds;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.maps.PvPMap;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.PvPShip;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.Spaceship;
import com.serhatmerak.spacewars.stations.Station;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Serhat Merak on 31.07.2018.
 */

public class PvPWarScreen extends CustomScreen implements ContactListener {
    private GameMain game;
    private PvPWarScreenHuds huds;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera inputCamera;
    private OrthographicCamera box2dCamera;
    private Viewport viewport;
    private World world;
    private PvPMap map;
    private Box2DDebugRenderer debugRenderer;
    private Sprite pix;
    private Spaceship spaceship;
    private MiniMap miniMap;
    private Effects effects;
    private Array<LaserController.Laser> lasers;

    private PvPShip pvPShip;
    private Socket socket;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private Vector3 pvpShipPos;
    private boolean attack = false;
    private Vector2 attackCoors;


    public PvPWarScreen(GameMain game,boolean builder) {
        this.game = game;
        map = new PvPMap(game);
        pvpShipPos = new Vector3();
        attackCoors = new Vector2();
        try {
            socket = IO.socket("http://localhost:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        world = map.world;
        world.setContactListener(this);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
        huds = new PvPWarScreenHuds(batch);
        inputCamera = new OrthographicCamera();
        inputCamera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, camera);
        viewport.setScreenBounds(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);

        AssetManager assetManager = Assets.getInstance().assetManager;
        pix = new Sprite(assetManager.get(Assets.pix, Texture.class));
        pix.setBounds(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        pix.setColor(Color.valueOf("02050c"));


        debugRenderer = new Box2DDebugRenderer();
        box2dCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);


        if (builder) {
            spaceship = new Spaceship(world, huds.stage, new Vector2(0, 0));
            pvPShip = new PvPShip(world,new Vector2(4000,4000),Assets.ship5);
        } else {
            spaceship = new Spaceship(world, huds.stage, new Vector2(4000, 4000));
            pvPShip = new PvPShip(world,new Vector2(0,0),Assets.ship5);
        }
        spaceship.laserController.socket = socket;
        spaceship.pet = null;

        pvPShip.attackSpeed = User.user.getAttackSpeed();
        pvPShip.damage = User.user.getDamage();
        pvPShip.init();

        spaceship.attackPad = huds.attackpad;

        miniMap = new MiniMap(spaceship,map);

        effects = new Effects();
        lasers = new Array<LaserController.Laser>();
        spaceship.laserController.activeLasers = lasers;
        pvPShip.laserController.activeLasers = lasers;

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(huds.stage);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMultiplexer);

        socket.on("playerAttacked", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                try {
                    float x = (float) jsonObject.getDouble("x");
                    float y = (float) jsonObject.getDouble("y");
                    attack = true;
                    attackCoors.set(x,y);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        System.out.println(Gdx.graphics.getFramesPerSecond());
        updateWorld();
        drawWorld();
        updateSocket();

    }


    private void updateWorld() {

        spaceship.move(huds.touchpad.getKnobPercentX(), huds.touchpad.getKnobPercentY());
        spaceship.atack();
        pvPShip.setBodyPos(pvpShipPos.x,pvpShipPos.y,pvpShipPos.z);
        if(attack){
            pvPShip.laserController.Attack(attackCoors);
        }
        attack = false;

        camera.position.set(spaceship.getX() + spaceship.getWidth() / 2
                ,spaceship.getY() + spaceship.getHeight() / 2,0);
        //border check
        if(miniMap.mapBorderX  < 0 || miniMap.mapBorderY < 0 ||
                miniMap.mapBorderX > miniMap.minimapSize + 10 || miniMap.mapBorderY > miniMap.minimapSize + 10 ) {
            if(huds.redAlert.getActions().size == 0) {
                huds.showRedAlert();
            }
        }
        else {
            if(huds.redAlert.getActions().size == 1)
                huds.removeRedAlert();
        }

        box2dCamera.position.set(camera.position.x / GameInfo.PPM,camera.position.y / GameInfo.PPM,0);
        box2dCamera.update();


        //Arkaplan hareketi
        map.bgX = (spaceship.getX() - (GameInfo.WIDTH / 2 - spaceship.getWidth() / 2)) / 1.2f;
        map.bgY = (spaceship.getY() - (GameInfo.HEIGHT / 2 - spaceship.getHeight() / 2)) / 1.2f;

        for (LaserController.Laser laser:lasers){
            laser.update();
        }
        removeBodies();

        if(huds.border_warning){
            spaceship.currentHelath -= spaceship.health * Gdx.graphics.getDeltaTime() / 10;
            if(spaceship.currentHelath <= 0)
                effects.addBigExplosionEffect(new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                        spaceship.getY() + spaceship.getHeight() / 2));


        }

        camera.update();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    private void updateSocket() {

        float x = spaceship.body.getPosition().x;
        float y = spaceship.body.getPosition().y;
        float a = spaceship.getRotation();
        float c = spaceship.currentHelath;
//        JSONObject data = new JSONObject();
//        try {
//            data.put("x",x);
//            data.put("y",y);
//            data.put("a",a);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        socket.emit("setPlayerPos",x,y,a,c);
        socket.on("setPlayerPos", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        float x = (float) jsonObject.getDouble("x");
                        float y = (float) jsonObject.getDouble("y");
                        float a = (float) jsonObject.getDouble("a");
                        float c = (float) jsonObject.getDouble("c");

                    pvpShipPos.set(x,y,a);
                    pvPShip.currentHelath = c;
                    miniMap.otherPlayer = pvpShipPos;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void removeBodies() {

        for (LaserController.Laser laser:lasers){
            if(laser.isMaxDistance()){
                laser.body.setUserData("REMOVE");
            }
            if(laser.body.getUserData() != null && laser.body.getUserData().equals("REMOVE")) {
                world.destroyBody(laser.body);
                lasers.removeValue(laser, false);
            }
        }
    }

    private void drawWorld() {

        batch.begin();
        {
            batch.setProjectionMatrix(inputCamera.combined);
            pix.draw(batch);
            batch.setProjectionMatrix(camera.combined);
            drawBackground();
            pvPShip.draw(batch);
            spaceship.draw(batch);
            drawEngineEffect();
            effects.drawExplosionEffects(batch);
            effects.drawBigExplosionEffects(batch);
            drawLasers();
            batch.setProjectionMatrix(inputCamera.combined);
            miniMap.draw(batch);


        }

        batch.end();

        huds.stage.act();
        huds.stage.draw();
        batch.setColor(Color.WHITE);
//        debugRenderer.render(world,box2dCamera.combined);
    }

    private void drawBackground() {
        map.pix.draw(batch);

        for (int i = 0; i < (map.bgSize / map.star.getWidth()) + 1; i++) {
            for (int j = 0; j < (map.bgSize / map.star.getHeight()) + 1; j++) {
                if (checkInScreen(new Vector2((map.star.getWidth() * i) + map.bgX, (map.star.getHeight() * j + map.bgY)),
                        new Vector2(map.star.getWidth(), map.star.getHeight())))
                    batch.draw(map.star, (map.star.getWidth() * i) + map.bgX, (map.star.getHeight() * j + map.bgY));
            }
        }

        for (Map.Layers layer : map.layers) {
            if (checkInScreen(new Vector2(layer.pos.x + map.bgX, layer.pos.y + map.bgY), new Vector2(layer.texture.getWidth(), layer.texture.getHeight())))
                batch.draw(layer.texture, layer.pos.x + map.bgX, layer.pos.y + map.bgY);
        }
    }

    private boolean checkInScreen(Vector2 pos,Vector2 size) {
        if (camera.position.x - GameInfo.WIDTH / 2 <= pos.x + size.x &&
                camera.position.x + GameInfo.WIDTH / 2 >= pos.x &&
                camera.position.y - GameInfo.HEIGHT / 2 <= pos.y + size.y &&
                camera.position.y + GameInfo.HEIGHT / 2 >= pos.y) {

            return true;

        }

        return false;
    }

    private void drawLasers() {
        for(LaserController.Laser laser:lasers)
            laser.draw(batch);
    }




    //////////////////////////////////////////////////////////////


    private void drawEngineEffect() {
        if(spaceship.body.getLinearVelocity().x == 0 && spaceship.body.getLinearVelocity().y == 0){
            effects.engineEfcElapsedTime = 0;
        }else {
            effects.drawEngineEffect(batch, new Vector2(spaceship.getX() + spaceship.getWidth() / 2f, spaceship.getY()),
                    new Vector2(spaceship.getX() + spaceship.getWidth() / 2, spaceship.getY() + spaceship.getHeight() / 2),
                    spaceship.getRotation());
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }


    @Override
    public void beginContact(Contact contact) {

        Object[] objectsA = (Object[]) contact.getFixtureA().getUserData();
        Object[] objectsB = (Object[]) contact.getFixtureB().getUserData();


        if(objectsA[1].equals("LASER") || objectsB[1].equals("LASER")){
            LaserController.Laser laser;
            Object[] damagedObjec;
            Ship attackedShip;


            if(objectsA[1].equals("LASER")){
                laser = (LaserController.Laser) objectsA[0];
                damagedObjec = objectsB;
            }else {
                laser = (LaserController.Laser) objectsB[0];
                damagedObjec = objectsA;
            }

            attackedShip = laser.ship;

            System.out.println("contact");
            if(attackedShip == spaceship) {
                System.out.println("spaceship");
                float stolenLife;

                if(damagedObjec[1].equals("PvPShip")){

                    stolenLife = (laser.damage * laser.lifeSteal) / 100;
                    attackedShip.currentHelath += stolenLife;
                    if(attackedShip.currentHelath > attackedShip.health)
                        attackedShip.currentHelath = attackedShip.health;
                    spaceship.stolenLife(stolenLife);

                    effects.addExplosionEffect(new Vector2(contact.getWorldManifold().getPoints()[0].x * GameInfo.PPM,
                            contact.getWorldManifold().getPoints()[0].y * GameInfo.PPM));
                    laser.body.setUserData("REMOVE");


                }
            }else {
                System.out.println("değişik");
                if(damagedObjec[1].equals("SPACESHIP")) {
                    System.out.println("PvpShip");
                    effects.addExplosionEffect(new Vector2(contact.getWorldManifold().getPoints()[0].x * GameInfo.PPM,
                            contact.getWorldManifold().getPoints()[0].y * GameInfo.PPM));
                    laser.body.setUserData("REMOVE");
                    spaceship.decreaseAttackSpeed(laser.decreaseAttackSpeed);

                    float moreDamage = (laser.damage * laser.moreDamage) / 100;
                    if (spaceship.getDamage(laser.damage + moreDamage)) {
                        effects.addBigExplosionEffect(new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                                spaceship.getY() + spaceship.getHeight() / 2));
                        //TODO: GAME OVER
                        game.setScreen(new MainMenuScreen(game));
                    }

                }
            }

        }



    }
    @Override
    public void endContact(Contact contact) {
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean isConnact(String id1, String id2, Object a,Object b){
        return  ((a.equals(id1) && b.equals(id2)) ||
                (a.equals(id2) && b.equals(id1)) );
    }
}
