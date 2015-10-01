package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
 * 15.9.29gha: Moved getSprite functionality from sub to super
 *             Moved variables from sub to super
 *
 */

public abstract class Model {
	protected int textureHeight; // moved from subs - gha 15.9.29
	protected int textureWidth; // moved from subs - gha 15.9.29
	protected Body body;
	protected Sprite sprite;
	protected TagList tags;
	
	public Model(){
		tags = new TagList();
		tags.add("model");
	}


	public Body getBody(){ return body; }
	public Sprite getSprite(){ 
		if(sprite == null) return null;
		Vector2 pos = body.getPosition();
		sprite.setPosition(pos.x*PPM - textureWidth/2, pos.y*PPM - textureHeight/2); // TODO: offset to align with Box2D Body - gha 15.9.20
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		return sprite; 
	}
	
	public void addTag(String t){ tags.add(t); }
	public void addTags(String[] t){ tags.add(t); }
	public boolean hasTag(String[] t){ return tags.contains(t); }
	public boolean hasTag(String t){ return tags.contains(t); }
	public String getId(){ return tags.getId(); }
	
	public boolean equals(Object obj){
		if(!(obj instanceof Model)){ return false; }
		Model model = (Model) obj;
		return this.getId().equals(model.getId());
	}
}
