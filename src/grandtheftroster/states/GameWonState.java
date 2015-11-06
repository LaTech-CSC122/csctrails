package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;


public class GameWonState extends GameState {

	private static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/audio paths.config");
	}
	
	private Music backgroundMusic;
	
	public GameWonState(GameStateManager gsm) {
		super(gsm, "Game Won");
		
		backgroundMusic = Gdx.audio.newMusic(new FileHandle(cfg.getProperty("THEME@PATHS:AUDIO")));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.5f);
		backgroundMusic.play();
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) game.shutdown();//gsm.popState();
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)){
			gsm.setState(GameStateManager.LEVEL_ONE);
			hud.resetAll();
		}
	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(hudCam.combined);
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
			gfont16.draw("*Press ESC to exit*", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/6);
			
			sb.draw(cabFrame, 0, 0);
		sb.end();

	}
	
	public void dispose(){
		backgroundMusic.stop();
		backgroundMusic.dispose();
	}

}
