/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Marcio
 */
public class SplashState extends GameState {
    
    private Stage stage;
    private Skin skin;
    private TextureAtlas uiAtlas;
    private Image imgIntro;
    private Label label;
    
    private float speed = -6f;
    
    private boolean introFinished = false;
    
    public SplashState(GameStateManager gsm){
        System.out.println("Constructor do "+this.toString()+" criado");
        
        this.gsm = gsm;
        
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        uiAtlas = new TextureAtlas(
                Gdx.files.internal("skin/uiskin.atlas"));
        
        skin = new Skin(
                Gdx.files.internal("skin/uiskin.json"),
                uiAtlas);        
        
        
        Texture tex = new Texture("img/erlenmeyer_intro.png");
        imgIntro = new Image(tex);
        
        imgIntro.setPosition(Gdx.graphics.getWidth()/2-imgIntro.getWidth()/2, 
                            Gdx.graphics.getHeight());
        
        stage.addActor(imgIntro);        
        
        stage.addAction(Actions.alpha(0));
        stage.addAction(Actions.sequence(Actions.fadeIn(5f)));
        
        label = new Label("ChemEng", skin);
        label.setPosition(250, 650);
        label.setFontScale(7);
        
    }
    
    @Override
    public void update() {
        if(imgIntro.getY() > 0){
            imgIntro.moveBy(0, speed);
        } else if(!introFinished){            
               
            label.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
            
            stage.addActor(label);
            
            stage.addAction(
                    Actions.sequence(Actions.delay(3), 
                    Actions.fadeOut(1),
                    Actions.run(new Runnable(){
                        @Override
                        public void run() {
                            gsm.setState(new MainMenuState(gsm));
                        }
            })));
            
            introFinished = true;
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        stage.act();
        stage.draw();
        
        update();        
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        uiAtlas.dispose();
    }
    
}
