/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author marci
 */
public class GameTime {
 
    private static final String TAG = GameTime.class.getName();
    
    /*
    Definir aqui nesta classe o "passo" (ritmo do jogo)
    Comparar tempo de jogo versus tempo real
    Por ex: 1 frame/s = 1min de vida real
    */
    
    private static long MPF = 3; //Minutos_por_Frame a serem simulados
    private static byte GAME_SPEED = 1; //Fator para poder avançar o jogo mais rápido;
    
    private long timing;
    
    //O constructor possui parâmetro para usar em save/load das fases
    public GameTime(long timing){
        this.timing = timing;
    }
    
    public void update(){    
        timing++;
    }
    
    public long getTiming(){
        return timing; //frames que passaram
    }
    
    public static long getMPF(){
        return MPF*GAME_SPEED;
    }
    
    public static void setNormalSpeed(){
        GAME_SPEED = 1;
        Gdx.app.log(TAG, "Speed set to: "+GAME_SPEED);
    }
    
    public static void setDoubleSpeed(){
        GAME_SPEED = 2;
        Gdx.app.log(TAG, "Speed set to: "+GAME_SPEED);
    } 
    public static void setQuadrupleSpeed(){
        GAME_SPEED = 4;
        Gdx.app.log(TAG, "Speed set to: "+GAME_SPEED);
    }
    
}
