package grandtheftroster.animation;

public class KeyFrame {

	float time;
	float xpos;
	float ypos;
	
	public KeyFrame(float time, float xpos, float ypos){
		this.time = time;
		this .xpos = xpos;
		this.ypos = ypos;
	}
	
	public float getTime(){ return time; }
	public float getPosistionX(){ return xpos; }
	public float getPosistionY(){ return ypos; }
}
