package grandtheftroster.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.MyInput;

public class Hovering extends Activity{
	
	//private static final int HOVER_UP = 0;
	private static final int HOVER_LEFT = 1;
	private static final int HOVER_RIGHT = 2;
	
	private static final float SPEED = 0.01f;



	public Hovering(Player p) {
		super(p);
		loadAnimations();
		state=HOVER_LEFT;
	}

	public void begin() {
		System.out.println("Hovering");
		if(body.getLinearVelocity().y<-0.5f){
				body.setLinearVelocity(0f, -.5f);
		}
		
		if(body.getLinearVelocity().x>0){
			am.setState(HOVER_RIGHT);
		}
		if(body.getLinearVelocity().x<0){
			am.setState(HOVER_LEFT);
		}
	}

	public void end() {
		if(body.getLinearVelocity().y>1f){
			body.setLinearVelocity(0f, 1f);
		}
		
	}

	public void update(float dt) {
		body.applyLinearImpulse(new Vector2(0f, 0.09f), body.getWorldCenter(), false);
		handleInput();
		handleState(dt);
	}

	public void dispose() {}

	protected void handleInput() {
		if(MyInput.isDown(MyInput.BUTTON_LEFT))moveLeft();
		if(MyInput.isDown(MyInput.BUTTON_RIGHT))moveRight();
	}

	protected void handleState(float dt) {
		am.setState(state);
		am.update(dt);
		
	}

	public void handleBeginContact(Model model) {}

	public void handleEndContact(Model model) {}
	
	
	private void moveLeft(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-SPEED, pos.y, 0);
		//state = WALKING_LEFT;
	}
	private void moveRight(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+SPEED, pos.y, 0);
		//state = WALKING_RIGHT;
	}
	
	
	private void loadAnimations(){
		float runningSpeed = 0.11f;
		
		Animation spin = AnimationManager.createAnimation(runningSpeed, 32, 32, false, false, 
				cfg.getProperty("JDK_SPINNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(spin,HOVER_RIGHT);
		
		//Animation left = AnimationManager.createAnimation(runningSpeed, 32, 32, true, false,
		//		cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(spin,HOVER_LEFT);
		
		//Animation climb = AnimationManager.createAnimation(runningSpeed, 32, 32, true, false,
		//		cfg.getProperty("JDK_CLIMBING" + "@" + "PATHS:SPRITES"));
		//am.addAnimation(climb,HOVER_UP);
		
		am.setState(HOVER_RIGHT);
		am.resetAnimation();
	}
}
