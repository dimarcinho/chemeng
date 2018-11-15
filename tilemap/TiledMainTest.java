/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.tilemap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ArrayMap.Entries;
import com.badlogic.gdx.utils.ObjectMap;
import com.chemeng.GUI;
import com.chemeng.tilemap.LogicCell;

import java.util.Iterator;

public class TiledMainTest extends ApplicationAdapter {
    
    private static final String TAG = TiledMainTest.class.getName();
    
    TextureMapObject tmo;
    TiledMap tiledMap;    
    OrthographicCamera camera;    
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch batch;    
    Texture texture;
    Actor actor;
    Stage stage;
    
    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        Gdx.app.log(TAG, "Width: "+w);
        Gdx.app.log(TAG, "Height: "+h);
        
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        
        camera.setToOrtho(false,w,h);
        camera.update();        
        
        
        InternalFileHandleResolver handle = new InternalFileHandleResolver();
        handle.resolve("mapa_teste.tmx");
        TmxMapLoader loader = new TmxMapLoader(handle);        
        tiledMap = loader.load("img/mapas/mapa_teste.tmx");
        
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);        
        
        Iterator<String> itr = tiledMap.getProperties().getKeys();
        while(itr.hasNext()){
            String str = itr.next();            
            Gdx.app.log(TAG, "TiledMap->"+ str + ": " + tiledMap.getProperties().get(str));
        }
        
        Iterator<TiledMapTileSet> it_set = tiledMap.getTileSets().iterator();
        Iterator<TiledMapTile> it_tile = tiledMap.getTileSets().getTileSet(0).iterator();
        
        while(it_set.hasNext()){      
            Gdx.app.log(TAG, it_set.next().getName());
            while(it_tile.hasNext()){
                int a = it_tile.next().getId();
                Gdx.app.log(TAG, "Tile_ID: "+a);
            }
        }
        
        TiledMapTileLayer l = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        l.getCell(10, 2).setTile(
                tiledMap.getTileSets().getTileSet(0).getTile(149)
        );
        
        //TextureMapObject
        texture = new Texture(Gdx.files.internal("img/tanque_192x192.png"));
        tmo = new TextureMapObject(new TextureRegion(texture,192,192));        
        tmo.setX(128);
        tmo.setY(128);
        tiledMap.getLayers().get("Objects").getObjects().add(tmo);        
        
        //Stage & Actor
        stage = new Stage();
        actor = new Actor();
        actor.setBounds(128,128, tmo.getTextureRegion().getRegionWidth(), tmo.getTextureRegion().getRegionHeight());
        actor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log(TAG, "Tanque clicado!");
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if(pointer == -1)
                    Gdx.app.log(TAG, "Mouse saindo do actor!");                
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if(pointer == -1)
                    Gdx.app.log(TAG, "Mouse entrando no actor!");                
            }
                     
        });
        stage.addActor(actor);                
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().setCamera(camera);
        stage.setDebugAll(true);        

        testandoMaps();
    
    }
    public void testandoMaps(){
        
        Gdx.app.log(TAG, "---- Iniciando teste de Maps -----");
        
        ObjectMap<Vector2, Vector3> map = new ObjectMap<Vector2, Vector3>();
        
        map.put(new Vector2(10,8), new Vector3(0,10,8));
        map.put(new Vector2(0,1), new Vector3(1,3,5));
        map.put(new Vector2(53,7), new Vector3(2,5,9));
        map.put(new Vector2(0,0), new Vector3(0,0,0));
        map.put(new Vector2(23,5), new Vector3(32,31,79));
        map.put(new Vector2(7,31), new Vector3(81,4,45));
        
        
        Iterator<ObjectMap.Entry<Vector2, Vector3>> it = map.entries().iterator();
        while(it.hasNext()){
            ObjectMap.Entry<Vector2, Vector3> pair = it.next();
            Gdx.app.log(TAG, pair.key + " - " + pair.value);
        }

        Gdx.app.log(TAG, "---- Fim do teste de Maps -----");
    }

    @Override
    public void render () {

        input();
        
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        batch.setProjectionMatrix(camera.combined);
        
        BitmapFont font = GUI.getInstance().getFont();
        font.setColor(1f, 1f, 0f, 1f);
        
        batch.begin();        
        
        batch.draw(tmo.getTextureRegion(), tmo.getX(), tmo.getY(),
                tmo.getOriginX(), tmo.getOriginY(), tmo.getTextureRegion().getRegionWidth(), tmo.getTextureRegion().getRegionHeight(),
                tmo.getScaleX(), tmo.getScaleY(), tmo.getRotation());               
                    
        TiledMapTileLayer l = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        
        for(int i = 0; i < l.getWidth(); i++){
            for(int j = 0; j < l.getHeight(); j++){
        
                font.draw(batch, "("+i+","+j+")",
                        64*i+16,
                        64*j+32);
            }
        }
        
        batch.end();
        
        
        stage.act();
        stage.draw();

    }

    
    public void input(){
        
        float cameraSpeed = 7f;
        
        cameraSpeed*=camera.zoom;
        
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            camera.translate(0f, +cameraSpeed);            
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            camera.translate(0f, -cameraSpeed);                        
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            camera.translate(-cameraSpeed, 0f);                        
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            camera.translate(+cameraSpeed, 0f);                        
        } 
        if(Gdx.input.isKeyPressed(Input.Keys.F2)){
            camera.zoom += 0.01f;
            camera.zoom = 2.844444f;
        }if(Gdx.input.isKeyPressed(Input.Keys.F3)){
            camera.zoom -= 0.01f;
            camera.zoom = 1f;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            stage.dispose();
            batch.dispose();
            tiledMap.dispose();            
            Gdx.app.exit();
        }
        
        if(Gdx.input.justTouched()){
            float screenX, screenY;
            screenX = Gdx.input.getX();
            screenY = Gdx.input.getY();
            Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
            Vector3 position = camera.unproject(clickCoordinates);            
            int tileX, tileY;
            tileX = (int) Math.floor(position.x/64);
            tileY = (int) Math.floor(position.y/64);
            Gdx.app.log(TAG, "Tile clicado: "+tileX + ", " + tileY);
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            this.testandoMaps();
        }
            
    }
    
}