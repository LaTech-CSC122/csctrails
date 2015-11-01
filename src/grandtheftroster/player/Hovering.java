package grandtheftroster.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.handlers.MyInput;

public class Hovering extends Activity{
	
	private static final int HOVER_LEFT = 1;
	private static final int HOVER_RIGHT = 2;
	
	private static final float SPEED = 0.01f;



	public Hovering(Player p) {
		super(p);
		loadAnimations();
		state=HOVER_LEFT;
	}

	public void begin() {
		super.begin();
		System.out.println("Activity: Hovering");
		if(body.getLinearVelocity().y<-0.5f){
				body.setLinearVelocity(0f, -.5f);
		}
	}

	public void end() {
		super.end();
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

	public void handleEndContact(Model model) {
		if(active && model.hasTag("fan")){
			player.setActivity(player.ACTIVITY_WALKING);
		}
		if(active && model.hasTag("ladder"))
		{
			player.setActivity(player.ACTIVITY_WALKING);
		}
	}
	
	
	private void moveLeft(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x-SPEED, pos.y, 0);
	}
	private void moveRight(){
		Vector2 pos = body.getPosition();
		body.setTransform(pos.x+SPEED, pos.y, 0);
	}
	
	
	private void loadAnimations(){
		float runningSpeed = 0.11f;
		Animation spin = AnimationManager.createAnimation(runningSpeed, 32, 32, false, false, 
				cfg.getProperty("JDK_SPINNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(spin,HOVER_RIGHT);
		am.addAnimation(spin,HOVER_LEFT);
		am.setState(HOVER_RIGHT);
		am.resetAnimation();
	}
}
