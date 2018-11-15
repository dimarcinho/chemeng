/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.chemeng.testes.TestObjectPhysics.State;

/**
 *
 * @author Marcio
 */
public class TestObjectGraphics {
    
    private TextureRegion[] regions = new TextureRegion[3];
    private int actualRegion;
    
    public TestObjectGraphics(TestObject test){
        
        Texture spritesheet = new Texture(Gdx.files.internal("img/teste_ss_64x192.png"));
        
        for(int i = 0; i <= 2; i++){
            regions[i] = new TextureRegion(spritesheet, 64*i,0,64,64);
        }        
        actualRegion = 0;        
        
    }

    public TextureRegion getTextureRegion(){
        return regions[actualRegion];        
    }
 
    public void update(TestObject test){
        if(test.getPhysics().state.equals(State.NORMAL)){            
            actualRegion = 0;
        }
        if(test.getPhysics().state.equals(State.OFF)){            
            actualRegion = 1;
        }
        if(test.getPhysics().state.equals(State.ON_FIRE)){            
            actualRegion = 2;
        }
    }
    
}
