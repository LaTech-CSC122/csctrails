package csctrails.elements;

import com.badlogic.gdx.physics.box2d.World;

import csctrails.configuration.Configuration;

public class Thrown extends Model {
	
	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/models/thrown.config");
	}


	private float speed;
	private int radius;
	private int groundContact;
	private Thrower thrower;

	public Thrown(World world, String cfgProfileName){
		super(world, cfgProfileName, 50, 50);
		groundContact = 0;
		radius = 7;
		speed = 0.5f;
		
		//Pull config vars
		
		//SPEED
		if(cfg.hasProperty("SPEED@" + cfgProfileName)){
			speed = Float.parseFloat(cfg.getProperty("SPEED@" + cfgProfileName));
		}else{
			speed = 0.5f;
		}
		
		//FIXTURE_SHAPE_RADIUS
		if(cfg.hasProperty("FIXTURE_SHAPE_RADIUS@" + cfgProfileName)){
			radius = Integer.parseInt(cfg.getProperty("FIXTURE_SHAPE_RADIUS@" + cfgProfileName));
		} else{
			radius = 7;
		}
	}
	
	public void setThrower(Thrower t){
		thrower = t;
	}
	public Thrower getThrower(){
		return thrower;
	}
	
	public void pushRight(){
		body.setLinearVelocity(speed, body.getLinearVelocity().y);
		body.setAngularVelocity(-speed*314/(radius));
	}
	public void pushLeft(){
		body.setLinearVelocity(-speed, body.getLinearVelocity().y);
		body.setAngularVelocity(speed*314/(radius));
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
