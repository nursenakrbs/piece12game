package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Level;
import com.piece12.game.Piece12;


public class LevelMapState extends State {

    //properties
    private Texture levelCircle;
    private Texture levelLocked;
    private Level levels[];
    private BitmapFont levelNum;
    private Texture backBtn;
    private Texture howToPlayBtn;

    private Rectangle backBounds;
    private Rectangle howToPlayBounds;
    private Rectangle[] levelBounds;


    //constructor
    public LevelMapState( GameStateManager gsm ){
        super( gsm );
        levelCircle = new Texture("level.png");
        levelLocked = new Texture( "levellocked.png");
        levels = new Level[30];
        for (int i = 0; i<30; i++){
            levels[i]= new Level(i + 1, "normal");
        }
        levels[0].setLocked(false);
        levelBounds = new Rectangle[30];
        for(int j = 0; j<3; j++ ) {
            for (int i = 0; i < 10; i++)
                levelBounds[i+j*10] = new Rectangle(80 + i * 66, 200 + j * 77 -46,46,46 );
        }
        levelNum = new BitmapFont();
        levelNum.setColor(Color.WHITE);
        backBtn = new Texture("backbtn.png");
        howToPlayBtn = new Texture("htpbtn.png");
        howToPlayBounds = new Rectangle(Piece12.WIDTH - howToPlayBtn.getWidth()*3/2,  howToPlayBtn.getWidth()*3/2-howToPlayBtn.getHeight(),howToPlayBtn.getWidth(),howToPlayBtn.getHeight());
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
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
            else if (howToPlayBounds.contains(tmp.x, tmp.y)) {
                goHowtoPlayState();
            }
            for(int j = 0; j<30; j++ ){
                if ((levelBounds[j]).contains(tmp.x, tmp.y) && !levels[j].getLocked())
                    goPlayState( levels[j] );
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
        for(int j = 0; j<3; j++ ){
            for (int i = 0; i < 10; i++) {
                if (!levels[j*10 + i].getLocked())
                    sb.draw(levelCircle, 80 + i * 66, 200-j*77);
                else
                    sb.draw(levelLocked, 80 + i * 66, 200-j*77);
                if(j*10+i<9)
                    levelNum.draw(sb, String.valueOf(j*10 + i + 1), 100 + i* 66, 230-j*77);
                else
                    levelNum.draw(sb, String.valueOf(j*10 + i + 1), 95 + i * 66, 230-j*77);
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
        levelCircle.dispose();
        background.dispose();
        backBtn.dispose();
        logo.dispose();
        howToPlayBtn.dispose();
        levelLocked.dispose();
    }
}