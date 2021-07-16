package com.serhatmerak.spacewars.TEST;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.custom_actors.Box;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.screens.MainMenuScreen;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;
import com.serhatmerak.spacewars.ships.Spaceship;

import java.util.Locale;

import javax.management.DynamicMBean;

/**
 * Created by Serhat Merak on 16.03.2018.
 */

public class TestScreen extends CustomScreen {
    private GameMain game;
    private OrthographicCamera camera;
    private OrthographicCamera box2dCamera;
    private SpriteBatch batch;
    private Texture bg;
    private BitmapFont font;
    private Array<SpaceShipStyle> spaceShipStyles;
    private int styleIndex = 0;

    private Spaceship spaceship;

    private Stage stage;
    private Touchpad touchpad;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private boolean firstClicked;
    private float clickElapsedTime = 0.5f;
    ParticleEffect effect;
    private Vector2 vector2;

    private Skin skin;

    Sprite fire;

    private Hack hack;

    private Sprite base;
    private Animation<TextureRegion> baseAnim;
    private float elapsedTime;

    private List list;
    private ShapeRenderer shapeRenderer;
    private Vector2 m,l;
    private int rotation = 0;
    private Array<Vector2> circles;



    public TestScreen(final GameMain game){
       this.game = game;

       camera = new OrthographicCamera();
       camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);
       box2dCamera = new OrthographicCamera();
       box2dCamera.setToOrtho(false,GameInfo.WIDTH / GameInfo.PPM,GameInfo.HEIGHT / GameInfo.PPM);

       batch = new SpriteBatch();

       bg = Assets.getInstance().assetManager.get(Assets.bg);
       world = new World(new Vector2(0,0),true);
       box2DDebugRenderer = new Box2DDebugRenderer();

       stage = new Stage(new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT),batch);
       spaceship = new Spaceship(world,stage,new Vector2(GameInfo.WIDTH/ 2 ,GameInfo.HEIGHT / 2));
       font = Assets.getInstance().assetManager.get(Assets.fntAerial);
       font.getData().markupEnabled = true;


        skin = new Skin(Gdx.files.internal("skin/uiskin.json"),new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));

       Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
       touchpadStyle.background = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.padbg,Texture.class)));
       touchpadStyle.knob = new SpriteDrawable(new Sprite(new Sprite(Assets.getInstance().assetManager.get(Assets.padknock,Texture.class))));

       touchpad = new Touchpad(10,skin);

       touchpad.setBounds(50,50,300,300);
       stage.addActor(touchpad);

       Gdx.input.setInputProcessor(stage);

       spaceShipStyles = new Array<SpaceShipStyle>();
       spaceShipStyles.addAll(SpaceShipStyle.values());

       Gdx.input.setCatchBackKey(true);

       fire = new Sprite(Assets.getInstance().assetManager.get(Assets.efcEngine,TextureAtlas.class).getTextures().first());
       fire.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       fire.setFlip(false,true);
       vector2 = new Vector2();

       hack = new Hack(game);

        base = new Sprite(Assets.getInstance().assetManager.get(Assets.portal,Texture.class));
        base.setPosition(10,10);
        base.setSize(base.getWidth() * 2 / 3,base.getHeight() * 2 / 3);
        base.setOrigin(base.getWidth() / 2,base.getHeight() / 2);

        baseAnim = new Animation<TextureRegion>(Gdx.graphics.getDeltaTime() * 4,Assets.getInstance().assetManager.get(Assets.portal_animation,TextureAtlas.class).getRegions(),
                Animation.PlayMode.LOOP);


        Gdx.input.setCatchBackKey(true);

        createSekizgen();

    }

    private void createSekizgen() {
        shapeRenderer = new ShapeRenderer();
        m = new Vector2(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2);
        l = new Vector2(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2 + 500);
        circles = new Array<Vector2>();
    }



    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            batch.draw(bg,0,0);
