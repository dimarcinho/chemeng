/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.gameobjects.components.*;
import com.chemeng.gameobjects.ConnectionType;

/**
 *
 * @author Marcio
 */
public abstract class TestPhysicsComponent {

    private TestConnectionList conexoes;
    
    public int tempConex = -1;
    public ConnectionType tempType = null;
    
    public TestPhysicsComponent(int numEntradas, int numSaidas){        

        //conexoes = new TestConnectionList(numEntradas, numSaidas, this);        
        conexoes = new TestConnectionList(numEntradas, numSaidas);

    }
    
    public abstract void init();
    public abstract void balancoMassa();
    public abstract void balancoEnergia();

    public TestConnectionList getConexoes() {
        return conexoes;
    }
    
    public TestConnection getEntrada(int i){
        return getConexoes().getEntrada(i);
    }
    public TestConnection getSaida(int i){
        return getConexoes().getSaida(i);
    }
    
    public void update(GeneralObject obj){
        
        /*********************************
        * Simulação da Operação Unitária
        * 
        * Cada equipamento deverá implementar seus próprios métodos:
        * balancoMassa() e balancoEnergia() que serão chamados aqui
        * 
        * 
        *  ---------- IMPORTANTE! --------------
        * 
        *  --SEMPRE-- deve terminar usando as funções setSaida() para atualizar tudo
        * 
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
