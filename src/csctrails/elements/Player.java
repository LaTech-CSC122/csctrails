package csctrails.elements;


import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.configuration.Configuration;
import csctrails.handlers.MyInput;

/**
 * WARNING: Class is incomplete and not fully tested
 * 
 * Change Log:
 *   .  .  gha: First Edition
 * 15.09.29gha: Moved getSprite() and vars from sub to super
 */

public class Player extends Model {
	
	//Player States
	private static int MOVE_LEFT = 1;
	private static int MOVE_RIGHT = 2;
	private static int STANDING_LEFT = 3;
	private static int STANDING_RIGHT = 4;
	private static int CLIMBING_UP = 5;
	private static int CLIMBING_DOWN = 6;
	private static int CLIMBING_STILL = 7;
	
	private static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models/player.config");
		cfg.loadConfiguration("res/config/paths/sprite paths.config");
	}
	
	private float runSpeed;
	private float climbSpeed;
	private float jumpHeight;
	private int groundContacts;
	private int ladderContacts;
	private boolean isAlive;

	
	private int state;
	private float stateTime;
	private Animation runRightAnimation;
	private Animation runLeftAnimation;
	private Animation climbingAnimation;
	private Animation currentAnimation;
	private TextureRegion currentFrame;

	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		groundContacts = 0;
		ladderContacts = 0;
		isAlive = true;
		state = 0;
		loadConfigurationParameters(cfgProfileName);
		loadAnimations();	
	
	}
	
	
	//Motion Methods
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
	
	//Important
	public void update(float dt){
		// HANDLE INPUT
		handleInput();
		updateAnimation(dt);
		
		//UPDATE ANIMATION
		
	}
	
	public void draw(SpriteBatch sb){
		Vector2 posBody = body.getPosition();
		float xpos = posBody.x*PPM - currentFrame.getRegionWidth()/2;
		float ypos = posBody.y*PPM - currentFrame.getRegionHeight()/2;
		sb.draw(currentFrame, xpos, ypos);
	}
	
	//Getters and Mutators
	public boolean isAlive(){ return isAlive; }
	public void setIsAlive(boolean l){ isAlive = l; }




	  //------------------------\\
	 //PRIVATE METHODS STATR HERE\\
	//----------------------------\\

	private void handleInput(){
		if(MyInput.isDown(MyInput.BUTTON_LEFT) && !MyInput.isDown(MyInput.BUTTON_RIGHT)){
			moveLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT) && !MyInput.isDown(MyInput.BUTTON_LEFT)){
			moveRight();
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			if(climbUp());
			else if(jump());
		}
		if(MyInput.isDown(MyInput.BUTTON_UP)){
			climbUp();
		}
		if(MyInput.isDown(MyInput.BUTTON_DOWN)){
			climbDown();
		}
	}
	private void updateAnimation(float dt){
		if(state==MOVE_LEFT){
			stateTime += dt;
			currentAnimation = runLeftAnimation;
			state = STANDING_LEFT;
		}
		else if(state==MOVE_RIGHT){
			stateTime += dt;
			currentAnimation = runRightAnimation;
			state = STANDING_RIGHT;
		}
		else if(state==STANDING_RIGHT){
			stateTime = 0;
			currentAnimation = runRightAnimation;
		}
		else if(state==STANDING_LEFT){
			stateTime = 0;
			currentAnimation = runLeftAnimation;
		}
		else if(state==CLIMBING_UP || state == CLIMBING_DOWN){
			stateTime += dt;
			currentAnimation = climbingAnimation;
			state = CLIMBING_STILL;
		}
		else if(state == CLIMBING_STILL){
			currentAnimation = climbingAnimation;
		}
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}
	
	private void loadConfigurationParameters(String cfgProfileName){
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
	private void loadAnimations(){
		float runningSpeed = 0.11f;
		float climbingSpeed = 0.1f;
		TextureRegion[] frameArray;
		Texture runSheet;
		//Load Running Right
		runSheet= new Texture(cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		frameArray = createFrameArray(runSheet, 32, 32, false, false);
		runRightAnimation = new Animation(runningSpeed, frameArray);
		//Load Running Left
		frameArray = createFrameArray(runSheet, 32, 32, true, false);
		runLeftAnimation = new Animation(runningSpeed, frameArray);
		//Load Climbing
		runSheet = new Texture(cfg.getProperty("JDK_CLIMBING" + "@" + "PATHS:SPRITES"));
		frameArray = createFrameArray(runSheet, 32, 32, false, false);
		climbingAnimation= new Animation(climbingSpeed, frameArray);
		
		//Set starting pointers
		currentAnimation = runRightAnimation;
		currentFrame = currentAnimation.getKeyFrame(0f);
	}
	private TextureRegion[] createFrameArray(Texture spriteSheet, int width, int height, boolean flipX, boolean flipY){
		TextureRegion[][] frameMatrix = TextureRegion.split(spriteSheet, width, height);
		TextureRegion[] frameArray = new TextureRegion[frameMatrix.length*frameMatrix[0].length];
		int index = 0;
		for(int i=0; i < frameMatrix.length; i++){
			for(int j=0; j<frameMatrix[0].length; j++){
				frameMatrix[i][j].flip(flipX, flipY);
				frameArray[index] = frameMatrix[i][j];
				index++;
			}
		}
		return frameArray;
	}

}




