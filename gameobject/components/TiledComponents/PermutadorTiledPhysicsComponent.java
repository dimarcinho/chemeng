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
public class PermutadorTiledPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = PermutadorTiledPhysicsComponent.class.getName();

    private Vector2 input_A, output_A, input_B, output_B;
    
    private double efficiency = 1.0f;
    private double HEAD_LOSS = 71000f; //Pa
    
    private float U, A; //Coeficiente Global de Transferência de calor,
                        //Área de troca térmica
    
    
    public PermutadorTiledPhysicsComponent() {
        super(2, 2);
        
        input_A = new Vector2(0,1);
        output_A = new Vector2(0,0);
        
        input_B = new Vector2(2,1);        
        output_B = new Vector2(6,0);
        
        super.getConnections().put(input_A, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(input_B, 
                new TiledConnection(super.getEntrada(1),
                        ConnectionType.ENTRADA, TiledConnection.Direction.DOWN));
        
        super.getConnections().put(output_A, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.LEFT));
        
        super.getConnections().put(output_B, 
                new TiledConnection(super.getSaida(1),
                        ConnectionType.SAIDA, TiledConnection.Direction.DOWN));
        
                
        U = 350; // W/m²K
        A = 1.5f; //m²
        /*
        
        Considerar 1 com 1 e 2 com 2, ou seja, Entrada(0) manda pra Saída(0);
        Entrada(1) manda pra Saída(1)
        
        */
    }

    @Override
    public void init() {
        
    }

    @Override
    public void balancoMassa() {
        
        Corrente conex0, conex1;        
        conex0 = this.getEntrada(0);
        conex1 = this.getEntrada(1);
        
        
        if(conex0 == null || conex1 == null){
            
            this.getSaida(0).setFlow(0);
            this.getSaida(1).setFlow(0);
            
        } else if(this.getEntrada(0) == null){            
            
            this.getSaida(0).setFlow(0);
            
        } else if(this.getEntrada(1) == null){
            
            this.getSaida(1).setFlow(0);
            
        } else {
            
            Corrente c0, c1;
            c0 = this.getEntrada(0);
            c1 = this.getEntrada(1); 

            this.getSaida(0).setData(c0);
            this.getSaida(1).setData(c1);
            
        }
        
    }

    @Override
    public void balancoEnergia() {
        
                
        double flow0, flow1;
        flow0 = this.getEntrada(0).getFlow();
        flow1 = this.getEntrada(1).getFlow();
        
        
        if(flow0 == 0 || flow1 == 0){
            //não faz nada
        } else if(flow0 == 0){
            
            Corrente c1 = this.getEntrada(1);        
            this.getSaida(1).setData(c1);
            
            double P = this.getSaida(1).getPressure();
            
            P -= HEAD_LOSS;
            if(P <= 0){
                this.getSaida(1).setFlow(0);
            } else {
                this.getSaida(1).setPressure(P);
            }
            
            
        } else if(flow1 == 0){
            
            Corrente c0 = this.getEntrada(0);
            this.getSaida(0).setData(c0);
            
            double P = this.getSaida(0).getPressure();
            
            P -= HEAD_LOSS;
            if(P <= 0){
                this.getSaida(0).setFlow(0);
            } else {
                this.getSaida(0).setPressure(P);
            }
            
        } else {
            
            metodoNUT();
            
        }
        
    }
    
    public void metodoNUT(){
        Corrente c0, c1;
        c0 = this.getEntrada(0);
        c1 = this.getEntrada(1);
        
        //Método NUT-e
        double Tqe, Tfe, Tqs, Tfs;
        double Cq, Cf, Cr, Cmin, Cmax;
        double q, qmax, NUT, e;
        
        if(c0.getTemperature() > c1.getTemperature()){ //corrente c0 é a mais quente
            Cq = (float) (c0.getFlow()*c0.getCp());
            Cf = (float) (c1.getFlow()*c1.getCp());
        } else {            
            Cq = (float) (c1.getFlow()*c1.getCp());
            Cf = (float) (c0.getFlow()*c0.getCp());
        }        
        Tqe = Math.max(c0.getTemperature(), c1.getTemperature());
        Tfe = Math.min(c0.getTemperature(), c1.getTemperature());
        
        Cmin = Math.min(Cq, Cf);
        Cmax = Math.max(Cq, Cf);
        
        Cr = Cmin/Cmax;
        
        qmax = Cmin*(Tqe - Tfe);
        
        NUT = this.U*this.A/Cmin;
        
        //Calcula o 'e' usando correlações que vão depender do permutador;        
        /*
        -----------------------------------------------------------------
        -----------------------------------------------------------------
        -----------------------------------------------------------------
        ---------------------------  TO DO ------------------------------
        -----------------------------------------------------------------
        -----------------------------------------------------------------
        -----------------------------------------------------------------                
        */
        e = 0.60f; // <------ apagar, deve ser calculado!
        
        
        //Corrige o calor trocado com o fator de eficiência do permutador
        q = (e*qmax*this.efficiency);
        
        //seta as temperaturas para as correntes de saída;
        Tfs = Tfe + q/Cf;
        Tqs = Tqe - q/Cq;
        
        if(c0.getTemperature() > c1.getTemperature()){ //corrente c0 é a mais quente
            this.getSaida(0).setTemperature((float) Tqs);
            this.getSaida(1).setTemperature((float) Tfs);
        } else {
            this.getSaida(0).setTemperature((float) Tfs);
            this.getSaida(1).setTemperature((float) Tqs);            
        }
        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
}
