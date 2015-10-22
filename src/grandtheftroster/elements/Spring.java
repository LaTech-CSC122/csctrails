package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Spring extends Model {
	
	//private float length;
	
	public Spring(World world, int xpos, int ypos)
	{
		super(world, "MODEL:SPRING");
		//this.length = length/PPM;
		//createBody(world, xpos, ypos, length);
		//tags.add("MODEL,SPRING");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
	}

	
	private void createBody(World world, int xpos, int ypos, int length){
		BodyDef bdef = new BodyDef();
		bdef.type = B2DVars.STATIC;
		//bdef.position.set(0, -length/2/PPM);
		
		
		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0,0);
		v[1] = new Vector2(0,-length/PPM);
		shape.createChain(v);
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		
		body = world.createBody(bdef);
		body.createFixture(fdef);
		body.setUserData(this);
	}
	
	//public float getLength(){
		//return length;
	//}


	
}