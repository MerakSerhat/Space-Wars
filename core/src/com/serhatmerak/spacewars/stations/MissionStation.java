package com.serhatmerak.spacewars.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.helpers.Assets;

/**
 * Created by Serhat Merak on 27.05.2018.
 */

public class MissionStation extends Station {

    public MissionStation(GameMain game, Vector2 position) {
        super(game, position);

        sprite = new Sprite(Assets.getInstance().assetManager.get(Assets.mission, Texture.class));
        sprite.setPosition(position.x - sprite.getWidth() / 2,position.y - sprite.getHeight() / 2);

        Pixmap pixmap = new Pixmap(6,18, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0,0,6,3);
        pixmap.fillRectangle(0,5,6,12);

        icon = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        sprite.draw(batch);
    }

    @Override
    public void update() {
        super.update();
        sprite.rotate(1);
    }

    @Override
    public void setActive(boolean b) {
        if(b){
            gamePlay.huds.showMissionPanel();
        }
    }
}
