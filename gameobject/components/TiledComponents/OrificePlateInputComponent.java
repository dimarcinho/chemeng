/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author marci
 */
public class OrificePlateInputComponent extends TiledInputComponent {
    
    private static final String TAG = OrificePlateInputComponent.class.getSimpleName();
    
    public OrificePlateInputComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Orifice Plate clicado!");
            }
            
        });
    }

    @Override
    public void update(GameObject obj) {
        if(super.events.size > 0){            
            obj.sendGameEvent(events.removeFirst(), null);            
        }
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
