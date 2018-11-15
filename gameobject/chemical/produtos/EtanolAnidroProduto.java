/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical.produtos;

import com.chemeng.gameobjects.chemical.Corrente;

/**
 *
 * @author marci
 */
public class EtanolAnidroProduto implements Produto {

    private static final String TAG = EtanolAnidroProduto.class.getName();
    
    private static String NAME;
    public final double MIN_ETOH;
    
    public EtanolAnidroProduto(){
        NAME = "Etanol Anidro 99%";
        MIN_ETOH = 0.99d;
    }
    
    @Override
    public boolean isProduto(Corrente c) {
        
        int k = Corrente.getMoleculas().size;
        
        for(int i = 0; i < k; i++){
            if(Corrente.getMoleculas().get(i).getFormula().equals("C2H5OH")){
                if(c.z[i] > MIN_ETOH){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
