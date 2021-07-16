package com.serhatmerak.spacewars.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.gameplay_objects.Crystal;
import com.serhatmerak.spacewars.stations.MissionStation;
import com.serhatmerak.spacewars.stations.Portal;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.ships.EnemyController;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.stations.Station;

/**
 * Created by Serhat Merak on 21.03.2018.
 */

public class Map {
    public GameMain game;

    public int mapSize;
    public int bgSize;
    public float bgX = 0,bgY = 0;
    public World world;
    public EnemyController enemyController;
    public Vector2 shipPosition;
    public Array<Layers> layers;
    public Texture soloStar;
    public Array<SoloStar> stars;
    public Class<? extends Object> mapName;



    public Texture star,planet1,planet2,planet3,earth,sun;
    public Texture coldNeblua,hotNebula,pinkDust,blueDust,yellowDust;
    public Texture rock,grayAsteroid,redAsteroid;
    public Sprite pix;

    public MissionStation missionStation;
    public Array<Portal> portals;
    public Array<Crystal> crystals;
    public Array<Station> stations;

    public Map(GameMain game,Class<? extends Object> previousScreen){
        this.game = game;
        createLayers();
        world = new World(new Vector2(0,0),true);
        enemyController = new EnemyController(world);
        crystals = new Array<Crystal>();
        createStars();
        soloStar = new Texture("star.png");

    }

    private void createStars() {
        stars = new Array<SoloStar>();
        for(int i=0;i<5000;i++){
            SoloStar soloStar = new SoloStar();
            soloStar.pos = new Vector2(MathUtils.random(0,12500),MathUtils.random(0,12500));
            soloStar.speed = MathUtils.random(0.5f,1f);
            stars.add(soloStar);
        }
    }

    public void findShipPosition(Class<? extends Object> previousScreen) {
        shipPosition = new Vector2(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2);
        for(Portal portal:portals){
            if(portal.nextScreen == previousScreen)
                shipPosition.set(portal.sprite.getX(),portal.sprite.getY());

        }

    }

    private void createLayers() {
        star = Assets.getInstance().assetManager.get(Assets.lyrStar);
        planet1 = Assets.getInstance().assetManager.get(Assets.lyrPlanet1);
        planet2 = Assets.getInstance().assetManager.get(Assets.lyrPlanet2);
        planet3 = Assets.getInstance().assetManager.get(Assets.lyrPlanet3);
        rock = Assets.getInstance().assetManager.get(Assets.lyrRock);
        earth = Assets.getInstance().assetManager.get(Assets.lyrEarth);
        sun = Assets.getInstance().assetManager.get(Assets.lyrSun);
        coldNeblua = Assets.getInstance().assetManager.get(Assets.lyrColdNebula);
        hotNebula = Assets.getInstance().assetManager.get(Assets.lyrHotNebula);
        pinkDust = Assets.getInstance().assetManager.get(Assets.lyrPinkDust);
        yellowDust = Assets.getInstance().assetManager.get(Assets.lyrYellowDust);
        blueDust = Assets.getInstance().assetManager.get(Assets.lyrBlueDust);
        redAsteroid = Assets.getInstance().assetManager.get(Assets.lyrRedAsteroid);
        grayAsteroid = Assets.getInstance().assetManager.get(Assets.lyrGrayAsteroid);
        pix = new Sprite(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
    }

    public void drawBackground(SpriteBatch batch,float bgX,float bgY){}
    public void drawMinimapBackground(SpriteBatch batch,float size, float x, float y){}
    public void drawEnemies(SpriteBatch batch){};
    public void updateEnemies(){};

    public class Layers{
        public Texture texture;
        public Vector2 pos;
        public Sprite sprite;

        public Layers(Texture texture,Vector2 pos){
            this.texture = texture;
            this.pos = pos;

        }

        public Layers(Sprite sprite){
            this.sprite = sprite;
        }
    }

    public class SoloStar{
        public Vector2 pos;
        public float speed;
    }

    public Array<Station> getStations() {
        if(stations == null){
            stations = new Array<Station>();
            for(Portal portal:portals)
                stations.add(portal);

            if(missionStation != null)
                stations.add(missionStation);
        }
        return stations;
    }
}
