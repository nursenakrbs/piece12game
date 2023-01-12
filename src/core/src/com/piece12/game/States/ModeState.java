package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;

public class ModeState extends State{

    //properties
    private Texture timeBtn;
    private Texture memoryBtn;
    private Texture classicBtn;
    private Texture backBtn;
    private Texture howToPlayBtn;

    private Rectangle classicBounds;
    private Rectangle timeRaceBounds;
    private Rectangle memoryBounds;
    private Rectangle backBounds;
    private Rectangle howToPlayBounds;


    //constructors
    public ModeState( GameStateManager gsm ){
        super( gsm );
        memoryBtn = new Texture( "memorymodebtn.png" );
        timeBtn = new Texture( "timeracemodebtn.png" );
        classicBtn = new Texture( "classicmodebtn.png" );
        backBtn = new Texture("backbtn.png");
        howToPlayBtn = new Texture("htpbtn.png");

        howToPlayBounds = new Rectangle(Piece12.WIDTH - howToPlayBtn.getWidth()*3/2,  howToPlayBtn.getWidth()*3/2-howToPlayBtn.getHeight(),howToPlayBtn.getWidth(),howToPlayBtn.getHeight());
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
        classicBounds = new Rectangle((Piece12.WIDTH/2) - classicBtn.getWidth()/2, Piece12.HEIGHT/2-classicBtn.getHeight(), classicBtn.getWidth(),classicBtn.getHeight());
        timeRaceBounds = new Rectangle((Piece12.WIDTH/2) - memoryBtn.getWidth()/2, Piece12.HEIGHT*2/3 + memoryBtn.getHeight()*5/4-timeBtn.getHeight(), timeBtn.getWidth(),timeBtn.getHeight());
        memoryBounds = new Rectangle((Piece12.WIDTH/2) - memoryBtn.getWidth()/2, Piece12.HEIGHT*2/3-memoryBtn.getHeight(), memoryBtn.getWidth(),memoryBtn.getHeight());

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
            else if (classicBounds.contains(tmp.x, tmp.y)) {
                goLevelMapState();
            }
            else if (timeRaceBounds.contains(tmp.x, tmp.y)) {
                goPlayState( new Level( (int) Math.ceil( Math.random() * 30 ), "race") );
            }
            else if (memoryBounds.contains(tmp.x, tmp.y)) {
                goPlayState( new Level( (int) Math.ceil( Math.random() * 30 ), "memory" ) );
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
        sb.draw(classicBtn, (Piece12.WIDTH/2) - classicBtn.getWidth()/2, Piece12.HEIGHT/2 );
        sb.draw(memoryBtn, (Piece12.WIDTH/2) - memoryBtn.getWidth()/2, Piece12.HEIGHT/3 );
        sb.draw(timeBtn, (Piece12.WIDTH/2) - memoryBtn.getWidth()/2, Piece12.HEIGHT/3 - memoryBtn.getHeight()*5/4 );
        sb.draw(backBtn, backBtn.getWidth()/2, Piece12.HEIGHT - backBtn.getWidth()*3/2);
        sb.draw(howToPlayBtn, Piece12.WIDTH - howToPlayBtn.getWidth()*3/2, Piece12.HEIGHT - howToPlayBtn.getWidth()*3/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.dispose();
        classicBtn.dispose();
        logo.dispose();
        timeBtn.dispose();
        memoryBtn.dispose();
        howToPlayBtn.dispose();
    }
}
