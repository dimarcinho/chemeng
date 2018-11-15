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
public class LineInputComponent extends TiledInputComponent {

    private static final String TAG = LineInputComponent.class.getSimpleName();
        
    public LineInputComponent(float x, float y, float width, float height) {
        super(x,y,width,height);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Linha clicada!");
            }
            
        });
        
        /*
        Actor testActor = new Actor();
        testActor.setBounds(0, 0, 64, 64); //-------> consertar!
        testActor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "testActor clicado!");
            }            
        });
        super.getActors().addActor(testActor);
        
        //tentar por aqui estabelecer uma lógica para ao selecionar um componente,
        //selecionar todos
        InputListener listenerDoGroup = new InputListener();
        super.getActors().addListener(listenerDoGroup);
        */
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
