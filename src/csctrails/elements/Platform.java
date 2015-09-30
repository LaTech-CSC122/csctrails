package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


/**
 * All platforms that exist in the game
 * 
 * Change Log:
 * 15.09.28 gha: First Edition
 *
 */

public class Platform extends Model{

	private static final int PLATFORM_HEIGHT = 16;
	private static final int PLATFORM_WIDTH = 32;

	public static ArrayList<Platform> loadPlatforms(World world, TiledMapTileLayer tileLayer){
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		float tileHeight = tileLayer.getTileHeight();
		float tileWidth = tileLayer.getTileWidth();
		for(int row = 0; row < tileLayer.getHeight(); row++){

			for(int col = 0; col < tileLayer.getWidth(); col++){
				Cell cell = tileLayer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				platforms.add(new Platform(world, "platform:(" + col + "," + row + ")", 
						(int)((col+0.5)*tileWidth), (int)((row+1)*tileHeight-PLATFORM_HEIGHT/2), // position (align to center top)
						PLATFORM_WIDTH, PLATFORM_HEIGHT)); // size
			}
		}
		
		return platforms;
	}
	
	public Platform(World world, String title, int xpos, int ypos, int xwidth, int xheight) {
		super(null, null, title);
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(xpos/PPM, ypos/PPM);
		bdef.type = B2DVars.STATIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(xwidth/2/PPM, xheight/2/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.friction= 1f;
		fdef.shape = shape;
		
		this.body = world.createBody(bdef);
		this.body.createFixture(fdef);
		
		
	}

}
