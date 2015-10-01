package csctrails.elements;


import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.main.Paths;

/**
 * WARNING: Class is incomplete and not fully tested
 * 
 * Change Log:
 *   .  .  gha: First Edition
 * 15.09.29gha: Moved getSprite() and vars from sub to super
 */

public class Player extends Model {
	private final String[] DEFAULT_TAGS = {"model", "player"};
	private final int DEFAULT_WIDTH = 16;
	private final int DEFAULT_HEIGHT = 20;
	private final float DEFAULT_SPEED = 0.01f;
	private final float DEFAULT_JUMP_HEIGHT = 1.5f;
	
	private int groundContacts;
	private int ladderContacts;
	private boolean dirFacing;
	private float speed;
	private boolean isAlive;

	public Player(World world, int xpos, int ypos) {
		super();
		
		//Add Tags
		addTags(DEFAULT_TAGS);
		
		//Create Sprite
		Texture tex = new Texture(Paths.SPRITE_MAN_STANDING);
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
		((PolygonShape) shape).setAsBox(DEFAULT_WIDTH/ 2/PPM, DEFAULT_HEIGHT / 2 /PPM);
		//3. FixtureDef
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1f;
		//4. Put it all together
		body = world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef);
		
		PolygonShape polyFoot = new PolygonShape();
		Vector2[] vp = new Vector2[4];
		vp[0] = new Vector2(-1/PPM, (-DEFAULT_HEIGHT/2+5)/PPM);
		vp[1] = new Vector2(-1/PPM, (-DEFAULT_WIDTH/2-7) /PPM);
		vp[2] = new Vector2(1/PPM, (-DEFAULT_WIDTH/2-7) /PPM);
		vp[3] = new Vector2(1/PPM, (-DEFAULT_WIDTH/2+5) /PPM);
		polyFoot.set(vp);
		
		fdef = new FixtureDef();
		fdef.isSensor = true;
		fdef.shape = polyFoot;
		Fixture fix = this.body.createFixture(fdef);
		fix.setUserData("player_foot");
		
		//Field Variable Initialization
		groundContacts = 0;
		dirFacing = true;
		speed = DEFAULT_SPEED;
		ladderContacts = 0;
		isAlive = true;
	}
	
	public void moveLeft(){
		body.setAwake(true);
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-speed, pos.y, 0);
		if(dirFacing != false){
			sprite.flip(true, false);
			dirFacing = false;
		}
	}	
	public void moveRight(){
		body.setAwake(true);
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
			body.applyLinearImpulse(new Vector2(0f, DEFAULT_JUMP_HEIGHT), body.getWorldCenter(), false);
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
		body.setLinearVelocity(0f, 0f);
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
	
	public boolean isAlive(){ return isAlive; }
	public void setIsAlive(boolean l){ isAlive = l; }
}
