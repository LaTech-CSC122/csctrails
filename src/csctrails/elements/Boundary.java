package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Boundary extends Model{

	private String[] DEFAULT_TAGS = {"model", "boundary"};
	
	public Boundary(World world, int width, int height) {
		super(world, "");
		//add Tags
		addTags(DEFAULT_TAGS);
		
		BodyDef boundaryBodyDef = new BodyDef();
		boundaryBodyDef.type = B2DVars.STATIC;
		boundaryBodyDef.position.set(0,0);
		FixtureDef boundFixDef = new FixtureDef();
		ChainShape shape = new ChainShape();
		body = world.createBody(boundaryBodyDef);
		body.setUserData(this);
		//Boundary left fix 
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0, 0);
		v[1] = new Vector2(0, (height)/PPM);
		shape.createChain(v);
		boundFixDef.shape = shape;
		body.createFixture(boundFixDef);
		//Boundary right fix
		v[0] = new Vector2(width/PPM, 0);
		v[1] = new Vector2(width/PPM, (height)/PPM);
		shape = new ChainShape();
		shape.createChain(v);
		boundFixDef.shape = shape;
		body.createFixture(boundFixDef);
	}

}
