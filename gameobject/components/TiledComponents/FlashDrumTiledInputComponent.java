/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
/**
 *
 * @author marci
 */
public class FlashDrumTiledInputComponent extends TiledInputComponent {

    private static final String TAG = FlashDrumTiledInputComponent.class.getSimpleName();

    public FlashDrumTiledInputComponent(float x, float y, float width, float height) {
        super(x,y,width,height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Vaso de Flash clicado!");
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
