/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Marcio
 */
public class Operut {

    protected Sprite sprite;
    
    private static int counterID = 0;
    
    private final int ID;
    
    private final int numEntradas; //número de entradas
    private final int numSaídas; //número de saídas
    
    private float[] CorrentesEntrada; //refatorar de float para o objeto Corrente ou equivalente
    private float[] CorrentesSaida;

    private ConexaoOperutList conexoes;
    
    public Operut(int numEntradas, int numSaidas){
        this.numEntradas = numEntradas;
        this.numSaídas = numSaidas;
        
        CorrentesEntrada = new float[numEntradas];
        CorrentesSaida = new float[numSaidas];
        
        conexoes = new ConexaoOperutList(numSaidas, this);
        
        this.ID = counterID;
        counterID++;
    }

    public int getID() {
        return ID;
    }
    
    public void update(){
        
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
        
        //agora que os valores de saída estão atualizados,
        //eu repasso para os outros equipamentos via conexoes.update()
        
        balancoMassa();
        balancoEnergia();        
        
        conexoes.update();
    }
    
    public void balancoMassa(){}
    public void balancoEnergia(){}
    
    public void setDestino(int indiceOrigem, Operut o, int indiceDestino){
        
        //o.setEntrada(indiceDestino, this.getSaida(indiceOrigem));     
        this.conexoes.setConexao(indiceOrigem, o, indiceDestino);
        
    }
    
    public float getEntrada(int i){
        return this.CorrentesEntrada[i];
    }
    
    public float getSaida(int i){
        return this.CorrentesSaida[i];
    }
    
    public void setEntrada(int entrada, float x){
        this.CorrentesEntrada[entrada] = x;
    }
    
    public void setSaida(int saida, float x){
        this.CorrentesSaida[saida] = x;
    }
    
    public void print(){
        
        System.out.printf("Equipamento: %d%n", this.getID());        
        System.out.println("Entradas:");
        for(int i = 0; i < CorrentesEntrada.length; i++){
            System.out.printf("[%d] - %f ", i, CorrentesEntrada[i]);
        }
        System.out.println("\nSaídas:");
        for(int i = 0; i < CorrentesSaida.length; i++){
            System.out.printf("[%d] - %f ", i, CorrentesSaida[i]);
        }
        System.out.println("\n-------------");
    }
    
    public Sprite getSprite(){
        return sprite;
    }
 
    public void dispose(){}
    public void render(){}
}
