package csctrails.elements;

public class HudCounter {
	
	private int defaultLives;
	private float defaultTime;
	private int defaultScore;
	
	@SuppressWarnings("unused")
	private int lives;
	private float time;
	private int score;
	
	public HudCounter(int defaultLives, float defaultTime, int defaultScore){
		this.defaultLives = defaultLives;
		this.defaultTime = defaultTime;
		this.defaultScore = defaultScore;
		lives = 0;
		time = 0;
		score = 0;
	}
	
	public HudCounter() {
		defaultLives = 0;
		defaultTime = 0;
		defaultScore = 0;
		lives = 0;
		time = 0;
		score = 0;
	}
	
	public void resetLives(){ lives = defaultLives; }
	public void setLives(int l){ lives = l; }
	public void modifyLives(int delta){ lives += delta; }
	public int getLives(int lives){ return lives; }
	
	public void resetTime(){ time = defaultTime; }
	public void setTime(float t){ time = t; }
	public void modifyTime(float delta){ time += delta; }
	public float getTime(){ return time; }
	
	public void resetScore(){ score = defaultScore; }
	public void setScore(int s){ score = s; }
	public void modifyScore(float delta){ score += delta; }
	public int getScore(){ return score; }

	public void resetAll(){
		lives = defaultLives;
		time = defaultTime;
		score = defaultScore;
	}
}
