package grandtheftroster.states;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import grandtheftroster.elements.Fan;
import grandtheftroster.elements.Model;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.Lvl2ContactListener;
import grandtheftroster.main.Game;
import grandtheftroster.player.Player;

public class Lvl2State extends GameState{
	private World world;
	private Player player;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	private Lvl2ContactListener cl;
	public Lvl2State(GameStateManager gsm) {
		super(gsm, "LvL 2");
		cl = new Lvl2ContactListener();
		world = new World(new Vector2(0f, -3f), false);
		world.setContactListener(cl);
		
		b2dDebugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
		player = new Player(world, "MODEL:PLAYER", 16*5, 16*37);
		models.add(player);
		new Model(world, "MODEL:BOUNDARY_SIDES");
		new Model(world, "MODEL:BOUNDARY_BOTTOM");
		new Fan(world, 64+16*10, 64*2, 100);
		
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
		
		sb.begin();
		for(Model m:models){
			m.draw(sb);
		}
		sb.end();
		
		b2dDebugRenderer.render(world, b2dCamera.combined);
		// TODO Auto-generated method stub
		
	}

}
