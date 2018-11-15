/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.tilemap;

import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;

/**
 *
 * @author Márcio Brito
 */
public class TiledConnection {
 
    private static final String TAG = TiledConnection.class.getName();
    
    private Corrente c;
    private ConnectionType type;
    private Direction dir;
    
    /**
     *
     * @param c
     * @param type
     * @param dir
     */
    public TiledConnection(Corrente c, ConnectionType type, Direction dir){
        this.c = c;
        this.type = type;
        this.dir = dir;        
    }

    public Corrente getCorrente() {
        return c;
    }
    
    public ConnectionType getType() {
        return type;
    }

    public Direction getDirection() {
        return dir;
    }
    
    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }
    
}
