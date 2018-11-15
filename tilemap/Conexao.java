/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.tilemap;

import com.badlogic.gdx.Gdx;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.components.TiledComponents.GameObject;

/**
 *
 * @author Márcio Brito
 */
public class Conexao {

    private static final String TAG = Conexao.class.getName();
    
    private Corrente saida, entrada;
    
    GameObject nextObj;
    /*
    A -----> B (A envia pra B)
    A-> corrente de saida
    ->B corrente de entrada
    */
    public Conexao(Corrente saida, Corrente entrada){
        this.saida = saida;
        this.entrada = entrada;                
        //this.nextObj = nextObj;
    }
    
    public void update(){
        saida.free = entrada.free;
        if(entrada.free)
            entrada.setData(saida);
    }
    
    public void clearConnection(){
        Gdx.app.log(TAG, "Implementar NullCorrente para clear() // Checar estado dos equipamentos");
        /*
        Aqui preciso verificar a melhor maneira de como o equipamento q irá receber
        a Null Corrente irá lidar; talvez seja simples de só colocar NullCorrente aqui
        e o cada equipamento toma conta do seu proprio estado (StateMachine)
        */
        entrada.setFlow(0);
        saida.setFlow(0);
        update();
        
    }

    public GameObject getNextObj() {
        return nextObj;
    }

    public Corrente getSaida() {
        return saida;
    }

    public Corrente getEntrada() {
        return entrada;
    }    
    
}
