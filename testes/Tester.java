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
public class Tester {
    
    public TestPhysicsComponent tpc;
    
    public Tester(TestPhysicsComponent tpc){
        this.tpc = tpc;
    }
    
    public void update(){
        System.out.println(this.toString());
        tpc.update(null);
    }
    
    public TestPhysicsComponent getPhysics(){
        return tpc;
    }
}
