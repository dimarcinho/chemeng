/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.chemical;

/**
 *
 * @author marci
 */
public class Property {
    
    private double min, max;
    
    public Property(double min, double max){
        this.min = min;
        this.max = max;
    }
    
    public boolean isChecked(double value){        
        return (value > min && value < max);        
    }
    
    public static Property PropertyMax(double max){
        return new Property(Double.NEGATIVE_INFINITY, max);
    }
    
    public static Property PropertyMin(double min){
        return new Property(min, Double.POSITIVE_INFINITY);
    }
}