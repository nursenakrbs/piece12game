package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;

public class FailureState extends State {

    private Level level;
    private Texture restartBtn;
    private Texture mainMenuBtn;
    private Texture gameover;
    private Rectangle restartBounds;
    private Rectangle mainMenuBounds;

    public FailureState(GameStateManager gsm, Level l) {
        super(gsm);
        level = l;
        background = new Texture("winBackground.png");
        restartBtn = new Texture("pausestaterestart.png");
        mainMenuBtn = new Texture("pausestatehome.png");
        gameover = new Texture("gameover.png");
        restartBounds = new Rectangle(Piece12.WIDTH/2+5,Piece12.HEIGHT/2+95-mainMenuBtn.getHeight(),mainMenuBtn.getWidth(),mainMenuBtn.getHeight());
        mainMenuBounds = new Rectangle(Piece12.WIDTH/2-5-mainMenuBtn.getWidth(), Piece12.HEIGHT/2+95-mainMenuBtn.getHeight(), mainMenuBtn.getWidth(),mainMenuBtn.getHeight());

    }
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        tmp = fixCoordinates( tmp );

        if (mainMenuBounds.contains(tmp.x, tmp.y)) {
            goMenuState();
        }
        else if (restartBounds.contains(tmp.x, tmp.y)) {
            gsm.pop();
            gsm.pop();
            goPlayState( level );
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
        sb.draw(mainMenuBtn, Piece12.WIDTH/2-5-mainMenuBtn.getWidth(), Piece12.HEIGHT/2-95);
        sb.draw(restartBtn, Piece12.WIDTH/2+5, Piece12.HEIGHT/2-95);
        sb.draw(gameover, (Piece12.WIDTH/2)-(gameover.getWidth()/2), Piece12.HEIGHT/2 + (gameover.getHeight())-100);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        mainMenuBtn.dispose();
        restartBtn.dispose();
        gameover.dispose();
    }
}
