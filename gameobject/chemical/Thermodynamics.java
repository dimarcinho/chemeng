/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical;

/**
 *
 * @author Marcio
 */
public class Thermodynamics {
    
    private float acentricFactor;
    private float Tc, Pc, Zc, Vc; //Propriedades críticas;
    
    //Temperatura e Pressão de saturação - parâmetros de Antoine
    private float A, B, C;
    
    //Capacidade Calorífica do Gás Ideal
    private float A_Cpg, B_Cpg, C_Cpg, D_Cpg;
    
    //Capacidade Calorífica do Sólido
    private float A_Cps, B_Cps, D_Cps; // C = 0;
    
    //Capacidade Calorífica do Líquido
    private float A_Cpl, B_Cpl, C_Cpl; // D = 0
    
    
    //Entalpia e Energia de Gibbs - Formação Padrão
    private float H_form, G_form;
    
    private float H, S, U, G; //Entalpia, Entropia, Interna, Gibbs
    private float Hx, Sx, Ux, Gx;
    private float Hy, Sy, Uy, Gy;
    private float Cpx, Cpy; //Capacidade Calorífica
    private float Cp;
    
    public Thermodynamics(String name){
        
        /*
        
        utilizar o nome da molécula para buscar em alguma de base de dados
        todos os dados descritos acima;
        
        */        
    }
    
    /*
    
    TEM QUE TOMAR CUIDADO E TESTAR AS EQUAÇÕES
    OS PARÂMETROS AQUI UTILIZADOS PARA ANTOINE SÃO DO VAN NESS, PAGINA 419
    (CAPÍTULO 12, EXERCÍCIOS)
    
    P DE ENTRADA EM kPa E T DE ENTRADA EM ºC
    
    O JOGO IRÁ FAZER AS SIMULAÇÕES NO SI, OU SEJA, EM Pa E Kelvin; ASSIM, 
    É NECESSÁRIO CONVERTER;
    
    */
    
    //Por Antoine
    // ln(Psat) = A - B/(T+C)
    public double getTsat(double P){ //Kelvin
        
        double Psat = P/1000; //convertendo para kPa
        
        double x = B/(A - Math.log(Psat)) - C;
        
        x += 273.15f; //convertendo para Kelvin
//        System.out.println("Pressão de entrada: "+P+ " Pa");
//        System.out.println("Pressão de entrada: "+Psat+ " kPa");
//        System.out.println("Tsat: "+(x-273.15f)+ " ºC");
//        System.out.println("Tsat: "+x+ " K");
//        System.out.println("A,B,C: "+ A + ", "+ B + ", "+ C);
        
        return x;
    }    
    //Por Antoine
    // ln(Psat) = A - B/(T+C)
    public double getPsat(double T){ //Pa
        
        
        double Tsat = T - 273.15f; //convertendo para Kelvin
                
        double x = Math.exp(A - B/(Tsat+C));
        
        x = x*1000; //convertendo de kPa para Pa
        
        return x;
    }
    
    //
    public void setAntoineParameters(float A, float B, float C){
        this.A = A;
        this.B = B;
        this.C = C;
    }
    
    //Equação de Rackett
    //devolve o volume molar do líquido saturado
    public double getVsatLiq(double T){
        
        double x;
        x = 1 - T/Tc;
        x = Math.pow(x, 0.2857f);
        
        return Vc*Math.pow(Zc, x);
    }
    
}
