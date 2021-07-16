package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.pets.PetData;
import com.serhatmerak.spacewars.pets.PetStyle;

/**
 * Created by Serhat Merak on 8.04.2018.
 */

public class Ship extends Sprite{
    public SpaceShipStyle shipStyle;
    public World world;
    public Body body;
    public float speed;
    public float health;
    public float damage;
    public float attackSpeed;
    public float currentHelath;

    public boolean died;

    public Ship(World world, SpaceShipStyle shipStyle){
        super(Assets.getInstance().assetManager.get(shipStyle.shipTexture, Texture.class));
        this.world = world;
        this.shipStyle = shipStyle;

        speed = shipStyle.speed;
        health = shipStyle.health;
        damage = shipStyle.damage;
        attackSpeed = shipStyle.attackSpeed;

        currentHelath = health;
    }

    public Ship(World world, User user){
        super(User.user.getShipTexture());
        this.world = world;
        this.shipStyle = user.shipStyle;

        speed = user.getSpeed();
        health = user.getHealth();
        damage = user.getDamage();
        attackSpeed = user.getAttackSpeed();

        currentHelath = health;
    }

    public Ship(World world,String texture){
        super(Assets.getInstance().assetManager.get(texture,Texture.class));
        this.world = world;
        attackSpeed = User.user.getAttackSpeed();
    }

    public Ship(World world){
        super(Assets.getInstance().assetManager.get(PetData.petData.petStyle.petTexture,Texture.class));
        this.world = world;
        attackSpeed = User.user.getAttackSpeed();
    }

    public boolean getDamage(float damage){
        currentHelath -= damage;
        if(currentHelath < 0)
            currentHelath = 0;


        if (currentHelath == 0)
            died = true;

        return died;

    }

    public void decreaseAttackSpeed(int ratio) {
    }

    public void remove(){
        world.destroyBody(body);
    };
}
