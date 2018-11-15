/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical.produtos;

import com.chemeng.gameobjects.chemical.Corrente;


/**
 *
 * @author Marcio
 */
public class AguaProduto implements Produto {

    private static final String TAG = AguaProduto.class.getName();
    
    private static String NAME;
    public final double MIN_H2O;
    
    public AguaProduto(){        
        NAME = "Water 95%";        
        MIN_H2O = 0.95d;        
    }
    
    @Override
    public boolean isProduto(Corrente c) {
        
        int k = Corrente.getMoleculas().size;
        
        for(int i = 0; i < k; i++){
            if(Corrente.getMoleculas().get(i).getFormula().equals("H2O")){
                if(c.z[i] > MIN_H2O){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public final String getName() {
        return NAME;
    }
    
}
