package com.piece12.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.piece12.game.States.GameStateManager;
import com.piece12.game.States.MenuState;

//This is the class that creates the game
public class Piece12 extends ApplicationAdapter {
	//properties
	public static final int WIDTH = 800;
	public static final int HEIGHT = 400;
	public static final String TITLE = "Piece12";
	private GameStateManager gsm;
	public static Music music;
	private Preferences prefs;
	public static Sound buttonSound;
	SpriteBatch batch;
	//methods
	//create method creates a new game
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		prefs = Gdx.app.getPreferences("Settings");
		if( prefs.getBoolean( "musicOn", true) ){
			music.play();
		}
		buttonSound = Gdx.audio.newSound( Gdx.files.internal("click.wav") );
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}
	//this method keeps updating the game
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	//this method works like a destructor
	@Override
	public void dispose() {
		super.dispose();
		buttonSound.dispose();
		music.dispose();
	}
}
