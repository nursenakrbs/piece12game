package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;
import com.sun.jndi.toolkit.url.Uri;

public abstract class State {
    //Properties
    protected OrthographicCamera cam;
    protected Vector3 tmp;
    protected GameStateManager gsm;
    protected Preferences prefs;
    protected Texture background;
    protected Texture logo;
    private float x;
    private float y;

    //constructor
    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Piece12.WIDTH, Piece12.HEIGHT);
        cam.position.set(Piece12.WIDTH / 2, Piece12.HEIGHT / 2, 0);
        cam.update();
        x = Gdx.graphics.getWidth();
        y = Gdx.graphics.getHeight();
        prefs = Gdx.app.getPreferences("Settings");
        background = new Texture("background.png");
        logo = new Texture("logo_large.png");
        prefs = Gdx.app.getPreferences("Settings");
    }

    //abstract methods
    //This method decides what to do with an input
    public abstract void handleInput();
    public abstract void update(float dt);

    //This method draws the images on the screen
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

    //navigational path helper methods
    public void goNumberState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new NumberState(gsm));
    }
    public void goMenuState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        while(gsm.isEmpty()==false)
            gsm.pop();
        gsm.push(new MenuState(gsm));
    }
    public void goModeState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new ModeState(gsm));
    }
    public void goCreditsState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new CreditsState(gsm));
    }
    public void goBack(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.pop();
    }
    public void goLeaderboardState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new LeaderboardState(gsm));
    }
    public void goShopState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new ShopState(gsm));
    }
    public void goHowtoPlayState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new HowtoPlayState(gsm));
    }
    public void goLevelMapState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new LevelMapState(gsm));
    }
    public void goPlayState( Level level ){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new PlayState(gsm,level));
    }
    public void goSettingsState(){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new SettingsState(gsm));
    }
    public void goFindOpponentState() {
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new FindOpponentState(gsm));
    }
    public void goPauseState( Level level ){
        if( prefs.getBoolean("soundOn", true ) ){
            Piece12.buttonSound.play();
        }
        gsm.push(new PauseState(gsm, level));
    }
    public void goMarket(){
        Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.piece12.game");
    }
    public void goWinState( Level level){
        gsm.push(new WinState(gsm,level));
    }
    public void goFailureState( Level level){
        gsm.push(new FailureState(gsm,level));
    }
    public Vector3 fixCoordinates( Vector3 v ){
        v.x = v.x * 800 / x;
        v.y = v.y * 400 / y;
        return v;
    }
}
