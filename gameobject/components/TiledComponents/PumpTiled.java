/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chemeng.TiledWorld;

/**
 *
 * @author marci
 */
public class PumpTiled extends GameObject {

    private static final String TAG = PumpTiled.class.getSimpleName();
    
    public PumpTiled(float x, float y) {
        super(x, y, 128, 64);
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
        return Operut.PUMP;
    }
    
}
