package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;


/**
 * All platforms that exist in the game
 * 
 * Change Log:
 * 15.09.28 gha: First Edition
 *
 */

public class Platform extends Model{

	public static final String[] DEFAULT_TAGS= {"modle", "platform"};

	public static ArrayList<Platform> loadPlatforms(World world, TiledMapTileLayer tileLayer, String[] additionalTags){
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		float tileHeight = tileLayer.getTileHeight();
		float tileWidth = tileLayer.getTileWidth();
		for(int row = 0; row < tileLayer.getHeight(); row++){

			for(int col = 0; col < tileLayer.getWidth(); col++){
				Cell cell = tileLayer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;

				
				Platform p = new Platform(world, (int)((col+0.5)*tileWidth), (int)((row+0.5)*tileHeight), 
						(int)tileWidth, (int)(tileHeight/2));
				if(additionalTags != null){ p.addTags(additionalTags); }
				p.addTag("ground");
				platforms.add(p);
				p = new Platform(world, (int)((col+0.5)*tileWidth), (int)((row+0.5)*tileHeight), 
						(int)tileWidth, (int)(tileHeight/2-1));
				if(additionalTags != null){ p.addTags(additionalTags); }
				p.addTag("ceiling");
				platforms.add(p);
			}
		}
		
		return platforms;
	}
	
	public Platform(World world, int xpos, int ypos, int width, int height) {
		super(world, "");
		
		//Add Tags
		addTags(DEFAULT_TAGS);
		
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(xpos/PPM, ypos/PPM);
		bdef.type = B2DVars.STATIC;

		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(-width/2/PPM, height/PPM);
		v[1] = new Vector2(width/2/PPM, height/PPM);
		shape.createChain(v);
		//TODO: add base to platform. If player hits head, he can collide with a ball
		FixtureDef fdef = new FixtureDef();
		fdef.friction= 0f;
		fdef.shape = shape;
		
		body = world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef);
		
	}

}
