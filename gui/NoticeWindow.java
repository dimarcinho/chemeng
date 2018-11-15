/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.chemeng.GUI;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class NoticeWindow extends Window {
    
    private static final String TAG = NoticeWindow.class.getName();
    
    TiledLevelState level;
    
    private Image icon;
    
    public NoticeWindow(TiledLevelState level) {
        super("Bem vindo!", GUI.getInstance().getSkin());        
        this.level = level;
        
        Skin skin = GUI.getInstance().getSkin();
        
        super.center();
        
        Table table = new Table();
        
        Texture tex = new Texture(Gdx.files.internal("img/menu/executive_menu.png"), true);
        TextureRegion texreg = new TextureRegion(tex);
        icon = new Image(texreg);               
        
        String str = "Bem vindo ao ChemEng! Neste tutorial voce deve produzir agua com 95% de pureza\n"                
                + "Bom trabalho!";
        
        Label label = new Label("", skin);
        label.setText(str);
        label.setWrap(true);        
        
        table.add(icon).expandY().center();
        table.add(label).width(200).top().pad(10);
        table.row().pad(20);
        
        final Button closeButton = new TextButton("Fechar", skin);
        closeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {                        
                        NoticeWindow.this.remove();
                }
        });   
        
        add(table);
        super.row();
        add(closeButton).padTop(20).padBottom(20);
        super.row();
        
    }    
    
    
}
