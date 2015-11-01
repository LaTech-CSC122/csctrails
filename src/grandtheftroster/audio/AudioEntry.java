package grandtheftroster.audio;



public abstract class AudioEntry {
	protected boolean loop;
	protected float gain;
	
	protected AudioEntry(String path, float gain, boolean loop){
		this.gain = gain;
		this.loop = loop;
	}
	
	public void setGain(float gain){ this.gain = gain; }
	public void setLoop(boolean b){ loop = b; }
	
	public abstract void dispose();
	public abstract void stop();
	public abstract void play(float gain);
		
}

