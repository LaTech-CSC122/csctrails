package grandtheftroster.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundEntry extends AudioEntry{
	
	private Sound sound;
	
	public SoundEntry(String path, float gain){
		super(path, gain, false);
		sound = Gdx.audio.newSound(new FileHandle(path));
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

	@Override
	public void stop() {
		sound.stop();
	}

	@Override
	public void play(float volume) {
		if(loop){ sound.loop(volume*gain); }
		else{ sound.play(volume*gain); }
	}

}
