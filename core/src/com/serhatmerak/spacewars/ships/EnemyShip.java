package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.serhatmerak.spacewars.game_objects.GameObjectBag;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.gameplay_objects.HealthBar;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;


/**
 * Created by Serhat Merak on 17.03.2018.
 */

public class EnemyShip extends Ship {
    private float speedX,speedY;
    private float waitingTime = 0;

    private Vector2 nextPos;

    public Spaceship spaceship;

    public enum State{
        MOVING,
        WAITING,
        ATTACKING,
        FOLLOWING
    }

    private HealthBar healthBar;
    private boolean decreasedAttackSpeed = false;
    private float decreasedAttackSpeedElapsedTime = 0;

    public LaserController laserController;
    private final float range = 750;
    public State state = State.WAITING;
    private Label nameLabel;
    private BitmapFont font;
    private Vector2 labelSize;

    public GameObjectBag gameObjectBag;

    public EnemyShip(SpaceShipStyle shipStyle, World world, Vector2 pos){
        super(world,shipStyle);
        getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        setPosition(pos.x,pos.y);
        createBody();
        nextPos = new Vector2();
        setNextPosition();

        laserController = new com.serhatmerak.spacewars.gameplay_objects.LaserController(world,this);
        laserController.attackSpeed = attackSpeed;
        laserController.damage = damage;
        laserController.fromEnemy = true;

        healthBar = new HealthBar(health);
        font = Assets.getInstance().assetManager.get(Assets.fntAerial);
        createNameLabel();

        createGameObjectBag(shipStyle);

    }

