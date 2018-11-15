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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.chemeng.GUI;
import com.chemeng.TiledWorld;
import com.chemeng.gameobjects.ConnectionType;
import com.chemeng.gamestates.TiledLevelState;
import com.chemeng.tilemap.LogicCell;
import com.chemeng.tilemap.LogicGrid;
import com.chemeng.tilemap.TiledConnection;
import com.chemeng.tilemap.TiledConnection.Direction;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marci
 */
public class LineFactory extends Actor {
    
    private static final String TAG = LineFactory.class.getSimpleName();
    
    private TiledLevelState level;
    private Rectangle objectArea;
    
    private Vector2 firstPoint, finalPoint;
    
    private ConnectionType firstConnection, finalConnection;
    private TiledConnection.Direction firstDir, finalDir;
    
    private Image shadow;
    
    private final float TILE_WIDTH = 64, TILE_HEIGHT = 64;
    
    public LineFactory(TiledLevelState level){        
        this.level = level;
        objectArea = new Rectangle();        
        objectArea.width = 64;
        objectArea.height = 64;
        
        createImageShadow();
        
        firstPoint = new Vector2();
        finalPoint = new Vector2();
        
        firstConnection = ConnectionType.NENHUM;
        finalConnection = ConnectionType.NENHUM;
        firstDir = Direction.UP;
        finalDir = Direction.DOWN;
        
        TiledMapTileLayer layer = (TiledMapTileLayer) 
                level.getTiledWorld().getTiledMap().getLayers().get(0);        
        
        float WIDTH_AREA = TILE_WIDTH*layer.getWidth();
        float HEIGHT_AREA = TILE_HEIGHT*layer.getHeight();
        
        setBounds(0,0,WIDTH_AREA,HEIGHT_AREA);
        
        this.addListener(new LineFactoryFirstState());        
        
    }
    
    /*
    
    Rotina para criar linhas em formatos retangulares apenas
    --------------------------------------------------------
    
    1o Clique:
        - Desenhar Shadow
        - Verifico se tem conexao por perto; 
            - Se não, coloco default para cima e seto Entrada;
            - Se sim, coloco o mesmo tipo e salvo se é Entrada/Saída;
        - Pulo para o estado de 2o clique;
    2o Clique
        - Checar clique do 2o botão
            - Se sim, voltar pro 1o Clique0
            - Se não, precisa ficar checando se as coordenas X ou Y estão iguais ao anterior
                - Se não, nada acontece (melhor: desenhar quem tem o maior delta (x ou y));
                - Se sim, Verifico se tem conexao por perto
                    - Se não, coloco default para baixo e Seto saída e crio o objeto
                    ----> aqui posso pegar a diferença vetorial e "alinhar" as direóces das conexoes
                    - Se sim, checo se é do mesmo tipo do 1o clique
                        - Se sim, acuso erro (shadow vermelho e nada ocorre)
                        - Se não, seto a direcao e o tipo (Entrada/Saida) e crio o objeto;
        - Desenhar shadow
    
    */
    
    public void removeFactory(){
        shadow.remove();
        this.remove();
    }
    
    public boolean collides(){
        TiledWorld world = level.getTiledWorld();
        for(GameObject obj : world.objects){
            if(obj.collides(objectArea))
                return !(obj instanceof LineTiled); //permite que as linhas sejam trepassadas
        }
        return false;
    }

    public final void createImageShadow(){
        
        Texture texture = new Texture(Gdx.files.internal("img/white_tile_64x64.png"));        
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
        shadow = new Image(new TextureRegion(texture));
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
        //updateShadow(x,y);
        shadow.setPosition(x, y);
        
        //corrige a cor do shadow a depender da situação
        if(!collides()){
            shadow.setColor(0.f, 1.f, 0.f, 0.5f);            
        } else {
            shadow.setColor(1.f, 0.f, 0.f, 0.5f);
        }
    }
    
    public void updateShadow(float x, float y){        
        float shadowX = Math.min(firstPoint.x, finalPoint.x);
        float shadowY = Math.min(firstPoint.y, finalPoint.y);
        float shadowWidth = Math.abs(firstPoint.x - finalPoint.x);        
        float shadowHeight = Math.abs(firstPoint.y - finalPoint.y);

        shadowWidth = Math.max(shadowWidth+64f, 64f);
        shadowHeight = Math.max(shadowHeight+64f, 64f);
        
        /*
        Trecho para manter as linhas só na horizontal ou vertical:
        ----------------------------------------------------------
        */
        
        //--------------------------------------------------------
        if(isAligned()){
            shadow.setBounds(shadowX, shadowY, shadowWidth, shadowHeight);
            objectArea.setPosition(shadowX, shadowY);
            objectArea.setSize(shadowWidth, shadowHeight);
        }        
        
    }
        
