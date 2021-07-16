package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;

/**
 * Created by Serhat Merak on 30.07.2018.
 */

public class PvPRoomHuds {
    public Stage stage;
    public Image nameMenu1,nameMenu2;
    public Label nameLabel1,nameLabel2;
    public Image roomIdMenu;
    public Label roomIdLabel;


    public PvPRoomHuds(SpriteBatch batch) {
        stage = new Stage(new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT), batch);
        createNameActors();
        createRoomActors();
    }

    private void createRoomActors() {
        roomIdMenu = new Image(Assets.getInstance().assetManager.get(Assets.menu6,Texture.class));
        roomIdMenu.setSize(400,400 * (roomIdMenu.getHeight() / roomIdMenu.getWidth()));
        roomIdMenu.setPosition(GameInfo.WIDTH / 2 - roomIdMenu.getWidth() / 2,nameMenu1.getY());
        stage.addActor(nameMenu1);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        roomIdLabel = new Label("",labelStyle);
        roomIdLabel.setWidth(roomIdMenu.getWidth());
        roomIdLabel.setAlignment(Align.center);
        roomIdLabel.setPosition(roomIdMenu.getX(),roomIdMenu.getY() + 30);

        stage.addActor(roomIdLabel);
    }

    private void createNameActors() {
        SpriteDrawable nameMenuDrawable = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.menu11,Texture.class)));
        nameMenu1 = new Image(nameMenuDrawable);
        nameMenu2 = new Image(nameMenuDrawable);

        nameMenu1.setSize(700,700 * (nameMenu1.getHeight() / nameMenu1.getWidth()));
        nameMenu2.setSize(nameMenu1.getWidth(),nameMenu1.getHeight());

        nameMenu1.setPosition(30,GameInfo.HEIGHT - 30 - nameMenu1.getHeight());
        nameMenu2.setPosition(GameInfo.WIDTH - 30 - nameMenu2.getWidth(),nameMenu1.getY());

        stage.addActor(nameMenu1);
        stage.addActor(nameMenu2);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);

        nameLabel1 = new Label("",labelStyle);
        nameLabel2 = new Label("",labelStyle);

        nameLabel1.setWidth(700);
        nameLabel2.setWidth(700);

        nameLabel1.setAlignment(Align.center);
        nameLabel2.setAlignment(Align.center);

        nameLabel1.setPosition(nameMenu1.getX(),nameMenu1.getY() + 30);
        nameLabel2.setPosition(nameMenu2.getX(),nameMenu2.getY() + 30);

        stage.addActor(nameLabel1);
        stage.addActor(nameLabel2);
    }


}
