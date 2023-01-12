package com.piece12.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.piece12.game.Piece12;


public class MenuState extends State{

    //properties
    private Texture newGameBtn;
    private Texture dailyMissionBtn;
    private Texture howToPlayBtn;
    private Texture creditsBtn;
    private Texture rateUsBtn;
    private Texture shopBtn;
    private Texture settingsBtn;
    private Texture leaderboardBtn;
    private Rectangle newGameBounds;
    private Rectangle leaderboardBounds;
    private Rectangle howToPlayBounds;
    private Rectangle creditsBounds;
    private Rectangle shopBounds;
    private Rectangle dailyMissionBounds;
    private Rectangle rateUsBounds;
    private Rectangle settingsBounds;


    //constructors
    public MenuState(GameStateManager gsm) {

        super(gsm);
        //Textures for UI
        newGameBtn = new Texture("newgamebtn.png");
        dailyMissionBtn = new Texture("dailymissionbtn.png");
        howToPlayBtn = new Texture("howtoplaybtn.png");
        creditsBtn = new Texture("creditsbtn.png");
        rateUsBtn = new Texture("rateusbtn.png");
        shopBtn = new Texture("shopbtn.png");
        settingsBtn = new Texture("settingsbtn.png");
        leaderboardBtn = new Texture("leaderboardbtn.png");
        //Rectangles are for clickable buttons
        newGameBounds = new Rectangle((Piece12.WIDTH/2)-(newGameBtn.getWidth())*9/7, Piece12.HEIGHT/2 + (newGameBtn.getHeight()/4)-newGameBtn.getHeight(), newGameBtn.getWidth(), newGameBtn.getHeight());
        leaderboardBounds = new Rectangle((Piece12.WIDTH/2)+(newGameBtn.getWidth())*2/7, Piece12.HEIGHT/2 + (newGameBtn.getHeight()/4)-leaderboardBtn.getHeight(), leaderboardBtn.getWidth(), leaderboardBtn.getHeight());
        howToPlayBounds = new Rectangle((Piece12.WIDTH/2)-(newGameBtn.getWidth())*9/7, Piece12.HEIGHT/2 + (newGameBtn.getHeight()*2)- newGameBtn.getHeight()/3 -howToPlayBtn.getHeight(), howToPlayBtn.getWidth(), howToPlayBtn.getHeight());
        creditsBounds= new Rectangle((Piece12.WIDTH/2)+(newGameBtn.getWidth())*2/7, Piece12.HEIGHT/2 + (newGameBtn.getHeight()*2)- newGameBtn.getHeight()/3-creditsBtn.getHeight(), creditsBtn.getWidth(), creditsBtn.getHeight());
        shopBounds= new Rectangle(shopBtn.getWidth()/2,  Piece12.HEIGHT-shopBtn.getWidth()/2- shopBtn.getHeight(), shopBtn.getWidth(), shopBtn.getHeight());
        dailyMissionBounds = new Rectangle(Piece12.WIDTH - settingsBtn.getWidth()*3/2,  dailyMissionBtn.getWidth()*3/2-dailyMissionBtn.getHeight(), dailyMissionBtn.getWidth(), dailyMissionBtn.getHeight());
        rateUsBounds= new Rectangle(rateUsBtn.getWidth()/2,  rateUsBtn.getWidth()*3/2-rateUsBtn.getHeight(), rateUsBtn.getWidth(), rateUsBtn.getHeight());
        settingsBounds= new Rectangle(Piece12.WIDTH - settingsBtn.getWidth()*3/2, Piece12.HEIGHT-settingsBtn.getWidth()/2-settingsBtn.getHeight(), settingsBtn.getWidth(), settingsBtn.getHeight());
    }

    //methods
    //This method decides what to do with an input
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            tmp = fixCoordinates( tmp );
            if (newGameBounds.contains(tmp.x, tmp.y)) {
                goNumberState();
            }
            else if (howToPlayBounds.contains(tmp.x, tmp.y)) {
                goHowtoPlayState();
            }
            else if (creditsBounds.contains(tmp.x, tmp.y)) {
                goCreditsState();
            }
            else if (rateUsBounds.contains(tmp.x, tmp.y)) {
                goMarket();
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
        sb.setProjectionMatrix( cam.combined );
        sb.begin();
        sb.draw(background,0,0,Piece12.WIDTH, Piece12.HEIGHT);
        sb.draw(logo, (Piece12.WIDTH/2)-(logo.getHeight()/2), Piece12.HEIGHT/2 + (logo.getHeight()));
        sb.draw(newGameBtn,(Piece12.WIDTH/2)-(newGameBtn.getWidth())*9/7, Piece12.HEIGHT/2 - (newGameBtn.getHeight()/4));
        sb.draw(leaderboardBtn,(Piece12.WIDTH/2)+(newGameBtn.getWidth())*2/7, Piece12.HEIGHT/2 - (newGameBtn.getHeight()/4));
        sb.draw(howToPlayBtn,(Piece12.WIDTH/2)-(newGameBtn.getWidth())*9/7, Piece12.HEIGHT/2 - (newGameBtn.getHeight()*2)+ newGameBtn.getHeight()/3);
        sb.draw(creditsBtn,(Piece12.WIDTH/2)+(newGameBtn.getWidth())*2/7, Piece12.HEIGHT/2 - (newGameBtn.getHeight()*2)+ newGameBtn.getHeight()/3);
        sb.draw(rateUsBtn, rateUsBtn.getWidth()/2, Piece12.HEIGHT - rateUsBtn.getWidth()*3/2 );
        sb.draw(shopBtn, shopBtn.getWidth()/2,  shopBtn.getWidth()/2 );
        sb.draw(dailyMissionBtn, Piece12.WIDTH - settingsBtn.getWidth()*3/2, Piece12.HEIGHT - rateUsBtn.getWidth()*3/2 );
        sb.draw(settingsBtn, Piece12.WIDTH - settingsBtn.getWidth()*3/2, settingsBtn.getWidth()/2);
        sb.end();
    }

    @Override
    public void dispose() {

        background.dispose();
        logo.dispose();
        newGameBtn.dispose();
        leaderboardBtn.dispose();
        howToPlayBtn.dispose();
        creditsBtn.dispose();
        rateUsBtn.dispose();
        shopBtn.dispose();
        dailyMissionBtn.dispose();
        settingsBtn.dispose();

    }
}