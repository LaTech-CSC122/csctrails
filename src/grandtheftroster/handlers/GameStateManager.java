package grandtheftroster.handlers;

import grandtheftroster.main.Game;
import grandtheftroster.states.*;

import java.util.Stack;



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
	
	private static class State{
		private static int nextId = 0;
		private int id;
		public State(){
			id = nextId;
			nextId++;
		}
	}
	
	public static final GameStateManager.State INFO = new State();
	public static final GameStateManager.State LEVEL_ONE = new State();
	public static final GameStateManager.State LEVEL_TWO = new State();
	public static final GameStateManager.State LEVEL_THREE = new State();
	public static final GameStateManager.State GAME_OVER = new State();
	public static final GameStateManager.State GAME_WON = new State();
	public static final GameStateManager.State SPLASH_SCREEN = new State();
	
	private Game game;
	private Stack<GameState> gameStates;
	
	
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
	}
	
	public Game getGame() { return game; }
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	private GameState getState(GameStateManager.State state) {
		if(state.id == LEVEL_ONE.id) return new Lvl1State(this);
		if(state.id == LEVEL_TWO.id) return new Lvl2State(this);
		if(state.id == INFO.id) return new InfoState(this);
		if(state.id == GAME_OVER.id) return new GameOverState(this);
		if(state.id == GAME_WON.id) return new GameWonState(this);
		if(state.id == SPLASH_SCREEN.id) return new SplashScreenState(this);
		return null;
	}
	
	public void setState(State state) {
		if(pushState(state)) popState(gameStates.size()-2);
		
	}
	
	public boolean pushState(State state) {
		GameState gamestate = getState(state);
		if(gamestate==null){
			return false;
		}
		else{
			gameStates.push(gamestate);
			return true;
		}
	}
	
	public void popState() {
		popState(gameStates.size()-1);
	}
	
	private void popState(int index) {
		GameState g = gameStates.remove(index);
		g.dispose();
	}
	
	public void destoryAll(){
		while(!gameStates.empty()){
			gameStates.pop().dispose();
		}
	}
	
}















