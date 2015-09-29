package csctrails.elements;

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
	
	// Category bits for Bit Masking
	// Must be a power of two  - gha 15.9.21
	public static final short BIT_DEFAULT = 1; //0000 0000 0000 0001
	public static final short BIT_GROUND = 2;  //0000 0000 0000 0010
	public static final short BIT_PLAYER = 4;  //0000 0000 0000 0100
	
	// Body Types
	public static BodyDef.BodyType STATIC = BodyDef.BodyType.StaticBody;
	public static BodyDef.BodyType DYNAMIC = BodyDef.BodyType.DynamicBody;
	public static BodyDef.BodyType KINEMATIC = BodyDef.BodyType.KinematicBody;
}