    public boolean isAligned(){
        return (firstPoint.x - finalPoint.x == 0 || firstPoint.y - finalPoint.y == 0);
    }
    
    public void checkAroundConnections(Vector2 v){
        
        LogicGrid grid = level.getTiledWorld().getGrid();
        
        //Array com os vetores direções
        Array<Vector2> possibleDirections = new Array<Vector2>();
        possibleDirections.add(new Vector2(1,0));
        possibleDirections.add(new Vector2(0,1));
        possibleDirections.add(new Vector2(-1,0));
        possibleDirections.add(new Vector2(0,-1));        
        
        //Importante: o loop a seguir irá parar assim que encontrar a primeira conexao!
        for(Vector2 next : possibleDirections){          
            Vector2 sum = v.cpy();
            sum.scl(1/64f);
            sum.add(next);
            LogicCell cell = grid.getCell((int)sum.x, (int)sum.y);
            if(cell.getTiledConnection() != null){
                if(v.equals(firstPoint)){
                    firstConnection = invertType(cell.getTiledConnection().getType());
                    firstDir = cell.getTiledConnection().getDirection();
                } else {
                    finalConnection = invertType(cell.getTiledConnection().getType());
                    finalDir = cell.getTiledConnection().getDirection();
                }
                return;
            }
        }
        
    }

    public void checkFinalConnection(Vector2 v){
        
        Gdx.app.log(TAG, "First: "+firstConnection.toString()+" : "+firstDir.toString());
        Gdx.app.log(TAG, "Final: "+finalConnection.toString()+" : "+finalDir.toString());
        Gdx.app.log(TAG, "Calling checkFinalConnection()...");
        
        checkAroundConnections(v);

        //linha solta, ou seja, nenhuma das pontas está ligada a nenhum equipamento
        if( finalConnection.equals(ConnectionType.NENHUM) &&
            firstConnection.equals(ConnectionType.NENHUM)){
            setLineWithNoConnections();
        //linha com uma ponta em um equipamento e a outra solta
        } else if(finalConnection.equals(ConnectionType.NENHUM)){
            finalConnection = invertType(firstConnection);
        } else if(firstConnection.equals(ConnectionType.NENHUM)){
            firstConnection = invertType(finalConnection);
        } else if(firstConnection.equals(finalConnection)){
            //Gdx.app.log(TAG, "Não é possível conectar os mesmos tipos!");
        }
        
        Gdx.app.log(TAG, "First: "+firstConnection.toString()+" : "+firstDir.toString());
        Gdx.app.log(TAG, "Final: "+finalConnection.toString()+" : "+finalDir.toString());
    }
    
    public void setLineWithNoConnections(){
        firstConnection = ConnectionType.ENTRADA;
        finalConnection = ConnectionType.SAIDA;
        
        //x = 0 ----> linha vertical
        if(firstPoint.x - finalPoint.x == 0){
            if(firstPoint.y > finalPoint.y){
                firstDir = Direction.DOWN;                
            } else {
                firstDir = Direction.UP;                
            }
        } else {
        //linha horizontal
            if(firstPoint.x > finalPoint.x){
                firstDir = Direction.LEFT;                
            } else {
                firstDir = Direction.RIGHT;                
            }
        }
        finalDir = firstDir;
    }
    
    public boolean isEqualType(ConnectionType a, ConnectionType b){
        return a.equals(b);
    }
    
    public ConnectionType invertType(ConnectionType type){
        switch (type) {
            case ENTRADA:
                return ConnectionType.SAIDA;
            case SAIDA:
                return ConnectionType.ENTRADA;
            default:
                return ConnectionType.NENHUM;
        }
    }
    
