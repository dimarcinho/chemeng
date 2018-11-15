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
public interface GameObject {
        
    public float x=0, y=0, width=0, height=0;
    
    public abstract void input();
    public abstract void update();
    public abstract void render();
    public abstract void dispose();
}
