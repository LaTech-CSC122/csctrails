package grandtheftroster.states;


import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.elements.GlyphFont;
import grandtheftroster.elements.Model;
import grandtheftroster.elements.ModelLoader;
import grandtheftroster.elements.Player;
import grandtheftroster.elements.Thrower;
import grandtheftroster.elements.Thrown;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.PlayContactListener;
import grandtheftroster.main.Game;
import grandtheftroster.utilities.Configuration;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;



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
	@SuppressWarnings("unused")
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
		models.add(new Model(world, "MODEL:KEY", 16*28, 16*33));
		//Player
		player = new Player(world, "MODEL:PLAYER", 16*5, 16*7);
		models.add(player);
		//Boss
		models.add(new Model(world, "MODEL:BOSS", Game.V_WIDTH-16*11, Game.V_HEIGHT-16*9));
		//Thrower
		thrower = new Thrower(world, 16, 3f, 0.5f);
		thrower.setDeviation(8);//30 
		thrower.setPosistion(16*19, 16*32);
		models.add(thrower.throwObject(16*24, 16*11));
		models.add(thrower.throwObject(16*14, 16*11));
		models.add(thrower.throwObject(16*24, 16*15));
		models.add(thrower.throwObject(16*14, 16*15));
		models.add(thrower.throwObject(16*24, 16*19));
		models.add(thrower.throwObject(16*14, 16*19));
		models.add(thrower.throwObject(16*24, 16*23));
		models.add(thrower.throwObject(16*14, 16*23));
		models.add(thrower.throwObject(16*24, 16*28));
		models.add(thrower.throwObject(16*14, 16*28));
		models.add(thrower.throwObject(16*14, 16*32));
		
		//Fonts
		font = new BitmapFont();
	}

				
	public void handleInput() {		
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
		Thrown t = thrower.update(dt);
		if(t != null){ models.add(t); }
		
		//Has Player won? if so add 1 to the classes score
		if(cl.getGameWon()){
			hud.modifyClassScore(+1);
			gsm.setState(GameStateManager.GAME_WON);
		}
		
		//See if player has died and if so adds one to the anky score
		if(!player.isAlive() && hud.getLives()>0){
			player.setIsAlive(true);
			hud.modifyLives(-1);
			player.getBody().setTransform(16*5/PPM, 16*7/PPM, 0);
			player.getBody().setLinearVelocity(0, 0);
		}
		else if(!player.isAlive() && hud.getLives()<=0){
			hud.modifyAnky(+1);
			gsm.setState(GameStateManager.GAME_OVER);			
		}

	}
	
	public void render() {
		// Clear previous screen
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tmr.render();
		
		//SpriteBatch to GPU
		sb.setProjectionMatrix(camera.combined);
		sb.begin();

		gfont16.draw("Time " + (int) hud.getTime(), GlyphFont.COLOR_WHITE, 8+64, Game.V_HEIGHT-20-64); //-10 originally
		gfont16.draw(hud.getScore(), GlyphFont.COLOR_WHITE, 64+16*13, Game.V_HEIGHT-20-64); // -10 originally
		gfont16.draw("Lives " + hud.getLives(), GlyphFont.COLOR_WHITE, Game.V_WIDTH-64-16*8, Game.V_HEIGHT-20-64); // -10 originally
		
		
		for(Model i:models){
			i.draw(sb);
		}
		
		sb.draw(cabFrame, 0, 0);
		sb.end();
				
		// Render Box2d world - development purposes only
		//b2dDebugRenderer.render(world, b2dCamera.combined);
	}
	
}









