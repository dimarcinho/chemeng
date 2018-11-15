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
public class FlashDrumTiledPhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = FlashDrumTiledPhysicsComponent.class.getName();
    
    private Vector2 input, outputTop, outputBottom;
    
    private double beta = 0.6d;
    
    public FlashDrumTiledPhysicsComponent() {
        super(1, 2);
        input = new Vector2(0,2);
        outputTop = new Vector2(1,4);
        outputBottom = new Vector2(1,0);
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(outputTop, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.UP));
        super.getConnections().put(outputBottom, 
                new TiledConnection(super.getSaida(1),
                        ConnectionType.SAIDA, TiledConnection.Direction.DOWN));        
        
    }

    @Override
    public void init() {
        //to do
    }

    @Override
    public void balancoMassa() {        
        
        float T = this.getEntrada(0).getTemperature();
        double P = this.getEntrada(0).getPressure();        
        this.getSaida(0).setTP(T, P);
        this.getSaida(1).setTP(T, P);
        
        
        Corrente c = this.getEntrada(0);
        
        switch (c.getEstado()) {
            case LIQUIDO_SUBRESFRIADO:

                beta = 0d;
                
                this.getSaida(0).setFlow(0);
                this.getSaida(1).setData(this.getEntrada(0));
           
                break;
                
            case VAPOR_SUPERAQUECIDO:
                
                beta = 1d;
                
                this.getSaida(0).setData(this.getEntrada(0));
                this.getSaida(1).setFlow(0);
                
                break;
                
            case ELV:

                c.calculaFlash();
                
                beta = c.getFlashBeta();
                
                double gasFlow = c.getFlow()*beta;
                double liquidFlow = c.getFlow()*(1.d-beta);
                double[] veczero = new double[c.getZ().length];
                
                //Saída do gás:
                this.getSaida(0).setFlow(gasFlow);
                this.getSaida(0).setXcomposition(veczero);
                this.getSaida(0).setYcomposition(c.getY());
                this.getSaida(0).setZcomposition(c.getY());
                
                //Saída do Líquido:
                this.getSaida(1).setFlow(liquidFlow);
                this.getSaida(1).setXcomposition(c.getX());
                this.getSaida(1).setYcomposition(veczero);
                this.getSaida(1).setZcomposition(c.getX());
                
                this.getSaida(0).setTP(T, P);
                this.getSaida(1).setTP(T, (P+5000f)); //pequeno peso de coluna de liquido no vaso, para garantir estado líquido na saída
                                                        
                
                break;
                
            default:
                beta = -1;
                System.out.println("Erro em "+this.toString());
                break;
        }

        
    }

    @Override
    public void balancoEnergia() {
        
        //to do
        
    }

    public double getBeta() {
        return beta;
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }
    
}
