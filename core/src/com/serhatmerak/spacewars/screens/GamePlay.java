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
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.Effects;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.TEST.Hack;
import com.serhatmerak.spacewars.helpers.MissionController;
import com.serhatmerak.spacewars.huds.GamePlayHuds;
import com.serhatmerak.spacewars.gameplay_objects.MiniMap;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.Spaceship;
import com.serhatmerak.spacewars.stations.Station;


/**
 * Created by Serhat Merak on 16.03.2018.
 */

public class GamePlay extends CustomScreen implements ContactListener{

    private GameMain game;
    private Map map;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera inputCamera;
    private OrthographicCamera box2dCamera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Sprite pix;
    private Spaceship spaceship;
    private boolean firstClicked;
    private float clickElapsedTime = 0.5f;
    public GamePlayHuds huds;
    private MiniMap miniMap;
    private Effects effects;
    private Array<LaserController.Laser> lasers;
    private MissionController missionController;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);



    //TODO:remove
    private Hack hack;


    public GamePlay(GameMain game, Map map){
        this.game = game;
        this.map = map;
        for(Station station:map.getStations())
            station.gamePlay = this;

        world = map.world;
        world.setContactListener(this);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);

        inputCamera = new OrthographicCamera();
        inputCamera.setToOrtho(false,GameInfo.WIDTH,GameInfo.HEIGHT);

        viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);
        viewport.setScreenBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);

        AssetManager assetManager = Assets.getInstance().assetManager;
        pix = new Sprite(assetManager.get(Assets.pix,Texture.class));
        pix.setBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);
        pix.setColor(Color.valueOf("02050c"));


        debugRenderer = new Box2DDebugRenderer();
        box2dCamera = new OrthographicCamera(GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);

        huds = new GamePlayHuds(batch);

        spaceship = new Spaceship(world,huds.stage,map.shipPosition);

        spaceship.attackPad = huds.attackpad;

        miniMap = new MiniMap(spaceship,map);

        effects = new Effects();
        lasers = new Array<LaserController.Laser>();
        spaceship.laserController.activeLasers = lasers;
        for (EnemyShip enemyShip:map.enemyController.enemyShips)
            enemyShip.laserController.activeLasers = lasers;
        spaceship.pet.laserController.activeLasers = lasers;

        hack = new Hack(game);


        missionController = new MissionController();
        missionController.setMap(map.mapName);
        missionController.setMission(User.user.mission);
        huds.missionController = missionController;

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(huds.stage);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMultiplexer);

        stageListeners();

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        updateWorld();
        drawWorld();


    }


    private void updateWorld() {

        spaceship.move(huds.touchpad.getKnobPercentX(), huds.touchpad.getKnobPercentY());
        spaceship.atack();

        spaceship.safeZone = false;
        for (Station station:map.stations){
            if(station.position.dst(new Vector2(spaceship.getX(),spaceship.getY())) <= 750)
                spaceship.safeZone = true;
        }


        map.updateEnemies();

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

        if(huds.portalBtnEnabled)
            huds.setPortalBtnEnabled(false);

        for(Station station : map.stations) {
            station.update();
            if(station.isNear(new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                    spaceship.getY() + spaceship.getHeight() / 2))){
                if (!huds.portalBtnEnabled)
                    huds.setPortalBtnEnabled(true);
            }
        }

        missionController.update();
        if(User.user.mission != null) {
            huds.currentMissionPanel.setAmountLabelTexts();
        } else {
            if(huds.currentMissionPanel != null)
                huds.currentMissionPanel = null;
        }


        camera.update();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
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

        for (Crystal crystal:map.crystals){
            if(crystal.body.getUserData() != null && crystal.body.getUserData().equals("REMOVE")) {
                missionController.beatenCrystal();
                world.destroyBody(crystal.body);
                map.crystals.removeValue(crystal,true);


            }
        }

        for (EnemyShip enemyShip : map.enemyController.enemyShips){
            if(enemyShip.died){
                missionController.beatenEnemy(enemyShip.shipStyle);
                enemyShip.remove();
                map.enemyController.enemyShips.removeValue(enemyShip,false);
            }
        }
    }

    private void drawWorld() {

        batch.begin();
        {
            batch.setProjectionMatrix(inputCamera.combined);
            map.pix.draw(batch);
            batch.setProjectionMatrix(camera.combined);
            drawBackground();
            for(Station station:map.stations)
                station.draw(batch);
            drawEnemies();
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

        hack.drawAndUpdate(inputCamera);

//        debugRenderer.render(world,box2dCamera.combined);
    }

    private void drawBackground() {
        map.pix.draw(batch);

        for(int i = 0; i< (map.bgSize / map.star.getWidth()) + 1;i++){
            for(int j = 0; j < (map.bgSize / map.star.getHeight() ) + 1;j++) {
                if(checkInScreen(new Vector2((map.star.getWidth() * i) + map.bgX,(map.star.getHeight() * j + map.bgY)),
                        new Vector2(map.star.getWidth(),map.star.getHeight())))
                    batch.draw(map.star,(map.star.getWidth() * i) + map.bgX,(map.star.getHeight() * j + map.bgY));
            }
        }

        for (Map.Layers layer:map.layers){
            if(checkInScreen(new Vector2(layer.pos.x + map.bgX,layer.pos.y + map.bgY),new Vector2(layer.texture.getWidth(),layer.texture.getHeight())))
                batch.draw(layer.texture,layer.pos.x + map.bgX,layer.pos.y + map.bgY);
        }

        for(Map.SoloStar soloStar:map.stars){
            if(checkInScreen(new Vector2(map.bgX * soloStar.speed + soloStar.pos.x,map.bgY * soloStar.speed + soloStar.pos.y),
                    new Vector2(10,10))) {
                batch.draw(map.soloStar, map.bgX * soloStar.speed + soloStar.pos.x, map.bgY * soloStar.speed + soloStar.pos.y);
            }
        }



    }

    private void drawEnemies() {
        for(Crystal crystal:map.crystals) {
            if(checkInScreen(new Vector2(crystal.getX(),crystal.getY()),new Vector2(crystal.getWidth(),crystal.getHeight())))
                crystal.draw(batch);
        }

        for(EnemyShip enemyShip:map.enemyController.enemyShips){
            if(checkInScreen(new Vector2(enemyShip.getX(),enemyShip.getY()),new Vector2(enemyShip.getWidth(),enemyShip.getHeight())))
                enemyShip.draw(batch);
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

    private void stageListeners() {


        huds.portal_btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                for(Station station : map.getStations()) {
                    station.update();
                    if(station.isNear(new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                            spaceship.getY() + spaceship.getHeight() / 2))){
                        station.setActive(true);
                    }
                }
            }
        });

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

        if(isConnact("SENSOR","SPACESHIP",objectsA[1],objectsB[1])){
            if(!spaceship.safeZone) {
                EnemyShip enemyShip;
                if (objectsA[1].equals("SPACESHIP")) {
                    enemyShip = (EnemyShip) objectsB[0];
                } else {
                    enemyShip = (EnemyShip) objectsA[0];
                }

                enemyShip.spaceship = spaceship;
                enemyShip.state = EnemyShip.State.FOLLOWING;
            }
        }

        if(isConnact("SPACESHIP_SENSOR","ENEMY",objectsA[1],objectsB[1])){
            if(objectsA[1].equals("ENEMY")){
                spaceship.sensoredBodies.add(((EnemyShip) objectsA[0]).body);
            }else {
                spaceship.sensoredBodies.add(((EnemyShip) objectsB[0]).body);
            }
        }

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

            if(attackedShip == spaceship || attackedShip == spaceship.pet) {
                float stolenLife, extraDamage, decreaseAttackSpeed, moreCoin;

                extraDamage = (laser.damage * laser.moreDamage) / 100;
                decreaseAttackSpeed = laser.decreaseAttackSpeed;
                moreCoin = laser.moreCoin;

                if(damagedObjec[1].equals("ENEMY")){

                    stolenLife = (laser.damage * laser.lifeSteal) / 100;
                    attackedShip.currentHelath += stolenLife;
                    if(attackedShip.currentHelath > attackedShip.health)
                        attackedShip.currentHelath = attackedShip.health;
                    spaceship.stolenLife(stolenLife);

                    EnemyShip enemyShip = (EnemyShip) damagedObjec[0];
                    enemyShip.decreaseAttackSpeed((int) decreaseAttackSpeed);
                    if(enemyShip.getDamage(laser.damage + extraDamage)){
                        effects.addBigExplosionEffect(new Vector2(enemyShip.getX() + enemyShip.getWidth() / 2,
                                enemyShip.getY() + enemyShip.getHeight() / 2));
                        collectBag(enemyShip,moreCoin);
                    }

                    if(enemyShip.state != EnemyShip.State.ATTACKING && enemyShip.state != EnemyShip.State.FOLLOWING){
                        enemyShip.spaceship = spaceship;
                        enemyShip.state = EnemyShip.State.FOLLOWING;
                    }

                    effects.addExplosionEffect(new Vector2(contact.getWorldManifold().getPoints()[0].x * GameInfo.PPM,
                            contact.getWorldManifold().getPoints()[0].y * GameInfo.PPM));
                    laser.body.setUserData("REMOVE");

                    spaceship.pet.focusedToEnemy = true;
                    spaceship.pet.focusedEnemy = enemyShip;

                }else if(damagedObjec[1].equals("CRYSTAL")){

                    stolenLife = (laser.damage * laser.lifeSteal) / 100;
                    attackedShip.currentHelath += stolenLife;
                    if(attackedShip.currentHelath > attackedShip.health)
                        attackedShip.currentHelath = attackedShip.health;
                    spaceship.stolenLife(stolenLife);

                    Crystal crystal = (Crystal) damagedObjec[0];
                    if(crystal.getDamage(extraDamage + laser.damage)){
                        effects.addBigExplosionEffect(new Vector2(crystal.getX() + crystal.getWidth() / 2,
                                crystal.getY() + crystal.getHeight() / 2));
                        collectBag(crystal,moreCoin);
                        crystal.body.setUserData("REMOVE");
                    }

                    effects.addExplosionEffect(new Vector2(contact.getWorldManifold().getPoints()[0].x * GameInfo.PPM,
                            contact.getWorldManifold().getPoints()[0].y * GameInfo.PPM));
                    laser.body.setUserData("REMOVE");
                }

                spaceship.pet.durationOfEscape = 0;

            }else {
                if(damagedObjec[1].equals("SPACESHIP")) {

                    if(!spaceship.pet.focusedToEnemy){
                        spaceship.pet.focusedToEnemy = true;
                        spaceship.pet.focusedEnemy = (EnemyShip) attackedShip;
                    }

                    spaceship.pet.durationOfEscape = 0;


                    effects.addExplosionEffect(new Vector2(contact.getWorldManifold().getPoints()[0].x * GameInfo.PPM,
                            contact.getWorldManifold().getPoints()[0].y * GameInfo.PPM));
                    laser.body.setUserData("REMOVE");

                    if (spaceship.getDamage(laser.damage)) {
                        effects.addBigExplosionEffect(new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                                spaceship.getY() + spaceship.getHeight() / 2));
                        //TODO: GAME OVER
                        game.setScreen(new MainMenuScreen(game));
                    }

                }
            }

        }



    }

    private void collectBag(Ship damagedShip,float moreCoin) {
        EnemyShip enemyShip  = (EnemyShip) damagedShip;
        String alert = "";



        for (GameObjectCollector gameObjectCollector:enemyShip.gameObjectBag.gameObjectCollectors){
            if(gameObjectCollector.state == GameObjectCollector.State.COIN) {
                int shipCoin = Integer.valueOf(gameObjectCollector.object.toString());
                int coin = (int) (shipCoin + ((moreCoin * shipCoin / 100)));
                alert += "[YELLOW] " + coin + " " + texts.get("coin") + "[]\n";

                User.user.coin += coin;
            }
            else if(gameObjectCollector.state == GameObjectCollector.State.WEAPON){
                Weapon.WeaponStyle weapon = (Weapon.WeaponStyle) gameObjectCollector.object;
                alert += weapon.color + " " + weapon.name + "[]\n ";

                User.user.weaponBag.add(new Weapon(weapon));
                GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
            }else if(gameObjectCollector.state == GameObjectCollector.State.GEM){
                Gem.GemStyle gem = (Gem.GemStyle) gameObjectCollector.object;
                alert += gem.color + " " + gem.name + "[]\n ";

                User.user.gemBag.add(new Gem(gem));
                GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));
            }

            GameManager.gameManager.saveCoin();
            huds.missionController.collected(gameObjectCollector,false,enemyShip.shipStyle);

        }


        huds.showToast(alert);
    }
    private void collectBag(Crystal crystal,float moreCoin) {
        String alert = "";

        for (GameObjectCollector gameObjectCollector:crystal.gameObjectCollectors){
            if(gameObjectCollector.state == GameObjectCollector.State.COIN) {
                int shipCoin = Integer.valueOf(gameObjectCollector.object.toString());
                int coin = (int) (shipCoin + ((moreCoin * shipCoin / 100)));
                alert += "[YELLOW] " + coin + " " + texts.get("coin") + "[]\n";

                User.user.coin += coin;
            }
            else if(gameObjectCollector.state == GameObjectCollector.State.WEAPON){
                Weapon.WeaponStyle weapon = (Weapon.WeaponStyle) gameObjectCollector.object;
                alert += weapon.color + " " + weapon.name + "[]\n ";

                User.user.weaponBag.add(new Weapon(weapon));
                GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
            }else if(gameObjectCollector.state == GameObjectCollector.State.GEM){
                Gem.GemStyle gem = (Gem.GemStyle) gameObjectCollector.object;
                alert += gem.color + " " + gem.name + "[]\n ";

                User.user.gemBag.add(new Gem(gem));
                GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));

            }

            GameManager.gameManager.saveCoin();
            huds.missionController.collected(gameObjectCollector,true,null);

        }


        huds.showToast(alert);
    }

    @Override
    public void endContact(Contact contact) {
        contact.setEnabled(false);
        Object[] objectsA = (Object[]) contact.getFixtureA().getUserData();
        Object[] objectsB = (Object[]) contact.getFixtureB().getUserData();

        if(isConnact("SENSOR","SPACESHIP",objectsA[1],objectsB[1])){
            EnemyShip enemyShip;
            if(objectsA[1].equals("SPACESHIP")){
                enemyShip = (EnemyShip) objectsB[0];
            }else {
                enemyShip = (EnemyShip) objectsA[0];
            }

            enemyShip.state = EnemyShip.State.WAITING;

        }

        if(isConnact("SPACESHIP_SENSOR","ENEMY",objectsA[1],objectsB[1])){
            if(objectsA[1].equals("ENEMY")){
                spaceship.sensoredBodies.removeValue(((EnemyShip) objectsA[0]).body,false);
            }else {
                spaceship.sensoredBodies.removeValue(((EnemyShip) objectsB[0]).body,false);
            }
        }
    }


    private boolean isDoubleClick(){
        if(!firstClicked && Gdx.input.justTouched()){
            firstClicked = true;
            return false;
        }

        if(firstClicked){
            if(Gdx.input.justTouched())
                return true;
            else
                clickElapsedTime -= Gdx.graphics.getDeltaTime();
        }

        if(clickElapsedTime < 0){
            firstClicked = false;
            clickElapsedTime = 0.5f;
        }

        return false;
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