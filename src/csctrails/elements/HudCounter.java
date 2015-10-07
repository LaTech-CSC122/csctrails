package csctrails.elements;

public class HudCounter {
	
	private static float MAX_TIME_A = 30;
	private static float MAX_TIME_B = 35;
	
	private int defaultLives;
	private float defaultTime;
	
	private int lives;
	private float time;
	
	public HudCounter(int defaultLives, float defaultTime, int defaultScore){
		this.defaultLives = defaultLives;
		this.defaultTime = defaultTime;
		lives = 0;
		time = 0;
	}
	
	public HudCounter() {
		defaultLives = 0;
		defaultTime = 0;
		lives = 0;
		time = 0;
	}
	
	public void resetLives(){ lives = defaultLives; }
	public void setLives(int l){ lives = l; }
	public void modifyLives(int delta){ lives += delta; }
	public int getLives(){ return lives; }
	
	public void resetTime(){ time = defaultTime; }
	public void setTime(float t){ time = t; }
	public void modifyTime(float delta){ time += delta; }
	public float getTime(){ return time; }
	

	public String getScore(){ 
		if(time < MAX_TIME_A){ return "A"; }
		else if(time < MAX_TIME_B){ return "B"; }
		else{ return "C"; }
		
	}

	public void resetAll(){
		lives = defaultLives;
		time = defaultTime;
	}
}
