package com.serhatmerak.spacewars.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.stations.MissionStation;
import com.serhatmerak.spacewars.stations.Portal;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 21.03.2018.
 */

public class Map1 extends Map{
    public static String name = "Red Map";


private SpaceShipStyle[] spaceShipStyles = new SpaceShipStyle[]{
        SpaceShipStyle.RedShip1,SpaceShipStyle.RedShip2,SpaceShipStyle.RedShip3,SpaceShipStyle.RedShip4
};
    private int[] amounts = new int[]{10,10,10,5};





    public Map1(GameMain gameMain,Class<? extends Object> previousScreen){
        super(gameMain,previousScreen);
        mapName = Map1.class;

        bgSize = 5000;
        mapSize = 25000;
        setBackground();
        enemyController.createRandomEnemies(spaceShipStyles,amounts,new Vector2(mapSize,mapSize));
//        pix.setColor(Color.valueOf("#100b1f"));
        pix.setColor(Color.BLACK);
        pix.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        createPortals();
        createCrystals();
        findShipPosition(previousScreen);

        missionStation = new MissionStation(gameMain,new Vector2(0,1000));






    }

    private void createCrystals() {
        for (int i = 0; i < 2; i++) {
            Crystal crystal = new Crystal(6000, new Vector2(MathUtils.random(0, 25000), MathUtils.random(0, 25000)), world);
            crystal.addCoin(new float[]{500, 1000});
            crystal.addGem(75, Gem.GemStyle.RED1);
            crystal.addGem(50, Gem.GemStyle.RED2);
            crystal.addGem(25, Gem.GemStyle.RED3);
            crystal.addGem(10, Gem.GemStyle.RED4);
            crystal.addWeapon(10, Weapon.WeaponStyle.Red_1);
            crystal.addWeapon(3, Weapon.WeaponStyle.Red_2);
            crystals.add(crystal);
        }

    }

    private void createPortals() {
        portals = new Array<Portal>();
        portals.add(new Portal(game,new Vector2(21000,3000),Base.class));
        for(Portal portal:portals){
            portal.currentScreen = this.getClass();
        }
    }

    private void setBackground() {
       layers = new Array<Layers>();
       layers.add(new Layers(coldNeblua,new Vector2(120,800)));
       layers.add(new Layers(hotNebula,new Vector2(3700,3500)));
       layers.add(new Layers(blueDust,new Vector2(400,300)));
       layers.add(new Layers(blueDust,new Vector2(4000,300)));
       layers.add(new Layers(blueDust,new Vector2(3600,800)));
       layers.add(new Layers(blueDust,new Vector2(2400,2400)));
       layers.add(new Layers(blueDust,new Vector2(1000,3400)));
       layers.add(new Layers(blueDust,new Vector2(1800,1200)));
       layers.add(new Layers(sun,new Vector2(3000,3000)));
       layers.add(new Layers(planet1,new Vector2(700,4000)));
       layers.add(new Layers(planet2,new Vector2(4000,400)));
       layers.add(new Layers(earth,new Vector2(2000,800)));


    }

    @Override
    public void drawEnemies(SpriteBatch batch) {

        for(Crystal crystal:crystals) {
            crystal.draw(batch);
        }

        for(EnemyShip enemyShip:enemyController.enemyShips){
            enemyShip.draw(batch);
        }
    }

    @Override
    public void updateEnemies() {
        for(EnemyShip enemyShip:enemyController.enemyShips){
            enemyShip.move();
        }

        for(Crystal crystal:crystals)
            crystal.update();    }

    @Override
    public void drawMinimapBackground(SpriteBatch batch,float size, float x, float y){
        pix.setBounds(x,y,size,size);
        pix.draw(batch);
        pix.setBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);
        batch.draw(star,x,y,size,size);
        for (Layers layer : layers){
            batch.draw(layer.texture,x + ((size * layer.pos.x ) / bgSize), y + ((size * layer.pos.y ) / bgSize)
            ,(size * layer.texture.getWidth()) / bgSize,(size * layer.texture.getHeight()) / bgSize);
        }
    }
}
