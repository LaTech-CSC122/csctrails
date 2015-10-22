package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.World;

public class Switch extends Model {
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
