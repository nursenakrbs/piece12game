package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Piece12;

public class HowtoPlayState extends State {
    //properties
    private Texture backBtn;
    private Texture htp;
    private Rectangle backBounds;

    //constructors
    public HowtoPlayState( GameStateManager gsm ){
        super( gsm );
        backBtn = new Texture("backbtn.png");
        htp = new Texture("htp.png");
        backBounds = new Rectangle(backBtn.getWidth()/2, backBtn.getHeight()/2, backBtn.getWidth(), backBtn.getHeight());
    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (backBounds.contains(tmp.x, tmp.y)) {
                goBack();
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
        sb.draw(htp, (Piece12.WIDTH/2)-(htp.getWidth()/2),(logo.getHeight()/2));
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        backBtn.dispose();
        htp.dispose();
    }
}
