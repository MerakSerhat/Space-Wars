package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;

/**
 * Created by Serhat Merak on 30.06.2018.
 */

public class Toast extends Actor {
    private Label label;
    private Image background;
    private int duration;

    public Toast(String text,int duartion){
        label = new Label(text,new Label.LabelStyle(Assets.getInstance().assetManager.get(Assets.fntSarani34,BitmapFont.class),
                Color.WHITE));
        label.setWidth(GameInfo.WIDTH / 2);
        label.setWrap(true);
        label.setAlignment(Align.center);
        background = new Image(Assets.getInstance().assetManager.get(Assets.pix, Texture.class));
        background.setSize(label.getWidth() + 60,label.getPrefHeight() + 20);
        background.setColor(0.4f,0.4f,0.4f,0.8f);
        label.setColor(1,1,1,0.8f);

        this.duration = duartion;
        background.setPosition(GameInfo.WIDTH / 2 - background.getWidth() / 2, 120);
        label.setPosition(GameInfo.WIDTH / 2 - label.getWidth() / 2,130);
    }


    public Toast show(){
        addAction(Actions.sequence(Actions.delay(duration),Actions.run(new Runnable() {
            @Override
            public void run() {
                remove();
            }
        })));
        return this;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(getStage() != null && background.getStage() == null){
            getStage().addActor(background);
            getStage().addActor(label);
        }
    }

    @Override
    public void addAction(Action action) {
        super.addAction(action);
        label.addAction(action);
        background.addAction(action);
    }

    @Override
    public boolean remove() {
        background.remove();
        label.remove();

        return super.remove();
    }
}
