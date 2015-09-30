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
 * 
 * Change Log:
 *   .  .  gha: First Edition
 * 15.09.29gha: Moved getSprite() and vars from sub to super
 */

public class Player extends Model {

	private int groundContacts;
	private int ladderContacts;
	private boolean dirFacing;
	private float speed;
	private final float DEFAULT_SPEED = 0.02f;
	private final int JUMP_HEIGHT = 150;
	

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
		this.body.setUserData(this);
		this.body.createFixture(fdef).setUserData("player_body");
		
		//Create foot sensor (this is for ground detection) - gha 15.9.25
		ChainShape cs = new ChainShape();
		Vector2[] v = new Vector2[2];
		v[0] = new Vector2(0/PPM, 0/PPM);
		v[1] = new Vector2(0/PPM, (-textureHeight/2-5) /PPM);
		cs.createChain(v);
		fdef = new FixtureDef();
		fdef.isSensor = true;
		fdef.shape = cs;
		fdef.filter.maskBits = -1;
		Fixture fix = this.body.createFixture(fdef);
		fix.setUserData("player_foot");
		
		//Field Variable Initialization
		groundContacts = 0;
		dirFacing = true;
		speed = DEFAULT_SPEED;
		ladderContacts = 0;
	}
	
	public void moveLeft(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-speed, pos.y, 0);
		if(dirFacing != false){
			sprite.flip(true, false);
			dirFacing = false;
		}
	}	
	public void moveRight(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+speed, pos.y, 0);
		sprite.flip(false, false);
		if(dirFacing != true){
			sprite.flip(true, false);
			dirFacing = true;
		}
	}
	public boolean jump(){
		if(groundContacts > 0){
			body.applyForceToCenter(0, JUMP_HEIGHT, false);
			groundContacts = 0;
			return true;
		}
		else{
			return false;
		}
	}
	public boolean climbUp(){
		if(ladderContacts > 0){
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y+speed, 0);
			return true;
		}
		else{
			return false;
		}
	}
	public boolean climbDown(){
		if(ladderContacts > 0){
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y-speed, 0);
			return true;
		}
		else{
			return false;
		}
	}
	
	public void addGroundContact(){
		groundContacts ++;
	}
	public void removeGroundContact(){
		groundContacts --;
		if(groundContacts<0){ groundContacts = 0; }
	}
	public int getGroundContacts(){
		return groundContacts;
	}

	public void addLadderContact(){
		ladderContacts ++;
		body.setGravityScale(0f);
	}
	public void removeLadderContact(){
		ladderContacts--;
		if(ladderContacts <= 0){
			ladderContacts = 0;
			body.setGravityScale(1f);
		}
	}
	public int getLadderContact(){
		return ladderContacts;
	}
}
