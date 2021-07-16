package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.custom_actors.Box;
import com.serhatmerak.spacewars.custom_actors.CoinTable;
import com.serhatmerak.spacewars.custom_actors.HangarBoxWindow;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.helpers.*;

/**
 * Created by Serhat Merak on 7.06.2018.
 */

public class MainMenuHuds {
    public Stage stage;
    private TextButton.TextButtonStyle normalButtonStyle;
    public TextButton btnFree,btnStages,btnHangar,btnShop,btnLab,btnPVP;
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private CoinTable coinTable;

    public MainMenuHuds(SpriteBatch batch) {
        stage = new Stage(new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT), batch);
        createTextButtons();
        createCoinTable();
    }

    private void createCoinTable() {
        coinTable = new CoinTable();
        coinTable.setPosition(100,949.4194f);
        stage.addActor(coinTable);
    }


    private void createTextButtons() {
        normalButtonStyle = new TextButton.TextButtonStyle();
        normalButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
        normalButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        normalButtonStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));
        normalButtonStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked,Texture.class)));

        btnFree = new TextButton("Free Game",normalButtonStyle);
        btnStages = new TextButton("Stages",normalButtonStyle);
        btnHangar = new TextButton("Hangar",normalButtonStyle);
        btnShop = new TextButton("Shop",normalButtonStyle);
        btnLab = new TextButton("Lab",normalButtonStyle);
        btnPVP = new TextButton("PVP",normalButtonStyle);

        btnFree.setPosition(GameInfo.WIDTH - 700,440);
        btnStages.setPosition(GameInfo.WIDTH - 700,230);
        btnPVP.setPosition(GameInfo.WIDTH - 700,20);
        btnHangar.setPosition(100,440);
        btnLab.setPosition(100,230);
        btnShop.setPosition(100,20);

        btnFree.setHeight(600 * btnFree.getHeight() / btnFree.getWidth());
        btnFree.setWidth(600);

        btnShop.setHeight(600 * btnShop.getHeight() / btnShop.getWidth());
        btnShop.setWidth(600);

        btnHangar.setHeight(600 * btnHangar.getHeight() / btnHangar.getWidth());
        btnHangar.setWidth(600);

        btnStages.setHeight(600 * btnStages.getHeight() / btnStages.getWidth());
        btnStages.setWidth(600);

        btnPVP.setHeight(600 * btnPVP.getHeight() / btnPVP.getWidth());
        btnPVP.setWidth(600);

        btnLab.setHeight(600 * btnLab.getHeight() / btnLab.getWidth());
        btnLab.setWidth(600);

        stage.addActor(btnFree);
        stage.addActor(btnHangar);
        stage.addActor(btnShop);
        stage.addActor(btnStages);
        stage.addActor(btnLab);
        stage.addActor(btnPVP);

    }


}
