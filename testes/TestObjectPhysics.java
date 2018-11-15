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
public class TestObjectPhysics {
    
    public enum State {
        NORMAL, OFF, ON_FIRE
    }
    public State state;
    
    private boolean freeEntrada, freeSaida;    
    private float correnteEntrada, correnteSaida;
    
    private int fire = 100;
    
    public TestObjectPhysics(TestObject test){
    
        freeEntrada = true;
        freeSaida = true;
            
    }
    
    public void update(TestObject test){
     
        correnteSaida = 0.985f*correnteEntrada; //exemplo de perdas
        fire--;
        if(fire == 0){
            fire = -1;
            state = State.ON_FIRE;
        }
    }
    
    public void setFire(int i){
        fire = i;
    }

    public float getCorrenteEntrada() {
        return correnteEntrada;
    }

    public void setCorrenteEntrada(float correnteEntrada) {
        this.correnteEntrada = correnteEntrada;
    }

    public float getCorrenteSaida() {
        return correnteSaida;
    }

    public void setCorrenteSaida(float correnteSaida) {
        this.correnteSaida = correnteSaida;
    }

    public void setFreeEntrada(boolean freeEntrada) {
        this.freeEntrada = freeEntrada;
    }

    public void setFreeSaida(boolean freeSaida) {
        this.freeSaida = freeSaida;
    }

    public boolean isFreeEntrada() {
        return freeEntrada;
    }

    public boolean isFreeSaida() {
        return freeSaida;
    }
 
    
    
}
