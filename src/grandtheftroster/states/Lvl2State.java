package grandtheftroster.states;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.main.Game;
import grandtheftroster.player.Player;

public class Lvl2State extends GameState{
	private World world;
	private Player player;
	private Box2DDebugRenderer b2dDebugRenderer;
	private OrthographicCamera b2dCamera;
	public Lvl2State(GameStateManager gsm) {
		super(gsm, "LvL 2");
		world = new World(new Vector2(0f, -3f), false);
		player = new Player(world, "MODELS:PLAYER", 64+16*4, 64+16*4);
		new Model(world, "MODELS:BOUNDARY_BOTTOM");
		new Model(world, "MODELS:BOUNDARY_SIDES");
		b2dDebugRenderer = new Box2DDebugRenderer();
		b2dCamera = new OrthographicCamera();
		b2dCamera.setToOrtho(false, Game.V_WIDTH*Game.SCALE/PPM, Game.V_HEIGHT*Game.SCALE/PPM);
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		world.step(dt, 6, 2);
		
	}

	@Override
	public void render() {
		b2dDebugRenderer.render(world, b2dCamera.combined);
		// TODO Auto-generated method stub
		
	}

}
