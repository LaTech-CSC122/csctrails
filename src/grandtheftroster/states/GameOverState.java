package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class GameOverState extends GameState {

	BitmapFont font;
	GlyphFont gfont;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm, "Game Over");
		font = new BitmapFont();
		gfont = new GlyphFont("res/images/retro font.png", 8);
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
			gfont.draw(sb, "Game Over! Better luck next time.", GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2);
			gfont.draw(sb, "Lives lost: 3", GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-15);
			gfont.draw(sb, "Time: " + (int) hud.getTime(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-30);
			gfont.draw(sb, "Grade: F", GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-45);
			gfont.draw(sb, "Anky:  " +(int) hud.getAnky(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-60);
			gfont.draw(sb, "Class:  " +(int) hud.getClassScore(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-23, Game.V_HEIGHT/2-60);
			gfont.draw(sb, hud.getLeader(), GlyphFont.COLOR_WHITE, Game.V_WIDTH/2-100, Game.V_HEIGHT/2-75);
			
			font.draw(sb, "Press ESC to return to main menu", 10, Game.V_HEIGHT - 10);
			font.draw(sb, "Press ENTER to play again", 10, Game.V_HEIGHT - 25);
			
			gfont.draw(sb, "Testing", GlyphFont.COLOR_WHITE, 50, 50);
			
		sb.end();

	}

}
