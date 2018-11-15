/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.chemeng.TiledWorld;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.tilemap.Conexao;
import com.chemeng.tilemap.TiledConnection;


/**
 *
 * @author Marcio
 */
public abstract class TiledPhysicsComponent {
    
    private static final String TAG = TiledPhysicsComponent.class.getName();
    
    private Corrente[] entradas;
    private Corrente[] saidas;
    
    private Array<Conexao> conexoes;
    
    private ObjectMap<Vector2, TiledConnection> map;
    
    public TiledPhysicsComponent(int numEntradas, int numSaidas){                
        entradas = new Corrente[numEntradas];
        saidas = new Corrente[numSaidas];
        
        for(int i = 0; i < entradas.length; i++){
            entradas[i] = new Corrente();
        }
        for(int i = 0; i < saidas.length; i++){
            saidas[i] = new Corrente();
        }
        
        conexoes = new Array<Conexao>();
        
        map = new ObjectMap<Vector2, TiledConnection>();
        
    }
    
    public void clearConnections(){
        for(Conexao c : conexoes){
            c.clearConnection();
        }
        conexoes.clear();
    }

    public Array<Conexao> getConexoes() {
        return conexoes;
    }
    
    public ObjectMap<Vector2, TiledConnection> getConnections() {
        return map;
    }
    
    public abstract void init();
    public abstract void balancoMassa();
    public abstract void balancoEnergia();
    
    public Corrente getEntrada(int i){
        return entradas[i];
    }
    
    public Corrente getSaida(int i){
        return saidas[i];
    }
    
    public void update(GameObject obj, TiledWorld world){
        
        
        /*********************************
        * Simulação da Operação Unitária
        * 
        * Cada equipamento deverá implementar seus próprios métodos:
        * balancoMassa() e balancoEnergia() que serão chamados aqui
        * 
        **********************************/
        
        balancoMassa();
        balancoEnergia();
        
        for(Conexao c : conexoes){
            if(c != null)
                c.update();
        }

        
    }
 
    public void dispose(){}
    public void render(){}
    
    public abstract void handleGameEvent(GameEvent event, Object obj);
}
