package com.piece12.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;
//This class holds a stack of states and manage the navigation of the game
public class GameStateManager {

    //properties
    private Stack<State> states;

    //constructor
    public GameStateManager(){
        states = new Stack<State>();
    }

    //methods
    public void push( State state ){
        states.push( state );
    }

    public void pop(){
        states.pop().dispose();
    }

    public boolean isEmpty(){
        if ( states.empty())
            return true;
        return false;
    }
    public void update( float dt ){
        states.peek().update( dt );
    }

    public void render(SpriteBatch sb ){
        states.peek().render( sb );
    }
}
