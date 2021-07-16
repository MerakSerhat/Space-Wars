package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.custom_actors.Box;
import com.serhatmerak.spacewars.custom_actors.CoinTable;
import com.serhatmerak.spacewars.custom_actors.CustomCheckBox;
import com.serhatmerak.spacewars.custom_actors.Toast;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.BoxListener;
import com.serhatmerak.spacewars.helpers.GameInfo;

import java.util.Arrays;

/**
 * Created by Serhat Merak on 27.06.2018.
 */

public class LabHuds implements BoxListener{
    public Stage stage;
    private Image menu9,bagBG,labBG;
    private TextButton btnGem,btnWep;
    private enum MenuState{GEMS,WEAPONS}
    private MenuState menuState = MenuState.GEMS;
    private CoinTable coinTable;

    public ImageButton btnHome,btnInfo;
    private Table weaponsTable,gemsTable;
    private ScrollPane weaponsScrollPane,gemsScrollPane;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private Box boxWepBefore,boxWepAfter;
    private Image arrow;
    private TextButton.TextButtonStyle upragadeStyle,disabledStyle;
    private TextButton btnUpragade;
    private Label upragadeLbl,lblRate,lblWarning;
    private BoxListener boxWepBeforeListener;
    private CustomCheckBox wepUpragadeCbx;

    private Array<Box> toBeMeltedGemBoxes;
    private Box boxGemAfter;
    private Array<Image> arrowImages;
    private Array<Label> lblMelts;
    private TextButton btnMelt;
    private Label lblRateGem;
    private Label lblMelt;
    private Gem.GemStyle toBeMeltedGemStyle;

    public LabHuds(Viewport viewport, SpriteBatch batch){
        stage = new Stage(viewport,batch);

        createMenu9();
        createButtons();
        createCoinTable();
        createMiniButtons();
        createBagBG();
        createLabBG();
        createWeaponsTable();
        createGemsTable();
        createWeaponLabActors();
        createGemLabActors();
    }

    private void createGemLabActors() {
        toBeMeltedGemBoxes = new Array<Box>(3);
        arrowImages = new Array<Image>(3);
        lblMelts = new Array<Label>(3);

        BoxListener boxListener = new BoxListener() {
            @Override
            public void boxClicked(Box box) {
                if(box.itemType != Box.ItemType.NULL){
                    box.setItemNull();
                    gemsTable.clearChildren();
                    setGemsTableCells();

                    if(toBeMeltedGemBoxes.get(0).itemType == Box.ItemType.NULL &&
                            toBeMeltedGemBoxes.get(1).itemType == Box.ItemType.NULL &&
                            toBeMeltedGemBoxes.get(2).itemType == Box.ItemType.NULL){

                        toBeMeltedGemStyle = null;
                        boxGemAfter.setItemNull();
                        btnMelt.setText("-");
                        lblRateGem.setText("-%");

                    }else {
                        btnMelt.setStyle(disabledStyle);
                        lblMelt.setText("place gems");
                    }

                    lblMelt.setText("place gems");

                }
            }

            @Override
            public void boxLongPress(Box box) {

            }
        };

        for (int i=0;i<3;i++){
            Box box = new Box();
            box.enabled = false;
            box.setBoxListener(boxListener);

            Image arrow = new Image(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class));
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
            arrow.setOrigin(Align.center);
            Label label = new Label("melt",labelStyle);
            label.setAlignment(Align.center);
            label.setWidth(arrow.getHeight());
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            switch (i){
                case 0:{
                    box.setPosition(labBG.getX() + 70,labBG.getY() + 34);
                    arrow.setPosition(box.getX() + box.getWidth() + 20,box.getY() + box.getHeight() / 2 - 20);
                    arrow.setRotation(125);
                    label.setPosition(arrow.getX(),arrow.getY() - 10 - label.getPrefHeight());
                }break;
                case 1:{
                    box.setPosition(labBG.getX() + 70,labBG.getY() + labBG.getHeight() / 2 - box.getHeight() / 2);
                    arrow.setPosition(box.getX() + box.getWidth() + 20,box.getY() + box.getHeight() / 2 - arrow.getWidth() / 2);
                    arrow.setRotation(90);
                    label.setPosition(arrow.getX(),arrow.getY() - 10 - label.getPrefHeight());
                }break;
                case 2:{
                    box.setPosition(labBG.getX() + 70,labBG.getY() + labBG.getHeight() - box.getHeight() - 34);
                    arrow.setPosition(box.getX() + box.getWidth() + 20,box.getY() + box.getHeight() / 2 - arrow.getWidth() + 20);
                    arrow.setRotation(55);
                    label.setPosition(arrow.getX(),arrow.getY() - 10 - label.getPrefHeight());

                }break;
            }

            toBeMeltedGemBoxes.add(box);
            arrowImages.add(arrow);
            lblMelts.add(label);

            stage.addActor(box);
            stage.addActor(arrow);
            stage.addActor(label);

        }

