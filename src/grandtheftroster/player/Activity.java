package grandtheftroster.player;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.elements.Model;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Array;


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
	
	protected boolean isContacting(String tag){
		Array<Contact> contacts = body.getWorld().getContactList();
		for(Contact c:contacts){
			Model m1 = (Model) c.getFixtureA().getBody().getUserData();
			Model m2 = (Model) c.getFixtureB().getBody().getUserData();
			if(m1.equals(player) && m2.hasTag(tag)){ 
				for(int i = 0; i<m1.getTags().length; i++){
					//System.out.print(m1.getTags()[i] + " | ");
				}
				//System.out.println();
				return true;
			}
			if(m2.equals(player) && m1.hasTag(tag)){ 
				for(int i = 0; i<m1.getTags().length; i++){
					//System.out.print(m1.getTags()[i] + " | ");
				}
				//System.out.println();
				return true;
			}
		}
		return false;
	}
}
