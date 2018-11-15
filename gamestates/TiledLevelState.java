/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.chemeng.GUI;
import com.chemeng.GameTime;
import com.chemeng.LoadMapObjects;
import com.chemeng.SaveMapObjects;
import com.chemeng.TiledWorld;
import com.chemeng.gameobjects.*;
import com.chemeng.gameobjects.chemical.*;
import com.chemeng.gameobjects.chemical.produtos.*;
import com.chemeng.gameobjects.components.TiledComponents.*;
import com.chemeng.gameobjects.corporative.Cash;
import com.chemeng.gameobjects.corporative.Contrato;
import com.chemeng.gameobjects.corporative.ContratoManager;
import com.chemeng.gui.ContratosWindow;
import com.chemeng.gui.GameOverWindow;
import com.chemeng.gui.LevelWinWindow;
import com.chemeng.gui.MenuBasics;
import com.chemeng.gui.MenuLevel;
import com.chemeng.gui.NoticeWindow;
import com.chemeng.gui.TiledMenuUITest;
import com.chemeng.tilemap.LogicGrid;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Marcio
 */
public class TiledLevelState extends GameState {

    private static final String TAG = TiledLevelState.class.getSimpleName();
    
    private TiledWorld tiledWorld;
    
    private OrthographicCamera camera;
    private SpriteBatch batch;    
    
    private Stage stageGame, stageUI;
    private boolean stageDebug = true;
       
    private InputMultiplexer plex;
        
    private float cameraSpeed = 10f;
    
    //UI
    private TiledMenuUITest menuUItest;
    private MenuBasics menuBasics;
    
    private AnimatedCursor cursor;
    
    private Group equipWin;
    
    //Variaveis globais
    private Cash cash = new Cash(200000);
    private ElectricPower eletricpower = new ElectricPower(0.0015d);
    private WaterSimpleSystem wss = new WaterSimpleSystem(0.0001d);
    
    //Inicialização das variáveis do level:
    private Array<Molecula> moleculas;
    private ContratoManager contratoManager;
    
    //TiledMap
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private LogicGrid grid;
    
    public TiledLevelState(GameStateManager gsm){               
        this.gsm = gsm;
        
        this.initCorrentes();
        
        //GRÁFICOS
        
        //camera
        batch = new SpriteBatch();
        //camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCamera();        
        
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());      
        camera.update();

        //GUI
        stageGame = new Stage(new ScreenViewport());
        stageUI = new Stage(new ScreenViewport(), stageGame.getBatch());
        
        menuUItest = new TiledMenuUITest(this);
        stageUI.addActor(menuUItest);
        
        menuBasics = new MenuBasics();
        stageUI.addActor(menuBasics);
        
        equipWin = new Group();
        stageUI.addActor(equipWin);
        
        plex = new InputMultiplexer();
        plex.addProcessor(stageUI);
        plex.addProcessor(stageGame);
        Gdx.input.setInputProcessor(plex);
       
        //Tiled Map   
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("img/mapas/mapa_teste.tmx");        
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        
        
        //FÍSICA
        grid = new LogicGrid((TiledMapTileLayer) map.getLayers().get("Logic"));                
        tiledWorld = new TiledWorld(map, grid, batch);
        
        
        //CONTRATOS (GAME OVER / GAME WIN)
        contratoManager = new ContratoManager(this);        
        contratoManager.addContrato(new Contrato("XPTO Ltda.",10, new AguaProduto(),5,0.0005f, true));        
        contratoManager.addContrato(new Contrato("Drunk Co.",12, new EtanolAnidroProduto(),10,0.005f, false));
        
