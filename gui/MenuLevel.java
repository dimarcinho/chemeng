/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.chemeng.GUI;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class MenuLevel extends Table {
    
    private static final String TAG = MenuLevel.class.getName();
    
    private TiledLevelState level;
    
    private Image contratos, build, config;
    
    public MenuLevel(final TiledLevelState level){
        this.level = level;
        
        float MENU_HEIGHT = 192;
        setBounds(0,0,64,MENU_HEIGHT);
        this.setPosition(0, Gdx.graphics.getHeight()-MENU_HEIGHT);
        
        Texture texture = new Texture(Gdx.files.internal("img/icon2_contrato_64x64.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        contratos = new Image(new TextureRegion(texture));        
        contratos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "Imagem -- contratos -- clicada em MenuLevel");
                getLevel().createContratosWindow();                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        
        texture = new Texture(Gdx.files.internal("img/gui/factoryButton.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        build = new Image(new TextureRegion(texture));        
        build.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GUI.getInstance().getUIStage().addActor(new BuildWindow(level));
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        texture = new Texture(Gdx.files.internal("img/gui/gear64tempo.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        config = new Image(new TextureRegion(texture));        
        config.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "ConfigImg -- teste -- clicada em MenuLevel");
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        
        
        addActor(contratos);
        addActor(build);
        addActor(config);
        
        build.setPosition(0, 64);
        contratos.setPosition(0, 128);


    }
    
    public void callBuildWindow(TiledLevelState level){
        
    }

    public TiledLevelState getLevel() {
        return level;
    }
    
    
}
