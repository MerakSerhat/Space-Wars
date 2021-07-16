package com.serhatmerak.spacewars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.database.GemData;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.maps.Map1;
import com.serhatmerak.spacewars.maps.Map2;
import com.serhatmerak.spacewars.pets.PetData;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 24.03.2018.
 */

public class User {
    public static final User user = new User();
    private static User getInstance() {
        return user;
    }

    private User() {}

    public SpaceShipStyle shipStyle;
    public Array<Gem> gemBag;
    public Array<Weapon> weaponBag;
    public Array<Gem> gems;
    public Array<Weapon> weapons;
    public Mission mission;
    public int coin = 0;

    public void create(){
        gemBag = new Array<Gem>();
        weaponBag = new Array<Weapon>();
        gems = new Array<Gem>(5);
        weapons = new Array<Weapon>(3);
        weapons.add(null);
        weapons.add(null);
        weapons.add(null);
        gems.add(null);
        gems.add(null);
        gems.add(null);
        gems.add(null);
        gems.add(null);

        GameManager.gameManager.getShips();
        PetData.petData.create(PetData.petData.petStyle);
        getData();



    }

    public float getDamage(){
        float damage = shipStyle.damage;
        for(Gem gem:gems) {
            if(gem != null)
            damage += gem.damage;
        }
        for(Weapon weapon:weapons) {
            if (weapon != null)
                damage += weapon.damage;
        }

        return damage;
    }
    public float getAttackSpeed(){
        int gemAttackSpeed = 0;
        for (Gem gem :gems) {
            if(gem != null)
                gemAttackSpeed += gem.attackSpeed;
        }

        return shipStyle.attackSpeed - ((shipStyle.attackSpeed * gemAttackSpeed) / 100);
    }
    public float getSpeed(){
        int gemSpeed = 0;
        for (Gem gem :gems) {
            if(gem != null)
                gemSpeed += gem.speed;

        }

        return shipStyle.speed + ((shipStyle.speed * gemSpeed) / 100);
    }
    public float getHealth(){
        float health = shipStyle.health;
        for(Gem gem:gems) {
            if(gem != null)
                health += gem.health;

        }

        return health;
    }

    public int getDecreasedAttacSpeed(){
        int decreasedAttackSpeed = 0;

        for(Weapon weapon:weapons){
            if(weapon != null)
                decreasedAttackSpeed += weapon.decreaseSpeed;
        }

        return decreasedAttackSpeed;
    }

    public int getMoreCoin(){
        int moreCoin = 0;

        for(Weapon weapon:weapons){
            if(weapon != null)
                moreCoin += weapon.moreCoin;
        }

        return moreCoin;
    }
    public int getMoreDamage(){
        int moreDamage = 0;

        for(Weapon weapon:weapons){
            if(weapon != null)
                moreDamage += weapon.moreDamage;
        }

        return moreDamage;
    }
    public int getLifeSteal(){
        int lifeSteal = 0;

        for(Weapon weapon:weapons){
            if(weapon != null)
                lifeSteal += weapon.lifesteal;
        }

        return lifeSteal;
    }


    public Texture getShipTexture(){
        return Assets.getInstance().assetManager.get(shipStyle.shipTexture,Texture.class);
    }

    private void getData() {
        gemBag = GameManager.gameManager.getGemBag();
        weaponBag = GameManager.gameManager.getWepBag();
        GameManager.gameManager.getSlots();
        mission = GameManager.gameManager.getMission();
        coin = GameManager.gameManager.loadCoin();
    }
}
