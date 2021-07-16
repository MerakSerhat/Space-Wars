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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.custom_actors.Box;
import com.serhatmerak.spacewars.custom_actors.CoinTable;
import com.serhatmerak.spacewars.custom_actors.CustomDialog;
import com.serhatmerak.spacewars.custom_actors.ShopItemPanel;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 24.07.2018.
 */

public class ShopHuds{
    public Stage stage;
    private CoinTable coinTable;
    private Image shopBg,line;

    public ImageButton btnHome,btnInfo;
    private TextButton btnGems,btnWeapons,btnPets,btnShips;
    enum MenuState{
        GEMS,WEAPONS,PETS,SHIPS
    }
    private MenuState state = MenuState.GEMS;
    private TextButton.TextButtonStyle onStyle,offStyle;

    private ScrollPane scrollPane;
    private Table gemsTable,weaponsTable,shipsTable,petsTable;
    private CustomDialog confirmationDialog,inadequateCoinDialog;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    private Object item;
    private ShopItemPanel.ItemState itemState;
    private int price;


    public ShopHuds(Viewport viewport, SpriteBatch batch){
        stage = new Stage(viewport,batch);
        createMiniButtons();
        createCoinTable();
        createShopBg();
        createButtons();
        createLine();
        createScrollPane();
        createGemsTable();
        createWeaponsTable();
        createShipsTable();
        createPetsTable();
        createConfirmationDialog();
        createInadequateCoinDialog();

        menuStateChanged();
    }

