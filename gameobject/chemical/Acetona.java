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
public class Acetona extends Molecula {
    
    public Acetona(){
        this.name = "Acetona";
        this.formula = "CH3(CO)CH3";
        this.MM = 58;
        this.polar = true;
        this.Cp = 2152; // J/kgºC
        this.Cp = Cp*MM/1000; // J/molºC       
        
        thermo = new Thermodynamics(name);
        thermo.setAntoineParameters(14.3916f, 2795.82f, 230.00f);
    }
    
}
