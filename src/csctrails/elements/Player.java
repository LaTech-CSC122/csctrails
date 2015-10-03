package csctrails.elements;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.configuration.Configuration;

/**
 * WARNING: Class is incomplete and not fully tested
 * 
 * Change Log:
 *   .  .  gha: First Edition
 * 15.09.29gha: Moved getSprite() and vars from sub to super
 */

public class Player extends Model {
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		//cfg.loadConfiguration("res/config/models/player.config");
	}
	
	private float runSpeed;
	private float climbSpeed;
	private float jumpHeight;
	private int groundContacts;
	private int ladderContacts;
	private boolean dirFacing;
	private boolean isAlive;

	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		groundContacts = 0;
		dirFacing = true;
		ladderContacts = 0;
		isAlive = true;

		//Load Configuration Vars
		
		//RUN_SPEED
		if(cfg.hasProperty("RUN_SPEED" + "@" + cfgProfileName)){
			runSpeed = Float.parseFloat(cfg.getProperty("RUN_SPEED" + "@" + cfgProfileName));
		}else{			
			runSpeed = 0.01f;
		}
		
		//CLIMB_SPEED
		if(cfg.hasProperty("CLIMB_SPEED" + "@" + cfgProfileName)){
			climbSpeed = Float.parseFloat(cfg.getProperty("CLIMB_SPEED" + "@" + cfgProfileName));
		}else{
			climbSpeed = 0.01f;
		}
		
		//JUMP_HEIGHT
		if(cfg.hasProperty("JUMP_HEIGHT" + "@" + cfgProfileName)){
			jumpHeight = Float.parseFloat(cfg.getProperty("JUMP_HEIGHT" + "@" + cfgProfileName));
		} else{
			jumpHeight = 1f;
		}
		
	}
	
	public void moveLeft(){
		body.setAwake(true);
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-runSpeed, pos.y, 0);
		if(dirFacing != false){
			sprite.flip(true, false);
			dirFacing = false;
		}
	}	
	public void moveRight(){
		body.setAwake(true);
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+runSpeed, pos.y, 0);
		sprite.flip(false, false);
		if(dirFacing != true){
			sprite.flip(true, false);
			dirFacing = true;
		}
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
