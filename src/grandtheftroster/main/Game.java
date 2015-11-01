package grandtheftroster.main;


import grandtheftroster.audio.AudioPlayer;
import grandtheftroster.elements.HudCounter;
import grandtheftroster.handlers.GameStateManager;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.handlers.MyInputProcessor;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


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
	
	private static Configuration cfg;
	
	public static final String TITLE = "Grand Theft Roster";
	public static final int V_WIDTH = 576; //448
	public static final int V_HEIGHT = 640; //512
	public static final int SCALE = 1;
	
	public static final float STEP = 1 / 60f;
	private float accum;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}
	
	
	private SpriteBatch sb;
	private OrthographicCamera camera;
	private OrthographicCamera hudCamera;
	private GameStateManager gsm;
	private HudCounter hud;
	private LwjglApplication app;
	private AudioPlayer playlist;
	
	public void create() {
		//TODO: Organize Game.create() method
		loadAudio();
		
		Texture.setEnforcePotImages(false); // Prevents GL from forcing power of 2 images
		Gdx.input.setInputProcessor(new MyInputProcessor()); // Set the input listener of the application - gha 15.9.21
		sb = new SpriteBatch();
		hud = new HudCounter(3, 0, 0);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH*SCALE, V_HEIGHT*SCALE);  // Set the view of the main camera - gha 15.9.21
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, V_WIDTH*SCALE, V_HEIGHT*SCALE);
		
		gsm = new GameStateManager(this);
		gsm.pushState(GameStateManager.LEVEL_THREE); 
		
	}
	public void render() {
		accum = Gdx.graphics.getDeltaTime();
		gsm.update(accum);
		gsm.render();
		MyInput.update();
	}

	public void dispose() {
		gsm.destoryAll();
	}	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	public void shutdown(){
		app.exit();
	}
	
	public void setApplication(LwjglApplication app) { this.app = app; }
	public SpriteBatch getSpriteBatch() { return sb; }
	public OrthographicCamera getCamera() { return camera; }
	public OrthographicCamera getHudCamera(){ return hudCamera; }
	public HudCounter getHud(){ return hud; }
	public AudioPlayer getPlaylist(){ return playlist; }
	
	
	private void loadAudio(){
		playlist = new AudioPlayer();
		playlist.addSound("Level 1", cfg.getProperty("LVL1BKG@PATHS:AUDIO"), 0.5f, true);
		playlist.addSound("Level 2", cfg.getProperty("LVL2BKG@PATHS:AUDIO"), 0.3f, true);
		playlist.addSound("Level 3", cfg.getProperty("LVL3BKG@PATHS:AUDIO"), 0.4f, true);
		playlist.setVolume(0.75f);
	}
}
