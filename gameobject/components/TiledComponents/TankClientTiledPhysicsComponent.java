/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.corporative.ContratoManager;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class TankClientTiledPhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = TankClientTiledPhysicsComponent.class.getName();
    
    private Vector2 input;
    
    private ContratoManager cm;
    
    public TankClientTiledPhysicsComponent(ContratoManager cm) {
        super(1, 0);
        this.cm = cm;
        input = new Vector2(0,1);        
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
    }
    
    public void setContratoManager(ContratoManager cm){
        this.cm = cm;
    }

    @Override
    public void init() {
        //to do
    }

    @Override
    public void balancoMassa() {
        try {
            cm.receive(this.getEntrada(0));            
        } catch (Exception e){            
            Gdx.app.log(TAG, e.toString());
        }
    }

    @Override
    public void balancoEnergia() {

        //to do
        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
