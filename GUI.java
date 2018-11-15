/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 * @author Marcio
 */
public class GUI {
    
    private static final String TAG = GUI.class.getName();
    
    //-------- SINGLETON -----------
    
    private static GUI instance;
    
    private Skin skin;
    private TextureAtlas uiAtlas;
    private BitmapFont font;
    
    private Stage GameStage, UIStage;
    
    private GUI(){        
        uiAtlas = new TextureAtlas(Gdx.files.internal("skin/sgx/sgx-ui.atlas"));
        skin = new Skin(Gdx.files.internal("skin/sgx/sgx-ui.json"), uiAtlas);
        font = new BitmapFont();
    }

    public BitmapFont getFont() {
        return font;
    }
    
    public Skin getSkin(){
        return skin;
    }
    
    public synchronized static GUI getInstance(){
        //Lazy initialization
        if(instance == null){
            instance = new GUI();
        }
        return instance;
    }

    public Stage getGameStage() {
        return GameStage;
    }

    public void setGameStage(Stage GameStage) {
        this.GameStage = GameStage;
    }

    public Stage getUIStage() {
        return UIStage;
    }

    public void setUIStage(Stage UIStage) {
        this.UIStage = UIStage;
    }
    
}
