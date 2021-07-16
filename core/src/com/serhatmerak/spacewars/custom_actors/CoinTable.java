package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 27.06.2018.
 */

public class CoinTable extends Actor {
    private Image background;
    private Label label;

    public CoinTable(){
        background = new Image(Assets.getInstance().assetManager.get(Assets.menu6, Texture.class));
        background.setSize(300,300 * (background.getHeight() / background.getWidth()));
        setSize(background.getWidth(),background.getHeight());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        label = new Label(User.user.coin+" C",labelStyle);
        label.setWidth(getWidth());
        label.setColor(Color.YELLOW);
        label.setHeight(getHeight());
        label.setAlignment(Align.center);
    }

    public void update(){
        label.setText(User.user.coin + " C");
    }

    public void inadequateCoinAnim(){
        label.addAction(Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                label.setColor(Color.RED);
            }
        }),Actions.delay(1),Actions.run(new Runnable() {
            @Override
            public void run() {
                label.setColor(Color.YELLOW);
            }
        })));
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        label.setPosition(x,y);
        background.setPosition(x,y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(background.getStage() == null){
            getStage().addActor(background);
            getStage().addActor(label);
        }
    }

    @Override
    public boolean remove() {
        background.remove();
        label.remove();

        return super.remove();
    }
}

