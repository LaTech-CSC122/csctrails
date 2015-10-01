package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ladder extends Model {

	private static final int LADDER_WIDTH = 0;
	private static final int LADDER_HEIGHT = 32;
	
	public static ArrayList<Ladder> loadLadders(World world, TiledMapTileLayer tmtl, String name){
		ArrayList<Ladder> ladders = new ArrayList<Ladder>();
		float tileWidth = tmtl.getTileWidth();
		float tileHeight = tmtl.getTileHeight();
		
		for(int row = 0; row < tmtl.getHeight(); row++){

			for(int col = 0; col < tmtl.getWidth(); col++){
				Cell cell = tmtl.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				ladders.add(new Ladder(world, (int)((col+0.5)*tileWidth), 
						(int)((row+1)*tileHeight-LADDER_HEIGHT/2), name)); // size
			}
		}
		
		return ladders;
	}
	
	public Ladder (World world, int xpos, int ypos, String name) {
		super(null, null, name);
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		bdef.position.set(xpos/PPM, ypos/PPM);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(LADDER_WIDTH/PPM, LADDER_HEIGHT/2/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body = world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef).setUserData(name);
	}

}
