package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import csctrails.main.Paths;

public class Thrown extends Model {
	private static final String[] DEFAULT_TAGS = {"model", "thrown"};
	private static final float DEFAULT_SPEED = 0.45f; //0.45f
	private static final int DEFAULT_RADIUS = 7;
	
	
	private int groundContact;
	private Thrower thrower;

	public Thrown(World world, int xpos, int ypos) {
		super();
		//tags
		addTags(DEFAULT_TAGS);
		
		//Sprites
		Texture tex = new Texture(Paths.SPRITE_THROWN_1);
		textureHeight = tex.getHeight();
		textureWidth = tex.getWidth();
		sprite = new Sprite(tex);
		
		//Body
		BodyDef bdef = new BodyDef();
		bdef.position.set(xpos/PPM, ypos/PPM);
		bdef.type = B2DVars.DYNAMIC;
		//Shape
		CircleShape shape = new CircleShape();
		shape.setRadius(DEFAULT_RADIUS/PPM);
		//Fixture
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0f;
		//Creation
		body = world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef);
		
		//Initialize Field Variables
		groundContact = 0;
	}
	
	public void setThrower(Thrower t){
		thrower = t;
	}
	public Thrower getThrower(){
		return thrower;
	}
	
	public void pushRight(){
		body.setLinearVelocity(DEFAULT_SPEED, body.getLinearVelocity().y);
		body.setAngularVelocity(-DEFAULT_SPEED*314/(DEFAULT_RADIUS));
	}
	public void pushLeft(){
		body.setLinearVelocity(-DEFAULT_SPEED, body.getLinearVelocity().y);
		body.setAngularVelocity(DEFAULT_SPEED*314/(DEFAULT_RADIUS));
	}
	
	public void addGroundContact(){
		groundContact++;
	}
	public void removeGroundConact(){
		groundContact--;
		if(groundContact <= 0){
			groundContact = 0;
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
	}
	public int getGroundContact(){ return groundContact; }

	public boolean destory(){
		if(!super.destory()){ return false; }
		
		if(thrower != null){
			thrower.removeObject(this);
		}
		return true;
	}
}
