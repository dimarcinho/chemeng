/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.TiledWorld;

/**
 *
 * @author marci
 */
public abstract class GameObject {
    
    private static final String TAG = GameObject.class.getSimpleName();
    
    private final float TILE_SIZE = 64;
    
    private final int ID;
    private static int nextID = 0;
    
    public float x, y, width, height;    
    
    private TiledInputComponent ic;
    private TiledPhysicsComponent pc;
    private TiledGraphicsComponent gc;
    
    private final Vector2 tiledOrigin; //armazena as coordenadas em tile da origem
    
    public GameObject(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ID = ++nextID;
                
        tiledOrigin = new Vector2(x/TILE_SIZE, y/TILE_SIZE);
    }

    public Vector2 getTiledOrigin() {
        return tiledOrigin;
    }
    
    public void init(TiledInputComponent ic,
                    TiledPhysicsComponent pc,
                    TiledGraphicsComponent gc){
        this.ic = ic;
        this.pc = pc;
        this.gc = gc;
    }

    public TiledInputComponent getInput() {
        return ic;
    }

    public TiledPhysicsComponent getPhysics() {
        return pc;
    }

    public TiledGraphicsComponent getGraphics() {
        return gc;
    }
    
    public boolean collides(GameObject obj){
        Rectangle thisArea = new Rectangle(x, y, width, height);
        Rectangle otherArea = new Rectangle(obj.x, obj.y, obj.width, obj.height);
        return thisArea.overlaps(otherArea);
    }
    
    public boolean collides(Rectangle r){
        Rectangle thisArea = new Rectangle(x, y, width, height);
        Rectangle otherArea = r;
        return thisArea.overlaps(otherArea);
    }

    public final int getID() {
        return ID;
    }
    
    public abstract void input();
    public abstract void update(TiledWorld world, SpriteBatch batch);
    public abstract void render();
    public abstract void dispose();
    public abstract Operut getType();
    
    public final void sendGameEvent(GameEvent event, Object obj){
        ic.handleGameEvent(event, obj);
        pc.handleGameEvent(event, obj);
        gc.handleGameEvent(event, obj);
    }
}
