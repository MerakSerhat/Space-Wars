package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.game_objects.GameObject;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 15.06.2018.
 */

public class HangarBoxWindow extends Image {
    private Sprite bg;
    private Box box;
    public Box activeBox;

    private Label title,mainAttribute,explanation,lblSell;
    public TextButton btnSell,btnClose,btnUnequip;
    private Vector2 boxPosition;

    public int price;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public boolean equippedBox = false;
    private String mainAttributeText = "";


    public HangarBoxWindow(){

        box = new Box();
        box.enabled = false;
        box.showTxt = false;

        setSize(650,700);
        bg = new Sprite(Assets.getInstance().assetManager.get(Assets.menu1, Texture.class));
        bg.setSize(650,700);
        setDrawable(new SpriteDrawable(bg));
        boxPosition = new Vector2();
        
        createTitle();
        createMainAttribute();
        createExplanation();
        createButtons();
        createPriceLabel();
    }

    private void createPriceLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        lblSell = new Label("",labelStyle);
        lblSell.setWidth(btnSell.getWidth());
        lblSell.setAlignment(Align.center);
    }

    public void setItem(Box box){
        this.activeBox = box;
        this.box.setItem(box.item,box.itemType);

        if(box.itemType == Box.ItemType.GEM)
            explanation.setText(((GameObject) box.item).info);
        else if(box.itemType == Box.ItemType.WEAPON)
            explanation.setText("[YELLOW]LEVEL " + ((Weapon) box.item).level + "[]\n" +((Weapon) box.item).info);
        if(box.itemType == Box.ItemType.GEM){
            mainAttributeText = "";
            if(((Gem) box.item).damage != 0)
                mainAttributeText += "[RED]+" + ((Gem) box.item).damage +"\n"+texts.get("damage")+"[]\n";
            if (((Gem) box.item).attackSpeed != 0)
                mainAttributeText += "[YELLOW]+" + ((Gem) box.item).attackSpeed +"%\n"+texts.get("attackSpeed")+"[]\n";
            if (((Gem) box.item).speed != 0)
                mainAttributeText += "[#0099ff]+" + ((Gem) box.item).speed +"%\n"+texts.get("speed")+"[]\n";
            if (((Gem) box.item).health != 0)
                mainAttributeText += "[GREEN]+" + ((Gem) box.item).health +"\n"+texts.get("health")+"[]\n";

            mainAttributeText = mainAttributeText.substring(0,mainAttributeText.length() - 2);
            mainAttribute.setText(mainAttributeText);
            Label.LabelStyle labelStyle = mainAttribute.getStyle();
            labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
            mainAttribute.setStyle(labelStyle);
            for (int i = 0; i < 2; i++) {

                if (i == 0){
                    if (mainAttribute.getPrefHeight() > box.getHeight()){
                        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
                        mainAttribute.setStyle(labelStyle);
                    }
                }
                else {
                    if (mainAttribute.getPrefHeight() > box.getHeight()){
                        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
                        mainAttribute.setStyle(labelStyle);
                    }
                }
            }
        }else {
            Label.LabelStyle labelStyle = mainAttribute.getStyle();
            labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
            mainAttribute.setStyle(labelStyle);

            mainAttribute.setText("[RED]+" + ((Weapon) box.item).damage +"\n"+texts.get("damage")+"[]");
        }
        title.setText(((GameObject) box.item).name);


        if(activeBox.itemType == Box.ItemType.GEM){
            Gem gem = (Gem) activeBox.item;
            int level = Integer.valueOf(gem.gemStyle.toString().substring(gem.gemStyle.toString().length() - 1));
            price = 250;
            for (int i = 0; i < level; i++) {
                price *= 2;
            }

        }else {
            Weapon weapon = (Weapon) activeBox.item;
            price = 250;
            for (int i = 0; i < weapon.level; i++) {
                price *= 2;
            }
            price += weapon.damage * 100;
        }
        lblSell.setText(price + " C");

    }

    private void createExplanation() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        explanation = new Label("",labelStyle);
        explanation.setAlignment(Align.center);
        explanation.setWidth(500);
        explanation.setWrap(true);
    }
    private void createMainAttribute() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        mainAttribute = new Label("",labelStyle);
        mainAttribute.setAlignment(Align.center);
        mainAttribute.setWidth(350);
        mainAttribute.setWrap(true);
    }
    private void createTitle() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        title = new Label("",labelStyle);
        title.setAlignment(Align.center);
        title.setWidth(650);
    }
    private void createButtons() {
        TextButton.TextButtonStyle sellStyle = new TextButton.TextButtonStyle();
        sellStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        sellStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnNorm,Texture.class)));
        sellStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnHover,Texture.class)));
        sellStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnClicked,Texture.class)));

        btnSell = new TextButton(texts.get("sell"),sellStyle);
        btnSell.setSize(270,270 * (btnSell.getHeight() / btnSell.getWidth()));

        btnUnequip = new TextButton("unequip",sellStyle);
        btnUnequip.setSize(270,270 * (btnUnequip.getHeight() / btnUnequip.getWidth()));

        TextButton.TextButtonStyle closeStyle = new TextButton.TextButtonStyle();
        closeStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        closeStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnNorm,Texture.class)));
        closeStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnHover,Texture.class)));
        closeStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnClicked,Texture.class)));

        btnClose = new TextButton(texts.get("close"),closeStyle);
        btnClose.setSize(270,270 * (btnClose.getHeight() / btnClose.getWidth()));
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bg.setPosition(x,y);
        title.setPosition(x,y + 670);
        box.setPosition(x + 40,title.getY() - 320);
        mainAttribute.setPosition(box.getX() + 250,box.getY() + 120);
        explanation.setPosition(x + 75,y + 270);
        btnSell.setPosition(x + 40,y + 50);
        lblSell.setPosition(btnSell.getX(),btnSell.getY() + btnSell.getHeight() + 15);
        btnUnequip.setPosition(x + 40,y + 50);
        btnClose.setPosition(x + 650 - 40 - 270,y + 50);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        bg.draw(batch);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(btnSell.getStage() == null) {
            if(equippedBox)
                getStage().addActor(btnUnequip);
            else {
                getStage().addActor(btnSell);
                getStage().addActor(lblSell);
            }
            getStage().addActor(btnClose);
            getStage().addActor(title);
            getStage().addActor(explanation);
            getStage().addActor(box);
            getStage().addActor(mainAttribute);
        }
    }

    @Override
    public boolean remove() {
        boolean remove = super.remove();
        btnSell.remove();
        btnClose.remove();
        box.remove();
        mainAttribute.remove();
        title.remove();
        explanation.remove();
        btnUnequip.remove();
        lblSell.remove();

        return remove;
    }
}
