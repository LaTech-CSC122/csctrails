package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.World;


import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Switch extends Model implements MovingElement {
	private float time;
	private float speed;
	private int range;
	private int center;
	private boolean isOn;
	public Switch(World world, int width, int range, float speed, int xpos, int ypos) {
		super(world, "MODEL:SWITCH");
		this.speed = speed;
		this.center = xpos-Math.abs(range/2);
		this.range = range;
		body.setTransform(xpos/PPM, ypos/PPM, 0);
		isOn = false;
		setPosition(xpos,ypos,0);
	}
	
	
	public boolean isOn()
	{
		return isOn;
		
	}
	public void setState(boolean state)
	{
		isOn = state;
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

}

//old code delete if you wish! keeping for now until opinions heard
/*public class Switch extends Model {
private boolean isOn;
	public Switch(World world, int xpos, int ypos) {
		super(world, "MODEL:SWITCH");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
		isOn = false;
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOn()
	{
		return isOn;
		
	}
	public void setState(boolean state)
	{
		isOn = state;
	}
}
*/
