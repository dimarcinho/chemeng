/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and blocked the template in the editor.
 */
package com.chemeng.testes;


import com.chemeng.gameobjects.*;
import com.chemeng.gameobjects.chemical.Corrente;

/**
 *
 * @author Marcio
 */
public class TestConnection {    
    
    /*
    
    Este objeto é muito importante!
    
    Ele é como se fosse o nó de um grafo;
    Cada conexão está ligada a outra conexão (ou a nenhuma).
    A simulação irá rodar sempre atualizando as conexões de entrada a partir
    das simulações que irão alterar as conexões de saída.
    
    */
    
    
    private TestConnection conexao;
    private Corrente corrente = new Corrente();
    private TestPhysicsComponent physics;
    private final ConnectionType tipo;
    private boolean blocked;    
    
    public TestConnection(TestPhysicsComponent physics, ConnectionType tipo){
        this.physics = physics;
        this.tipo = tipo;
        this.blocked = false;
    }

    public boolean isBlocked() {
        return blocked;
    }
    
    public void Open(){
        blocked = false;
    }
    
    public void Block(){
        blocked = true;
    }

    public Corrente getCorrente() {
        return corrente;
    }

    public void setCorrente(Corrente corrente) {
        this.corrente = corrente;
    }
    
    public TestConnection getConexao() {
        return conexao;
    }
    
    public void link(TestConnection conexao) {
        this.conexao = conexao;
    }
    
    public TestPhysicsComponent getPhysics(){
        return physics;
    }

    public ConnectionType getTipo() {
        return tipo;
    }
    
    public void update(){
        if(this.tipo == ConnectionType.SAIDA){
            if(this.getConexao() != null){
                //this.getConexao().setCorrente(corrente);
                this.getConexao().getCorrente().setFlow(corrente.getFlow());
            }
        }
    }
}
