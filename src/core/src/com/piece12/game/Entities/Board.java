package com.piece12.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Board {

    //properties
    private Circle holes[];
    private Texture image;
    private float initialX;
    private float initialY;
    private final int x1 = 11;
    private final int y1 = 5;
    private float rad;
    private int total;

    /**constructors
     * This constructors creates a board with holes to put pieces on it
     * it also creates the board image
     */

    public Board(){
        image = new Texture( "board.png" );
        rad = image.getWidth() / x1;
        rad = rad / 2;
        total = x1 * y1;
        holes = new Circle[ total ];
        for( int i = 0; i < total; i++ ){
            holes[i] = new Circle();
            holes[ i ].setRadius( rad );
        }
    }
    public Board( float x, float y ){
        image = new Texture( "board.png" );
        initialX = x;
        initialY = y;
        rad = image.getWidth() / x1;
        rad = rad / 2;
        total = x1 * y1;
        holes = new Circle[ total ];
        for( int i = 0; i < total; i++ ){
            holes[i] = new Circle();
            holes[ i ].setRadius( rad );
            holes[i].setPosition( initialX + ( 2 * ( i % 11 ) + 1 ) * rad, initialY + image.getHeight() - ( 2 * ( i / 11 ) + 1 ) * rad );
        }
    }

    //methods
    //This method draws board image
    public void draw(SpriteBatch sb){
        sb.draw( image, initialX, initialY );
    }
    //this method returns the board image
    public Texture getTexture(){
        return image;
    }
    //this method helps to locate pieces properly on the board
    public void magnet( Piece p ){
        Circle temp[] = p.getCircles();
        double min = 1000;
        float diffX = 0;
        float diffY = 0;
        double diff;
        for( int i = 0; i < total; i++ ){
            diff = Math.sqrt( Math.pow( holes[i].x - temp[1].x, 2 ) + Math.pow( holes[i].y - temp[1].y, 2 ) );
            if( min > diff ){
                min = diff;
                diffX = holes[i].x - temp[1].x;
                diffY = holes[i].y - temp[1].y;
            }
        }
        p.transform( diffX, diffY );
        p.changeOnBoard( true );
    }
    //This method puts a piece on the board
    public void fill( Piece p, int place ){
        Circle temp[] = p.getCircles();
        float diffX = 0;
        float diffY = 0;
        diffX = holes[place].x - temp[0].x;
        diffY = holes[place].y - temp[0].y;
        p.transform( diffX, diffY );
        p.changeOnBoard( true );
    }
    //This method checks if all the pieces on the board or not except for memory mode
    public boolean win( Piece pieces[] ){
        Circle circles[];
        boolean temp = false;
        for( int i = 0; i < pieces.length; i++ ){
            if( pieces[i].isMoveable() ){
                circles = pieces[i].getCircles();
                for( int j = 0; j < circles.length; j++ ){
                    for( int k = 0; k < total; k++ ){
                        if( circles[j].overlaps( holes[k] ) ){
                            temp = true;
                            break;
                        }
                    }
                    if( temp == false ){
                        return false;
                    }
                    temp = false;
                }
            }
        }
        return true;
    }
    //this method set the position of holes on the board
    public void setPosition( float x, float y){
        initialX = x;
        initialY = y;
        for( int i = 0; i < total; i++ ){
            holes[i].setPosition( initialX + ( 2 * ( i % 11 ) + 1 ) * rad, initialY + image.getHeight() - ( 2 * ( i / 11 ) + 1 ) * rad );
        }
    }
    //this method checks if the pieces at right place on the board on memory mode
    public boolean checkWinCondition( Piece pieces[],int w[] ){
        Circle circles[];
        for( int i = 0; i < pieces.length; i++ ){
            circles = pieces[i].getCircles();
            if( !circles[0].overlaps( holes[w[i]] ) ){
                return false;
            }
        }
        return true;
    }
}
