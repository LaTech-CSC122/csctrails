package grandtheftroster.states;

import com.badlogic.gdx.graphics.Texture;
import grandtheftroster.elements.GlyphFont;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class GameOverState extends GameState {

	BitmapFont font;
	private Texture wastedGraphic;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm, "Game Over");
        wastedGraphic = new Texture("res/images/failure_wasted/failure_wasted_ingame2x.png"); //wasted logo
		font = new BitmapFont();
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) game.shutdown(); //gsm.popState(); Marc changed this can go back if decided.
		if(MyInput.isPressed(MyInput.BUTTON_ENTER)) gsm.setState(GameStateManager.LEVEL_ONE);
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
            sb.draw(wastedGraphic, Game.V_WIDTH/2-wastedGraphic.getWidth()/2, Game.V_HEIGHT/3*2-wastedGraphic.getHeight()/2); //"wasted" graphic
            //gfont16.draw("*** Game Over ***", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT*3/4); text-based "game over"

			int middleX = Game.V_WIDTH/2+16;
			int middleY = Game.V_HEIGHT*2/3-16*4;
			gfont16.draw("Time", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_RIGHT, middleX-20, middleY);
			gfont16.draw(" " + (int) hud.getTime(), GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_LEFT, middleX, middleY);
			gfont16.draw("Grade", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_RIGHT, middleX, middleY-32);
			gfont16.draw(" F", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_LEFT, middleX, middleY-32);
			
			gfont16.draw("Anky " +(int) hud.getAnky() + ", Class " +(int) hud.getClassScore(), GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, middleY-64);
			
			gfont16.draw("*Press enter to play*", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/4);
			gfont16.draw("*Press ESC to exit*", GlyphFont.COLOR_WHITE, GlyphFont.ALIGN_CENTER, Game.V_WIDTH/2, Game.V_HEIGHT/6);
			
			sb.draw(cabFrame, 0, 0);
		sb.end();

	}

}
