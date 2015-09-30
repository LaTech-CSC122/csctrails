package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.main.Game;

public class Ladder extends Model {

	public Ladder (World world, int xpos, int ypos) {
		super(null, null, "ladder");
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		bdef.position.set(xpos/PPM, ypos/PPM);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8/PPM, 16*3/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		body = world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef).setUserData("ladder");
	}

}
