package grandtheftroster.player;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.MyInput;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Climbing extends Activity{

	private static final int MOVING_VERTICAL = 0;
	private static final int MOVING_LEFT = 1;
	private static final int MOVING_RIGHT = 2;
	private static final int STILL = 3;
	
	private final float CLIMB_SPEED = 0.01f;
	
	private int contacts;
	
	public Climbing(Player player){
		super(player);
		loadAnimation();
		contacts = 0;
	}


	@Override
	public void begin() {
		super.begin();
		System.out.println("Activity: Climbing");
		body.setGravityScale(0f);
		body.setLinearVelocity(0f, 0f);
		
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){
			state = MOVING_LEFT;
		}
		else if(MyInput.isDown(MyInput.BUTTON_LEFT)){
			state = MOVING_LEFT;
		}
	}


	@Override
	public void end() {
		super.end();
		body.setGravityScale(1f);
	}


	public void update(float dt) {
		
		handleInput();
		handleState(dt);
	}



	public void dispose() {}


	protected void handleInput() {
		if(MyInput.isDown(MyInput.BUTTON_UP)){
			climbUp();
		}
		if(MyInput.isDown(MyInput.BUTTON_DOWN)){
			climbDown();
		}
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){
			climbLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			climbRight();
		}
	}


	@Override
	protected void handleState(float dt) {
		am.setState(state);
		if(state == MOVING_LEFT){
			am.resetAnimation();
		}
		else if(state == MOVING_RIGHT){
			am.resetAnimation();
		}
		else if(state == MOVING_VERTICAL){
			am.update(dt);
			state = STILL;
		}
		else if(state == STILL){
			am.resetAnimation();
		}
		
	}


	@Override
	public void handleBeginContact(Model model) {
		if(model.hasTag("ladder")) contacts++;
		if(model.hasTag("fan")) player.actMan.setActivity(player.ACTIVITY_HOVERING);
	}


	@Override
	public void handleEndContact(Model model) {
		if(model.hasTag("ladder")){
			contacts --;
			if(contacts==0 && active==true){ 
				player.actMan.setActivity(player.ACTIVITY_WALKING);
			}
		}
		
	}
	
	
	private void climbUp(){
		state = MOVING_VERTICAL;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x, pos.y+CLIMB_SPEED, 0);
	}
	private void climbDown(){
		state = MOVING_VERTICAL;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x, pos.y-CLIMB_SPEED, 0);
	}
	private void climbLeft(){
		state = MOVING_LEFT;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-CLIMB_SPEED, pos.y, 0);
	}
	private void climbRight(){
		state = MOVING_RIGHT;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+CLIMB_SPEED, pos.y, 0);
	}
	
	private void loadAnimation(){

		Animation climb = AnimationManager.createAnimation(0.1f, 32, 32, false, false, 
				cfg.getProperty("JDK_CLIMBING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(climb,STILL);
		am.addAnimation(climb,MOVING_VERTICAL);
		
		
		Animation left = AnimationManager.createAnimation(0.1f, 32, 32, true, false, 
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(left,MOVING_LEFT);
		
		Animation right = AnimationManager.createAnimation(0.1f, 32, 32, false, false, 
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(right,MOVING_RIGHT);
		
		am.setState(STILL);
		am.resetAnimation();
	}
}
