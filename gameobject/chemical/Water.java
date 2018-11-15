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
public class Water extends Molecula {

    public Water(){
        this.name = "Water";
        this.formula = "H2O";
        this.MM = 18;
        this.polar = true;
        this.Cp = 4184; // J/kgºC
        this.Cp = Cp*MM/1000; // J/molºC
        
        thermo = new Thermodynamics(name);
        thermo.setAntoineParameters(16.2620f, 3799.89f, 226.35f);
    }    
    
}
