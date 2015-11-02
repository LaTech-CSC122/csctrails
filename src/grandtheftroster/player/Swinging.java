package grandtheftroster.player;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.elements.Model;
import grandtheftroster.elements.Rope;
import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.handlers.MyInput;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class Swinging extends Activity{
	
	private static final int SWING_CCW = 0;
	private static final int SWING_CW = 1;
	
	private static final float PI = (float)Math.PI;
	private static final float SWING_ANGLE = PI*5/8; // 5/8
	private static final float SWING_MIN = PI*3/2-SWING_ANGLE/2;
	private static final float SWING_MAX = PI*3/2+SWING_ANGLE/2;
	private static final float SWING_OMEGA = SWING_ANGLE/1/5*7; // Radians/Second x/9
	
	private Vector2 por;
	private float radius;
	private float time;
	private Rope rope;
	private float angularPos;
	private float dr;
	
	public Swinging(Player p) {
		super(p);
		loadAnimations();
		state=SWING_CCW;
		por = new Vector2(0/PPM, 0/PPM);
		radius = 50/PPM;
	}
	

	
	public void begin() {
		super.begin();
		time = (float)Math.acos(0);
	}

	public void end() {
		super.end();
		rope.getBody().setTransform(por, 0);
	}

	public void update(float dt) {
		stepSwing(dt);
		handleState(dt);
		handleInput();
	}

	public void dispose() {}

	protected void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_UP)) jump();
	}

	protected void handleState(float dt) {
		am.setState(state);
		am.resetAnimation();

	}

	public void handleBeginContact(Model model) {
		if(model.hasTag("rope") && active==false){
			player.setActivity(player.ACTIVITY_SWINGING);
			Rope rope = (Rope) model;
			radius = rope.getLength();
			this.rope = rope;
			por = rope.getBody().getPosition();
		}
	}

	public void handleEndContact(Model model) {}
		
	private void jump(){
		body.setLinearVelocity(-dr*radius*(float)Math.sin(angularPos)*2f, dr*radius*(float)Math.cos(angularPos)*2f);
		player.setActivity(player.ACTIVITY_WALKING);
	}
	
	private void stepSwing(float dt){
		//calculate the change of angle
		time+=dt;
		
		//Angular Position
		//The following equation outputs the angular position of the player wrt the point of rotation
		//Using the sine equation places a sight damper when near the extrema giving a more realistic illusion
		angularPos = (SWING_ANGLE/2)*(float)-Math.cos(time*SWING_OMEGA)+PI*3/2;
		
		// Instantaneous Angular Velocity
		dr = (SWING_ANGLE/2)*(float)Math.sin(time*SWING_OMEGA);

		//set direction
		if(dr<0){state=SWING_CW;}
		else if(dr>0){state=SWING_CCW;}
		
		//Normalize to account for errors over time
		if(angularPos>SWING_MAX) angularPos=SWING_MAX;
		else if(angularPos<SWING_MIN) angularPos=SWING_MIN;
		
		//Position player
		float xPlayer = por.x+radius*(float)Math.cos(angularPos);
		float yPlayer = por.y+radius*(float)Math.sin(angularPos);
		body.setTransform(xPlayer, yPlayer, 0);
		
		//position rope
		float xRope = rope.getBody().getPosition().x;
		float yRope = rope.getBody().getPosition().y;
		rope.getBody().setTransform(xRope, yRope, angularPos+PI/2);
	}
	
	private void loadAnimations(){
		
		Animation left = AnimationManager.createAnimation(1f, 32, 32, true, false,
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(left,SWING_CW);
		
		Animation right = AnimationManager.createAnimation(1f, 32, 32, false, false,
				cfg.getProperty("JDK_RUNNING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(right, SWING_CCW);
		
		am.setState(SWING_CCW);
		am.resetAnimation();
	}
	
}