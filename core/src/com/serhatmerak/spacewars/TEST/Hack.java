package com.serhatmerak.spacewars.TEST;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.TEST.TestScreen;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.maps.Base;
import com.serhatmerak.spacewars.maps.Map1;
import com.serhatmerak.spacewars.maps.Map2;
import com.serhatmerak.spacewars.maps.Map6;
import com.serhatmerak.spacewars.maps.Map7;
import com.serhatmerak.spacewars.maps.Map8;
import com.serhatmerak.spacewars.screens.GamePlay;

/**
 * Created by Serhat Merak on 7.04.2018.
 */

public class Hack {

    private GameMain game;
    private Rectangle btnChangeScreen;
    private ShapeRenderer shapeRenderer;

    public Hack(GameMain game){
        this.game = game;
        btnChangeScreen = new Rectangle();
        btnChangeScreen.set(GameInfo.WIDTH - 100,GameInfo.HEIGHT - 100,50,50);
        shapeRenderer = new ShapeRenderer();
    }

    public void drawAndUpdate(OrthographicCamera camera){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(btnChangeScreen.x,btnChangeScreen.y,btnChangeScreen.width,btnChangeScreen.height);
        shapeRenderer.end();
        if(isClicked(btnChangeScreen,camera)){
            if(game.getScreen().getClass() == TestScreen.class)
                game.setScreen(new GamePlay(game,new Base(game,null)));
            else
                game.setScreen(new TestScreen(game));
        }
    }

    private boolean isClicked(Rectangle rectangle, OrthographicCamera camera) {
        Vector3 vector3 = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
        if(rectangle.contains(vector3.x,vector3.y) && Gdx.input.justTouched())
            return true;

        return false;
    }
}
