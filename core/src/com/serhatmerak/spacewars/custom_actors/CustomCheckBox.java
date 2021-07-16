package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 2.07.2018.
 */

public class CustomCheckBox extends Actor {
    private Image background;
    private Image checkedImg;
    public boolean checked = false;
    private Label label;


    public CustomCheckBox(){
        background = new Image(Assets.getInstance().assetManager.get(Assets.checkbox2,Texture.class));
        checkedImg = new Image(Assets.getInstance().assetManager.get(Assets.checkbox1,Texture.class));

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(checked){
                    checked = false;
                }else
                    checked = true;
            }
        });
    }

    public void addLabel(Label label){
        this.label = label;
        label.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(checked){
                    checked = false;
                }else
                    checked = true;
            }
        });
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        checkedImg.setSize(width * (checkedImg.getWidth() / background.getWidth()),
                height * (checkedImg.getHeight() / background.getHeight()));
        background.setSize(width,height);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        background.setPosition(x,y);
        checkedImg.setPosition(x + (background.getWidth() - checkedImg.getWidth()) / 2,
                y + (background.getHeight() - checkedImg.getHeight()) / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (background.getStage() == null){
            getStage().addActor(background);
        }

        if(checked){
            if(checkedImg.getStage() == null)
                getStage().addActor(checkedImg);
        }else {
            if (checkedImg.getStage() != null)
                checkedImg.remove();
        }

        if(!checked) {
            if (getZIndex() < background.getZIndex()) {
                setZIndex(background.getZIndex());
            }
        }else {
            if (getZIndex() < checkedImg.getZIndex()) {
                setZIndex(checkedImg.getZIndex());
            }
        }
    }

    @Override
    public boolean remove() {
        if (checked)
            checkedImg.remove();

        background.remove();

        return super.remove();
    }
}
