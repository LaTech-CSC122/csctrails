package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;
import com.badlogic.gdx.physics.box2d.World;

public class Fan extends Model {
	
	public Fan(World world, int xpos, int ypos, int height)
	{
		super(world, "MODEL:FAN");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
	}

}
