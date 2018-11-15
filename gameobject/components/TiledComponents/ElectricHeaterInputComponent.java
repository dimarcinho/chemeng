/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author marci
 */
public class ElectricHeaterInputComponent extends TiledInputComponent {

    private static final String TAG = ElectricHeaterInputComponent.class.getSimpleName();
    
    public ElectricHeaterInputComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Electric Heater clicado!");                
            }
            
        });
    }

    @Override
    public void update(GameObject obj) {
        //to do 
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }

}
