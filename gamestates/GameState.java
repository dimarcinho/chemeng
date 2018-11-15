/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

/**
 *
 * @author Marcio
 */
public abstract class GameState {
    
    public GameStateManager gsm;
    
    public abstract void update();
    public abstract void render();
    public abstract void dispose();
    
}
