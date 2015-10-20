package grandtheftroster.player;

import com.badlogic.gdx.math.Vector2;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.MyInput;

public class Hovering extends Activity{
private static final float SPEED = 0.01f;
	public Hovering(Player p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		//System.out.println("Hovering");
		if(body.getLinearVelocity().y<-0.5f){
				body.setLinearVelocity(0f, -.5f);
		}
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		if(body.getLinearVelocity().y>1f){
			body.setLinearVelocity(0f, 1f);
		}
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		body.applyLinearImpulse(new Vector2(0f, .09f), body.getWorldCenter(), false);
		
		handleInput();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		if(MyInput.isDown(MyInput.BUTTON_LEFT))moveLeft();
		if(MyInput.isDown(MyInput.BUTTON_RIGHT))moveRight();
		
		
	}

	@Override
	protected void handleState(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleBeginContact(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEndContact(Model model) {
		// TODO Auto-generated method stub
		
	}
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
}
