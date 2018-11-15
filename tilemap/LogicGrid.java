/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.tilemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 *
 * @author Márcio Brito
 */
public class LogicGrid {
    
    private static final String TAG = LogicGrid.class.getName();
       
    private static int TILE_SIZE = 64;
    
    private int width, height;
    private float tileWidth, tileHeight;
    
    private LogicCell[][] cells;
    
    public LogicGrid(TiledMapTileLayer layer){
        this.width = layer.getWidth();
        this.height = layer.getHeight();
        this.tileWidth = layer.getTileWidth();
        this.tileHeight = layer.getTileHeight();
        cells = new LogicCell[width][height];       
 
        Gdx.app.log(TAG, "Layer width: "+width);
        Gdx.app.log(TAG, "Layer height: "+height);
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){                
                setCell(i,j,new LogicCell(null,0,null));
            }
        }
        
    }
    
    public void setCell(int x, int y, LogicCell cell){
        
                if (x < 0 || x >= width) throw new UnsupportedOperationException("Not supported yet.");
		if (y < 0 || y >= height) throw new UnsupportedOperationException("Not supported yet.");                
		cells[x][y] = cell;
    }
    
    public LogicCell getCell(int x, int y){
            if (x < 0 || x >= width) return null;
            if (y < 0 || y >= height) return null;
            return cells[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public LogicCell getCell(float x, float y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
