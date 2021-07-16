package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 24.07.2018.
 */

public class ShopItemInfo extends Actor {
    private Image background,darkBg;
    private TextButton btnClose;
    private Label title;
    private Label info,infoStats,wepInfo;
    private SpaceShipStyle spaceShipStyle;
    private PetStyle petStyle;
    private Gem.GemStyle gemStyle;
    private Weapon.WeaponStyle weaponStyle;

    private enum State{GEM,WEAPON,PET,SHIP}
    private State state;
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public ShopItemInfo(SpaceShipStyle shipStyle){
        this.spaceShipStyle = shipStyle;
        state = State.SHIP;
        init();
    }

    public ShopItemInfo(PetStyle petStyle){
        this.petStyle = petStyle;
        state = State.PET;
        init();
    }

    public ShopItemInfo(Gem.GemStyle gemStyle){
        this.gemStyle = gemStyle;
        state = State.GEM;
        init();
    }

    public ShopItemInfo(Weapon.WeaponStyle weaponStyle){
        this.weaponStyle = weaponStyle;
        state = State.WEAPON;
        init();
    }
    private void init() {
        background = new Image(Assets.getInstance().assetManager.get(Assets.menu1, Texture.class));
        background.setSize(500,450);
        background.setPosition(GameInfo.WIDTH / 2 - background.getWidth() / 2,
                GameInfo.HEIGHT / 2 - background.getHeight() / 2);

        darkBg = new Image(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        darkBg.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        darkBg.setPosition(0,0);
        darkBg.setColor(0,0,0,0.7f);

        Label.LabelStyle titleStyle  = new Label.LabelStyle();
        titleStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        Label.LabelStyle infoStyle  = new Label.LabelStyle();
        infoStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);


        if(state == State.SHIP){
            title = new Label(spaceShipStyle.name(),titleStyle);
            info = new Label(texts.get("damage") + "\n" + texts.get("health") + "\n" +
                    texts.get("speed") + "\n" + texts.get("attackSpeed"),infoStyle);
            infoStats = new Label("[RED]" + spaceShipStyle.damage +"\n" +
                    "[GREEN]" + spaceShipStyle.health + "\n" +
                    "[#009bdf]" + spaceShipStyle.speed + "\n" +
                    "[YELLOW]" + spaceShipStyle.attackSpeed,infoStyle);
        }else if (state == State.PET){
            title = new Label(petStyle.name(),titleStyle);
            info = new Label(texts.get("damage") + "\n" + texts.get("attackSpeed") + "\n" +
                    texts.get("gemNest") + "\n" + texts.get("weaponNest") + "\n" + "healing per sec",infoStyle);
            infoStats = new Label("[RED]" + petStyle.damage +"\n" +
                    "[YELLOW]" + petStyle.attackSpeed + "\n" +
                    "[#00ccff]" + petStyle.gemNest + "\n" +
                    "[#00ccff]" + petStyle.weaponNest + "\n" +
                    "[GREEN]" + petStyle.healPerSecond,infoStyle);
        }else if (state == State.GEM){
            title = new Label(gemStyle.name,titleStyle);
            info = new Label("",infoStyle);
            infoStats = new Label("",infoStyle);
            if(gemStyle.damage != 0) {
                info.setText(info.getText() + texts.get("damage") + "\n");
                infoStats.setText(infoStats.getText() + "[RED]" + gemStyle.damage +"\n");
            }
            if(gemStyle.health != 0){
                info.setText(info.getText() + texts.get("health") + "\n");
                infoStats.setText(infoStats.getText() + "[GREEN]" + gemStyle.health +"\n");
            }
            if(gemStyle.speed != 0){
                info.setText(info.getText() + texts.get("speed") + "\n");
                infoStats.setText(infoStats.getText() + "[#009bdf]+" + gemStyle.speed +"%\n");
            }
            if(gemStyle.attackSpeed != 0){
                info.setText(info.getText() + texts.get("attackSpeed") + "\n");
                infoStats.setText(infoStats.getText() + "[YELLOW]+" + gemStyle.attackSpeed +"%\n");
            }

        }else if (state == State.WEAPON){
            title = new Label(weaponStyle.name,titleStyle);
            info = new Label(texts.get("damage"),infoStyle);
            infoStats = new Label("[RED]" + (weaponStyle.damage + 5),infoStyle);
            wepInfo = new Label(weaponStyle.info,infoStyle);
            wepInfo.setWidth(450);
            wepInfo.setWrap(true);
            wepInfo.setAlignment(Align.center);
        }

        title.setPosition(GameInfo.WIDTH / 2 - title.getWidth() / 2,
                background.getY() + background.getHeight() - 10 - title.getHeight());


        info.setPosition(background.getX() + 40,GameInfo.HEIGHT / 2 - info.getHeight() / 2 + 30 );
        infoStats.setPosition(background.getX() + background.getWidth() - 40 - infoStats.getPrefWidth(),
                GameInfo.HEIGHT / 2 - info.getHeight() / 2 + 30);

        if(infoStats.getX() - (info.getX() + info.getPrefWidth()) > 80){
            float dsc = infoStats.getX() - (info.getX() + info.getPrefWidth()) - 80;
            infoStats.setX(infoStats.getX() - dsc / 2);
            info.setX(info.getX() + dsc / 2);
        }

        if(wepInfo != null){

            wepInfo.setPosition(GameInfo.WIDTH / 2 - wepInfo.getWidth() / 2,
                    GameInfo.HEIGHT / 2 - (info.getHeight() + 20 + wepInfo.getPrefHeight()) / 2 + 30);
            info.setY(wepInfo.getY() + wepInfo.getHeight() + 20);
            infoStats.setY(info.getY());
        }

        TextButton.TextButtonStyle closeStyle = new TextButton.TextButtonStyle();
        closeStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        closeStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnNorm,Texture.class)));
        closeStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnHover,Texture.class)));
        closeStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnClicked,Texture.class)));

        btnClose = new TextButton(texts.get("close"),closeStyle);
        btnClose.setSize(270,270 * (btnClose.getHeight() / btnClose.getWidth()));
        btnClose.setPosition(GameInfo.WIDTH / 2 - 135,background.getY() + 40);

        btnClose.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (background.getStage() == null){
            getStage().addActor(darkBg);
            getStage().addActor(background);
            getStage().addActor(title);
            getStage().addActor(info);
            getStage().addActor(infoStats);
            getStage().addActor(btnClose);
            if(wepInfo != null)
                getStage().addActor(wepInfo);
        }
    }

    @Override
    public boolean remove() {
        darkBg.remove();
        background.remove();
        title.remove();
        infoStats.remove();
        info.remove();
        btnClose.remove();
        if(wepInfo != null)
            wepInfo.remove();

        return super.remove();
    }
}

