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
import com.chemeng.tilemap.TiledConnection;

/**
 *
 * @author marci
 */
public class ValvePCVPhysicsComponent extends TiledPhysicsComponent {
    
    private static final String TAG = ValvePCVPhysicsComponent.class.getName();

    private Vector2 input, output;
    
    private double open = 1d; //abertura
    
    public double Kmin = 0.15d, Kmax = 115d, K = 50;
    
    public double setPoint = 101325; //pressão, em Pa
    private double ganho = 0.0001d;
    private double erro = 0;
    
    public double u = 0;
    public double deltaP = 0;
    
    public ValvePCVPhysicsComponent() {
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
        
        Corrente in = super.getEntrada(0);
        Corrente out = super.getSaida(0);

        if(in.getFlow() > 0){
        
            double P_in = in.getPressure()/101325;
            double set = setPoint/101325;

            double flow = in.getFlow(); //aqui considerando m3/h
            flow /= 3600; //m3/s
            double D = 4d; //diametro, em polegadas
            D *= 2.54d/100d; //diametro em metros
            double A = Math.PI*D*D/4d; //area em m2

            u = flow/A; //velocidade, em m/s

            deltaP = P_in - set; //atm
            deltaP *= 10.3d; //em metros de água

//            double ro = 1000d; //densidade do fluido; usar algo como getDensidade()
            
            if(deltaP < 0){
                
                K = Kmin;
                deltaP = K*u*u/(2d*9.81d);
                
            } else {

                K = deltaP*2d*9.81d/(u*u);

                if(K < Kmin){
                    K = Kmin;
                    deltaP = K*u*u/(2d*9.81d);                    
                } else if(K > Kmax){
                    K = Kmax-0.01d;
                    deltaP = K*u*u/(2d*9.81d);
                }
                
            }
            
            open = (Math.log(K/Kmax))/(Math.log(Kmin/Kmax));

            deltaP /= 10.3d; //deltaP em atm

            out.setData(in);
            out.setPressure((P_in - deltaP)*101325);
            
        } else {
            super.getEntrada(0).setFlow(0);
            super.getSaida(0).setFlow(0);
        }

        
    }

    @Override
    public void balancoEnergia() {
        
    }

    public double getOpen() {
        return open;
    }

    public double getSetPoint() {
        return setPoint;
    }

    public void setSetPoint(double SetPoint) {
        this.setPoint = SetPoint;
    }

    
    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        switch(event){            
            case SET_OPEN:
                double set = Double.parseDouble(obj.toString());
                setSetPoint(set*101325);
                Gdx.app.log(TAG, "Novo Set Point: "+set);
                break;
            default:
                //nao faz nada;
        }
    }
    
}
