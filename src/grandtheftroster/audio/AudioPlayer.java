package grandtheftroster.audio;

import java.util.HashMap;
import java.util.Set;



public class AudioPlayer{

	private HashMap<String,AudioEntry> playlist;
	private float volume;
	
	public AudioPlayer() {
		playlist = new HashMap<String,AudioEntry>();
		volume = 1f;
	}
	
	public void addSound(String name, String path, float gain, boolean stream){
		if(path==null || name==null){ throw new NullPointerException("passed null argument"); }
		if(name.trim().equals("")){ throw new IllegalArgumentException("name cannot be empty"); }
		if(path.trim().equals("")){ throw new IllegalArgumentException("path cannot be empty"); }
		
		AudioEntry audio;
		if(stream){ audio = new MusicEntry(path, gain); }
		else{ audio = new SoundEntry(path, gain); }
		
		playlist.put(name,audio);
	}
	
	public void play(String name) {
		getSound(name).play(volume);
	}
	public void stop(String name){
		getSound(name).stop();
	}
	
	public void setVolume(float value){
		this.volume = value;
	}
	public void setGain(String name, float gain){
		getSound(name).setGain(gain);
	}
	
	public void dispose() {
		Set<String> keyset = playlist.keySet();
		for(String k:keyset){
			playlist.get(k).dispose();
			playlist.remove(k);
		}
	}

	private AudioEntry getSound(String name){
		return playlist.get(name);
	}
}
