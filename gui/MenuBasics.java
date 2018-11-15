/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.chemeng.GUI;
import com.chemeng.gameobjects.ElectricPower;
import com.chemeng.gameobjects.WaterSimpleSystem;
import com.chemeng.gameobjects.corporative.Cash;

/**
 *
 * @author Marcio
 */
public class MenuBasics extends Window {

    private static final String TAG = MenuBasics.class.getName();
    
    private Skin skin = GUI.getInstance().getSkin();;
    
    private Label money, power, water;
    
    private byte FPU = 120; //Frames por update - atualizar apenas passado este número de frames
    private byte count = 0;
    
    public MenuBasics(){
        super("General", GUI.getInstance().getSkin());
        
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("MenuBasics clicado! - touch Down");                
                return true;
                
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("MenuBasics clicado! - touch Up");                                
                super.touchUp(event, x, y, pointer, button); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        this.setWidth(200);
        this.setHeight(150);
        this.setPosition(Gdx.graphics.getWidth()-120, Gdx.graphics.getHeight()-this.getHeight()-650);
        this.setMovable(false);
        this.setResizable(false);
        
        String str;
        
        money = new Label("Cash",skin);        
        str = String.format("%.0f",Cash.getAmount());        
        money.setText("$"+str);
        
        power = new Label("Power",skin);        
        ElectricPower ep = new ElectricPower();
        str = String.format("%.2f", ep.getAmount());
        str = str + " kW";
        power.setText(str);
        
        water = new Label("Water",skin);        
        WaterSimpleSystem wss = new WaterSimpleSystem();
        str = String.format("%.2f", wss.getAmount());
        str = str + " m3/h";
        water.setText(str);
        
        
        add(money).pad(5).row();
        add(power).pad(5).left().row();
        add(water).pad(5).left().row();
        row();        
        this.left().top();
    }

    public void update(){
        count++;
        
        String str;
        
        if(count > FPU){        
            str = String.format("%.0f",Cash.getAmount());        
            money.setText("$"+str);
            count = 0;
        }
        
        ElectricPower ep = new ElectricPower();
        str = String.format("%.2f", ep.getAmount());
        str = str + " kW";
        power.setText(str);        
        
        WaterSimpleSystem wss = new WaterSimpleSystem();
        str = String.format("%.2f", wss.getAmount());
        str = str + " m3/h";
        water.setText(str);
    }
    
    public Actor getActors(){
        return this;
    }

}
