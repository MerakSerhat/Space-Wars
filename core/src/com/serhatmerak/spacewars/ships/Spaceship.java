package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.gameplay_objects.HealthBar;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.pets.Pet;


/**
 * Created by Serhat Merak on 16.03.2018.
 */

public class Spaceship extends Ship{
    private Stage stage;
    public float currentSpeedX,currentSpeedY;
    public Array<Body> sensoredBodies;

    public LaserController laserController;
    public Touchpad attackPad;

    private HealthBar healthBar;
    private boolean decreasedAttackSpeed = false;
    private float decreasedAttackSpeedElapsedTime = 0;

    public Label damagedLabel,lifestealLabel;
    public Pet pet;
    public boolean safeZone;


    public Spaceship(World world, Stage stage,Vector2 pos){
        super(world, User.user);
        this.stage = stage;

        createBody(pos);
        setPosition(pos.x - getWidth() / 2,pos.y - getHeight() / 2);

        sensoredBodies = new Array<Body>();

        createLaserController();
        createLabels();

        healthBar = new HealthBar(health);

        pet = new Pet(world,this,new Vector2(getX(),getY()  - 300));


    }

    private void createLabels() {
        BitmapFont font = Assets.getInstance().assetManager.get(Assets.fntAerial);
        damagedLabel = new Label("",new Label.LabelStyle(font, Color.RED));
        lifestealLabel = new Label("",new Label.LabelStyle(font, Color.GREEN));

        damagedLabel.setPosition(GameInfo.WIDTH / 2 - 200,GameInfo.HEIGHT / 2, Align.center);
        lifestealLabel.setPosition(GameInfo.WIDTH / 2 + 200,GameInfo.HEIGHT / 2,Align.center);


        stage.addActor(damagedLabel);
        stage.addActor(lifestealLabel);


    }

    private void createLaserController() {
        laserController = new LaserController(world,this);
        laserController.attackSpeed = attackSpeed;
        laserController.damage = damage;
        laserController.fromEnemy = false;
        laserController.decreaseAttackSpeed = User.user.getDecreasedAttacSpeed();
        laserController.moreDamage = User.user.getMoreDamage();
        laserController.lifeSteal = User.user.getLifeSteal();
        laserController.moreCoin = User.user.getMoreCoin();
    }
    private void createBody(Vector2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = pos.x / GameInfo.PPM;
        bodyDef.position.y = pos.y / GameInfo.PPM;

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 3) / GameInfo.PPM,(getHeight() / 3) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Object[]{this,"SPACESHIP"});

        //sensor
        CircleShape sensorShape = new CircleShape();
        sensorShape.setRadius(700 / GameInfo.PPM);
        FixtureDef sensorDef = new FixtureDef();

        sensorDef.shape = sensorShape;
        Fixture sensorFixture = body.createFixture(sensorDef);
        sensorFixture.setUserData(new Object[]{this,"SPACESHIP_SENSOR"});
        sensorFixture.setSensor(true);


    }

    public void move(float speedX,float speedY){
        currentSpeedX = speedX * speed;
        currentSpeedY = speedY * speed;
        body.setLinearVelocity(speedX * speed,speedY * speed);
        setPosition((body.getPosition().x * GameInfo.PPM) - getWidth() / 2,
                (body.getPosition().y * GameInfo.PPM) - getHeight() / 2);
        if(speedX != 0 && speedY !=0) {
            setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * speedX, 1 * speedY));
            body.setTransform(body.getPosition(),getRotation() * MathUtils.degreesToRadians);
        }

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
        if(pet!= null)
            pet.update(new Vector2(speedX,speedY));
    }

    @Override
    public boolean getDamage(float damage) {
        boolean returnBool = super.getDamage(damage);
        damagedLabel.setText(damage + "");
                if(damagedLabel.getActions().size > 0)
                    damagedLabel.removeAction(damagedLabel.getActions().get(
                            damagedLabel.getActions().size - 1));

                damagedLabel.addAction(Actions.sequence(Actions.fadeOut(0f),Actions.fadeIn(0.05f),
                        Actions.scaleTo(2,2,0.5f),Actions.fadeOut(0.5f)));

        return returnBool;
    }

    public void atack(){
        if(attackPad.getKnobPercentX() != 0 && attackPad.getKnobPercentY() != 0) {
            setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * attackPad.getKnobPercentX(),
                    1 * attackPad.getKnobPercentY()));
            body.setTransform(body.getPosition(),getRotation() * MathUtils.degreesToRadians);
        }
        laserController.update(new Vector2(attackPad.getKnobPercentX(), attackPad.getKnobPercentY()));

    }

    public void stolenLife(float stolenLife){
        if (stolenLife != 0) {
            lifestealLabel.setText(stolenLife + "");

            if (lifestealLabel.getActions().size > 0)
                lifestealLabel.removeAction(lifestealLabel.getActions().get(lifestealLabel.getActions().size - 1));

            lifestealLabel.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.fadeIn(0.05f),
                    Actions.scaleTo(2, 2, 0.5f), Actions.fadeOut(0.5f)));
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        healthBar.draw(batch);
        if(pet!= null)
            pet.draw(batch);
    }

    @Override
    public void decreaseAttackSpeed(int ratio) {
        laserController.attackSpeed += (laserController.attackSpeed * ratio) / 100;
        decreasedAttackSpeed = true;
    }

}
