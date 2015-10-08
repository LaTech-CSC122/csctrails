package csctrails.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import csctrails.handlers.GameStateManager;
import csctrails.handlers.MyInput;
import csctrails.main.Game;

public class GameOverState extends GameState {

	BitmapFont font;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm, "Game Over");
		font = new BitmapFont();
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) gsm.popState();
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)) gsm.setPlayState(GameStateManager.PLAY);
	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(camera.combined);

		sb.begin(); //lives lost is a placeholder in case we give a power up that gives extra lives. 
			font.draw(sb, "Lives lost: 3", Game.V_WIDTH/2-70, Game.V_HEIGHT/2-15);
			font.draw(sb, "Time: " + (int) hud.getTime(), Game.V_WIDTH/2-70, Game.V_HEIGHT/2-30);
			font.draw(sb, "Grade: F", Game.V_WIDTH/2-70, Game.V_HEIGHT/2-45);
			font.draw(sb, "Game Over! Better luck next time.", Game.V_WIDTH/2-70, Game.V_HEIGHT/2);
			font.draw(sb, "Anky:  " +(int) hud.getAnky(), Game.V_WIDTH/2-70, Game.V_HEIGHT/2-60);
			font.draw(sb, "Class:  " +(int) hud.getClassScore(), Game.V_WIDTH/2+7, Game.V_HEIGHT/2-60);
			font.draw(sb, hud.getLeader(), Game.V_WIDTH/2-70, Game.V_HEIGHT/2-75);
			font.draw(sb, "Press ESC to return to main menu", 10, Game.V_HEIGHT - 10);
			font.draw(sb, "Press ENTER to play again", 10, Game.V_HEIGHT - 25);
		sb.end();

	}

}
