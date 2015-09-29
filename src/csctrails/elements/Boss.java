package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Boss extends Model{
	private int textureHeight;
	private int textureWidth;
	
	public Boss(World world, int xpos, int ypos) {
		super(null, null, "filler");
		BodyDef BD = new BodyDef();
		BD.position.set(xpos/PPM, ypos/PPM);
		BD.type=B2DVars.DYNAMIC;
		PolygonShape Bboss = new PolygonShape();
		Bboss.setAsBox(5/PPM, 5/PPM);
		FixtureDef FD = new FixtureDef();
		FD.shape = Bboss;
		this.body = world.createBody(BD);
		this.body.createFixture(FD);
		
	}

}
