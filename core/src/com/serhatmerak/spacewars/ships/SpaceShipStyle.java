package com.serhatmerak.spacewars.ships;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.game_objects.GameObjectBag;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 16.03.2018.
 */

public enum SpaceShipStyle {
    Ship1(Assets.ship1,750,7f,20f,0.5f,null,0),
    Ship2(Assets.ship2,1000,8f,30f,0.45f,null,5000),
    Ship3(Assets.ship3,1200,10f,30f,0.25f,null,25000),
    Ship4(Assets.ship4,2500,7f,60f,0.5f,null,50000),
    Ship5(Assets.ship5,1500,13f,60f,0.3f,null,100000),
    Ship6(Assets.ship6,2500,10f,80f,0.3f,null,150000),
    Ship7(Assets.ship7,2000,13f,120f,0.25f,null,300000),
    GreenShip1(Assets.greenship1,200,9f,10f,0.375f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(50),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN1,8),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_1, 1)
    },0),
    GreenShip2(Assets.greenship2,150,9f,35f,0.275f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(100),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN1,8),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN2,4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_1, 1)

    },0),
    GreenShip3(Assets.greenship3,300,8f,35f,0.3f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(150),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN1,8),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN2,6),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN3,3),

            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_1, 2)

    },0),
    GreenShip4(Assets.greenship4,250,12f,40f,0.25f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN1, 10),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN2, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN3, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN4, 2),

            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_1, 5)
    },0),
    RedShip1(Assets.redship1,130,8,10f,0.30f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(50),
            new GameObjectCollector().addGem(Gem.GemStyle.RED1,8),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_1,1)
    },0),
    RedShip2(Assets.redship2,130,12f,10f,0.25f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(100),
            new GameObjectCollector().addGem(Gem.GemStyle.RED1, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.RED2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_1, 1)
    },0),
    RedShip3(Assets.redship3,180,12f,10f,0.20f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(150),
            new GameObjectCollector().addGem(Gem.GemStyle.RED1, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.RED2, 6),
            new GameObjectCollector().addGem(Gem.GemStyle.RED3, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_1, 2)
    },0),
    RedShip4(Assets.redship4,400,9f,30f,0.35f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.RED1, 10),
            new GameObjectCollector().addGem(Gem.GemStyle.RED2, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.RED3, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.RED4, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_1, 5)
    },0),
    BlueShip1(Assets.blueship1,300,9f,20f,0.375f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(50),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE1,8),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_1,1)
    },0),
    BlueShip2(Assets.blueship2,240,11f,25f,0.3f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(100),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE1,8),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE2,4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_1,1)
    },0),
    BlueShip3(Assets.blueship3,550,9f,30f,0.3f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(150),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE1,4),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE2,6),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3,3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_1,2)
    },0),
    BlueShip4(Assets.blueship4,350,12f,60f,0.25f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE1, 10),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE2, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_1, 5)
    },0),
    OrangeShip1(Assets.orangeship1,150,9f,15,0.375f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(50),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW1, 8),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_1, 1)
    },0),
    OrangeShip2(Assets.orangeship2,150,9f,15,0.375f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(100),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW1, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_1, 1)
    },0),
    OrangeShip3(Assets.orangeship3,250,9f,25,0.375f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(150),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW1, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2, 6),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW3, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_1, 2)
    },0),
    OrangeShip4(Assets.orangeship4,600,9f,50,0.4f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW1, 10),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW2, 8),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW3, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW4, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_1, 5)
    },0),
    BlackShip1(Assets.blackship1,300,10f,50f,0.14f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW3, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN3, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.RED3, 5),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 1)
    },0),
    BlackShip2(Assets.blackship2,300,12f,70f,0.18f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(250),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW3, 6),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN3, 6),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3, 6),
            new GameObjectCollector().addGem(Gem.GemStyle.RED3, 6),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 2)
    },0),
    BlackShip3(Assets.blackship3,350,12f,100f,0.14f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(300),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW4, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN4, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE4, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.RED4, 5),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 3)
    },0),
    BlackShip4(Assets.blackship4,1500,12f,120,0.12f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(400),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW5, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN5, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE5, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.RED5, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW6, 2),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN6, 2),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE6, 2),
            new GameObjectCollector().addGem(Gem.GemStyle.RED6, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 3)
    },0),
    Droid1(Assets.droid1,300,10f,50f,0.2f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(200),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW3, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN3, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE3, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.RED3, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE1, 5)
    },0),
    Droid2(Assets.droid2,300,12f,50f,0.18f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(250),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW4, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN4, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE4, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.RED4, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 2),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 2),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE1, 7),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE2, 3)

    },0),
    Droid3(Assets.droid3,300,18f,100f,0.18f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(300),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.RED5, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 3),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE1, 7),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE2, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE3, 3)

    },0),
    Droid4(Assets.droid4,1500,12f,100,0.2f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(400),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.RED5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.RED6, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE3, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE4, 3),

    },0),
    Droid5(Assets.droid5,1500,14f,100,0.12f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(700),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.RED5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.YELLOW6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.GREEN6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.BLUE6, 1),
            new GameObjectCollector().addGem(Gem.GemStyle.RED6, 1),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Yellow_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Green_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Red_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.Blue_2, 4),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.White, 4),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE3, 7),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE4, 5),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE5, 3),
    },0),
    Crystal_Protector(Assets.crystal_protector,3000,18f,250,0.1f,new GameObjectCollector[]{
            new GameObjectCollector().addCoin(1000),
            new GameObjectCollector().addWep(Weapon.WeaponStyle.White, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE5, 3),
            new GameObjectCollector().addGem(Gem.GemStyle.WHITE6, 2),
    },0);

    public String shipTexture;
    public int health;
    public float speed;
    public float damage;
    public float attackSpeed;
    public GameObjectCollector[] gameObjectCollectors;
    public int price;

    SpaceShipStyle(String value,int health,float speed,float damage,float attackSpeed,GameObjectCollector[] gameObjectCollectors
    ,int price) {
        this.shipTexture = value;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.gameObjectCollectors = gameObjectCollectors;
        this.price = price;
    }
}
