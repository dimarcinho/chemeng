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
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.tilemap.TiledConnection;
import com.chemeng.tilemap.TiledConnection.Direction;

/**
 *
 * @author marci
 */
public class LineGraphicsComponent extends TiledGraphicsComponent{
        
    private static final String TAG = LineGraphicsComponent.class.getSimpleName();
    
    private TextureMapObject tempTMO = new TextureMapObject();
    private TextureMapObject[] objects;
    private TextureRegion[] regions;
    private int actualRegion;
    
    private float x, y, width, height;
    private Vector2 v0, vf;
    private ConnectionType t0, tf;
    private Direction d0, df;
    
    public LineGraphicsComponent(float x, float y, float width, float height,
                                Vector2 Point0, ConnectionType type0, Direction d0,
                                Vector2 Pointf, ConnectionType typef, Direction df){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.v0 = Point0;
        this.vf = Pointf;
        this.t0 = type0;
        this.tf = typef;
        this.d0 = d0;
        this.df = df;
        
        boolean isVertical = (width <=64);
        
        Texture spritesheet = new Texture(Gdx.files.internal("img/linhas_ss.png"),true);        
        spritesheet.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.MipMapLinearNearest);
        
        int k = spritesheet.getWidth()/64; //número de frames na linha                
        
        regions = new TextureRegion[k];        
        for(int i = 0; i <= k-1; i++){
            regions[i] = new TextureRegion(spritesheet, 64*i,0,64,64);
        }        
        actualRegion = 0;
        
        if(isVertical){            
            objects = new TextureMapObject[(int)(height/64)];
        } else { //entao é horizontal
            objects = new TextureMapObject[(int)(width/64)];
        }
        
        int lastIndex = objects.length - 1;
        
        Vector2 v0 = this.getVectorConnectionDirection(d0, type0);
        Vector2 vf = this.getVectorConnectionDirection(df, typef);
        Vector2 flowDir = this.getFlowDirection(Point0, Pointf, type0);
        
        if(Point0.x > Pointf.x || Point0.y > Pointf.y){ //Point0 é o ponto `mais longe`
            objects[0] = this.getRegion(vf, flowDir, typef, regions);
            objects[0].setX(x);
            objects[0].setY(y);
            rotate(objects[0], getAngle(vf));
            objects[lastIndex] = this.getRegion(v0, flowDir, type0, regions);
            objects[lastIndex].setX(x+width-64);
            objects[lastIndex].setY(y+height-64);
            rotate(objects[lastIndex], getAngle(v0));
        } else {
            objects[0] = this.getRegion(v0, flowDir, type0, regions);
            objects[0].setX(x);
            objects[0].setY(y);
            rotate(objects[0], getAngle(v0));
            objects[lastIndex] = this.getRegion(vf, flowDir, typef, regions);
            objects[lastIndex].setX(x+width-64);
            objects[lastIndex].setY(y+height-64);
            rotate(objects[lastIndex], getAngle(vf));
        }
        
