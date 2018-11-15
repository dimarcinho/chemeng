/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.chemeng.gameobjects.components.TiledComponents.GameObject;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gameobjects.components.TiledComponents.TiledPhysicsComponent;
import com.chemeng.tilemap.Conexao;
import com.chemeng.tilemap.LogicCell;
import com.chemeng.tilemap.LogicGrid;
import com.chemeng.tilemap.TiledConnection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcio
 */
public class TiledWorld {
    
    private static final String TAG = TiledWorld.class.getSimpleName();
    
    public static final int TILE_SIZE = 64;
 
    public Array<GameObject> objects;
    
    private TiledMap tiledMap;
    private LogicGrid grid;
    private SpriteBatch batch;
    private int counterConnectionsTest = 0;
    
    
    public TiledWorld(TiledMap tiledMap, LogicGrid grid, SpriteBatch batch){
        objects = new Array();        
        this.tiledMap = tiledMap;
        this.grid = grid;
        this.batch = batch;        
    }

    public void add(GameObject obj){
        objects.add(obj);
    }
    
    public void remove(GameObject obj){        
        clearConnections(obj);
        clearObjectGrid(obj);
        obj.getInput().removeFromStage();
        objects.removeValue(obj, true);
    }
    
    public void removeAll(){
        
        while(objects.size > 0){
            int lastIndex = objects.size - 1;
            GameObject obj = objects.get(lastIndex);
            remove(obj);
        }
    }
    
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public LogicGrid getGrid() {
        return grid;
    }
    
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }
    
    public void input() {
        for(GameObject go : objects){
            go.input();
        }
    }

    public void update() {
        for(GameObject go : objects){
            go.update(this, batch);
        }
    }

    public void render() {
        for(GameObject go : objects){
            go.render();
        }
    }

    public void dispose() {
        for(GameObject go : objects){
            go.dispose();
        }
    }    
    
    public void printConnections(){
        for(GameObject go : objects){
            Gdx.app.log(TAG, "Object: "+go.toString());
            TiledPhysicsComponent physics = go.getPhysics();
            Gdx.app.log(TAG, "Physics: "+physics.toString());
            Array<Conexao> connections = physics.getConexoes();
            Gdx.app.log(TAG, "Conexao "+physics.getConexoes().toString());
            for(Conexao c : connections){
                Gdx.app.log(TAG, "Corrente de Entrada:"+c.getEntrada().hashCode());
                Gdx.app.log(TAG, "Corrente de Saida:"+c.getSaida().hashCode());
            }
        }
    }
    
    public void createEquipment(GameObject obj) throws Exception {
        
        TiledPhysicsComponent physics = obj.getPhysics();        
        Vector2 origin = obj.getTiledOrigin();        
        int tileWidthSize = (int) (obj.width/TILE_SIZE);
        int tileHeightSize = (int) (obj.height/TILE_SIZE);
        
        //Primeiro preenche toda a grade
        for(int i = 0; i < tileWidthSize; i++){
            for(int j = 0; j < tileHeightSize; j++){
                Vector2 toSum = new Vector2(i,j);
                Vector2 sum = origin.cpy();
                sum.add(toSum);
                
                grid.setCell((int)sum.x, (int)sum.y, new LogicCell(obj,0,null));
                        
//                Gdx.app.log(TAG, "Setting LogicCell from filling the grid");        
//                Gdx.app.log(TAG, "x: "+sum.x);
//                Gdx.app.log(TAG, "y: "+sum.y);
            }
        }
        
        //Agora crio os pontos de conexão
        ObjectMap<Vector2, TiledConnection> map = physics.getConnections();
        ObjectMap.Entries<Vector2, TiledConnection> it = map.entries().iterator();
        while(it.hasNext()){
            ObjectMap.Entry<Vector2, TiledConnection> pair = it.next();
            Vector2 toSum = pair.key;
            Vector2 sum = origin.cpy();
            sum.add(toSum);
            grid.setCell((int)sum.x, (int)sum.y, new LogicCell(obj,0,pair.value));
            
//            Gdx.app.log(TAG, "Vector origin: "+origin.toString());
//            Gdx.app.log(TAG, "Vector toSum: "+toSum.toString());
//            Gdx.app.log(TAG, "Vector sum: "+sum.toString());
//            
//            Gdx.app.log(TAG, "Creating connection in LogicCell...");
//            Gdx.app.log(TAG, "x: "+sum.x);
//            Gdx.app.log(TAG, "y: "+sum.y);
        }
        
        /*
        
        TO DO
        
        Os dois loops acima podem ser refeitos em um (no 2o), desde que o PhysicsComponent
        possua o ObjectMap com todos os tiles que serão ocupados. Aqueles que não
        possuírem conexão terão `null` como TiledConnection
        
        */
        
        //agora eh checar as conexoes
        checkConnections(obj);
    }
    
    public void addGraphics(GameObject obj){
        tiledMap.getLayers().get("Objects").getObjects().add(
                obj.getGraphics().getTextureMapObject()
        );
    }
    
    public void removeGraphics(GameObject obj){
        tiledMap.getLayers().get("Objects").getObjects().remove(
                obj.getGraphics().getTextureMapObject()
        );
    }    
    
    public void checkConnections(GameObject obj){
        
        /*
        
        Regras para serem checadas:
        
        1) Objetos têm que ser diferentes
        2) Direções das conexões precisam ser iguais
        3) Checar os tiles na direção à frente e atrás
        4) Checar se o nextTile já não está conectado ---> acho que é impossível
        5) Verificar se é Entrada/Saída ou Saída/Entrada
        
        */
        
        int tileWidthSize = (int) (obj.width/TILE_SIZE);
        int tileHeightSize = (int) (obj.height/TILE_SIZE);
        
        //busca todas as celulas q o objeto ocupa
        for(int i = 0; i < tileWidthSize; i++){
            for(int j = 0; j < tileHeightSize; j++){
                
//                Gdx.app.log(TAG, "Loop (i,j): "+i+","+j);
                
                int x = (int) (i+obj.getTiledOrigin().x);
                int y = (int) (j+obj.getTiledOrigin().y);
                
                LogicCell cell = (LogicCell) grid.getCell(x,y);
                                
                //verifica se o tile contem algum ponto de conexao
                if(cell.getTiledConnection() != null){                    
                    
                    //Gdx.app.log(TAG, "cell.TiledConnection: "+cell.getTiledConnection().toString());
                    
                    //cria um vetor, baseado na direcao, para checar o proximo tile
                    TiledConnection.Direction dir = cell.getTiledConnection().getDirection();
                    Vector2 next = this.getVectorDirection(dir);
                    
                    //Gdx.app.log(TAG, "Vector next: "+(int)next.x+","+(int)(next.y));
                    //Gdx.app.log(TAG, "nextCell: "+(int)(x+next.x)+","+(int)(y+next.y));
                    
                    //pega a proxima celula
                    LogicCell nextCell = (LogicCell) grid.getCell(x+(int)next.x, y+(int)next.y);
                    
                    //inverte a direcao do vetor soma, para testar o tile 'anterior'
                    Vector2 before = next.cpy();
                    before.scl(-1);
                    
                    //Gdx.app.log(TAG, "Vector before: "+(int)before.x+","+(int)(before.y));
                    //Gdx.app.log(TAG, "beforeCell: "+(int)(x+before.x)+","+(int)(y+before.y));
                    LogicCell beforeCell = (LogicCell) grid.getCell(x+(int)before.x, y+(int)before.y);
                    
                    checkNextCells(obj, cell, nextCell);
                    checkNextCells(obj, cell, beforeCell);
                }
                
            }
        }
    }
    
    public void checkNextCells(GameObject o, LogicCell a, LogicCell b){
        
        GameObject obj = o;
        LogicCell cell = a;
        LogicCell nextCell = b;        
        
        if(nextCell.getTiledConnection() != null){
            //checa se elas possuem as mesmas direcoes 
            //ps - condicao necessaria para conectar os equipamentos
            if(cell.getTiledConnection().getDirection().equals(
                nextCell.getTiledConnection().getDirection()
                )){

                //Crio a conexao;
                Conexao c;
                Corrente saida = null, entrada = null;
                /*
                Aqui preciso checar se estou fazendo uma conexao do tipo ENTRADA/SAIDA
                ou SAIDA/ENTRADA                        
                */
                /*
                
                *****************************************
                
                Necessario consertar esta parte; o objeto deve apenas possuir conexoes do tipo Saida>Entrada
                Se for invertido, eh necessario entao adicionar a conexao ao OUTRO objeto
                
                usando o boolean inverted temporariamente como teste - 02/06/2018
                
                está funcionando, acho que vou deixar assim mesmo - 10/06/2018
                *****************************************
                
                */
                boolean inverted = false;
                if(cell.getTiledConnection().getType().equals(ConnectionType.SAIDA)){
                    saida = cell.getTiledConnection().getCorrente();
                    entrada = nextCell.getTiledConnection().getCorrente();
                    inverted = true;
                } else if (cell.getTiledConnection().getType().equals(ConnectionType.ENTRADA)){
                    entrada = cell.getTiledConnection().getCorrente();
                    saida = nextCell.getTiledConnection().getCorrente();
                }                        
                c = new Conexao(saida, entrada);

                if(isSameObject(cell.getObj(), nextCell.getObj())){
                    Gdx.app.log(TAG, "Não é possível conectar um objeto a ele mesmo!");
                } else {                    
                    
                    if(inverted){
                        //c = new Conexao(obj, saida, entrada);
                        //c = new Conexao(nextCell.getObj(), saida, entrada);
                        obj.getPhysics().getConexoes().add(c);                        
                    } else {                        
                        //c = new Conexao(obj, saida, entrada);
                        //c = new Conexao(nextCell.getObj(), saida, entrada);
                        nextCell.getObj().getPhysics().getConexoes().add(c);
                    }
                    
                    Gdx.app.log(TAG, "Objetos conectados com sucesso!");
                    Gdx.app.log(TAG, " >>>> Total de conexões: "+(++counterConnectionsTest)+" <<<<" );
                }


            } else {

                //tocar algum som de erro, ou aviso, ou algo do genero; ou nada mesmo

            }
        }
    }
       
    public boolean isSameObject(GameObject a, GameObject b){
        return a.equals(b);
    }
    
    public Vector2 getVectorDirection(TiledConnection.Direction dir){
        Vector2 next = null;
        switch(dir){
            case UP: 
                next = new Vector2(0,1);
                break;
            case LEFT:
                next = new Vector2(-1,0);
                break;
            case DOWN:
                next = new Vector2(0,-1);
                break;
            case RIGHT:
                next = new Vector2(1,0);
                break;
            default:
                    {
                        try {
                            throw new Exception("Erro tentando criar conexao do Direction do TiledConnection");
                        } catch (Exception ex) {
                            Logger.getLogger(TiledWorld.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
        }
        return next;
    }

    private void clearConnections(GameObject obj) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Gdx.app.log(TAG, "Não implementado: clearConnections()");
        obj.getPhysics().clearConnections();
    }

    private void clearObjectGrid(GameObject obj) {
        //Gdx.app.log(TAG, "Não implementado: clearObjectGrid()");
        
        Vector2 origin = obj.getTiledOrigin();        
        int tileWidthSize = (int) (obj.width/TILE_SIZE);
        int tileHeightSize = (int) (obj.height/TILE_SIZE);
        
        //Limpa a grade
        for(int i = 0; i < tileWidthSize; i++){
            for(int j = 0; j < tileHeightSize; j++){
                Vector2 toSum = new Vector2(i,j);
                Vector2 sum = origin.cpy();
                sum.add(toSum);
                
                grid.setCell((int)sum.x, (int)sum.y, new LogicCell(null,0,null));
                /*
                Gdx.app.log(TAG, "Setting LogicCell from filling the grid");        
                Gdx.app.log(TAG, "x: "+sum.x);
                Gdx.app.log(TAG, "y: "+sum.y);
                */
            }
        }
    }

}
