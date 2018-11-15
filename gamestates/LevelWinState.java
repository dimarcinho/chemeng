/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.chemeng.gui.LevelWinWindow;

/**
 *
 * @author Marcio
 */
public class LevelWinState extends GameState {
    
    private static final String TAG = LevelWinState.class.getSimpleName();

    private Stage stage;
    private LevelWinWindow win;
    
    public LevelWinState(GameStateManager gsm){        
        this.gsm = gsm;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        win = new LevelWinWindow(gsm);
        win.setSize(500, 300);
        win.setVisible(true);
        win.setMovable(true);
        win.setPosition(Gdx.graphics.getWidth()/2 - win.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - win.getHeight()/2);
        
        stage.addActor(win);
        
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render() {
        
        //Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
    
    
}
