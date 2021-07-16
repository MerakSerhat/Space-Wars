package com.serhatmerak.spacewars.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyShip;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;
import com.serhatmerak.spacewars.stations.MissionStation;
import com.serhatmerak.spacewars.stations.Portal;
import com.serhatmerak.spacewars.stations.Station;

/**
 * Created by Serhat Merak on 31.07.2018.
 */

public class PvPMap extends Map {
    public static String name;

    public PvPMap(GameMain gameMain){
        super(gameMain,null);
        mapName = Base.class;
        name = "PVP";
        bgSize = 1500;
        mapSize = 7500;
        setBackground();
        pix.setColor(Color.BLACK);
        pix.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
    }

    private void setBackground() {
        layers = new Array<Layers>();
        layers.add(new Layers(coldNeblua,new Vector2(0,0)));
        layers.add(new Layers(blueDust,new Vector2(400,300)));
        layers.add(new Layers(sun,new Vector2(800,800)));
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

    @Override
    public Array<Station> getStations() {
        return new Array<Station>();
    }
}
