/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Queue;
/**
 *
 * @author marci
 */
public class PumpTiledInputComponent extends TiledInputComponent {

    private static final String TAG = PumpTiledInputComponent.class.getSimpleName();
    
//    boolean clicked = false;
    
    public PumpTiledInputComponent(float x, float y, float width, float height) {
        super(x,y,width,height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);                
                Gdx.app.log(TAG, "Bomba clicada!");
//                clicked = true;
            }
            
        });
        
    }

    @Override
    public void update(GameObject obj) {        
        if(events.size > 0){            
            obj.sendGameEvent(events.removeFirst(), null);
        }
//        if(clicked){
//            PumpTiledPhysicsComponent physics = (PumpTiledPhysicsComponent) obj.getPhysics();
//            boolean x = physics.getEntrada(0).free;
//            physics.getEntrada(0).free = !x;
//            clicked = false;
//        }
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
