package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Entities.Board;
import com.piece12.game.Entities.Level;
import com.piece12.game.Entities.Piece;
import com.piece12.game.Piece12;

public class PlayState extends State implements GestureDetector.GestureListener {

    //properties
    private Texture pauseBtn;
    private Texture howToPlayBtn;
    private Texture backBtn;

    private Board board;
    private Piece pieces[];
    private Level level;

    private Rectangle pauseBounds;
    private Rectangle howToPlayBounds;
    private Rectangle backBounds;
    private Rectangle boardBounds;
    private int selectedPiece;
    private boolean resetted;
    private float timeSeconds;
    private float period;
    private BitmapFont time;
    private Sound tada;
    private Sound dada;


    //constructors
    public PlayState( GameStateManager gsm, Level l ){
        super( gsm );
        pauseBtn = new Texture("pausebtn.png");
        board = new Board();
        board.setPosition( (Piece12.WIDTH/2)-(board.getTexture().getWidth()/2), board.getTexture().getHeight()/3);
        howToPlayBtn = new Texture("htpbtn.png");
        backBtn = new Texture("backbtn.png" );
        pauseBounds = new Rectangle(Piece12.WIDTH - pauseBtn.getWidth()*3/2,  pauseBtn.getHeight()/2,pauseBtn.getWidth(),pauseBtn.getHeight());
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(),backBtn.getWidth(),backBtn.getHeight());
        boardBounds = new Rectangle((Piece12.WIDTH/2)-(board.getTexture().getWidth()/2),  Piece12.HEIGHT - board.getTexture().getHeight()/3 - board.getTexture().getHeight(), board.getTexture().getWidth(), board.getTexture().getHeight());
        howToPlayBounds = new Rectangle( Piece12.WIDTH - howToPlayBtn.getWidth()*3/2, Piece12.HEIGHT-howToPlayBtn.getWidth()/2-howToPlayBtn.getHeight(), howToPlayBtn.getWidth(), howToPlayBtn.getHeight() );
        pieces = new Piece[ 12 ];
        selectedPiece = -1;

        pieces[0] = new Piece(  1, 150, 40);
        pieces[1]= new Piece (2, 40, 150);
        pieces[2]=new Piece(3,120,150);
        pieces[3] = new Piece(4, 40,40);
        pieces[4]=new Piece(5,Piece12.WIDTH-200,220);
        pieces[5]=new Piece(6,Piece12.WIDTH-40,200);
        pieces[6] = new Piece(7, Piece12.WIDTH/2+130,300);
        pieces[7] = new Piece(8,180,260);
        pieces[8] = new Piece(9, 60,250);
        pieces[9]= new Piece(10,Piece12.WIDTH-150, 100);
        pieces[10] = new Piece(11, Piece12.WIDTH-95, 135);
        pieces[11]=new Piece(12,300, 270);

