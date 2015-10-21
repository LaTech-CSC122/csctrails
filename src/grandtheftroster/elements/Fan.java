package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Fan extends Model {
	
	public Fan(World world, int xpos, int ypos, int height)
	{
		super(world, "");
		createBody(world, xpos, ypos, height);
		tags.add("MODEL,FAN");
		body.setTransform(xpos/PPM, (ypos+height/2)/PPM, 0);
	}

	
	private void createBody(World world, int xpos, int ypos, int height){
		BodyDef bdef = new BodyDef();
		bdef.type = B2DVars.STATIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0, height/2/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		
		body = world.createBody(bdef);
		body.createFixture(fdef);
		body.setUserData(this);
	}
}
