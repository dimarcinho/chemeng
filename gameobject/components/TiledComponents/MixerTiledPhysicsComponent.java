/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.chemeng.Calculator;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class MixerTiledPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = MixerTiledPhysicsComponent.class.getName();

    private Vector2 inputTop, inputBottom, output;
    
    public MixerTiledPhysicsComponent() {
        super(2,1);
        
        inputTop = new Vector2(0,2);
        inputBottom = new Vector2(0,0);
        output = new Vector2(0,1);
        
        super.getConnections().put(inputTop, 
        new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(inputBottom, 
                new TiledConnection(super.getEntrada(1),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(output, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.RIGHT));
    }

    @Override
    public void init() {
        
    }

    @Override
    public void balancoMassa() {
        
        Corrente inA, inB, out;
        
        inA = getEntrada(0);
        inB = getEntrada(1);
        out = Calculator.mix(inA, inB);
        
        if(inA.getFlow() == 0){
            getSaida(0).setData(inB);
        } else if(inB.getFlow() == 0){
            getSaida(0).setData(inA);
        } else {
            getSaida(0).setData(out);
        }
        
        
        
    }

    @Override
    public void balancoEnergia() {
        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
