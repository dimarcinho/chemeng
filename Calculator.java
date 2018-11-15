/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.utils.Array;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.chemical.Molecula;

/**
 *
 * @author Marcio
 */
public class Calculator {
    
    /*
    CLASSE COM MÉTODOS QUE SERÃO UTILIZADOS PARA CALCULAR DIVERSAS 
    PROPRIEDADES FÍSICAS, QUÍMICAS, ENTALPIAS, ETC
    */

    public static double getVolumetricFlow(double v_max, double d){        
        //v_max em m/s
        //diâmetro em polegadas
        return v_max*d*d*3.14159d/4d; //em m³/s
    }
    
    public static double getCp(double T, float A, float B, float C, float D){                
        return A*T + B*T*T + C/T + D/(T*T);
    }
    
    public static Corrente mix(Corrente c1, Corrente c2){
        
        int n = c1.z.length;
        
        //BALANÇO DE MASSA
        double flow_1 = c1.getFlow();
        double flow_2 = c2.getFlow();
        
        double flowFinal = flow_1 + flow_2;
        
        double[] molarFlowZ = new double[n];
            
                        
        for(int i = 0; i < n; i++){
            molarFlowZ[i] = (flow_1*c1.z[i] + flow_2*c2.z[i])/flowFinal;            
        }
        
        //BALANÇO DE ENERGIA
        //  ---- ainda sem mudança de fase! -----
        
        double tempT, sum1, sum2;
    
        sum1 = flow_1*c1.getCp()*c1.getTemperature()+
                flow_2*c2.getCp()*c2.getTemperature();
        sum2 = flow_1*c1.getCp()+
                flow_2*c2.getCp();
        tempT = sum1/sum2;
        
//        System.out.println("T1: "+c1.getTemperature());
//        System.out.println("T2: "+c2.getTemperature());
//        System.out.println("Tfinal: "+tempT);
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //NECESSÁRIO PENSAR EM COMO SERÁ FEITO O BALANÇO DE PRESSÃO

        double p1 = c1.Pressure;
        double p2 = c2.Pressure;
        double pf = (p1+p2)/2;
        
        //por hora usando média simples;
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        Corrente c = new Corrente(molarFlowZ, (float) flowFinal,
                (float) tempT, pf);
        
        return c;
    }

    //Lei de Raoult
    public static double BOL_P(Array<Molecula> moleculas, double[] x, float T){
        
        double value = 0d;
        
        for(int i=0; i < moleculas.size; i++){
            value += x[i]*moleculas.get(i).getPsat(T);
        }
        return value;
    }
    
    //Lei de Raoult
    public static double ORV_P(Array<Molecula> moleculas, double[] y, float T){
        
        double value = 0d;
        
        for(int i=0; i < moleculas.size; i++){
            value += y[i]/moleculas.get(i).getPsat(T);
        }
        return 1.d/value;
    }
    
}
