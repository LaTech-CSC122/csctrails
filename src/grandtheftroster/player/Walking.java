package grandtheftroster.player;

import grandtheftroster.elements.Model;
import grandtheftroster.elements.MovingElement;
import grandtheftroster.elements.Switch;
import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.handlers.MyInput;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Walking extends Activity{
	
	private static final int WALKING_LEFT = 0;
	private static final int WALKING_RIGHT = 1;
	private static final int STANDING_LEFT = 2;
	private static final int STANDING_RIGHT = 3;
	
	
	private static final float RUN_SPEED = 0.01f;
	private static final float JUMP_HEIGHT = 1f;
	private ArrayList<MovingElement> movingElements;
	
	private int contacts;
	
	public Walking(Player player){
		super(player);
		state = STANDING_RIGHT;
		loadAnimations();
		contacts=0;
		movingElements = new ArrayList<MovingElement>();
	}

	@Override
	public void begin() {
		super.begin();
		System.out.println("Activity: Walking");
	}
	@Override
	public void dispose() {}
	

	public void update(float dt) {
		handleInput();
		handleState(dt);
		float xoffset = 0;
		float yoffset = 0;
		for(MovingElement m: movingElements)
		{
			xoffset+= m.getChangeX(dt);
			yoffset+=m.getChangeY(dt);
		}
		body.setTransform(body.getPosition().x+xoffset, body.getPosition().y+yoffset, 0);
	}

	@Override
	public void handleInput() {
		if(MyInput.isDown(MyInput.BUTTON_LEFT)){ 
			moveLeft();
		}
		if(MyInput.isDown(MyInput.BUTTON_RIGHT)){
			moveRight();
		}
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			jump();
		}
	}

	public void handleBeginContact(Model model){
		if(model.hasTag("ground")){ contacts++; }
		if(active && model.hasTag("spring")){body.setLinearVelocity(0, 2);;}
		if(active && model.hasTag("ladder")){ player.setActivity(player.ACTIVITY_CLIMBING); }
		if(active && model.hasTag("fan")){ player.setActivity(player.ACTIVITY_HOVERING); }
		if(active && model.hasTag("switch,key")){
			
			if(!((Switch)model).getState()){
				((Switch)model).setState(true);}
		}
		if(active && model.hasTag("switch,chestKey")){
			
			if(!((Switch)model).getState()){
				((Switch)model).setState(true);}
		}
		if(model instanceof MovingElement)
		{
			movingElements.add((MovingElement)model);
		}
	}
	public void handleEndContact(Model model){
		if(model.hasTag("ground")){ contacts--; }
		if(model instanceof MovingElement)
		{
			movingElements.remove((MovingElement)model);
		}
	}
	
	protected void handleState(float dt){
		am.setState(state);
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
		if(contacts>0){
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