    public Direction invertDirection(Direction dir){
        switch (dir) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            default:
                return null;
        }
    }
    
    public void setLineFactoryFirstState(){
        this.clearListeners();
        this.addListener(new LineFactoryFirstState());
    }
    
    public void setLineFactoryOneClickState(){
        this.clearListeners();
        this.addListener(new LineFactoryOneClickState());        
    }
    
    public void setFirstPoint(float x, float y){
        firstPoint.x = (float) (64*Math.floor(x/64));
        firstPoint.y = (float) (64*Math.floor(y/64));
    }
    
    public void setFinalPoint(float x, float y){        
        finalPoint.x = (float) (64*Math.floor(x/64));
        finalPoint.y = (float) (64*Math.floor(y/64));
    }
    
    public void correctPoints(){
        
    }
    
    public LinePhysicsComponent createLinePhysicsComponent(){
        
        LinePhysicsComponent pc = null;
        
        Vector2 input, output, origin;
        
        origin = new Vector2(shadow.getX(), shadow.getY());
        
//        Gdx.app.log(TAG, "--- LinePhysicsComponent() at LineFactory.java ---");
//        Gdx.app.log(TAG, "ShadowX: "+shadow.getX());
//        Gdx.app.log(TAG, "ShadowY: "+shadow.getY());
//        
        
        input = firstPoint.cpy();
        output = finalPoint.cpy();
        
        input.sub(origin.cpy());
        input.scl(1/64f);
        output.sub(origin.cpy());
        output.scl(1/64f);
        
//        Gdx.app.log(TAG, "vetor_origem: "+origin.toString());
//        Gdx.app.log(TAG, "vetor0: "+input.toString());
//        Gdx.app.log(TAG, "vetor1: "+output.toString());
        
        if(input.x < 0 || input.y < 0){//first - 0,0; final - x,x
            input.scl(-1.f);
            /*
            vector 0,0
            direcao do first
            conexao do first
            
            vetor x,0 ou 0,y
            direcao do final
            conexao do final            
            */
            
            pc = new LinePhysicsComponent(output, firstConnection, firstDir,
                                            input, finalConnection, finalDir);
            
        } else { //if(input.x > 0 || input.y > 0){//first - x,x; final - 0,0
            /*
            vector 0,0
            direcao do first
            conexao do first
            
            vetor x,0 ou 0,y
            direcao do final
            conexao do final            
            */
            pc = new LinePhysicsComponent(input, firstConnection, firstDir,
                                            output, finalConnection, finalDir);
//        } else {
//            Gdx.app.log(TAG, "Não foi possível criar o PhysicsComponent!!!!");
        }
        
        //Gdx.app.log(TAG, "PhysicsComponent: "+pc.toString());
        
//        Gdx.app.log(TAG, "--------------------------------------------------");
//        input.scl(-1f);
//        Gdx.app.log(TAG, "Angulo: "+firstPoint.angle(finalPoint));
//        Gdx.app.log(TAG, "Angulo In/Out: "+input.angle(output));
//        Gdx.app.log(TAG, "Angulo Out/In: "+output.angle(input));
//        
//        Gdx.app.log(TAG, "first - final: "+firstPoint.sub(finalPoint).toString());
//        Gdx.app.log(TAG, "final - first: "+finalPoint.sub(firstPoint).toString());
//
//        Vector2 u = new Vector2();
//        Vector2 v;
//        u = getVectorDirection(Direction.UP);
//        v = firstPoint.sub(finalPoint);
//        Gdx.app.log(TAG, "<u,v>: "+u.angle(v));

//        Gdx.app.log(TAG, "Physics.v0: "+input.toString());
//        Gdx.app.log(TAG, "Physics.Connection0: "+firstConnection.toString());
//        Gdx.app.log(TAG, "Physics.Direction0: "+firstDir.toString());        
//        Gdx.app.log(TAG, "Physics.vf: "+output.toString());
//        Gdx.app.log(TAG, "Physics.Connectionf: "+finalConnection.toString());
//        Gdx.app.log(TAG, "Physics.Directionf: "+finalDir.toString());
//        
        return pc;
        
//        Gdx.app.log(TAG, "Input vector: "+input.toString());
//        Gdx.app.log(TAG, "Output vector: "+output.toString());
    }
    
        public Vector2 getVectorDirection(Direction dir){
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
        
        return u;
    }
    
    public LineGraphicsComponent createLineGraphicsComponent(){
        LineGraphicsComponent gc = null;
        
        
        
        return gc;
    }
    
    public void createLineObject() throws Exception {
                
        GameObject obj;
        
//        Gdx.app.log(TAG, "--------- createLineObject() ------------");
//        Gdx.app.log(TAG, "firstPoint: "+firstPoint.toString());
//        Gdx.app.log(TAG, "finalPoint: "+finalPoint.toString());
//        Gdx.app.log(TAG, "-- Shadow -- ");
//        Gdx.app.log(TAG, "ShadowX: "+shadow.getX());
//        Gdx.app.log(TAG, "ShadowY: "+shadow.getY());
//        Gdx.app.log(TAG, "ShadowWidth: "+shadow.getWidth());
//        Gdx.app.log(TAG, "ShadowHeight: "+shadow.getHeight());

        
        obj = new LineTiled(shadow.getX(), shadow.getY(),
                            shadow.getWidth(), shadow.getHeight());
        
//        Gdx.app.log(TAG, "Obj.x: "+obj.x);
//        Gdx.app.log(TAG, "Obj.y: "+obj.y);
//        Gdx.app.log(TAG, "Obj.width: "+obj.width);
//        Gdx.app.log(TAG, "Obj.height: "+obj.height);
//        Gdx.app.log(TAG, "Obj.v0: "+firstPoint.toString());
//        Gdx.app.log(TAG, "Obj.Connection0: "+firstConnection.toString());
//        Gdx.app.log(TAG, "Obj.Direction0: "+firstDir.toString());        
//        Gdx.app.log(TAG, "Obj.vf: "+finalPoint.toString());
//        Gdx.app.log(TAG, "Obj.Connectionf: "+finalConnection.toString());
//        Gdx.app.log(TAG, "Obj.Directionf: "+finalDir.toString());
//        Gdx.app.log(TAG, "-----------------------------------------");
        
        obj.init(new LineInputComponent(obj.x, obj.y, obj.width, obj.height),
                createLinePhysicsComponent(),
                new LineGraphicsComponent(obj.x, obj.y, obj.width, obj.height,
                                        firstPoint, firstConnection, firstDir,
                                        finalPoint, finalConnection, finalDir)
        );

        TiledWorld tiledWorld = level.getTiledWorld();
        tiledWorld.add(obj);
        tiledWorld.createEquipment(obj);
        tiledWorld.addGraphics(obj);
        obj.getInput().addToStage(level.getStageGame());
    }
    
    class LineFactoryFirstState extends InputListener{
                
        public LineFactoryFirstState(){
//            Gdx.app.log(TAG, "InputListener: LineFactoryFirstState");
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            update(x, y);
            //updateShadow(x,y);
            super.enter(event, x, y, pointer, fromActor); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y) {
            update(x, y);
            //updateShadow(x,y);
            return super.mouseMoved(event, x, y); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            
            Gdx.app.log(TAG, "Clique no FirstState listener");
            
            update(x, y);
            //updateShadow(x,y);
            
            if(button == 0){

                //Verifica se há colisões
                if(!collides()){
                    //Cria o objeto
                    Gdx.app.log(TAG, "Primeiro ponto da linha criado!");
                    setFirstPoint(x,y);
                    checkAroundConnections(firstPoint);
                    setLineFactoryOneClickState();
                } else {
                    Gdx.app.log(TAG, "Impossível criar linha! -- já um objeto no local");
                }

            } else if(button == 1){
                removeFactory();
                Gdx.app.log(TAG, "LineFactory desativada");
            }
            return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    class LineFactoryOneClickState extends InputListener{
                
        public LineFactoryOneClickState(){
//            Gdx.app.log(TAG, "InputListener: LineFactoryOneCickState");
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            update(x, y);
            updateShadow(x,y);
            super.enter(event, x, y, pointer, fromActor); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean mouseMoved(InputEvent event, float x, float y) {
            update(x, y);
            setFinalPoint(x,y);
            updateShadow(x,y);
            return super.mouseMoved(event, x, y); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            
//            Gdx.app.log(TAG, "Clique no OneClickState listener");
            
            update(x, y);
            updateShadow(x,y);
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
                    setFinalPoint(x,y);
                    checkFinalConnection(finalPoint);
                    
                    if(firstConnection.equals(finalConnection)){
                        Gdx.app.log(TAG, "Não é possível conectar os mesmos tipos!");
                        setLineFactoryOneClickState();
                        /*
                        ------------------
                        SOM DE ERRO !!!!!!
                        ------------------
                        */
                    } else {
                    
                        try {
                            createLineObject();
//                            Gdx.app.log(TAG, "Linha criada!");
//                            Gdx.app.log(TAG, "Primeiro ponto: "+firstPoint.toString());
//                            Gdx.app.log(TAG, "Segundo ponto: "+finalPoint.toString());
                            removeFactory();
                        } catch (Exception ex) {
                            Logger.getLogger(LineFactory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    
                } else {
                    Gdx.app.log(TAG, "Impossível criar linha! -- já há um objeto no local");
                }

            } else if(button == 1){
                setLineFactoryFirstState();
                objectArea.setSize(64f, 64f);
            } 
            return super.touchDown(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
