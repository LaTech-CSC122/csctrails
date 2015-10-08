package csctrails.main;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import csctrails.elements.HudCounter;
import csctrails.elements.Player;
import csctrails.handlers.GameStateManager;
import csctrails.handlers.MyInput;
import csctrails.handlers.MyInputProcessor;

/**
 * The Game class is an Application Listener. The LwjglApplication
 * will take this class and tell it when it is time to render, if
 * it was resized, if the application is shutting down. This is
 * done by the LwjglApplication calling on the render(), resize(),
 * dispose(), etc methods ApplicationListener interface requires.
 * 
 * Rather than programming the entire game here, the game is
 * broken into multiple states. The Game State Manager (GSM) will
 * manage each of the states. By telling the GSM to render, the GSM
 * will tell the current game-state, whatever it may be, to render.
 * This allows for a more flexible, easy-to-read, and extensible
 * design.
 * 
 * In addition to the GSM, a few other important task are completed
 * here such. Each task should be documented in-line.
 *
 * Change Log:
 * 15.9.21gha: First edition
 * 
 */

public class Game implements ApplicationListener {
	
	public static final Logger logger = new Logger();
	
	public static final String TITLE = "CSC Trails";
	public static final int V_WIDTH = 448;
	public static final int V_HEIGHT = 512;
	public static final int SCALE = 1;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	private SpriteBatch sb;
	private OrthographicCamera camera;
	private GameStateManager gsm;
	private HudCounter hud;
	
	public void create() {
		//TODO: Organize Game.create() method
		logger.log("Application: Constructing application listener...");
		
		logger.log("Application: Disabling GL image power of two requirement. \n" +
				"This could cause problems in older systems.");
		Texture.setEnforcePotImages(false); // Prevents GL from forcing power of 2 images
		Gdx.input.setInputProcessor(new MyInputProcessor()); // Set the input listener of the application - gha 15.9.21
		sb = new SpriteBatch();
		hud = new HudCounter(2, 0, 0);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH*SCALE, V_HEIGHT*SCALE);  // Set the view of the main camera - gha 15.9.21
	
		
		gsm = new GameStateManager(this);
	}
	public void render() {
		// Throttles the frame rate (rendering) and step interval (Box2D) for the program  - gha 15.9.21  
		accum = Gdx.graphics.getDeltaTime();
		//if(accum>=STEP) System.out.println(accum/STEP);
		//while(accum >= STEP) {
			//accum -= STEP;
			gsm.update(accum);
			gsm.render();
			MyInput.update();
		//}
		
		
		
	}
	public void dispose() {
		logger.log("Application: Terminating");
		logger.close();
	}	
	public void resize(int w, int h) {
		logger.log("Application: Resized to (" + w + "," + h + ")");
	}
	public void pause() {
		logger.log("Application: Paused");
	}
	public void resume() {
		logger.log("Application: Resumed");
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return camera; }
	public HudCounter getHud(){ return hud; }
}
