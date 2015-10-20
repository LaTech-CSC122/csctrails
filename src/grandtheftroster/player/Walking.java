package grandtheftroster.player;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.MyInput;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Array;

public class Walking extends Activity{
	
	private static final int WALKING_LEFT = 0;
	private static final int WALKING_RIGHT = 1;
	private static final int STANDING_LEFT = 2;
	private static final int STANDING_RIGHT = 3;
	
	
	private static final float RUN_SPEED = 0.01f;
	private static final float JUMP_HEIGHT = 0.6f;
	
	private int contacts;
	
	public Walking(Player player){
		super(player);
		state = STANDING_RIGHT;
		loadAnimations();
	}

	@Override
	public void begin() {
		
		System.out.println("Activity: Walking");
		
		System.out.print("{");
		String[] tags = player.getTags();
		for(int i=0; i<tags.length; i++){
			System.out.print(tags[i]+ ", ");
		}
		System.out.println("}");
		

		
	}
	@Override
	public void end() {}
	@Override
	public void dispose() {}
	

	public void update(float dt) {
		am.setState(state);
		handleInput();
		handleState(dt);	
	}

	@Override
	public void handleInput() {
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){ 
			moveLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			moveRight();
		}
		if(MyInput.isDown(MyInput.BUTTON_UP)){
			jump();
		}
	}

	public void handleBeginContact(Model model){
	}
	public void handleEndContact(Model model){
	}
	
	protected void handleState(float dt){
		if(state == WALKING_LEFT){
			am.update(dt);
			state = STANDING_LEFT;
		}
		else if(state == WALKING_RIGHT){
			am.update(dt);
			state = STANDING_RIGHT;
		}
		else if(state == STANDING_LEFT){
			am.resetAnimation();
		}
		else if(state == STANDING_RIGHT){
			am.resetAnimation();
		}
	}
	
	private void moveLeft(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-RUN_SPEED, pos.y, 0);
		state = WALKING_LEFT;
	}
	private void moveRight(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+RUN_SPEED, pos.y, 0);
		state = WALKING_RIGHT;
	}
	public void jump(){
		if(isContacting("ground")){
			body.applyLinearImpulse(new Vector2(0f, JUMP_HEIGHT), body.getWorldCenter(), false);
		}
	}
	
	
	private void loadAnimations(){
		float runningSpeed = 0.11f;
		
		Animation right = AnimationManager.createAnimation(runningSpeed, 32, 32, false, false, 
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(right,WALKING_RIGHT);
		am.addAnimation(right,STANDING_RIGHT);
		
		Animation left = AnimationManager.createAnimation(runningSpeed, 32, 32, true, false,
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(left,WALKING_LEFT);
		am.addAnimation(left, STANDING_LEFT);
		
		am.setState(STANDING_RIGHT);
		am.resetAnimation();
	}

}
