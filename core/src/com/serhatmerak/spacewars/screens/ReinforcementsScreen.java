package com.serhatmerak.spacewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.huds.ReinforcementsHuds;


/**
 * Created by Serhat Merak on 23.04.2018.
 */

public class ReinforcementsScreen extends CustomScreen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameMain game;
    private ReinforcementsHuds huds;
    private Texture backgroundBlack;

    public ReinforcementsScreen(GameMain game){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);
        viewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);

        backgroundBlack = Assets.getInstance().assetManager.get(Assets.backgroundBlack);
        huds = new ReinforcementsHuds(viewport,batch);


        Gdx.input.setInputProcessor(huds.stage);
        Gdx.input.setCatchBackKey(true);

        addButtonListeners();


    }

    private void addButtonListeners() {
        huds.btnHome.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
            drawBackgroundBlack();
        batch.end();
        batch.setColor(Color.WHITE);
        huds.stage.act();
        huds.stage.draw();
        batch.setColor(Color.WHITE);


        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void drawBackgroundBlack() {
        batch.setColor(1, 1, 1, 0.8f);
        for (int i = 0; i < GameInfo.WIDTH / GameInfo.PPM; i++) {
            for (int j = 0; j < GameInfo.HEIGHT / GameInfo.PPM; j++) {
                batch.draw(backgroundBlack,i * GameInfo.PPM,j * GameInfo.PPM,GameInfo.PPM,GameInfo.PPM);
            }
        }
        batch.setColor(Color.WHITE);
    }
}
