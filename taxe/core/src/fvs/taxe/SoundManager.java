package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
	
	Music bgMusic;
	
	
	public SoundManager(){
		this.bgMusic = Gdx.audio.newMusic(Gdx.files.internal("BGMusicLoop.ogg"));
	}
	
	
	
	public void playBGMusic(){
		bgMusic.setVolume(0.0f);
		bgMusic.setLooping(true);
		bgMusic.play();
	}
	
	public void muteBGMusic(){
		bgMusic.setVolume(0.0f);
	}
	
	public void unmuteBGMusic(){
		bgMusic.setVolume(0.55f);
	}
	
	public Music getBGMusic(){
		return bgMusic;
	}
}
