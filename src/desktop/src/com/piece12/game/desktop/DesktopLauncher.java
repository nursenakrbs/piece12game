package com.piece12.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.piece12.game.Piece12;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Piece12.WIDTH;
		config.height = Piece12.HEIGHT;
		config.title = Piece12.TITLE;
		new LwjglApplication(new Piece12(), config);
	}
}