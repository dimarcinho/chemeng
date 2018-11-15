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
public abstract class Molecula {
    
    protected String formula, name;
        
    protected float MM;
    protected float Cp;

    protected Thermodynamics thermo;
    protected SolidParticles solids;
   
    protected float melting_T; //Temperatura de Fusão
    protected float boiling_T; //Temperatura de Ebulição
    protected float densidadeLiquido; //densidade
    protected boolean polar;
 
    public String getFormula() {
        return formula;
    }

    public float getMelting_T() {
        return melting_T;
    }

    public float getBoiling_T() {
        return boiling_T;
    }

    public String getName() {
        return name;
    }
    
    public double getCp(){
        return Cp;
    }
    
    public float getMM(){
        return MM;
    }

    public boolean isPolar() {
        return polar;
    }
    
    public double getTsat(double P){
        return thermo.getTsat(P);
    }
    
    public double getPsat(double T){
        return thermo.getPsat(T);
    }
    
    public Molecula.State getPureState(double T){
        if(T < melting_T){
            return State.SOLID;
        } else {
            if(T < boiling_T){
                return State.LIQUID;
            } else {
                return State.GAS;
            }
        }
    }
    
    public enum State {
        SOLID, LIQUID, GAS
    }
}
