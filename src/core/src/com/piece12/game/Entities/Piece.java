package com.piece12.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;


public class Piece{

    //properties
    private int pieceInfo[][];
    private int pieceNum;
    private boolean isMoveable;
    private Sprite pieceSprite;
    private float initialX, initialY;
    private Circle circles[];
    private float circleInits[][];
    private float rad;
    private int x1;
    private int y1;
    private float pieceWidth;
    private float pieceHeight;
    private int mode;
    private boolean flipped;
    boolean onBoard;

    /**constructors
     * This constructor creates and place a piece on screen and makes it clickable
     * @param pieceNo is the number of the piece
     * @param x is the position of the piece at x axis
     * @param y is the position of the piece at y axis
     */
    public Piece(int pieceNo, float x, float y ) {
        pieceNum = pieceNo;
        mode = 0;
        String temp = "piece" + pieceNum + ".png";
        pieceSprite = new Sprite(new Texture(temp));
        pieceSprite.setOriginCenter();
        pieceSprite.setCenter(x, y);
        initialX = x;
        initialY = y;
        isMoveable = true;
        flipped = false;
        onBoard = false;
        FileHandle pieceInformation = Gdx.files.internal("pieceInfo.txt");
        String info = pieceInformation.readString();
        String infos[] = info.split("--");
        info = infos[pieceNum - 1];
        info = info.trim();
        info = info.replaceAll( " ", "");
        info = info.replaceAll( "\r\n", "");
        int total = Integer.parseInt( info.charAt( 0 ) + "" );
        x1 = Integer.parseInt( info.charAt( 1 ) + "" );
        y1 = Integer.parseInt( info.charAt( 2 ) + "" );
        pieceInfo = new int[x1][y1];
        for (int i = 0; i < y1 ; i++) {
            for (int j = 0; j < x1 ; j++) {
                pieceInfo[j][i] = Integer.parseInt( info.charAt( 3 + j + i * x1  ) + "" );
            }
        }
        rad = pieceSprite.getWidth() / x1;
        rad = rad / 2;
        circles = new Circle[total];
        circleInits = new float[total][2];
        for( int i = 0; i < total; i++ ){
            circles[i] = new Circle();
            circles[ i ].setRadius( rad );
        }
        pieceWidth = pieceSprite.getWidth();
        pieceHeight = pieceSprite.getHeight();
        int k = 0;
        for (int i = 0; i < y1 ; i++) {
            for (int j = 0; j < x1 ; j++) {
                if( pieceInfo[j][i] == 1 ){
                    circleInits[k][0] = x - pieceWidth / 2 + ( 2 * j + 1 ) * rad;
                    circleInits[k][1] = y + pieceHeight / 2 - ( 2 * i + 1 ) * rad;
                    circles[ k ].setPosition( circleInits[k][0], circleInits[k][1]);
                    k++;
                }
            }
        }
    }
    //this method draws the piece sprite
    public void draw( SpriteBatch sb ){
        pieceSprite.draw( sb );
    }
    //this method rotates the piece
    public void changeMode() {
        //turn piece image
        mode++;
        if( mode == 4 ){
            mode = 0;
        }
        pieceSprite.rotate( 90 );
        int temp = x1;
        x1 = y1;
        y1 = temp;
        float temp2;
        temp2 = pieceHeight;
        pieceHeight = pieceWidth;
        pieceWidth = temp2;
        int temp1[][] = new int[x1][y1];
        for (int i = 0; i < y1 ; i++) {
            for (int j = 0; j < x1 ; j++) {
                temp1[j][i] = pieceInfo[ y1 - i - 1 ][ j ];
            }
        }
        pieceInfo = temp1;
        align();
    }

    //this method sets the moveability of piece
    public void setMoveable(boolean moveable) {
        isMoveable = moveable;
    }
    //this method returns true if the piece is moveable and false if not
    public boolean isMoveable() {
        return isMoveable;
    }

    //this method moves a piece according to the coordinate parameters x,y
    public void move( float x, float y ) {
        if (isMoveable) {
            pieceSprite.setCenter(x, y);
            align();
        }
    }
    //this method disposes the pieceSprite
    public void dispose(){
        pieceSprite.getTexture().dispose();
    }
    //this method flips a piece(mirror effect according to y axis)
    public void flip(){
        if( isMoveable ){
            flipped = !flipped;
            if( mode == 0 || mode == 2 ){
                pieceSprite.flip( true, false );
            }
            else{
                pieceSprite.flip( false, true );
            }
            int temp;
            for (int i = 0; i < y1 ; i++) {
                for (int j = 0; j < x1 / 2 ; j++) {
                    temp = pieceInfo[j][i];
                    pieceInfo[j][i] = pieceInfo[ x1 - j - 1 ][i];
                    pieceInfo[ x1 - j - 1][i] = temp;
                }
            }
            align();
        }
    }
    //collision detector method
    public boolean collision( Piece p ){
        Circle temp[] = p.getCircles();
        for (int i = 0; i < circles.length; i++ ){
            for (int j = 0; j < temp.length; j++ ){
                if( circles[i].x == temp[j].x && circles[i].y == temp[j].y ) {
                    return true;
                }
                else if( Math.sqrt( Math.pow( circles[i].x - temp[j].x, 2 ) + Math.pow( circles[i].y - temp[j].y, 2 ) ) <= 2 * rad - 4 ){
                    return true;
                }
            }
        }
        return false;
    }
    //this method returns the circles of the piece
    public Circle[] getCircles() {
        return circles;
    }
    //this method checks if given coordinates contains a circle and returns a value accordingly
    public boolean contains( float x, float y ) {
        for( int i = 0; i < circles.length; i++ ) {
            if (circles[i].contains(x, y)) {
                return true;
            }
        }
        return false;
    }
    //this method returns a piece to its first place
    public void reset(){
        for( int i = 0; i < circles.length; i++ ) {
            circles[ i ].setPosition( circleInits[i][0], circleInits[i][1] );
        }
        pieceSprite.setCenter( initialX, initialY );
        onBoard = false;
        setMoveable( true );
    }
    //this method sets the position of circles of piece
    private void align(){
        int k = 0;
        for (int i = 0; i < y1 ; i++) {
            for (int j = 0; j < x1 ; j++) {
                if( pieceInfo[j][i] == 1 ){
                    circles[ k ].setPosition( pieceSprite.getX() + pieceSprite.getWidth() / 2 - pieceWidth / 2 + ( 2 * j + 1 ) * rad, pieceSprite.getY() + pieceSprite.getHeight() / 2 + pieceHeight / 2 - ( 2 * i + 1 ) * rad);
                    k++;
                }
            }
        }
    }
    //this method transforms a piece
    public void transform(float x, float y){
        pieceSprite.translate(x, y);
        align();
    }
    //this method resets the piece and its conditions
    public void resetAll(){
        reset();
        while( mode != 0 ){
            changeMode();
        }
        if( flipped ){
            flip();
        }
    }

    public boolean onBoard(){
        return onBoard;
    }

    public void changeOnBoard( boolean b ){
        onBoard= b;
    }
}