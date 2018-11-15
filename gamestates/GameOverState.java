/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chemeng.GUI;
import com.chemeng.gui.GameOverWindow;

/**
 *
 * @author Marcio
 */
public class GameOverState extends GameState {
    
    private static final String TAG = GameOverState.class.getSimpleName();

    private Stage stage;    

    private GameOverWindow gow;
    
    public GameOverState(GameStateManager gsm){        
        this.gsm = gsm;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        gow = new GameOverWindow(gsm);
        gow.setSize(500, 300);
        gow.setVisible(true);
        gow.setMovable(true);
        gow.setPosition(Gdx.graphics.getWidth()/2 - gow.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - gow.getHeight()/2);
        
        stage.addActor(gow);
        
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
