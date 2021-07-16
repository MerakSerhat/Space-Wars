package com.serhatmerak.spacewars.pets;

import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;

/**
 * Created by Serhat Merak on 2.06.2018.
 */

public class PetData {
    public static final PetData petData = new PetData();
    private PetData() {
    }

    public PetStyle petStyle;
    public Array<Weapon> weapons;
    public Array<Gem> gems;

    public void create(PetStyle petStyle){
        this.petStyle = petStyle;
        weapons = new Array<Weapon>(petStyle.weaponNest);
        gems = new Array<Gem>(petStyle.gemNest);

        for (int i = 0; i < petStyle.weaponNest; i++) {
            weapons.add(null);
        }
        for (int i = 0; i < petStyle.gemNest; i++) {
            gems.add(null);
        }
    }

    public int getDamage() {
        int damage = petStyle.damage;
        for(Gem gem:PetData.petData.gems){
            if(gem != null)
                damage += gem.damage / 2;
        }

        for(Weapon weapon:PetData.petData.weapons){
            if(weapon != null)
                damage += weapon.damage / 2;
        }

        return damage;
    }
    public float getAttackSpeed() {
        float attackSpeed = petStyle.attackSpeed;

        for(Gem gem:PetData.petData.gems){
            if(gem != null)
                attackSpeed -= (gem.attackSpeed * attackSpeed ) / 100;
        }

        return attackSpeed;
    }
    public int getLifeSteal(){
        int lifeSteal = 0;

        for(Weapon weapon:PetData.petData.weapons){
            if(weapon != null)
                lifeSteal += weapon.lifesteal;
        }

        return lifeSteal;
    }
    public int getDecreaseAttackSpeed(){
        int decreaseAttackSpeed = 0;

        for(Weapon weapon:PetData.petData.weapons){
            if(weapon != null)
                decreaseAttackSpeed += weapon.decreaseSpeed;
        }

        return decreaseAttackSpeed;
    }
    public int getMoreDamage(){
        int moreDamage = 0;

        for(Weapon weapon:PetData.petData.weapons){
            if(weapon != null)
                moreDamage += weapon.moreDamage;
        }

        return moreDamage;
    }
    public int getMoreCoin(){
        int moreCoin = 0;

        for(Weapon weapon:PetData.petData.weapons){
            if(weapon != null)
                moreCoin += weapon.moreCoin;
        }

        return moreCoin;
    }

}
