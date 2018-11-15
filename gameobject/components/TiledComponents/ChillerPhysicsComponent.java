/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.ElectricPower;
import com.chemeng.gameobjects.WaterSimpleSystem;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class ChillerPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = ChillerPhysicsComponent.class.getName();

    private Vector2 input, output;
    
    private double eff = 0.3f;
    private double HEAD_LOSS = 71000f; //Pa
    
    private double POWER = 330d; //kW
    
    private float U, A; //Coeficiente Global de Transferência de calor,
                        //Área de troca térmica
    
    
    public ChillerPhysicsComponent() {
        super(1, 1);
        
        input = new Vector2(0,3);
        output = new Vector2(0,2);        
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));        
        
        super.getConnections().put(output, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.LEFT));
        
    }

    @Override
    public void init() {
        
    }

    @Override
    public void balancoMassa() {
        
        Corrente conex0;        
        conex0 = getEntrada(0);        
        
        
        if(conex0 == null){
            
            getSaida(0).setFlow(0);            
            
        } else {
            
            Corrente c0;
            c0 = getEntrada(0);
            
            getSaida(0).setData(c0);
        }
        
    }

    @Override
    public void balancoEnergia() {
        
        Corrente in = getEntrada(0);
        Corrente out = getSaida(0);
                
        double flow0;
        flow0 = this.getEntrada(0).getFlow();
        
        
        
        if(flow0 == 0){
            //não faz nada
            
        } else {
            
            /*
            PRECISA INCLUIR A MUDANÇA DE FASE!!!!!
            */
            
            double pot = POWER, cp = in.getCp(); // W = J/s
            double flow = in.getFlow()/3600; // mol/s
            double T0, Tf;
            T0 = in.getTemperature();
            
            Tf = (flow*cp*T0 - pot*eff)/(flow*cp);
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
