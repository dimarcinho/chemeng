/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 *
 * @author Marcio
 */
public abstract class GraphicsComponent {

    public abstract TextureRegion getTextureRegion();
    public abstract void update(GeneralObject o); 
    public abstract void render(SpriteBatch batch, GeneralObject o);
    
}
