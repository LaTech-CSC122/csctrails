package csctrails.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import csctrails.elements.Player;

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
	public void postSolve(Contact c, ContactImpulse ci) {}
	
	private void handleBeginContact(Fixture[] fixtures){
		Object userData0 = fixtures[0].getUserData();
		Object userData1 = fixtures[1].getUserData();
		if(userData0 == null || userData1 == null) return;
		
		//If the players foot collides with the ground
		if(userData0.toString().equals("player_foot") && userData1.toString().equals("ground")){
			((Player) fixtures[0].getBody().getUserData()).addGroundContact();
		}
		else if(userData0.toString().equals("player_body") && userData1.toString().equals("ladder")){
			((Player) fixtures[0].getBody().getUserData()).addLadderContact();
		}
	}

	private void handleEndContact(Fixture[] fixtures){
		Object userData0 = fixtures[0].getUserData();
		Object userData1 = fixtures[1].getUserData();
		if(userData0 == null || userData1 == null) return;
		
		//If the players foot collides with the ground
		if(userData0.toString().equals("player_foot") && userData1.toString().equals("ground")){
			((Player) fixtures[0].getBody().getUserData()).removeGroundContact();
		}
		if(userData0.toString().equals("player_body") && userData1.toString().equals("ladder")){
			((Player) fixtures[0].getBody().getUserData()).removeLadderContact();
		}
	}


}
