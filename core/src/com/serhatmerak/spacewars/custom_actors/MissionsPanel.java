package com.serhatmerak.spacewars.custom_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.serhatmerak.spacewars.custom_actors.ButtonHoverAnimation;
import com.serhatmerak.spacewars.game_objects.GameObjectCollector;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.maps.Map1;
import com.serhatmerak.spacewars.maps.Map2;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 8.05.2018.
 */

public class MissionsPanel {

    public Table table;
    public Image menu1, menu2;
    public ScrollPane scrollPane;
    private Window.WindowStyle windowStyle;
    private Label label;
    public ImageButton closeBtn;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public MissionsPanel() {
        menu1 = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.menu1,Texture.class))));
        menu2 = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.menu2,Texture.class))));

        label = new Label(texts.get("missions"),
                new Label.LabelStyle(Assets.getInstance().assetManager.get(Assets.fntSarani44, BitmapFont.class),Color.WHITE));

        table = new Table();

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.vScroll = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar1,Texture.class)));
        scrollPaneStyle.vScrollKnob = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.scrollbar2,Texture.class)));
        scrollPane = new ScrollPane(table, scrollPaneStyle);
        scrollPane.setVariableSizeKnobs(false);
        scrollPane.setOverscroll(false, false);

        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.closeBtn,Texture.class)));

        closeBtn = new ImageButton(buttonStyle);
        closeBtn.setSize(150,150);
        closeBtn.addListener(new ButtonHoverAnimation(closeBtn));

        closeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeBtn.remove();
                menu1.remove();
                scrollPane.remove();
                label.remove();
            }
        });

        createWindow();

    }

    private void createWindow() {
        windowStyle = new Window.WindowStyle();
        windowStyle.background = menu2.getDrawable();
        windowStyle.titleFont = Assets.getInstance().assetManager.get(Assets.fntSarani44);
    }


    public void setPosition(float x, float y) {
        menu1.setPosition(x, y);
        scrollPane.setPosition(x + 40, y + 50);
        label.setPosition(x + menu1.getWidth() / 2,y + menu1.getHeight() - 30,Align.center);
        closeBtn.setPosition(x + menu1.getWidth() - 120,y + menu1.getHeight() - 120);
    }

    public void setSize(float width, float height) {
        menu1.setSize(width, height);
        scrollPane.setSize(width - 80, height - 200);
        table.setSize(scrollPane.getWidth(), scrollPane.getHeight());
    }

    public void addToStage(Stage stage) {
        stage.addActor(menu1);
        stage.addActor(scrollPane);
        stage.addActor(label);
        stage.addActor(closeBtn);
    }



    public void addMission(Mission mission) {
        table.add(new MissionTable(mission)).padRight(40).padTop(20).row();
    }
}
