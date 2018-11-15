/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.ElectricPower;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class CompressorPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = CompressorPhysicsComponent.class.getName();

    private Vector2 input, output;
    
    private double EFF = 0.7d;
    private double POWER = 2100; //W
    
    
    public CompressorPhysicsComponent() {
        super(1,1);
        
        input = new Vector2(0,0);        
        output = new Vector2(5,2);
        
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
        
        double T0 = in.getTemperature();
        double P0 = in.getPressure();
        
        if(in.getFlow() != 0){

            double a = 2d/7d; //R/Cp
            double gama = 1d/(1d-a);
            double beta = (gama-1d)/gama;
            double PF = 0;
            double TF = 0;
            double R = 8.314; //constante universal dos gases
            double Ws = POWER;
            
            double k = ((Ws*beta)/(R*T0)+1);
            k = Math.pow(k, (1d/beta));
            
            PF = P0*k;
            
            double dH = Ws/EFF;
            
            TF = T0 + dH/in.getCp();
            
            out.setData(in);
            out.setPressure(PF);
            out.setTemperature((float) TF);
            
            ElectricPower ep = new ElectricPower();
            ep.consumePower(POWER/1000d);
                        
            
        } else {
            out.setFlow(0);
        }
        
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }
}
