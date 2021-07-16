package com.serhatmerak.spacewars.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;
import com.serhatmerak.spacewars.stations.MissionStation;
import com.serhatmerak.spacewars.stations.Portal;

/**
 * Created by Serhat Merak on 7.07.2018.
 */

public class Base extends Map {
    public static String name = "Base";

    private SpaceShipStyle[] spaceShipStyles = new SpaceShipStyle[]{
            SpaceShipStyle.GreenShip1,SpaceShipStyle.GreenShip2,
            SpaceShipStyle.RedShip1,SpaceShipStyle.RedShip2,
            SpaceShipStyle.OrangeShip1,SpaceShipStyle.OrangeShip2,
            SpaceShipStyle.BlueShip1,SpaceShipStyle.BlueShip2
    };
    private int[] amounts = new int[]{4,2,4,2,4,2,4,2};


    public Base(GameMain gameMain,Class<? extends Object> previousScreen){
        super(gameMain,previousScreen);
        mapName = Base.class;
        name = "Base";
        bgSize = 5000;
        mapSize = 25000;
        setBackground();
        pix.setColor(Color.BLACK);
        pix.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);

        enemyController.createRandomEnemies(spaceShipStyles,amounts,new Vector2(mapSize,mapSize));
        createPortals();
        createCrystals();
        findShipPosition(previousScreen);

        missionStation = new MissionStation(gameMain,new Vector2(10800,2000));






    }

    private void createCrystals() {
        for (int i = 0; i < 2; i++) {
            Crystal crystal1 = new Crystal(2000, new Vector2(MathUtils.random(0, 25000), MathUtils.random(0, 25000)), world);
            crystal1.addCoin(new float[]{250, 500});
            crystal1.addGem(50,Gem.GemStyle.RED1);
            crystal1.addGem(50,Gem.GemStyle.YELLOW1);
            crystal1.addGem(50,Gem.GemStyle.GREEN1);
            crystal1.addGem(50,Gem.GemStyle.BLUE1);

            crystals.add(crystal1);
        }
    }

    private void createPortals() {
        portals = new Array<Portal>();
        portals.add(new Portal(game,new Vector2(21000,22000),Map1.class).setIconColor(Color.RED));
        portals.add(new Portal(game,new Vector2(21000,3000),Map2.class).setIconColor(Color.YELLOW));
        portals.add(new Portal(game,new Vector2(0,22000),Map3.class).setIconColor(Color.valueOf("#0099ff")));
        portals.add(new Portal(game,new Vector2(0,3000),Map4.class).setIconColor(Color.GREEN));
        portals.add(new Portal(game,new Vector2(10800,mapSize / 2 ),Map5.class));
        for(Portal portal:portals){
            portal.currentScreen = this.getClass();
        }
    }

    @Override
    public void findShipPosition(Class<?> previousScreen) {
        shipPosition = new Vector2(11200,0);
        for(Portal portal:portals){
            if(portal.nextScreen == previousScreen)
                shipPosition.set(portal.sprite.getX(),portal.sprite.getY());

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
