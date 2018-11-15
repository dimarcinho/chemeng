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
public class ElectricHeaterPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = ElectricHeaterPhysicsComponent.class.getName();

    private Vector2 input, output;
    
    private double eff = 0.9f;
    private double HEAD_LOSS = 71000f; //Pa
    
    private double POWER = 100d; // kW
    
    
    public ElectricHeaterPhysicsComponent() {
        super(1, 1);
        
        input = new Vector2(2,1);
        output = new Vector2(6,1);
        
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.DOWN));        
        
        super.getConnections().put(output, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.UP));

    }

    @Override
    public void init() {
        
    }

    @Override
    public void balancoMassa() {        

        Corrente in = getEntrada(0);
        Corrente out = getSaida(0);
        
        if(in.getFlow() != 0){
            out.setData(in);
        } else {
            out.setFlow(0);
        }
        
    }

    @Override
    public void balancoEnergia() {

        Corrente in = getEntrada(0);
        Corrente out = getSaida(0);
        
        if(in.getFlow() != 0){
            
            /*
            PRECISA INCLUIR A MUDANÇA DE FASE!!!!!
            */
            
            double pot = POWER, cp = in.getCp(); // W = J/s
            double flow = in.getFlow()/3600; // mol/s
            double T0, Tf;
            T0 = in.getTemperature();
            
            Tf = (pot + flow*cp*T0)/(flow*cp);
            ElectricPower ep = new ElectricPower();            
            ep.consumePower(POWER);
            
            
            out.setTemperature((float) Tf);
            
            /*
            PRECISA INCLUIR A MUDANÇA DE FASE!!!!!
            */
        }
        
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }
}
