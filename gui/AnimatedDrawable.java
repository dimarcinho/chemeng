/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chemeng.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

/**
 *
 * @author marci
 */
public class AnimatedDrawable extends BaseDrawable {

    private Animation animation;
    private TextureRegion keyFrame;
    private float stateTime = 0;

    public AnimatedDrawable(Animation animation){

        this.animation = animation;
        TextureRegion key = (TextureRegion) animation.getKeyFrame(0);

        this.setLeftWidth(key.getRegionWidth()/2);
        this.setRightWidth(key.getRegionWidth()/2);
        this.setTopHeight(key.getRegionHeight()/2);
        this.setBottomHeight(key.getRegionHeight()/2);
        this.setMinWidth(key.getRegionWidth());
        this.setMinHeight(key.getRegionHeight());

    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height){

        stateTime += Gdx.graphics.getDeltaTime();
        keyFrame = (TextureRegion) animation.getKeyFrame(stateTime, true);

        batch.draw(keyFrame, x,y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
    }
}
