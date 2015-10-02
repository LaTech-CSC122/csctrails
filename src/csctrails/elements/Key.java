package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.main.Paths;

public class Key extends Model{
	private final String[] DEFAULT_TAGS = {"model", "key", "obtainable"};
	private final int DEFAULT_WIDTH = 32;
	private final int DEFAULT_HEIGHT = 32;
	
	public Key(World world, int xpos, int ypos) {
		//Add Tags
				addTags(DEFAULT_TAGS);
				
				//Create Sprite
				Texture tex = new Texture(Paths.SPRITE_KEY_1);
				textureHeight = tex.getHeight();
				textureWidth = tex.getWidth();
				sprite = new Sprite(tex);
				
				//Create Box2D Body
				//1. BodyDef
				BodyDef bdef = new BodyDef();
				bdef.position.set(xpos/PPM, ypos/PPM);
				bdef.type = B2DVars.STATIC;
				//2. Shape
				Shape shape = new PolygonShape();
				((PolygonShape) shape).setAsBox(DEFAULT_WIDTH/ 2/PPM, DEFAULT_HEIGHT / 2 /PPM);
				//3. FixtureDef
				FixtureDef fdef = new FixtureDef();
				fdef.shape = shape;
				fdef.isSensor = true;
				//4. Put it all together
				body = world.createBody(bdef);
				body.setUserData(this);
				body.createFixture(fdef);
	}

}
