package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.huds.ShopHuds;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 24.07.2018.
 */

public class ShopItemPanel extends Group{

    public Image background;
    public enum ItemState{
        GEM,WEAPON,PET,SHIP;
    }
    public ItemState itemState;

    private Label title;
    private TextButton btnInfo,btnBuy;
    private ShopItemInfo shopItemInfo;
    public ShopHuds shopHuds;

    private int price;
    private String name;
    private Object item;

    public ShopItemPanel(){
        background = new Image(Assets.getInstance().assetManager.get(Assets.menu1, Texture.class));
        background.setSize(400,600);
        setSize(background.getWidth(),background.getHeight());


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);

        title = new Label("",labelStyle);
        title.setWidth(300);
        title.setPosition(50,background.getHeight() - 30);
        title.setAlignment(Align.center);

        createButtons();

        addActor(background);
        addActor(title);
        addActor(btnInfo);
        addActor(btnBuy);

        btnInfo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getStage().addActor(shopItemInfo);
            }
        });

        btnBuy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(price != 0)
                    shopHuds.buyClicked(price,name,item,itemState);
            }
        });


    }

    private void createButtons() {
        TextButton.TextButtonStyle infoStyle = new TextButton.TextButtonStyle();
        infoStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
        infoStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnNorm,Texture.class)));
        infoStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnHover,Texture.class)));
        infoStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.yellowBtnClicked,Texture.class)));

        btnInfo = new TextButton("INFO",infoStyle);
        btnInfo.setSize(240,240 * (btnInfo.getHeight() / btnInfo.getWidth()));
        btnInfo.setPosition(getWidth() / 2 - btnInfo.getWidth() / 2,30 + btnInfo.getHeight() + 10);

        TextButton.TextButtonStyle buyStyle = new TextButton.TextButtonStyle();
        buyStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
        buyStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.greenBtnNorm,Texture.class)));
        buyStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.greenBtnHover,Texture.class)));
        buyStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.greenBtnClicked,Texture.class)));

        btnBuy = new TextButton("BUY",buyStyle);
        btnBuy.setSize(240,240 * (btnInfo.getHeight() / btnInfo.getWidth()));
        btnBuy.setPosition(getWidth() / 2 - btnInfo.getWidth() / 2,30 );
    }

    public void setItem(Gem.GemStyle gem,int price){
        itemState = ItemState.GEM;
        item = gem;
        this.price = price;
        name = gem.name;
        title.setText(gem.name);

        Box box = new Box();
        box.setItem(new Gem(gem), Box.ItemType.GEM);
        box.enabled = false;
        box.showTxt = true;

        box.setPosition(getWidth() / 2 - box.getWidth() / 2, title.getY() - 60 - box.getHeight());

        btnBuy.setText(price + " c\nbuy");
        addActor(box);

        shopItemInfo = new ShopItemInfo(gem);
    }

    public void setItem(Weapon.WeaponStyle weaponStyle, int price){
        itemState = ItemState.WEAPON;
        item = weaponStyle;
        this.price = price;
        name = weaponStyle.name;
        title.setText(weaponStyle.name);

        Box box = new Box();
        box.setItem(new Weapon(weaponStyle), Box.ItemType.WEAPON);
        box.enabled = false;
        box.showTxt = true;

        box.setPosition(getWidth() / 2 - box.getWidth() / 2, title.getY() - 60 - box.getHeight());

        btnBuy.setText(price + " c\nbuy");
        addActor(box);
        shopItemInfo = new ShopItemInfo(weaponStyle);

        Label.LabelStyle labelStyle = title.getStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani24);
        title.setStyle(labelStyle);
    }

    public void setItem(SpaceShipStyle shipStyle, int price){
        itemState = ItemState.SHIP;
        item = shipStyle;
        this.price = price;
        name = shipStyle.name();
        title.setText(shipStyle.name());

        Image image = new Image(Assets.getInstance().assetManager.get(shipStyle.shipTexture,Texture.class));
        image.setPosition(getWidth() / 2 - image.getWidth() / 2, title.getY() - 60 - image.getHeight());

        if(GameManager.gameManager.isAvailable(shipStyle)){
            btnBuy.setText("already\nhave");
            this.price = 0;

        }else
            btnBuy.setText(price + " c\nbuy");
        addActor(image);
        shopItemInfo = new ShopItemInfo(shipStyle);
    }

    public void setItem(PetStyle petStyle, int price){
        itemState = ItemState.PET;
        item = petStyle;
        this.price = price;
        name = petStyle.name();
        title.setText(petStyle.name());

        Image image = new Image(Assets.getInstance().assetManager.get(petStyle.petTexture,Texture.class));
        image.setSize(image.getWidth() * 0.7f,image.getHeight() * 0.7f);
        image.setPosition(getWidth() / 2 - image.getWidth() / 2, title.getY() - 60 - image.getHeight());

        if(GameManager.gameManager.isAvailable(petStyle)) {
            btnBuy.setText("already\nhave");
            this.price = 0;
        }else
            btnBuy.setText(price + " c\nbuy");
        addActor(image);
        shopItemInfo = new ShopItemInfo(petStyle);
    }
}
