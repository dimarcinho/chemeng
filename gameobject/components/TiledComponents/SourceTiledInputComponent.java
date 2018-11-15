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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chemeng.GUI;
import com.chemeng.gameobjects.old.ValveManualInput;

/**
 *
 * @author marci
 */
public class SourceTiledInputComponent extends TiledInputComponent {

    private static final String TAG = SourceTiledInputComponent.class.getSimpleName();    
    
    private boolean change = false;
    
    GameObject obj = null;
    
    public SourceTiledInputComponent(float x, float y, float width, float height) {
        super(x,y,width,height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);                
                Gdx.app.log(TAG, "Source clicada!");
                Gdx.app.log(TAG, "Next Object: "+obj);
            }
        });
        
        Actor areaSaida = new Actor();
        super.getActors().addActor(areaSaida);        
        areaSaida.setBounds(x+64+16, y+16, 32f, 32f);
        
        areaSaida.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "area de teste clicada!");
                change = true;
                return super.touchDown(event, x, y, pointer, button);                
            }  
        });
        
    }

    @Override
    public void update(GameObject obj) {
        if(change){
            changeOpen(obj);
            change = false;
        }
        if(dispatcher.size > 0){
            EventDispatcher ed = dispatcher.removeFirst();
            obj.sendGameEvent(ed.getEvent(), ed.getObj());
        }        
    }
    
    public void changeOpen(GameObject obj){
        SourceTiledPhysicsComponent pc = (SourceTiledPhysicsComponent) obj.getPhysics();
        new SourceTiledInputComponent.DialogImpl(
                        "Abertura de Valvula", GUI.getInstance().getSkin(), pc
                    ).show(GUI.getInstance().getUIStage());
    }
    
    public class DialogImpl extends Dialog {

        private Slider aberturaUI;
        
        public DialogImpl(String title, Skin skin, SourceTiledPhysicsComponent pc) {
            super(title, skin);
                        
            row().pad(10);
            text("Selecione Abertura"); 
            row().pad(20);
            aberturaUI = new Slider(0f,1f,0.01f,false,skin);
            aberturaUI.setLayoutEnabled(true);
            aberturaUI.setValue((float) pc.getAbertura());
            add(aberturaUI);
            row().pad(20);
            button("OK");            
        }
        
        public DialogImpl(String title, Skin skin, float abertura){
            super(title, skin);
            
            row().pad(10);
            text("Selecione Abertura"); 
            row().pad(20);
            aberturaUI = new Slider(0f,1f,0.01f,false,skin);
            aberturaUI.setLayoutEnabled(true);
            aberturaUI.setValue(abertura);
            add(aberturaUI);
            row().pad(20);
            button("OK");            
        }

        @Override
        protected void result(Object object) {            
            dispatcher.addLast(new EventDispatcher(GameEvent.SET_OPEN, aberturaUI.getValue()));
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
