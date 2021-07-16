package com.serhatmerak.spacewars.screens;

import com.badlogic.gdx.math.MathUtils;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.User;
import com.serhatmerak.spacewars.game_objects.Gem;
import com.serhatmerak.spacewars.game_objects.Weapon;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.helpers.CustomScreen;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.helpers.MissionDatas;
import com.serhatmerak.spacewars.pets.PetData;
import com.serhatmerak.spacewars.pets.PetStyle;
import com.serhatmerak.spacewars.ships.SpaceShipStyle;

/**
 * Created by Serhat Merak on 18.03.2018.
 */

public class SplashScreen extends CustomScreen {
    private GameMain game;

    public SplashScreen(GameMain game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Assets.getInstance().assetManager.update();

        //TODO:Veritabanından çek ve öyle yerleştir
        if(Assets.getInstance().assetManager.isLoaded(Assets.pix)) {
            GameManager.gameManager.initalize();
            Assets.getInstance().setFilterToTextures();
            MissionDatas.missionDatas.create();
            User.user.create();




//            for(int i = 0;i<1000;i++)
//                User.user.gemBag.add(new Gem(Gem.GemStyle.values()[MathUtils.random(0, Gem.GemStyle.values().length - 1)]));
//
//            for(int i = 0;i<1000;i++)
//                User.user.weaponBag.add(new Weapon(Weapon.WeaponStyle.values()[MathUtils.random(0, Weapon.WeaponStyle.values().length - 1)]));
//




            game.setScreen(new MainMenuScreen(game));
        }
    }
}
