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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chemeng.GUI;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class BuildWindow extends Window {
    
    private TiledLevelState level;
    
    private static final WindowStyle windowStyle;
    
    static {
        //Texture tex = new Texture(Gdx.files.internal("img/gui/buildWindow_rev_0.png"), true);
        Texture tex = new Texture(Gdx.files.internal("img/window_background_4.png"), true);        
        TextureRegion texture = new TextureRegion(tex);
        windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, new TextureRegionDrawable(texture));        
    }
    
    public BuildWindow(TiledLevelState level) {        
        super("", windowStyle);
        this.level = level;
        
        setSize(500, 300);
        setVisible(true);
        setMovable(true);
        setPosition(Gdx.graphics.getWidth()/2 - this.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - this.getHeight()/2);
        
        setClip(false);
        setTransform(true);
        //this.setMovable(true);
        
        super.addListener(new InputListener(){
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                removeMe();
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
    }
    
    public void removeMe(){
        super.remove();
    }
    
}
