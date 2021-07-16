package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Serhat Merak on 31.05.2018.
 */

public class CustomDialog extends Dialog{


    public TextButton btnYes,btnNo,btnOk;
    private Image darkBackground;
    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);
    private Label messageLabel;

    public CustomDialog(WindowStyle windowStyle) {
        super("",windowStyle);
        getTitleLabel().setAlignment(Align.center);
        setModal(true);
        Sprite sprite = new Sprite(
                Assets.getInstance().assetManager.get(Assets.menu5, Texture.class));
        sprite.setSize(800,800 * (sprite.getHeight() / sprite.getWidth()));
        setBackground(new SpriteDrawable(sprite));
        getTitleTable().getCells().first().padTop(60);


        darkBackground = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.pix,Texture.class))));
        darkBackground.setSize(GameInfo.WIDTH,GameInfo.HEIGHT);
        darkBackground.setColor(0,0,0,0.7f);
    }

    public void setMessage(String message){
        setMessage(message,Assets.getInstance().assetManager.get(Assets.fntSarani34,BitmapFont.class));
    }

    public void setMessage(String message, BitmapFont font){
        if(messageLabel == null) {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;

            messageLabel = new Label(message, labelStyle);
            messageLabel.setWrap(true);
            getContentTable().row().colspan(2).center();
            getContentTable().add(messageLabel).width(getPrefWidth() - 60).center().padTop(80).row();
        }else {
            messageLabel.setText(message);
        }
    }

    public void setOkButton(){
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        textButtonStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));
        textButtonStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked,Texture.class)));

        btnOk = new TextButton("ok",textButtonStyle);
        btnOk.setSize(400,400 / btnOk.getWidth() * btnOk.getHeight());
        button(btnOk).sizeBy(btnOk.getWidth(),btnOk.getHeight());

        btnOk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
    }

    public void setConfirmationButtons(){
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Assets.getInstance().assetManager.get(Assets.fntSarani44);
        textButtonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnNorm,Texture.class)));
        textButtonStyle.over = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnHover,Texture.class)));
        textButtonStyle.down = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.blueBtnClicked,Texture.class)));

        btnYes = new TextButton(texts.get("yes"),textButtonStyle);
        btnNo = new TextButton(texts.get("no"),textButtonStyle);
        btnYes.setSize(400,400 / btnYes.getWidth() * btnYes.getHeight());
        btnNo.setSize(400,400 / btnNo.getWidth() * btnNo.getHeight());
        button(btnYes).sizeBy(btnYes.getWidth(),btnYes.getHeight());
        button(btnNo).sizeBy(btnNo.getWidth(),btnNo.getHeight());

        btnYes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        btnNo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });
    }

    @Override
    public Dialog button(Button button) {
        getButtonTable().add(button).width(400).height(400 / button.getWidth() * button.getHeight());

        return this;
    }

    @Override
    public void hide() {
        super.hide();
        darkBackground.clearActions();
        darkBackground.addAction(sequence(Actions.fadeOut(0.4f),Actions.removeActor()));
    }

    @Override
    public Dialog show(Stage stage) {
        stage.addActor(darkBackground);
        darkBackground.addAction(sequence(Actions.alpha(0),Actions.alpha(0.7f,0.4f)));

        super.show(stage);
        return this;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getZIndex() < darkBackground.getZIndex()) {
            darkBackground.setZIndex(darkBackground.getZIndex() - 1);
            setZIndex(getZIndex() + 1);
        }

    }
}
