package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Rope extends Model {
	
	private float length;
	
	public Rope(World world, int xpos, int ypos, int length)
	{
		super(world, "MODEL:ROPE");
		this.length = length/PPM;
		createBody(world, xpos, ypos, length);
		tags.add("MODEL,ROPE");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
	}

	
	private void createBody(World world, int xpos, int ypos, int length){
		BodyDef bdef = new BodyDef();
		bdef.type = B2DVars.STATIC;
		
		
		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0,0);
		v[1] = new Vector2(0,-length/PPM);
		shape.createChain(v);
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		
		body = world.createBody(bdef);
		body.createFixture(fdef);
		body.setUserData(this);
	}
	
	public float getLength(){
		return length;
	}
	
	public void draw(SpriteBatch sb){ 
		if(sprite == null) return;
		float angle =  body.getAngle();
		//Original Position
		Vector2 pos = new Vector2(body.getPosition().x*PPM, body.getPosition().y*PPM);
		//Texture Offset
		pos.sub(textureWidth/2, textureHeight);
		//Rotational Offset to reset origin
		pos.add(textureHeight/2*(float)Math.sin(angle), textureHeight/2*(1-(float)Math.cos(angle)));
		
		sprite.setPosition(pos.x, pos.y);
		sprite.setRotation((float) Math.toDegrees(body.getAngle()));
		sprite.draw(sb);
	}
	
}