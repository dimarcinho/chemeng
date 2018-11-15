/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Marcio
 */
public abstract class InputComponent extends Actor {

    private boolean initializated = false;
       
    private Group actors;
    
    public InputComponent(float x, float y, float width, float height){               
        setBounds(x, y, width, height);
        actors = new Group();
        initializated = true;
    }
    public InputComponent(){        
        actors = new Group();
    }
    
    public void initActors(float x, float y, float width, float height){        
        setBounds(x, y, width, height);
        initializated = true;
    }

    public boolean isInitializated() {
        return initializated;
    }
    
    public Actor getActor(){
        return this;
    }
    
    public Group getActors() {
        return actors;
    }
    
    public void addToStage(Stage stage){        
        stage.addActor(actors);
        stage.addActor(this);
    }    
    
    public abstract void update(GeneralObject obj);    
}
