package grandtheftroster.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicEntry extends AudioEntry{

	private Music music;
	
	public MusicEntry(String path, float gain){
		super(path, gain, true);
		music = Gdx.audio.newMusic(new FileHandle(path));
	}

	@Override
	public void dispose() {
		music.dispose();
	}

	@Override
	public void stop() {
		music.stop();
	}

	@Override
	public void play(float volume) {
		music.setLooping(loop);
		music.setVolume(gain*volume);
		music.play();
	}


}
