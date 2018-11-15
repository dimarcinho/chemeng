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
import com.badlogic.gdx.scenes.scene2d.Action.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
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
import com.chemeng.GUI;
import com.chemeng.gamestates.GameStateManager;
import com.chemeng.gamestates.MainMenuState;
import com.chemeng.gamestates.TiledLevelState;
import java.lang.Runnable;

/**
 *
 * @author marci
 */
public class LevelWinWindow extends Window {
    
    private static final String TAG = LevelWinWindow.class.getName();
    
    private final GameStateManager gsm;

    
    public LevelWinWindow(final GameStateManager gsm) { 
        super("Parabens!", GUI.getInstance().getSkin());
        this.gsm = gsm;        
        
        setClip(false);
        setTransform(true);
        setMovable(false);                
        
        this.add(new Label("Level concluido com sucesso!", GUI.getInstance().getSkin()));
        //this.row();
        this.pad(15).row();
        this.row();        
        
        TextButton textButton = new TextButton("Proximo nivel", GUI.getInstance().getSkin());
        textButton.addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "Proximo nivel clicado!");                
                gsm.setState(new TiledLevelState(gsm));
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        TextButton textButton2 = new TextButton("Continuar jogando...", GUI.getInstance().getSkin());
        textButton2.addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "Continuar  clicado!");
                LevelWinWindow.this.fade();
                gsm.removeState();
                TiledLevelState state = (TiledLevelState) gsm.getState();
                state.initInputs();
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        this.add(textButton);
        this.pad(15).row();
        this.add(textButton2);

    }

    private void fade(){        
        this.addAction(sequence(fadeOut(0.20f), run(new Runnable() {
            public void run () {
                    LevelWinWindow.this.remove();                    
            }
        })));
    }
    
}
