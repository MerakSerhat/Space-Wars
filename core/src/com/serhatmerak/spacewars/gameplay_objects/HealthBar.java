package com.serhatmerak.spacewars.gameplay_objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 15.04.2018.
 */

public class HealthBar {
    private Sprite background;
    private Sprite healthBar;
    private float maxHealth;

    public Vector2 size;

    public HealthBar(float maxHealth){
        this.maxHealth = maxHealth;
        Texture pix = Assets.getInstance().assetManager.get(Assets.pix);

        size = new Vector2(150,5);


        background = new Sprite(pix);
        background.setBounds(0,0,size.x,size.y);
        background.setColor(Color.GRAY);

        healthBar = new Sprite(pix);
        healthBar.setBounds(0,0,size.x,size.y);
        healthBar.setColor(Color.GREEN);
    }

    public void update(float currentHealth, Vector2 pos){
        float currentBarSize = (size.x * currentHealth ) / maxHealth;
        healthBar.setBounds(pos.x,pos.y,currentBarSize,size.y);
        background.setPosition(pos.x,pos.y);
    }

    public void draw(Batch batch){
        background.draw(batch);
        healthBar.draw(batch);
    }


}