//            spaceship.draw(batch);
////            drawShipInfo();
////            drawFire();
////            drawUser();
////            if(gecti)
////                elapsedTime -= delta;
////            else
//        elapsedTime += delta;
////
////            if(elapsedTime >= 5f)
////                gecti = true;
////            if(elapsedTime  < 0)
////                gecti = false;
//        base.rotate(elapsedTime * 10);
//        base.draw(batch);
//
//        if(baseAnim.isAnimationFinished(elapsedTime - 1)) {
//            elapsedTime = 0;
//        }
//
//
//        if(elapsedTime > 1)
//            batch.draw(baseAnim.getKeyFrame(elapsedTime - 1),base.getX() + base.getWidth() / 2 - baseAnim.getKeyFrame(elapsedTime - 1).getRegionWidth() / 2,
//                    base.getY() + base.getHeight() / 2 - baseAnim.getKeyFrame(elapsedTime - 1).getRegionHeight() / 2);

        batch.end();


        rotate(spaceship);

        stage.act();
        stage.draw();
        world.step(delta,6,2);


        if(isDoubleClick()){
            User.user.shipStyle = SpaceShipStyle.values()[MathUtils.random(0,7)];
        }

        hack.drawAndUpdate(camera);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
        }
        l = rotateAround(new Vector2(m.x,m.y+ 500),m,rotation);
        rotation++;
        if(rotation % 45 == 0 && circles.size < 9){
            circles.add(new Vector2(l.x,l.y));
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(m,l);
        for (Vector2 circle:circles) {
            shapeRenderer.circle(circle.x,circle.y,5);
        }
        shapeRenderer.end();

    }

    private Vector2 rotateAround(Vector2 vector,Vector2 origin,float rotation){
        return vector.sub(origin).rotate(rotation).add(origin);
    }

    private void drawFire() {
        fire.setPosition(spaceship.getX()+ spaceship.getWidth() / 2  - fire.getWidth() / 2,spaceship.getY() - fire.getHeight());
        Vector2 vector2 = rotateAround(
                new Vector2(fire.getX() + fire.getWidth() / 2,fire.getY() + fire.getHeight() / 2),
                new Vector2(spaceship.getX() + spaceship.getWidth() / 2,spaceship.getY() + spaceship.getHeight() / 2)
                ,spaceship.getRotation());
        fire.setOriginBasedPosition(vector2.x,vector2.y);
        fire.setRotation(spaceship.getRotation());

        fire.draw(batch);


    }


    private void drawUser() {
        for(int i = 0; i < User.user.gems.size; i++){
            if(User.user.gems.get(i) != null)
                batch.draw(User.user.gems.get(i).img,1750,150 + i * 180);
        }

        for(int i = 0; i < User.user.weapons.size; i++){
            if(User.user.weapons.get(i) != null)
                batch.draw(User.user.weapons.get(i).img,1500,200 + i * 220);
        }
    }

    private void drawGems() {
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem1,Texture.class),1750,900);
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem2,Texture.class),1750,750);
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem3,Texture.class),1750,600);
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem4,Texture.class),1750,450);
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem5,Texture.class),1750,300);
        batch.draw(Assets.getInstance().assetManager.get(Assets.green_gem6,Texture.class),1750,150);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem1,Texture.class),1600,900);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem2,Texture.class),1600,750);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem3,Texture.class),1600,600);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem4,Texture.class),1600,450);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem5,Texture.class),1600,300);
        batch.draw(Assets.getInstance().assetManager.get(Assets.yellow_gem6,Texture.class),1600,150);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem1,Texture.class),1450,900);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem2,Texture.class),1450,750);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem3,Texture.class),1450,600);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem4,Texture.class),1450,450);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem5,Texture.class),1450,300);
        batch.draw(Assets.getInstance().assetManager.get(Assets.blue_gem6,Texture.class),1450,150);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem1,Texture.class),1300,900);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem2,Texture.class),1300,750);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem3,Texture.class),1300,600);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem4,Texture.class),1300,450);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem5,Texture.class),1300,300);
        batch.draw(Assets.getInstance().assetManager.get(Assets.red_gem6,Texture.class),1300,150);
    }

    private void drawShipInfo() {

        font.draw(batch,"Speed = [BLUE]"+ spaceship.speed,50,900);
        font.draw(batch,"Health = [GREEN]"+ spaceship.health,50,820);
        font.draw(batch,"Damage = [RED]"+ spaceship.damage,50,740);
        font.draw(batch,"Attack Speed = [YELLOW]"+ spaceship.attackSpeed,50,660);
    }

    private void rotate(Spaceship spaceship) {

            if(!(touchpad.getKnobX() == 150) && !(touchpad.getKnobY() == 150))
                spaceship.setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(
                        150 - touchpad.getKnobX(), -1 * (150 - touchpad.getKnobY())));

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

    private Vector3 getCursorPos(){
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(touchPos);
    }

}
