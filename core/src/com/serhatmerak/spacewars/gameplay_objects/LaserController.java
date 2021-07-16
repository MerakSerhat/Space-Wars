package com.serhatmerak.spacewars.gameplay_objects;

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
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.Ship;

import io.socket.client.Socket;

/**
 * Created by Serhat Merak on 8.04.2018.
 */

public class LaserController {

    private final float laserSpeed = 100f;

    private World world;
    private Ship ship;

    public float damage;
    public float attackSpeed;

    private Array<Laser> laserPool;
    public Array<Laser> activeLasers;
    private float elapsedTime;

    public boolean fromEnemy;

    public int decreaseAttackSpeed,lifeSteal,moreDamage,moreCoin;

    public Socket socket;



    public LaserController(World world, Ship ship){
        this.world = world;
        this.ship = ship;

        laserPool = new Array<Laser>();
    }

    public void Attack(Vector2 coor){
        Laser laser;

        if(laserPool.size > 0){
            laser = laserPool.first();
            laserPool.removeValue(laserPool.first(),false);
        }else {
            laser = new Laser(world);
        }

        laser.decreaseAttackSpeed = decreaseAttackSpeed;
        laser.moreDamage = moreDamage;
        laser.lifeSteal = lifeSteal;
        laser.moreCoin = moreCoin;

        Vector2 vector2 = rotateAround(
                new Vector2(ship.getX() + ship.getWidth() / 2,ship.getY() + ship.getHeight() ),
                new Vector2(ship.getX() + ship.getWidth() / 2,ship.getY() + ship.getHeight() / 2),
                ship.getRotation());


        laser.setOriginBasedPosition(vector2.x,vector2.y);
        laser.setFirstPoint(new Vector2(vector2.x,vector2.y));
        laser.setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(-1 * coor.x, 1 * coor.y));


        float speedX,speedY;

        float ratio = 1f / Math.max(Math.abs(coor.x),Math.abs(coor.y));

        speedX = ratio * coor.x;
        speedY = ratio * coor.y;

        laser.speed = new Vector2(laserSpeed * speedX,laserSpeed * speedY);

        laser.damage = damage;
        laser.fromEnemy = fromEnemy;
        laser.ship = ship;




        activeLasers.add(laser);

        if(socket != null)
            socket.emit("playerAttacked",coor.x,coor.y);


    }

    public void update(Vector2 coor){

        elapsedTime += Gdx.graphics.getDeltaTime();

        if(coor.x != 0 || coor.y != 0){
            if(elapsedTime > attackSpeed){
                Attack(coor);
                elapsedTime = 0;
            }
        }
    }

    private Vector2 rotateAround(Vector2 vector,Vector2 origin,float rotation){
        return vector.sub(origin).rotate(rotation).add(origin);
    }

    public class Laser extends Sprite{
        private World world;
        public float damage;
        public Body body;
        Vector2 speed;
        public Ship ship;
        private Vector2 firstPoint;
        private final int distance = 750;

        public boolean fromEnemy;
        public int decreaseAttackSpeed,lifeSteal,moreDamage,moreCoin;


        public Laser(World world){
            super(Assets.getInstance().assetManager.get(Assets.laser,Texture.class));
            this.world = world;
            createBody();
        }

        private void createBody() {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.x = getX()  / GameInfo.PPM;
            bodyDef.position.y = getY() / GameInfo.PPM;

            body = world.createBody(bodyDef);
            body.setBullet(true);


            PolygonShape shape = new PolygonShape();
            shape.setAsBox((getWidth() / 2) / GameInfo.PPM,(getHeight() / 2) / GameInfo.PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;

            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setSensor(true);
            fixture.setUserData(new Object[]{this,"LASER"});
        }

        public void update(){
            setPosition(getX() + speed.x,getY() + speed.y);
            body.setTransform((getX() + getWidth() / 2) / GameInfo.PPM,
                    (getY() + getHeight() / 2) / GameInfo.PPM,getRotation() * MathUtils.degreesToRadians);

            if(firstPoint.dst(new Vector2(getX() + getWidth() / 2,getY() + getHeight() / 2)) > distance)
                body.setUserData("REMOVE");


        }

        public boolean isMaxDistance(){
            return firstPoint.dst(new Vector2(getX() + getWidth() / 2,getY() + getHeight() / 2)) > distance;
        }

        public void setFirstPoint(Vector2 firstPoint) {
            this.firstPoint = firstPoint;
        }

    }


}
