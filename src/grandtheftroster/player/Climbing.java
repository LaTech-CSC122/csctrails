package grandtheftroster.player;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.MyInput;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Climbing extends Activity{

	private static final int MOVING_VERTICAL = 0;
	private static final int STILL = 1;
	private static final int MOVING_HOROZONTAL = 2;
	
	private final float CLIMB_SPEED = 0.01f;
	
	private int contacts;
	
	public Climbing(Player player){
		super(player);
		loadAnimation();
		contacts = 0;
	}


	@Override
	public void begin() {
		System.out.println("Activity: Climbing");
		body.setGravityScale(0f);
		body.setLinearVelocity(0f, 0f);
	}


	@Override
	public void end() {
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
		if(state == MOVING_HOROZONTAL){
			am.resetAnimation();
		}
		else if(state == STILL){
			am.resetAnimation();
		}
		else if(state == MOVING_VERTICAL){
			am.update(dt);
			state = STILL;
		}
		
	}


	@Override
	public void handleBeginContact(Model model) {
		if(model.hasTag("ladder")) contacts++;
		
	}


	@Override
	public void handleEndContact(Model model) {
		if(model.hasTag("ladder")){
			contacts --;
			if(contacts==0){ 
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
		state = MOVING_HOROZONTAL;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-CLIMB_SPEED, pos.y, 0);
	}
	private void climbRight(){
		state = MOVING_HOROZONTAL;
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+CLIMB_SPEED, pos.y, 0);
	}
	
	private void loadAnimation(){

		Animation right = AnimationManager.createAnimation(0.1f, 32, 32, false, false, 
				cfg.getProperty("JDK_CLIMBING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(right,STILL);
		am.addAnimation(right,MOVING_HOROZONTAL);
		am.addAnimation(right,MOVING_VERTICAL);
		
		am.setState(STILL);
		am.resetAnimation();
	}
}