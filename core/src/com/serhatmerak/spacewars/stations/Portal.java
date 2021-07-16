package com.serhatmerak.spacewars.stations;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.maps.Base;
import com.serhatmerak.spacewars.maps.Map;
import com.serhatmerak.spacewars.maps.Map1;
import com.serhatmerak.spacewars.maps.Map2;
import com.serhatmerak.spacewars.maps.Map3;
import com.serhatmerak.spacewars.maps.Map4;
import com.serhatmerak.spacewars.maps.Map5;
import com.serhatmerak.spacewars.maps.Map6;
import com.serhatmerak.spacewars.maps.Map7;
import com.serhatmerak.spacewars.maps.Map8;
import com.serhatmerak.spacewars.screens.GamePlay;

/**
 * Created by Serhat Merak on 25.04.2018.
 */

public class Portal extends Station{
    private GameMain game;

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean active;
    public Class<? extends Object> nextScreen;
    public Class<? extends Object> currentScreen;


    public Portal(GameMain game, Vector2 position, Class<? extends Object> nextScreen){
        super(game,position);
        this.game = game;
        this.nextScreen = nextScreen;
        sprite = new Sprite(Assets.getInstance().assetManager.get(Assets.portal,Texture.class));
        sprite.setSize(sprite.getWidth() * 2 / 3,sprite.getHeight() * 2 / 3);
        sprite.setOrigin(sprite.getWidth() / 2,sprite.getHeight() / 2);
        sprite.setPosition(position.x - sprite.getWidth() / 2,position.y - sprite.getHeight() / 2);

        animation = new Animation<TextureRegion>(Gdx.graphics.getDeltaTime() * 4,
                Assets.getInstance().assetManager.get(Assets.portal_animation, TextureAtlas.class).getRegions());

        Pixmap pixmap = new Pixmap(28,28, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawCircle(13,13,13);
        pixmap.drawCircle(13,13,12);
        pixmap.drawCircle(13,13,11);
        pixmap.fillCircle(13,13,4);

        icon = new Texture(pixmap);
        pixmap.dispose();

    }

    @Override
    public void update(){
        super.update();
        if(active) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            sprite.rotate(elapsedTime * 10);

            if(animation.isAnimationFinished(elapsedTime - 1f))
                changeScreen();
        }
    }
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        sprite.draw(batch);

        if(active && elapsedTime > 1)
            batch.draw(animation.getKeyFrame(elapsedTime - 1),sprite.getX() + sprite.getWidth() / 2 - animation.getKeyFrame(elapsedTime - 1).getRegionWidth() / 2,
                    sprite.getY() + sprite.getHeight() / 2 - animation.getKeyFrame(elapsedTime - 1).getRegionHeight() / 2);

    }

    public void changeScreen(){
        Map nextMap = new Map(game,currentScreen);

        if(nextScreen == Map1.class){
            nextMap = new Map1(game,currentScreen);
        }else if(nextScreen == Map2.class)
            nextMap = new Map2(game,currentScreen);
        else if(nextScreen == Map3.class)
            nextMap = new Map3(game,currentScreen);
        else if(nextScreen == Map4.class)
            nextMap = new Map4(game,currentScreen);
        else if(nextScreen == Map5.class)
            nextMap = new Map5(game,currentScreen);
        else if(nextScreen == Map6.class)
            nextMap = new Map6(game,currentScreen);
        else if(nextScreen == Map7.class)
            nextMap = new Map7(game,currentScreen);
        else if(nextScreen == Map8.class)
            nextMap = new Map8(game,currentScreen);
        else if (nextScreen == Base.class)
            nextMap = new Base(game,currentScreen);




        game.setScreen(new GamePlay(game,nextMap));


    }

    public Portal setIconColor(Color color){
        this.color = color;
        return this;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
