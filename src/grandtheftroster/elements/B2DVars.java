package grandtheftroster.elements;

import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * B2DVars contains a list constants pertaining to the Box2D World.
 * 
 * Change Log:
 * 15.9.21gha: First Edition
 */

public abstract class B2DVars {
	
	//Pixels per Meter conversion
	public static final float PPM = 200;
	
	// Body Types
	public static BodyDef.BodyType STATIC = BodyDef.BodyType.StaticBody;
	public static BodyDef.BodyType DYNAMIC = BodyDef.BodyType.DynamicBody;
	public static BodyDef.BodyType KINEMATIC = BodyDef.BodyType.KinematicBody;
}
