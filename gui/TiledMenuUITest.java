/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.chemeng.gameobjects.old.ValvePCVPhysics;
import com.chemeng.gameobjects.old.ValveManualPhysics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.chemeng.GUI;
import com.chemeng.gameobjects.*;
import com.chemeng.gameobjects.UnitOpType;
import com.chemeng.gameobjects.components.PhysicsComponent;
import com.chemeng.gameobjects.components.TiledComponents.Operut;
import com.chemeng.gamestates.TiledLevelState;
/**
 *
 * @author Marcio
 */
public class TiledMenuUITest extends Window {

    private static final String TAG = TiledMenuUITest.class.getName();
    
    private Skin skin = GUI.getInstance().getSkin();;
    
    private TextButton btnMixer, btnSplitter, btnHorizontalDrum, btnTanque, btnPermutador, btnPump,
                        btnChimney,
                        btnValveOnOff, btnValveManual, btnPCV;
    private TextButton btnSource, btnDestruir;
    
    private TiledLevelState level;
    
    public TiledMenuUITest(TiledLevelState level){
        super("Equipamentos", GUI.getInstance().getSkin());
        this.level = level;
        
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("TiledMenuUITest clicado!");                
                return super.touchDown(event, x, y, pointer, button);
            }
            
        });
        
        this.setWidth(200);
        this.setHeight(570);
        this.setPosition(Gdx.graphics.getWidth()-120, Gdx.graphics.getHeight()-this.getHeight());
        this.setMovable(false);
        this.setResizable(false);
        
        btnDestruir = new TextButton("Destruir", skin);
        btnDestruir.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {                
                getLevel().activeObjectDestroyer();
                return true;
            } 
        });
        this.align(Align.top);
        
        Operut[] operuts = Operut.values();
        for(Operut op : operuts){
            
            if(!op.equals(Operut.LINE)){
            
                add(new OperutButton(op, level, this));                
                
            } else {
                add(new OperutButton(op, level, this){
                
                    
                    
                });
            }
            row();
        }
        add(btnDestruir).pad(5);
        row();
    }
    
    private TiledLevelState getLevel(){
        return level;
    }
    
    public boolean hasMoney(float x){
        if(level.getCash().hasMoney(x)){            
            return true;
        } else {
            /*
            TOCA SOM DE ERRO DE NÂO TER DINHEIRO
            */
            System.out.println("Não há dinheiro suficiente!");
            return false;
        }
    }
    
    public void createObject(UnitOpType typeOperut, PhysicsComponent physics, float cost){
        //level.activeObjectFactory(typeOperut, physics, cost);
    }
    
    public Actor getActors(){
        return this;
    }

    public void invokeObjectDestroyer(){
        //level.activeObjectDestroyer();
    }
    
}
