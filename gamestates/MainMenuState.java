/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Marcio
 */
public class MainMenuState extends GameState {
    
    private Stage stage;
    
    private Skin skin;
    private TextureAtlas uiAtlas;
    
    private Image imgBackground, imgCampanha, imgTutorial, imgCenario;
    private Image imgSandbox, imgConfig, imgCreditos;
        
    
    public MainMenuState(GameStateManager gsm){
        this.gsm = gsm;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        uiAtlas = new TextureAtlas(
                Gdx.files.internal("skin/uiskin.atlas"));
        
        skin = new Skin(
                Gdx.files.internal("skin/uiskin.json"),
                uiAtlas);
        
        Texture background = new Texture("img/menu/chemical_plant_menu_background.jpg");
        imgBackground = new Image(background);
        imgBackground.addAction(Actions.alpha(0.15f));        
        
        imgCampanha = new Image(new Texture("img/menu/executive_menu.png"));
        imgCampanha.setPosition(750, 500);
        imgCampanha.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println("Imagem da campanha clicada...");                
                teste();
                return true;
            }
        });
                
        imgTutorial = new Image(new Texture("img/menu/estudante_menu.png"));
        imgTutorial.setPosition(710, 250);
        
        imgCenario = new Image(new Texture("img/menu/engineer_menu.png"));
        imgCenario.setPosition(480, 230, 10);
        
        imgSandbox = new Image(new Texture("img/menu/crazy_scientist_menu.png"));
        imgSandbox.setPosition(50, 520);
        
        imgConfig = new Image(new Texture("img/menu/quimica_menu.png"));
        imgConfig.setPosition(50, 50);
        
        imgCreditos = new Image(new Texture("img/menu/planta_menu.png"));
        imgCreditos.setPosition(380, 310);
        imgCreditos.addListener(new TextTooltip("Creditos",skin));
        

        stage.addActor(imgBackground);
        stage.addActor(imgCampanha);
        stage.addActor(imgTutorial);
        stage.addActor(imgCenario);
        stage.addActor(imgSandbox);
        stage.addActor(imgConfig);
        stage.addActor(imgCreditos);        
        
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.fadeIn(2));
        
        stage.addListener(new InputListener(){
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(Keys.ESCAPE == keycode)
                    Gdx.app.exit();
                return super.keyDown(event, keycode); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        
    }
    
    private void teste(){
        System.out.println("Método teste() chamado;");
        System.out.println("Atualizando GSM para o novo LevelState");
        gsm.setState(new TiledLevelState(gsm));
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0.10f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        uiAtlas.dispose();
    }
    
}
