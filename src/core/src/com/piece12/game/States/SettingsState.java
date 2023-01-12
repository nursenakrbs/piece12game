package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Piece12;

public class SettingsState extends State {

    //properties
    private Texture backBtn;
    private Texture musicText;
    private Texture soundText;
    private Texture notificationsText;
    private Texture musicBtn;
    private Texture prefText;

    private BitmapFont onText;
    private BitmapFont offText;
    private BitmapFont onText1;
    private BitmapFont offText1;
    private BitmapFont onText2;
    private BitmapFont offText2;

    private Rectangle musicOnBounds;
    private Rectangle musicOffBounds;
    private Rectangle soundOnBounds;
    private Rectangle soundOffBounds;
    private Rectangle notificationsOnBounds;
    private Rectangle notificationsOffBounds;
    private Rectangle backBounds;

    //constructors
    public SettingsState( GameStateManager gsm ){
        super( gsm );

        backBtn = new Texture("backbtn.png");
        musicText = new Texture("musictext.png");
        soundText = new Texture("soundtext.png");
        notificationsText = new Texture("notificationstext.png");
        musicBtn = new Texture("onoffbtn.png");
        prefText = new Texture("preferencestext.png");
        onText = new BitmapFont();
        offText = new BitmapFont();
        onText1 = new BitmapFont();
        offText1 = new BitmapFont();
        onText2 = new BitmapFont();
        offText2 = new BitmapFont();
        prefs = Gdx.app.getPreferences("Settings");
        if( prefs.getBoolean("musicOn", true ) ){
            onText.setColor(Color.SKY);
            offText.setColor(Color.WHITE);
        }
        else{
            offText.setColor(Color.SKY);
            onText.setColor(Color.WHITE);
        }
        if( prefs.getBoolean("soundOn", true ) ){
            onText1.setColor(Color.SKY);
            offText1.setColor(Color.WHITE);
        }
        else{
            offText1.setColor(Color.SKY);
            onText1.setColor(Color.WHITE);
        }
        if( prefs.getBoolean("notificationsOn", true ) ){
            onText2.setColor(Color.SKY);
            offText2.setColor(Color.WHITE);
        }
        else{
            offText2.setColor(Color.SKY);
            onText2.setColor(Color.WHITE);
        }
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
        musicOnBounds = new Rectangle( musicText.getHeight()+ backBtn.getWidth()+ 20 + musicText.getWidth(), (logo.getHeight())+ musicText.getHeight()*3+20,46,30);
        musicOffBounds = new Rectangle(musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+musicText.getWidth(),  (logo.getHeight())+ musicText.getHeight()*3+20,46,30);
        soundOnBounds = new Rectangle( musicText.getHeight()+ backBtn.getWidth()+ 20 + soundText.getWidth(), (logo.getHeight())+ musicText.getHeight()*5+20,46,30);
        soundOffBounds = new Rectangle(musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+soundText.getWidth(),  (logo.getHeight())+ musicText.getHeight()*5+20,46,30);
        notificationsOnBounds = new Rectangle( musicText.getHeight()+ backBtn.getWidth()+ 20 + notificationsText.getWidth(), (logo.getHeight())+ musicText.getHeight()*7+20,46,30);
        notificationsOffBounds = new Rectangle(musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+notificationsText.getWidth(),  (logo.getHeight())+ musicText.getHeight()*7+20,46,30);
    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (backBounds.contains(tmp.x, tmp.y)) {
                if( prefs.getBoolean("soundOn", true ) ){
                    Piece12.buttonSound.play();
                }
                goBack();
            }
            else if (musicOnBounds.contains(tmp.x, tmp.y)) {
                if( prefs.getBoolean("soundOn", true ) ){
                    Piece12.buttonSound.play();
                }
                Piece12.music.play();
                onText.setColor(Color.SKY);
                offText.setColor(Color.WHITE);
                prefs.putBoolean( "musicOn", true );
                prefs.flush();
            }
            else if (musicOffBounds.contains(tmp.x, tmp.y)) {
                if( prefs.getBoolean("soundOn", true ) ){
                    Piece12.buttonSound.play();
                }
                Piece12.music.pause();
                offText.setColor(Color.SKY);
                onText.setColor(Color.WHITE);
                prefs.putBoolean( "musicOn", false );
                prefs.flush();
            }
            else if (soundOnBounds.contains(tmp.x, tmp.y)) {
                Piece12.buttonSound.play();
                onText1.setColor(Color.SKY);
                offText1.setColor(Color.WHITE);
                prefs.putBoolean( "soundOn", true );
                prefs.flush();
            }
            else if (soundOffBounds.contains(tmp.x, tmp.y)) {
                offText1.setColor(Color.SKY);
                onText1.setColor(Color.WHITE);
                prefs.putBoolean( "soundOn", false );
                prefs.flush();
            }
            else if (notificationsOnBounds.contains(tmp.x, tmp.y)) {
                onText2.setColor(Color.SKY);
                offText2.setColor(Color.WHITE);
                prefs.putBoolean( "notificationsOn", true );
                prefs.flush();
            }
            else if (notificationsOffBounds.contains(tmp.x, tmp.y)) {
                offText2.setColor(Color.SKY);
                onText2.setColor(Color.WHITE);
                prefs.putBoolean( "notificationsOn", false );
                prefs.flush();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    //This method draws the images on the screen
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,Piece12.WIDTH, Piece12.HEIGHT);
        sb.draw(logo, (Piece12.WIDTH/2)-(logo.getHeight()/2), Piece12.HEIGHT/2 + (logo.getHeight()));
        sb.draw(musicText,musicText.getHeight()+ backBtn.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*3-50 );
        sb.draw(soundText,musicText.getHeight()+ backBtn.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*5-50 );
        sb.draw(notificationsText,musicText.getHeight()+ backBtn.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*7-50);
        sb.draw(prefText, musicText.getHeight()+ backBtn.getWidth(),Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*2-25);
        onText.draw(sb,"ON",musicText.getHeight()+ backBtn.getWidth()+20+musicText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*2-55);
        offText.draw(sb,"OFF",musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+musicText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*2-55);
        onText1.draw(sb,"ON",musicText.getHeight()+ backBtn.getWidth()+20+soundText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight()) - musicText.getHeight()*4-55);
        offText1.draw(sb,"OFF",musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+soundText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*4-55);
        onText2.draw(sb,"ON",musicText.getHeight()+ backBtn.getWidth()+20+notificationsText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight()) - musicText.getHeight()*6-55);
        offText2.draw(sb,"OFF",musicText.getHeight()+ backBtn.getWidth()+40+musicBtn.getWidth()+notificationsText.getWidth()+musicBtn.getWidth()/3,Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*6-55);

        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+25+musicText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*3-50);
        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+47+musicBtn.getWidth()+musicText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*3-50);
        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+25+soundText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*5-50);
        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+47+musicBtn.getWidth()+soundText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*5-50);
        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+25+notificationsText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*7-50);
        sb.draw(musicBtn,musicText.getHeight()+ backBtn.getWidth()+47+musicBtn.getWidth()+notificationsText.getWidth(), Piece12.HEIGHT - (logo.getHeight())- musicText.getHeight()*7-50);
        sb.draw(backBtn, backBtn.getWidth()/2, Piece12.HEIGHT - backBtn.getWidth()*3/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.dispose();
        logo.dispose();
        musicText.dispose();
        soundText.dispose();
        notificationsText.dispose();
        musicBtn.dispose();
    }
}