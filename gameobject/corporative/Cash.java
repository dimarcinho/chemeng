/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.corporative;

/**
 *
 * @author Marcio
 */
public class Cash {
       
    private static final String TAG = Cash.class.getSimpleName();
    
    private static double amount;
    
    public Cash(){}
    
    public Cash(double x){
        amount = x;
    }
    
    public boolean hasMoney(double money){
        return (money <= amount);        
    }
    
    public void addMoney(double money){
        amount += money;
    }
    
    public void takeMoney(double money){
        amount -= money;
    }

    public static double getAmount() {
        return amount;
    }
    
}
