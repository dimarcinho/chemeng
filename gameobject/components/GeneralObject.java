/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Marcio
 */
public abstract class GeneralObject {
    
    private InputComponent input;
    private PhysicsComponent physics;
    private GraphicsComponent graphics;
    
    public float x, y, width, height;
    public Vector2 position;
    public Rectangle area;
    
    public GeneralObject(float x,
                         float y,
                         float width,
                         float height,
                         InputComponent ic, 
                         PhysicsComponent pc, 
                         GraphicsComponent gc){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.input = ic;
        this.physics = pc;
        this.graphics = gc;
        
        //Com esta inicialização posterior, não é necesssário passar x,y,w,d
        //para o constructor do InputComponent
        /*
        -------- DANDO ERRO, ACTORS ESTÃO DESLOCANDO DAS FIGURAS --------
        */
        if(!input.isInitializated()){
            input.initActors(x, y, width, height);
        }
        
        position = new Vector2(x, y);
        area = new Rectangle(x,y,width,height);
    };
    
    public abstract void input();
    public void update(){
        this.getInputActor().update(this);
        this.getPhysics().update(this);
        this.getGraphics().update(this);
    };
    public abstract void render();
    public abstract void dispose();
    
    public InputComponent getInputActor(){
        return input;
    }
    
    public PhysicsComponent getPhysics(){
        return physics;
    }
    public GraphicsComponent getGraphics(){
        return graphics;
    }

    public Vector2 getPosition() {
        return position;
    }
    
}
