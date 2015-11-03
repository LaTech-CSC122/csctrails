package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.World;

public class Spring extends Model {
	
	//TODO: Decide if this class is necessary
	
	public Spring(World world, int xpos, int ypos)
	{
		super(world, "MODEL:SPRING");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
	}

	
}