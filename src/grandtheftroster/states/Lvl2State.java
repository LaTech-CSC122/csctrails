package grandtheftroster.states;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import grandtheftroster.elements.*;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.Lvl2ContactListener;
import grandtheftroster.main.Game;
import grandtheftroster.player.Player;
import grandtheftroster.utilities.Configuration;

public class Lvl2State extends GameState{
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}
	
	private World world;
	private Player player;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private Lvl2ContactListener cl;
	
	//tiled
	private TiledMap map;
	private OrthogonalTiledMapRenderer tmr;

	public Lvl2State(GameStateManager gsm) {
		super(gsm, "LvL 2");
		cl = new Lvl2ContactListener();
		world = new World(new Vector2(0f, -3f), false);
		world.setContactListener(cl);
		
		b2dDebugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
		
		
		//Tiled Map Stuff
		if(cfg.hasProperty("LEVEL_2@PATHS:MAPS")){
			map = new TmxMapLoader().load(cfg.getProperty("LEVEL_2@PATHS:MAPS"));
			//Load Tile Map Tile Layers
			TiledMapTileLayer tmPlatform = (TiledMapTileLayer) map.getLayers().get("platforms");
			TiledMapTileLayer tmDeathbed = (TiledMapTileLayer) map.getLayers().get("deathbed");
			TiledMapTileLayer tmLadder = (TiledMapTileLayer) map.getLayers().get("ladders");
			//Load Models
			ModelLoader.tiledMapLoader(tmPlatform, world, "MODEL:PLATFORM_GROUND", "ground");
			ModelLoader.tiledMapLoader(tmPlatform, world, "MODEL:PLATFORM_CEILING", "ceiling");
			ModelLoader.tiledMapLoader(tmDeathbed, world, "MODEL:PLATFORM_GROUND", "fatal");
			ModelLoader.tiledMapLoader(tmDeathbed, world, "MODEL:PLATFORM_CEILING", "ceiling");
			ModelLoader.tiledMapLoader(tmLadder, world, "MODEL:LADDER", "");
	
		} else{
			map = new TiledMap();
		}
		tmr = new OrthogonalTiledMapRenderer(map);
		tmr.setView(camera);
		
		
		//Place Models
		new Model(world, "MODEL:BOUNDARY_SIDES");
		new Model(world, "MODEL:BOUNDARY_BOTTOM");
		player = new Player(world, "MODEL:PLAYER", 64+16*1, 64+16*4);
		//player = new Player(world, "MODEL:PLAYER", 64+16*27, 64+16*24);
		models.add(player);
		//Fans
		models.add(new Fan(world, 64+16*26, 64+16*2, 32*6));
		models.add(new Fan(world, 64+16*2, 64+16*10, 16*9));
		models.add(new Fan(world, 64+16*27, 64+16*24, 16*3));
		//Springs
		models.add(new Spring(world, 64+16*13, 64+16*6+2));
		models.add(new Spring(world, 64+16*5, 64+16*2+2));
		//Rope
		models.add(new Rope(world, 64+16*20, 64+16*20-3, 2*29)); //18
		//models.add(new Rope(world, 64+16*12, 64+16*20-3, 16*4));
		models.add(new Rope(world, 64+16*20, 64+16*34, 16*4));
		
	}


	public void handleInput() {}

	public void update(float dt) {
		world.step(dt, 6, 2);
		
		for(Model m:models){
			m.update(dt);
		}
		
	}

	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tmr.render();
		
		sb.begin();
		for(Model m:models){
			m.draw(sb);
		}
		sb.end();
		
		b2dDebugRenderer.render(world, b2dCamera.combined);
		// TODO Auto-generated method stub
		
	}

}
