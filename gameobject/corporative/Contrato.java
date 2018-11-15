/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.corporative;

import com.chemeng.GameTime;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.chemical.produtos.Produto;

/**
 *
 * @author Marcio
 */
public class Contrato {
 
    private static final String TAG = Contrato.class.getName();
    
    public final String empresa;
    public int prazo;
    public Produto produto;
    private double preco, meta, produzido; //meta - qtde a ser produzida    
    private boolean finished = false;
    private boolean levelRequired; //se o contrato é necessário para passar de fase
    
    public Contrato(String empresa, 
                    int prazo,
                    Produto produto,
                    double meta,
                    double preco,
                    boolean levelRequired){
        
        this.empresa = empresa;
        this.prazo = prazo*86400/3;
        this.produto = produto;
        this.meta = meta;
        this.preco = preco;
        this.levelRequired = levelRequired;
        
        this.produzido = 0;
    }
    
    public void update(){
        prazo -= 1;
    }

    public String getEmpresa() {
        return empresa;
    }
    
    public Produto getProduto(){
        return produto;
    }

    public double getMeta() {
        return meta;
    }

    public double getProduzido() {
        return produzido;
    }

    public float getPercProduzido() {
        return (float) (produzido/meta);
    }
    
    public void setProduzido(double produzido) {
        this.produzido = produzido;
    }

    public int getPrazo() {
        return prazo;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isLevelRequired() {
        return levelRequired;
    }

    public boolean isFinished() {
        return finished;
    }
    
    
    public void receive(Corrente c){
        if(!isExpired()){
            if(produto.isProduto(c)){
                Cash x = new Cash();
                x.addMoney(preco*c.getFlow());
                produzido += c.getFlow()*GameTime.getMPF()*(1d/60d);
                x = null;
            }
        }
    }
    
    public boolean isExpired(){        
        finished = (produzido > meta);
        return prazo < 0;        
    }
}