        boxGemAfter = new Box();
        boxGemAfter.border.setColor(Color.GREEN);
        boxGemAfter.enabled = false;
        boxGemAfter.showTxt = false;
        boxGemAfter.setPosition(lblMelts.get(1).getX() + lblMelts.get(1).getWidth() + (labBG.getWidth() + labBG.getX() - lblMelts.get(1).getWidth() - lblMelts.get(1).getX()) / 2  - boxGemAfter.getWidth() / 2,toBeMeltedGemBoxes.get(1).getY());

        stage.addActor(boxGemAfter);

        Label.LabelStyle rateStyle = new Label.LabelStyle();
        rateStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        lblRateGem = new Label("-%",rateStyle);
        lblRateGem.setWidth(350);
        lblRateGem.setAlignment(Align.center);
        lblRateGem.setPosition(boxGemAfter.getX() + boxGemAfter.getWidth() / 2 - lblRateGem.getWidth() / 2,
                boxGemAfter.getY() + boxGemAfter.getHeight() + (labBG.getY() + labBG.getHeight() - boxGemAfter.getHeight() - boxGemAfter.getY()) / 2 - lblRateGem.getHeight());

        stage.addActor(lblRateGem);

        btnMelt = new TextButton("-",disabledStyle);
        btnMelt.setSize(370,370 * (btnMelt.getHeight() / btnMelt.getWidth()));
        btnMelt.setPosition(lblMelts.get(1).getX() + lblMelts.get(1).getWidth() +
                        (labBG.getWidth() + labBG.getX() - lblMelts.get(1).getWidth() - lblMelts.get(1).getX()) / 2 - btnMelt.getWidth() / 2,
                toBeMeltedGemBoxes.get(0).getY() + 30);

        stage.addActor(btnMelt);

