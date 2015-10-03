package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.configuration.Configuration;

public class ModelLoader {

	private static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models");
		cfg.loadConfiguration("res/config/paths/sprite paths.config");
	}
	
	//Static Factory Methods
	public static Body createBody(String cfgProfileName, World world){
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
		Vector2[] points = new Vector2[0];
		
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
		
		
		//Get Shape Points
		if(shapeType.contains("_POINTS") &&
				cfg.hasProperty("FIXTURE_SHAPE_POINT_COUNT" + index + "@" + cfgProfileName)){
			int numOfPoints = Integer.parseInt(cfg.getProperty("FIXTURE_SHAPE_POINT_COUNT" + index + "@" + cfgProfileName));
			points = new Vector2[numOfPoints];
			for(int i = 1; i<=numOfPoints; i++){
				String coordinates = cfg.getProperty("FIXTURE_SHAPE_POINT" + index + "_" + i + "@" + cfgProfileName);
				int x = Integer.parseInt(coordinates.substring(0, coordinates.indexOf(",")));
				int y = Integer.parseInt(coordinates.substring(coordinates.indexOf(",")+1));
				points[i-1] = new Vector2(x/PPM, y/PPM);
			}
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
		else if(shapeType.equals("CHAINSHAPE_POINTS")){
			//System.out.println(shapeType);
			ChainShape cShape = new ChainShape();
			cShape.createChain(points);
			shape = cShape;
	}
		return shape;

	}
	
	private static Fixture createFixture(String cfgProfileName, Body body, String index){
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

	public static ArrayList<Fixture> createFixtures(String cfgProfileName, Body body){
		ArrayList<Fixture> fixtureList = new ArrayList<Fixture>();
		if(cfg.hasProperty("FIXTURE_COUNT@" + cfgProfileName)){
			int count = Integer.parseInt(cfg.getProperty("FIXTURE_COUNT@" + cfgProfileName));
			for(int i=1; i<=count; i++){
				Fixture f = createFixture(cfgProfileName, body, "_"+i);
				if(f != null){ fixtureList.add(f); }
			}
		} else{
			fixtureList.add(createFixture(cfgProfileName, body, ""));
		}
		return fixtureList;
	}
	
	public static Sprite createSprite(String cfgProfileName){
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
	

	//Map Loader
	public static ArrayList<Model> tiledMapLoader(TiledMapTileLayer tileLayer, World world, String cfgProfileName, String additionalTags){
		ArrayList<Model> models = new ArrayList<Model>();
		float tileHeight = tileLayer.getTileHeight();
		float tileWidth = tileLayer.getTileWidth();
		for(int row = 0; row < tileLayer.getHeight(); row++){

			for(int col = 0; col < tileLayer.getWidth(); col++){
				Cell cell = tileLayer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;

				Model g = new Model(world, cfgProfileName);
				g.getBody().setTransform((col+0.5f)*tileWidth/PPM, (row+0.5f)*tileHeight/PPM, 0);
				if(additionalTags != null){ g.addTag(additionalTags); }
				models.add(g);
			}
		}
		
		return models;
	}
	
	
	//Property Accessor
	public static String getProperty(String id){
		return cfg.getProperty(id);
	}
}
