package csctrails.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
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

public class PlayContactListener implements ContactListener {
	
	// called when two fixtures start to collide
	public void beginContact(Contact c) {}
	
	// called when two fixtures no longer collide
	public void endContact(Contact c) {}
	
	//called before calculating physics of collision
	public void preSolve(Contact c, Manifold m) {}
	
	//called after calculating physics of collision
	public void postSolve(Contact c, ContactImpulse ci) {}
}
