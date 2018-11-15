/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

/**
 *
 * @author Marcio
 */
public class PermutadorTeste {
    
    public PermutadorTeste(){
        
    }
    
    public static void main(String[] args){
        
        //Método NUT-e
        double Tqe, Tfe, Tqs, Tfs;
        double Cq, Cf, Cr, Cmin, Cmax;
        double q, qmax, NUT, e;
        
        double T0, Cp0, q0;
        double T1, Cp1, q1;
        double U, A;
        
        U = 340; // W/m²ºC
        A = 2.04f; //m²
        
        //óleo
        T0 = 160; //ºC
        Cp0 = 2200; //J/kgºC
        q0 = 0.20f; // kg/s
        
        T1 = 18; //ºC
        Cp1 = 4180; //J/kgºC
        q1 = 0.10f; // kg/s
        
        if(T0 > T1){ //corrente c0 é a mais quente
            Cq = q0*Cp0;
            Cf = q1*Cp1;
        } else {            
            Cq = q1*Cp1;
            Cf = q0*Cp0;            
        }
        
        System.out.println("Cq: "+Cq);
        System.out.println("Cf: "+Cf);
        
        Tqe = Math.max(T0, T1);
        Tfe = Math.min(T0, T1);
        
        System.out.println("Tqe: "+Tqe);
        System.out.println("Tqe: "+Tfe);     
        
        Cmin = Math.min(Cq, Cf);
        Cmax = Math.max(Cq, Cf);
        
        System.out.println("Cmin: "+Cmin);
        System.out.println("Cmax: "+Cmax);
                
        Cr = Cmin/Cmax;
        
        System.out.println("Cr: "+Cr);
        
        qmax = Cmin*(Tqe - Tfe);
        
        System.out.println("qmax: "+qmax);
        
        NUT = U*A/Cmin; //tem q dar 1,66 neste exemplo
        
        System.out.println("NUT: "+NUT);
        
        //Calcula o 'e' usando correlações que vão depender do permutador;
        e = 0.61f; // retirado do exemplo da apostila
        
        
        //Corrige o calor trocado com o fator de eficiência do permutador
        q = e*qmax;
        
        System.out.println("q: "+q);
        
        //seta as temperaturas para as correntes de saída;
        Tfs = q/Cf + Tfe;
        Tqs = Tqe - q/Cq;
        
        System.out.println("Tqs: "+Tqs);
        System.out.println("Tqf: "+Tfs);        
        
        System.out.println("Resultado esperado:\n Tqs = 104,6ºC\n Tfs = 77,7ºC");
    }
    
}
