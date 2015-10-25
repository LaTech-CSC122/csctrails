package grandtheftroster.elements;

public class HudCounter {
	

	private static float MAX_TIME_A = 31;
	private static float MAX_TIME_B = 36;
	
	private int defaultLives;
	private float defaultTime;
	private int defaultAnky;
	private int defaultclassScore;
	
	
	
	private int lives;
	private float time;
	private int anky;
	private int classScore;
	



	
	public HudCounter(int defaultLives, float defaultTime, int defaultScore){
		this.defaultLives = defaultLives;
		this.defaultTime = defaultTime;
		lives = 0;
		time = 0;
		anky = 0;
		classScore = 0;
		


	}
	
	public HudCounter() {
		defaultLives = 0;
		defaultTime = 0;
		lives = 0;
		time = 0;
		defaultAnky = 0;
		anky = 0;
		defaultclassScore = 0;
		anky = 0;
		
		


	}
	
	public void resetLives(){ lives = defaultLives; }
	public void setLives(int l){ lives = l; }
	public void modifyLives(int delta){ lives += delta; }
	public int getLives(){ return lives; }
	
	public void resetTime(){ time = defaultTime; }
	public void setTime(float t){ time = t; }
	public void modifyTime(float delta){ time += delta; }
	public float getTime(){ return time; }
	 
	public void resetAnky(){ anky = defaultAnky; }
	public void setAnky(int a){ anky = a; }
	public void modifyAnky(int delta){ anky += delta; }
	public int getAnky(){ return anky; }
	
	public void resetClassScore(){ classScore = defaultclassScore; }
	public void setClassScore(int a){ classScore = a; }
	public void modifyClassScore(int delta){ classScore += delta; }
	public int getClassScore(){ return classScore; }
	
	
	public String getScore(){ 
		int score = (int)time - lives * 15;
		System.out.println(score);
		if(score <= 100)
		{
			return ("A+");
		}
		if( score > 100 && score <= 110)
		{
			return ("A");
		}
		if( score > 110 && score <= 120)
		{
			return ("A-");
		}
		if( score > 120 && score <= 130)
		{
			return ("B+");
		}
		if( score > 130 && score <= 140)
		{
			return ("B");
		}
		if( score > 140 && score <= 150)
		{
			return ("B-");
		}
		if( score > 150 && score <= 160)
		{
			return ("C+");
		}
		if( score > 160 && score <= 170)
		{
			return ("C");
		}
		if( score > 170)
		{
			return ("C-");
		}
		return ("N/A");
		
	}
	public String getLeader(){
	if(anky > classScore){return anky-classScore + " win lead for Anky. Try harder!";} 
	else if (anky < classScore) {return classScore-anky + " win lead for the class! Keep it up.";}
	else {return anky + " win tie between Anky and the class. Don't quit now!";}
	}
	
	

		
	


	public void resetAll(){
		lives = defaultLives;
		time = defaultTime;

	
	} 
	
	
	

	}



