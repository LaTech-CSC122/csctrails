package grandtheftroster.states;

import static grandtheftroster.elements.B2DVars.PPM;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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

		
	@SuppressWarnings("unchecked")
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
		
		
		//---Boundaries
		new Model(world, "MODEL:BOUNDARY_SIDES");
		new Model(world, "MODEL:BOUNDARY_BOTTOM");
		//---Player
		player = new Player(world, "MODEL:PLAYER", 64+16*1, 64+16*10);
		models.add(player);
		
		//---Fans
		models.add(new Fan(world, 64+16*27, 64+16*2, 32*6));
		models.add(new Fan(world, 64+16*25, 64+16*2, 32*6));
		
		models.add(new Fan(world, 64+16*1, 64+16*10, 16*9));
		//models.add(new Fan(world, 64+16*3, 64+16*10, 16*9));
		
		models.add(new Fan(world, 64+16*27, 64+16*24, 16*3));
		
		//---Springs
		models.add(new Spring(world, 64+16*13, 64+16*6+2));
		models.add(new Spring(world, 64+16*5, 64+16*2+2));
		
		//---Rope
		models.add(new Rope(world, 64+16*18-5, 64+16*19, 16*5)); //18
		models.add(new Rope(world, 64+16*18, 64+16*32, 16*3));
		
	
		
		//moving platforms
		ArrayList<Switchable> platformSwitchables = new ArrayList<Switchable>();
		platformSwitchables.add(new MovingPlatform(world, 32, 16*5, (2*3.412f)/2, 64+16*15, 64+8*4) );
		platformSwitchables.add(new MovingPlatform(world, 64, 16*4, (2*3.412f)/2, 64+16*14, 64+16*24) );
		platformSwitchables.add(new MovingPlatform(world, 64, -16*6, (2*3.412f)/2, 64+16*24, 64+16*24) );
		platformSwitchables.add(new MovingPlatform(world, 32, 16*7, (2*3.142f)/1.3f, 64+16*14, 64+16*28) );
		models.addAll((Collection<? extends Model>) platformSwitchables);
		
		
		
		//---Roster
		Model roster = new Model(world, "MODEL:ROSTER",16*6, 16*33);
		roster.setVisible(false);
		models.add(roster);
		
		//---Keys
		Switch chestKey = new Switch (world, 32, 16*5, (2*3.412f)/2, 64+16*15, 64+8*6-9);
		chestKey.addTag("chestKey");
		ArrayList<Switchable> chestKeySwitchables = new ArrayList<Switchable>();
		chestKeySwitchables.add(roster);
		chestKey.setSwitchable(chestKeySwitchables);
		models.add(chestKey);
		
		Switch platformSwitch = new Switch(world,32, 16*5, 0, 64+16*7, 64+16*24+4);
		platformSwitch.addTag("platformSwitch");
		platformSwitchables.add(chestKey);
		platformSwitch.setSwitchable(platformSwitchables);
		models.add(platformSwitch);
		platformSwitch.setVisible(true);
		
		
		//Load and begin music
		playlist.play("Level 2");
	}


	public void handleInput() {}

	public void update(float dt) {
		world.step(dt, 6, 2);
		
		for(Model m:models){
			m.update(dt);
		}
		
		//Check to see if player died
		if(!player.isAlive()&& hud.getLives()> 1){
			player.revive();
			player.setPosition(64+16*1, 64+16*4, 0);
			hud.modifyLives(-1);
		}
		else if(!player.isAlive() && hud.getLives()<= 1){
			hud.modifyAnky(+1);
			gsm.setState(GameStateManager.GAME_OVER);
		}
		
		//Check for if the game has been won
		if(cl.getGameWon()){
			gsm.setState(GameStateManager.LEVEL_THREE);
		}
		
		hud.modifyTime(dt);
	}

	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tmr.render();
		
		//SpriteBatch to camera
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		for(Model m:models){
			m.draw(sb);
		}
		sb.end();
		
		//SpriteBatch to hudCam
		sb.setProjectionMatrix(hudCam.combined);
		sb.begin();
		gfont16.draw("Time " + (int) hud.getTime(), GlyphFont.COLOR_WHITE, 8+64, Game.V_HEIGHT-20-64); //-10 originally
		gfont16.draw(hud.getScore(), GlyphFont.COLOR_WHITE, 64+16*13, Game.V_HEIGHT-20-64); // -10 originally
		gfont16.draw("Lives " + hud.getLives(), GlyphFont.COLOR_WHITE, Game.V_WIDTH-64-16*8, Game.V_HEIGHT-20-64); // -10 originally
		sb.draw(cabFrame, 0, 0);
		sb.end();
		
		//Show Physics Engine
		//b2dDebugRenderer.render(world, b2dCamera.combined);
		
	}
	
	public void dispose(){
		playlist.stop("Level 2");
	}
	
}
