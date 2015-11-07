package grandtheftroster.states;

import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;


public class SplashScreenState extends GameState {

	
	private Texture markoLogo;
	private Texture gameLogo;
	private float time;
	private float[] stageTimes = {2.5f, 2f};
	private int state;
	
	
	public SplashScreenState(GameStateManager gsm) {
		super(gsm, "Game Won");
		time = 0;
		markoLogo = new Texture("res/images/marko-marc logo.png");
		gameLogo = new Texture("res/images/gtr_logo_ingame2x.png");
		state = 0;
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ENTER))
        {
            time = stageTimes[state];
        }
	}

	@Override
	public void update(float dt) {
		handleInput();
		time += dt;
		if(state==0 && stageTimes[0] <= time){
			time = 0;
			state++;
		}
		else if(state==1 && stageTimes[1] <= time){
			time = 0;
			state++;
			gsm.setState(GameStateManager.INFO);
		}
	}

	@Override
	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		
		if(state==0){
			sb.draw(markoLogo, Game.V_WIDTH/2-markoLogo.getWidth()/2, Game.V_HEIGHT/3*2-markoLogo.getHeight()/2-64);
			gfont16.draw("Presents", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/3*2-markoLogo.getHeight()/2-32-64);
		}
		else if(state == 1){
			sb.draw(gameLogo, Game.V_WIDTH/2-gameLogo.getWidth()/2, Game.V_HEIGHT/3*2-gameLogo.getHeight()/2-64);
		}
		int startingY = 64+16*3;
		gfont8.draw("Copyright 2015", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, startingY);
		gfont8.draw("Marko-Marc & the Funky Bunch", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, startingY-16);
		gfont8.draw("All Rights Reserved", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, startingY-32);
		sb.draw(cabFrame, 0, 0);
		
		sb.end();

	}

}
