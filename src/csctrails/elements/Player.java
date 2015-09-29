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

/**
 * WARNING: Class is incomplete and not fully tested
 */

public class Player extends Model {

	private int textureHeight;
	private int textureWidth;
	
	public Player(World world, String name, String type, int xpos, int ypos) {
		super(null, null, name);
		//Create Sprite
		Texture tex = new Texture(type);
		this.textureHeight = tex.getHeight();
		this.textureWidth = tex.getWidth();
		this.sprite = new Sprite(tex);
		
		//Create Box2D Body
		//1. BodyDef
		BodyDef bdef = new BodyDef();
		bdef.position.set(xpos/PPM, ypos/PPM);
		bdef.type = B2DVars.DYNAMIC;
		//2. Shape
		Shape shape = new PolygonShape();
		((PolygonShape) shape).setAsBox(textureWidth/ 2/PPM, textureHeight / 2 /PPM);
		//3. FixtureDef
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1f;
		//4. Put it all together
		this.body = world.createBody(bdef);
		this.body.createFixture(fdef).setUserData("player");
		
		//Create foot sensor (this is for ground detection) - gha 15.9.25
		ChainShape cs = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0/PPM, (-textureHeight)/PPM);
		v[1] = new Vector2(0/PPM, (-textureHeight/2-5) /PPM);
		cs.createChain(v);
		fdef = new FixtureDef();
		fdef.isSensor = true;
		fdef.shape = cs;
		fdef.filter.maskBits = -1;
		Fixture fix = this.body.createFixture(fdef);
		fix.setUserData("player_foot");
		
	}

	public Sprite getSprite() {
		// Update position of sprite before returning
		Vector2 pos = body.getPosition();
		sprite.setPosition(pos.x*PPM - textureWidth/2, pos.y*PPM - textureHeight/2); // TODO: offset to align with Box2D Body - gha 15.9.20
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		return sprite;
	}

}
