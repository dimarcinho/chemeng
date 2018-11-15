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
public class MixerTiledGraphicsComponent extends TiledGraphicsComponent{
    
    private static final String TAG = MixerTiledGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tmo;    
    private TextureRegion textureRegion;
    
    public MixerTiledGraphicsComponent(float x, float y){
        
        Texture texture = new Texture(Gdx.files.internal("img/basic_mixer_64x192.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);        
        textureRegion = new TextureRegion(texture);
        
        tmo = new TextureMapObject(textureRegion);
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
        drawString(obj, batch, "F_in(m3/h): "+str, 72, -192);
        
        number = obj.getPhysics().getSaida(0).getFlow();
        str = String.format("%.2f", number);
        drawString(obj, batch, "F_out(m3/h): "+str, 72, -128);
        
        number = obj.getPhysics().getEntrada(1).getFlow();
        str = String.format("%.2f", number);
        drawString(obj, batch, "F_in(m3/h): "+str, 72, -64);
        
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
