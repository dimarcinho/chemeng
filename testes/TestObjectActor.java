/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.chemeng.testes.TestObjectPhysics.State;

/**
 *
 * @author Marcio
 */
public class TestObjectActor extends Actor {
    
    private TestObject test;
    private Actor areaEntrada, areaSaida;
    private Group actors;
    
    public TestObjectActor(TestObject test){
        this.test = test;
        
        float x, y, width, height;
        x = test.x;
        y = test.y;
        width = test.width;
        height = test.height;
        
        setBounds(x, y, width, height);    
        
        addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                
                //System.out.println("Mouse over TestObject");
                
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                
                System.out.println("TestObjectActor clicado");
                
                return false;
            }
        
        });
        
        areaEntrada = new Actor();
        areaEntrada.setBounds(x, y+15, 15, 15);
        areaEntrada.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                clickEntrada();
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        areaSaida = new Actor();
        areaSaida.setBounds(x+49, y+15, 15, 15);
        areaSaida.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                clickSaida();
                
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        
        actors = new Group();
        
        actors.addActor(areaEntrada);
        actors.addActor(areaSaida);
        
    }

    public Actor getActor(){
        return this;
    }
    
    public Group getActors() {
        return actors;
    }
    
    public int clickEntrada(){
        test.getPhysics().state = State.NORMAL;
        test.getPhysics().setFire(100);
        System.out.println("Entrada clicada!");
        return 0;
    }
    
    public int clickSaida(){
        test.getPhysics().state = State.OFF;
        System.out.println("Saída clicada!");
        return 1;
    }
    
    public void update(TestObject test){
        //setBounds(test.x, test.y, test.width, test.height);
    }
    
}
