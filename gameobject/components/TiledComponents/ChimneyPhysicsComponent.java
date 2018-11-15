/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class ChimneyPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = ChimneyPhysicsComponent.class.getName();

    private Vector2 input;
    
    public ChimneyPhysicsComponent() {
        super(1,0);
        
        input = new Vector2(0,0);        
        
        super.getConnections().put(input, 
        new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.UP));        
    }

    @Override
    public void init() {
        
    }

    @Override
    public void balancoMassa() {
        
        
    }

    @Override
    public void balancoEnergia() {
        
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }

    
}
