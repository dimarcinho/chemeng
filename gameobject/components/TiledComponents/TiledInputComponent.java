/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gameobjects.components.TiledComponents;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Queue;

/**
 *
 * @author Marcio
 */
public abstract class TiledInputComponent extends Actor {

    private static final String TAG = TiledInputComponent.class.getName();
    
    protected Queue<GameEvent> events = new Queue();
    
    protected Queue<EventDispatcher> dispatcher = new Queue();
    
    private Group actors;
    
    public TiledInputComponent(float x, float y, float width, float height){               
        setBounds(x, y, width, height);
        actors = new Group();        
    }
    
    public final void addToStage(Stage stage){        
        stage.addActor(this);
        stage.addActor(actors);        
    }
    
    public final void removeFromStage(){
        actors.remove();
        this.remove();
    }

    public Group getActors() {
        return actors;
    }
    
    public abstract void update(GameObject obj);
    public abstract void handleGameEvent(GameEvent event, Object obj);
    
    public class EventDispatcher {
        
        GameEvent event;
        Object obj;
        
        public EventDispatcher(GameEvent event, Object obj){
            this.event = event;
            this.obj = obj;
        }

        public GameEvent getEvent() {
            return event;
        }

        public Object getObj() {
            return obj;
        }        
        
    }
    
}