        Label.LabelStyle meltLabelStyle = new Label.LabelStyle();
        meltLabelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);

        lblMelt = new Label("place gems",meltLabelStyle);
        lblMelt.setWidth(350);
        lblMelt.setAlignment(Align.center);
        lblMelt.setWrap(true);
        lblMelt.setPosition(btnMelt.getX() + 10,btnMelt.getY() + btnMelt.getHeight() + 10);

        stage.addActor(lblMelt);

        btnMelt.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(btnMelt.getStyle() == upragadeStyle){
                    meltClicked();
                }
            }
        });

    }

    private void createWeaponLabActors() {
        boxWepBefore = new Box();
        boxWepBefore.enabled = false;
        boxWepBefore.setItemNull();
        boxWepBefore.setPosition(labBG.getX() + 70,labBG.getY() + 70);

        boxWepAfter = new Box();
        boxWepAfter.enabled = false;
        boxWepAfter.setItemNull();
        boxWepAfter.setPosition(labBG.getX() + 70,labBG.getY() + labBG.getHeight() - 70 - boxWepBefore.getHeight());
        boxWepAfter.border.setColor(Color.GREEN);

        boxWepBeforeListener = new BoxListener() {
            @Override
            public void boxClicked(Box box) {
                box.setItemNull();
                boxWepAfter.setItemNull();
                for (Cell<Box> cell : weaponsTable.getCells()) {
                    cell.getActor().state = Box.State.NORMAL;
                    cell.getActor().stateChanged();
                }

                btnUpragade.setStyle(disabledStyle);
                btnUpragade.setText("-");
                upragadeLbl.setText("place a weapon");
                lblRate.setText("-%");
            }

            @Override
            public void boxLongPress(Box box) {

            }
        };

        boxWepBefore.setBoxListener(boxWepBeforeListener);

        Sprite arrowSprite = new Sprite(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class));
        arrowSprite.flip(false,true);
        arrow = new Image(new SpriteDrawable(arrowSprite));
        arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
        arrow.setOrigin(Align.center);
        arrow.setPosition(boxWepBefore.getX() + boxWepBefore.getWidth() / 2 - arrow.getWidth() / 2,
                boxWepBefore.getY() + boxWepBefore.getHeight() +
                        (boxWepAfter.getY() - boxWepBefore.getY() - boxWepBefore.getHeight()) / 2 - arrow.getHeight() / 2);
        arrow.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1.3f,1.3f,0.5f),
                Actions.scaleTo(1f,1f,0.5f)
        )));

        disabledStyle = new TextButton.TextButtonStyle();
        disabledStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);
        disabledStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blackDisabled,Texture.class)));


        upragadeStyle = new TextButton.TextButtonStyle();
        upragadeStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);
        upragadeStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnNorm,Texture.class)));
        upragadeStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnHover,Texture.class)));
        upragadeStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnClicked,Texture.class)));

        btnUpragade = new TextButton("-",disabledStyle);
        btnUpragade.setSize(370,370 * (btnUpragade.getHeight() / btnUpragade.getWidth()));
        btnUpragade.setPosition(boxWepBefore.getX() + boxWepBefore.getWidth() +
                (labBG.getWidth() + labBG.getX() - boxWepBefore.getWidth() - boxWepBefore.getX()) / 2 - btnUpragade.getWidth() / 2,
                labBG.getY() + 120);

        btnUpragade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(btnUpragade.getStyle() == upragadeStyle){
                    upragadeClicked();
                }
            }
        });


        Label.LabelStyle upragadeLabelStyle = new Label.LabelStyle();
        upragadeLabelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);

        upragadeLbl = new Label("place a weapon",upragadeLabelStyle);
        upragadeLbl.setWidth(350);
        upragadeLbl.setAlignment(Align.center);
        upragadeLbl.setWrap(true);
        upragadeLbl.setPosition(btnUpragade.getX() + 10,btnUpragade.getY() + btnUpragade.getHeight() + 10);

        Label.LabelStyle rateStyle = new Label.LabelStyle();
        rateStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        lblRate = new Label("-%",rateStyle);
        lblRate.setWidth(350);
        lblRate.setAlignment(Align.center);
        lblRate.setPosition(upragadeLbl.getX(),arrow.getY() + arrow.getHeight() / 2 - lblRate.getHeight() / 2);

        Label.LabelStyle warningStyle = new Label.LabelStyle();
        warningStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);

        lblWarning = new Label("To re-use the weapon If the upragade fails, pay %50 more coin",warningStyle);
        lblWarning.setWidth(390);
        lblWarning.setWrap(true);
        lblWarning.setPosition(boxWepBefore.getX() + boxWepBefore.getWidth() +
                (labBG.getWidth() + labBG.getX() - boxWepBefore.getWidth() - boxWepBefore.getX()) / 2 - lblWarning.getWidth() / 2
                + 60,boxWepAfter.getY() + boxWepAfter.getHeight() / 2 - lblWarning.getHeight() / 2);
        lblWarning.setHeight(lblWarning.getPrefHeight());

        wepUpragadeCbx = new CustomCheckBox();
        wepUpragadeCbx.setSize(75,75);
        wepUpragadeCbx.setPosition(lblWarning.getX() - wepUpragadeCbx.getWidth() - 15,
                lblWarning.getY() + (lblWarning.getHeight() - wepUpragadeCbx.getHeight()) / 2);
        wepUpragadeCbx.addLabel(lblWarning);

        ClickListener checkBoxListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!(boxWepBefore.itemType == Box.ItemType.NULL)){
                    btnUpragade.setText(getUpragadePrice(((Weapon) boxWepBefore.item)) + " C");

                    if(User.user.coin >= getUpragadePrice(((Weapon) boxWepBefore.item))){
                        btnUpragade.setStyle(upragadeStyle);
                        upragadeLbl.setText("upragade");
                    }else {
                        btnUpragade.setStyle(disabledStyle);
                        upragadeLbl.setText("yetersiz coin");
                    }
                }
            }
        };

        wepUpragadeCbx.addListener(checkBoxListener);
        lblWarning.addListener(checkBoxListener);

    }

    private void createGemsTable() {
        gemsTable = new Table();
        gemsTable.setWidth(1200);
        gemsTable.setHeight(((User.user.weaponBag.size / 3) + 1) * 260);
        gemsTable.align(Align.topLeft);

        setGemsTableCells();


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        gemsScrollPane = new ScrollPane(gemsTable, scrollPaneStyle);
        gemsScrollPane.setVariableSizeKnobs(false);
        gemsScrollPane.setOverscroll(false, false);
        gemsScrollPane.setSize(800,680);
        gemsScrollPane.setPosition(bagBG.getX() + 60,bagBG.getY() + 60);

        stage.addActor(gemsScrollPane);
    }

    private void setGemsTableCells() {
        int j = 0;
        for(int i=0;i<User.user.gemBag.size;i++){

            if(!(toBeMeltedGemBoxes != null && ((toBeMeltedGemBoxes.get(0).item != null && toBeMeltedGemBoxes.get(0).item == User.user.gemBag.get(i)) ||
                    (toBeMeltedGemBoxes.get(1).item != null && toBeMeltedGemBoxes.get(1).item == User.user.gemBag.get(i)) ||
                    (toBeMeltedGemBoxes.get(2).item != null && toBeMeltedGemBoxes.get(2).item == User.user.gemBag.get(i))))) {
                if (j % 3 == 0)
                    gemsTable.row();
                Box box = new Box();
                box.enabled = false;
                box.setItem(User.user.gemBag.get(i), Box.ItemType.GEM);
                gemsTable.add(box).padRight(10).padTop(10);
                box.setBoxListener(this);
                j++;
            }
        }
    }

    private void createWeaponsTable() {
        weaponsTable = new Table();
        weaponsTable.setWidth(1200);
        weaponsTable.setHeight(((User.user.gemBag.size / 3) + 1) * 260);
        weaponsTable.align(Align.topLeft);

        setWeaponsTableCells();


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        weaponsScrollPane = new ScrollPane(weaponsTable, scrollPaneStyle);
        weaponsScrollPane.setVariableSizeKnobs(false);
        weaponsScrollPane.setOverscroll(false, false);
        weaponsScrollPane.setSize(800,680);
        weaponsScrollPane.setPosition(bagBG.getX() + 60,bagBG.getY() + 60);
    }

    private void setWeaponsTableCells() {
        for(int i=0;i<User.user.weaponBag.size;i++){
            if(i%3 == 0)
                weaponsTable.row();
            Box box = new Box();
            box.enabled = false;
            box.setItem(User.user.weaponBag.get(i), Box.ItemType.WEAPON);
            weaponsTable.add(box).padRight(10).padTop(10);
            box.setBoxListener(this);
        }
    }


    private void createLabBG() {
        labBG = new Image(Assets.getInstance().assetManager.get(Assets.menu10,Texture.class));
        labBG.setSize(915,800);
        labBG.setPosition(menu9.getX(),menu9.getY() / 2 - labBG.getHeight() / 2);
        stage.addActor(labBG);
    }

    private void createBagBG() {
        bagBG = new Image(Assets.getInstance().assetManager.get(Assets.menu10,Texture.class));
        bagBG.setSize(915,800);
        bagBG.setPosition(menu9.getX() + menu9.getWidth() - bagBG.getWidth(),
                menu9.getY() / 2 - bagBG.getHeight() / 2);
        stage.addActor(bagBG);
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
        home.setSize(80,80);
        homeStyle.imageUp = new SpriteDrawable(home);

        ImageButton.ImageButtonStyle infoStyle = new ImageButton.ImageButtonStyle();
        infoStyle.up = up;
        infoStyle.over = over;
        infoStyle.down = down;
        Sprite questionMark = new Sprite(Assets.getInstance().assetManager.get(Assets.questionMark,Texture.class));
        questionMark.setColor(Color.valueOf("73c4ee"));
        questionMark.setSize(80,80);
        infoStyle.imageUp = new SpriteDrawable(questionMark);

        btnHome = new ImageButton(homeStyle);
        btnInfo = new ImageButton(infoStyle);

        btnHome.setSize(140,140);
        btnInfo.setSize(140,140);

        btnInfo.setPosition(btnWep.getX() + btnWep.getWidth() + (menu9.getX() + menu9.getWidth() - (btnWep.getX() + btnWep.getWidth())) / 2 - 5 - btnInfo.getWidth()
                ,menu9.getY() + (menu9.getHeight() / 2) - ( btnInfo.getHeight() / 2) - 10);
        btnHome.setPosition(btnInfo.getX() + btnInfo.getWidth() + 10,btnInfo.getY());

        stage.addActor(btnHome);
        stage.addActor(btnInfo);

    }

    private void createCoinTable() {
        coinTable = new CoinTable();
        coinTable.setPosition(menu9.getX() + (btnGem.getX() - menu9.getX()) / 2 - coinTable.getWidth() / 2,
                menu9.getY() + (menu9.getHeight() / 2) - ( coinTable.getHeight() / 2) - 10);

        stage.addActor(coinTable);
    }

    private void createButtons() {
        Sprite up = new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class));
        Sprite over = new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class));
        up.flip(true,false);
        over.flip(true,false);

        final SpriteDrawable weaponNormal = new SpriteDrawable(up);
        final SpriteDrawable weaponChecked = new SpriteDrawable(over);
        final SpriteDrawable gemNormal = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        final SpriteDrawable gemChecked = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));

        TextButton.TextButtonStyle gemsStyle = new TextButton.TextButtonStyle();
        gemsStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
        gemsStyle.up = gemChecked;

        TextButton.TextButtonStyle weaponsStyle = new TextButton.TextButtonStyle();
        weaponsStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
        weaponsStyle.up = weaponNormal;


        btnGem = new TextButton(texts.get("gems"),gemsStyle);
        btnWep = new TextButton(texts.get("weapons"),weaponsStyle);

        btnGem.setSize(450,450 * (btnGem.getHeight() / btnGem.getWidth()));
        btnWep.setSize(450,450 * (btnWep.getHeight() / btnWep.getWidth()));

        btnGem.setPosition(GameInfo.WIDTH / 2 - 5 - btnGem.getWidth(),menu9.getY() + (menu9.getHeight() / 2) - ( btnGem.getHeight() / 2) - 10);
        btnWep.setPosition(GameInfo.WIDTH / 2 + 5,btnGem.getY());

        stage.addActor(btnWep);
        stage.addActor(btnGem);

        btnGem.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(menuState == MenuState.WEAPONS){
                    menuState = MenuState.GEMS;
                    btnGem.getStyle().up = gemChecked;
                    btnWep.getStyle().up = weaponNormal;
                    weaponsScrollPane.remove();
                    stage.addActor(gemsScrollPane);

                    boxWepAfter.remove();
                    boxWepBefore.remove();
                    arrow.remove();
                    btnUpragade.remove();
                    upragadeLbl.remove();
                    lblRate.remove();
                    lblWarning.remove();
                    wepUpragadeCbx.remove();

                    for (int i=0;i<3;i++){
                        stage.addActor(arrowImages.get(i));
                        stage.addActor(toBeMeltedGemBoxes.get(i));
                        stage.addActor(lblMelts.get(i));
                    }
                    stage.addActor(boxGemAfter);
                    stage.addActor(lblRateGem);
                    stage.addActor(btnMelt);
                    stage.addActor(lblMelt);

                }
            }
        });

        btnWep.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(menuState == MenuState.GEMS){
                    menuState = MenuState.WEAPONS;
                    btnWep.getStyle().up = weaponChecked;
                    btnGem.getStyle().up = gemNormal;

                    gemsScrollPane.remove();
                    for (int i=0;i<3;i++){
                        arrowImages.get(i).remove();
                        toBeMeltedGemBoxes.get(i).remove();
                        lblMelts.get(i).remove();
                    }
                    boxGemAfter.remove();
                    lblRateGem.remove();
                    btnMelt.remove();
                    lblMelt.remove();


                    stage.addActor(weaponsScrollPane);
                    stage.addActor(boxWepAfter);
                    stage.addActor(boxWepBefore);
                    stage.addActor(arrow);
                    stage.addActor(btnUpragade);
                    stage.addActor(upragadeLbl);
                    stage.addActor(lblRate);
                    stage.addActor(lblWarning);
                    stage.addActor(wepUpragadeCbx);
                }
            }
        });

    }

    private void createMenu9() {
        menu9 = new Image(Assets.getInstance().assetManager.get(Assets.menu9, Texture.class));
        menu9.setPosition(GameInfo.WIDTH / 2 - menu9.getWidth() / 2, GameInfo.HEIGHT - 20 - menu9.getHeight());
        stage.addActor(menu9);
    }

    private int getUpragadePrice(Weapon weapon){
        int coin = weapon.damage * 50 + weapon.level * 500;
        if(wepUpragadeCbx.checked)
            coin += coin / 2;

        return coin;
    }

    private int getUpragadeRate(Weapon weapon){
        if(100 - weapon.level * 10 - weapon.damage > 5)
            return 100 - weapon.level * 10 - weapon.damage;
        else
            return 5;
    }

    private int getMeltPrice(Gem.GemStyle gemStyle){
        int level = Integer.valueOf(gemStyle.toString().substring(gemStyle.toString().length() - 1,gemStyle.toString().length()));
        int price = 500;
        for(int i = 0;i < level;i++){
            price *=2;
        }
        return price;
    }

    private int getMeltRate(Gem.GemStyle gemStyle){
        int level = Integer.valueOf(gemStyle.toString().substring(gemStyle.toString().length() - 1,gemStyle.toString().length()));
        int rate = 100;
        for(int i = 0;i < level;i++){
            rate -= 10;
        }
        return rate;
    }

    //todo
    private void upragadeClicked() {
        User.user.coin -= getUpragadePrice((Weapon)boxWepBefore.item);
        int rate = getUpragadeRate((Weapon) boxWepBefore.item);

        if(MathUtils.random(1,100) <= rate){
            User.user.weaponBag.set(User.user.weaponBag.indexOf((Weapon)boxWepBefore.item,true),(Weapon)boxWepAfter.item);
            stage.addActor(new Toast("SUCCESSFUL",2).show());
        }else {
            if(!wepUpragadeCbx.checked)
                User.user.weaponBag.removeValue((Weapon)boxWepBefore.item,true);
            stage.addActor(new Toast("UNSUCCESSFUL",2).show());
        }

        boxWepBeforeListener.boxClicked(boxWepBefore);
        weaponsTable.clearChildren();
        setWeaponsTableCells();
        coinTable.update();
        GameManager.gameManager.saveCoin();
    }

    private void meltClicked(){
        User.user.coin -= getMeltPrice(toBeMeltedGemStyle);
        int rate = getMeltRate(toBeMeltedGemStyle);

        for (Box box:toBeMeltedGemBoxes) {
            User.user.gemBag.removeValue((Gem) box.item,true);
            box.setItemNull();
        }


        if(MathUtils.random(1,100) <= rate){
            User.user.gemBag.add((Gem) boxGemAfter.item);
            stage.addActor(new Toast("SUCCESSFUL",2).show());
        }else {
            stage.addActor(new Toast("UNSUCCESSFUL",2).show());
        }

        toBeMeltedGemStyle = null;
        boxGemAfter.setItemNull();
        btnMelt.setStyle(disabledStyle);
        btnMelt.setText("-");
        lblMelt.setText("place gems");
        lblRateGem.setText("-%");
        gemsTable.clearChildren();
        setGemsTableCells();
        coinTable.update();
        GameManager.gameManager.saveCoin();
        GameManager.gameManager.updateGemBag();
    }

    @Override
    public void boxClicked(Box box) {
        if(menuState == MenuState.WEAPONS) {
            for (Cell<Box> cell : weaponsTable.getCells()) {
                cell.getActor().state = Box.State.NORMAL;
                cell.getActor().stateChanged();
            }
        }else {
            for (Cell<Box> cell : gemsTable.getCells()) {
                cell.getActor().state = Box.State.NORMAL;
                cell.getActor().stateChanged();
            }
        }

        box.state = Box.State.CLICK;
        box.border.setColor(Color.YELLOW);

        if(menuState == MenuState.WEAPONS){
            boxWepBefore.setItem(box.item,box.itemType);
            Weapon weapon = new Weapon(((Weapon) box.item).weaponStyle);
            weapon.level = ((Weapon) box.item).level;
            weapon.levelUp();
            boxWepAfter.setItem(weapon, Box.ItemType.WEAPON);

            //todo:para yetmiyosa yetersiz para de
            if(User.user.coin >= getUpragadePrice(((Weapon) box.item))){
                btnUpragade.setStyle(upragadeStyle);
                upragadeLbl.setText("upragade");
            }else {
                btnUpragade.setStyle(disabledStyle);
                upragadeLbl.setText("yetersiz coin");
            }

            btnUpragade.setText(getUpragadePrice(((Weapon) box.item)) + " C");
            lblRate.setText(getUpragadeRate(((Weapon) box.item)) + "%");

        }else {
            if(((Gem) box.item).gemStyle.toString().substring(((Gem) box.item).gemStyle.toString().length() - 1,((Gem) box.item).gemStyle.toString().length()).equals("6")){
                stage.addActor(new Toast("The gem is already in last level",2).show());
            }else {
                if (toBeMeltedGemStyle == null || toBeMeltedGemStyle != ((Gem) box.item).gemStyle) {
                    for (Box meltBox : toBeMeltedGemBoxes) {
                        meltBox.setItemNull();
                    }
                    toBeMeltedGemBoxes.get(0).setItem(box.item, Box.ItemType.GEM);
                    toBeMeltedGemStyle = ((Gem) box.item).gemStyle;

                    gemsTable.clearChildren();
                    setGemsTableCells();
                    lblMelt.setText("place gems");
                    btnMelt.setStyle(disabledStyle);
                    btnMelt.setText(getMeltPrice(toBeMeltedGemStyle) + " C");
                    lblRateGem.setText(getMeltRate(toBeMeltedGemStyle) + "%");
                } else {
                    if (toBeMeltedGemBoxes.get(0).itemType == Box.ItemType.NULL) {
                        toBeMeltedGemBoxes.get(0).setItem(box.item, Box.ItemType.GEM);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                    } else if (toBeMeltedGemBoxes.get(1).itemType == Box.ItemType.NULL) {
                        toBeMeltedGemBoxes.get(1).setItem(box.item, Box.ItemType.GEM);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                    } else if (toBeMeltedGemBoxes.get(2).itemType == Box.ItemType.NULL) {
                        toBeMeltedGemBoxes.get(2).setItem(box.item, Box.ItemType.GEM);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                    } else {
                        toBeMeltedGemBoxes.get(0).setItem(box.item, Box.ItemType.GEM);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                    }

                    if(toBeMeltedGemBoxes.get(0).itemType != Box.ItemType.NULL &&
                            toBeMeltedGemBoxes.get(1).itemType != Box.ItemType.NULL &&
                            toBeMeltedGemBoxes.get(2).itemType != Box.ItemType.NULL){

                        if(getMeltPrice(toBeMeltedGemStyle) <= User.user.coin){
                            btnMelt.setStyle(upragadeStyle);
                            lblMelt.setText("Melt");
                        }else {
                            btnMelt.setStyle(disabledStyle);
                            lblMelt.setText("yetersiz coin");
                        }
                    }
                }

                Gem.GemStyle afterStyle = Gem.GemStyle.values()[Arrays.asList(Gem.GemStyle.values()).indexOf(toBeMeltedGemStyle) + 1];
                boxGemAfter.setItem(new Gem(afterStyle), Box.ItemType.GEM);
            }
        }
    }

    @Override
    public void boxLongPress(Box box) {

    }
}
