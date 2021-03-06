/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class OrificePlatePhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = OrificePlatePhysicsComponent.class.getName();

    private Vector2 input, output;
    private boolean opened = true;
    
    public OrificePlatePhysicsComponent() {
        super(1,1);
        
        input = new Vector2(0,0);        
        output = new Vector2(1,0);
        
        super.getConnections().put(input, 
        new TiledConnection(super.getEntrada(0),
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
        
        getSaida(0).setData(getEntrada(0));
        getSaida(0).setPressure(getEntrada(0).getPressure()*0.80); //queda de pressão
        
    }

    @Override
    public void balancoEnergia() {
        
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            
            case VALVE_ON_OFF_CHANGE:
                setOpened(!opened);
                break;
            
            default:
                //nao faz nada;
        }
    }
    
}
