package grandtheftroster.player;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.audio.AudioPlayer;
import grandtheftroster.elements.Model;
import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;


public abstract class Activity
{
	
	protected static Configuration cfg;
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models/player.config");
		cfg.loadConfiguration("res/config/paths/sprite paths.config");
		cfg.loadConfiguration("res/config/paths/audio paths.config");
	}
	
	protected boolean active;
	protected Player player;
	protected Body body;
	protected AnimationManager am;
	protected int state;
	
	public Activity(Player p){
		this.player = p;
		this.body = p.getBody();
		state = 0;
		active = false;
		am = new AnimationManager();
	}
	
	public void begin(){
		active = true;
	}
	public void end(){
		active = false;
	}
	public abstract void update(float dt);
	public abstract void dispose();
	protected abstract void handleInput();
	protected abstract void handleState(float dt);
	public abstract void handleBeginContact(Model model);
	public abstract void handleEndContact(Model model);
	
	public void draw(SpriteBatch sb){
		float x = body.getPosition().x*PPM;
		float y = body.getPosition().y*PPM;
		am.draw(sb, x, y);
	}
	
	
}
