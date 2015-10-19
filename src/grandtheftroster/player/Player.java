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
		cfg.loadConfiguration("res/config/paths/audio paths.config");
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
	
	private AnimationManager am;
	
	private SoundBox sounds;
	private static final String SOUND_JUMP = "jump";
	private static final String SOUND_HIT = "hit";
	
	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		groundContacts = 0;
		ladderContacts = 0;
		isAlive = true;
		state = 0;
		loadConfigurationParameters(cfgProfileName);
		loadAnimations();
		loadSounds();
	
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
		handleInput();
		updateAnimation(dt);	
	}
	
	public void draw(SpriteBatch sb){
		Vector2 posBody = body.getPosition();
		float xpos = posBody.x*PPM - currentFrame.getRegionWidth()/2;
		float ypos = posBody.y*PPM - currentFrame.getRegionHeight()/2;
		//sb.draw(currentFrame, xpos, ypos);
		am.draw(sb, xpos, ypos);
		
	}
	
	//Getters and Mutators
	public boolean isAlive(){ return isAlive; }
	public void kill(){ 
		isAlive = false;
		sounds.play(SOUND_HIT);
	}
	public void revive(){
		isAlive = true;
		state = STANDING_RIGHT;
	}



	  //------------------------\\
	 //PRIVATE METHODS STATR HERE\\
	//----------------------------\\

	private void handleInput(){
		if(MyInput.isDown(MyInput.BUTTON_LEFT))
		{ 
			moveLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			moveRight();
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP))
		{
			if(climbUp());
			else if(jump()){
				sounds.play(SOUND_JUMP);
			}
			
		}
		if(MyInput.isDown(MyInput.BUTTON_UP))
		{
			climbUp();
		}
		if(MyInput.isDown(MyInput.BUTTON_DOWN))
		{
			climbDown();
		} 
		
	}
	private void updateAnimation(float dt){
		if(state==MOVE_LEFT){
			state = STANDING_LEFT;
			am.setState(MOVE_LEFT);
			am.update(dt);
		}
		else if(state==MOVE_RIGHT){
			state = STANDING_RIGHT;
			am.setState(MOVE_RIGHT);
			am.update(dt);
		}
		else if(state==STANDING_RIGHT){
			am.setState(MOVE_RIGHT);
			am.resetAnimation();
		}
		else if(state==STANDING_LEFT){
			am.setState(MOVE_LEFT);
			am.resetAnimation();
		}
		else if(state==CLIMBING_UP || state == CLIMBING_DOWN){
			am.setState(CLIMBING_UP);
			am.update(dt);
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

		am = new AnimationManager();
		am.addAnimation(AnimationManager.createAnimation(runningSpeed, 32, 32, false, false,
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES")),MOVE_RIGHT);
		am.addAnimation(AnimationManager.createAnimation(runningSpeed, 32, 32, true, false,
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES")),MOVE_LEFT);
		am.addAnimation(AnimationManager.createAnimation(runningSpeed, 32, 32, false, false,
				cfg.getProperty("JDK_CLIMBING" + "@" + "PATHS:SPRITES")),CLIMBING_UP);
		am.setState(MOVE_RIGHT);
		am.resetAnimation();
	}
	private void loadSounds(){
		sounds = new SoundBox();
		String path = cfg.getProperty("JUMPING@PATHS:AUDIO");
		sounds.addSound(SOUND_JUMP, path);
		path = cfg.getProperty("HIT@PATHS:AUDIO");
		sounds.addSound(SOUND_HIT, path);
	}
}




