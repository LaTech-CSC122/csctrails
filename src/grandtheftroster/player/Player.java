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
	final Activity ACTIVITY_WALKING = new Walking(this);
	final Activity ACTIVITY_CLIMBING = new Climbing(this);
	final Activity ACTIVITY_HOVERING = new Hovering(this);
	final Activity ACTIVITY_SWINGING = new Swinging(this);
	boolean isAlive;
	ActivityManager actMan;
	
	public Player(World world, String cfgProfileName, int xpos, int ypos) {
		super(world, cfgProfileName, xpos, ypos);
		isAlive = true;
		actMan = new ActivityManager();
		actMan.setActivity(ACTIVITY_WALKING);
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
		//do any handleing needed in player
		//if(actMan.getActivity()==ACTIVITY_WALKING && model.hasTag("ladder")){
		//	actMan.setActivity(ACTIVITY_CLIMBING);
		//}
		//actMan.getActivity().handleBeginContact(model);
	}
	public void handleEndContact(Model model){
		//Let activities know of contact
		ACTIVITY_CLIMBING.handleEndContact(model);
		ACTIVITY_WALKING.handleEndContact(model);
		ACTIVITY_HOVERING.handleEndContact(model);
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
	}
	public void revive(){
		isAlive = true;
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




