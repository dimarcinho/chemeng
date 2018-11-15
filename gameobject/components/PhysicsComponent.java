/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components;

import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.Connection;
import com.chemeng.gameobjects.ConnectionList;


/**
 *
 * @author Marcio
 */
public abstract class PhysicsComponent {
    
    private ConnectionList conexoes;
    
    public int tempConex = -1;
    public ConnectionType tempType = null;
    
    public PhysicsComponent(int numEntradas, int numSaidas){        
        
        conexoes = new ConnectionList(numEntradas, numSaidas);
        
    }
    
    public abstract void init();
    public abstract void balancoMassa();
    public abstract void balancoEnergia();

    public ConnectionList getConexoes() {
        return conexoes;
    }
    
    public Connection getEntrada(int i){
        return getConexoes().getEntrada(i);
    }
    public Connection getSaida(int i){
        return getConexoes().getSaida(i);
    }
    
    public final void update(GeneralObject obj){
        
        /*********************************
        * Simulação da Operação Unitária
        * 
        * Cada equipamento deverá implementar seus próprios métodos:
        * balancoMassa() e balancoEnergia() que serão chamados aqui
        * 
        * Este método deve ser 'final'
        * 
        **********************************/
        
        balancoMassa();
        balancoEnergia();        
        
        //agora que os valores de saída estão atualizados,
        //eu repasso para os outros equipamentos via conexoes.update()
        
        conexoes.update();
    }
 
    public void dispose(){}
    public void render(){}
}
