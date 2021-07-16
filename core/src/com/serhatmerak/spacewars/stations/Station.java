package com.serhatmerak.spacewars.stations;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.huds.GamePlayHuds;
import com.serhatmerak.spacewars.screens.GamePlay;

/**
 * Created by Serhat Merak on 27.05.2018.
 */

public class Station {
    public GameMain game;
    public Vector2 position;
    public GamePlay gamePlay;
    public Sprite sprite;
    public Texture icon;
    public Color color = Color.WHITE;
    public Sprite safeBorder1,safeBorder2,safeBorder3,safeBorder4;
    private int rotation = 0;

    public Station(GameMain game, Vector2 position){
        this.game = game;
        this.position = position;
        safeBorder1 = new Sprite(Assets.getInstance().assetManager.get(Assets.safeBorder,Texture.class));
        safeBorder2 = new Sprite(Assets.getInstance().assetManager.get(Assets.safeBorder,Texture.class));
        safeBorder3 = new Sprite(Assets.getInstance().assetManager.get(Assets.safeBorder,Texture.class));
        safeBorder4 = new Sprite(Assets.getInstance().assetManager.get(Assets.safeBorder,Texture.class));
        safeBorder1.setRotation(90);
        safeBorder4.setRotation(180);
        safeBorder3.setRotation(270);
    }


    public void draw(SpriteBatch batch){
        safeBorder1.draw(batch);
        safeBorder2.draw(batch);
        safeBorder3.draw(batch);
        safeBorder4.draw(batch);
    }
    public void update(){
        Vector2 safe1 = rotateAround(new Vector2(position.x - safeBorder1.getWidth() / 2,
                position.y + 750 - safeBorder1.getWidth() / 2),position,safeBorder1.getRotation() - 90);
        safeBorder1.setPosition(safe1.x,safe1.y);
        safeBorder1.rotate(2);

        Vector2 safe2 = rotateAround(new Vector2(position.x - safeBorder2.getWidth() / 2 + 750,
                position.y - safeBorder2.getWidth() / 2),position,safeBorder2.getRotation());
        safeBorder2.setPosition(safe2.x,safe2.y);
        safeBorder2.rotate(2);

        Vector2 safe3 = rotateAround(new Vector2(position.x - safeBorder3.getWidth() / 2,
                position.y - 750 - safeBorder3.getWidth() / 2),position,safeBorder3.getRotation() - 270);
        safeBorder3.setPosition(safe3.x,safe3.y);
        safeBorder3.rotate(2);

        Vector2 safe4 = rotateAround(new Vector2(position.x - safeBorder4.getWidth() / 2 - 750,
                position.y - safeBorder4.getWidth() / 2),position,safeBorder4.getRotation() - 180);
        safeBorder4.setPosition(safe4.x,safe4.y);
        safeBorder4.rotate(2);


    }

    public boolean isNear(Vector2 pos){
        return pos.dst(sprite.getX() + sprite.getWidth() / 2,sprite.getY() + sprite.getHeight() / 2) < 750;
    }

    public void setActive(boolean b) {
        gamePlay.huds.showMissionPanel();
    }

    private Vector2 rotateAround(Vector2 vector,Vector2 origin,float rotation){
        return vector.sub(origin).rotate(rotation).add(origin);
    }
}
