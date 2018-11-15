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

/**
 *
 * @author Marcio
 */
public class PauseState extends GameState {

    private Stage stage;
    private Skin skin;
    
    private TextButton pauseButton;

    
    public PauseState(GameStateManager gsm){
        System.out.println("Constructor do "+this.toString()+" criado");
        this.gsm = gsm;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        
        skin = GUI.getInstance().getSkin();
        
        pauseButton = new TextButton("Jogo Pausado", skin);
        
        pauseButton.addListener(new ClickListener(){            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume();                
            }            
        });
        
        pauseButton.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        pauseButton.setSize(150f, 80f);
        stage.addActor(pauseButton);
        
    }
    
    private void resume(){
    
        gsm.removeState();
        TiledLevelState x = (TiledLevelState) gsm.getState();
        x.initInputs();        
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
