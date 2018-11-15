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
public class Mixer extends Operut {
    
    public Mixer(){
        super(2,1);
    }
    
    @Override
    public void balancoMassa(){
        
        this.setSaida(0, this.getEntrada(0)+this.getEntrada(1));
        
    }
    
}
