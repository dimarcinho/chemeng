/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.TiledConnection;
import java.util.Random;

/**
 *
 * @author marci
 */
public class SourceTiledPhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = SourceTiledPhysicsComponent.class.getSimpleName();
    
    private int counter = 0;
    
    private Vector2 output;
    
    private double maxFlow = 80f;
    private double abertura = 1.0d;
    
    public SourceTiledPhysicsComponent() {
        super(0, 1);
        output = new Vector2(1,0);
        
        super.getConnections().put(output, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.RIGHT));        
        
        
        super.getSaida(0).setPressure(101325d);
        super.getSaida(0).setTemperature(298.15f);
        super.getSaida(0).setFlow(maxFlow);
        
        double[] composition = new double[Corrente.getMoleculas().size];
        composition[0] = 0.550d; //Água
        composition[1] = 0.255d; //Etanol
        composition[2] = 0.045d; //Acetona
        composition[3] = 0.150d; //Dodecano
        super.getSaida(0).setZcomposition(composition);
        
        float Temperature = 320f;
        Random rnd = new Random();
        Temperature += rnd.nextInt(100);
        super.getSaida(0).setTemperature(Temperature);
        
    }
    
    public void setComposition(double[] a){
        if(a.length != Corrente.getMoleculas().size){
            Gdx.app.log(TAG, "Erro ao configurar composição: tamanho dos vetores diferentes");            
        } else {
            super.getSaida(0).setZcomposition(a);
        }
    }

    @Override
    public void init() {
        //to do
    }

    @Override
    public void balancoMassa() {

        if(!(getSaida(0) == null)){
        //if(!(getSaida(0) == null) && getSaida(0).free){
        //if(getSaida(0).free){
            if((counter - 120) == 0){
                //double flowBefore = getSaida(0).getFlow();
                double flowBefore = maxFlow*abertura;
                double var;
                var = MathUtils.random(-1.f, 1.f);
                var = flowBefore*(1d+var/100f);
                getSaida(0).setFlow(var);
                counter = -1;
            } else {                
                counter++;
            }       
        } else {
            getSaida(0).setFlow(0d);
        }
        
    }

    @Override
    public void balancoEnergia() {
        //to do
    }

    public double getAbertura() {
        return abertura;
    }
    
    public void setAbertura(double abertura) {
        
        if(abertura < 0){
            Gdx.app.log(TAG, "Abertura menor que 0% inválida!");
        } else if (abertura > 1){
            Gdx.app.log(TAG, "Abertura menor que 100% inválida!");
        } else {
            this.abertura = abertura;
        }
        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){
            case SET_OPEN:
                double open = Double.parseDouble(obj.toString());
                setAbertura(open);
                break;
                
            default:
                //nao faz nada;
        }
    }
    
}
