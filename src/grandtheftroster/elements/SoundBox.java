package grandtheftroster.elements;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;



public class SoundBox{

	private HashMap<String,Sound> playlist;
	
	public SoundBox() {
		playlist = new HashMap<String,Sound>();
	}
	
	public void addSound(String name, String path){
		if(path==null || name==null){ throw new NullPointerException("passed null argument"); }
		if(name.trim().equals("")){ throw new IllegalArgumentException("name cannot be empty"); }
		if(path.trim().equals("")){ throw new IllegalArgumentException("path cannot be empty"); }
		
		Sound s = Gdx.audio.newSound(new FileHandle(path));
		if(s==null){ 
			throw new NullPointerException("Could not load file: " + path);
		}
		else{
			playlist.put(name,s);
		}
	}
	
	public void play(String name) {
		getSound(name).play();
	}
	public void play(String name, float volume) {
		getSound(name).play(volume);
	}
	public void play(String name, float volume, float pitch, float pan) {
		getSound(name).play(volume, pitch, pan);
	}

	public void loop(String name) {
		getSound(name).loop();
	}
	public void loop(String name, float volume) {
		getSound(name).loop(volume);
	}
	public void loop(String name, float volume, float pitch, float pan) {
		getSound(name).loop(volume, pitch, pan);
	}

	public void pause(String name) {
		getSound(name).pause();
	}
	public void stop(String name) {
		getSound(name).stop();
	}
	public void resume(String name) {
		getSound(name).resume();
	}
	
	public void dispose() {
		Set<String> keyset = playlist.keySet();
		for(String k:keyset){
			playlist.get(k).dispose();
			playlist.remove(k);
		}
	}



	//public void pause(long arg0) {}



	

	
	//public void resume(long arg0) {}
	//public void setLooping(long arg0, boolean arg1) {}
	//public void setPan(long arg0, float arg1, float arg2) {}
	//public void setPitch(long arg0, float arg1) {}
	//public void setPriority(long arg0, int arg1) {}
	//public void setVolume(long arg0, float arg1) {}
	//public void stop(long arg0) {}

	private Sound getSound(String name){
		return playlist.get(name);
	}
}
