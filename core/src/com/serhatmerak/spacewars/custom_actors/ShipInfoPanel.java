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
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 26.06.2018.
 */

public class ShipInfoPanel extends Actor {
    private Image background,darkBg;
    private TextButton btnClose;
    private Label title;
    private Label info,infoStats;
    private SpaceShipStyle spaceShipStyle;
    private PetStyle petStyle;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public ShipInfoPanel(SpaceShipStyle shipStyle){
        this.spaceShipStyle = shipStyle;
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
        titleStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        Label.LabelStyle infoStyle  = new Label.LabelStyle();
        infoStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);


        if(spaceShipStyle != null){
            title = new Label(spaceShipStyle.name(),titleStyle);
            info = new Label(texts.get("damage") + "\n" + texts.get("health") + "\n" +
                    texts.get("speed") + "\n" + texts.get("attackSpeed"),infoStyle);
            infoStats = new Label("[RED]" + spaceShipStyle.damage +"\n" +
                    "[GREEN]" + spaceShipStyle.health + "\n" +
                    "[#009bdf]" + spaceShipStyle.speed + "\n" +
                    "[YELLOW]" + spaceShipStyle.attackSpeed,infoStyle);
        }else {
            title = new Label(petStyle.name(),titleStyle);
            info = new Label(texts.get("damage") + "\n" + texts.get("attackSpeed") + "\n" +
                    texts.get("gemNest") + "\n" + texts.get("weaponNest") + "\n" + "healing per sec",infoStyle);
            infoStats = new Label("[RED]" + petStyle.damage +"\n" +
                    "[YELLOW]" + petStyle.attackSpeed + "\n" +
                    "[#00ccff]" + petStyle.gemNest + "\n" +
                    "[#00ccff]" + petStyle.weaponNest + "\n" +
                    "[GREEN]" + petStyle.healPerSecond,infoStyle);
        }

        title.setPosition(GameInfo.WIDTH / 2 - title.getWidth() / 2,
                background.getY() + background.getHeight() - 5 - title.getHeight());

        info.setPosition(background.getX() + 40,GameInfo.HEIGHT / 2 - info.getHeight() / 2 + 30 );
        infoStats.setPosition(background.getX() + background.getWidth() - 40 - infoStats.getWidth(),
                GameInfo.HEIGHT / 2 - info.getHeight() / 2 + 30);

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

    public ShipInfoPanel(PetStyle petStyle){
        this.petStyle = petStyle;
        init();
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

        return super.remove();
    }
}
