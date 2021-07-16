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
import com.serhatmerak.spacewars.stations.Portal;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 24.04.2018.
 */

public class Map5 extends Map{

    private SpaceShipStyle[] spaceShipStyles = new SpaceShipStyle[]{
            SpaceShipStyle.BlueShip4,SpaceShipStyle.RedShip4,SpaceShipStyle.OrangeShip4,SpaceShipStyle.GreenShip4
    };
    private int[] amounts = new int[]{10,10,10,10};

    private final Color color = new Color(Color.valueOf("10081f"));



    public Map5(GameMain gameMain,Class<? extends Object> previousScreen){
        super(gameMain,previousScreen);
        mapName = Map5.class;
        bgSize = 5000;
        mapSize = 25000;
        setBackground();
        enemyController.createRandomEnemies(spaceShipStyles,amounts,new Vector2(mapSize,mapSize));
        pix.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        pix.setColor(color);

        createPortals();
        findShipPosition(previousScreen);
        createCrystals();

    }

    private void createCrystals() {
        for (int i = 0; i < 2; i++) {
            Crystal crystal = new Crystal(8000, new Vector2(MathUtils.random(0, 25000), MathUtils.random(0, 25000)), world);
            crystal.addCoin(new float[]{500, 1000});
            crystal.addGem(20, Gem.GemStyle.BLUE3);
            crystal.addGem(20, Gem.GemStyle.RED3);
            crystal.addGem(20, Gem.GemStyle.GREEN3);
            crystal.addGem(20, Gem.GemStyle.YELLOW3);
            crystal.addGem(5, Gem.GemStyle.BLUE4);
            crystal.addGem(5, Gem.GemStyle.RED4);
            crystal.addGem(5, Gem.GemStyle.GREEN4);
            crystal.addGem(5, Gem.GemStyle.YELLOW4);
            crystal.addWeapon(5, Weapon.WeaponStyle.Green_1);
            crystal.addWeapon(5, Weapon.WeaponStyle.Yellow_1);
            crystal.addWeapon(5, Weapon.WeaponStyle.Blue_1);
            crystal.addWeapon(5, Weapon.WeaponStyle.Red_1);
            crystal.addWeapon(2, Weapon.WeaponStyle.Green_2);
            crystal.addWeapon(2, Weapon.WeaponStyle.Red_2);
            crystal.addWeapon(2, Weapon.WeaponStyle.Yellow_2);
            crystal.addWeapon(2, Weapon.WeaponStyle.Blue_2);
            crystals.add(crystal);
        }

    }

    private void createPortals() {
        portals = new Array<Portal>();
        portals.add(new Portal(game,new Vector2(18000,18000),Map6.class));
        portals.add(new Portal(game,new Vector2(0,0),Base.class));
        for(Portal portal:portals){
            portal.currentScreen = this.getClass();
        }


    }

    private void setBackground() {
        layers = new Array<Layers>();
        layers.add(new Layers(planet3,new Vector2(120,800)));
        layers.add(new Layers(hotNebula,new Vector2(3700,3500)));
        layers.add(new Layers(redAsteroid,new Vector2(400,300)));
        layers.add(new Layers(redAsteroid,new Vector2(4000,300)));
        layers.add(new Layers(redAsteroid,new Vector2(3600,800)));
        layers.add(new Layers(grayAsteroid,new Vector2(2400,2400)));
        layers.add(new Layers(grayAsteroid,new Vector2(1000,3400)));
        layers.add(new Layers(grayAsteroid,new Vector2(1800,1200)));
        layers.add(new Layers(planet2,new Vector2(3000,3000)));
        layers.add(new Layers(yellowDust,new Vector2(700,4000)));
        layers.add(new Layers(earth,new Vector2(4000,400)));
        layers.add(new Layers(sun,new Vector2(2000,800)));


    }
    public void drawBackground(SpriteBatch batch, float mapX, float mapY){

        pix.draw(batch);
        for(int i = 0; i< (bgSize / star.getWidth()) + 1;i++){
            for(int j = 0; j < (bgSize / star.getHeight() ) + 1;j++) {
                batch.draw(star,(star.getWidth() * i) + mapX,(star.getHeight() * j + mapY));
            }
        }

        for (Layers layer:layers){
            batch.draw(layer.texture,layer.pos.x + mapX,layer.pos.y + mapY);
        }

    }

    @Override
    public void drawEnemies(SpriteBatch batch) {
        for(Crystal crystal:crystals)
            crystal.draw(batch);
        for(EnemyShip enemyShip:enemyController.enemyShips){
            enemyShip.draw(batch);
        }
    }

    @Override
    public void updateEnemies() {
        for(Crystal crystal:crystals)
            crystal.update();
        for(EnemyShip enemyShip:enemyController.enemyShips){
            enemyShip.move();
        }
    }

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

