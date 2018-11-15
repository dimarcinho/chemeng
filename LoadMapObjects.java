/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.components.TiledComponents.*;
import com.chemeng.gamestates.TiledLevelState;
import com.chemeng.tilemap.TiledConnection.Direction;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author marci
 */
public class LoadMapObjects {
    
    private static final String TAG = LoadMapObjects.class.getName();
 
    private FileHandle handle;
    private JSONArray jarray;
    
    public LoadMapObjects(TiledLevelState level){
        
        loadData();
        
        level.getTiledWorld().removeAll();
                
        Iterator it = jarray.iterator();
        
        //Constroi os objetos
        while(it.hasNext()){
            
            JSONObject jobj = (JSONObject) it.next();
            
            String s = (String) jobj.get("tipo");
            Operut type = Operut.valueOf(s);
            
            if(!type.equals(Operut.LINE)){
                
                Gdx.app.log(TAG, jobj.toString());
                
                Object x = jobj.get("x");
                Object y = jobj.get("y");

                float xx = Float.valueOf(x.toString());
                float yy = Float.valueOf(y.toString());

                try {
                    loadObject(xx, yy, type, level);
                } catch (Exception ex) {
                    Logger.getLogger(LoadMapObjects.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        it = null;
        it = jarray.iterator();

        //Constroi as linhas
        while(it.hasNext()){

            JSONObject jobj = (JSONObject) it.next();

            Gdx.app.log(TAG, jobj.toString());

            String s = (String) jobj.get("tipo");
            Operut type = Operut.valueOf(s);

            if(type.equals(Operut.LINE)){

                try {
                    loadLine(jobj, level);
                } catch (Exception ex) {
                    Logger.getLogger(LoadMapObjects.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        }
    }
    public final void loadData() {
            
        try {
            handle = Gdx.files.internal("data/savegame.json");                
            Gdx.app.log(TAG, "handle.readString(): "+handle.readString());            
            jarray = new JSONArray(handle.readString());
            Gdx.app.log(TAG, jarray.toString());
            handle = null;
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public final void loadLine(JSONObject jobj, TiledLevelState level) throws Exception{
        
        String s;
        
        Object xx = jobj.get("x");
        float x = Float.valueOf(xx.toString());
        Object yy = jobj.get("y");
        float y = Float.valueOf(yy.toString());
        Object ww = jobj.get("width");
        float width = Float.valueOf(ww.toString());
        Object hh = jobj.get("height");
        float height = Float.valueOf(hh.toString());
        
        Object vv0 = jobj.get("v0");
        s = vv0.toString();
        Vector2 v0 = new Vector2();
        v0.fromString(s);
        
        Object vvf = jobj.get("vf");
        s = vvf.toString();
        Vector2 vf = new Vector2();
        vf.fromString(s);
        
        s = (String) jobj.getString("t0");
        ConnectionType type0 = ConnectionType.valueOf(s);
        s = (String) jobj.getString("tf");
        ConnectionType typef = ConnectionType.valueOf(s);
        
        s = (String) jobj.getString("d0");
        Direction d0 = Direction.valueOf(s);
        s = (String) jobj.getString("df");
        Direction df = Direction.valueOf(s);
        
        GameObject obj;
        
            obj = new LineTiled(x, y, width, height);
            
            obj.init(new LineInputComponent(x, y, width, height),
                    createLinePhysicsComponent(v0, vf, type0, d0, typef, df),
                    new LineGraphicsComponent(  x, y, width, height,
                                                v0, type0, d0,
                                                vf, typef, df)
                    );
        
        TiledWorld tiledWorld = level.getTiledWorld();
        tiledWorld.add(obj);
        tiledWorld.createEquipment(obj);
        tiledWorld.addGraphics(obj);
        obj.getInput().addToStage(level.getStageGame());
    }
    
      private LinePhysicsComponent createLinePhysicsComponent(Vector2 in, Vector2 out, ConnectionType type0, Direction d0, ConnectionType typef, Direction df){
        
        LinePhysicsComponent pc = null;
        
        Vector2 input, output, origin;
        
        origin = out.cpy();
        
        input = in.cpy();
        output = out.cpy();
        
        input.sub(origin.cpy());
        input.scl(1/64f);
        output.sub(origin.cpy());
        output.scl(1/64f);
        
        if(input.x < 0 || input.y < 0){
            input.scl(-1.f);
            pc = new LinePhysicsComponent(output, type0, d0,
                                            input, typef, df);            
        } else {
            pc = new LinePhysicsComponent(input, type0, d0,
                                            output, typef, df);
        }
        
        return pc;

    }
    
    public final void loadObject(float x, float y, Operut type, TiledLevelState level) throws Exception {
        x = (float) (64*Math.floor(x/64));
        y = (float) (64*Math.floor(y/64));
        GameObject obj;
        
        
        switch (type) {
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
                Gdx.app.log(TAG, "GameObject nao encontrado em Factory: "+type.toString());
                return;
        }
        
        TiledWorld tiledWorld = level.getTiledWorld();
        tiledWorld.add(obj);
        tiledWorld.createEquipment(obj);
        tiledWorld.addGraphics(obj);
        obj.getInput().addToStage(level.getStageGame());
        
    }
    
}
