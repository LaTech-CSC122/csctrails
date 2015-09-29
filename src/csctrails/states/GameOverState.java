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
	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
			font.draw(sb, "Game Over Screen", Game.V_WIDTH/2-70, Game.V_HEIGHT/2);
			font.draw(sb, "Press ESC to return to main menu", 10, Game.V_HEIGHT - 10);
		sb.end();

	}

}
