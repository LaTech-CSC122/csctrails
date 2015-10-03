package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


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

public class Model {
	//Static Fields
	private static final ArrayList<Model> flaggedForDestruction = new ArrayList<Model>();
	
	//Static Utilities
	public static ArrayList<Model> getDestoryList(){
		ArrayList<Model> output = new ArrayList<Model>();
		output.addAll(flaggedForDestruction);
		return output;
	}
	public static void clearDestoryList(){ flaggedForDestruction.clear(); }
	public static int destorySize(){ return flaggedForDestruction.size(); }
	
	
	//+------------------+
	//|STATICS END NOW!!!|
	//+------------------+
	
	
	//Instance Field Variables	
	protected int textureHeight; // moved from subs - gha 15.9.29
	protected int textureWidth; // moved from subs - gha 15.9.29
	protected Body body;
	protected Sprite sprite;
	protected TagList tags;
	
	public Model(World world, String cfgProfileName){
		this(world, cfgProfileName, -1, -1);
	}
	
	public Model(World world, String cfgProfileName, float xpos, float ypos){
		//Initialize all fields (that you can)
		textureHeight = 0;
		textureWidth = 0;
		tags = new TagList();
		
		//Stop here if use passed a null profile name
		if(cfgProfileName.equals(null)){ return; }
		
		//load sprite
		sprite = ModelLoader.createSprite(cfgProfileName);
		if(sprite != null){
			textureHeight = sprite.getTexture().getHeight();
			textureWidth =  sprite.getTexture().getWidth();
		}
		//create body
		body = ModelLoader.createBody(cfgProfileName, world);
		body.setUserData(this);
		ModelLoader.createFixtures(cfgProfileName, body);
		//move the body if x,y != -1
		if(xpos >= 0 && ypos >= 0){
			body.setTransform(xpos/PPM, ypos/PPM, 0);
		}
		//Import tags from profile
		String cfgTags = ModelLoader.getProperty("TAGS@" + cfgProfileName);
		if(cfgTags != null){
			addTag(cfgTags);
		}
		
	}

	//Accessors
	public Body getBody(){ return body; }
	public Sprite getSprite(){ 
		if(sprite == null) return null;
		Vector2 pos = body.getPosition();
		sprite.setPosition(pos.x*PPM - textureWidth/2, pos.y*PPM - textureHeight/2); // TODO: offset to align with Box2D Body - gha 15.9.20
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		return sprite; 
	}
	
	
	//TagList methods
	public void addTag(String t){ tags.add(t); }
	public void addTags(String[] t){ tags.add(t); }
	public boolean hasTag(String t){ return tags.contains(t); }
	public String getId(){ return tags.getId(); }
	public String[] getTags(){
		return tags.getTags();
	}
	
	//Utilities
	public boolean equals(Object obj){
		if(!(obj instanceof Model)){ return false; }
		Model model = (Model) obj;
		return this.getId().equals(model.getId());
	}
	public boolean destory(){
		//Do not run if the Box2D world is locked
		if(body.getWorld().isLocked()){ return false; }
		if(body != null){
			Array<Fixture> fixtures = body.getFixtureList();
			for(Fixture currentFixture:fixtures){
				body.destroyFixture(currentFixture);
			}
			body.getWorld().destroyBody(body);
		}
		return true;
	}
	public void flagForDestory(){
		flaggedForDestruction.add(this);
	}
}
