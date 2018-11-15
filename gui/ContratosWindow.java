/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.chemeng.GUI;
import com.chemeng.gameobjects.corporative.Contrato;
import com.chemeng.gameobjects.corporative.ContratoManager;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author marci
 */
public class ContratosWindow extends Window {
    
    private static final String TAG = ContratosWindow.class.getName();
    
    private static final WindowStyle windowStyle;
    private static final ImageButtonStyle closeButtonStyle;
    private final TiledLevelState level;
    
    static {
        Texture tex = new Texture(Gdx.files.internal("img/window_background_3.png"), true);        
        TextureRegion texture = new TextureRegion(tex);        
        //windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, new TextureRegionDrawable(texture));        
        
        windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, null);
        
        tex = new Texture(Gdx.files.internal("img/close_button_1.png"), true);
        texture = new TextureRegion(tex);
        closeButtonStyle = new ImageButtonStyle();
        closeButtonStyle.imageUp = new TextureRegionDrawable(texture);
        tex = new Texture(Gdx.files.internal("img/close_button_1_over.png"), true);
        texture = new TextureRegion(tex);
        closeButtonStyle.imageOver = new TextureRegionDrawable(texture);
        
    }
    
    public ContratosWindow(TiledLevelState level) {        
        //super("", windowStyle);        
        super("", GUI.getInstance().getSkin());
        this.level = level;        
        
        final Button closeButton = new ImageButton(closeButtonStyle);
        closeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {                        
                        ContratosWindow.this.remove();
                }
        });        
        getTitleTable().add(closeButton).size(32, 32).padRight(10).padTop(-20);
        
        setClip(false);
        setTransform(true);
        this.setMovable(true);
        
        addListener(new InputListener(){            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log(TAG, "ContratosWindow clicado!");                
                return true;
                
            }
            
        });
        
        setMovable(true);
        
        this.left().top();
        this.pad(40);        
        
        Skin skin = GUI.getInstance().getSkin();        
        Label contrato, prazo, produto, meta, produzido, price;        
        Table table = new Table();        
        
        Color redColor = new Color(Color.CYAN);
        Color blackColor = new Color(Color.WHITE);
        LabelStyle redStyle = new LabelStyle(GUI.getInstance().getFont(), redColor);
        LabelStyle blackStyle = new LabelStyle(GUI.getInstance().getFont(), blackColor);
        
        //cabeçalho
        contrato = new Label("Empresa", skin);
        prazo = new Label("Prazo", skin);
        produto = new Label("Produto", skin);
        meta = new Label("Meta", skin);
        produzido = new Label("Produzido", skin);
        price = new Label("Preço", skin);
        
        contrato.setStyle(redStyle);
        prazo.setStyle(redStyle);
        produto.setStyle(redStyle);
        meta.setStyle(redStyle);
        produzido.setStyle(redStyle);
        price.setStyle(redStyle);
        table.add(contrato).width(90);
        table.add(prazo).width(70);
        table.add(produto).width(150);
        table.add(meta).width(70);
        table.add(produzido).width(150);
        table.add(price).width(50);
        table.row();
        //dados
        ContratoManager cm = level.getContratoManager();
        Array<Contrato> contratos = cm.getContratos();
        
        for(Contrato c : contratos){
            contrato = new Label(c.getEmpresa(), skin);
            prazo = new Label(c.getPrazo()*3/86400+"", skin);            
            produto = new Label(c.getProduto().getName(), skin);
            meta = new Label((int)c.getMeta()+"", skin);            
            produzido = new Label((int)c.getProduzido()+"", skin);
            price = new Label(String.format("%.2f", 1000*c.getPreco()), skin);
            contrato.setStyle(blackStyle);
            prazo.setStyle(blackStyle);
            produto.setStyle(blackStyle);
            meta.setStyle(blackStyle);
            produzido.setStyle(blackStyle);
            price.setStyle(blackStyle);
            table.add(contrato).width(90);
            table.add(prazo).width(70);
            table.add(produto).width(150);
            table.add(meta).width(70);
            table.add(produzido).width(150);
            table.add(price).width(50);
            table.row();
        }
        
        //table.row();
        LabelStyle estilo = new LabelStyle(GUI.getInstance().getFont(), Color.YELLOW);
        Label finalizados = new Label("\nFinalizados", skin);
        finalizados.setStyle(estilo);
        table.add(finalizados).align(Align.left);
        table.row();
        
        contratos = cm.getFinalizados();
        for(Contrato c : contratos){
            contrato = new Label(c.getEmpresa(), skin);
            prazo = new Label("---", skin);            
            produto = new Label(c.getProduto().getName(), skin);
            meta = new Label("---", skin);
            produzido = new Label((int)c.getProduzido()+"", skin);
            price = new Label("---", skin);
            contrato.setStyle(blackStyle);
            prazo.setStyle(blackStyle);
            produto.setStyle(blackStyle);
            meta.setStyle(blackStyle);
            produzido.setStyle(blackStyle);
            price.setStyle(blackStyle);
            table.add(contrato).width(90);
            table.add(prazo).width(70);
            table.add(produto).width(150);
            table.add(meta).width(70);
            table.add(produzido).width(150);
            table.add(price).width(50);
            table.row();
        }
        add(table);
        
        this.center().top();
    }
    
    
    
}
