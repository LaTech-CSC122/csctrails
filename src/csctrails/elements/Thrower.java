package csctrails.elements;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;

public class Thrower {
	
	private Random random = new Random();
	
	private int maxCount;
	private int deviation;
	private ArrayList<Thrown> allObjects;
	private int xpos;
	private int ypos;
	private World world;
	private int deltaFrames;
	private int minInterval;
	private int currentInterval;
	private boolean active;
	
	
	public Thrower(World world, int maxCount, int interval) {
		this.maxCount = maxCount;
		this.deviation = 30;
		this.minInterval = interval;
		this.currentInterval = interval;
		this.world = world;
		allObjects = new ArrayList<Thrown>();
		deltaFrames = 0;
		active = false;
	}
	
	
	public void setDeviation(int p){ deviation = p; }
	public void setPosistion(int x, int y){
		xpos = x;
		ypos = y;
	}
	
	
	public Thrown throwObject(){
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
		if(deltaFrames > minInterval){
			deltaFrames = 0;
		}
	}

	public void setActive(boolean b){ active = b; }
	public boolean getActive(){ return active; }
}
