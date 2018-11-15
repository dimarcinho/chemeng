/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;


/**
 *
 * @author Marcio
 */
public abstract class TiledGraphicsComponent {

    public abstract TextureMapObject getTextureMapObject();
    public abstract TextureRegion getTextureRegion();
    public abstract void update(GameObject obj); 
    public abstract void render(GameObject obj, SpriteBatch batch);
    public abstract void handleGameEvent(GameEvent event, Object obj);
    
}
