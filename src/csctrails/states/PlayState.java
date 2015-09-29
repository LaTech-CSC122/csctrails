package csctrails.states;


import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.elements.Boss;
import csctrails.elements.Model;
import csctrails.elements.Player;
import csctrails.handlers.GameStateManager;
import csctrails.handlers.MyInput;
import csctrails.handlers.PlayContactListener;
import csctrails.main.Game;
import csctrails.main.Paths;

/**
 * PlayState is the game-state where most of the game play takes
 * place. It is ran using the event listeners declared by
 * its super class, GameState to run the game-state. See
 * in-line comment headers for more details
 *
 * Change Log:
 * 15.9.21gha: First Edition
 * 
 */
public class PlayState extends GameState {
	//Box2D Fields
	private World world;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private PlayContactListener cl;
	
	//Model Fields
	Player user;
	
	//Fonts
	BitmapFont font;

	public PlayState(GameStateManager gsm) {
		super(gsm, "Play");
		
		//Box2D World
		Game.logger.log("GS: Creating Box2D world and cameras for " + title);
		world = new World(new Vector2(0f, -15f), false);
		cl = new PlayContactListener();
		b2dDebugRenderer = new Box2DDebugRenderer(); // Used to render Box2D world when developing - gha 15.9.20
		world.setContactListener(cl);
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
		//Game State Layout
		Game.logger.log("GS: Creating Models for " + title);
		user = new Player(world, "player", Paths.SPRITE_MAN_STANDING,  100, 200);
		models.add(user); // Model must be added to modelList or it will not be rendered - gha 15.9.25
		Boss B1 = new Boss (world, 50, 50);
		models.add(B1);
		//Fonts
		font = new BitmapFont();
		
	}

				
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) gsm.popState();
	}
	
	public void update(float dt) {
		//dt is the time since update was last ran - gha 15.9.25
		world.step(dt, 6, 2);
		handleInput();
	}
	
	public void render() {
		// Clear previous screen
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

		//SpriteBatch to GPU
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		font.draw(sb, "Press ESC to return to the main menu.", 10, Game.V_HEIGHT-10);
		for(Model i:models){
			Sprite sprite = i.getSprite();
			if(sprite != null) sprite.draw(sb);
		}
		sb.end();
				
		// Render Box2d world - development purposes only
		b2dDebugRenderer.render(world, b2dCamera.combined);
	}
	
}









