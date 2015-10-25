package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovingPlatform extends Model {
	private float time;
	private float speed;
	private int range;
	public MovingPlatform(World world, int width, int range, float speed, int xpos, int ypos) {
		super(world, "");
		this.speed = speed;
		this.time = time;
		this.range = range;
		
		createBody(world,width, xpos, ypos);
		setPosition(xpos,ypos,0);
	}
		private void createBody(World world, int width, int xpos, int ypos){
			BodyDef bdef = new BodyDef();
			bdef.type = B2DVars.STATIC;
			//bdef.position.set(0, -length/2/PPM);
			
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width/2/PPM, 6/PPM);
			
			
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.isSensor = true;
			
			body = world.createBody(bdef);
			body.createFixture(fdef);
			body.setUserData(this);
		}
	public void setLocation()
	{ 
		float xpos = (float) ((range/2/PPM)*Math.sin(time*speed))+body.getPosition().x;
		float ypos = body.getPosition().y; 
		body.setTransform(xpos, ypos,0);
	}
	
	public  void update(float dt)
	{
		time+=dt;
		setLocation();
	}

}
