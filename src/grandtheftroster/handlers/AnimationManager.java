package grandtheftroster.handlers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager {

	public static Animation createAnimation(float frameRate, int xframes, int yframes, boolean xflip, boolean yflip, String path){
		//Load Sprite Sheet
		Texture spriteSheet= new Texture(path);
		
		//Split Sprite Sheet into a 2D array
		TextureRegion[][] frameMatrix = TextureRegion.split(spriteSheet, xframes, yframes);
		
		//Move from 2D array to 1D array
		TextureRegion[] frameArray = new TextureRegion[frameMatrix.length*frameMatrix[0].length];
		int index = 0;
		for(int i=0; i < frameMatrix.length; i++){
			for(int j=0; j<frameMatrix[0].length; j++){
				frameMatrix[i][j].flip(xflip, yflip);
				frameArray[index] = frameMatrix[i][j];
				index++;
			}
		}
		//Create the Animation object
		Animation a = new Animation(frameRate, frameArray);
		a.setPlayMode(Animation.LOOP);
		return a;
	}
	
	private HashMap<String,Animation> assets;
	private int state;
	private float time;
	private TextureRegion currentFrame;

	public AnimationManager(){
		assets = new HashMap<String,Animation>();
		state = 0;
		time = 0;
	}
	
	public void draw(SpriteBatch sb, float x, float y){
		if(currentFrame != null){ 
			float xpos = x - currentFrame.getRegionWidth()/2;
			float ypos = y - currentFrame.getRegionHeight()/2;
			sb.draw(currentFrame, xpos, ypos); 
		}
	}
	public void update(float dt){
		time += dt;
		currentFrame = assets.get(state+"").getKeyFrame(time);
	}
	public void resetAnimation(){
		time=0; 
		update(0);
	}
	
	public void setState(int id){
		if(assets.get(id+"")==null){ throw new IllegalArgumentException("Animation id not found: " + id); }
		else{
			if(state!=id){time=0;}
			state=id;
		}
	}
	
	public void addAnimation(Animation animation, int id){
		if(animation == null){ throw new IllegalArgumentException("Cannot pass null animation"); }
		if(assets.containsKey(id+"")){ throw new IllegalArgumentException("Id has alredy been assigned:" + id); }
		assets.put(id+"", animation);
	}
	
	public void setPlayMode(int id, int playMode){
		if(assets.get(id+"")==null){ throw new IllegalArgumentException("Animation id not found: " + id); }
		assets.get(id+"").setPlayMode(playMode);
	}


}
