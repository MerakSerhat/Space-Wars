package com.serhatmerak.spacewars.pets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.gameplay_objects.LaserController;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.Ship;
import com.serhatmerak.spacewars.ships.Spaceship;

/**
 * Created by Serhat Merak on 2.06.2018.
 */

public class Pet extends Ship{
    private World world;
    private Spaceship spaceship;
    private PetStyle petStyle;

    public Body body;

    private enum State{FOLLOWING,ATTACKING}
    private State state = State.FOLLOWING;

    private final int maxDistance = 1500;
    private final int minDistance = 400;
    private final int range = 500;
    private final int minEscapeDurationForHealing = 3;

    private Vector2 position,spaceShipPosition,focusedEnemyPosition;
    private float speedX,speedY;

    public float currentSpeed;

    public LaserController laserController;

    //TODO:sürekli arttır hasar gördüğünde sıfırla
    public float durationOfEscape = 0;
    //TODO:eğer düşman gemiye saldırırsa ilk sadırana odaklan ve true yap
    //TODO:eğer patlatılırsa veya range den çıkılırsa false yap
    //TODO:odaklanmışken gemi başka düşmana saldırırsa odak gemisini değiştir
    public boolean focusedToEnemy = false;
    public EnemyShip focusedEnemy;

    private float healingColdDown = 1;



    public Pet(World world, Spaceship spaceship,Vector2 position){
        super(world);
        setScale(0.7f);
        setOrigin(getWidth() / 2 ,getHeight() / 2);
        this.world = world;
        this.spaceship = spaceship;

        this.position = position;
        setPosition(position.x,position.y);

        createBody();

        petStyle = PetData.petData.petStyle;
        spaceShipPosition = new Vector2();
        focusedEnemyPosition = new Vector2();

        laserController = new LaserController(world,this);
        laserController.attackSpeed = PetData.petData.getAttackSpeed();
        laserController.damage = PetData.petData.getDamage();
        laserController.fromEnemy = false;
        laserController.lifeSteal = PetData.petData.getLifeSteal();
        laserController.moreCoin = PetData.petData.getMoreCoin();
        laserController.moreDamage = PetData.petData.getMoreDamage();
        laserController.decreaseAttackSpeed = PetData.petData.getDecreaseAttackSpeed();


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
        fixture.setUserData(new Object[]{this,"PET"});

    }






    public void update(Vector2 padData){
        position.set(getX(),getY());
        spaceShipPosition.set(spaceship.getX(),spaceship.getY());
        durationOfEscape += Gdx.graphics.getDeltaTime();
        if(focusedEnemy != null)
            focusedEnemyPosition.set(focusedEnemy.getX(),focusedEnemy.getY());

        setPosition((body.getPosition().x * GameInfo.PPM) - getWidth() / 2,
                (body.getPosition().y * GameInfo.PPM) - getHeight() / 2);


        if(state == State.FOLLOWING) {
            if (position.dst(spaceShipPosition) > minDistance) {
                followSpaceShip(padData);
            }else {
                setRotation(spaceship.getRotation());
                body.setTransform(body.getPosition(),spaceship.body.getAngle());
            }
            if(durationOfEscape > minEscapeDurationForHealing && spaceship.health != spaceship.currentHelath){
                healTheShip();
            }
            if(focusedToEnemy){
                state = State.ATTACKING;
            }


        }else {
            if(position.dst(spaceShipPosition) > maxDistance){
                state = State.FOLLOWING;
                focusedToEnemy = false;
                focusedEnemy = null;
                return;

            }
            if(!focusedToEnemy){
                state = State.FOLLOWING;
                return;
            }
            if(!(focusedEnemy.currentHelath > 0)){
                state = State.FOLLOWING;
                focusedToEnemy = false;
                focusedEnemy = null;
                return;
            }
            attackToEnemy();

        }


    }

    private void attackToEnemy() {
        if(position.dst(focusedEnemyPosition) < range){

            setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * ((focusedEnemy.getX() + focusedEnemy.getWidth() / 2) - (getX() + getWidth() / 2)),
                    (focusedEnemy.getY() + focusedEnemy.getHeight() / 2) - (getY() + getHeight() / 2)));

            body.setTransform(body.getPosition(), getRotation() * MathUtils.degreesToRadians);
            body.setLinearVelocity(0,0);

            laserController.update(new Vector2((focusedEnemy.getX() + focusedEnemy.getWidth() / 2) - (getX() + getWidth() / 2),
                    (focusedEnemy.getY() + focusedEnemy.getHeight() / 2) - (getY() + getHeight() / 2) ));
        }else {
            Vector2 coor = new Vector2(focusedEnemy.getX() + focusedEnemy.getWidth() / 2,
                    focusedEnemy.getY() + focusedEnemy.getHeight() / 2);

            float gapX = Math.abs(coor.x - (getX() + getWidth() / 2));
            float gapY = Math.abs(coor.y - (getY() + getHeight() / 2));

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

            body.setLinearVelocity(spaceship.speed * speedX, spaceship.speed * speedY);

            setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * (coor.x - (getX() + getWidth() / 2)),
                    coor.y - (getY() + getHeight() / 2)));
            body.setTransform(body.getPosition(), getRotation() * MathUtils.degreesToRadians);
        }

    }

    private void followSpaceShip(Vector2 padData) {
        Vector2 coor = new Vector2(spaceship.getX() + spaceship.getWidth() / 2,
                spaceship.getY() + spaceship.getHeight() / 2);

        float gapX = Math.abs(coor.x - (getX() + getWidth() / 2));
        float gapY = Math.abs(coor.y - (getY() + getHeight() / 2));

        setRotation(spaceship.getRotation());
        body.setTransform(body.getPosition(),spaceship.body.getAngle());

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

        body.setLinearVelocity(spaceship.speed * speedX * Math.abs(padData.x), spaceship.speed * speedY * Math.abs(padData.y));
    }

    private void healTheShip() {
        healingColdDown += Gdx.graphics.getDeltaTime();

        if(position.dst(spaceShipPosition) > range){
            followSpaceShip(new Vector2(1,1));
        }else {
            if((int) healingColdDown >= 1){

                healingColdDown = 0;
                if(spaceship.health - spaceship.currentHelath < petStyle.healPerSecond) {
                    spaceship.stolenLife(spaceship.health - spaceship.currentHelath);
                    spaceship.currentHelath += spaceship.health - spaceship.currentHelath;
                }else {
                   spaceship.stolenLife(petStyle.healPerSecond);
                   spaceship.currentHelath += petStyle.healPerSecond;
                }
            }
        }

        setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * (spaceship.getX() + spaceship.getWidth() / 2 - (getX() + getWidth() / 2)),
                spaceship.getY() + spaceship.getHeight() / 2 - (getY() + getHeight() / 2)));
        body.setTransform(body.getPosition(), getRotation() * MathUtils.degreesToRadians);
    }

    @Override
    public float getWidth() {
        return getTexture().getWidth() * getScaleX();
    }

    @Override
    public float getHeight() {
        return getTexture().getHeight() * getScaleY();
    }
}
