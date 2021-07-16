package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
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
import com.serhatmerak.spacewars.custom_actors.CustomDialog;
import com.serhatmerak.spacewars.custom_actors.HangarBoxWindow;
import com.serhatmerak.spacewars.database.GemData;
import com.serhatmerak.spacewars.database.WeaponData;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.BoxListener;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.pets.PetData;


/**
 * Created by Serhat Merak on 23.04.2018.
 */

public class ReinforcementsHuds implements BoxListener {

    public Stage stage;
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private Image menu3;

    public enum MenuState{WEAPONS,GEMS}
    public enum ShipState{SHIP,PET}

    public MenuState menuState = MenuState.GEMS;
    public ShipState shipState = ShipState.SHIP;

    public TextButton btnWeapons,btnGems,btnShip,btnPet;

    public Table gemsTable,weaponsTable;
    public ScrollPane gemsScrollPane,weaponsScrollPane;

    public Array<Box> shipGems,shipWeapons,petGems,petWeapons;
    private CoinTable coinTable;

    private Image shipImage,petImage,darkBg;
    private HangarBoxWindow hangarBoxWindow;

    private Array<Image> shipGemsArrows,shipWeaponsArrows,petGemsArrows,petWeaponsArrows;
    private Array<Image> shipGemsDownArrows,shipWeaponsDownArrows,petGemsDownArrows,petWeaponsDownArrows;
    private CustomDialog confirmationDialog;
    public ImageButton btnInfo,btnHome;

    private TextButton sellSelecteds,btnCancel;

    private boolean longPressed = false;
    private int checkedPrice;
    private CustomDialog sellCheckedDialog;





    public ReinforcementsHuds(Viewport viewport, SpriteBatch batch){
        stage = new Stage(viewport,batch);
        createMenu3();
        createMenuButtons();
        createGemsTable();
        createWeaponsTable();
        createShipButtons();
        createShipGems();
        createShipWeapons();
        createShipImage();
        createPetGems();
        createPetWeapons();
        createPetImage();
        createHangarBoxWindow();
        createDarkBg();
        createArrows();
        createDownArrows();
        createConfirmationDialog();
        createCoinTable();
        createMiniButtons();
        createCheck();
        createSellCheckedsDialog();
    }

    private void createSellCheckedsDialog() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        sellCheckedDialog = new CustomDialog(windowStyle);
        sellCheckedDialog.getTitleLabel().setText(texts.get("warning"));
        sellCheckedDialog.setMessage("Are you sure you want to sell selected items for " + getCheckedPrice() + " c ?",
                Assets.getInstance().assetManager.get(Assets.fntSarani44, BitmapFont.class));
        sellCheckedDialog.setConfirmationButtons();
        sellCheckedDialog.pack();

        sellCheckedDialog.btnYes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                for (Box box :getCheckedBoxes()) {
                    if(box.itemType == Box.ItemType.GEM){
                        User.user.gemBag.removeValue(((Gem) box.item),true);
                    }else {
                        User.user.weaponBag.removeValue(((Weapon) box.item),true);
                    }
                }
                User.user.coin += checkedPrice;
                if(menuState == MenuState.GEMS){
                    gemsTable.clearChildren();
                    setGemsTableCells();
                    GameManager.gameManager.updateGemBag();
                }else {
                    weaponsTable.clearChildren();
                    setWeaponsTableCells();
                    GameManager.gameManager.updateWepBag();
                }

