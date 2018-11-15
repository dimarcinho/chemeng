/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.chemical.Molecula;
import com.chemeng.tilemap.TiledConnection;
import java.util.Arrays;

/**
 *
 * @author marci
 */
public class HorizontalDrumPhysicsComponent extends TiledPhysicsComponent {

    private static final String TAG = HorizontalDrumPhysicsComponent.class.getName();
    
    private Vector2 input, outputHeavy, outputLight;
    
    //private double alfa = 0.80d;
    
    private double eff = 0.90d;
    
    private boolean debug = false;
    
    public HorizontalDrumPhysicsComponent() {
        super(1, 2);
        input = new Vector2(0,1);
        outputHeavy = new Vector2(4,0);
        outputLight = new Vector2(6,0);
        
        super.getConnections().put(input, 
                new TiledConnection(super.getEntrada(0),
                        ConnectionType.ENTRADA, TiledConnection.Direction.RIGHT));
        
        super.getConnections().put(outputHeavy, 
                new TiledConnection(super.getSaida(0),
                        ConnectionType.SAIDA, TiledConnection.Direction.DOWN));
        super.getConnections().put(outputLight, 
                new TiledConnection(super.getSaida(1),
                        ConnectionType.SAIDA, TiledConnection.Direction.DOWN));
    }

    @Override
    public void init() {
        //to do
    }

    @Override
    public void balancoMassa() {
            
        double flow = this.getEntrada(0).getFlow();
        
        Corrente c = this.getEntrada(0);
        
        switch (c.getEstado()) {
            case LIQUIDO_SUBRESFRIADO:
                
                if(checkPolarDifference()){
                    simulateDrum(flow);
                } else {
                    splitFlow(flow);
                }
                
                break;
                
            case VAPOR_SUPERAQUECIDO:
                
                splitFlow(flow);                
                break;
                
            case ELV:
                
                splitFlow(flow);
                break;
                
            default:
                System.out.println("Erro em "+this.toString());
                break;
        }

        
    }
    
    private void simulateDrum(double flow){
        
        float T = this.getEntrada(0).getTemperature();
        double P = this.getEntrada(0).getPressure();
        
        getSaida(0).setTP(T, P);
        getSaida(1).setTP(T, P);

        //F -- alimentacao
        //L -- apolares (light) (saida 1)
        //H -- polares (heavy) (saida 0)
        //ok, o certo eh fazer por densidade, mas deixar assim por eqnto

        double H = 0d, L = 0d;
        Array<Molecula> mol = Corrente.getMoleculas();

        double flow_in[], flow_H[], flow_L[];
        flow_in = new double[mol.size];
        flow_H = new double[mol.size];
        flow_L = new double[mol.size];
        double z_in[] = this.getEntrada(0).getZ();

        for(int i = 0; i < mol.size; i++){

            flow_in[i] = z_in[i]*flow;

            if(mol.get(i).isPolar()){

                flow_H[i] = eff*flow_in[i];
                flow_L[i] = (1d-eff)*flow_in[i];

            } else {

                flow_L[i] = eff*flow_in[i];
                flow_H[i] = (1d-eff)*flow_in[i];

            }

            H += flow_H[i];
            L += flow_L[i];

        }

        double H_comp[], L_comp[];
        H_comp = new double[mol.size];
        L_comp = new double[mol.size];

        for(int i = 0; i < mol.size; i++){                    
            H_comp[i] = flow_H[i]/H;
            L_comp[i] = flow_L[i]/L;
        }

        getSaida(0).setFlow(H);
        getSaida(0).setZcomposition(H_comp);
        getSaida(0).setXcomposition(H_comp);

        getSaida(1).setFlow(L);
        getSaida(1).setZcomposition(L_comp);
        getSaida(1).setXcomposition(L_comp);

//        if(!debug){
//            Gdx.app.log(TAG, "F_in_comp"+Arrays.toString(z_in));
//            Gdx.app.log(TAG, "Flow_in"+Arrays.toString(flow_in));
//            Gdx.app.log(TAG, "Flow_H"+Arrays.toString(flow_H));
//            Gdx.app.log(TAG, "Flow_L"+Arrays.toString(flow_L));
//            Gdx.app.log(TAG, "H_comp"+Arrays.toString(H_comp));
//            Gdx.app.log(TAG, "L_comp"+Arrays.toString(L_comp));
//            debug = true;
//        }
                
    }
    
    private boolean checkPolarDifference(){
                
        double polar = 0, nonpolar = 0, flow;
        flow = getEntrada(0).getFlow();
        Array<Molecula> mol = Corrente.getMoleculas();
        double z[] = this.getEntrada(0).getZ();
        
        for(int i = 0; i < z.length; i++){
            if(mol.get(i).isPolar()){
                polar += z[i]*flow;
            } else {
                nonpolar += z[i]*flow;
            }
        }
        
//        if(!debug){
//            Gdx.app.log(TAG, "polar_flow: "+polar);
//            Gdx.app.log(TAG, "nonpolar_flow: "+nonpolar);
//        }
        
        return !(polar == 0 || nonpolar == 0);  
        
    }
    
    private void splitFlow(double flow){
        this.getSaida(0).setData(this.getEntrada(0));
        this.getSaida(1).setData(this.getEntrada(0));
        this.getSaida(0).setFlow(flow/2d);
        this.getSaida(1).setFlow(flow/2d);
    }

    @Override
    public void balancoEnergia() {
        
        //to do
        
    }

    @Override
    public void handleGameEvent(GameEvent event, Object obj) {
        //nada
    }
    
}
