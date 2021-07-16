package com.serhatmerak.spacewars.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Serhat Merak on 18.03.2018.
 */

public class GameObject {
    public Texture img;
    public String info;
    public String name;
    public String color;

    public void draw(SpriteBatch batch, Vector2 pos){
        batch.draw(img,pos.x,pos.y);
    }

    public void showInfo(){

    }
}
