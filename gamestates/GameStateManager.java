/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import java.util.Stack;

public class GameStateManager {
    
    private static final String TAG = GameStateManager.class.getName();
    
    private Stack<GameState> states;
    
    public GameStateManager(){        
        states = new Stack<GameState>();        
    }
    
    public void addState(GameState state){
        states.push(state);
    }
    
    public void setState(GameState state){        
        
        if(!states.empty()){
            //dispose();
            states.pop();
        }
        states.push(state);
    }
    
    public void removeState(){
        if(!states.empty()) {
            //dispose();
            states.pop();
        }
    }
    
    public GameState getState(){
        return states.peek();
    }
    
    public void update(){
        states.peek().update();
    }
    
    public void render(){
        states.peek().render();
    }
    
    public void dispose(){
        states.peek().dispose();        
        Gdx.app.log(TAG, "Dispose:"+getState().toString());
    }
    
}
