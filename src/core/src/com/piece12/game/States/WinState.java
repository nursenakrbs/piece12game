package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;


public class WinState extends State {

    //properties
    private Level level;
    private Texture nextBtn;
    private Texture levelBtn;
    private Texture restartBtn;
    private Texture mainMenuBtn;
    private Texture congratulations;
    private Texture youwin;
    private Texture nonext;
    private Rectangle nextBounds;
    private Rectangle restartBounds;
    private Rectangle mainMenuBounds;
    private Rectangle levelMapBounds;
    private Level nextlevel;

    //constructor
    public WinState(GameStateManager gsm, Level l) {
        super(gsm);
        level = l;
        if( l.getMode().equals( "normal") ){
            nextlevel = new Level(level.getNumber()+1, "normal" );
            nextlevel.openLevel();
        }
        background = new Texture("winBackground.png");
        nextBtn = new Texture("pausestateresume.png");
        restartBtn = new Texture("pausestaterestart.png");
        mainMenuBtn = new Texture("pausestatehome.png");
        levelBtn = new Texture("levelmapbtn.png");
        congratulations = new Texture("congratz.png");
        youwin = new Texture("win.png");
        nonext = new Texture("nonext.png");
        nextBounds = new Rectangle(Piece12.WIDTH/2-5-nextBtn.getWidth(), Piece12.HEIGHT/2+95-nextBtn.getHeight(), nextBtn.getWidth(),nextBtn.getHeight());
        mainMenuBounds = new Rectangle(Piece12.WIDTH/2+5,Piece12.HEIGHT/2+95-mainMenuBtn.getHeight(),mainMenuBtn.getWidth(),mainMenuBtn.getHeight());
        if( l.getMode().equals( "normal") ){
            levelMapBounds = new Rectangle(Piece12.WIDTH/2-5-levelBtn.getWidth(), Piece12.HEIGHT/2+105,levelBtn.getWidth(),levelBtn.getHeight());
            restartBounds = new Rectangle(Piece12.WIDTH/2+5, Piece12.HEIGHT/2+105,restartBtn.getWidth(),restartBtn.getHeight());
        }
    }
    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (nextBounds.contains(tmp.x, tmp.y) && level.getNumber()!=30) {
                gsm.pop();
                gsm.pop();
                if( level.getMode().equals( "normal" ) ){
                    goPlayState( nextlevel );
                }else{
                    goPlayState( new Level( (int) Math.ceil( Math.random() * 30 ), level.getMode() ) );
                }
            }
            else if (mainMenuBounds.contains(tmp.x, tmp.y)) {
                goMenuState();
            }
            else if (restartBounds.contains(tmp.x, tmp.y)) {
                gsm.pop();
                gsm.pop();
                goPlayState(  level );
            }
            else if (levelMapBounds.contains(tmp.x, tmp.y)) {
                gsm.pop();
                gsm.pop();
                gsm.pop();
                goLevelMapState();
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
        sb.draw(congratulations, (Piece12.WIDTH/2)-(congratulations.getWidth()/2), Piece12.HEIGHT/2 + (congratulations.getHeight())-100);
        sb.draw(youwin,(Piece12.WIDTH/2)-(youwin.getWidth()/2),Piece12.HEIGHT/2 + (congratulations.getHeight())-120);
        if(level.getNumber() == 30)
            sb.draw(nonext, Piece12.WIDTH/2-5-nextBtn.getWidth(), Piece12.HEIGHT/2-95);
        else
            sb.draw(nextBtn, Piece12.WIDTH/2-5-nextBtn.getWidth(), Piece12.HEIGHT/2-95);
        sb.draw(mainMenuBtn, Piece12.WIDTH/2+5, Piece12.HEIGHT/2-95);
        if( level.getMode().equals( "normal") ){
            sb.draw(restartBtn, Piece12.WIDTH/2+5, Piece12.HEIGHT/2-5-restartBtn.getHeight()-100);
            sb.draw(levelBtn, Piece12.WIDTH/2-5-levelBtn.getWidth(), Piece12.HEIGHT/2-5-levelBtn.getHeight()-100);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        nextBtn.dispose();
        levelBtn.dispose();
        restartBtn.dispose();
        mainMenuBtn.dispose();
        background.dispose();
        logo.dispose();
        congratulations.dispose();
        youwin.dispose();
    }
}