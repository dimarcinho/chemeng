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
public class ValvePCVGraphicsComponent extends TiledGraphicsComponent{
    
    private static final String TAG = ValvePCVGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tmo;        
    private TextureRegion textureRegion;
    
    public ValvePCVGraphicsComponent(float x, float y){
        
        Texture texture = new Texture(Gdx.files.internal("img/pcv_valve_128x64.png"));
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
        
        number = obj.getPhysics().getEntrada(0).getPressure()/101325f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "P_in(atm): "+str, 16, -16);
        
        number = obj.getPhysics().getSaida(0).getPressure()/101325f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "P_out(atm): "+str, 16, -0);
        
        ValvePCVPhysicsComponent pc = (ValvePCVPhysicsComponent) obj.getPhysics();        
        
        number = pc.getOpen()*100;        
        str = String.format("%.4f", number);
        drawString(obj, batch, "Open(%): "+str, 16, 16);
        
        number = pc.u;
        str = String.format("%.4f", number);
        drawString(obj, batch, "Speed(m/s): "+str, 16, 32);
        
        number = pc.deltaP;
        str = String.format("%.4f", number);
        drawString(obj, batch, "deltaP(atm): "+str, 16, 48);
        
        number = pc.K;
        str = String.format("%.2f", number);
        drawString(obj, batch, "K_value(m): "+str, 16, 64);
        
        number = pc.setPoint/101325d;
        str = String.format("%.2f", number);
        drawString(obj, batch, "SetPoint(atm): "+str, 16, 80);
        
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
