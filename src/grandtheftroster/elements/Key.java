package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.World;


import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
public class Key extends Model {
private boolean isOn;

	public Key(World world, int xpos, int ypos) {
		super(world, "MODEL:KEY");
		body.setTransform(xpos/PPM, ypos/PPM, 0); // divide by PPM??
		isOn = false;
		body.setActive(false);
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
