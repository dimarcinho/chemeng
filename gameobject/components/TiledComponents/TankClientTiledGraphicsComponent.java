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
import java.util.Arrays;

/**
 *
 * @author marci
 */
public class TankClientTiledGraphicsComponent extends TiledGraphicsComponent {

    private static final String TAG = FlashDrumTiledGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tmo;    
    private TextureRegion textureRegion;
    
    public TankClientTiledGraphicsComponent(float x, float y){
        
        Texture texture = new Texture(Gdx.files.internal("img/tank_client_192x192.png"), true);        
        texture.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.MipMapLinearNearest);
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
        
        //number = obj.getPhysics().getEntrada(0).getFlow();
        //str = Arrays.toString(obj.getPhysics().getEntrada(0).getZ());
        str = toPercentStringFormat(obj.getPhysics().getEntrada(0).getZ());
        drawString(obj, batch, "Composition(%): "+str, 8, 40);
        
        number = obj.getPhysics().getEntrada(0).getTemperature()-273.15f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "T(oC): "+str, 8, 56);            

        number = obj.getPhysics().getEntrada(0).getPressure()/101325f;
        str = String.format("%.2f", number);
        drawString(obj, batch, "P(atm): "+str, 8, 72);
    }
    
    private void drawString(GameObject o, SpriteBatch batch,
                                String str, float x, float y){
        GUI.getInstance().getFont().draw(batch, 
                (CharSequence)(str), 
                                        o.x + x, 
                                        o.y - y); 
    }
    
    private String toPercentStringFormat(double[] x){
        String str = "[ ", temp = "";
        
        for(int i = 0; i < x.length; i++){
            temp = String.format("%.2f", 100*x[i]);
            str = str.concat(temp+" ");
        }
        str = str.concat("]");
        
        return str;
    }
    
    @Override
    public void handleGameEvent(GameEvent event, java.lang.Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }
    
}
