package com.serhatmerak.spacewars.gameplay_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;

/**
 * Created by Serhat Merak on 29.04.2018.
 */

public class Crystal extends Sprite{

    private World world;

    public float health;
    public float currentHealth;
    public Body body;
    public Vector2 position;
    private HealthBar healthBar;
    private float scale = 1;
    private boolean increasing = true;

    public Array<GameObjectCollector> gameObjectCollectors;

    public Crystal(float health, Vector2 pos, World world){
        super(Assets.getInstance().assetManager.get(Assets.crystal, Texture.class));
        this.world = world;
        this.position = pos;
        setPosition(pos.x,pos.y);
        this.health = health;
        this.currentHealth = health;
        healthBar = new HealthBar(health);
        createBody(pos);
        gameObjectCollectors = new Array<GameObjectCollector>();
    }

    private void createBody(Vector2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = (pos.x + getWidth() / 2) / GameInfo.PPM;
        bodyDef.position.y = (pos.y + getHeight() / 2) / GameInfo.PPM;

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 3) / GameInfo.PPM,(getHeight() / 3) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Object[]{this,"CRYSTAL"});

    }

    public void setPosition(Vector2 position) {
        body.setTransform((position.x + getWidth() / 2) / GameInfo.PPM,(position.y + getHeight() / 2) / GameInfo.PPM,0);
        setPosition(position.x,position.y);
        this.position = position;
    }

    public void addGem(float percent,Gem.GemStyle gemStyle){
        float random = MathUtils.random(1,100);
        if(percent >= random){
            GameObjectCollector gameObjectCollector = new GameObjectCollector();
            gameObjectCollector.state = GameObjectCollector.State.GEM;
            gameObjectCollector.object = gemStyle;
            gameObjectCollectors.add(gameObjectCollector);
        }
    }

    public void addWeapon(float percent, Weapon.WeaponStyle weaponStyle){
        float random = MathUtils.random(1,100);
        if(percent >= random){
            GameObjectCollector gameObjectCollector = new GameObjectCollector();
            gameObjectCollector.state = GameObjectCollector.State.WEAPON;
            gameObjectCollector.object = weaponStyle;
            gameObjectCollectors.add(gameObjectCollector);
        }
    }

    public void addCoin(float [] coin){
        GameObjectCollector gameObjectCollector = new GameObjectCollector();
        gameObjectCollector.state = GameObjectCollector.State.COIN;
        gameObjectCollector.object = (int) MathUtils.random(coin[0],coin[1]);
        gameObjectCollectors.add(gameObjectCollector);

    }



    public boolean getDamage(float damage){
        currentHealth -=  damage;
        if(currentHealth < 0) {
            currentHealth = 0;
            return true;
        }

        return false;
    }

    public void update(){
        healthBar.update(currentHealth,new Vector2(getX() + getWidth() / 2 - healthBar.size.x / 2,
                getY() + getHeight() + 20));
        if(increasing)
            scale += Gdx.graphics.getDeltaTime();
        else
            scale -= Gdx.graphics.getDeltaTime();

        if(scale > 1.3f) {
            scale = 1.3f;
            increasing = false;
        }
        else if (scale < 1f){
            scale = 1f;
            increasing = true;
        }




        setScale(scale);
    }


    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        healthBar.draw(batch);
    }
}
