/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Marcio
 */
public class Source extends Operut implements GameObject {

    //private Sprite sprite;
    private Texture texture;  
    
    public float x, y, vazao;    
    
    public Source(Texture texture, float x, float y, float vazao) {
        super(0, 1);
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.vazao = vazao;
        
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }
    
    public Source(float x, float y, float vazao) {
        super(0, 1);        
        this.x = x;
        this.y = y;
        this.vazao = vazao;
        
        texture = new Texture(Gdx.files.internal("img/oilspill64.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }
  
    public Sprite getSprite(){
        return sprite;
    }

    @Override
    public void input() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void render() {
        
    }

    @Override
    public void dispose() {
        texture.dispose();        
    }
    
}
