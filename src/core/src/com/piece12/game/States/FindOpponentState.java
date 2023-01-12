package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Piece12;

public class FindOpponentState extends State {
    private Texture backBtn;
    private Texture randomBtn;
    private Texture facebookBtn;
    private Texture text;
    private Rectangle randomBounds;
    private Rectangle facebookBounds;
    private Rectangle backBounds;

    protected FindOpponentState(GameStateManager gsm) {
        super(gsm);
        backBtn = new Texture("backbtn.png");
        randomBtn = new Texture("randombtn.png");
        facebookBtn = new Texture("facebtn.png");
        text =new Texture("chooseoptext.png");
        backBounds = new Rectangle(backBtn.getWidth()/2,  backBtn.getWidth()*3/2-backBtn.getHeight(), backBtn.getWidth(), backBtn.getHeight());
        randomBounds=new Rectangle((Piece12.WIDTH/2)-(randomBtn.getWidth())*9/7,  Piece12.HEIGHT/2 + (randomBtn.getHeight()/2)-randomBtn.getHeight(), randomBtn.getWidth(), randomBtn.getHeight()+50);
        facebookBounds=new Rectangle((Piece12.WIDTH/2)+(facebookBtn.getWidth())*2/7,  Piece12.HEIGHT/2 + (facebookBtn.getHeight()/2)- facebookBtn.getHeight(), facebookBtn.getWidth(), facebookBtn.getHeight()+50);

    }
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (backBounds.contains(tmp.x, tmp.y))
                goBack();
            //if (randomBounds.contains(tmp.x, tmp.y))
            //    goPlayState(0);
            //if (facebookBounds.contains(tmp.x, tmp.y))
               // goPlayState(0);
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
        sb.draw(text, (Piece12.WIDTH/2)-(text.getWidth()/2), Piece12.HEIGHT/2 + (text.getHeight()));
        sb.draw(backBtn, backBtn.getWidth()/2, Piece12.HEIGHT - backBtn.getWidth()*3/2);
        sb.draw(randomBtn,(Piece12.WIDTH/2)-(randomBtn.getWidth())*9/7, Piece12.HEIGHT/2 - (randomBtn.getHeight()/2)-50);
        sb.draw(facebookBtn,(Piece12.WIDTH/2)+(facebookBtn.getWidth())*2/7, Piece12.HEIGHT/2 - (facebookBtn.getHeight()/2)-50);
        sb.end();
    }

    @Override
    public void dispose() {
        backBtn.dispose();
        background.dispose();
        logo.dispose();
        randomBtn.dispose();
        facebookBtn.dispose();
    }
}
