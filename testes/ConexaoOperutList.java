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
public class ConexaoOperutList {
    
    private Operut OperutOrigem;
    
    private final int[] origem;
    private Operut[] operut;
    private int[] destino;
    
    public ConexaoOperutList(int size, Operut o){
        
        this.OperutOrigem = o;
        
        origem = new int[size];
        operut = new Operut[size];
        destino = new int[size];
        
        
        //preenhce o vetor origem com os índices
        for(int i=0; i < size; i++){
            origem[i] = i;
            destino[i] = -1;
        }
        
        //o outro vetor fica com 'null' até ser setado;
        
    }
    
    public void setConexao(int indexOrigem, Operut o, int indexDestino){
        
        if(o.equals(this.OperutOrigem)){
         
            System.out.println("Conexão no mesmo objeto, impossível criar;");
            
        } else {
        
            this.operut[indexOrigem] = o;
            this.destino[indexOrigem] = indexDestino;
        
        }
    }
    
    public void clearConexao(int i){
        this.operut[i] = null;
        this.destino[i] = -1;
    }
    
    public void clear(){
        for(int i=0; i < origem.length; i++){
            this.operut[i] = null;
            this.destino[i] = -1;
        }
    }
    
    public void update(){
        
        for(int i = 0; i < origem.length; i++){            
            if(operut[i] != null)
            {
                operut[i].setEntrada(destino[i], this.OperutOrigem.getSaida(origem[i]));
            }
        }
        
    }
    
}
