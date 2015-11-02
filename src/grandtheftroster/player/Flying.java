package grandtheftroster.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;

import grandtheftroster.elements.Model;
import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.handlers.MyInput;

public class Flying extends Activity{

	private static final int FLYING_RIGHT = 0;
	Music fly;
	public Flying(Player player){
		super(player);
		loadAnimation();
	}

	@Override
	public void begin(){
		super.begin();
		body.setLinearVelocity(body.getLinearVelocity().x, 1.5f);
	}
	@Override
	public void update(float dt) {
		body.setLinearVelocity(0.4f, body.getLinearVelocity().y);
		handleInput();
		handleState(dt);
	}

	@Override
	public void dispose() {
		fly.stop();
		fly.dispose();
		}

	@Override
	protected void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON_UP)){
			body.setLinearVelocity(body.getLinearVelocity().x, 1f);
			//flying sound
			//fly = Gdx.audio.newMusic(new FileHandle(cfg.getProperty("FLYING@PATHS:AUDIO")));
			//fly.setVolume(3.5f);
			//fly.play();
		
			player.getVoicebox().play("flap");
			
			}
	}

	
	
	@Override
	protected void handleState(float dt) {
		am.setState(state);
		am.update(dt);
		
	}

	@Override
	public void handleBeginContact(Model model) {
		if(active && model.hasTag("ground")){
			player.setActivity(player.ACTIVITY_WALKING);
		}
		
	}

	@Override
	public void handleEndContact(Model model) {
		// TODO Auto-generated method stub
		
			
		
	}
	
	private void loadAnimation(){
		float runningSpeed = 0.15f;
		Animation spin = AnimationManager.createAnimation(runningSpeed, 32, 32, false, false, 
				cfg.getProperty("JDK_FLYING" + "@" + "PATHS:SPRITES"));
		am.addAnimation(spin,FLYING_RIGHT);
		am.setPlayMode(FLYING_RIGHT, Animation.LOOP_PINGPONG);
		am.setState(FLYING_RIGHT);
		am.resetAnimation();
	}
	

}
