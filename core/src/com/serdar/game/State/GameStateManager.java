package com.serdar.game.State;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

/**
 * Created by Serdar on 18.5.2018.
 */

public class GameStateManager {

    private java.util.Stack<State> states;

    public  GameStateManager(){
        states = new java.util.Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }
    public  void set(State state){
        states.pop();
        states.push(state);
    }
    public  void update(float dt){

        states.peek().update(dt);
    }
public  void render(SpriteBatch sb){
        states.peek().render(sb);
}




}
