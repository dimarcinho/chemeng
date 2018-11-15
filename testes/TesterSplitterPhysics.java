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
public class TesterSplitterPhysics extends TestPhysicsComponent {

    public double alfa;
    
    public TesterSplitterPhysics(double alfa) {
        super(1, 2);
        this.alfa = alfa;
    }
    
    @Override
    public void init(){
        this.getConexoes().initConnections(this);
        this.getConexoes().getEntrada(0).setCorrente(new Corrente()); 
        
        this.getConexoes().getSaida(0).setCorrente(new Corrente());
        this.getConexoes().getSaida(1).setCorrente(new Corrente());
    }

    @Override
    public void balancoMassa() {
        
        double e, topo, fundo;
        
        e = this.getConexoes().getEntrada(0).getCorrente().getFlow();
        topo = alfa*e;
        fundo = (1-alfa)*e;
        
        this.getConexoes().getSaida(0).getCorrente().setFlow(topo);
        this.getConexoes().getSaida(1).getCorrente().setFlow(fundo);
        
    }

    @Override
    public void balancoEnergia() {
        
    }
    
}