                sellSelecteds.remove();
                btnCancel.remove();
                stage.addActor(btnGems);
                stage.addActor(btnWeapons);
                longPressed = false;
                coinTable.update();
                GameManager.gameManager.saveCoin();

            }
        });
    }

    private void createCheck() {
        final TextButton.TextButtonStyle sellStyle = new TextButton.TextButtonStyle();
        sellStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);
        Sprite up = new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnNorm,Texture.class));
        Sprite over = new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnHover,Texture.class));
        Sprite down = new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnClicked,Texture.class));
        up.flip(true,false);
        over.flip(true,false);
        down.flip(true,false);
        sellStyle.up = new SpriteDrawable(up);
        sellStyle.over = new SpriteDrawable(over);
        sellStyle.down = new SpriteDrawable(down);

        sellSelecteds = new TextButton("sell selecteds",sellStyle);
        sellSelecteds.setSize(btnGems.getWidth(),
                (btnGems.getWidth()) * (sellSelecteds.getHeight() / sellSelecteds.getWidth()));
        sellSelecteds.setPosition(btnWeapons.getX(),btnWeapons.getY());

        sellSelecteds.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sellCheckedDialog.setMessage("Are you sure you want to sell selected items for " + getCheckedPrice() + " c ?",
                        Assets.getInstance().assetManager.get(Assets.fntSarani44, BitmapFont.class));
                sellCheckedDialog.show(stage);
            }
        });

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);


        TextButton.TextButtonStyle closeStyle = new TextButton.TextButtonStyle();
        closeStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);
        closeStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnNorm,Texture.class)));
        closeStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnHover,Texture.class)));
        closeStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.redBtnClicked,Texture.class)));

        btnCancel = new TextButton("cancel",closeStyle);
        btnCancel.setSize(sellSelecteds.getWidth(),sellSelecteds.getHeight());
        btnCancel.setPosition(btnGems.getX(),sellSelecteds.getY());
        btnCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                longPressed = false;
                for (Box box :getCheckedBoxes()) {
                    box.state = Box.State.NORMAL;
                    box.stateChanged();
                    box.touchDownTime = 0;
                    box.pressing = false;
                }
                sellSelecteds.remove();
                btnCancel.remove();
                stage.addActor(btnGems);
                stage.addActor(btnWeapons);
            }
        });

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

        btnHome.setPosition(btnWeapons.getX() + btnWeapons.getWidth() - btnHome.getWidth(),menu3.getY() / 2 - btnHome.getHeight() / 2);
        btnInfo.setPosition(btnHome.getX() - 10 - btnInfo.getWidth(),btnHome.getY());

        stage.addActor(btnHome);
        stage.addActor(btnInfo);

    }

    private void createCoinTable() {
        coinTable = new CoinTable();
        coinTable.setPosition(btnGems.getX(),menu3.getY() / 2 - coinTable.getHeight() / 2);
        stage.addActor(coinTable);
    }

    private void createConfirmationDialog() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        confirmationDialog = new CustomDialog(windowStyle);
        confirmationDialog.getTitleLabel().setText(texts.get("warning"));
        confirmationDialog.setMessage("Are you sure you want to sell this ?",
                Assets.getInstance().assetManager.get(Assets.fntSarani44, BitmapFont.class));
        confirmationDialog.setConfirmationButtons();
        confirmationDialog.pack();

        confirmationDialog.btnYes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){

                    User.user.coin += hangarBoxWindow.price;
                    User.user.gemBag.removeValue(((Gem) hangarBoxWindow.activeBox.item),true);
                    gemsTable.clearChildren();
                    setGemsTableCells();

                    GameManager.gameManager.updateGemBag();
                }else {

                    User.user.coin += hangarBoxWindow.price;
                    User.user.weaponBag.removeValue(((Weapon) hangarBoxWindow.activeBox.item),true);
                    weaponsTable.clearChildren();
                    setWeaponsTableCells();

                    GameManager.gameManager.updateWepBag();
                }

                coinTable.update();
                GameManager.gameManager.saveCoin();
                closeHangarBoxWindow();
            }
        });
    }

    private void createDownArrows() {
        shipGemsDownArrows = new Array<Image>();
        shipWeaponsDownArrows = new Array<Image>();
        petGemsDownArrows = new Array<Image>();
        petWeaponsDownArrows = new Array<Image>();

        for (int i=0;i<5;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(shipGems.get(i).getX() + shipGems.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    shipGems.get(i).getY() + shipGems.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getDownArrowListener(i));
            shipGemsDownArrows.add(arrow);
        }

        for (int i=0;i<3;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(shipWeapons.get(i).getX() + shipWeapons.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    shipWeapons.get(i).getY() + shipWeapons.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getDownArrowListener(i));
            shipWeaponsDownArrows.add(arrow);
        }

        for (int i=0;i<PetData.petData.petStyle.gemNest;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(petGems.get(i).getX() + petGems.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    petGems.get(i).getY() + petGems.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getDownArrowListener(i));
            petGemsDownArrows.add(arrow);
        }

        for (int i=0;i<PetData.petData.petStyle.weaponNest;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.downArrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(petWeapons.get(i).getX() + petWeapons.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    petWeapons.get(i).getY() + petWeapons.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getDownArrowListener(i));
            petWeaponsDownArrows.add(arrow);
        }
    }
    private void createArrows() {
        shipGemsArrows = new Array<Image>();
        shipWeaponsArrows = new Array<Image>();
        petGemsArrows = new Array<Image>();
        petWeaponsArrows = new Array<Image>();

        for (int i=0;i<5;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.arrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(shipGems.get(i).getX() + shipGems.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    shipGems.get(i).getY() + shipGems.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getArrowListener(i));
            shipGemsArrows.add(arrow);
        }

        for (int i=0;i<3;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.arrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(shipWeapons.get(i).getX() + shipWeapons.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    shipWeapons.get(i).getY() + shipWeapons.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getArrowListener(i));
            shipWeaponsArrows.add(arrow);
        }

        for (int i=0;i<PetData.petData.petStyle.gemNest;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.arrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(petGems.get(i).getX() + petGems.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    petGems.get(i).getY() + petGems.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getArrowListener(i));
            petGemsArrows.add(arrow);
        }

        for (int i=0;i<PetData.petData.petStyle.weaponNest;i++){
            Image arrow = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.arrow,Texture.class))));
            arrow.setSize(120 * (arrow.getWidth() / arrow.getHeight()),120);
            arrow.setPosition(petWeapons.get(i).getX() + petWeapons.get(i).getWidth() / 2 - arrow.getWidth() / 2,
                    petWeapons.get(i).getY() + petWeapons.get(i).getHeight() / 2 - arrow.getHeight() / 2);
            arrow.setOrigin(Align.center);
            arrow.addAction(Actions.forever(Actions.sequence(
                    Actions.scaleTo(1.3f,1.3f,0.5f),
                    Actions.scaleTo(1f,1f,0.5f)
            )));

            arrow.addListener(getArrowListener(i));
            petWeaponsArrows.add(arrow);
        }
    }
    private void createDarkBg() {
        darkBg = new Image(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        darkBg.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        darkBg.setColor(0,0,0,0.7f);
    }
    private void createHangarBoxWindow() {
        hangarBoxWindow = new HangarBoxWindow();
        hangarBoxWindow.setPosition(((menu3.getX() + menu3.getX() + menu3.getWidth()) / 2) - (hangarBoxWindow.getWidth() / 2),
                GameInfo.HEIGHT / 2 - hangarBoxWindow.getWidth() / 2);

        hangarBoxWindow.btnClose.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeHangarBoxWindow();
            }
        });

        hangarBoxWindow.btnUnequip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if(shipState == ShipState.SHIP){
                    if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                        User.user.gems.set(User.user.gems.indexOf((Gem) hangarBoxWindow.activeBox.item,true),null);
                        User.user.gemBag.add((Gem) hangarBoxWindow.activeBox.item);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                        GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));
                    }else {

                        User.user.weapons.set(User.user.weapons.indexOf((Weapon) hangarBoxWindow.activeBox.item,true),null);
                        User.user.weaponBag.add((Weapon) hangarBoxWindow.activeBox.item);
                        weaponsTable.clearChildren();
                        setWeaponsTableCells();
                        GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
                    }
                }else {
                    if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                        PetData.petData.gems.set(PetData.petData.gems.indexOf((Gem) hangarBoxWindow.activeBox.item,true),null);
                        User.user.gemBag.add((Gem) hangarBoxWindow.activeBox.item);
                        gemsTable.clearChildren();
                        setGemsTableCells();
                        GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));
                    }else {
                        PetData.petData.weapons.set(PetData.petData.weapons.indexOf((Weapon) hangarBoxWindow.activeBox.item,true),null);
                        User.user.weaponBag.add((Weapon) hangarBoxWindow.activeBox.item);
                        weaponsTable.clearChildren();
                        setWeaponsTableCells();
                        GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
                    }
                }

                hangarBoxWindow.activeBox.setItemNull();
                GameManager.gameManager.saveSlots();
                closeHangarBoxWindow();
            }
        });

        hangarBoxWindow.btnSell.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmationDialog.show(stage);
            }
        });
    }
    private void createPetImage() {
        petImage = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(
                PetData.petData.petStyle.petTexture,Texture.class))));
        petImage.setPosition((btnPet.getX() - 3 )- petImage.getWidth() / 2,
                (264 + (petWeapons.first().getY() - 12 - 264) / 2) - petImage.getHeight() / 2);
    }
    private void createPetWeapons() {
        petWeapons = new Array<Box>();
        for (int i = 0; i < PetData.petData.petStyle.weaponNest; i++) {

            Box box = new Box();
            if(PetData.petData.weapons.get(i) != null) {
                Weapon weapon = PetData.petData.weapons.get(i);
                box.setItem(weapon, Box.ItemType.WEAPON);
            }



            if(PetData.petData.petStyle.weaponNest == 1){
                box.setPosition(264,btnShip.getY() - 252);
            }else {
                if (i == 0){
                    box.setPosition((btnPet.getX() - 3) - 246,btnShip.getY() - 252);
                }else {
                    box.setPosition((btnPet.getX() - 3) + 6,btnShip.getY() - 252);
                }
            }

            box.setBoxListener(this);
            petWeapons.add(box);
        }
    }
    private void createPetGems() {
        petGems = new Array<Box>();
        for (int i = 0; i < PetData.petData.petStyle.gemNest; i++) {

            Box box = new Box();
            if(PetData.petData.gems.get(i) != null) {
                Gem gem = PetData.petData.gems.get(i);
                box.setItem(gem, Box.ItemType.GEM);
            }

            if(PetData.petData.petStyle.gemNest == 1)
                box.setPosition(264,12);
            else if(PetData.petData.petStyle.gemNest == 2){
                if (i == 0)
                    box.setPosition((btnPet.getX() - 3) - 246,12);
                else
                    box.setPosition((btnPet.getX() - 3) + 6,12);
            }else {
                if (i == 0)
                    box.setPosition(12,12);
                else if (i == 1)
                    box.setPosition(264,12);
                else
                    box.setPosition(516,12);
            }

            box.setBoxListener(this);
            petGems.add(box);
        }
    }
    private void createShipImage() {
        shipImage = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(
                User.user.shipStyle.shipTexture,Texture.class))));
        shipImage.setPosition((btnPet.getX() - 3 )- shipImage.getWidth() / 2,
                (264 + (shipWeapons.get(1).getY() - 12 - 264) / 2) - shipImage.getHeight() / 2);
        stage.addActor(shipImage);
        System.out.println(shipImage.getWidth());
    }
    private void createShipWeapons() {
        shipWeapons = new Array<Box>();
        for (int i = 0; i < 3; i++) {

            Box box = new Box();
            if(User.user.weapons.get(i) != null) {
                Weapon weapon = User.user.weapons.get(i);
                box.setItem(weapon, Box.ItemType.WEAPON);
            }

            switch (i){
                case 0:
                    box.setPosition(12,btnShip.getY() - 372);
                    break;
                case 1:
                    box.setPosition(264,btnShip.getY() - 252);
                    break;
                case 2:
                    box.setPosition(516,btnShip.getY() - 372);
                    break;
            }

            box.setBoxListener(this);
            stage.addActor(box);
            shipWeapons.add(box);
        }
    }
    private void createShipGems() {
        shipGems = new Array<Box>();
        for (int i = 0; i < 5; i++) {

            Box box = new Box();
            if(User.user.gems.get(i) != null) {
                Gem gem = User.user.gems.get(i);
                box.setItem(gem, Box.ItemType.GEM);
            }
            switch (i){
                case 0:
                    box.setPosition(12,264);
                    break;
                case 1:
                    box.setPosition(12,12);
                    break;
                case 2:
                    box.setPosition(264,12);
                    break;
                case 3:
                    box.setPosition(516,12);
                    break;
                case 4:
                    box.setPosition(516,264);
                    break;
            }

            box.setBoxListener(this);
            stage.addActor(box);
            shipGems.add(box);
        }
    }
    private void createShipButtons() {
        Sprite up = new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class));
        Sprite over = new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class));
        up.flip(true,false);
        over.flip(true,false);

        final SpriteDrawable petNormal = new SpriteDrawable(up);
        final SpriteDrawable petChecked = new SpriteDrawable(over);
        final SpriteDrawable shipNormal = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        final SpriteDrawable shipChecked = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));

        TextButton.TextButtonStyle shipStyle = new TextButton.TextButtonStyle();
        shipStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
        shipStyle.up = shipChecked;

        TextButton.TextButtonStyle petStyle = new TextButton.TextButtonStyle();
        petStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani64);
        petStyle.up = petNormal;

        btnShip = new TextButton(texts.get("ship"),shipStyle);
        btnPet = new TextButton(texts.get("pet"),petStyle);

        btnShip.setSize(360,btnShip.getHeight() * (360 / btnShip.getWidth()));
        btnPet.setSize(360,btnPet.getHeight() * (360 / btnPet.getWidth()));

        btnShip.setPosition(32,menu3.getY() + menu3.getHeight() - btnShip.getHeight() - 24);
        btnPet.setPosition(398,menu3.getY() + menu3.getHeight() - btnPet.getHeight() - 24);

        stage.addActor(btnShip);
        stage.addActor(btnPet);

        btnShip.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(shipState == ShipState.PET){
                    shipState = ShipState.SHIP;
                    btnShip.getStyle().up = shipChecked;
                    btnPet.getStyle().up = petNormal;

                    //TODO: REMOVE pets
                    petImage.remove();
                    for (Box box:petGems)
                        box.remove();
                    for (Box box:petWeapons)
                        box.remove();

                    for (Box box:shipGems)
                        stage.addActor(box);
                    for (Box box:shipWeapons)
                        stage.addActor(box);
                    stage.addActor(shipImage);
                }
            }
        });

        btnPet.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(shipState == ShipState.SHIP){
                    shipState = ShipState.PET;
                    btnPet.getStyle().up = petChecked;
                    btnShip.getStyle().up = shipNormal;

                    //TODO: add pets
                    stage.addActor(petImage);
                    for (Box box:petGems)
                        stage.addActor(box);
                    for (Box box:petWeapons)
                        stage.addActor(box);

                    for (Box box:shipGems)
                        box.remove();
                    for (Box box:shipWeapons)
                        box.remove();
                    shipImage.remove();

                }
            }
        });
    }
    private void createWeaponsTable() {
        weaponsTable = new Table();
        weaponsTable.setWidth(840);
        weaponsTable.setHeight(((User.user.gemBag.size / 3) + 1) * 260);
        weaponsTable.align(Align.topLeft);

        setWeaponsTableCells();


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        weaponsScrollPane = new ScrollPane(weaponsTable, scrollPaneStyle);
        weaponsScrollPane.setVariableSizeKnobs(false);
        weaponsScrollPane.setOverscroll(false, false);
        weaponsScrollPane.setSize(880,600);
        weaponsScrollPane.setPosition(menu3.getX() + menu3.getWidth() / 2 - weaponsScrollPane.getWidth() / 2,menu3.getY() + 60);
    }
    private void setWeaponsTableCells() {
        for(int i=0;i<User.user.weaponBag.size;i++){
            if(i%3 == 0)
                weaponsTable.row();
            Box box = new Box();
            box.setItem(User.user.weaponBag.get(i), Box.ItemType.WEAPON);
            weaponsTable.add(box).padRight(15).padBottom(20).padLeft(15);
            box.setBoxListener(this);
        }
    }
    private void createGemsTable() {
        gemsTable = new Table();
        gemsTable.setWidth(840);
        gemsTable.setHeight(((User.user.gemBag.size / 3) + 1) * 260);
        gemsTable.align(Align.topLeft);
//        gemsTable.setPosition(menu3.getX() + 74,menu3.getY() + 10);

        setGemsTableCells();


        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        gemsScrollPane = new ScrollPane(gemsTable, scrollPaneStyle);
        gemsScrollPane.setVariableSizeKnobs(false);
        gemsScrollPane.setOverscroll(false, false);
        gemsScrollPane.setSize(880,600);
        gemsScrollPane.setPosition(menu3.getX() + menu3.getWidth() / 2 - gemsScrollPane.getWidth() / 2  ,
                menu3.getY() + 60);
        stage.addActor(gemsScrollPane);
    }
    private void setGemsTableCells() {
        for(int i=0;i<User.user.gemBag.size;i++){
            if(i%3 == 0)
                gemsTable.row();
            Box box = new Box();
            box.setItem(User.user.gemBag.get(i), Box.ItemType.GEM);
            box.setBoxListener(this);
            gemsTable.add(box).padRight(15).padBottom(20).padLeft(15);
        }
    }
    private void createMenuButtons() {


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

        btnGems = new TextButton(texts.get("gems"),gemsStyle);
        btnWeapons = new TextButton(texts.get("weapons"),weaponsStyle);

        btnGems.setSize(450,btnGems.getHeight() * (450 / btnGems.getWidth()));
        btnWeapons.setSize(450,btnGems.getHeight() * (450 / btnGems.getWidth()));

        btnGems.setPosition(menu3.getX() + menu3.getWidth() / 2 - 5 - btnGems.getWidth(),menu3.getY() + 710);
        btnWeapons.setPosition(menu3.getX() + menu3.getWidth() / 2 + 5,menu3.getY() + 710);

        stage.addActor(btnGems);
        stage.addActor(btnWeapons);

        btnGems.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(menuState == MenuState.WEAPONS){
                    menuState = MenuState.GEMS;
                    btnGems.getStyle().up = gemChecked;
                    btnWeapons.getStyle().up = weaponNormal;
                    weaponsScrollPane.remove();
                    stage.addActor(gemsScrollPane);
                }
            }
        });

        btnWeapons.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(menuState == MenuState.GEMS){
                    menuState = MenuState.WEAPONS;
                    btnWeapons.getStyle().up = weaponChecked;
                    btnGems.getStyle().up = gemNormal;
                    gemsScrollPane.remove();
                    stage.addActor(weaponsScrollPane);
                }
            }
        });
    }
    private void createMenu3() {
        menu3 = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(
                Assets.menu3, Texture.class))));
        menu3.setSize(1050,900);
        menu3.setPosition(GameInfo.WIDTH - 20,160, Align.bottomRight);
        stage.addActor(menu3);
    }
    private ClickListener getArrowListener(final int finalI){
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(shipState == ShipState.SHIP){
                    if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                        Gem gem = (Gem) hangarBoxWindow.activeBox.item;
                        hangarBoxWindow.activeBox.setItem(User.user.gems.get(finalI), Box.ItemType.GEM);
                        User.user.gemBag.set(User.user.gemBag.indexOf(gem,true),User.user.gems.get(finalI));
                        User.user.gems.set(finalI,gem);
                        shipGems.get(finalI).setItem(gem, Box.ItemType.GEM);

                        GameManager.gameManager.updateGemBag();

                    }else {
                        Weapon weapon = (Weapon) hangarBoxWindow.activeBox.item;
                        hangarBoxWindow.activeBox.setItem(User.user.weapons.get(finalI), Box.ItemType.WEAPON);
                        User.user.weaponBag.set(User.user.weaponBag.indexOf(weapon,true),User.user.weapons.get(finalI));
                        User.user.weapons.set(finalI,weapon);
                        shipWeapons.get(finalI).setItem(weapon, Box.ItemType.WEAPON);

                        GameManager.gameManager.updateWepBag();
                    }
                }else {
                    if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                        Gem gem = (Gem) hangarBoxWindow.activeBox.item;
                        hangarBoxWindow.activeBox.setItem(PetData.petData.gems.get(finalI), Box.ItemType.GEM);
                        User.user.gemBag.set(User.user.gemBag.indexOf(gem,true),PetData.petData.gems.get(finalI));
                        PetData.petData.gems.set(finalI,gem);
                        petGems.get(finalI).setItem(gem, Box.ItemType.GEM);

                        GameManager.gameManager.updateGemBag();
                    }else {
                        Weapon weapon = (Weapon) hangarBoxWindow.activeBox.item;
                        hangarBoxWindow.activeBox.setItem(PetData.petData.weapons.get(finalI), Box.ItemType.WEAPON);
                        User.user.weaponBag.set(User.user.weaponBag.indexOf(weapon,true),PetData.petData.weapons.get(finalI));
                        PetData.petData.weapons.set(finalI,weapon);
                        petWeapons.get(finalI).setItem(weapon, Box.ItemType.WEAPON);

                        GameManager.gameManager.updateWepBag();
                    }
                }
                GameManager.gameManager.saveSlots();
                closeHangarBoxWindow();
            }
        };
    }
    private ClickListener getDownArrowListener(final int finalI){
        return new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Box box = hangarBoxWindow.activeBox;
                if(shipState == ShipState.SHIP){
                    if(menuState == MenuState.GEMS){
                        shipGems.get(finalI).setItem(box.item,box.itemType);
                        User.user.gems.set(finalI,((Gem) box.item));
                        User.user.gemBag.removeValue(((Gem) box.item),true);
                        gemsTable.clearChildren();
                        setGemsTableCells();

                        GameManager.gameManager.updateGemBag();

                    }else {
                        shipWeapons.get(finalI).setItem(box.item,box.itemType);
                        User.user.weapons.set(finalI,((Weapon) box.item));
                        User.user.weaponBag.removeValue(((Weapon) box.item),true);
                        weaponsTable.clearChildren();
                        setWeaponsTableCells();

                        GameManager.gameManager.updateWepBag();
                    }
                }else {
                    if(menuState == MenuState.GEMS){
                        petGems.get(finalI).setItem(box.item,box.itemType);
                        PetData.petData.gems.set(finalI,((Gem) box.item));
                        User.user.gemBag.removeValue(((Gem) box.item),true);
                        gemsTable.clearChildren();
                        setGemsTableCells();

                        GameManager.gameManager.updateGemBag();
                    }else {
                        petWeapons.get(finalI).setItem(box.item,box.itemType);
                        PetData.petData.weapons.set(finalI,((Weapon) box.item));
                        User.user.weaponBag.removeValue(((Weapon) box.item),true);
                        weaponsTable.clearChildren();
                        setWeaponsTableCells();

                        GameManager.gameManager.updateWepBag();
                    }
                }
                GameManager.gameManager.saveSlots();
                closeHangarBoxWindow();
            }
        };
    }
    private void closeHangarBoxWindow(){
        hangarBoxWindow.activeBox.state = Box.State.NORMAL;
        hangarBoxWindow.activeBox.stateChanged();
        hangarBoxWindow.remove();
        darkBg.remove();

        if(shipState == ShipState.SHIP){
            if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                for (Image image:shipGemsArrows)
                    image.remove();
                for (Image image:shipGemsDownArrows)
                    image.remove();
            }else {
                for (Image image:shipWeaponsArrows)
                    image.remove();
                for (Image image:shipWeaponsDownArrows)
                    image.remove();
            }
        }else {
            if(hangarBoxWindow.activeBox.itemType == Box.ItemType.GEM){
                for (Image image:petGemsArrows)
                    image.remove();
                for (Image image:petGemsDownArrows)
                    image.remove();
            }else {
                for (Image image:petWeaponsArrows)
                    image.remove();
                for (Image image:petWeaponsDownArrows)
                    image.remove();
            }
        }
    }

    @Override
    public void boxClicked(Box box) {
        if(longPressed) {
            if(box.itemType == Box.ItemType.GEM) {
                if (User.user.gemBag.contains(((Gem) box.item), true)) {
                    if(box.state == Box.State.CLICK){
                        box.state = Box.State.NORMAL;
                        box.stateChanged();
                        if(getCheckedBoxes().size == 0){
                            stage.addActor(btnGems);
                            stage.addActor(btnWeapons);
                            sellSelecteds.remove();
                            btnCancel.remove();
                            longPressed = false;
                        }

                    }else {
                        box.state = Box.State.CLICK;
                        box.stateChanged();
                    }
                }
            }
            else {
                if (User.user.weaponBag.contains(((Weapon) box.item), true)) {
                    if(box.state == Box.State.CLICK){
                        box.state = Box.State.NORMAL;
                        box.border.setColor(Color.valueOf("156c93"));
                        if(getCheckedBoxes().size == 0){
                            stage.addActor(btnGems);
                            stage.addActor(btnWeapons);
                            sellSelecteds.remove();
                            btnCancel.remove();
                            longPressed = false;
                        }
                    }else {
                        box.state = Box.State.CLICK;
                        box.stateChanged();
                    }
                }
            }
            sellSelecteds.setText("sell selecteds\n" + getCheckedPrice() + " c" );
        }else {

            if (box.itemType != Box.ItemType.NULL) {
                stage.addActor(darkBg);
                hangarBoxWindow.setItem(box);
                stage.addActor(hangarBoxWindow);

                if (shipState == ShipState.SHIP) {
                    if (box.itemType == Box.ItemType.GEM) {
                        if (!shipGems.contains(box, true)) {
                            hangarBoxWindow.equippedBox = false;
                            for (int i = 0; i < shipGemsArrows.size; i++) {
                                if (shipGems.get(i).itemType == Box.ItemType.NULL) {
                                    stage.addActor(shipGemsDownArrows.get(i));
                                    shipGemsDownArrows.get(i).setScale(1);
                                    shipGemsDownArrows.get(i).getActions().first().restart();
                                } else {
                                    stage.addActor(shipGemsArrows.get(i));
                                    shipGemsArrows.get(i).setScale(1);
                                    shipGemsArrows.get(i).getActions().first().restart();
                                }
                            }
                        } else
                            hangarBoxWindow.equippedBox = true;
                    } else {
                        if (!shipWeapons.contains(box, true)) {
                            hangarBoxWindow.equippedBox = false;
                            for (int i = 0; i < shipWeaponsArrows.size; i++) {
                                if (shipWeapons.get(i).itemType == Box.ItemType.NULL) {
                                    stage.addActor(shipWeaponsDownArrows.get(i));
                                    shipWeaponsDownArrows.get(i).setScale(1);
                                    shipWeaponsDownArrows.get(i).getActions().first().restart();
                                } else {
                                    stage.addActor(shipWeaponsArrows.get(i));
                                    shipWeaponsArrows.get(i).setScale(1);
                                    shipWeaponsArrows.get(i).getActions().first().restart();
                                }
                            }
                        } else
                            hangarBoxWindow.equippedBox = true;
                    }
                } else {
                    if (box.itemType == Box.ItemType.GEM) {
                        if (!petGems.contains(box, true)) {
                            hangarBoxWindow.equippedBox = false;
                            for (int i = 0; i < petGemsArrows.size; i++) {
                                if (petGems.get(i).itemType == Box.ItemType.NULL) {
                                    stage.addActor(petGemsDownArrows.get(i));
                                    petGemsDownArrows.get(i).setScale(1);
                                    petGemsDownArrows.get(i).getActions().first().restart();
                                } else {
                                    stage.addActor(petGemsArrows.get(i));
                                    petGemsArrows.get(i).setScale(1);
                                    petGemsArrows.get(i).getActions().first().restart();
                                }
                            }
                        } else
                            hangarBoxWindow.equippedBox = true;
                    } else {
                        if (!petWeapons.contains(box, true)) {
                            hangarBoxWindow.equippedBox = false;
                            for (int i = 0; i < petWeaponsArrows.size; i++) {
                                if (petWeapons.get(i).itemType == Box.ItemType.NULL) {
                                    stage.addActor(petWeaponsDownArrows.get(i));
                                    petWeaponsDownArrows.get(i).setScale(1);
                                    petWeaponsDownArrows.get(i).getActions().first().restart();

                                } else {
                                    stage.addActor(petWeaponsArrows.get(i));
                                    petWeaponsArrows.get(i).setScale(1);
                                    petWeaponsArrows.get(i).getActions().first().restart();
                                }
                            }
                        } else
                            hangarBoxWindow.equippedBox = true;
                    }
                }
            }
        }
    }

    private Array<Box> getCheckedBoxes(){
        Array<Box> boxes = new Array<Box>();
        if(menuState == MenuState.WEAPONS) {
            for (Cell<Box> cell : weaponsTable.getCells()) {
                Box box = cell.getActor();
                if(box.state == Box.State.CLICK)
                    boxes.add(box);
            }
        }else {
            for (Cell<Box> cell : gemsTable.getCells()) {
                Box box = cell.getActor();
                if(box.state == Box.State.CLICK)
                    boxes.add(box);

            }
        }

        return boxes;
    }

    private int getCheckedPrice(){
        checkedPrice = 0;
        for (Box box :getCheckedBoxes()) {
            checkedPrice += getPrice(box);
        }

        return checkedPrice;
    }


    private int getPrice(Box box){
        int price;
        if(box.itemType == Box.ItemType.GEM){
            Gem gem = (Gem) box.item;
            int level = Integer.valueOf(gem.gemStyle.toString().substring(gem.gemStyle.toString().length() - 1));
            price = 250;
            for (int i = 0; i < level; i++) {
                price *= 2;
            }

        }else {
            Weapon weapon = (Weapon) box.item;
            price = 250;
            for (int i = 0; i < weapon.level; i++) {
                price *= 2;
            }
            price += weapon.damage * 100;
        }

        return price;
    }

    @Override
    public void boxLongPress(Box box) {
        if(!longPressed) {
            if (box.itemType == Box.ItemType.GEM) {
                if (User.user.gemBag.contains(((Gem) box.item), true))
                    longPressed = true;
            } else if (User.user.weaponBag.contains(((Weapon) box.item), true))
                    longPressed = true;
            if (longPressed) {
                box.state = Box.State.CLICK;
                box.stateChanged();
                btnWeapons.remove();
                btnGems.remove();
                stage.addActor(sellSelecteds);
                stage.addActor(btnCancel);
                sellSelecteds.setText("sell selecteds\n" + getCheckedPrice() + " c" );


            }
        }
    }
}
