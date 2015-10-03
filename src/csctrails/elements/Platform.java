package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.World;


/**
 * All Models that exist in the game
 * 
 * Change Log:
 * 15.09.28 gha: First Edition
 *
 */

public class Platform{

	public static ArrayList<Model> loadPlatforms(World world, TiledMapTileLayer tileLayer, String[] additionalTags){
		ArrayList<Model> Models = new ArrayList<Model>();
		float tileHeight = tileLayer.getTileHeight();
		float tileWidth = tileLayer.getTileWidth();
		for(int row = 0; row < tileLayer.getHeight(); row++){

			for(int col = 0; col < tileLayer.getWidth(); col++){
				Cell cell = tileLayer.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;

				
				Model g = new Model(world, "MODEL:PLATFORM_GROUND");
				g.getBody().setTransform((col+0.5f)*tileWidth/PPM, (row+0.5f)*tileHeight/PPM, 0);
				if(additionalTags != null){ g.addTags(additionalTags); }
				g.addTag("ground");
				Models.add(g);
				
				Model c = new Model(world, "MODEL:PLATFORM_CEILING");
				c.getBody().setTransform((col+0.5f)*tileWidth/PPM, (row+0.5f)*tileHeight/PPM, 0);
				if(additionalTags != null){ c.addTags(additionalTags); }
				c.addTag("ceiling");
				Models.add(c);
			}
		}
		
		return Models;
	}
}
