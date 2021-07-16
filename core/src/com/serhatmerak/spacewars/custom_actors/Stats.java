package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.pets.PetData;

/**
 * Created by Serhat Merak on 19.06.2018.
 */

public class Stats extends Actor {

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private Image background;
    private Image line1,line2;
    private Label lblShip,lblShipAttributes,lblShipStats,lblPet,lblPetAttributes,lblPetStats,title;

    public Stats(){
        background = new Image(Assets.getInstance().assetManager.get(Assets.menu1, Texture.class));
        background.setSize(650,630);

        Sprite pix = new Sprite(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        line1 = new Image(new SpriteDrawable(pix));
        line2 = new Image(new SpriteDrawable(pix));

        line1.setSize(557,3);
        line2.setSize(557,3);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        lblShip = new Label(texts.get("ship"),labelStyle);
        lblPet = new Label(texts.get("pet"),labelStyle);
        lblShipAttributes = new Label(
                texts.get("damage") +"\n"+
                texts.get("health") + "\n"+
                texts.get("speed") + "\n" +
                texts.get("attackSpeed"),labelStyle);

        lblPetAttributes = new Label(
                texts.get("damage") + "\n"+
                texts.get("attackSpeed"),labelStyle);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        title = new Label("stats",titleStyle);

        lblShipStats = new Label("[RED]" + User.user.getDamage() + "\n" +
                "[GREEN]" + User.user.getHealth() + "\n" +
                "[#009bdf]" + User.user.getSpeed() + "\n" +
                "[YELLOW]" + (String.valueOf(User.user.getAttackSpeed()).length() < 6? String.valueOf(User.user.getAttackSpeed()) :
                String.valueOf(User.user.getAttackSpeed()).substring(0,5)),labelStyle);
        lblPetStats = new Label("[RED]" + PetData.petData.getDamage() + "\n" +
                "[YELLOW]" + (String.valueOf(PetData.petData.getAttackSpeed()).length() < 6? String.valueOf(PetData.petData.getAttackSpeed()) :
                String.valueOf(PetData.petData.getAttackSpeed()).substring(0,5)),labelStyle);

        setStats();
    }

    public void setStats(){
        lblShipStats.setText("[RED]" + User.user.getDamage() + "\n" +
        "[GREEN]" + User.user.getHealth() + "\n" +
        "[#009bdf]" + User.user.getSpeed() + "\n" +
        "[YELLOW]" + (String.valueOf(User.user.getAttackSpeed()).length() < 6? String.valueOf(User.user.getAttackSpeed()) :
                String.valueOf(User.user.getAttackSpeed()).substring(0,5)));

        lblPetStats.setText("[RED]" + PetData.petData.getDamage() + "\n" +
        "[YELLOW]" + (String.valueOf(PetData.petData.getAttackSpeed()).length() < 6? String.valueOf(PetData.petData.getAttackSpeed()) :
                String.valueOf(PetData.petData.getAttackSpeed()).substring(0,5)) );
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        background.setPosition(x,y);
        lblShip.setPosition(x + 35,y + 460);
        lblShipAttributes.setPosition(x + 74,lblShip.getY() - 23 - lblShipAttributes.getHeight());
        lblPet.setPosition(lblShip.getX() , lblShipAttributes.getY() - 50 - lblPet.getHeight());
        lblPetAttributes.setPosition(x + 74,lblPet.getY() - 23 - lblPetAttributes.getHeight());
        line1.setPosition(lblShip.getX(),lblShip.getY() - 13);
        line2.setPosition(lblShip.getX(),lblPet.getY() - 13);
        title.setPosition(x + background.getWidth() / 2 - title.getWidth() / 2,y + 585);
        lblShipStats.setPosition(lblShipAttributes.getX() + 400,lblShip.getY() - 23 - lblShipAttributes.getHeight());
        lblPetStats.setPosition(lblPetAttributes.getX() + 400,lblPet.getY() - 23 - lblPetAttributes.getHeight());

        System.out.println(lblPetStats.getPrefHeight() + " " + lblPetAttributes.getPrefWidth());
    }

    @Override
    public void act(float delta) {
        if(background.getStage() == null){
            getStage().addActor(background);
            getStage().addActor(title);
            getStage().addActor(lblShip);
            getStage().addActor(lblPet);
            getStage().addActor(lblPetAttributes);
            getStage().addActor(lblShipAttributes);
            getStage().addActor(line1);
            getStage().addActor(line2);
            getStage().addActor(lblPetStats);
            getStage().addActor(lblShipStats);
        }
    }

    @Override
    public boolean remove() {
        background.remove();
        title.remove();
        lblShip.remove();
        lblPet.remove();
        lblShipAttributes.remove();
        lblShipAttributes.remove();
        line1.remove();
        line2.remove();
        lblPetStats.remove();
        lblShipStats.remove();

        return super.remove();
    }
}
