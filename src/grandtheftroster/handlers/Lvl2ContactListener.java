package grandtheftroster.handlers;

import grandtheftroster.elements.Model;
import grandtheftroster.player.Player;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


/**
 * This is the ContactListener for the Play game-state.
 * After being passed to the world object, the game-state
 * will serve as an event handle for every collision.
 * This gives a location to program what happens when
 * collisions occur.
 * 
 * Change Log:
 * 15.9.21gha: First Edition
 *
 */

public class Lvl2ContactListener implements ContactListener {
	
	private boolean gameWon;
	
	public Lvl2ContactListener(){
		gameWon = false;
	}
	
	public boolean getGameWon(){ return gameWon; }
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {
		Fixture[] fixtures = new Fixture[2]; 
		
		fixtures[0] = c.getFixtureA();
		fixtures[1] = c.getFixtureB();
		handleBeginContact(fixtures);
		
		fixtures[0] = fixtures[1];
		fixtures[1] = c.getFixtureA();
		handleBeginContact(fixtures);
		
	}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {
		Fixture[] fixtures = new Fixture[2];
		
		fixtures[0] = c.getFixtureA();
		fixtures[1] = c.getFixtureB();
		handleEndContact(fixtures);
		
		fixtures[0] = c.getFixtureB();
		fixtures[1] = c.getFixtureA();
		handleEndContact(fixtures);
	}
	
	//called before calculating physics of collision
	public void preSolve(Contact c, Manifold m) {}
	
	//called after calculating physics of collision
	public void postSolve(Contact c, ContactImpulse ci) {
		Fixture[] fixtures = new Fixture[2];
		
		fixtures[0] = c.getFixtureA();
		fixtures[1] = c.getFixtureB();
		handlePostSolve(fixtures);
		
		fixtures[0] = c.getFixtureB();
		fixtures[1] = c.getFixtureA();
		handlePostSolve(fixtures);
		
	}
	
	
	private void handleBeginContact(Fixture[] fixtures){
		if(!(fixtures[0].getBody().getUserData() instanceof Model) ||
				!(fixtures[1].getBody().getUserData() instanceof Model)){ return; }
		Model model0 = (Model) fixtures[0].getBody().getUserData();
		Model model1 = (Model) fixtures[1].getBody().getUserData();
		if(model0.hasTag("player")){
			String[] tags = model1.getTags();
			for(int i=0; i<tags.length; i++){
				System.out.print(tags[i] + " | ");
			}
			System.out.println();
			((Player) model0).handleBeginContact(model1);
		}
		else if(model0.hasTag("roster")){
			gameWon = true;
		}
		
		
		
		
	}

	private void handleEndContact(Fixture[] fixtures){
		if(!(fixtures[0].getBody().getUserData() instanceof Model) ||
				!(fixtures[1].getBody().getUserData() instanceof Model)){ return; }
		Model model0 = (Model) fixtures[0].getBody().getUserData();
		Model model1 = (Model) fixtures[1].getBody().getUserData();
		if(model0.hasTag("player")){
			((Player) model0).handleEndContact(model1);
		}
		
		//If the players foot collides with the ground
		
	}

	private void handlePostSolve(Fixture[] fixtures){
		if(!(fixtures[0].getBody().getUserData() instanceof Model) ||
				!(fixtures[1].getBody().getUserData() instanceof Model)){ return; }
		Model model0 = (Model) fixtures[0].getBody().getUserData();
		Model model1 = (Model) fixtures[1].getBody().getUserData();
		
	}
}
