package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.elements.Model;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;




public class InfoState extends GameState {
	
	public InfoState(GameStateManager gsm){
		super(gsm, "Main Menu");
	}
	
	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){ gsm.pushState(GameStateManager.LEVEL_ONE); }
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
		sb.setProjectionMatrix(hudCam.combined);
		sb.begin();
			gfont16.draw("Press ENTER to continue",  GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-180); //originally -10
			gfont16.draw("Press ESC to quit",  GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-200); //originally -25
			gfont16.draw("Controls:", GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2+20);
			gfont16.draw("UP-Jump/Fly", GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-10);
			gfont16.draw("LEFT/RIGHT-Move Left/Right", GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-30);
			gfont16.draw("DOWN-Move Down", GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2-50);
			gfont8.draw("These are the daily stories of a boy trying to save",
						GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2+160);
			gfont8.draw("the attendance grades of his fellow classmates.",
					GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2+150);
			gfont8.draw("He must travel through the harsh terrain of UNVH 111",
					GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2+140);
			gfont8.draw("to retrieve the class roster from the infamous ANKY.",
					GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/2+130);
			for(Model i:models){
				i.draw(sb);
			}
			sb.draw(cabFrame, 0, 0);
		sb.end();
	}

}
