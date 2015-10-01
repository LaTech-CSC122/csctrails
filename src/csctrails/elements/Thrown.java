package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Thrown extends Model {
	private static final String[] DEFAULT_TAGS = {"model", "thrown"};
	private static final float DEFAULT_SPEED = 0.45f;
	private static final ArrayList<Thrown> flagForDelete = new ArrayList<Thrown>();
	
	public static void destroy(){
		for(Thrown currentThrown:flagForDelete){
			Array<Fixture> fList = currentThrown.body.getFixtureList();
			for(Fixture currentFixture:fList){
				currentThrown.body.destroyFixture(currentFixture);
			}
			currentThrown.body.getWorld().destroyBody(currentThrown.body);
			currentThrown.thrower.removeObject(currentThrown);
		}
		flagForDelete.clear();
		
	}
	
	private int groundContact;
	private Thrower thrower;

	public Thrown(World world, int xpos, int ypos) {
		super();
		//tags
		addTags(DEFAULT_TAGS);
		
		//Sprites
		//TODO: Attach sprite
		
		//Body
		BodyDef bdef = new BodyDef();
		bdef.position.set(xpos/PPM, ypos/PPM);
		bdef.type = B2DVars.DYNAMIC;
		//Shape
		CircleShape shape = new CircleShape();
		shape.setRadius(7/PPM);
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
	
	public void flagForDelete(){
		flagForDelete.add(this);
	}
	
	public void pushRight(){
		body.setLinearVelocity(DEFAULT_SPEED, body.getLinearVelocity().y);
	}
	public void pushLeft(){
		body.setLinearVelocity(-DEFAULT_SPEED, body.getLinearVelocity().y);
	}
	
	public void addGroundContact(){
		groundContact++;
	}
	public void removeGroundConact(){
		groundContact--;
		System.out.println(groundContact);
		if(groundContact <= 0){
			groundContact = 0;
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
	}
	public int getGroundContact(){ return groundContact; }
}
