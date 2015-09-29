package csctrails.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import csctrails.elements.Model;
import csctrails.handlers.GameStateManager;
import csctrails.handlers.MyInput;
import csctrails.main.Game;



public class MainMenuState extends GameState {
	
	BitmapFont font;

	public MainMenuState(GameStateManager gsm){
		super(gsm, "Main Menu");
		font = new BitmapFont();
	}
	
	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){ gsm.pushState(GameStateManager.PLAY); }
		if(MyInput.isPressed(MyInput.BUTTON_1)) { gsm.pushState(GameStateManager.GAME_OVER); }
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render() {
		//Clear screen - gha 15.9.25
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//SpriteBatch to GPU
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
			font.draw(sb, "Main Menu", Game.V_WIDTH/2-35, Game.V_HEIGHT/2);
			font.draw(sb, "Press ENTER to load play state", 10, Game.V_HEIGHT-10);
			font.draw(sb, "Press 1 to load gameover", 10, Game.V_HEIGHT-25);
			for(Model i:models){
				i.getSprite().draw(sb);
			}
		sb.end();
		
		
	}

}
