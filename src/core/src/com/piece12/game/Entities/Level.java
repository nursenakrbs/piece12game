package com.piece12.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public class Level {

    //properties
    private int number;
    private boolean isLocked;
    private Preferences prefs;
    private String mode;
    private int win[];

    /**constructor
     * This constructor creates a level according to the level number and mode
     * @param levelNumber is Level Number
     * @param m this parameter is  for mode of the level
     */
    public Level( int levelNumber, String m ){
        number = levelNumber;
        mode = m;
        if( m.equals( "normal" ) ){
            prefs = Gdx.app.getPreferences("Level");
            boolean temp =  prefs.getBoolean("" + number, true );
            setLocked( temp );
            if( number == 1 ){
                setLocked( false );
            }
        }
        if( m.equals( "memory" ) ){
            win = new int[12];
        }
    }
    //this method returns true if the level is locked and false if it is not
    public boolean getLocked() {
        return isLocked;
    }
    //this method returns level number
    public int getNumber() {
        return number;
    }
    //this method locks or unlocks a level
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    //this method arranges the starting board according to the level number
    public void setBoardPiece( Board b, Piece p[]){
        FileHandle levelInformation;
        if( mode.equals( "memory" ) ){
            levelInformation = Gdx.files.internal("memoryLevelInfo.txt");
        }
        else{
            levelInformation = Gdx.files.internal("levelInfo.txt");
        }
        String info = levelInformation.readString();
        String infos[] = info.split("--");
        info = infos[number - 1];
        info = info.trim();
        info = info.replaceAll( " ", "");
        info = info.replaceAll( "\r\n", "");
        for( int i = 0; i < 12; i++ ) {
            int mode1 = Integer.parseInt(info.charAt(i * 5) + "");
            int flip = Integer.parseInt(info.charAt(i * 5 + 1) + "");
            int place = Integer.parseInt(info.charAt(i * 5 + 2) + "" + info.charAt(i * 5 + 3));
            int moveable = Integer.parseInt(info.charAt(i * 5 + 4) + "");
            for (int j = 0; j < mode1; j++) {
                p[i].changeMode();
            }
            if (flip == 1) {
                p[i].flip();
            }
            if (place != 55) {
                b.fill(p[i], place);
                if( mode.equals( "memory" ) ){
                    win[i] = place;
                }
            }
            if (moveable == 0) {
                p[i].setMoveable(false);
            }
        }
    }
    //this method opens next level
    public void openLevel(){
        prefs.putBoolean( "" + number, false );
        prefs.flush();
    }
    //this method returns mode
    public String getMode(){
        return mode;
    }
    //this method returns full board for memory mode
    public int[] getWin(){
        return win;
    }
}