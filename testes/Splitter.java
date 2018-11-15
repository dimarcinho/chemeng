/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.testes.Operut;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Marcio
 */
public class Splitter extends Operut implements GameObject {
    
    //Componentes Gráficos
    public float x, y;
    public ShapeRenderer sr;
    private Camera cam;
    
    //Componentes da Mecânica
    private float alfa;
    
    //Componentes da UI    
    private Rectangle conexaoEntrada, conexaoTopo, conexaoFundo;
    public boolean bEntrada, bTopo, bFundo;

    public Splitter(float x, float y, float alfa){
        super(1,2);
        this.alfa = alfa;
        
        conexaoEntrada = new Rectangle(x,y+28,7,12);
        conexaoTopo = new Rectangle(x+57,y+42,7,12);
        conexaoFundo = new Rectangle(x+26,y,12,7);
        
        Texture texture = new Texture(Gdx.files.internal("img/drum64.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
                
        sr = new ShapeRenderer();
    }
    
    @Override
    public void input() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void render(){
        cam.update();
        sr.setProjectionMatrix(cam.combined);        
        sr.begin(ShapeRenderer.ShapeType.Filled);        
        sr.setColor(Color.FIREBRICK);                
        drawRectangle(conexaoEntrada);
        drawRectangle(conexaoTopo);
        drawRectangle(conexaoFundo);
        sr.end();
    }
    
    public void setCamera(Camera cam){
        this.cam = cam;
    }
    
    public void drawRectangle(Rectangle r){        
        sr.rect(r.x, r.y, r.width, r.height);
    }
    
    @Override
    public Sprite getSprite(){
        return sprite;
    }
    
    public Rectangle getConexaoEntrada(){
        return this.conexaoEntrada;
    }    

    public Rectangle getConexaoTopo() {
        return conexaoTopo;
    }

    public Rectangle getConexaoFundo() {
        return conexaoFundo;
    }
    
    @Override
    public void balancoMassa(){
        
        this.setSaida(0, this.getEntrada(0)*alfa);
        this.setSaida(1, this.getEntrada(0)*(1-alfa));
        
    }
    
}
