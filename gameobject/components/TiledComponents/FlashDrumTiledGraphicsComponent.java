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
public class FlashDrumTiledGraphicsComponent extends TiledGraphicsComponent {

    private static final String TAG = FlashDrumTiledGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tmo;    
    private TextureRegion textureRegion;
    
    public FlashDrumTiledGraphicsComponent(float x, float y){
        
        Texture texture = new Texture(Gdx.files.internal("img/flash_drum_192x320.png"));
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
        drawString(obj, batch, "F_in(m3/h): "+str, 8, 24);            

        number = obj.getPhysics().getSaida(0).getFlow();
        str = String.format("%.2f", number);
        drawString(obj, batch, "F_topo(m3/h): "+str, 8, 40);            
        
        number = obj.getPhysics().getSaida(1).getFlow();
        str = String.format("%.2f", number);
        drawString(obj, batch, "F_fundo(m3/h): "+str, 8, 56);
        
        number = obj.getPhysics().getSaida(0).getTemperature()-273.15f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "T(oC): "+str, 8, 72);            

        number = obj.getPhysics().getSaida(0).getPressure()/101325f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "P(atm): "+str, 8, 88);
        
        FlashDrumTiledPhysicsComponent p = (FlashDrumTiledPhysicsComponent) obj.getPhysics();
        number = p.getBeta();
        str = String.format("%.4f", number);
        drawString(obj, batch, "Beta: "+str, 8, 104);
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
