/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical.produtos;

import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.chemical.Property;
import java.util.LinkedList;

/**
 *
 * @author Marcio
 */
public interface Produto {
        
    public boolean isProduto(Corrente c);
    public String getName();
}
