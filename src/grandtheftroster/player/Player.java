package grandtheftroster.player;


import grandtheftroster.elements.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	public final Activity ACTIVITY_WALKING = new Walking(this);
	public final Activity ACTIVITY_CLIMBING = new Climbing(this);
	public final Activity ACTIVITY_HOVERING = new Hovering(this);
	public final Activity ACTIVITY_SWINGING = new Swinging(this);
	public final Activity ACTIVITY_FLYING = new Flying(this);
	private boolean isAlive;
	private ActivityManager actMan;
	
	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		isAlive = true;
		actMan = new ActivityManager();
		actMan.setActivity(ACTIVITY_WALKING);
	}
	
	public void setActivity(Activity a){
		actMan.setActivity(a);
	}

	//Important
	public void update(float dt){
		actMan.getActivity().update(dt);
	}
	
	
	public void draw(SpriteBatch sb){ actMan.getActivity().draw(sb);}
	
	public void handleBeginContact(Model model){
		//Let activities know of contact
		ACTIVITY_CLIMBING.handleBeginContact(model);
		ACTIVITY_WALKING.handleBeginContact(model);
		ACTIVITY_HOVERING.handleBeginContact(model);
		ACTIVITY_SWINGING.handleBeginContact(model);
		ACTIVITY_FLYING.handleBeginContact(model);
		//do any handleing needed in player
		if(model.hasTag("fatal")){
			kill();
		}
		else if(model.hasTag("redbull")){
			actMan.setActivity(ACTIVITY_FLYING);
		}
	}
	public void handleEndContact(Model model){
		//Let activities know of contact
		ACTIVITY_CLIMBING.handleEndContact(model);
		ACTIVITY_WALKING.handleEndContact(model);
		ACTIVITY_HOVERING.handleEndContact(model);
		ACTIVITY_SWINGING.handleEndContact(model);
		ACTIVITY_SWINGING.handleEndContact(model);
		//do any handleing needed in player		
		
		if(actMan.getActivity()==ACTIVITY_HOVERING && model.hasTag("fan")){
			actMan.setActivity(ACTIVITY_WALKING);
		}
		
		
	}
	
	//Getters and Mutators
	public boolean isAlive(){ return isAlive; }
	public void kill(){ 
		isAlive = false;
		body.setLinearVelocity(0, 0);
		setActivity(ACTIVITY_WALKING);
	}
	public void revive(){
		isAlive = true;
		body.setLinearVelocity(0, 0);
		setActivity(ACTIVITY_WALKING);
	}

	public boolean isFlying(){
		return actMan.activity.equals(ACTIVITY_FLYING);
	}

	
	public class ActivityManager{
		private Activity activity;
		public void setActivity(Activity a){
			if(activity!=null){activity.end(); }
			activity = a;
			activity.begin();
		}
		public Activity getActivity(){ return activity; }
		
	}
	
}




