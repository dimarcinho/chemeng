/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.testes;

import com.chemeng.testes.TestObjectPhysics.State;

/**
 *
 * @author Marcio
 */
public class TestObject implements GameObject {
    
    
    public float x, y, width, height;
    
    
    private TestObjectActor actor; //input
    private TestObjectPhysics physics; //update
    private TestObjectGraphics graphics; //render
    
    public TestObject(TestObjectActor actor, TestObjectPhysics physics, TestObjectGraphics graphics){
        this.actor = actor;
        this.physics = physics;
        this.graphics = graphics;
        
        physics.state = State.NORMAL;
    }
    
    public TestObject(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        actor = new TestObjectActor(this);
        physics = new TestObjectPhysics(this);
        graphics = new TestObjectGraphics(this);        
        
        physics.state = State.NORMAL;
    }

    public TestObjectActor getActor() {
        return actor;
    }

    public TestObjectPhysics getPhysics() {
        return physics;
    }

    public TestObjectGraphics getGraphics() {
        return graphics;
    }
    

    @Override
    public void input() {
        
    }

    @Override
    public void update() {
        actor.update(this);
        physics.update(this);
        graphics.update(this);
    }

    @Override
    public void render() {
        
    }

    @Override
    public void dispose() {
        
    }
    
}
