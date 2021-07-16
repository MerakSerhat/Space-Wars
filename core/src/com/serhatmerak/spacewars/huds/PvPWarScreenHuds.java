package com.serhatmerak.spacewars.huds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.custom_actors.CurrentMissionPanel;
import com.serhatmerak.spacewars.custom_actors.CustomDialog;
import com.serhatmerak.spacewars.custom_actors.MissionTable;
import com.serhatmerak.spacewars.custom_actors.MissionsPanel;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.game_objects.Mission;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.ButtonHoverAnimation;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.helpers.MissionController;
import com.serhatmerak.spacewars.helpers.MissionDatas;

/**
 * Created by Serhat Merak on 31.07.2018.
 */

public class PvPWarScreenHuds {

    public Stage stage;
    public Touchpad touchpad, attackpad;
    public final float dedzoneRadius = 20f;
    public Image redAlert;

    public ImageButton portal_btn;
    public boolean portalBtnEnabled = false;

    public boolean border_warning;
    public Label positionLabel;
    private Array<Label> labels;
    private MissionsPanel missionsPanel;
    private CustomDialog overrideMissionDlg;
    public CurrentMissionPanel currentMissionPanel;
    public MissionController missionController;

    private I18NBundle texts = Assets.getInstance().assetManager.get(Assets.texts);

    public PvPWarScreenHuds(SpriteBatch batch) {
        stage = new Stage(new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT), batch);
        labels = new Array<Label>();
        createRedAlert();
        createTouchPad();
        createAttackPad();

    }

    private void createAttackPad() {
        Touchpad.TouchpadStyle attackpadStyle = new Touchpad.TouchpadStyle();
        attackpadStyle.knob = new SpriteDrawable(new Sprite(new Texture("padknock.png")));

        attackpad = new Touchpad(dedzoneRadius, attackpadStyle);
        attackpad.setBounds(GameInfo.WIDTH - 425, 125, 300, 300);

        stage.addActor(attackpad);
    }
    private void createRedAlert() {
        redAlert = new Image(new SpriteDrawable(new Sprite(Assets.getInstance().assetManager.get(Assets.pix, Texture.class))));
        redAlert.setBounds(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        redAlert.setColor(1, 0, 0, 0.0f);
        stage.addActor(redAlert);

    }
    public void showRedAlert() {
        redAlert.addAction(Actions.forever(new SequenceAction(Actions.alpha(0f, 0.5f)
                , Actions.alpha(0.5f, 0.5f))));
        border_warning = true;
    }
    public void removeRedAlert() {
        redAlert.removeAction(redAlert.getActions().first());
        redAlert.setColor(1, 0, 0, 0);
        border_warning = false;
    }
    private void createTouchPad() {
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = new SpriteDrawable(new Sprite(new Texture("pad bg.png")));
        touchpadStyle.knob = new SpriteDrawable(new Sprite(new Texture("padknock.png")));

        touchpad = new Touchpad(dedzoneRadius, touchpadStyle);
        touchpad.setPosition(125, 125);

        stage.addActor(touchpad);
    }
    public void showToast(String text) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assets.getInstance().assetManager.get(Assets.fntAerial);

        final Label label = new Label(text, labelStyle);
        label.addAction(new SequenceAction(Actions.delay(1.5f), Actions.fadeOut(0.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        labels.removeValue(label,true);
                        float y = GameInfo.HEIGHT - 25;
                        for (Label label1:labels){
                            y -= label1.getHeight();
                            label1.setPosition(label1.getX(),y);

                        }
                    }}),Actions.removeActor()));
        label.setWrap(true);
        label.setWidth(GameInfo.WIDTH / 2);
        label.setAlignment(Align.center);
        float y = GameInfo.HEIGHT - 25 - label.getHeight();
        for(Label label1:labels)
            y -= label1.getHeight();

        label.setPosition(GameInfo.WIDTH / 2 - label.getWidth() / 2, y);

        labels.add(label);
        stage.addActor(label);

    }
}
