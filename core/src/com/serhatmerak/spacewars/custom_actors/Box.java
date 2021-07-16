package com.serhatmerak.spacewars.custom_actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.serhatmerak.spacewars.game_objects.GameObject;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.BoxListener;


/**
 * Created by Serhat Merak on 23.04.2018.
 */

public class Box extends Group {

    private Sprite box;
    public Sprite border;

    public enum State{NORMAL,CLICK}
    public State state = State.NORMAL;

    public enum ItemType{NULL,GEM,WEAPON}
    public ItemType itemType = ItemType.NULL;
    public Object item;
    private Image background,itemImg,borderImg;
    private Label label;
    private String txt = "";

    public boolean enabled = true;
    public boolean showTxt = true;
    private BoxListener boxListener;

    public float touchDownTime = 0;
    public boolean pressing = false;

    public Box(){
        box = new Sprite(Assets.getInstance().assetManager.get(Assets.box, Texture.class));
        box.setSize(240,240);
        background = new Image(new SpriteDrawable(box));

        border = new Sprite(Assets.getInstance().assetManager.get(Assets.boxBorder, Texture.class));
        border.setSize(240,240);
        borderImg = new Image(new SpriteDrawable(border));
        borderImg.setPosition(0,0);


        setSize(240,240);
        stateChanged();


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        label = new Label(txt,labelStyle);



        addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(boxListener != null) {
                    if(touchDownTime < 0.5f)
                        boxListener.boxClicked(Box.this);
                    touchDownTime = 0;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDownTime = 0;
                pressing = true;
                return super.touchDown(event,x,y,pointer,button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressing = false;
                super.touchUp(event, x, y, pointer, button);

            }
        });

        itemImg = new Image();
        itemImg.setPosition(20 ,20);

        addActor(background);
        addActor(borderImg);
        addActor(itemImg);
        addActor(label);

    }

    public void stateChanged() {
        if (state == State.NORMAL) {
            border.setColor(Color.valueOf("156c93"));
        } else {
            border.setColor(Color.YELLOW);
        }
    }

    public void setItem(Object item,ItemType itemType) {
        this.item = item;
        this.itemType = itemType;
        itemImg.setDrawable(new SpriteDrawable(new Sprite(((GameObject) item).img)));
        itemImg.setSize(200,200);


        txt = "";
        if(itemType == ItemType.WEAPON){
            txt = "[#800000]" + ((Weapon) item).damage+"[]";
        }else {
            Gem gem = (Gem) item;
            if (gem.speed != 0)
                txt += "[BLUE]" + gem.speed+"%[]\n";
            if (gem.attackSpeed != 0)
                txt += "[#ff9900]" + gem.attackSpeed +"%[]\n";
            if (gem.damage != 0)
                txt += "[#800000]" + gem.damage + "[]\n";
            if(gem.health != 0) {
                txt += "[#006600]" + gem.health + "[]\n";
            }

            txt = txt.substring(0,txt.length() - 2);
        }
        label.setText(txt);
        label.setHeight(label.getPrefHeight());
        label.setWidth(label.getPrefWidth());
        label.setPosition(box.getWidth()  - label.getPrefWidth() - 20,30);
    }

    public void setItemNull(){
        this.itemType = ItemType.NULL;
        this.item = null;
        itemImg.setDrawable(null);
        txt = "";
        label.setText("");
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x,y);
        box.setPosition(x,y);
        border.setPosition(x,y);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(pressing)
            touchDownTime += Gdx.graphics.getDeltaTime();
        if(touchDownTime > 0.5f){
            if(boxListener != null)
                boxListener.boxLongPress(Box.this);
        }
        super.draw(batch, parentAlpha);
    }

    //    @Override
//    public void draw(Batch batch, float parentAlpha) {
//
//        validate();
//
//        float x = getX();
//        float y = getY();
//
//        Color color = batch.getColor();
//        batch.setColor(Color.WHITE);
//        getDrawable().draw(batch,x, y,
//                getImageWidth() * getScaleX(),getImageHeight() * getScaleY());
//        borderDrawable.draw(batch, x, y,
//                getImageWidth() * getScaleX(), getImageHeight() * getScaleY());
//        if(itemType != ItemType.NULL) {
//            itemDrawable.draw(batch, x + 20, y + 20,
//                    200 * getScaleX(), 200 * getScaleY());
//
//        }
//
//        if(showTxt)
//            font.draw(batch,txt,x + 170,y + 40);
//
//        batch.setColor(color);
//    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(!showTxt && getChildren().contains(label,true)){
            label.remove();
        }
    }

    public void setBoxListener(BoxListener boxListener) {
        this.boxListener = boxListener;
    }
}