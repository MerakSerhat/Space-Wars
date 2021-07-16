package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 6.07.2018.
 */

public class MissionTable extends Group {
    private Sprite sprite;
    public Mission mission;
    public Label label;
    private Label lblMission;
    private TextButton.TextButtonStyle onStyle,offStyle;
    private TextButton generalBtn,prizeBtn,explanationBtn;
    public ImageButton selectButton;
    public Image background;
    public Array<Label> labels,amountLabels;

    private float labelHeight;

    public I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);
    private final float lbHtoBgH = 2.2f;


    public MissionTable(Mission mission){
        sprite = new Sprite(Assets.getInstance().assetManager.get(Assets.menu2,Texture.class));
        sprite.setSize(sprite.getWidth(),sprite.getHeight() * 1.2f);
        setSize(sprite.getWidth(),sprite.getHeight());
        this.mission = mission;
        background = new Image(new SpriteDrawable(sprite));
        labels = new Array<Label>();
        amountLabels = new Array<Label>();
        createGeneralLabels();
        setMissionLabel();



        setTitleLabel();
        createButtons();
        setActorPositions();
        setBackgroundSize();

        addActor(background);
        addActor(label);
        addActor(lblMission);
        addActor(explanationBtn);
        addActor(prizeBtn);
        addActor(generalBtn);
        addActor(selectButton);
        for (int i = 0; i < labels.size; i++) {
            addActor(labels.get(i));
            addActor(amountLabels.get(i));
        }
    }

    private void setActorPositions() {
        label.setPosition(sprite.getWidth() / 2 - label.getWidth() / 2,sprite.getHeight() - label.getHeight() - 5);
        lblMission.setPosition(sprite.getWidth() / 2 - lblMission.getWidth() / 2,
                sprite.getHeight() / 2 - lblMission.getHeight() / 2 - 10);

        generalBtn.setPosition(sprite.getWidth() / 2 - generalBtn.getWidth() / 2,0.78f * sprite.getHeight());
        explanationBtn.setPosition(generalBtn.getX() - 10 - explanationBtn.getWidth(),generalBtn.getY());
        prizeBtn.setPosition(generalBtn.getX() + generalBtn.getWidth() + 10,generalBtn.getY());
        selectButton.setPosition(sprite.getWidth() - 20 - selectButton.getWidth(),20);
    }

    private void setBackgroundSize() {
        if(lblMission.getPrefHeight() > labelHeight)
            labelHeight = lblMission.getPrefHeight();

        float labelSpace = generalBtn.getY() - selectButton.getHeight() - selectButton.getY() - 40;
        if(labelHeight > labelSpace) {
            sprite.setSize(background.getWidth(), labelHeight * lbHtoBgH);
            background.setSize(sprite.getWidth(),sprite.getHeight());
            setSize(background.getWidth(),background.getHeight());
            setActorPositions();
        }
    }
    private void createGeneralLabels() {

        Mission.ComplexMission complexMission = mission.getComplexMission();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);

        for (Mission.BeatMission beatMission:complexMission.beatMissions){
            Label label = new Label("",labelStyle);
            Label lblDone = new Label("",labelStyle);
            String map = "";
            if(beatMission.map != null)
                map += "[#8888ff] (" + beatMission.map.toString().substring(
                        37,beatMission.map.toString().length()) + ") []";



            if(beatMission.beatEnemy){
                label.setText(beatMission.spaceShipStyle.name() + "\n" + map);
            }else
                label.setText(texts.get("crystal") + "\n" + map);

            lblDone.setText(beatMission.amount + "");
            labels.add(label);
            amountLabels.add(lblDone);
        }

        for (Mission.FindMission findMission:complexMission.findMissions){
            Label label = new Label("",labelStyle);
            Label lblDone = new Label("",labelStyle);
            String from = "";
            if(findMission.fromCrystal)
                from += "[#8888ff] ("+ texts.get("crystal").toUpperCase() + ")[]";
            else {
                if(findMission.enemy != null){
                    from += "[#8888ff] (" + findMission.enemy.name() + ")[]";
                }
            }

            String name = "";

            switch (findMission.gameObjectCollector.state){
                case GEM:
                    name = ((Gem.GemStyle) findMission.gameObjectCollector.object).name;
                    break;
                case WEAPON:
                    name = ((Weapon.WeaponStyle) findMission.gameObjectCollector.object).name;
                    break;
                case COIN:
                    name = findMission.gameObjectCollector.object +" COIN";
                    break;
            }
            lblDone.setText(findMission.amount + "");
            label.setText(name + "\n" + from);
            labels.add(label);
            amountLabels.add(lblDone);
        }

        for (Mission.GoMission goMission:complexMission.goMissions){
            Label label = new Label("",labelStyle);
            Label lblDone = new Label("",labelStyle);
            String map = goMission.map.toString().substring(37,goMission.map.toString().length());

            label.setUserObject(goMission.amount);
            label.setText(texts.get("goTo")+ " " + map);
            lblDone.setText("" + goMission.amount);

            amountLabels.add(lblDone);
            labels.add(label);
        }

        showGeneral();
    }
    private void showGeneral() {
        int height = 0;
        for (Label label:labels){
            height += label.getPrefHeight();
            height += 10;
        }

        labelHeight = height;

        float y = sprite.getHeight() / 2 - height / 2 + 30;

        System.out.println(labels.size);

        for (int i = 0; i < labels.size; i++) {
            labels.get(i).setPosition(75,y);
            y += labels.get(i).getPrefHeight() + 20;
        }

        for (int i = 0; i < labels.size; i++) {
            amountLabels.get(i).setPosition(sprite.getWidth() - 75 - amountLabels.get(i).getWidth(),
                    labels.get(i).getY() + labels.get(i).getPrefHeight() / 2 - amountLabels.get(i).getPrefHeight() / 2);
        }

        for (int i = 0; i < labels.size; i++) {
            System.out.println(labels.get(i).getText() + " - " + amountLabels.get(i).getText());
        }

    }
    private void createButtons() {
        onStyle = new TextButton.TextButtonStyle();
        onStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
        onStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));

        offStyle = new TextButton.TextButtonStyle();
        offStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
        offStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));

        generalBtn = new TextButton(texts.get("general"),onStyle);
        explanationBtn = new TextButton(texts.get("explan."),offStyle);
        prizeBtn = new TextButton(texts.get("prize"),offStyle);

        generalBtn.setSize(150,150 * (generalBtn.getHeight() / generalBtn.getWidth()));
        explanationBtn.setSize(150,150 * (explanationBtn.getHeight() / explanationBtn.getWidth()));
        prizeBtn.setSize(150,150 * (prizeBtn.getHeight() / prizeBtn.getWidth()));

        explanationBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(explanationBtn.getStyle() == offStyle){
                    explanationBtn.setStyle(onStyle);
                    prizeBtn.setStyle(offStyle);
                    generalBtn.setStyle(offStyle);
                    lblMission.setText(mission.getTxtMission());
                    lblMission.setAlignment(Align.left);
                    for (int i = 0; i <labels.size ; i++) {
                        labels.get(i).remove();
                        amountLabels.get(i).remove();
                    }
                }
            }
        });

        prizeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(prizeBtn.getStyle() == offStyle){
                    prizeBtn.setStyle(onStyle);
                    explanationBtn.setStyle(offStyle);
                    generalBtn.setStyle(offStyle);
                    lblMission.setText(mission.getTxtPrize());
                    lblMission.setAlignment(Align.center);

                    for (int i = 0; i <labels.size ; i++) {
                        labels.get(i).remove();
                        amountLabels.get(i).remove();
                    }
                }
            }
        });

        generalBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(generalBtn.getStyle() == offStyle){
                    generalBtn.setStyle(onStyle);
                    explanationBtn.setStyle(offStyle);
                    prizeBtn.setStyle(offStyle);

                    lblMission.setText("");

                    for (int i = 0; i <labels.size ; i++) {
                        addActor(labels.get(i));
                        addActor(amountLabels.get(i));
                    }
                }
            }
        });

        TextureAtlas atlas = Assets.getInstance().assetManager.get(Assets.blueSquareButton);

        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = new SpriteDrawable(new Sprite(atlas.findRegion("BlueNormal2")));
        imageButtonStyle.over = new SpriteDrawable(new Sprite(atlas.findRegion("BlueHover5")));
        imageButtonStyle.down = new SpriteDrawable(new Sprite(atlas.findRegion("BlueClicked8")));
        imageButtonStyle.imageUp = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.select,Texture.class)));


        selectButton = new ImageButton(imageButtonStyle);
        selectButton.setUserObject(mission);
        selectButton.setSize(80,80);
    }
    private void setMissionLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);

        lblMission = new Label(mission.getTxtMission(),labelStyle);
        lblMission.setWrap(true);
        lblMission.setWidth(sprite.getWidth() - 50);
        lblMission.setText("");

    }

    private void setTitleLabel() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        label = new Label(mission.getTxtTitle(),labelStyle);
    }

}