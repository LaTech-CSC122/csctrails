package grandtheftroster.elements;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;

public class Thrower {
	
	private Random random = new Random();
	
	private int maxCount;
	private float deviation; //random next throw time
	private ArrayList<Thrown> allObjects;
	private int xpos;
	private int ypos;
	private World world;
	//private int deltaFrames;
	private  float interval; //default time it waits. is constant
	private float currentInterval;
	private float time;
	
	public Thrower(World world, int maxCount, float interval, float deviation) {
		this.maxCount = maxCount;
		this.deviation = 30;  
		this.interval = interval;
		this.currentInterval = interval;
		this.world = world;
		allObjects = new ArrayList<Thrown>();
		time = 0;
	}
	
	
	public void setDeviation(int p){ deviation = p; }
	public void setPosistion(int x, int y){
		xpos = x;
		ypos = y;
	}
	
	public Thrown update(float dt){
		time += dt;
		if(allObjects.size() >= maxCount){ return null; }
		//System.out.println(this.time + " | " + currentInterval );
		if(time>currentInterval){
			Thrown t = new Thrown(world, "MODEL:THROWN");
			t.getBody().setTransform((float)xpos/B2DVars.PPM, (float)ypos/B2DVars.PPM, 0);
			t.setThrower(this);
			allObjects.add(t);
			
			//reset frame params;
			time = 0;
			float timeOffset = random.nextInt()%deviation;
			if(timeOffset<0){ timeOffset = timeOffset*-1; } 
			currentInterval = interval + timeOffset;
			return t;
		}
		return null;
		
	}
	
/*	public Thrown throwObject(){
		if(!active){ return null; }
		deltaFrames++;
		if(allObjects.size() >= maxCount){ return null; }
		System.out.println(currentInterval + " : " + deltaFrames + " | " + active + " | " + allObjects.size());
		if(deltaFrames>currentInterval){
			Thrown t = new Thrown(world, "MODEL:THROWN");
			t.getBody().setTransform((float)xpos/B2DVars.PPM, (float)ypos/B2DVars.PPM, 0);
			t.setThrower(this);
			allObjects.add(t);
			
			//reset frame params
			deltaFrames = 0;
			int frameOffset = random.nextInt()%deviation;
			if(frameOffset<0){ frameOffset = frameOffset*-1; }
			currentInterval = minInterval + frameOffset;
			return t;
		}
		
		return null;
	}*/

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
