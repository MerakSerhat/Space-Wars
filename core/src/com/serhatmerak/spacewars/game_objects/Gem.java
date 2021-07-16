package com.serhatmerak.spacewars.game_objects;

import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 24.03.2018.
 */

public class Gem extends GameObject {

    public int attackSpeed = 0;
    public int health = 0;
    public int damage = 0;
    public int speed = 0;
    public static I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);
    public GemStyle gemStyle;

    public Gem(GemStyle gemStyle){
        this.gemStyle = gemStyle;
        attackSpeed = gemStyle.attackSpeed;
        speed = gemStyle.speed;
        health = gemStyle.health;
        damage = gemStyle.damage;

        img = Assets.getInstance().assetManager.get(gemStyle.texture);
        name = gemStyle.name;
        color = gemStyle.color;
        info = gemStyle.info;
    }

//    private static final int[] zero = {};
    private static final String redInfo = "Increases damage of ship";
//    private static final int[] r1 = {1,5};
//    private static final int[] r2 = {5,10};
//    private static final int[] r3 = {10,15};
//    private static final int[] r4 = {15,20};
//    private static final int[] r5 = {20,25};
//    private static final int[] r6 = {25,30};
//    private static final int[] wr1 = {1,4};
//    private static final int[] wr2 = {4,8};
//    private static final int[] wr3 = {8,12};
//    private static final int[] wr4 = {12,16};
//    private static final int[] wr5 = {16,20};
//    private static final int[] wr6 = {20,24};
    private static final String greenInfo = "Increases health of ship";
//    private static final int[] g1 = {1,100};
//    private static final int[] g2 = {100,200};
//    private static final int[] g3 = {200,300};
//    private static final int[] g4 = {300,400};
//    private static final int[] g5 = {400,500};
//    private static final int[] g6 = {500,600};
//    private static final int[] wg1 = {1,75};
//    private static final int[] wg2 = {75,150};
//    private static final int[] wg3 = {150,225};
//    private static final int[] wg4 = {225,300};
//    private static final int[] wg5 = {300,375};
//    private static final int[] wg6 = {375,450};
    private static final String yellowInfo = "Increases attach speed of ship";
    private static final String blueInfo = "Increases speed of ship";
    private static final String whiteInfo = "Increases speed , attackSpeed , damage and health of ship";

    private static final int lv6Price = 64000;
    private static final int lv5Price = 32000;
    private static final int lv4Price = 16000;
    private static final int lv3Price = 8000;
    private static final int lv2Price = 4000;
    private static final int lv1Price = 2000;


    public enum GemStyle{


        RED1(0,0, 5,0,Assets.red_gem1,texts.get("redgem1"),redInfo,"[RED]",lv1Price),
        RED2(0,0, 10,0,Assets.red_gem2,texts.get("redgem2"),redInfo,"[RED]",lv2Price),
        RED3(0,0, 15,0,Assets.red_gem3,texts.get("redgem3"),redInfo,"[RED]",lv3Price),
        RED4(0,0, 20,0,Assets.red_gem4,texts.get("redgem4"),redInfo,"[RED]",lv4Price),
        RED5(0,0, 25,0,Assets.red_gem5,texts.get("redgem5"),redInfo,"[RED]",lv5Price),
        RED6(0,0, 30,0,Assets.red_gem6,texts.get("redgem6"),redInfo,"[RED]",lv6Price),
        GREEN1(0,100,0,0,Assets.green_gem1,texts.get("greengem1"),greenInfo,"[GREEN]",lv1Price),
        GREEN2(0,200,0,0,Assets.green_gem2,texts.get("greengem2"),greenInfo,"[GREEN]",lv2Price),
        GREEN3(0,300,0,0,Assets.green_gem3,texts.get("greengem3"),greenInfo,"[GREEN]",lv3Price),
        GREEN4(0,400,0,0,Assets.green_gem4,texts.get("greengem4"),greenInfo,"[GREEN]",lv4Price),
        GREEN5(0,500,0,0,Assets.green_gem5,texts.get("greengem5"),greenInfo,"[GREEN]",lv5Price),
        GREEN6(0,600,0,0,Assets.green_gem6,texts.get("greengem6"),greenInfo,"[GREEN]",lv6Price),
        YELLOW1(2,0,0,0,Assets.yellow_gem1,texts.get("yellowgem1"),yellowInfo,"[YELLOW]",lv1Price),
        YELLOW2(4,0,0,0,Assets.yellow_gem2,texts.get("yellowgem2"),yellowInfo,"[YELLOW]",lv2Price),
        YELLOW3(6,0,0,0,Assets.yellow_gem3,texts.get("yellowgem3"),yellowInfo,"[YELLOW]",lv3Price),
        YELLOW4(9,0,0,0,Assets.yellow_gem4,texts.get("yellowgem4"),yellowInfo,"[YELLOW]",lv4Price),
        YELLOW5(11,0,0,0,Assets.yellow_gem5,texts.get("yellowgem5"),yellowInfo,"[YELLOW]",lv5Price),
        YELLOW6(14,0,0,0,Assets.yellow_gem6,texts.get("yellowgem6"),yellowInfo,"[YELLOW]",lv6Price),
        BLUE1(0,0,0,2,Assets.blue_gem1,texts.get("bluegem1"),blueInfo,"[BLUE]",lv1Price),
        BLUE2(0,0,0,4,Assets.blue_gem2,texts.get("bluegem2"),blueInfo,"[BLUE]",lv2Price),
        BLUE3(0,0,0,6,Assets.blue_gem3,texts.get("bluegem3"),blueInfo,"[BLUE]",lv3Price),
        BLUE4(0,0,0,8,Assets.blue_gem4,texts.get("bluegem4"),blueInfo,"[BLUE]",lv4Price),
        BLUE5(0,0,0,10,Assets.blue_gem5,texts.get("bluegem5"),blueInfo,"[BLUE]",lv5Price),
        BLUE6(0,0,0,12,Assets.blue_gem6,texts.get("bluegem6"),blueInfo,"[BLUE]",lv6Price),
        WHITE1(1,75,3,1,Assets.white_gem1,texts.get("whitegem1"),whiteInfo,"[#c0c0c0]",lv1Price * 3),
        WHITE2(3,150,6,3,Assets.white_gem2,texts.get("whitegem2"),whiteInfo,"[#c0c0c0]",lv2Price * 3),
        WHITE3(5,225,9,5,Assets.white_gem3,texts.get("whitegem3"),whiteInfo,"[#c0c0c0]",lv3Price * 3),
        WHITE4(7,300,12,7,Assets.white_gem4,texts.get("whitegem4"),whiteInfo,"[#c0c0c0]",lv4Price * 3),
        WHITE5(9,375,15,9,Assets.white_gem5,texts.get("whitegem5"),whiteInfo,"[#c0c0c0]",lv5Price * 3),
        WHITE6(12,450,18,11,Assets.white_gem6,texts.get("whitegem6"),whiteInfo,"[#c0c0c0]",lv6Price * 3);


        public int attackSpeed;
        public int health;
        public int damage;
        public int speed;
        public String texture;
        public String name;
        public String info;
        public String color;
        public int price;

        GemStyle(int attackSpeed,int health,int damage,int speed,String texture,String name,String info,String color,int price){
            this.attackSpeed = attackSpeed;
            this.health = health;
            this.damage = damage;
            this.speed = speed;
            this.texture = texture;
            this.name = name;
            this.info = info;
            this.color = color;
            this.price = price;
        }
    }
}
