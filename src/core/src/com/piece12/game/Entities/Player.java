package com.piece12.game.Entities;
/*
    This class has not been implemented yet because there is no database
 */
public class Player {

    //properties
    private int playerId;
    private int coin;
    private int highScore;
    private String playerName;

    //constructors
    public Player(){
        //todo:fetch info from database
    }

    //methods
    public int getId(){
        return playerId;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin( int inc ){
        coin = coin + inc;
    }

    public String getName(){
        return playerName;
    }

    public void setName( String name ){
        playerName = name;
    }

}
