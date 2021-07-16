package com.serhatmerak.spacewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.TEST.TestScreen;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.huds.MainMenuHuds;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * Created by Serhat Merak on 7.06.2018.
 */

public class MainMenuScreen extends CustomScreen {

    private GameMain game;
    private Texture backgroundBlack;
    private SpriteBatch batch;
    private StretchViewport viewport;
    private OrthographicCamera camera;

    private MainMenuHuds huds;

    private Socket socket;


    public MainMenuScreen(GameMain game){
        this.game = game;
        backgroundBlack = Assets.getInstance().assetManager.get(Assets.backgroundBlack);
        camera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        camera.setToOrtho(false,GameInfo.WIDTH,GameInfo.HEIGHT);
        viewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);
        batch = new SpriteBatch();

        huds = new MainMenuHuds(batch);
        addButtonListeners();

        Gdx.input.setInputProcessor(huds.stage);

//        connectSocket();
    }

    private void connectSocket() {
//        try {
//            socket = IO.socket("http://localhost:8080");
//            socket.connect().on("sa", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    System.out.println(args[0].toString());
//                }
//            });
//
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }

    private void addButtonListeners() {
        huds.btnFree.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TestScreen(game));
            }
        });

        huds.btnHangar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HangarScreen(game));
            }
        });

        huds.btnLab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LabScreen(game));
            }
        });

        huds.btnShop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ShopScreen(game));
            }
        });

        huds.btnPVP.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PvPScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        {
            drawBackgroundBlack();
        }
        batch.end();

        huds.stage.draw();
        huds.stage.act();

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            User.user.gems.set(0,null);

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

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}
