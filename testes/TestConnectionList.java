/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.gameobjects.*;

/**
 *
 * @author Marcio
 */
public class TestConnectionList {
    
    public TestPhysicsComponent p;
    private TestConnection[] entradas;
    private TestConnection[] saidas;    
    
    public TestConnectionList(int numEntradas, int numSaidas){        
        entradas = new TestConnection[numEntradas];
        saidas = new TestConnection[numSaidas];
    }
    
    public void initConnections(TestPhysicsComponent p){
        this.p = p;
        System.out.println("\n-------------");
        System.out.println("Inicializando correntes:");
        System.out.println("Instância: "+p.toString());
        for(int i = 0; i < entradas.length; i++){
            entradas[i] = new TestConnection(p, ConnectionType.ENTRADA);
            System.out.println(entradas[i].toString());
        }
        for(int i = 0; i < saidas.length; i++){
            saidas[i] = new TestConnection(p, ConnectionType.SAIDA);
            System.out.println(saidas[i].toString());
        }
        System.out.println("Fim da inicialização");
        System.out.println("----------------------");
    }
    
    public static void Link(TestConnection origem, TestConnection destino){
        
        if(origem.getPhysics().equals(destino.getPhysics())){
         
            System.out.println("***** Conexão no mesmo objeto, impossível criar; ******");
            
        } else if(origem.getConexao() != null) {
        
            System.out.println("***** Conexão de origem já em uso! *****");
            
        } else if(destino.getConexao() != null){
            
            System.out.println("***** Conexão de destino já em uso! *****");
        
        } else if(origem.getTipo().equals(destino.getTipo())){
            
            System.out.println("***** Conexões do mesmo tipo, impossível conectar *****");
            System.out.println("***** Erro: Entrada/Entrada ou Saída/Saída *****");
            
        } else {
            
            origem.link(destino);
            destino.link(origem);
            
            System.out.println("\nConexão feita com sucesso!");
            System.out.println("Objeto: "+origem.getPhysics()+ ";"
                    + "\nConexao de Origem: "+origem.toString());
            System.out.println("Objeto: "+destino.getPhysics()+ ";"
                    + "\nConexao de Destino: "+destino.toString());
        }
    }
    
    public TestConnection getEntrada(int i){
        return entradas[i];
    }
    public TestConnection getSaida(int i){
        return saidas[i];
    }
    
    public void clearConexao(TestConnection conexao){
        conexao.getConexao().link(null);
        conexao.link(null);        
    }
    
    public void clearAllConnections(){
        for (TestConnection c : entradas) {
            clearConexao(c);
        }
        for (TestConnection c : saidas) {
            clearConexao(c);
        }
    }

    public void update(){
        for (TestConnection c : saidas) {
            c.update();
        }
    }
    
    public void print(){
        System.out.println("\nEquipamento: "+p.toString());
        System.out.println("Entradas:");
        for (TestConnection c : entradas) {
            System.out.println(c.toString());
            System.out.println(c.getCorrente().getFlow());
        }
        System.out.println("\nSaídas:");
        for (TestConnection c : saidas) {
            System.out.println(c.toString());            
            System.out.println(c.getCorrente().getFlow());
        }
        System.out.println("-------------\n");
    }
    
    
    
}
