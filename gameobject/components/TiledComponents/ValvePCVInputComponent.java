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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chemeng.GUI;

/**
 *
 * @author marci
 */
public class ValvePCVInputComponent extends TiledInputComponent {
    
    private static final String TAG = ValvePCVInputComponent.class.getSimpleName();
    
    private ValvePCVPhysicsComponent physics;
    
    public ValvePCVInputComponent(float x, float y, float width, float height) {
        super(x, y, width, height);
        
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Valvula PCV clicada!");
                openDialog();
            }
            
        });
    }

    @Override
    public void update(GameObject obj) {
        if(super.events.size > 0){
            obj.sendGameEvent(events.removeFirst(), null);            
        }
        if(super.dispatcher.size > 0){
            EventDispatcher ed = dispatcher.removeFirst();
            obj.sendGameEvent(ed.getEvent(), ed.getObj());
        }
        
        physics = (ValvePCVPhysicsComponent) obj.getPhysics();
    }
    
    public void openDialog(){            
        
        new ValvePCVInputComponent.DialogPCV(
                        "Pressao desejada", GUI.getInstance().getSkin(), physics
                    ).show(GUI.getInstance().getUIStage());
    
    }
    
    public class DialogPCV extends Dialog {

        private TextField setpointText;
        
        public DialogPCV(String title, Skin skin, ValvePCVPhysicsComponent pc) {
            super(title, skin);
                        
            row().pad(10);
            text("Defina o Setpoint"); 
            row().pad(20);
            
            setpointText = new TextField("Pressure", skin);
            setpointText.setLayoutEnabled(true);
            setpointText.setMaxLength(5);
            
            DigitFilter filter = new DigitFilter();
            setpointText.setTextFieldFilter(filter);
            
            String str = String.valueOf(pc.getSetPoint()/101325d);
            setpointText.setText(str);            
            add(setpointText);
            row().pad(20);
            button("OK");
        }


        @Override
        protected void result(Object object) {            
            dispatcher.addLast(new EventDispatcher(GameEvent.SET_OPEN, setpointText.getText()));
        }
        
        private class DigitFilter implements TextFieldFilter {

            private char[] accepted;

            public DigitFilter() {
                accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
            }

            @Override
            public boolean acceptChar(TextField textField, char c) {
                for (char a : accepted)
                    if (a == c) return true;
                return false;
            }
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
