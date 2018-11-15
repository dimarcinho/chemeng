/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Marcio
 */
public class OperutConector implements GameObject {    
    
    
    private Array<GameObject> objects;
    
    private Operut origin;
    private Operut destiny;
    
    private float mx, my; //coordenadas do mouse
    
    public boolean firstClick = false;
    public boolean active = false;
    
    private Rectangle area;
    
    private ShapeRenderer sr;
    private Camera cam;
    
    public OperutConector(Camera cam){
        
        this.cam = cam;
        
        sr = new ShapeRenderer();
        active = true;
        sr.setProjectionMatrix(cam.combined);
    }
    
    public void input(){
        
    }

    public void setXY(float x, float y){
        mx = x;
        my = y;
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render() {
        
        cam.update();
        sr.setProjectionMatrix(cam.combined);
        
        sr.begin(ShapeRenderer.ShapeType.Filled);        
        sr.setColor(Color.FIREBRICK);        
        sr.arc(mx,my, 4f, 0, 360);
        
        sr.end();
    }

    
    public void dispose() {
        sr.dispose();        
    }

    public void setOrigin(Operut origin) {
        this.origin = origin;
    }

    public void setDestiny(Operut destiny) {
        this.destiny = destiny;
    }
    
}
