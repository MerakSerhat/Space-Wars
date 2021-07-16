package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.custom_actors.CoinTable;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;

/**
 * Created by Serhat Merak on 28.07.2018.
 */

public class PvPHuds {
    public Stage stage;
    public TextField textField;
    public TextButton createRoom,joinRoom;

    public PvPHuds(SpriteBatch batch) {
        stage = new Stage(new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT), batch);
        createTextField();
    }

    private void createTextField() {
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"),new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani34);
        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        textButtonStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));
        textButtonStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked,Texture.class)));


        joinRoom = new TextButton("Join to Room",textButtonStyle);
        joinRoom.setSize(600, 600 * (joinRoom.getHeight() / joinRoom.getWidth()));
        joinRoom.setPosition(GameInfo.WIDTH - 30 - joinRoom.getWidth(),
                GameInfo.HEIGHT / 2 - joinRoom.getHeight() / 2);

        createRoom = new TextButton("Create Room",textButtonStyle);
        createRoom.setSize(600,600 * (joinRoom.getHeight() / joinRoom.getWidth()));
        createRoom.setPosition(30,GameInfo.HEIGHT / 2 - createRoom.getHeight() / 2);

        stage.addActor(joinRoom);
        stage.addActor(createRoom);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        Sprite sprite = new Sprite(new Texture("gui/menus/textField.png"));
        textFieldStyle.background = new SpriteDrawable(sprite);
        textFieldStyle.fontColor = Color.WHITE;

        textFieldStyle.cursor = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.pix,Texture.class)));
        Sprite selectionSprite = new Sprite(Assets.getInstance().assetManager.get(Assets.pix,Texture.class));
        selectionSprite.setColor(Color.valueOf("007f7f"));
        textFieldStyle.selection = new SpriteDrawable(selectionSprite);

        textField = new TextField("",textFieldStyle);
        textField.setSize(600,175);
        textField.setPosition(GameInfo.WIDTH - 30 - textField.getWidth(),
                joinRoom.getY() + joinRoom.getHeight() + 10);
        textField.setAlignment(Align.center);
        textField.setMaxLength(15);

        stage.addActor(textField);


    }


}
