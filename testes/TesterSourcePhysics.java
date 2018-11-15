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
public class TesterSourcePhysics extends TestPhysicsComponent {

    public TesterSourcePhysics() {
        super(0, 1);
    }

    @Override
    public void init() {
        this.getConexoes().initConnections(this);
        this.getSaida(0).setCorrente(new Corrente());
        this.getSaida(0).getCorrente().setFlow(100.d);
    }
    
    
    @Override
    public void balancoMassa() {
        
        System.out.println(this.getSaida(0).toString());
        System.out.println(this.getSaida(0).getCorrente().toString());
        System.out.println("balancoMassa(): Flow da Corrente do Source: "+
                this.getSaida(0).getCorrente().getFlow());
    }

    @Override
    public void balancoEnergia() {
        
    }
    
}
