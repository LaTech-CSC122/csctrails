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
		
			gfont16.draw("*** Congratulations ***", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT*3/4);
			gfont16.draw("You stole the roster!", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT*3/4-32);
			
			int middleX = Game.V_WIDTH/2+16;
			int middleY = Game.V_HEIGHT*2/3-16*6;
			gfont16.draw("Time", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_RIGHT, middleX-20, middleY);
			gfont16.draw(" " + (int) hud.getTime(), GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_LEFT, middleX, middleY);
			gfont16.draw("Grade", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_RIGHT, middleX, middleY-32);
			gfont16.draw(" " + hud.getScore(), GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_LEFT, middleX, middleY-32);
			gfont16.draw("Anky " +(int) hud.getAnky() + ", Class " +(int) hud.getClassScore(), GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, middleY-64);
			gfont16.draw("*Press enter to play*", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/4);
			
			sb.draw(cabFrame, 0, 0);
		sb.end();

	}

}