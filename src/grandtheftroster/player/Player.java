package grandtheftroster.player;


import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.elements.Model;
import grandtheftroster.elements.SoundBox;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


/**
 * WARNING: Class is incomplete and not fully tested
 * 
 * Change Log:
 *   .  .  gha: First Edition
 * 15.09.29gha: Moved getSprite() and vars from sub to super
 */

public class Player extends Model{
	
	//Player States
	private final Activity ACTIVITY_WALKING = new Walking(this);
	//private final Activity ACTIVITY_CLIMBING = new Climbing(this);
	
	private int groundContacts;
	private int ladderContacts;
	private boolean isAlive;

	private Activity activity;

	
	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		groundContacts = 0;
		ladderContacts = 0;
		isAlive = true;
		activity = ACTIVITY_WALKING;
	}
	


	//Important
	public void update(float dt){activity.update(dt);}
	
	public void draw(SpriteBatch sb){ activity.draw(sb);}
	
	//Getters and Mutators
	public boolean isAlive(){ return isAlive; }
	public void kill(){ 
		isAlive = false;
	}
	public void revive(){
		isAlive = true;
	}



	  //------------------------\\
	 //PRIVATE METHODS STATR HERE\\
	//----------------------------\\

	/*Motion Methods
	public void moveLeft(){
		body.setAwake(true);
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-runSpeed, pos.y, 0);
		state = MOVE_LEFT;
	}
	
	
	public void moveRight(){
		body.setAwake(true);
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+runSpeed, pos.y, 0);
		state = MOVE_RIGHT;
	}
	public boolean jump(){
		if(groundContacts > 0){
			body.applyLinearImpulse(new Vector2(0f, jumpHeight), body.getWorldCenter(), false);
			return true;
		}
		else{
			return false;
		}
	}
	public boolean climbUp(){
		if(ladderContacts > 0){
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y+climbSpeed, 0);
			state = CLIMBING_UP;
			return true;
		}
		else{
			return false;
		}
	}
	public boolean climbDown(){
		if(ladderContacts > 0){
			Vector2 pos = body.getPosition();
			body.setTransform(pos.x, pos.y-climbSpeed, 0);
			state = CLIMBING_DOWN;
			return true;
		}
		else{
			return false;
		}
	}
	*/
	
	//Contacts
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
	
}




