package com.serhatmerak.spacewars.gameplay_objects;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.ships.Spaceship;
import com.serhatmerak.spacewars.stations.Portal;
import com.serhatmerak.spacewars.stations.Station;


/**
 * Created by Serhat Merak on 17.03.2018.
 */

public class MiniMap {

    public float x = 60;
    public float y = 730;
    public int size = 300;
    public int minimapSize;

    private Map map;
    private Sprite minimapbg;
    private Spaceship spaceship;
    private int bgSize;
    private Sprite pix;
    private Sprite background;
    private AssetManager assetManager = Assets.getInstance().assetManager;

    public float mapBorderX,mapBorderY;
    private Array<Vector2> portalPositions;
    public Vector3 otherPlayer;



    public MiniMap(Spaceship spaceship, Map map){
        bgSize = map.bgSize;
        this.spaceship = spaceship;
        this.map = map;
        minimapSize = size - (size / 4);

        minimapbg = new Sprite(assetManager.get(Assets.minimapbg,Texture.class));
        minimapbg.setColor(1,1,1,0.5f);
        minimapbg.setBounds(x,y,size,size);

        background = new Sprite(assetManager.get(Assets.bg,Texture.class));
        background.setColor(1,1,1,0.5f);
        background.setBounds(x + (size - minimapSize) / 2 , y + (size - minimapSize ) / 2,minimapSize,minimapSize);

        portalPositions = new Array<Vector2>();

        for(Station station:map.getStations()) {
            float bgX = (station.sprite.getX() - (GameInfo.WIDTH / 2 - station.sprite.getWidth() / 2)) / 1.2f;
            float bgY = (station.sprite.getY() - (GameInfo.HEIGHT / 2 - station.sprite.getHeight() / 2)) / 1.2f;


            float portalMapX = (minimapSize * (station.sprite.getX() - bgX + station.sprite.getWidth() / 2) ) / bgSize;
            float portalMapY = (minimapSize * (station.sprite.getY() - bgY + station.sprite.getHeight() / 2) ) / bgSize;

            portalPositions.add(new Vector2(x + ((size - minimapSize) / 2) + portalMapX - station.icon.getWidth() / 2,
                    y + ((size - minimapSize) / 2) + portalMapY - station.icon.getHeight() / 2));


        }


        pix = new Sprite(assetManager.get(Assets.pix,Texture.class));
        pix.setColor(Color.GREEN);
        pix.setAlpha(0.8f);
        pix.setBounds(0,0,10,10);
        pix.setOrigin(5f,5f);

    }

    public void draw(SpriteBatch batch){

        minimapbg.draw(batch);
        batch.setColor(1,1,1,0.5f);
        map.drawMinimapBackground(batch,225,x + 37.5f,y + 37.5f);
        batch.setColor(Color.WHITE);

        float shipMapX = (minimapSize * (spaceship.getX() - map.bgX + spaceship.getWidth() / 2) ) / bgSize;
        mapBorderX = shipMapX;
        if(shipMapX < 0 ) shipMapX = 0;
        if(shipMapX > minimapSize - 10) shipMapX = minimapSize - 10;
        float shipMapY = (minimapSize * (spaceship.getY() - map.bgY + spaceship.getHeight() / 2) ) / bgSize;
        mapBorderY = shipMapY;
        if(shipMapY < 0 ) shipMapY = 0;
        if(shipMapY > minimapSize - 10) shipMapY = minimapSize - 10;

        for (int i=0;i < map.getStations().size;i++){
            Color color = batch.getColor();
            batch.setColor(map.getStations().get(i).color);
            batch.draw(map.getStations().get(i).icon,portalPositions.get(i).x,portalPositions.get(i).y);
            batch.setColor(color);
        }

        for(int i=0;i< map.enemyController.enemyShips.size;i++){
            EnemyShip enemyShip = map.enemyController.enemyShips.get(i);
            Sprite redPix = new Sprite(assetManager.get(Assets.pix,Texture.class));
            redPix.setColor(Color.RED);
            redPix.setBounds(0,0,6,6);
            float bgXforEnemy = (enemyShip.getX() - (GameInfo.WIDTH / 2 - enemyShip.getWidth() / 2)) / 1.2f;
            float bgYforEnemy = (enemyShip.getY() - (GameInfo.HEIGHT / 2 - enemyShip.getHeight() / 2)) / 1.2f;
            float enemyMapX = (minimapSize * (enemyShip.getX() - bgXforEnemy + enemyShip.getWidth() / 2) ) / bgSize;
            float enemyMapY = (minimapSize * (enemyShip.getY() - bgYforEnemy + enemyShip.getHeight() / 2) ) / bgSize;
            redPix.setPosition(x + ((size - minimapSize) / 2) + enemyMapX - 3,
                    y + ((size - minimapSize) / 2) + enemyMapY - 3);
            redPix.draw(batch);
        }

        if(otherPlayer != null){


            Sprite redPix = new Sprite(assetManager.get(Assets.pix,Texture.class));
            redPix.setColor(Color.RED);
            redPix.setBounds(0,0,6,6);
            float bgXforEnemy = ((otherPlayer.x * GameInfo.PPM) - (GameInfo.WIDTH / 2 - spaceship.getWidth() / 2)) / 1.2f;
            float bgYforEnemy = ((otherPlayer.y * GameInfo.PPM) - (GameInfo.HEIGHT / 2 - spaceship.getHeight() / 2)) / 1.2f;
            float enemyMapX = (minimapSize * ((otherPlayer.x * GameInfo.PPM)- bgXforEnemy + spaceship.getWidth() / 2) ) / bgSize;
            float enemyMapY = (minimapSize * ((otherPlayer.y * GameInfo.PPM) - bgYforEnemy + spaceship.getHeight() / 2) ) / bgSize;
            redPix.setPosition(x + ((size - minimapSize) / 2) + enemyMapX - 3,
                    y + ((size - minimapSize) / 2) + enemyMapY - 3);
            redPix.setRotation(otherPlayer.z);
            redPix.draw(batch);
        }

        pix.setPosition(x + ((size - minimapSize) / 2) + shipMapX - 5, y + ((size - minimapSize) / 2) + shipMapY - 5);
        pix.draw(batch);
        pix.setRotation(spaceship.getRotation());


    }
}
