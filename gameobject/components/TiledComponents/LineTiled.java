/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chemeng.TiledWorld;
import com.chemeng.gameobjects.ConnectionType;

/**
 *
 * @author marci
 */
public class LineTiled extends GameObject {

    public LineTiled(float x, float y, float width, float height) {
        super(x, y, width, height);
    }    

    @Override
    public void input() {
        
    }

    @Override
    public void update(TiledWorld world, SpriteBatch batch) {
        super.getInput().update(this);
        super.getPhysics().update(this, world);
        super.getGraphics().update(this);
    }

    @Override
    public void render() {
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public Operut getType() {
        return Operut.LINE;
    }
    
}
