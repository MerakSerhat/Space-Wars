package com.serhatmerak.spacewars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.custom_actors.Toast;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.helpers.GameInfo;
import com.serhatmerak.spacewars.huds.PvPHuds;
import com.serhatmerak.spacewars.huds.ReinforcementsHuds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jdk.nashorn.api.scripting.JSObject;

/**
 * Created by Serhat Merak on 28.07.2018.
 */

public class PvPScreen extends CustomScreen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameMain game;
    private PvPHuds huds;
    private Texture backgroundBlack;

    private Socket socket;
    private boolean changeScreen = false;
    private String roomId;
    private boolean builder;
    public PvPScreen(GameMain game){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);
        viewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);
        huds = new PvPHuds(batch);

        backgroundBlack = Assets.getInstance().assetManager.get(Assets.backgroundBlack);


        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(huds.stage);

        connectToServer();
        hudsListeners();


    }

    private void hudsListeners() {
        huds.joinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(huds.textField.getText().length() == 6) {
                    socket.emit("joinToRoom"
                            , huds.textField.getText().toUpperCase());
                }else {
                    huds.stage.addActor(new Toast("Room id has to be 6 digits",2).show());
                }


            }
        });

        huds.createRoom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                socket.emit("createRandomRoom");
            }
        });
    }

    private void connectToServer() {
        try {

            socket = IO.socket("http://localhost:8080");
            socket.connect().on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connected");
                }
            }).on("socketID", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];

                    try {
                        String id = jsonObject.getString("id");
                        System.out.println(" id= " + id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }).on("joinedToRoom", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    builder = false;
                    try {
                        roomId = jsonObject.getString("roomId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    changeScreen = true;

                }
            }).on("roomCreated", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        roomId = jsonObject.getString("roomId");
                        changeScreen = true;
                        builder = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("roomNotFound", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    huds.stage.addActor(new Toast("Room not found",2).show());
                }
            }).on("roomIsFull", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    huds.stage.addActor(new Toast("Room is full",2).show());
                }
            });;


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawBackgroundBlack();
        batch.end();
        huds.stage.act();
        huds.stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new MainMenuScreen(game));
        }

        if(changeScreen)
            game.setScreen(new PvPRoomScreen(game,roomId,builder));

        if(Gdx.input.isKeyPressed(Input.Keys.B))
            game.setScreen(new PvPWarScreen(game,true));
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
