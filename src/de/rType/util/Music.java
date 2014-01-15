package de.rType.util;

import java.io.IOException;

import org.newdawn.easyogg.*;

import de.rType.main.Enviroment;
import de.rType.main.RType;

public enum Music {	
	
	TITLE("/de/rType/resources/r-type.ogg"),
	LVL1 ("/de/rType/resources/lvl1.ogg"),
	LVL2 ("/de/rType/resources/lvl2.ogg"),
	LVL3 ("/de/rType/resources/lvl3.ogg");	
	private OggClip clip;
	
	private Music(String path){
		try{
		this.clip = new OggClip(RType.class.getResourceAsStream(path));
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void play() {
		if (!Enviroment.getEnviroment().getGlobalSettings().getMusicMuted()) {
			if (clip.isPaused() && !clip.stopped()) {
				clip.resume();
			} else {
				clip.loop();
			}
		}
	}
	public void pause(){
		if(!clip.isPaused() && !clip.stopped()){
			clip.pause(); 	
		}
	}
	public void stop(){
		clip.stop();
	}
	
}
 