/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chemeng.TiledWorld;
import com.chemeng.gamestates.TiledLevelState;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marci
 */
public class TiledObjectDestroyer extends Actor {
 
    //private static final String TAG = TiledObjectDestroyer.class.getName();    
    private static final String TAG = TiledObjectDestroyer.class.getSimpleName();
    
    private TiledLevelState level;
    private Image icon;
    private Rectangle objectArea;
    
    private static boolean devMode;
    
    public TiledObjectDestroyer(TiledLevelState level){
        this.level = level;
        
        this.setArea();
        this.createImageIcon();
        
        devMode = false;
        
        TiledMapTileLayer layer = (TiledMapTileLayer) 
                level.getTiledWorld().getTiledMap().getLayers().get(0);
        
        //Gdx.app.log(TAG, 64*layer.getWidth()+"");
        
        float WIDTH_AREA = 64*layer.getWidth();
        float HEIGHT_AREA = 64*layer.getHeight();
        setBounds(0,0,WIDTH_AREA,HEIGHT_AREA);
        
        this.addListener(new InputListener(){
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                update(x, y);
                super.enter(event, x, y, pointer, fromActor); //To change body of generated methods, choose Tools | Templates.
            }
            
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                update(x, y);
                return super.mouseMoved(event, x, y); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                
                update(x, y);
                
                if(button == 0){
                
                    Gdx.app.log(TAG, "Teste Coordenadas x,y");
                    Gdx.app.log(TAG, "(x,y): ("+x+", "+y+")");                    
                    
                    if(collides(devMode)){
                        /*
                        TOCA SOM DE OBJETO SENDO DESTRUÍDO
                        */
                    } else {
                        /*
                        TOCA SOM DE ERRO 
                        OU NÃO FAZ NADA
                        */
                    }
                    
                } else if(button == 1){
                    
                    removeDestroyer();
                    Gdx.app.log(TAG, "Destroyer desativado");
                    
                }                
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }

    public static void setDevMode(boolean devMode) {
        TiledObjectDestroyer.devMode = devMode;
    }

    public static boolean isDevMode() {
        return devMode;
    }
    
    public void removeDestroyer(){
        icon.remove();
        this.remove();
    }
    
    public boolean collides(boolean devMode){
        
        CollideStrategy context;
        
        if(devMode){
            context = new DevModeStrategy();
        } else {
            context = new PlayerStrategy();
        }
        
        return context.collides();

    }
    
    public final void setArea(){
        objectArea = new Rectangle();
        objectArea.set(0, 0, 32, 32);        
    }
    /**
     *
     */
    public final void createImageIcon(){
        Texture texture = new Texture(Gdx.files.internal("img/cursor/icon_destroyer_32x32.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        icon = new Image(new TextureRegion(texture));
    }

    public Image getIcon() {
        return icon;
    }
    
    public void update(float x, float y){
        //corrige pra atulizar na grade de tiles
        //---------
        //x = (float) (64*Math.floor(x/64));
        //y = (float) (64*Math.floor(y/64));
        //---------
        
        //desenhando a partir da origem
        float offset = 16f;
        objectArea.setPosition(x-offset, y-offset); 
        icon.setPosition(x-offset, y-offset);
       
    }
    
    private interface CollideStrategy{
        public boolean collides();
    }
    
    private class PlayerStrategy implements CollideStrategy {

        @Override
        public boolean collides() {
            TiledWorld world = level.getTiledWorld();
            for(GameObject obj : world.objects){
                if(obj.collides(objectArea)){
                    
                    if(obj.getType().equals(Operut.SOURCE) ||
                            obj.getType().equals(Operut.TANK_CLIENT)){
                        
                        Gdx.app.log(TAG, "Objeto não pode ser removido! - "+obj.getClass().getSimpleName());
                        
                    } else {
                        Gdx.app.log(TAG, "Removendo objeto: "+obj.getClass().getSimpleName());
                        world.remove(obj);
                        return true;
                    }
                }
            }
            return false;
        }
    
    }
    
    private class DevModeStrategy implements CollideStrategy {

        @Override
        public boolean collides() {
            TiledWorld world = level.getTiledWorld();
            for(GameObject obj : world.objects){
                if(obj.collides(objectArea)){
                    Gdx.app.log(TAG, "Removendo objeto: "+obj.getClass().getSimpleName());
                    world.remove(obj);
                    return true;                    
                }
            }
            return false;
        }
    
    }
    
}
