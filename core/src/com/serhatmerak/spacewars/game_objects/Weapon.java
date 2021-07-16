package com.serhatmerak.spacewars.game_objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 18.03.2018.
 */

public class Weapon extends GameObject{
    public int lifesteal = 0;
    public int moreDamage = 0;
    public int decreaseSpeed = 0;
    public int moreCoin = 0;
    public int damage = 0;
    public int level = 1;

    public static I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public WeaponStyle weaponStyle;

    public Weapon(WeaponStyle weaponStyle){

        this.weaponStyle = weaponStyle;

        switch (weaponStyle){
            case Red_1: moreDamage = 3;
                break;
            case Red_2: moreDamage = 6;
                break;
            case Yellow_1: moreCoin = 10;
                break;
            case Yellow_2: moreCoin = 20;
                break;
            case Blue_1: decreaseSpeed = 10;
                break;
            case Blue_2: decreaseSpeed = 20;
                break;
            case Green_1: lifesteal = 3;
                break;
            case Green_2: lifesteal = 6;
                break;
            case White:{
                lifesteal = 4;
                decreaseSpeed = 15;
                moreDamage = 4;
            }
        }

        info = weaponStyle.info;
        name = weaponStyle.name;
        img = Assets.getInstance().assetManager.get(weaponStyle.texture);
        damage = weaponStyle.damage + (level * 5);
        color = weaponStyle.color;
    }

    public void levelUp(){
        level++;
        damage = weaponStyle.damage + (level * 5);
    }

    private static final int lv1Price = 8000;
    private static final int lv2Price = 64000;


    public enum WeaponStyle{
        Red_1(0,Assets.redwep1,"3% More damage",texts.get("redwep1"),"[RED]",lv1Price),
        Blue_1(0,Assets.bluewep1,"10% speed reduction",texts.get("bluewep1"),"[BLUE]",lv1Price),
        Yellow_1(0,Assets.yellowwep1,"10% More coin",texts.get("yellowwep1"),"[YELLOW]",lv1Price),
        Green_1(0,Assets.greenwep1,"3% Life steal",texts.get("greenwep1"),"[GREEN]",lv1Price),
        Red_2(25,Assets.redwep2,"6% More damage",texts.get("redwep2"),"[RED]",lv2Price),
        Blue_2(25,Assets.bluewep2,"20 speed reduction%",texts.get("bluewep2"),"[BLUE]",lv2Price),
        Yellow_2(25,Assets.yellowwep2,"20% More coin",texts.get("yellowwep2"),"[YELLOW]",lv2Price),
        Green_2(25,Assets.greenwep2,"6% Life steal",texts.get("greenwep2"),"[GREEN]",lv2Price),
        White(45,Assets.whitewep,"4% More Damage\n15% Speed Reduction\n4% Life Steal",texts.get("whitewep"),"[#c0c0c0]",lv2Price * 3);


        public String texture;
        public String info;
        public String name;
        public int damage;
        public String color;
        public int price;

        WeaponStyle(int damage,String texture,String info,String name,String color,int price){
            this.color = color;
            this.damage = damage;
            this.texture = texture;
            this.info = info;
            this.name = name;
            this.price = price;
        }
    }
}
