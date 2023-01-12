package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;

public class PauseState extends State {

    //properties
    private Level level;

    private Texture resumeBtn;
    private Texture restartBtn;
    private Texture mainMenuBtn;
    private Texture settingsBtn;

    private Rectangle resumeBounds;
    private Rectangle restartBounds;
    private Rectangle mainMenuBounds;
    private Rectangle settingsBounds;

    //constructors
    public PauseState( GameStateManager gsm, Level l ){
        super( gsm );
        level = l;

        background = new Texture("pauseBackground.png");
        resumeBtn = new Texture("pausestateresume.png");
        restartBtn = new Texture("pausestaterestart.png");
        settingsBtn = new Texture("pausestatesettings.png");
        mainMenuBtn = new Texture("pausestatehome.png");
        resumeBounds = new Rectangle(Piece12.WIDTH/2-5-resumeBtn.getWidth(), Piece12.HEIGHT/2-5-resumeBtn.getHeight(), resumeBtn.getWidth(),resumeBtn.getHeight());
        settingsBounds = new Rectangle(Piece12.WIDTH/2-5-settingsBtn.getWidth(), Piece12.HEIGHT/2+5,settingsBtn.getWidth(),settingsBtn.getHeight());
        restartBounds = new Rectangle(Piece12.WIDTH/2+5, Piece12.HEIGHT/2+5,restartBtn.getWidth(),restartBtn.getHeight());
        mainMenuBounds = new Rectangle(Piece12.WIDTH/2+5,Piece12.HEIGHT/2-5-mainMenuBtn.getHeight(),mainMenuBtn.getWidth(),mainMenuBtn.getHeight());

    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (resumeBounds.contains(tmp.x, tmp.y)) {
                goBack();
            }
            else if (mainMenuBounds.contains(tmp.x, tmp.y)) {
                goMenuState();
            }
            else if (restartBounds.contains(tmp.x, tmp.y)) {
                gsm.pop();
                gsm.pop();
                goPlayState( level );
            }
            else if (settingsBounds.contains(tmp.x, tmp.y)) {
                goSettingsState();
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
        sb.draw(resumeBtn, Piece12.WIDTH/2-5-resumeBtn.getWidth(), Piece12.HEIGHT/2+5);
        sb.draw(mainMenuBtn, Piece12.WIDTH/2+5, Piece12.HEIGHT/2+5);
        sb.draw(restartBtn, Piece12.WIDTH/2+5, Piece12.HEIGHT/2-5-restartBtn.getHeight());
        sb.draw(settingsBtn, Piece12.WIDTH/2-5-settingsBtn.getWidth(), Piece12.HEIGHT/2-5-settingsBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        settingsBtn.dispose();
        restartBtn.dispose();
        resumeBtn.dispose();
        mainMenuBtn.dispose();
    }
}
