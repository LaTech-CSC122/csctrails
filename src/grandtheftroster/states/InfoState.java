package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class InfoState extends GameState {

	
	
	BitmapFont font;
	GlyphFont gfont;
	
	
	public InfoState(GameStateManager gsm) {
		super(gsm, "Game Won");
		font = new BitmapFont();
		gfont = new GlyphFont("res/images/retro font 16.png", 16, sb);
	}

	@Override
	public void handleInput() {
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
			gfont.draw("Information Screen!", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2);
			gfont.draw("Grand Theft Roster", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-20);
			sb.draw(cabFrame, 0, 0);
		sb.end();

	}

}