        try {
            this.initWorldObjects();
        } catch (Exception ex) {
            Logger.getLogger(TiledLevelState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(GameObject o : tiledWorld.objects) {            
            o.getInput().addToStage(stageGame);         
        }
        
        /*
        *************************************************************
        *************************************************************
        *************************************************************
        
            AVALIAR A NECESSIDADE DE UTILIZAR ESTE SINGLETON
        */        
        GUI.getInstance().setGameStage(stageGame);
        GUI.getInstance().setUIStage(stageUI);
        
        /*
        TODA MUDANÇA DE STATE DEVE ACOMPANHAR ESSA MUDANÇA, COMO NA FUNCAO
        initInputs()
        */
        
        /*
        *************************************************************
        *************************************************************
        *************************************************************
        */
        
        stageGame.getViewport().setCamera(camera);
        
        stageUI.addActor(new MenuLevel(this));
        //stageUI.setDebugAll(true);
        //stageGame.setDebugAll(stageDebug);
        
        createNoticeWindow();
        
        stageUI.addAction(Actions.alpha(0));
        stageGame.addAction(Actions.alpha(0));        
        stageUI.addAction(Actions.fadeIn(0.5f));
        stageGame.addAction(Actions.fadeIn(0.5f));
    }
    
    public void receiveMessage(LEVEL_STATE state){
        switch(state){
            case RUNNING:
                //do nothing
                break;
            case GAME_OVER:
                //
                break;
            case LEVEL_WIN:
                createLevelWinWindow();
                break;
            default:
                Gdx.app.log(TAG, "LEVEL_STATE invalido");
        }
    }

    private void createNoticeWindow(){
        NoticeWindow win = new NoticeWindow(this);
        win.setSize(500, 350);
        win.setVisible(true);
        win.setMovable(true);
        win.setPosition(Gdx.graphics.getWidth()/2 - win.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - win.getHeight()/2);
        
        stageUI.addActor(win); 
    }
    
    public void createLevelWinWindow(){
        LevelWinWindow win = new LevelWinWindow(gsm);
        win.setSize(500, 300);
        win.setVisible(true);
        win.setMovable(true);
        win.setPosition(Gdx.graphics.getWidth()/2 - win.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - win.getHeight()/2);
        
        stageUI.addActor(win); 
    }
    
    public void createGameOverWindow(){
        GameOverWindow win = new GameOverWindow(gsm);
        win.setSize(500, 300);
        win.setVisible(true);
        win.setMovable(true);
        win.setPosition(Gdx.graphics.getWidth()/2 - win.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - win.getHeight()/2);
        
        stageUI.addActor(win); 
    }
    
    public void createContratosWindow(){
        ContratosWindow window = new ContratosWindow(this);
        window.setSize(650, 300);
        window.setVisible(true);
        window.setMovable(true);
        window.setPosition(Gdx.graphics.getWidth()/2 - window.getWidth()/2, 
                        Gdx.graphics.getHeight()/2 - window.getHeight()/2);
        
        stageUI.addActor(window); 
    }
    
    public void initInputs(){
        plex.clear();
        plex.addProcessor(stageUI);
        plex.addProcessor(stageGame);
        Gdx.input.setInputProcessor(plex);
        
        GUI.getInstance().setGameStage(stageGame);
        GUI.getInstance().setUIStage(stageUI);
    }
    
    public void testeParaSaveGame(){
        for(GameObject g : tiledWorld.objects){
            Gdx.app.log(TAG, "Object: "+g.toString()+ "(x,y): ("+g.x+","+g.y+")");
        }
        
        SaveMapObjects s = new SaveMapObjects(this.tiledWorld);
    }
    
    public void testeParaLoadGame(){
        LoadMapObjects lmo = new LoadMapObjects(this);
    }
    
    public final void initWorldObjects() throws Exception{
              
        GameObject obj;
        
        obj = new PumpTiled(384,256);
        obj.init(new PumpTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new PumpTiledPhysicsComponent(),
                new PumpTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        
        obj = new PumpTiled(512,256);
        obj.init(new PumpTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new PumpTiledPhysicsComponent(),
                new PumpTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        
        obj = new PumpTiled(640,256);
        obj.init(new PumpTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new PumpTiledPhysicsComponent(),
                new PumpTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        
        obj = new FlashDrumTiled(768,128);
        obj.init(new FlashDrumTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new FlashDrumTiledPhysicsComponent(),
                new FlashDrumTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        
        //--- Conjunto Tanque + Source para teste --- inicio ---
        obj = new SourceTiled(256,512);
        obj.init(new SourceTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new SourceTiledPhysicsComponent(),
                new SourceTiledGraphicsComponent(obj.x, obj.y));
        
        SourceTiledPhysicsComponent sc = (SourceTiledPhysicsComponent) obj.getPhysics();
        double[] composition = new double[Corrente.getMoleculas().size];
        composition[0] = 0.550d; //Água
        composition[1] = 0.005d; //Etanol
        composition[2] = 0.005d; //Acetona
        composition[3] = 0.440d; //Dodecano
        sc.setComposition(composition);
        
        
        createObject(obj);
        
        obj = new TankClientTiled(384,448); //384
        obj.init(new TankClientTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new TankClientTiledPhysicsComponent(contratoManager),
                new TankClientTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        //--- Conjunto Tanque + Source para teste --- fim ---

        obj = new SourceTiled(256,256);
        obj.init(new SourceTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new SourceTiledPhysicsComponent(), 
                new SourceTiledGraphicsComponent(obj.x, obj.y));

        
        createObject(obj);
        
        obj = new PermutadorTiled(768,448);
        obj.init(new PermutadorTiledInputComponent(obj.x,obj.y,obj.width,obj.height), 
                new PermutadorTiledPhysicsComponent(),
                new PermutadorTiledGraphicsComponent(obj.x, obj.y));
        
        createObject(obj);
        
    }

    public void createObject(GameObject obj) throws Exception{
        tiledWorld.add(obj);
        tiledWorld.createEquipment(obj);
        tiledWorld.addGraphics(obj);
    }
    
    public Cash getCash() {
        return cash;
    }

    public TiledWorld getTiledWorld() {
        return tiledWorld;
    }

    public ContratoManager getContratoManager() {
        return contratoManager;
    }
    

    public final void initCorrentes(){
    /*
    Aqui deverá ser criada a rotina para criar todos as substâncias
    que poderão ser manipuladas durante o level;
    */
    
        moleculas = new Array<Molecula>();
        moleculas.add(new Water());
        moleculas.add(new Etanol());
        moleculas.add(new Acetona());
        moleculas.add(new Dodecane());
        Corrente.init(moleculas);
    }
    
    private void drawUI(){
        
        stageUI.act();
        stageUI.draw();
        
    }

    public Stage getStageGame() {
        return stageGame;
    }
    
    public void input(){
        
        cameraSpeed = 8f;
        cameraSpeed *= camera.zoom;
        
        
        if(Gdx.input.isKeyPressed(Keys.S)){
            camera.translate(0f, -cameraSpeed);            
        } 
        if(Gdx.input.isKeyPressed(Keys.W)){
            camera.translate(0f, +cameraSpeed);            
        } 
        if(Gdx.input.isKeyPressed(Keys.A)){
            camera.translate(-cameraSpeed, 0f);            
        } 
        if(Gdx.input.isKeyPressed(Keys.D)){
            camera.translate(+cameraSpeed, 0f);            
        } 

        if(Gdx.input.isKeyJustPressed(Keys.R)){
            //Reseta o level
            gsm.setState(new TiledLevelState(gsm));
        } 
        if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
            createGameOverWindow();           
        }
        if(Gdx.input.isKeyJustPressed(Keys.NUM_0)){

            activeLineFactory();

        }
        
        if(Gdx.input.isKeyJustPressed(Keys.T)){

            activeFactory(Operut.CHILLER);            
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.O)){

            activeFactory(Operut.ORIFICE_PLATE);            
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.Z)){
            testeParaSaveGame();
        }
        if(Gdx.input.isKeyJustPressed(Keys.X)){
            testeParaLoadGame();
        }
        
        //Cria nova janela de teste na UI        
        if(Gdx.input.isKeyJustPressed(Keys.L)){
            createLevelWinWindow();
        }
        
        //teste para zoom
        if(Gdx.input.isKeyJustPressed(Keys.F2)){
            camera.zoom *= 0.8f;
        }
        if(Gdx.input.isKeyJustPressed(Keys.F3)){
            camera.zoom *= 1.25f;            
        }

//        if(Gdx.input.justTouched()){
//            Gdx.app.log(TAG, "x: "+Gdx.app.getInput().getX());
//            Gdx.app.log(TAG, "y: "+Gdx.app.getInput().getY());
//        }
        

        if(Gdx.input.isKeyJustPressed(Keys.F10)){
                tiledWorld.printConnections();
        }

        //Ativa e desativa o stageDebug - apenas do stageGame
        if(Gdx.input.isKeyJustPressed(Keys.F11)){
            stageDebug = !stageDebug;
            stageGame.setDebugAll(stageDebug);
        }
        //Ativa e desativa o stageDebug do stageGame e stageUI
        if(Gdx.input.isKeyJustPressed(Keys.F12)){
            stageDebug = !stageDebug;
            stageGame.setDebugAll(stageDebug);
            stageUI.setDebugAll(stageDebug);
            
            TiledObjectDestroyer.setDevMode(!TiledObjectDestroyer.isDevMode());
        }
        
        //--- GAME SPEED ---
        if(Gdx.input.isKeyJustPressed(Keys.NUM_1)){
                GameTime.setNormalSpeed();
        }
        if(Gdx.input.isKeyJustPressed(Keys.NUM_2)){
                GameTime.setDoubleSpeed();
        }
        if(Gdx.input.isKeyJustPressed(Keys.NUM_3)){
                GameTime.setQuadrupleSpeed();
        }
        
        //--- GAME SPEED ---
        
        //PAUSE GAME
        if(Gdx.input.isKeyJustPressed(Keys.P)){
                gsm.addState(new PauseState(gsm));
        }

        //SAI DO JOGO
        if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
            Gdx.app.exit();
        }
        
    }
    
    public void activeLineFactory(){
                    
        Array<Actor> actors = stageGame.getActors();
        for(Actor a : actors){
            if(a instanceof LineFactory){
                ((LineFactory) a).removeFactory();
            }
        }
        LineFactory fac = new LineFactory(this);
        stageGame.addActor(fac.getShadow());
        stageGame.addActor(fac);
    }    
    
    public void activeFactory(Operut op){

        if(!op.equals(Operut.LINE)){
            activeOperutFactory(op);
        } else {
            activeLineFactory();
        }        
    }
    
    public void activeOperutFactory(Operut op){
                    
        Array<Actor> actors = stageGame.getActors();
        for(Actor a : actors){
            if(a instanceof TiledObjectFactory){
                ((TiledObjectFactory) a).removeFactory();
            }
        }

        TiledObjectFactory fac = new TiledObjectFactory(op, this);
        stageGame.addActor(fac.getShadow());
        stageGame.addActor(fac);
    }
    
    
    
    
    public void activeObjectDestroyer(){
        
        TiledObjectDestroyer tod = new TiledObjectDestroyer(this);
            
        stageGame.addActor(tod.getIcon());
        stageGame.addActor(tod);
    }
    
    @Override
    public void update() {
        tiledWorld.update();
        contratoManager.update();
        menuBasics.update();
        eletricpower.update();
        wss.update();
        //cursor.update();
    }

    @Override
    public void render() {
        
        //INPUT
        input();
        
        //MECÂNICA DO JOGO
        update();
        
        //RENDERIZAÇÃO
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();

            for(GameObject o : tiledWorld.objects){                
                o.getGraphics().render(o, batch);
            }    
        
        batch.end();
        
        stageGame.act();
        stageGame.draw();
        
        //RENDRIZAÇÃO DO HUD
        drawUI();
        
        batch.begin();
        
            BitmapFont font = GUI.getInstance().getFont();
            font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
                        camera.position.x-500,
                        camera.position.y+354);
            
            
            font.draw(batch, "Zoom: " + camera.zoom,
                        camera.position.x-500,
                        camera.position.y+334);
            
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        stageGame.dispose();
        stageUI.dispose();        
        tiledWorld.dispose();
    }
    
    public enum LEVEL_STATE {
        RUNNING, GAME_OVER, LEVEL_WIN
    }
}
