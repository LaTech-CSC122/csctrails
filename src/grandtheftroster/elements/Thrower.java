package grandtheftroster.elements;

import java.util.ArrayList;


import com.badlogic.gdx.physics.box2d.World;

public class Thrower {
	
	
	
	private int maxCount;
	
	private ArrayList<Thrown> allObjects;
	private int xpos;
	private int ypos;
	private World world;
	private  float interval; //default time it waits. is constant
	private float time;
	
	public Thrower(World world, int maxCount, float interval)
	{ 
		this.maxCount = maxCount;
		this.interval = interval;
		this.world = world;
		allObjects = new ArrayList<Thrown>();
		time = 0;
		
	}
	
	
	public void setInterval(float p){ interval = p; }
	public void setPosistion(int x, int y){
		xpos = x;
		ypos = y;
	}
	
	public Thrown update(float dt){
		time += dt;
		if(allObjects.size() >= maxCount){ return null; }
		if(time>interval){
			Thrown t = new Thrown(world, "MODEL:THROWN");
			t.getBody().setTransform((float)xpos/B2DVars.PPM, (float)ypos/B2DVars.PPM, 0);
			t.setThrower(this);
			allObjects.add(t);
			time = 0;
			return t;
		}
		return null;
		
	}


	public Thrown throwObject(int x, int y){
		Thrown t = new Thrown(world, "MODEL:THROWN");
		t.getBody().setTransform(x/B2DVars.PPM, y/B2DVars.PPM, 0);
		t.setThrower(this);
		allObjects.add(t);
		return t;
	}
	
	public void removeObject(Thrown t){
		if(allObjects.contains(t)){
			allObjects.remove(t);
		}
	}
}
