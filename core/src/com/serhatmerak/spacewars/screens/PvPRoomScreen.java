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
import com.serhatmerak.spacewars.huds.PvPRoomHuds;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Serhat Merak on 30.07.2018.
 */

public class PvPRoomScreen extends CustomScreen {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameMain game;
    private PvPRoomHuds huds;
    private Texture backgroundBlack;


    private boolean builder;
    private String otherPlayerId;
    public String roomId;
    private Socket socket;
    private boolean goToPvpScreen = false;

    public PvPRoomScreen(GameMain game,String roomId,boolean builder){
        this.game = game;
        this.roomId = roomId;
        this.builder = builder;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WIDTH,GameInfo.HEIGHT);
        viewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);
        huds = new PvPRoomHuds(batch);
        huds.roomIdLabel.setText(roomId);

        backgroundBlack = Assets.getInstance().assetManager.get(Assets.backgroundBlack);


        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(huds.stage);

        connectToServer();
        if(builder)
            huds.nameLabel1.setText(socket.id());
        else {
            huds.nameLabel2.setText(socket.id());
            socket.emit("getOtherPlayer",roomId);
        }
    }


    private void connectToServer() {
        try {

            socket = IO.socket("http://localhost:8080");
            socket.on("newPlayerJoined", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        otherPlayerId = jsonObject.getString("playerId");
                        huds.nameLabel2.setText(otherPlayerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("getOtherPlayer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject jsonObject = (JSONObject) args[0];
                    try {
                        otherPlayerId = jsonObject.getString("otherPlayerId");
                        huds.nameLabel1.setText(otherPlayerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("roomClosed", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    goToPvpScreen = true;
                }
            }).on("playerLeft", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    otherPlayerId = "";
                    huds.nameLabel2.setText("");
                }
            });


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
            socket.emit("leftFromRoom");
            game.setScreen(new PvPScreen(game));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.B))
            game.setScreen(new PvPWarScreen(game,builder));

        if(goToPvpScreen)
            game.setScreen(new PvPScreen(game));
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
