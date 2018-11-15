/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.chemeng.GUI;
import com.chemeng.gameobjects.UnitOpType;
import com.chemeng.gameobjects.components.PhysicsComponent;
import com.chemeng.gameobjects.components.TiledComponents.Operut;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class OperutButton extends TextButton {
    
    private static final String TAG = OperutButton.class.getName();
    
    protected TiledLevelState level;    
    
    public OperutButton(final Operut operut, final TiledLevelState level, Actor actor) {
        super(operut.toString(), GUI.getInstance().getSkin());
        this.level = level;
        
        actor.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {                
                if(isOver() && button == 0){                    
                    level.activeFactory(operut);
                }
            }
        });        
        getLabel().setFontScale(0.7f);        
    }
    
    /*
    
    Tirar esta verificação de custos daqui e jogar diretamente para o level;
    daí, caso haja dinheiro, a Factory é ativada de fato.
    
    */
    
    public boolean hasMoney(float x){
        if(level.getCash().hasMoney(x)){            
            return true;
        } else {
            /*
            TOCA SOM DE ERRO DE NÃO TER DINHEIRO
            */
            Gdx.app.log(TAG, "Não há dinheiro suficiente!");            
            return false;
        }
    }
    

}
