package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class GameWonState extends GameState {

	BitmapFont font;
	GlyphFont gfont;
	
	public GameWonState(GameStateManager gsm) {
		super(gsm, "Game Won");
		font = new BitmapFont();
		gfont = new GlyphFont("res/images/retro font.png", 8, sb);
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) gsm.popState();
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)) gsm.setState(GameStateManager.PLAY);
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
			gfont.draw("Lives left: " + hud.getLives(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-15);
			gfont.draw("Time: " + (int) hud.getTime(),GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-30);
			gfont.draw("Grade: " + hud.getScore(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-45);
			gfont.draw("Congratulations, you won!", GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2);
			gfont.draw("Anky:  " +(int) hud.getAnky(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-60);
			gfont.draw("Class:  " +(int) hud.getClassScore(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-23, Game.V_HEIGHT/2-60);
			gfont.draw(hud.getLeader(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-75);
			gfont.draw("Press ESC to return to main menu", GlyphFont.COLOR_RED, 10, Game.V_HEIGHT - 20); //originally -10
			gfont.draw("Press ENTER to play again", GlyphFont.COLOR_RED, 10, Game.V_HEIGHT - 35); //originally -25
		sb.end();

	}

}
