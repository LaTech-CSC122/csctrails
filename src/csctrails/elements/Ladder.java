package csctrails.elements;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.physics.box2d.World;

public class Ladder{
	
	public static ArrayList<Model> loadLadders(World world, TiledMapTileLayer tmtl, String[] additionalTags){
		ArrayList<Model> ladders = new ArrayList<Model>();
		float tileWidth = tmtl.getTileWidth();
		float tileHeight = tmtl.getTileHeight();
		
		for(int row = 0; row < tmtl.getHeight(); row++){
			for(int col = 0; col < tmtl.getWidth(); col++){
				Cell cell = tmtl.getCell(col, row);
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				Model l = new Model(world, "MODEL:LADDER", (col+0.5f)*tileWidth, (row+0.5f)*tileHeight);
				if(additionalTags != null){ l.addTags(additionalTags); }
				ladders.add(l);
			}
		}
		
		return ladders;
	}
}
