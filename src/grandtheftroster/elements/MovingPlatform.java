package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovingPlatform extends Model implements MovingElement {
	private float time;
	private float speed;
	private int range;
	private int center;
	private static boolean G = false;
	public MovingPlatform(World world, int width, int range, float speed, int xpos, int ypos) {
		super(world, "");
		this.speed = speed;
		this.center = xpos-Math.abs(range/2);
		this.range = range;
		
		createBody(world,width, xpos, ypos);
		addTag("MODEL,MOVING_PLATFORM,GROUND");
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
			//fdef.isSensor = true;
			
			body = world.createBody(bdef);
			body.createFixture(fdef);
			body.setUserData(this);
		
		}
	public void setLocation()
	{ 
		float xpos = (float) ((range/2/PPM)*Math.cos(time*speed))+center/PPM;
		float ypos = body.getPosition().y; 
		body.setTransform(xpos, ypos,0);
	}
	
	public  void update(float dt)
	{
		time+=dt;
		setLocation();
		pTouched();
	}
	@Override
	public float getChangeX(float dt) {
		
		float velocity = (float) -((range/2/PPM)*Math.sin(time*speed))*speed;
		return velocity*dt;
	}
	@Override
	public float getChangeY(float dt) {
		
		return 0;
	}
	
	public void pTouched()
	{
		
		if(G == true){
			body.setActive(true);
			
			
		}
		else
		{
			body.setActive(false);
			
		}
	}


	public static void iTouch(boolean isTouched) {
		// TODO Auto-generated method stub
		G = true;
		
		
	}

}
