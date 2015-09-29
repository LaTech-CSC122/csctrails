package csctrails.handlers;

import java.util.Stack;

import csctrails.main.Game;
import csctrails.states.GameState;
import csctrails.states.*;


/**
 * The Game State Manager is the middle-man between the application
 * listener (Game) and the game-state. It had two main responsibilities.
 * 
 * 1. The GSM maintains a stack. The item on top of the stack is the
 *    current game-state that will be ran. The game-state will let the
 *    GSM know when it is done, what game state to push onto the stack
 *    next and whether it the current game-state should be popped off
 *    first.
 * 
 * 2. The GSM is responsible to passing the render, update, dispose
 *    etc. commands from the application listener to whichever
 *    game-state is the current game-state.
 *    
 *
 * Change Log:
 * 15.9.20gha: First Edition
 * 
 */

public class GameStateManager {
	
	public static final int MAIN_MENU = 1;
	public static final int PLAY = 2;
	public static final int GAME_OVER = 3;

	private Game game;
	private Stack<GameState> gameStates;
	
	
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY);
	}
	
	public Game getGame() { return game; }
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	private GameState getState(int state) {
		if(state == PLAY) return new PlayState(this);
		if(state == MAIN_MENU) return new MainMenuState(this);
		if(state == GAME_OVER) return new GameOverState(this);
		return null;
	}
	
	public void setPlayState(int state) {
		if(pushState(state)) popState(gameStates.size()-2);
		
	}
	
	public boolean pushState(int state) {
		GameState gamestate = getState(state);
		if(gamestate==null){
			Game.logger.log("Error: GSM could not push \"" + state + "\" onto the stack."
					+ "\nGSM will try to continue running the current state.");
			return false;
		}
		else{
			Game.logger.log("GSM: Pushing \"" + gamestate.getTitle() + "\" on the stack");
			gameStates.push(gamestate);
			return true;
		}
	}
	
	public void popState() {
		popState(gameStates.size()-1);
	}
	
	private void popState(int index) {
		GameState g = gameStates.remove(index);
		Game.logger.log("GSM: Popping \"" + g.getTitle() + "\" off the stack.");
		g.dispose();
	}
	
}