        for(int i = 1; i < lastIndex; i++){
            objects[i] = new TextureMapObject(regions[0]);
            if(isVertical){
                objects[i].setX(x);
                objects[i].setY(y+64*i);
                rotate90(objects[i]);                
            } else {                
                objects[i].setX(x+64*i);
                objects[i].setY(y);
            }
        }

    }
    
    private Vector2 getFlowDirection(Vector2 Point0, Vector2 Pointf, ConnectionType type0){
        Vector2 flowDirection;
        Vector2 p0 = Point0.cpy();
        Vector2 pf = Pointf.cpy();        
        if(type0.equals(ConnectionType.SAIDA)){
            flowDirection = p0.sub(pf).cpy();            
        } else {
            flowDirection = pf.sub(p0).cpy();
        }
        flowDirection.clamp(-1, 1);
        //Gdx.app.log(TAG, "flowDir: "+flowDirection.toString());
        return flowDirection;
    }
    
    private TextureMapObject getRegion(Vector2 v, Vector2 flowDirection, 
                                        ConnectionType type, TextureRegion[] regions){
        
        TextureMapObject tmo = null;
        Vector2 v0 = v.cpy();
        Vector2 flowDir = flowDirection.cpy();
        
        //Pego o Angulo em relacao ao vetor (-1,0)
        float angulo = v0.angle(new Vector2(-1,0));
        
//        Gdx.app.log(TAG, "flowDir: "+flowDir.toString());
//        Gdx.app.log(TAG, "v: "+v0.toString());
//        Gdx.app.log(TAG, "angulo_v_left: "+v0.angle(new Vector2(-1,0)));
//        Gdx.app.log(TAG, "angulo: "+angulo);
        
        if(v0.isOnLine(flowDir)){
            tmo = new TextureMapObject(regions[1]);
//            Gdx.app.log(TAG, "Colineares!");
        } else {
            Vector2 sum = v0.add(flowDir);
            sum.rotate(angulo);
//            Gdx.app.log(TAG, "Sum: "+sum.toString());
            if(type.equals(ConnectionType.SAIDA)){                
                if(sum.y > 0){
                    tmo = new TextureMapObject(regions[3]);
                } else {
                    tmo = new TextureMapObject(regions[2]);
                }
            } else {
                if(sum.y > 0){
                    tmo = new TextureMapObject(regions[2]);
                } else {
                    tmo = new TextureMapObject(regions[3]);
                }
            }
        }

//        Gdx.app.log(TAG, "\n");

        return tmo;
    }
    
    private float getAngle(Vector2 v0){
        return v0.angle(new Vector2(-1,0));
    }
    
    private void rotate(TextureMapObject tmo, float angulo){
        int a = (int) angulo;
        
//        Gdx.app.log(TAG, "Angulo em rotate(): "+angulo+" -- (int): "+a);
        
        switch (a){        
            case 0:
                //não precisa fazer nada, a figura já está na posição correta
                break;
            case 90:
                rotate270(tmo);
                break;
            case 180:
                rotate180(tmo); 
                break;
            case -90:
                rotate90(tmo);
                break;
            default:
                Gdx.app.log(TAG, "Erro rotacionando figuras...");
        }
    }
    
    private Vector2 getVectorConnectionDirection(Direction dir, ConnectionType type){
        Vector2 u = new Vector2();
        
        switch(dir){
            case UP:
                u.set(0, 1);
                break;
            case DOWN:
                u.set(0, -1);
                break;
            case LEFT:
                u.set(-1, 0);
                break;
            case RIGHT:
                u.set(1, 0);
                break;
            default:
                Gdx.app.log(TAG, "Erro em getVectorDirection()");
                break;
        }
        
        if(type.equals(ConnectionType.ENTRADA)){
            u.rotate(180);
            u.x = (int) u.x;
            u.y = (int) u.y;
        }
        
        return u;
    }
    
    private static void rotate180(TextureMapObject tmo){        
        tmo.setRotation(180);
        float old_x = tmo.getX();
        float old_y = tmo.getY();        
        tmo.setX(old_x+64);
        tmo.setY(old_y+64);
//        Gdx.app.log(TAG, "Rotacionando 180...");
    }
    
    private static void rotate90(TextureMapObject tmo){        
        tmo.setRotation(90);
        float old_x = tmo.getX();
        tmo.setX(old_x+64);
//        Gdx.app.log(TAG, "Rotacionando 90...");
    }
    
    private static void rotate270(TextureMapObject tmo){        
        tmo.setRotation(270);
        float old_y = tmo.getY();        
        tmo.setY(old_y+64);
//        Gdx.app.log(TAG, "Rotacionando 270...");
    }

    @Override
    public TextureMapObject getTextureMapObject() {        
        return tempTMO;
        /*
        Aqui é um certo desperdício de memória, mas como cada componente gráfico
        realiza sua própria renderização, então não há problemas
        */
    }
    
    @Override
    public TextureRegion getTextureRegion() {
        return regions[actualRegion]; 
    }    

    @Override
    public void update(GameObject obj) {

    }

    @Override
    public void render(GameObject obj, SpriteBatch batch) {               
        
            for(int i = 0; i < objects.length; i++){
                
                batch.draw(objects[i].getTextureRegion(),
                    objects[i].getX(), objects[i].getY(),
                    objects[i].getOriginX(), objects[i].getOriginY(),
                    objects[i].getTextureRegion().getRegionWidth(),
                    objects[i].getTextureRegion().getRegionHeight(),
                    objects[i].getScaleX(), objects[i].getScaleY(),
                    objects[i].getRotation());                
            }
    }

    interface Strategy {
        public void render(GameObject obj, SpriteBatch batch, TextureMapObject[] lines);
    }
    
    private class VerticalStrategy implements Strategy {

        @Override
        public void render(GameObject obj, SpriteBatch batch, TextureMapObject[] lines) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class HorizontalStrategy implements Strategy {

        @Override
        public void render(GameObject obj, SpriteBatch batch, TextureMapObject[] lines) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class LineAlignment {
        private Strategy strategy;
        public LineAlignment(Strategy strategy){
            this.strategy = strategy;
        }
        public void render(GameObject obj, SpriteBatch batch, TextureMapObject[] lines) {
            strategy.render(obj, batch, lines);
        }
    }
    
    @Override
    public void handleGameEvent(GameEvent event, java.lang.Object obj) {
        switch(event){
            default:
                //nao faz nada;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getV0() {
        return v0;
    }

    public Vector2 getVf() {
        return vf;
    }

    public ConnectionType getT0() {
        return t0;
    }

    public ConnectionType getTf() {
        return tf;
    }

    public Direction getD0() {
        return d0;
    }

    public Direction getDf() {
        return df;
    }
    
    
}
