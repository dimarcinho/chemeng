/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.ElectricPower;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class PumpTiledPhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = PumpTiledPhysicsComponent.class.getName();
    
    private Vector2 input, output;
    
    private double POWER, DELTA_P, EFF, DEPRECIATION;
    
    
    
    public PumpTiledPhysicsComponent() {
        super(1, 1);
        input = new Vector2(0,0);
        output = new Vector2(1,0);
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(output, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.RIGHT));
        
        POWER = 10f; //kW        
        EFF = 0.75d;
        
        DEPRECIATION = 0.000001d;
        
        /*
        ---------- TESTANDO ----------        
        */
        //super.getEntrada(0).free = false;
        /*
        ---------- TESTANDO ----------
        */
    }

    @Override
    public void init() {
        //to do
    }

    @Override
    public void balancoMassa() {

    }

    @Override
    public void balancoEnergia() {
        
        /*
        Pot = Q*deltaP/3600
            Pot - kW
            Q - m3/h
            deltaP - kPa
        */  
        Corrente in = this.getEntrada(0);       
        Corrente out = this.getSaida(0);
        
        if(in.free){

            double flow_in = in.getFlow(); //converter para m³/h
            double P0 = in.getPressure();

            DELTA_P = EFF*3600*(POWER)/flow_in; //em kPa        

            out.setData(in);
            out.setPressure(P0+DELTA_P*1000f);
            
            ElectricPower ep = new ElectricPower();            
            ep.consumePower(POWER);
            
            //PositiveDisplacement();
            
            
        } else {
            in.setFlow(0);
            out.setFlow(0);
        }
        
        EFF -= DEPRECIATION;
        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
    private void PositiveDisplacement(){
        
        float Q = 80; //vazao da bomba de deslocamento positivo
        
        Corrente in = this.getEntrada(0);
        Corrente out = this.getSaida(0);
                
        double P0 = in.getPressure();
        
        DELTA_P = EFF*3600*(POWER)/Q;
        out.setData(in);
        out.setFlow(Q);
        out.setPressure(P0+DELTA_P*1000f);
        
        ElectricPower ep = new ElectricPower();            
        ep.consumePower(POWER);
    }
    
}
