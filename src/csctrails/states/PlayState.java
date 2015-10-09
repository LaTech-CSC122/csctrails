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

import csctrails.configuration.Configuration;
import csctrails.elements.Model;
import csctrails.elements.ModelLoader;

import csctrails.elements.Player;
import csctrails.elements.Thrower;
import csctrails.elements.Thrown;
import csctrails.handlers.GameStateManager;
import csctrails.handlers.MyInput;
import csctrails.handlers.PlayContactListener;
import csctrails.main.Game;


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
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}
	
	//Box2D Fields
	private World world;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private PlayContactListener cl;
	
	//Model Fields
	Thrower thrower;
	Player player;
	
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
		
		
		
		//Load TileMap
		if(cfg.hasProperty("LEVEL_1@PATHS:MAPS")){
			map = new TmxMapLoader().load(cfg.getProperty("LEVEL_1@PATHS:MAPS"));
			//Load Tile Map Tile Layers
			TiledMapTileLayer tmPlatformLeft = (TiledMapTileLayer) map.getLayers().get("platforms_left");
			TiledMapTileLayer tmPlatformRight = (TiledMapTileLayer) map.getLayers().get("platforms_right");
			TiledMapTileLayer tmLadders = (TiledMapTileLayer) map.getLayers().get("ladders");
			//Load Models
			ModelLoader.tiledMapLoader(tmPlatformLeft, world, "MODEL:PLATFORM_GROUND", "left,ground");
			ModelLoader.tiledMapLoader(tmPlatformLeft, world, "MODEL:PLATFORM_CEILING", "left,ceiling");
			ModelLoader.tiledMapLoader(tmPlatformRight, world, "MODEL:PLATFORM_GROUND", "right,ground");
			ModelLoader.tiledMapLoader(tmPlatformRight, world, "MODEL:PLATFORM_CEILING", "right,ceiling");
			ModelLoader.tiledMapLoader(tmLadders, world, "MODEL:LADDER", "");
		} else{
			map = new TiledMap();
		}
		tmr = new OrthogonalTiledMapRenderer(map);
		tmr.setView(camera);
		
		//Boundaries
		new Model(world, "MODEL:BOUNDARY_SIDES");
		new Model(world, "MODEL:BOUNDARY_BOTTOM");
		//Key
		models.add(new Model(world, "MODEL:KEY", 16*24, 16*29));
		//Player
		player = new Player(world, "MODEL:PLAYER", 16*1, 16*3);
		models.add(player);
		//Boss
		models.add(new Model(world, "MODEL:BOSS", Game.V_WIDTH-16*7, Game.V_HEIGHT-16*5));
		//Thrower
		thrower = new Thrower(world, 16, 120);
		thrower.setProbability(0.08f);
		thrower.setPosistion(16*19, 16*32);
		thrower.setActive(false);
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
		
		
	}
	
	public void update(float dt) {
		//dt is the time since update was last ran - gha 15.9.25
		handleInput();
		world.step(dt, 6, 2);
		hud.modifyTime(dt);
		
		//Clean up inactive models
		ArrayList<Model> modelsToDestroy = Model.getDestoryList();
		if(modelsToDestroy.size() > 0){
			for(Model model:modelsToDestroy){
				if(model.destory() && models.contains(model)){ models.remove(model); }
			}
			Model.clearDestoryList();
		}
		
		for(Model m:models){
			m.update(dt);
		}
		
		//Try to Throw Something
		Thrown t = thrower.throwObject();
		if(t != null){ models.add(t); }
		
	
		
		
		
		//Has Player won? if so add 1 to the classes score
		if(cl.getGameWon()){
			hud.modifyClassScore(+1);
			gsm.setPlayState(GameStateManager.GAME_WON);
		}
		
		//See if player has died and if so adds one to the anky score
		if(!player.isAlive() && hud.getLives()>0){
			player.setIsAlive(true);
			hud.modifyLives(-1);
			player.getBody().setTransform(16*1/PPM, 16*3/PPM, 0);
			player.getBody().setLinearVelocity(0, 0);
		}
		else if(!player.isAlive() && hud.getLives()<=0){
			hud.modifyAnky(+1);
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
		font.draw(sb, "Time: " + (int) hud.getTime(), 10, Game.V_HEIGHT-10);
		font.draw(sb, "Grade: " + hud.getScore(), 180, Game.V_HEIGHT-10);
		font.draw(sb, "Lives left: " + hud.getLives(), 80, Game.V_HEIGHT-10);
		for(Model i:models){
			i.draw(sb);
		}
		sb.end();
				
		// Render Box2d world - development purposes only
		//b2dDebugRenderer.render(world, b2dCamera.combined);
	}
	
}









