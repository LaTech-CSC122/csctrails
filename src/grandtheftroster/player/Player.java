package grandtheftroster.player;


import grandtheftroster.elements.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


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
		int i = 0;
		Array<Contact> clist = body.getWorld().getContactList();
		for(Contact c:clist){
			if(((Model)c.getFixtureA().getBody().getUserData()).hasTag("player")){
				i++;
			}
			else if(((Model)c.getFixtureB().getBody().getUserData()).hasTag("player")){
				i++;
			}
		}
		System.out.println(i);
	
	}
	
	
	public void draw(SpriteBatch sb){ actMan.getActivity().draw(sb);}
	
	public void handleBeginContact(Model model){
		if(actMan.getActivity()==ACTIVITY_WALKING && model.hasTag("ladder")){
			actMan.setActivity(ACTIVITY_CLIMBING);
		}
		actMan.getActivity().handleBeginContact(model);
	}
	public void handleEndContact(Model model){
		if(actMan.getActivity()==ACTIVITY_CLIMBING && model.hasTag("ground")){
			//actMan.setActivity(ACTIVITY_WALKING);
		}
		actMan.getActivity().handleEndContact(model);
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




