package grandtheftroster.player;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.handlers.MyInput;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Walking implements Activity{
	
	private static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models/player.config");
		cfg.loadConfiguration("res/config/paths/sprite paths.config");
		cfg.loadConfiguration("res/config/paths/audio paths.config");
	}
	
	private static final int WALKING_LEFT = 0;
	private static final int WALKING_RIGHT = 1;
	private static final int STANDING_LEFT = 2;
	private static final int STANDING_RIGHT = 3;
	
	private static final float RUN_SPEED = 0.01f;
	
	private int state;
	
	AnimationManager am;
	Body body;
	Player player;
	
	public Walking(Player player){
		this.player = player;
		this.body = player.getBody();
		state = STANDING_RIGHT;
		loadAnimations();
	}

	@Override
	public void begin() {}
	@Override
	public void end() {}
	@Override
	public void dispose() {}
	

	@Override
	public void draw(SpriteBatch sb) {
		float x = body.getPosition().x*PPM;
		float y = body.getPosition().y*PPM;
		am.draw(sb, x, y);
	}

	@Override
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
	}
	
	private void handleState(float dt){
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
	
	private void loadAnimations(){
		float runningSpeed = 0.11f;
		am = new AnimationManager();
		
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
