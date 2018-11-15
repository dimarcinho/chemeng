/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.chemeng.GUI;

/**
 *
 * @author marci
 */
public class ValveOnOffGraphicsComponent extends TiledGraphicsComponent{
    
    private static final String TAG = ValveOnOffGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tmo;    
    //private TextureRegion textureRegion;
    
    private int frames = 2, actualRegion = 0;
    private TextureRegion[] regions;
    
    public ValveOnOffGraphicsComponent(float x, float y){
        
        Texture spritesheet = new Texture(Gdx.files.internal("img/on_off_valve128x64.png"));
        spritesheet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);        
        
        regions = new TextureRegion[frames];
        for(int i = 0; i < frames; i++){
            regions[i] = new TextureRegion(spritesheet, 128*i,0,128,64);
        }        
        actualRegion = 0;
        
        tmo = new TextureMapObject(regions[0]);
        tmo.setX(x);
        tmo.setY(y);
        
    }

    @Override
    public TextureMapObject getTextureMapObject() {
        return tmo;
    }

    public void setRegion(TextureRegion tex){
        tmo.setTextureRegion(tex);
    }
    
    @Override
    public TextureRegion getTextureRegion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(GameObject obj) {
        ValveOnOffPhysicsComponent v = (ValveOnOffPhysicsComponent) obj.getPhysics();
        
        if(v.isOpened()){
            actualRegion = 0;
        } else {
            actualRegion = 1;
        }
        tmo.setTextureRegion(regions[actualRegion]);
    }

    @Override
    public void render(GameObject obj, SpriteBatch batch) {
        batch.draw(tmo.getTextureRegion(),
        tmo.getX(), tmo.getY(),
        tmo.getOriginX(), tmo.getOriginY(),
        tmo.getTextureRegion().getRegionWidth(),
        tmo.getTextureRegion().getRegionHeight(),
        tmo.getScaleX(), tmo.getScaleY(),
        tmo.getRotation());
        
        double number;
        String str;
        
        number = obj.getPhysics().getEntrada(0).getFlow();
        str = String.format("%.2f", number);
        drawString(obj, batch, "F_in(m3/h): "+str, 16, -16);        
        
    }
        
    private void drawString(GameObject o, SpriteBatch batch,
                                String str, float x, float y){
        GUI.getInstance().getFont().draw(batch, 
                (CharSequence)(str), 
                                        o.x + x, 
                                        o.y - y);        
    }
    
    @Override
    public void handleGameEvent(GameEvent event, java.lang.Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
