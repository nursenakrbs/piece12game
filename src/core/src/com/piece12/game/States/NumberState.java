package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Piece12;

public class NumberState extends State {
    //properties

    private Texture singlePlayerBtn;
    private Texture multiPlayerBtn;
    private Texture backBtn;
    private Texture howToPlayBtn;

    private Rectangle singlePlayerBounds;
    private Rectangle multiplayerBounds;
    private Rectangle backBounds;
    private Rectangle howToPlayBounds;

    //constructors
    protected NumberState(GameStateManager gsm) {
        super(gsm);
        backBtn = new Texture("backbtn.png");
        singlePlayerBtn = new Texture("singleplayerbtn.png");
        multiPlayerBtn = new Texture("multiplayerbtn.png");
        howToPlayBtn = new Texture("htpbtn.png");
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
        singlePlayerBounds = new Rectangle((Piece12.WIDTH/2)-(singlePlayerBtn.getWidth())*9/7,  Piece12.HEIGHT/2 + (singlePlayerBtn.getHeight()/2)-singlePlayerBtn.getHeight(), singlePlayerBtn.getWidth(), singlePlayerBtn.getHeight());
        multiplayerBounds = new Rectangle((Piece12.WIDTH/2)+(multiPlayerBtn.getWidth())*2/7,  Piece12.HEIGHT/2 + (multiPlayerBtn.getHeight()/2)- multiPlayerBtn.getHeight(), multiPlayerBtn.getWidth(), multiPlayerBtn.getHeight());
        howToPlayBounds = new Rectangle(Piece12.WIDTH - howToPlayBtn.getWidth()*3/2,  howToPlayBtn.getWidth()*3/2-howToPlayBtn.getHeight(),howToPlayBtn.getWidth(),howToPlayBtn.getHeight());
    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (backBounds.contains(tmp.x, tmp.y)) {
                goBack();
            }
            else if (singlePlayerBounds.contains(tmp.x, tmp.y)) {
                goModeState();
            }
            else if (howToPlayBounds.contains(tmp.x, tmp.y)) {
                goHowtoPlayState();
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
        sb.draw(backBtn, backBtn.getWidth()/2, Piece12.HEIGHT - backBtn.getWidth()*3/2);
        sb.draw(howToPlayBtn, Piece12.WIDTH - howToPlayBtn.getWidth()*3/2, Piece12.HEIGHT - howToPlayBtn.getWidth()*3/2);
        sb.draw(singlePlayerBtn,(Piece12.WIDTH/2)-(singlePlayerBtn.getWidth())*9/7, Piece12.HEIGHT/2 - (singlePlayerBtn.getHeight()/2));
        sb.draw(multiPlayerBtn,(Piece12.WIDTH/2)+(multiPlayerBtn.getWidth())*2/7, Piece12.HEIGHT/2 - (multiPlayerBtn.getHeight()/2));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.dispose();
        singlePlayerBtn.dispose();
        logo.dispose();
        multiPlayerBtn.dispose();
        howToPlayBtn.dispose();
    }
}