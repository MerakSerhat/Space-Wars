package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.gameplay_objects.HealthBar;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.pets.Pet;

/**
 * Created by Serhat Merak on 31.07.2018.
 */

public class PvPShip extends Ship {

    private HealthBar healthBar;
    public LaserController laserController;
    public int decreaseAttackSpeed;
    public int moreDamage;
    public int lifeSteal;



    public PvPShip(World world,Vector2 pos,String texture) {
        super(world,texture);
        createBody(pos);
        setPosition(pos.x - getWidth() / 2,pos.y - getHeight() / 2);
        healthBar = new HealthBar(User.user.getHealth());
    }

    public void init(){
        createLaserController();
    }

    public void setBodyPos(float x,float y,float a){
        setRotation(a);
        body.setTransform(x,y,getRotation() * MathUtils.degreesToRadians);
    }


    private void createLaserController() {
        laserController = new LaserController(world,this);
        laserController.attackSpeed = attackSpeed;
        laserController.damage = damage;
        laserController.fromEnemy = false;
        laserController.decreaseAttackSpeed = User.user.getDecreasedAttacSpeed();
        laserController.moreDamage = User.user.getMoreDamage();
        laserController.lifeSteal = User.user.getLifeSteal();
        laserController.moreCoin = 0;
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
        fixture.setSensor(true);
        fixture.setUserData(new Object[]{this,"PvPShip"});

    }

    @Override
    public void draw(Batch batch) {
        setPosition((body.getPosition().x * GameInfo.PPM) - getWidth() / 2,
                (body.getPosition().y * GameInfo.PPM) - getHeight() / 2);
        super.draw(batch);
        healthBar.update(currentHelath,new Vector2(getX() + getWidth() / 2 - healthBar.size.x / 2,
                getY() + Math.max(getWidth(),getHeight()) + 20));
        healthBar.draw(batch);
    }
}
