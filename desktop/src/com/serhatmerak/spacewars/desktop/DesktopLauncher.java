package com.serhatmerak.spacewars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.serhatmerak.spacewars.GameMain;
import com.serhatmerak.spacewars.helpers.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameInfo.WIDTH / 2;
		config.height = GameInfo.HEIGHT / 2;
//		config.fullscreen = true;
		new LwjglApplication(new GameMain(), config);
	}
}