    private void createNameLabel() {
        labelSize = new Vector2();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font,shipStyle.name());
        labelSize.x = layout.width;
        labelSize.y = layout.height;
    }

    private void createGameObjectBag(SpaceShipStyle shipStyle) {
        if(shipStyle.gameObjectCollectors != null && shipStyle.gameObjectCollectors.length > 0){
            gameObjectBag = new GameObjectBag(shipStyle.gameObjectCollectors);
        }else
            gameObjectBag = new GameObjectBag(new GameObjectCollector[]{});

    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = getX()  / GameInfo.PPM;
        bodyDef.position.y = getY() / GameInfo.PPM;

        body = world.createBody(bodyDef);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 3) / GameInfo.PPM,(getHeight() / 3) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Object[]{this,"ENEMY"});

        //sensor
        CircleShape sensorShape = new CircleShape();
        sensorShape.setRadius(Math.max(getWidth(),getHeight()) * 7 / GameInfo.PPM);
        FixtureDef sensorDef = new FixtureDef();

        sensorDef.shape = sensorShape;
        Fixture sensorFixture = body.createFixture(sensorDef);
        sensorFixture.setUserData(new Object[]{this,"SENSOR"});
        sensorFixture.setSensor(true);


    }

    public void move(){


        if(state == State.WAITING){
            waitingTime += Gdx.graphics.getDeltaTime();

            if(waitingTime >= 3f) {
                setNextPosition();
                state = State.MOVING;
                waitingTime = 0;
            }

        }else if(state == State.MOVING){

            body.setLinearVelocity(speed * speedX,speed * speedY);
            if(Math.abs(getX() - nextPos.x) < speed * 2 || Math.abs(getY() - nextPos.y) < speed * 2) {
                state = State.WAITING;
                body.setLinearVelocity(0,0);
            }
        }else if(state == State.FOLLOWING){
            followToSpaceShip();
        }else if(state == State.ATTACKING){
            attackToSpaceShip();
        }

        if(state != State.ATTACKING){
            laserController.update(new Vector2(0,0));
        }

        setPosition((body.getPosition().x * GameInfo.PPM) - getWidth() / 2,
                (body.getPosition().y * GameInfo.PPM) - getHeight() / 2);
        healthBar.update(currentHelath,new Vector2(getX() + getWidth() / 2 - healthBar.size.x / 2,
                getY() + Math.max(getWidth(),getHeight()) + 20));

        if(decreasedAttackSpeed){
            decreasedAttackSpeedElapsedTime += Gdx.graphics.getDeltaTime();
            if (decreasedAttackSpeedElapsedTime > 0.5f) {
                decreasedAttackSpeed = false;
                decreasedAttackSpeedElapsedTime = 0;
                laserController.attackSpeed = attackSpeed;
            }
        }

    }
    private void attackToSpaceShip() {
        //TODO: Attack
        laserController.update(new Vector2((spaceship.getX() + spaceship.getWidth() / 2) - (getX() + getWidth() / 2),
                (spaceship.getY() + spaceship.getHeight() / 2) - (getY() + getHeight() / 2) ));


        Vector2 coor = new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                spaceship.getY() + spaceship.getHeight() / 2);
        setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * (coor.x - (getX() + getWidth() / 2)),
                coor.y - (getY() + getHeight() / 2)));
        body.setTransform(body.getPosition(),getRotation() * MathUtils.degreesToRadians);


        if(new Vector2(spaceship.getX(),spaceship.getY()).dst(new Vector2(getX(),getY())) > range)
            state = State.FOLLOWING;
    }
    private void followToSpaceShip() {
        Vector2 coor = new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                spaceship.getY() + spaceship.getHeight() / 2);

        float gapX = Math.abs(coor.x - (getX() + getWidth() / 2));
        float gapY = Math.abs(coor.y - (getY() + getHeight() / 2));


        setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * (coor.x - (getX() + getWidth() / 2)),
                coor.y - (getY() + getHeight() / 2)));
        body.setTransform(body.getPosition(), getRotation() * MathUtils.degreesToRadians);


        if (gapX > gapY) {
            speedY = gapY / gapX;
            speedX = 1f;
        } else {
            speedY = 1f;
            speedX = gapX / gapY;
        }

        if (coor.x < getX() + getWidth() / 2) {
            if (speedX > 0)
                speedX *= -1;
        } else {
            if (speedX < 0)
                speedX *= 1;
        }
        if (coor.y < getY() + getHeight() / 2) {
            if (speedY > 0)
                speedY *= -1;
        } else {
            if (speedY < 0)
                speedY *= -1;
        }


        if(coor.dst(new Vector2(getX(),getY())) < 550){
            state = State.ATTACKING;
            speedY = 0;
            speedX = 0;

        }
        body.setLinearVelocity(speed * speedX, speed * speedY);

    }

    private void setNextPosition() {
        nextPos.set(MathUtils.random(0,25000),MathUtils.random(0,25000));
        checkGap();





        float gapX = Math.abs(nextPos.x - (getX() + getWidth() / 2));
        float gapY = Math.abs(nextPos.y - (getY() + getHeight() / 2));

        setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * (nextPos.x - (getX() + getWidth() / 2)),
                nextPos.y - (getY() + getHeight() / 2)));
        body.setTransform(body.getPosition(),getRotation() * MathUtils.degreesToRadians);

        if(gapX > gapY){
            speedY = gapY / gapX;
            speedX = 1f;
        }else {
            speedY = 1f;
            speedX = gapX / gapY;
        }


        if(nextPos.x < getX() + getWidth() / 2) {
            if (speedX > 0)
                speedX *= -1;
        }else {
            if(speedX < 0)
                speedX *=1;
        }
        if(nextPos.y < getY() + getHeight() / 2){
            if (speedY > 0)
                speedY *= -1;
        }else {
            if (speedY < 0)
                speedY *= -1;
        }

    }

    public void checkGap(){
        if(Math.abs(nextPos.x - getX()) < 500){
            if(nextPos.x - getX() > 0)
                nextPos.x += 500;
            else
                nextPos.x -= 500;
        }

        if(Math.abs(nextPos.y - getY()) < 500){
            if(nextPos.y - getY() > 0)
                nextPos.y += 500;
            else
                nextPos.y -= 500;
        }
    }

    @Override
    public void decreaseAttackSpeed(int ratio) {
        laserController.attackSpeed += (laserController.attackSpeed * ratio) / 100;
        decreasedAttackSpeed = true;

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        healthBar.draw(batch);
        font.draw(batch,"[RED]" + shipStyle.name()+"[]",getX() + getWidth() / 2 - labelSize.x / 2,getY() - 5 - labelSize.y);

    }

}
