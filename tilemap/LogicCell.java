/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.tilemap;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.chemeng.gameobjects.components.TiledComponents.GameObject;


/**
 *
 * @author Márcio Brito
 */
public class LogicCell extends TiledMapTileLayer.Cell {
        
    private GameObject obj = null;        
    private int weight = 0;
    private TiledConnection tiledConnection = null;
    
    public LogicCell(){
        super();
    }
    
    public LogicCell(GameObject obj, int weight, TiledConnection conex){
        super();
        this.obj = obj;
        this.weight = weight;
        this.tiledConnection = conex;        
    }

    public GameObject getObj() {
        return obj;
    }

    public int getWeight() {
        return weight;
    }
    
    public TiledConnection getTiledConnection() {
        return tiledConnection;
    }

    public void setConex(TiledConnection conex) {
        this.tiledConnection = conex;
    }

}
