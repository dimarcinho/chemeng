/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.corporative;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.chemeng.GUI;
import com.chemeng.gameobjects.chemical.Corrente;
import com.chemeng.gamestates.GameOverState;
import com.chemeng.gamestates.LevelWinState;
import com.chemeng.gamestates.TiledLevelState;

/**
 *
 * @author Marcio
 */
public class ContratoManager {
 
    private static final String TAG = ContratoManager.class.getName();
    
    private TiledLevelState level;
    
    private Array<Contrato> atuais, finalizados;
    
    
    public ContratoManager(TiledLevelState level){
        this.level = level;
        atuais = new Array<Contrato>();
        finalizados = new Array<Contrato>();
    }
    
    public void update(){
    
        ArrayIterator<Contrato> it = new ArrayIterator(atuais);
    
        while(it.hasNext()){
            Contrato contrato = it.next();
            contrato.update();
            if(contrato.isExpired()){
                
                finalizados.add(contrato);
                it.remove();
                
                Gdx.app.log(TAG, "Contrato com "+contrato.getEmpresa()+" expirado");
                Gdx.app.log(TAG, "Meta "+contrato.getMeta()+" ");
                Gdx.app.log(TAG, "Produzido: "+contrato.getPercProduzido()+" ");
                Gdx.app.log(TAG, "Finalizado? "+contrato.isFinished()+" ");
                
                if(contrato.isLevelRequired() && !contrato.isFinished()){
                    
                    Gdx.app.log(TAG, "Um dos contratos obrigatórios não foi cumprido e voce foi demitido!");
                    Gdx.app.log(TAG, " -- Game Over -- ");
                    level.gsm.addState(new GameOverState(level.gsm));
                    
                    
                } else {
                    
                    checkLevelVictory();                  
                    
                }                
                
            }
        }
        
    }
    
    public synchronized void receive(Corrente c){
        
        ArrayIterator<Contrato> it = new ArrayIterator(atuais);
    
        while(it.hasNext()){            
            it.next().receive(c);
        }
        
    }
    
    public void addContrato(Contrato ct){
        atuais.add(ct);
    }
    
    public void removeContrato(Contrato ct){
        atuais.removeValue(ct, true);
    }

    public Array<Contrato> getContratos() {
        return atuais;
    }

    public Array<Contrato> getFinalizados() {
        return finalizados;
    }

    private void checkLevelVictory() {
        
        if(!this.hasRequiredContractsActive()){
            Gdx.app.log(TAG, "Nenhum contrato obrigatório está ativo...");
            if(this.AllRequiredContractsFinished()){
                Gdx.app.log(TAG, "Level concluído com sucesso!");
                /*
                Incluir rotina para terminar o level;
                É comum nestes games permitir que o jogador continue operando
                a fábrica mesmo após vitória;
                assim sendo, será necessário implementar alguma variável de estado
                no Level para que o jogo possa continuar sem checar aqui o tempo inteiro                
                */
                level.gsm.addState(new LevelWinState(level.gsm));
            }
        }
        
    }   
    //Verifica se ainda há contratos obrigatórios em andamento
    private boolean hasRequiredContractsActive(){        
        ArrayIterator<Contrato> it = new ArrayIterator(atuais);
        while(it.hasNext()){
            if(it.next().isLevelRequired()){
                //requiredActive = true;
                return true;
            }
        }
        return false;
    }
    
    //Verifica se os contratos obrigatórios concluídos foram finalizados batendo a meta
    private boolean AllRequiredContractsFinished(){
        
        ArrayIterator<Contrato> it = new ArrayIterator(finalizados);
        while(it.hasNext()){
            Contrato c = it.next();            
            if(c.isLevelRequired()){
                if(!c.isFinished()){
                    return false;
                }
            }
        }
        return true;
    }
}
