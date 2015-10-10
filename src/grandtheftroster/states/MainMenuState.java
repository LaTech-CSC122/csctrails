package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.elements.Model;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;




public class MainMenuState extends GameState {
	
	BitmapFont font;
	GlyphFont gfont;
	public MainMenuState(GameStateManager gsm){
		super(gsm, "Main Menu");
		font = new BitmapFont();
		gfont = new GlyphFont("res/images/retro font.png", 8, sb); 
	}
	
	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){ gsm.pushState(GameStateManager.PLAY); }
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) {
			game.shutdown();
		}
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
			//font.draw(sb, "Main Menu", Game.V_WIDTH/2-35, Game.V_HEIGHT/2);
			//font.draw(sb, "Press ENTER to continue.", 10, Game.V_HEIGHT-10);
			//font.draw(sb, "Press ESC to quit.", 10, Game.V_HEIGHT-25);
			gfont.draw("Main Menu", GlyphFont.COLOR_RED, Game.V_WIDTH/2-35, Game.V_HEIGHT/2);
			gfont.draw("Press ENTER to continue", GlyphFont.COLOR_RED, 10, Game.V_HEIGHT-10);
			gfont.draw("Press ESC to quit", GlyphFont.COLOR_RED, 10, Game.V_HEIGHT-25);
			for(Model i:models){
				i.draw(sb);
			}
		sb.end();
		
		
	}

}
