/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.components.TiledComponents.GameObject;
import com.chemeng.gameobjects.components.TiledComponents.LineGraphicsComponent;
import com.chemeng.gameobjects.components.TiledComponents.Operut;
import com.chemeng.tilemap.TiledConnection.Direction;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author marci
 */
public class SaveMapObjects {
    
    private static final String TAG = SaveMapObjects.class.getName();
 
    private JSONArray jarray;   
    
    public SaveMapObjects(TiledWorld world){    
        
        jarray = new JSONArray();
     
        for(GameObject obj : world.objects){
            
            
            if(obj.getType().equals(Operut.LINE)){
                
                LineGraphicsComponent lgc = (LineGraphicsComponent) obj.getGraphics();
                
                saveLineData(Operut.LINE, 
                            lgc.getX(), lgc.getY(),
                            lgc.getWidth(), lgc.getHeight(),
                            lgc.getV0(), lgc.getVf(),
                            lgc.getT0(), lgc.getTf(),
                            lgc.getD0(), lgc.getDf());
                
            } else {
            
                saveData(obj.getType(), obj.x, obj.y);
                
            }
        }
        
        FileWriter writeFile;
        try{
            writeFile = new FileWriter("data/savegame.json");
            //Escreve no arquivo conteudo do Objeto JSON
            //writeFile.write(jsonObject.toString());
            writeFile.write(jarray.toString());
            writeFile.close();            
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //Imprimne na Tela o Objeto JSONArray para vizualização
        Gdx.app.log(TAG, jarray.toString());
        
        Gdx.app.log(TAG, "Game Salvo!");
    }    
    
    private void saveData(Operut type, float x, float y){
            
        JSONObject jsonObject = new JSONObject();            
            
        jsonObject.put("tipo", type);
        jsonObject.put("x", x);
        jsonObject.put("y", y);

        jarray.put(jsonObject); 
            
    }
    
    private void saveLineData(Operut type,
                                float x, float y, float width, float height,
                                Vector2 v0, Vector2 vf,
                                ConnectionType t0, ConnectionType tf,
                                Direction d0, Direction df){
            
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("tipo", type);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            jsonObject.put("width", width);
            jsonObject.put("height", height);
            jsonObject.put("v0", v0);
            jsonObject.put("vf", vf);
            jsonObject.put("t0", t0);
            jsonObject.put("tf", tf);
            jsonObject.put("d0", d0);
            jsonObject.put("df", df);            
            
            jarray.put(jsonObject);
    }

    
}