    private void createInadequateCoinDialog() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        inadequateCoinDialog = new CustomDialog(windowStyle);
        inadequateCoinDialog.getTitleLabel().setText(texts.get("warning"));
        inadequateCoinDialog.setMessage("You don't have enought coin!");
        inadequateCoinDialog.setOkButton();
        inadequateCoinDialog.pack();
    }

    private void createConfirmationDialog() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSarani64);

        confirmationDialog = new CustomDialog(windowStyle);
        confirmationDialog.getTitleLabel().setText(texts.get("warning"));
        confirmationDialog.setConfirmationButtons();
        confirmationDialog.pack();

        confirmationDialog.btnYes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                User.user.coin -= price;
                GameManager.gameManager.saveCoin();
                coinTable.update();
                if(itemState == ShopItemPanel.ItemState.GEM){
                    User.user.gemBag.add(new Gem(((Gem.GemStyle) item)));
                    GameManager.gameManager.addGem(User.user.gemBag.get(User.user.gemBag.size - 1));
                }else if (itemState == ShopItemPanel.ItemState.WEAPON){
                    User.user.weaponBag.add(new Weapon(((Weapon.WeaponStyle) item)));
                    GameManager.gameManager.addWep(User.user.weaponBag.get(User.user.weaponBag.size - 1));
                }else if(itemState == ShopItemPanel.ItemState.SHIP){
                    GameManager.gameManager.openShip(((SpaceShipStyle) item));
                    createShipsTable();
                    scrollPane.setActor(shipsTable);
                }else {
                    GameManager.gameManager.openPet(((PetStyle) item));
                    createPetsTable();
                    scrollPane.setActor(petsTable);
                }


            }
        });
    }

    private void createPetsTable() {
        petsTable = new Table();
        for (int i = 0; i < 4; i++) {
            ShopItemPanel shopItemPanel = new ShopItemPanel();
            shopItemPanel.setItem(PetStyle.values()[i],PetStyle.values()[i].price);
            petsTable.add(shopItemPanel).padLeft(15).padRight(15);
            shopItemPanel.shopHuds = this;
        }

    }

    private void createShipsTable() {
        shipsTable = new Table();
        for (int i = 0; i < 7; i++) {
            ShopItemPanel shopItemPanel = new ShopItemPanel();
            shopItemPanel.setItem(SpaceShipStyle.values()[i],SpaceShipStyle.values()[i].price);
            shipsTable.add(shopItemPanel).padLeft(15).padRight(15);
            shopItemPanel.shopHuds = this;
        }

    }

    private void createWeaponsTable() {
        weaponsTable = new Table();
        for (int i = 0; i < 9; i++) {
            ShopItemPanel shopItemPanel = new ShopItemPanel();
            shopItemPanel.setItem(Weapon.WeaponStyle.values()[i],Weapon.WeaponStyle.values()[i].price);

            weaponsTable.add(shopItemPanel).padLeft(15).padRight(15);
            shopItemPanel.shopHuds = this;
        }

    }

    private void createGemsTable() {
        gemsTable = new Table();
        for (int i = 0; i < 30; i++) {
            ShopItemPanel shopItemPanel = new ShopItemPanel();
            shopItemPanel.setItem(Gem.GemStyle.values()[i],Gem.GemStyle.values()[i].price);
            gemsTable.add(shopItemPanel).padLeft(15).padRight(15);
            shopItemPanel.shopHuds = this;
        }
    }

    private void createScrollPane() {
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        scrollPane = new ScrollPane(null, scrollPaneStyle);
        scrollPane.setVariableSizeKnobs(false);
        scrollPane.setOverscroll(false, false);
        scrollPane.setSize(shopBg.getWidth() - 120,line.getY() - shopBg.getY() - 30);
        scrollPane.setPosition(GameInfo.WIDTH / 2 - scrollPane.getWidth() / 2,shopBg.getY() + 30);
        stage.addActor(scrollPane);
    }

    private void createLine() {
        line = new Image(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        line.setSize(shopBg.getWidth() - 30,10);
        line.setColor(Color.valueOf("156c93"));
        line.setPosition(GameInfo.WIDTH / 2 - line.getWidth() / 2,btnGems.getY() - 5 - line.getHeight());
        stage.addActor(line);
    }

    private void createButtons() {
        onStyle = new TextButton.TextButtonStyle();
        onStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        onStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));

        offStyle = new TextButton.TextButtonStyle();
        offStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        offStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));

        btnGems = new TextButton("gems",onStyle);
        btnWeapons = new TextButton("weapons",offStyle);
        btnPets = new TextButton("pets",offStyle);
        btnShips = new TextButton("ships",offStyle);

        btnGems.setSize(400,400 * (btnGems.getHeight() / btnGems.getWidth()));
        btnWeapons.setSize(btnGems.getWidth(),btnGems.getHeight());
        btnShips.setSize(btnGems.getWidth(),btnGems.getHeight());
        btnPets.setSize(btnGems.getWidth(),btnGems.getHeight());

        btnGems.setPosition(GameInfo.WIDTH / 2 - 15 - 2 * btnGems.getWidth(),
                shopBg.getY() + shopBg.getHeight() - 40 - btnGems.getHeight());
        btnWeapons.setPosition(GameInfo.WIDTH / 2 - 5 - btnGems.getWidth(),btnGems.getY());
        btnShips.setPosition(GameInfo.WIDTH / 2 + 5,btnGems.getY());
        btnPets.setPosition(btnShips.getX() + btnShips.getWidth() + 10,btnGems.getY());

        stage.addActor(btnGems);
        stage.addActor(btnShips);
        stage.addActor(btnWeapons);
        stage.addActor(btnPets);

        btnGems.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(state != MenuState.GEMS) {
                    state = MenuState.GEMS;
                    menuStateChanged();
                }
            }
        });

        btnWeapons.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(state != MenuState.WEAPONS) {
                    state = MenuState.WEAPONS;
                    menuStateChanged();
                }
            }
        });

        btnShips.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(state != MenuState.SHIPS) {
                    state = MenuState.SHIPS;
                    menuStateChanged();
                }
            }
        });

        btnPets.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(state!= MenuState.PETS) {
                    state = MenuState.PETS;
                    menuStateChanged();
                }
            }
        });


    }

    private void menuStateChanged(){
        switch (state){
            case GEMS:{
                btnGems.setStyle(onStyle);
                btnWeapons.setStyle(offStyle);
                btnShips.setStyle(offStyle);
                btnPets.setStyle(offStyle);

                scrollPane.setActor(gemsTable);
            }break;
            case WEAPONS:{
                btnGems.setStyle(offStyle);
                btnWeapons.setStyle(onStyle);
                btnShips.setStyle(offStyle);
                btnPets.setStyle(offStyle);

                scrollPane.setActor(weaponsTable);
            }break;
            case SHIPS:{
                btnGems.setStyle(offStyle);
                btnWeapons.setStyle(offStyle);
                btnShips.setStyle(onStyle);
                btnPets.setStyle(offStyle);

                scrollPane.setActor(shipsTable);
            }break;
            case PETS:{
                btnGems.setStyle(offStyle);
                btnWeapons.setStyle(offStyle);
                btnShips.setStyle(offStyle);
                btnPets.setStyle(onStyle);

                scrollPane.setActor(petsTable);
            }break;
        }

        if(scrollPane.getVelocityX() != 0)
            scrollPane.setVelocityX(0);
        scrollPane.setScrollX(0);


    }

    private void createShopBg() {
        shopBg = new Image(Assets.getInstance().assetManager.get(Assets.menu8,Texture.class));
//        shopBg.setSize(GameInfo.WIDTH - 60,btnHome.getY() - 60);
        shopBg.setSize(GameInfo.WIDTH - 60,GameInfo.HEIGHT - (btnHome.getY() + btnHome.getHeight()) - 60);
//        shopBg.setPosition(30,30);
        shopBg.setPosition(30,btnHome.getY() + btnHome.getHeight() + 30);
        stage.addActor(shopBg);
    }

    private void createCoinTable() {
        coinTable = new CoinTable();
        coinTable.setPosition(100,btnInfo.getY() + btnInfo.getHeight() / 2 - coinTable.getHeight() / 2);
        stage.addActor(coinTable);
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

//        btnInfo.setPosition(GameInfo.WIDTH - btnInfo.getWidth() * 2  - 10 - 100,
//                GameInfo.HEIGHT - btnInfo.getHeight() - 30);
        btnInfo.setPosition(GameInfo.WIDTH - btnInfo.getWidth() * 2  - 10 - 100,
                30);
        btnHome.setPosition(btnInfo.getX() + btnInfo.getWidth() + 10,btnInfo.getY());

        stage.addActor(btnHome);
        stage.addActor(btnInfo);
    }

    public void buyClicked(int price,String name,Object item,ShopItemPanel.ItemState state){
        if(User.user.coin >= price) {
            confirmationDialog.setMessage("Are you sure you want to buy " + name + " for " + price + "c ?",
                    Assets.getInstance().assetManager.get(Assets.fntSarani44, BitmapFont.class));
            confirmationDialog.show(stage);

            this.item = item;
            this.itemState = state;
            this.price = price;
        }else
            inadequateCoinDialog.show(stage);
    }

}

