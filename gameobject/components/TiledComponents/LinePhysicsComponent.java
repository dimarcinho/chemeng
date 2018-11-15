/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.tilemap.TiledConnection;
import com.chemeng.tilemap.TiledConnection.Direction;

/**
 *
 * @author marci
 */
public class LinePhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = LinePhysicsComponent.class.getName();
    
    public LinePhysicsComponent(Vector2 v0, ConnectionType type0, Direction d0,
                                Vector2 vf, ConnectionType typef, Direction df) {
        super(1, 1);        
        
        if(type0.equals(ConnectionType.ENTRADA)){
            super.getConnections().put(v0, 
                new TiledConnection(super.getEntrada(0),type0, d0));
            super.getConnections().put(vf, 
                new TiledConnection(super.getSaida(0),typef, df));
        } else {
            super.getConnections().put(vf, 
                new TiledConnection(super.getEntrada(0),typef, df));
            super.getConnections().put(v0, 
                new TiledConnection(super.getSaida(0),type0, d0));
        }
    }

    @Override
    public void init() {}

    @Override
    public void balancoMassa() {
        
        getSaida(0).setData(getEntrada(0));
        getSaida(0).setPressure(getEntrada(0).getPressure()*0.999); //perda de carga
    }

    @Override
    public void balancoEnergia() {
        /*
        implementar a perda de carga
        */
    }


    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }
    

}
