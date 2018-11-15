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
public class Etanol extends Molecula {

    public Etanol(){
        this.name = "Etanol";
        this.formula = "C2H5OH";
        this.MM = 46;
        this.polar = true;
        this.Cp = 2427; // J/kgºC
        this.Cp = Cp*MM/1000; // J/molºC
        
        thermo = new Thermodynamics(name);
        thermo.setAntoineParameters(16.6758f, 3674.49f, 226.45f);
    }
    
}
