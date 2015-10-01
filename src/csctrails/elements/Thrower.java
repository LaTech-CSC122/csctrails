package csctrails.elements;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;

public class Thrower {
	
	private Random random = new Random();
	
	private int maxCount;
	private float probability;
	private ArrayList<Thrown> allObjects;
	private int xpos;
	private int ypos;
	private World world;
	private int deltaFrames;
	private int interval;
	private boolean active;
	
	
	public Thrower(World world, int maxCount, int interval) {
		this.xpos = 0;
		this.ypos = 0;
		this.maxCount = maxCount;
		this.probability = 1;
		this.interval = interval;
		this.world = world;
		allObjects = new ArrayList<Thrown>();
		deltaFrames = 0;
		active = false;
	}
	
	
	public void setProbability(float p){ probability = p; }
	public void setPosistion(int x, int y){
		xpos = x;
		ypos = y;
	}
	
	
	private Thrown throwObject(boolean chance, int x, int y){
		deltaFrames++;
		
		if(allObjects.size() >= maxCount){ 
			return null;
		}
		else if((deltaFrames>interval && random.nextFloat()%1 <= probability) || !chance){
			Thrown t = new Thrown(world, xpos, ypos);
			t.getBody().setTransform((float)x/B2DVars.PPM, (float)y/B2DVars.PPM, 0);
			t.setThrower(this);
			allObjects.add(t);
			deltaFrames = 0;
			return t;
		}
		else{
			return null;
		}
	}
	
	public Thrown throwObject(){
		//if(!active) return null;
		return throwObject(true, xpos, ypos);
	}
	public Thrown throwObject(int x, int y, boolean force){
		//if(!active || !force) return null;
		return throwObject(false, x, y);
	}
	public Thrown throwObject(int x, int y){
		return throwObject(x, y, false);
	}
	
	public void removeObject(Thrown t){
		if(allObjects.contains(t)){
			allObjects.remove(t);
		}
		if(deltaFrames > interval){
			deltaFrames = 0;
		}
	}

	public void setActive(boolean b){ active = b; }
	public boolean getActive(){ return active; }
}
