package com.serhatmerak.spacewars.custom_actors;

/**
 * Created by Serhat Merak on 30.05.2018.
 */

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Serhat Merak on 25.02.2018.
 */

public class ButtonHoverAnimation extends ClickListener {
    private ImageButton button;

    public ButtonHoverAnimation(ImageButton button){
        this.button = button;
        button.setTransform(true);
        button.setOrigin(button.getWidth() / 2,button.getHeight() / 2);
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        button.addAction(Actions.repeat(3,Actions.scaleBy(0.05f,0.05f)));
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        button.addAction(Actions.repeat(3,Actions.scaleBy(-0.05f,-0.05f)));
    }
}
