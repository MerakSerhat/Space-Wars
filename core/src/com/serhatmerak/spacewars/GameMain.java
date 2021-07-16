package com.serhatmerak.spacewars;


import com.badlogic.gdx.Game;
import com.serhatmerak.spacewars.database.GameManager;
import com.serhatmerak.spacewars.helpers.Assets;
import com.serhatmerak.spacewars.screens.SplashScreen;

public class GameMain extends Game {


	@Override
	public void create () {
		Assets.getInstance().create();
		setScreen(new SplashScreen(this));
	}

	

}
