package grandtheftroster.states;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.elements.Model;
import grandtheftroster.elements.ModelLoader;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.Lvl3ContactListener;
import grandtheftroster.main.Game;
import grandtheftroster.player.Player;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

public class Lvl3State extends GameState{
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}
	
	private World world;
	private Player player;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private Lvl3ContactListener cl;
	
	//tiled
	private TiledMap map;
	private OrthogonalTiledMapRenderer tmr;

	public Lvl3State(GameStateManager gsm) {
		super(gsm, "LvL 3");
		cl = new Lvl3ContactListener();
		world = new World(new Vector2(0f, -3f), false);
		world.setContactListener(cl);
		
		b2dDebugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
		
		
		//Tiled Map Stuff
		if(cfg.hasProperty("LEVEL_3@PATHS:MAPS")){
			map = new TmxMapLoader().load(cfg.getProperty("LEVEL_3@PATHS:MAPS"));
			//Load Tile Map Tile Layers
			TiledMapTileLayer tmLethalBlocks = (TiledMapTileLayer) map.getLayers().get("lethal blocks");
			TiledMapTileLayer tmOtherBlocks = (TiledMapTileLayer) map.getLayers().get("other blocks");
			TiledMapTileLayer tmSpikes = (TiledMapTileLayer) map.getLayers().get("spikes");
			TiledMapTileLayer tmPlatforms = (TiledMapTileLayer) map.getLayers().get("platforms");
			//Load Models
			ModelLoader.tiledMapLoader(tmLethalBlocks, world, "MODEL:BLOCK", "fatal");
			ModelLoader.tiledMapLoader(tmOtherBlocks, world, "MODEL:BLOCK", "");
			ModelLoader.tiledMapLoader(tmSpikes, world, "MODEL:PLATFORM_GROUND", "fatal");
			ModelLoader.tiledMapLoader(tmPlatforms, world, "MODEL:BLOCK", "ground");
			
		} else{
			map = new TiledMap();
		}
		tmr = new OrthogonalTiledMapRenderer(map);
		tmr.setView(camera);
		
		
		//Place Models
		//player = new Player(world, "MODEL:PLAYER", 64+16*4, 64+16*14); //4/14
		player = new Player(world, "MODEL:PLAYER", 64+16*128*2, 64+16*18); //4/14
		player.setActivity(player.ACTIVITY_FLYING);
		models.add(player);
		models.add(new Model(world,"MODEL:REDBULL", 64+16*9, 64+16*12+8));
		models.add(new Model(world,"MODEL:ROSTER", 64+16*274, 64+16*9));
		
		}


	public void handleInput() {}

	public void update(float dt) {
		//Update World
		world.step(dt, 6, 2);
		
		//Update Models
		for(Model m:models){
			m.update(dt);
		}
		
		//Check for player death
		if(!player.isAlive()){
			player.revive();
			player.setPosition(64+16*4, 64+16*14, 0);
		}
		
		//Check for Winner
		if(cl.getGameWon()){
			gsm.setState(GameStateManager.GAME_WON);
		}
		
	}

	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// camera follow player
		camera.position.set(player.getBody().getPosition().x *PPM, (float)Game.V_HEIGHT / 2, 0f);
		camera.update();
		b2dCamera.position.set(player.getBody().getPosition().x, Game.V_HEIGHT/PPM/2, 0f);
		b2dCamera.update();
		
		tmr.setView(camera);
		tmr.render();
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		for(Model m:models){
			m.draw(sb);
		}
		sb.end();
		
		b2dDebugRenderer.render(world, b2dCamera.combined);
		// TODO Auto-generated method stub		
	}

}
