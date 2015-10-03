package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import csctrails.configuration.Configuration;


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
	private static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models");
		cfg.loadConfiguration("res/config/paths/sprite paths.config");
	}
	
	//Static Factory Methods
	private static Body createBody(String cfgProfileName, World world){
		BodyDef bdef = new BodyDef();
		
		//Set Body Position
		if(cfg.hasProperty("BODY_POSITION_X@"+cfgProfileName) && 
				cfg.hasProperty("BODY_POSITION_Y@" + cfgProfileName)){
			int xpos = Integer.parseInt(cfg.getProperty("BODY_POSITION_X@" + cfgProfileName));
			int ypos = Integer.parseInt(cfg.getProperty("BODY_POSITION_Y@" + cfgProfileName));
			bdef.position.set(xpos/PPM, ypos/PPM);
		} else{
			bdef.position.set(0,0);
		}
		
		//Set Body Angle
		if(cfg.hasProperty("BODY_ANGLE@" + cfgProfileName)){
			float angle = Float.parseFloat(cfg.getProperty("BODY_ANGLE@" + cfgProfileName));
			bdef.angle = angle;
		}else{
			bdef.angle = 0f;
		}
		
		//Set Body Type
		if(cfg.hasProperty("BODY_TYPE@" + cfgProfileName)){
			String bodyType = cfg.getProperty("BODY_TYPE@" + cfgProfileName);
			if(bodyType.equals("DYNAMIC")){ bdef.type = B2DVars.DYNAMIC; }
			else if(bodyType.equals("STATIC")){ bdef.type = B2DVars.STATIC; }
			else if(bodyType.equals("KINEMATIC")){ bdef.type = B2DVars.KINEMATIC; }
			else { bdef.type = B2DVars.STATIC; }
		} else{
			bdef.type = B2DVars.STATIC;
		}
		
		//Set Body Active
		if(cfg.hasProperty("BODY_ACTIVE@" + cfgProfileName)){
			boolean b = Boolean.parseBoolean(cfg.getProperty("BODY_ACTIVE@" + cfgProfileName));
			bdef.active = b;
		}else{
			bdef.active = true;
		}
		return world.createBody(bdef);
	}

	private static Shape createShape(String cfgProfileName, String index){
		//Default Values
		String shapeType = "POLYGONSHAPE";
		int shapeWidth = 5;
		int shapeHeight = 5;
		int shapeRadius = 5;
		
		//Get Shape Type
		if(cfg.hasProperty("FIXTURE_SHAPE_TYPE" + index + "@" + cfgProfileName)){
			shapeType = cfg.getProperty("FIXTURE_SHAPE_TYPE" + index + "@" + cfgProfileName);
		}
		
		//Get Shape Width
		if(shapeType.equals("POLYGONSHAPE") &&
				cfg.hasProperty("FIXTURE_SHAPE_WIDTH" + index + "@" + cfgProfileName)){
			shapeWidth = Integer.parseInt(cfg.getProperty("FIXTURE_SHAPE_WIDTH" + index + "@" + cfgProfileName));
		}
		
		//Get Shape Height
		if(shapeType.equals("POLYGONSHAPE") &&
				cfg.hasProperty("FIXTURE_SHAPE_HEIGHT" + index + "@" + cfgProfileName)){
			shapeHeight = Integer.parseInt(cfg.getProperty("FIXTURE_SHAPE_HEIGHT" + index + "@" + cfgProfileName));
		}
		
		//Get Shape Radius
		if(shapeType.equals("CIRCLESHAPE") &&
				cfg.hasProperty("FIXTURE_SHAPE_RADIUS" + index + "@" + cfgProfileName)){
			shapeRadius = Integer.parseInt(cfg.getProperty("FIXTURE_SHAPE_RADIUS" + index + "@" + cfgProfileName));
		}
		
		//Create Shapes
		Shape shape = null;
		if(shapeType.equals("POLYGONSHAPE")){
				PolygonShape pShape = new PolygonShape();
				pShape.setAsBox(shapeWidth/2/PPM, shapeHeight/2/PPM);
				 shape = pShape;
			}
		else if(shapeType.equals("CIRCLESHAPE")){
				CircleShape cShape = new CircleShape();
				cShape.setRadius(shapeRadius/PPM);
				shape = cShape;
		}
		return shape;

	}
	
	private static Fixture createFixtures(String cfgProfileName, Body body, String index){
		FixtureDef fdef = new FixtureDef();

		//Create the shape
		Shape shape = createShape(cfgProfileName, index);
		if(shape == null){ return null; }
		fdef.shape = shape;
		
		//Pull Friction
		if(cfg.hasProperty("FIXTURE_FRICTION" + index + "@" + cfgProfileName)){
			float friction = Float.parseFloat(cfg.getProperty("FIXTURE_FRICTION" + index + "@" + cfgProfileName));
			fdef.friction = friction;
		} else{
			fdef.friction = 1f;
		}
		
		//Pull Restitution
		if(cfg.hasProperty("FIXTURE_RESTITUTION" + index + "@" + cfgProfileName)){
			float bouncy = Float.parseFloat(cfg.getProperty("FIXTURE_RESTITUTION" + index + "@" + cfgProfileName));
			fdef.restitution = bouncy;
		} else{
			fdef.restitution= 0;
		}
		
		//Set Sensor
		if(cfg.hasProperty("FIXTURE_SENSOR" + index + "@" + cfgProfileName)){
			boolean b = Boolean.parseBoolean(cfg.getProperty("FIXTURE_SENSOR" + index + "@" + cfgProfileName));
			fdef.isSensor = b;
		} else{
			fdef.isSensor = false;
		}
		
		//Set Category Bits
		if(cfg.hasProperty("FIXTURE_CATEGORY" + index + "@" + cfgProfileName)){
			short cat = Short.parseShort(cfg.getProperty("FIXTURE_CATEGORY" + index + "@" + cfgProfileName));
			fdef.filter.categoryBits = cat;
		} else{
			fdef.filter.categoryBits = 1;
		}
		
		//Set Category Bits
		if(cfg.hasProperty("FIXTURE_MASK" + index + "@" + cfgProfileName)){
			short mask = Short.parseShort(cfg.getProperty("FIXTURE_MASK" + index + "@" + cfgProfileName));
			fdef.filter.maskBits = mask;
		} else{
			fdef.filter.maskBits = 1;
		}
		
		
		return body.createFixture(fdef);
	}

	private static ArrayList<Fixture> createFixtures(String cfgProfileName, Body body){
		ArrayList<Fixture> fixtureList = new ArrayList<Fixture>();
		if(cfg.hasProperty("FIXTURE_COUNT@" + cfgProfileName)){
			int count = Integer.parseInt(cfg.getProperty("FIXTURE_COUNT@" + cfgProfileName));
			for(int i=1; i<=count; i++){
				Fixture f = createFixtures(cfgProfileName, body, "_"+i);
				if(f != null){ fixtureList.add(f); }
			}
		} else{
			fixtureList.add(createFixtures(cfgProfileName, body, ""));
		}
		return fixtureList;
	}
	
	private static Sprite createSprite(String cfgProfileName){
		//Get sprite name
		if(!cfg.hasProperty("SPRITE@" + cfgProfileName)){ return null; }
		String spriteName = cfg.getProperty("SPRITE@" + cfgProfileName);
		//Get sprite path
		if(!cfg.hasProperty(spriteName + "@PATHS:SPRITES")){ return null; }
		String path = cfg.getProperty(spriteName + "@PATHS:SPRITES");
		//Create Sprite
		Texture tex = new Texture(path);
		return new Sprite(tex);
		
	}
	
	
	
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
	
	public Model(World world, String cfgProfileName, int xpos, int ypos){
		//Initialize all fields (that you can)
		textureHeight = 0;
		textureWidth = 0;
		tags = new TagList();
		
		//Stop here if use passed a null profile name
		if(cfgProfileName.equals(null)){ return; }
		
		//load sprite
		sprite = createSprite(cfgProfileName);
		if(sprite != null){
			textureHeight = sprite.getTexture().getHeight();
			textureWidth =  sprite.getTexture().getWidth();
		}
		//create body
		body = createBody(cfgProfileName, world);
		body.setUserData(this);
		createFixtures(cfgProfileName, body);
		//move the body if x,y != -1
		if(xpos >= 0 && ypos >= 0){
			body.setTransform(xpos/PPM, ypos/PPM, 0);
		}
		//Import tags from profile
		if(cfg.hasProperty("TAGS@" + cfgProfileName)){
			addTag(cfg.getProperty("TAGS@" + cfgProfileName));
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
