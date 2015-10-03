package csctrails.states;


import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.elements.Ladder;
import csctrails.elements.Model;
import csctrails.elements.Platform;
import csctrails.elements.Player;
import csctrails.elements.Thrower;
import csctrails.elements.Thrown;
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
	//private static Random random = new Random(); not sure why I need this - gha
	
	//Box2D Fields
	private World world;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private PlayContactListener cl;
	
	//Model Fields
	Player player;
	Thrower thrower;
	
	//Fonts
	BitmapFont font;
	
	//tiled
	TiledMap map;
	OrthogonalTiledMapRenderer tmr;

	public PlayState(GameStateManager gsm) {
		super(gsm, "Play");
	
		
		//HUD
		hud.resetAll();
		
		//Box2D World
		Game.logger.log("GS: Creating Box2D world and cameras for " + title);
		world = new World(new Vector2(0f, -3f), false);
		cl = new PlayContactListener();
		b2dDebugRenderer = new Box2DDebugRenderer(); // Used to render Box2D world when developing - gha 15.9.20
		world.setContactListener(cl);
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
		//Boundary Body
		new Model(world, "MODEL:BOUNDARY");
		
		//Tiled Map Layout
		map = new TmxMapLoader().load(Paths.TILEDMAP_PLAY_01);
		tmr = new OrthogonalTiledMapRenderer(map);
		tmr.setView(camera);
		//left platforms
		TiledMapTileLayer tmtl = (TiledMapTileLayer) map.getLayers().get("platforms_left");
		String[] leftPlatformTags = {"left"};
		Platform.loadPlatforms(world, tmtl, leftPlatformTags);
		//right platforms
		tmtl = (TiledMapTileLayer) map.getLayers().get("platforms_right");
		String[] rightPlatformTags = {"right"};
		Platform.loadPlatforms(world, tmtl, rightPlatformTags);
		//ladders
		tmtl = (TiledMapTileLayer) map.getLayers().get("ladders");
		Ladder.loadLadders(world, tmtl, null);
		
		//Object Layout
		Game.logger.log("GS: Creating Models for " + title);

		Model m = new Model(world, "MODEL:KEY");
		m.getBody().setTransform(16*24/PPM, 16*29/PPM, 0f);
		models.add(m);
		
		player = new Player(world, "MODEL:PLAYER", 16*1, 16*3);
		models.add(player); // Model must be added to modelList or it will not be rendered - gha 15.9.25
		
		models.add(new Model(world, "MODEL:BOSS", Game.V_WIDTH-16*7, Game.V_HEIGHT-16*5));
		
		thrower = new Thrower(world, 16, 120);
		thrower.setProbability(0.08f);
		thrower.setPosistion(16*19, 16*32);
		thrower.setActive(true);

		models.add(thrower.throwObject(16*20, 16*7));
		models.add(thrower.throwObject(16*10, 16*7));
		models.add(thrower.throwObject(16*20, 16*11));
		models.add(thrower.throwObject(16*10, 16*11));
		models.add(thrower.throwObject(16*20, 16*15));
		models.add(thrower.throwObject(16*10, 16*15));
		models.add(thrower.throwObject(16*20, 16*19));
		models.add(thrower.throwObject(16*10, 16*19));
		models.add(thrower.throwObject(16*20, 16*23));
		models.add(thrower.throwObject(16*10, 16*23));
		models.add(thrower.throwObject(16*10, 16*28));

		//Fonts
		font = new BitmapFont();
		
	}

				
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_ESC)) gsm.popState();
		
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){
			player.moveLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			player.moveRight();
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			if(player.climbUp());
			else if(player.jump());
		}
		if(MyInput.isDown(MyInput.BUTTON_UP)){
			player.climbUp();
		}
		if(MyInput.isDown(MyInput.BUTTON_DOWN)){
			player.climbDown();
		}
	}
	
	public void update(float dt) {
		//dt is the time since update was last ran - gha 15.9.25
		handleInput();
		world.step(dt, 6, 2);
		
		//Clean up inactive models
		ArrayList<Model> modelsToDestroy = Model.getDestoryList();
		if(modelsToDestroy.size() > 0){
			for(Model model:modelsToDestroy){
				if(model.destory() && models.contains(model)){ models.remove(model); }
			}
			Model.clearDestoryList();
		}
		
		//Try to Throw Something
		Thrown t = thrower.throwObject();
		if(t != null){ models.add(t); }
		//See if player has died
		if(!player.isAlive()){
			gsm.setPlayState(GameStateManager.GAME_OVER);
		}
		

	}
	
	public void render() {
		// Clear previous screen
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tmr.render();
		
		//SpriteBatch to GPU
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		font.draw(sb, "Press ESC to return to the main menu.", 10, 15);
		for(Model i:models){
			Sprite sprite = i.getSprite();
			if(sprite != null) sprite.draw(sb);
		}
		sb.end();
				
		// Render Box2d world - development purposes only
		b2dDebugRenderer.render(world, b2dCamera.combined);
	}
	
}









