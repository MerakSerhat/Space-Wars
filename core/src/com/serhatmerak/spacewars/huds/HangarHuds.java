package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.custom_actors.ShipSelector;
import com.serhatmerak.spacewars.custom_actors.Stats;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 19.06.2018.
 */

public class HangarHuds {
    public Stage stage;
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public TextButton btnReinforcements;
    private Stats stats;

    public ImageButton btnHome,btnInfo,btnSettings;

    private ShipSelector shipSelector;
    private ShipSelector petSelector;


    public HangarHuds(Viewport viewport, SpriteBatch batch) {
        stage = new Stage(viewport, batch);

        createBtnReinforcements();
        createStats();
        createMiniButtons();
        createShipSelector();
        createPetSelector();
    }

    private void createPetSelector() {
        petSelector = new ShipSelector();
        petSelector.setPosition(30,20);
        petSelector.addCell(PetStyle.PET1);
        petSelector.addCell(PetStyle.PET2);
        petSelector.addCell(PetStyle.PET3);
        petSelector.addCell(PetStyle.PET4);
        stage.addActor(petSelector);
        petSelector.pack();
        ShipSelector.stats = stats;
    }

    private void createShipSelector() {
        shipSelector = new ShipSelector();
        shipSelector.setPosition(30,570);
        shipSelector.addCell(SpaceShipStyle.Ship1);
        shipSelector.addCell(SpaceShipStyle.Ship2);
        shipSelector.addCell(SpaceShipStyle.Ship3);
        shipSelector.addCell(SpaceShipStyle.Ship4);
        shipSelector.addCell(SpaceShipStyle.Ship5);
        shipSelector.addCell(SpaceShipStyle.Ship6);
        shipSelector.addCell(SpaceShipStyle.Ship7);
        stage.addActor(shipSelector);
        shipSelector.pack();
        ShipSelector.stats = stats;

    }

    private void createMiniButtons() {
        TextureAtlas atlas = Assets.getInstance().assetManager.get(Assets.blueSquareButton);

        SpriteDrawable up = new SpriteDrawable(new Sprite(atlas.findRegion("BlueNormal2")));
        SpriteDrawable over = new SpriteDrawable(new Sprite(atlas.findRegion("BlueHover5")));
        SpriteDrawable down = new SpriteDrawable(new Sprite(atlas.findRegion("BlueClicked8")));

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.up = up;
        homeStyle.over = over;
        homeStyle.down = down;
        Sprite home = new Sprite(Assets.getInstance().assetManager.get(Assets.home,Texture.class));
        home.setColor(Color.valueOf("73c4ee"));
        home.setSize(94,94);
        homeStyle.imageUp = new SpriteDrawable(home);

        ImageButton.ImageButtonStyle settingsStyle = new ImageButton.ImageButtonStyle();
        settingsStyle.up = up;
        settingsStyle.over = over;
        settingsStyle.down = down;
        Sprite settings = new Sprite(Assets.getInstance().assetManager.get(Assets.settings,Texture.class));
        settings.setColor(Color.valueOf("73c4ee"));
        settings.setSize(94,94);
        settingsStyle.imageUp = new SpriteDrawable(settings);

        ImageButton.ImageButtonStyle infoStyle = new ImageButton.ImageButtonStyle();
        infoStyle.up = up;
        infoStyle.over = over;
        infoStyle.down = down;
        Sprite questionMark = new Sprite(Assets.getInstance().assetManager.get(Assets.questionMark,Texture.class));
        questionMark.setColor(Color.valueOf("73c4ee"));
        questionMark.setSize(94,94);
        infoStyle.imageUp = new SpriteDrawable(questionMark);

        btnHome = new ImageButton(homeStyle);
        btnSettings = new ImageButton(settingsStyle);
        btnInfo = new ImageButton(infoStyle);

        btnHome.setSize(164,164);
        btnInfo.setSize(164,164);
        btnSettings.setSize(164,164);

        btnHome.setPosition(btnReinforcements.getX() + btnReinforcements.getWidth() - btnHome.getWidth(),
                btnReinforcements.getY() - 20 - btnHome.getHeight());
        btnInfo.setPosition(btnReinforcements.getX(),btnReinforcements.getY() - 20 - btnInfo.getHeight());
        btnSettings.setPosition(btnReinforcements.getX() + (btnReinforcements.getWidth() / 2) - (btnSettings.getWidth() / 2),
                btnReinforcements.getY() - 20 - btnSettings.getHeight());

        stage.addActor(btnHome);
        stage.addActor(btnSettings);
        stage.addActor(btnInfo);

    }

    private void createStats() {
        stats = new Stats();
        stats.setPosition(1250,430);
        stage.addActor(stats);
    }

    private void createBtnReinforcements() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm, Texture.class)));
        textButtonStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover, Texture.class)));
        textButtonStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked, Texture.class)));

        btnReinforcements = new TextButton("Reinforcements",textButtonStyle);
        btnReinforcements.setSize(630,630 * (btnReinforcements.getHeight() / btnReinforcements.getWidth()));
        btnReinforcements.setPosition(1252,220);

        stage.addActor(btnReinforcements);
    }


}