        level = l;
        resetted = true;
        level.setBoardPiece( board, pieces );
        if( level.getMode().equals( "memory" ) ){
            resetted = false;
            time = new BitmapFont();
            time.setColor( Color.BLACK );
            timeSeconds = 0f;
            period = 10f;
        }
        else if( level.getMode().equals( "race" ) ){
            resetted = false;
            time = new BitmapFont();
            time.setColor( Color.BLACK );
            timeSeconds = 0f;
            period = 90f;
        }
        Gdx.input.setInputProcessor( new GestureDetector( this ));
        if( prefs.getBoolean("soundOn", true ) ){
            tada = Gdx.audio.newSound(Gdx.files.internal("tada.mp3"));
            dada = Gdx.audio.newSound(Gdx.files.internal("dada.mp3"));
        }
    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
       if( Gdx.input.justTouched() ) {
           tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
           tmp = fixCoordinates( tmp );

           //check if pause button is clicked.
           if (pauseBounds.contains(tmp.x, tmp.y))
               goPauseState( level );

           else if (backBounds.contains(tmp.x, tmp.y))
               goBack();

           else if( howToPlayBounds.contains(tmp.x, tmp.y)) {
               goHowtoPlayState();
           }
           for( int i = 0; i < 12; i++ ) {
               if (pieces[i].contains(tmp.x, Piece12.HEIGHT - tmp.y) && pieces[i].isMoveable() ) {
                   if (selectedPiece == -1) {
                       selectedPiece = i;
                   }
               }
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
        if( !resetted ){
            timeSeconds = timeSeconds + Gdx.graphics.getRawDeltaTime();
            if( level.getMode().equals( "memory")) {
                if (timeSeconds > period) {
                    for (int i = 0; i < 12; i++) {
                        pieces[i].resetAll();
                    }
                    resetted = true;
                }
            }
            else if (level.getMode().equals("race")) {
                if (timeSeconds > period) {
                    dada.play(1.0f);
                    gsm.pop();
                    goFailureState(level);
                }
            }
        }
        sb.begin();
        sb.draw(background,0,0,Piece12.WIDTH, Piece12.HEIGHT);
        sb.draw(logo, (Piece12.WIDTH/2)-(logo.getHeight()/2), Piece12.HEIGHT/2 + (logo.getHeight()));
        sb.draw(pauseBtn, Piece12.WIDTH - pauseBtn.getWidth()*3/2, Piece12.HEIGHT - pauseBtn.getWidth()*3/2);
        board.draw(sb);
        sb.draw(backBtn, backBtn.getWidth()/2, Piece12.HEIGHT - backBtn.getWidth()*3/2);
        sb.draw(howToPlayBtn, Piece12.WIDTH-howToPlayBtn.getWidth()*3/2, howToPlayBtn.getWidth()/2);
        for( int i = 0; i < 12; i++ ){
            pieces[ i ].draw( sb );
        }
        if( !resetted ){
            int t = (int)timeSeconds;
            if( level.getMode().equals( "memory") ){
                if( t == 0 ){
                    time.draw( sb, "0:" + 10, Piece12.WIDTH / 2 - 6, 30 );
                }
                else{
                    time.draw( sb, "0:0" + ( 10 - t ), Piece12.WIDTH / 2 - 6, 30 );
                }
            }
            else if( level.getMode().equals( "race") ){
                if( t < 21 ){
                    time.draw( sb, "1:" + ( 30 - t ), Piece12.WIDTH / 2 - 6, 30 );
                }
                else if( t < 31 ){
                    time.draw( sb, "1:0" + ( 30 - t ), Piece12.WIDTH / 2 - 6, 30 );
                }
                else if( t < 81 ){
                    time.draw( sb, "0:" + ( 90 - t ), Piece12.WIDTH / 2 - 6, 30 );
                }
                else{
                    time.draw( sb, "0:0" + ( 90 - t ), Piece12.WIDTH / 2 - 6, 30 );
                }
            }
        }
        if( resetted && level.getMode().equals( "memory") ){
            time.draw( sb, "", 0, 0 );
        }
        sb.end();
    }

    @Override
    public void dispose() {
        for( int i = 0; i < 12; i++ ){
            pieces[ i ].dispose();
        }
        logo.dispose();
        background.dispose();
        board.getTexture().dispose();
        pauseBtn.dispose();
        howToPlayBtn.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if( selectedPiece != -1 ){
            pieces[ selectedPiece ].changeMode();
            if( pieces[ selectedPiece ].onBoard() ) {
                for (int i = 0; i < 12; i++) {
                    if (i != selectedPiece && pieces[i].collision(pieces[selectedPiece])) {
                        pieces[selectedPiece].changeMode();
                        pieces[selectedPiece].changeMode();
                        pieces[selectedPiece].changeMode();
                        break;
                    }
                }
            }
            selectedPiece = -1;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        if( selectedPiece != -1 ){
            pieces[ selectedPiece ].flip();
            if( pieces[ selectedPiece ].onBoard() ) {
                for (int i = 0; i < 12; i++) {
                    if (i != selectedPiece && pieces[i].collision(pieces[selectedPiece])) {
                        pieces[selectedPiece].flip();
                        break;
                    }
                }
            }
            selectedPiece = -1;
        }
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(selectedPiece != -1 ){
            Vector3 tmp = new Vector3( x, y, 0 );
            tmp = fixCoordinates( tmp );
            pieces[ selectedPiece ].move( tmp.x, Piece12.HEIGHT - tmp.y );
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Vector3 tmp = new Vector3( x, y, 0 );
        tmp = fixCoordinates( tmp );
        if( selectedPiece != -1 && !boardBounds.contains( tmp.x, tmp.y )){
            pieces[ selectedPiece ].resetAll();
            selectedPiece = -1;
            return false;
        }
        else if( selectedPiece != -1 ){
            board.magnet(  pieces[ selectedPiece ] );
        }
        for( int i = 0; i < 12; i++ ){
            if( selectedPiece != -1 && i != selectedPiece && pieces[ i ].collision( pieces[ selectedPiece ] ) ){
                pieces[ selectedPiece ].resetAll();
                selectedPiece = -1;
                return false;
            }
        }
        selectedPiece = -1;
        checkWin();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
    }

    private void checkWin(){
        if( level.getMode().equals( "memory" ) ){
            if( resetted && board.checkWinCondition( pieces, level.getWin() ) ){
                gsm.pop();
                goModeState();
            }
        }
        else if( board.win( pieces ) ){
            tada.play(1.0f);
            gsm.pop();
            goWinState( level );
        }
    }
}
