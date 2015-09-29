package csctrails.elements;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;


/**
 * This is the super class for all models that exist in the game.
 * A model is object in the game that must be rendered (have a 
 * sprite) and exist in the physics simulation. Subclasses of Model
 * serve as a way to tie the sprite and Box2D body together along
 * with any other data and functionality the model requires.
 * Additional data and functionality could include a characters
 * health, a model's weight, or the ability to change the sprite 
 * during game play. 
 * 
 * Change Log:
 * 15.9.20gha: First Edition
 *
 */

public abstract class Model {
	
	protected Body body;
	protected Sprite sprite;
	protected String name;
	
	public Model(Body body, Sprite sprite, String name){
		this.body = body;
		this.sprite = sprite;
		this.name = name;
	}

	public Body getBody(){ return body; }
	public String getName(){ return name; }
	
	/**
	 * Considered updating the sprite position/rotation before returning 
	 * but decided to leave that functionality up to the subclass. I am
	 * un sure of the best way to handle this at the time. - gha 15.9.21
	*/
	public Sprite getSprite(){ return sprite; }
}
