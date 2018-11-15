/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical;

/**
 *
 * @author marci
 */
public class Dodecane extends Molecula {
    
    public Dodecane(){
        this.name = "Dodecane";
        this.formula = "C12H26";
        this.MM = 170.34f;
        this.polar = false;
//        this.Cp = 2427; // J/kgºC
//        this.Cp = Cp*MM/1000; // J/molºC
        this.Cp = 376; // J/molºC
        
        thermo = new Thermodynamics(name);
        thermo.setAntoineParameters(14.05841f, 3743.838f, 180.311f);
    }
    
}
