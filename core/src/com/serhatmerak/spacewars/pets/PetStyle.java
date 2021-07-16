package com.serhatmerak.spacewars.pets;

import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 2.06.2018.
 */

public enum PetStyle {
    PET1(Assets.pet1,5,0.5f,1,1,20,0),
    PET2(Assets.pet2,15,0.4f,2,1,40,15000),
    PET3(Assets.pet3,25,0.3f,3,1,80,50000),
    PET4(Assets.pet4,35,0.2f,3,2,120,120000);

    public String petTexture;
    public int damage;
    public float attackSpeed;
    public int gemNest;
    public int weaponNest;
    public int healPerSecond;
    public int price;

    PetStyle(String petTexture,int damage,float attackSpeed
    ,int gemNest,int weaponNest, int healPerSecond,int price){
        this.petTexture = petTexture;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.gemNest = gemNest;
        this.weaponNest = weaponNest;
        this.healPerSecond = healPerSecond;
        this.price = price;
    }
}
