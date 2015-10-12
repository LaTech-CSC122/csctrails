package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;
import grandtheftroster.utilities.Configuration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class Thrown extends Model{
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models/thrown.config");
	}


	private float speed;
	private int groundContact;
	private Thrower thrower;
	private Animation animation;
	private TextureRegion currentFrame;
	private float time;

	public Thrown(World world, String cfgProfileName){
		super(world, cfgProfileName, 50, 50);
		groundContact = 0;
		speed = 0.5f;
		
		//Pull config vars
		
		//SPEED
		if(cfg.hasProperty("SPEED@" + cfgProfileName)){
			speed = Float.parseFloat(cfg.getProperty("SPEED@" + cfgProfileName));
		}else{
			speed = 0.5f;
		}
		
		//Create Animation
		time = 0;
		Texture spriteSheet = new Texture("res/images/red chair.png");
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, 14, 14);
		TextureRegion[] spinCycle = new TextureRegion[tmp.length*tmp[0].length];
		int index = 0;
		for(int i=0; i<tmp.length; i++){
			for(int j=0; j<tmp[0].length; j++){
				spinCycle[index] = tmp[i][j];
				index++;
			}
		}
		animation = new Animation(0.15f, spinCycle);
		currentFrame = animation.getKeyFrame(time);
	}
	
	public void update(float dt){
		time = time + dt;
		currentFrame = animation.getKeyFrame(time, true);
	}
	public void draw(SpriteBatch sb){
		Vector2 pos = body.getPosition();
		sb.draw(currentFrame, pos.x*PPM-7, pos.y*PPM-7);
	}
	
	public void setThrower(Thrower t){
		thrower = t;
	}
	public Thrower getThrower(){
		return thrower;
	}
	
	public void pushRight(){
		body.setLinearVelocity(speed, body.getLinearVelocity().y);
		//body.setAngularVelocity(-speed*314/(radius)); cut off rotation for chair implementation 
	}
	public void pushLeft(){
		body.setLinearVelocity(-speed, body.getLinearVelocity().y);
		//body.setAngularVelocity(speed*314/(radius)); cut off rotation for chair implementation
	}
	
	public void addGroundContact(){
		groundContact++;
	}
	public void removeGroundConact(){
		groundContact--;
		if(groundContact <= 0){
			groundContact = 0;
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
	}
	public int getGroundContact(){ return groundContact; }

	public boolean destory(){
		if(!super.destory()){ return false; }
		if(thrower != null){
			thrower.removeObject(this);
		}
		return true;
	}
}
