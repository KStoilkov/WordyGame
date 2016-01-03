package com.kstoilkov.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kstoilkov.game.WordyMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wordy";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new WordyMain(), config);
	}
}
