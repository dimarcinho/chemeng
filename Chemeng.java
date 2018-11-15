package com.chemeng;

import com.badlogic.gdx.ApplicationAdapter;
import com.chemeng.gamestates.*;

public class Chemeng extends ApplicationAdapter {
    
        private static final String TAG = Chemeng.class.getName();
        
        private GameStateManager gsm;

	@Override
	public void create () {
             
            gsm = new GameStateManager();
            //gsm.setState(new SplashState(gsm));            
            //gsm.setState(new MainMenuState(gsm));            
            
            gsm.setState(new TiledLevelState(gsm));
	}

	@Override
	public void render () {
            
            gsm.update();
            gsm.render();
             
	}
	
	@Override
	public void dispose () {
            gsm.dispose();
	}
        
}
