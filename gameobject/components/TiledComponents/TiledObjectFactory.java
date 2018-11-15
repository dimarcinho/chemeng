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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chemeng.GUI;
import com.chemeng.TiledWorld;
import com.chemeng.gamestates.TiledLevelState;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marci
 */
public class TiledObjectFactory extends Actor {
    
    private static final String TAG = TiledObjectFactory.class.getSimpleName();
    
    private TiledLevelState level;
    private Operut operutType;    
    private Rectangle objectArea;
    
    private Image shadow;
    
    private final float TILE_WIDTH = 64, TILE_HEIGHT = 64;    
    
    public TiledObjectFactory(final Operut operutType, final TiledLevelState level){
        this.operutType = operutType;
        this.level = level;
        
        this.setArea(operutType);
        
        TiledMapTileLayer layer = (TiledMapTileLayer) 
                level.getTiledWorld().getTiledMap().getLayers().get(0);        
        
        float WIDTH_AREA = TILE_WIDTH*layer.getWidth();
        float HEIGHT_AREA = TILE_HEIGHT*layer.getHeight();
        
        setBounds(64,64,WIDTH_AREA-128,HEIGHT_AREA-128);
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
                    
                    //Verifica se tem dinheiro
                    /*
                    ---------------
                    Implementar rotina para checagem do dinheiro
                    ---------------
                    */    
                    //Verifica se há colisões
                    if(!collides()){
                        //Cria o objeto
                        try {                        
                            createObject(x,y, operutType, level);
                            Gdx.app.log(TAG, "Objeto criado!");
                            //removeFactory();                        
                        } catch (Exception ex) {                        
                            Logger.getLogger(TiledObjectFactory.class.getName()).log(Level.SEVERE, null, ex);                        
                        }                        
                    } else {
                        Gdx.app.log(TAG, "Impossível criar objeto! -- já um objeto no local");
                    }
                    
                } else if(button == 1){
                    
                    removeFactory();
                    Gdx.app.log(TAG, "Factory desativada");
                    
                } else if(button == 2){
                    //implementar rotação
                    //shadow.setRotation(90+shadow.getRotation());
                }               
                return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        
    }
    
    public void removeFactory(){
        shadow.remove();
        this.remove();
    }
    
    public boolean collides(){
        TiledWorld world = level.getTiledWorld();
        for(GameObject obj : world.objects){
            if(obj.collides(objectArea))
                return true;
        }
        return false;
    }
    
    public final void setArea(Operut operutType){
        objectArea = new Rectangle();
        /*
        incluir rotina para buscar numa base de dados ou num singleton os valores,
        evitando o uso de IF`s
        
        ps - uma classe static (ou singleton) pode ser criado no começo e os valores
        alocados na memória através de um Map ou Set, de maneira que não usaria vários if's
        */
        
        Texture texture = null;
        
        switch (operutType) {
            case PUMP:
                objectArea.set(0, 0, 128, 64);
                texture = new Texture(Gdx.files.internal("img/pump_128x64.png"));
                break;
            case SOURCE:
                objectArea.set(0, 0, 128, 64);
                texture = new Texture(Gdx.files.internal("img/source_128x64.png"));
                break;
            case TANK_CLIENT:
                objectArea.set(0, 0, 192, 192);
                texture = new Texture(Gdx.files.internal("img/tank_client_192x192.png"), true);
                break;
            case MIXER:
                objectArea.set(0, 0, 64, 192);
                texture = new Texture(Gdx.files.internal("img/basic_mixer_64x192.png"));
                break;
            case PERMUTADOR:
                objectArea.set(0, 0, 448, 128);
                texture = new Texture(Gdx.files.internal("img/permutador_448x128.png"));
                break;
            case FLASH_DRUM:
                objectArea.set(0, 0, 192, 320);
                texture = new Texture(Gdx.files.internal("img/flash_drum_192x320.png"));
                break;
            case CHIMNEY:
                objectArea.set(0, 0, 64, 256);
                texture = new Texture(Gdx.files.internal("img/chamine_64x256.png"));
                break;
            case HORIZONTAL_DRUM:
                objectArea.set(0, 0, 512, 128);
                texture = new Texture(Gdx.files.internal("img/horizontal_drum_512x128.png"));
                break;
            case VALVE_ON_OFF:
                objectArea.set(0, 0, 128, 64);
                texture = new Texture(Gdx.files.internal("img/on_off_valve128x64.png"));
                break;
            case ELECTRIC_HEATER:
                objectArea.set(0, 0, 448, 128);
                texture = new Texture(Gdx.files.internal("img/electric_heater_448x128.png"));
                break;
            case CHILLER:
                objectArea.set(0, 0, 320, 320);
                texture = new Texture(Gdx.files.internal("img/chiller_320x320.png"));
                break;
            case DRAIN:
                objectArea.set(0, 0, 64, 64);
                texture = new Texture(Gdx.files.internal("img/drain_64x64.png"));
                break;
            case ORIFICE_PLATE:
                objectArea.set(0, 0, 128, 64);
                texture = new Texture(Gdx.files.internal("img/orifice_plate_128x64.png"));
                break;
            case COMPRESSOR:
                objectArea.set(0, 0, 384, 192);
                texture = new Texture(Gdx.files.internal("img/compressor_384x192.png"));
                break;
            case VALVE_PCV:
                objectArea.set(0, 0, 128, 64);
                texture = new Texture(Gdx.files.internal("img/pcv_valve_128x64.png"));
                break;
            default:
                Gdx.app.log(TAG, "Erro criando objeto: "+operutType.toString());
                break;
        }
        
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        shadow = new Image(new TextureRegion(texture, (int)objectArea.getWidth(), (int)objectArea.getHeight()));
        
    }

    public Image getShadow() {
        return shadow;
    }
    
    public void update(float x, float y){
        //corrige pra atulizar na grade de tiles
        //---------
        x = (float) (64*Math.floor(x/64));        
        y = (float) (64*Math.floor(y/64));        
        //---------
        
        //desenhando a partir da origem        
        objectArea.setPosition(x, y); 
        shadow.setPosition(x, y);
        
        //corrige a cor do shadow a depender da situação
        if(!collides()){
            shadow.setColor(0.f, 1.f, 0.f, 0.5f);            
        } else {
            shadow.setColor(1.f, 0.f, 0.f, 0.5f);
        }        
    }

    
    public static void createObject(float x, float y, Operut operut, TiledLevelState level) throws Exception {
        x = (float) (64*Math.floor(x/64));
        y = (float) (64*Math.floor(y/64));
        GameObject obj;
        
        switch (operut) {
            case PUMP:
                obj = new PumpTiled(x,y);
                obj.init(new PumpTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new PumpTiledPhysicsComponent(),
                        new PumpTiledGraphicsComponent(obj.x, obj.y));
                break;
            case SOURCE:
                obj = new SourceTiled(x,y);
                obj.init(new SourceTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new SourceTiledPhysicsComponent(),
                        new SourceTiledGraphicsComponent(obj.x, obj.y));
                break;
            case TANK_CLIENT:
                obj = new TankClientTiled(x,y);
                obj.init(new TankClientTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new TankClientTiledPhysicsComponent(level.getContratoManager()),
                        new TankClientTiledGraphicsComponent(obj.x, obj.y));
                break;
            case MIXER:
                obj = new MixerTiled(x,y);
                obj.init(new MixerTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new MixerTiledPhysicsComponent(),
                        new MixerTiledGraphicsComponent(obj.x, obj.y));
                break;
            case PERMUTADOR:
                obj = new PermutadorTiled(x,y);
                obj.init(new PermutadorTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new PermutadorTiledPhysicsComponent(),
                        new PermutadorTiledGraphicsComponent(obj.x, obj.y));
                break;
            case FLASH_DRUM:
                obj = new FlashDrumTiled(x,y);
                obj.init(new FlashDrumTiledInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new FlashDrumTiledPhysicsComponent(),
                        new FlashDrumTiledGraphicsComponent(obj.x, obj.y));
                break;
            case CHIMNEY:
                obj = new Chimney(x,y);
                obj.init(new ChimneyInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new ChimneyPhysicsComponent(),
                        new ChimneyGraphicsComponent(obj.x, obj.y));
                break;
            case HORIZONTAL_DRUM:
                obj = new HorizontalDrum(x,y);
                obj.init(new HorizontalDrumInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new HorizontalDrumPhysicsComponent(),
                        new HorizontalDrumGraphicsComponent(obj.x, obj.y));
                break;
            case VALVE_ON_OFF:
                obj = new ValveOnOff(x,y);
                obj.init(new ValveOnOffInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new ValveOnOffPhysicsComponent(),
                        new ValveOnOffGraphicsComponent(obj.x, obj.y));
                break;
            case ELECTRIC_HEATER:
                obj = new ElectricHeater(x,y);
                obj.init(new ElectricHeaterInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new ElectricHeaterPhysicsComponent(),
                        new ElectricHeaterGraphicsComponent(obj.x, obj.y));
                break;
            case CHILLER:
                obj = new Chiller(x,y);
                obj.init(new ChillerInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new ChillerPhysicsComponent(),
                        new ChillerGraphicsComponent(obj.x, obj.y));
                break;
            case DRAIN:
                obj = new Drain(x,y);
                obj.init(new DrainInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new DrainPhysicsComponent(),
                        new DrainGraphicsComponent(obj.x, obj.y));
                break;
            case ORIFICE_PLATE:
                obj = new OrificePlate(x,y);
                obj.init(new OrificePlateInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new OrificePlatePhysicsComponent(),
                        new OrificePlateGraphicsComponent(obj.x, obj.y));
                break;
            case COMPRESSOR:
                obj = new Compressor(x,y);
                obj.init(new CompressorInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new CompressorPhysicsComponent(),
                        new CompressorGraphicsComponent(obj.x, obj.y));
                break;
            case VALVE_PCV:
                obj = new ValvePCV(x,y);
                obj.init(new ValvePCVInputComponent(obj.x,obj.y,obj.width,obj.height),
                        new ValvePCVPhysicsComponent(),
                        new ValvePCVGraphicsComponent(obj.x, obj.y));
                break;
            default:
                Gdx.app.log(TAG, "GameObject nao encontrado em Factory: "+operut);
                return;
        }
        
        TiledWorld tiledWorld = level.getTiledWorld();
        tiledWorld.add(obj);
        tiledWorld.createEquipment(obj);
        tiledWorld.addGraphics(obj);
        obj.getInput().addToStage(level.getStageGame());
        
    }
    
}
