package csctrails.states;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

import csctrails.elements.HudCounter;
import csctrails.elements.Model;
import csctrails.elements.Player;
import csctrails.handlers.GameStateManager;
import csctrails.main.Game;

/**
 * A Game State could be defined as a single state that the game
 * exist in. For example, the welcome screen, play screen, settings,
 * game-over, and credits could all be different Game States that
 * must be navigated through.
 * 
 * This is the superclass of all GameStates. It sets the interface
 * that will exist between the Game State Manager and each Game State.
 * Essential objects are already imported and instantiated.
 * 
 * Change Log:
 * 15.9.20gha: First Edition
 *
 */

public abstract class GameState {
	
	protected String title;
	protected GameStateManager gsm; // Used to the GS can tell the GSM when it is done and what to do next - gha 15.9.20
	protected Game game;
	
	protected SpriteBatch sb;
	protected OrthographicCamera camera;
	protected ArrayList<Model> models; // A list of all Models that exist. Missing objects will not be rendered by default. - gha 15.9.20
	protected HudCounter hud;
	
	protected GameState(GameStateManager gsm, String title) {
		//Params
		this.gsm = gsm;
		this.title = title;
		//Log File
		Game.logger.log(title + ": Constructing Game State");
		// Fields from Game
		game = gsm.getGame();
		sb = game.getSpriteBatch();
		camera = game.getCamera();
		hud = game.getHud();
		//Field initialization
		models = new ArrayList<Model>();
	}
	
	// Basic interface between the GSM and game states. - gha 15.9.20 
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public void dispose(){
		Game.logger.log(title + ": Disposing of game state");
		for(Model model:models){
			Array<Fixture> fixList = model.getBody().getFixtureList();
			for(Fixture fix:fixList){
				model.getBody().destroyFixture(fix);
			}
		}
	}
	
	public String getTitle(){ return title;}
	
}









