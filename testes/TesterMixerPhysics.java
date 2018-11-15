/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.gameobjects.chemical.Corrente;

/**
 *
 * @author Marcio
 */
public class TesterMixerPhysics extends TestPhysicsComponent {

    public TesterMixerPhysics() {
        super(2, 1);
        
                
    }
    
    @Override
    public void init(){
        this.getConexoes().initConnections(this);
        
        this.getConexoes().getEntrada(0).setCorrente(new Corrente());
        this.getConexoes().getEntrada(1).setCorrente(new Corrente());
        
        this.getConexoes().getSaida(0).setCorrente(new Corrente());
    }

    @Override
    public void balancoMassa() {
        
        double e1, e2, s;
        
        e1 = this.getConexoes().getEntrada(0).getCorrente().getFlow();
        e2 = this.getConexoes().getEntrada(1).getCorrente().getFlow();
        s = e1+e2;
        this.getConexoes().getSaida(0).getCorrente().setFlow(s);
    }

    @Override
    public void balancoEnergia() {
        
    }
    
}
