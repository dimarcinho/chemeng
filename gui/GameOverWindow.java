/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.chemeng.GUI;
import com.chemeng.gamestates.GameState;
import com.chemeng.gamestates.GameStateManager;
import com.chemeng.gamestates.MainMenuState;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class GameOverWindow extends Window {
    
    private static final String TAG = GameOverWindow.class.getName();
    
    private static final WindowStyle windowStyle;
    private static final ImageButtonStyle closeButtonStyle;
    
    static {
        Texture tex = new Texture(Gdx.files.internal("img/window_background_4.png"), true);        
        TextureRegion texture = new TextureRegion(tex);
        windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, new TextureRegionDrawable(texture));        
        
        tex = new Texture(Gdx.files.internal("img/close_button_1.png"), true);
        texture = new TextureRegion(tex);
        closeButtonStyle = new ImageButtonStyle();
        closeButtonStyle.imageUp = new TextureRegionDrawable(texture);
        tex = new Texture(Gdx.files.internal("img/close_button_1_over.png"), true);
        texture = new TextureRegion(tex);
        closeButtonStyle.imageOver = new TextureRegionDrawable(texture);
        
    }
    
    public GameOverWindow(final GameStateManager gsm) {        
        super("", windowStyle);
        
        setClip(false);
        setTransform(true);
        this.setMovable(true);
        
        addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "GameOverWindow clicado!");                
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        super.align(Align.top);
        
        this.add(new Label("Game Over!", GUI.getInstance().getSkin()));
        //this.row();  
        this.pad(15).row();
        
        TextButton textButton = new TextButton("Reiniciar Level", GUI.getInstance().getSkin());
        textButton.addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {                
                gsm.setState(new TiledLevelState(gsm));                
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        TextButton buttonSair = new TextButton("Sair", GUI.getInstance().getSkin());
        buttonSair.addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {                              
                gsm.setState(new MainMenuState(gsm));                
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        this.add(textButton).row();        
        this.add(buttonSair).row();

    }

    
    
    
}
