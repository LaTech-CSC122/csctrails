package csctrails.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import csctrails.elements.Model;
import csctrails.elements.Player;
import csctrails.elements.Thrown;

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

public class PlayContactListener implements ContactListener {
	
	private boolean gameWon;
	
	public PlayContactListener(){
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
		
		
		if(model0.hasTag("player") && model1.hasTag("platform, ground")){
			((Player) fixtures[0].getBody().getUserData()).addGroundContact();
		}
		else if(model0.hasTag("player") && model1.hasTag("ladder")){
			((Player) fixtures[0].getBody().getUserData()).addLadderContact();
			
		}
		else if(model0.hasTag("thrown") && model1.hasTag("boundary")){
			Thrown t = (Thrown) fixtures[0].getBody().getUserData();
			t.flagForDestory();
		}
		else if(model0.hasTag("player") && model1.hasTag("thrown")){
			((Player) fixtures[0].getBody().getUserData()).setIsAlive(false);
			Thrown t = (Thrown) fixtures[1].getBody().getUserData();
			t.flagForDestory();
		}
		else if(model0.hasTag("thrown") && model1.hasTag("platform,left")){
			Thrown t = (Thrown) fixtures[0].getBody().getUserData();
			t.addGroundContact();
		}
		else if(model0.hasTag("thrown") && model1.hasTag("platform,right")){
			Thrown t = (Thrown) fixtures[0].getBody().getUserData();
			t.addGroundContact();
		}
		else if(model0.hasTag("player") && model1.hasTag("key")){
			gameWon = true;
		}
		else if(model0.hasTag("player") && model1.hasTag("boundary")){
			((Player) fixtures[0].getBody().getUserData()).setIsAlive(false);
		}
		
	}

	private void handleEndContact(Fixture[] fixtures){
		if(!(fixtures[0].getBody().getUserData() instanceof Model) ||
				!(fixtures[1].getBody().getUserData() instanceof Model)){ return; }
		Model model0 = (Model) fixtures[0].getBody().getUserData();
		Model model1 = (Model) fixtures[1].getBody().getUserData();
		
		//If the players foot collides with the ground
		if(model0.hasTag("player") && model1.hasTag("platform, ground")){
			((Player) fixtures[0].getBody().getUserData()).removeGroundContact();
		}
		else if(model0.hasTag("player") && model1.hasTag("ladder")){
			((Player) fixtures[0].getBody().getUserData()).removeLadderContact();
		}
		else if(model0.hasTag("thrown") && model1.hasTag("platform")){
			((Thrown) fixtures[0].getBody().getUserData()).removeGroundConact();
		}
	}

	private void handlePostSolve(Fixture[] fixtures){
		if(!(fixtures[0].getBody().getUserData() instanceof Model) ||
				!(fixtures[1].getBody().getUserData() instanceof Model)){ return; }
		Model model0 = (Model) fixtures[0].getBody().getUserData();
		Model model1 = (Model) fixtures[1].getBody().getUserData();
		
		if(model0.hasTag("thrown") && model1.hasTag("platform, left")){
			Thrown t = (Thrown) fixtures[0].getBody().getUserData();
			t.pushLeft();
			//t.addGroundContact();
		}
		else if(model0.hasTag("thrown") && model1.hasTag("platform, right")){
			Thrown t = (Thrown) fixtures[0].getBody().getUserData();
			t.pushRight();
			//t.addGroundContact();
		}
	}
}
