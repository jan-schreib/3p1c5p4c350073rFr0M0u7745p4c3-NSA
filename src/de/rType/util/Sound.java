package de.rType.util;

import java.applet.Applet;
import java.applet.AudioClip;

import de.rType.main.Enviroment;
import de.rType.main.RType;

/**
 * Zugriff auf Sound-Effekte einfach per "Sound.[static final var].play();" BUG:
 * ca. jeder 10 Sound hackt, ohne thread noch schlimmer
 * 
 * @author rickuz
 * 
 */

public class Sound {

	// Hier gewuenschte soundzzz vars. erstellen
	public static final Sound LASER_SMALL = new Sound("/de/rType/resources/laser_small.wav");
	public static final Sound LASER_MEDIUM = new Sound("/de/rType/resources/laser_medium.wav");
	public static final Sound LASER_HUGE = new Sound("/de/rType/resources/laser2.wav");
	public static final Sound EXPLOSION = new Sound("/de/rType/resources/explo2.wav");
	public static final Sound BUILD_UP = new Sound("/de/rType/resources/up_new.wav");
	public static final Sound TITLE_MUSIC = new Sound("/de/rType/resources/r-type.wav");
	public static final Sound LVL1 = new Sound("/de/rType/resources/lvl1.wav");
	public static final Sound LVL2 = new Sound("/de/rType/resources/lvl2.wav");
	public static final Sound LVL3 = new Sound("/de/rType/resources/lvl3.wav");

	private AudioClip clip;

	private Sound(String file) {
		clip = Applet.newAudioClip(RType.class.getResource(file));
	}

	// Funktion zum abspielen der oben erstellent soundz
	public void play() {
		if (!Enviroment.getEnviroment().getGlobalSettings().getSoundeffectsMuted()) {
			try {
				new Thread() {
					public void run() {
						clip.play();
					}
				}.start();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}
	public void play_music() {
		if (!Enviroment.getEnviroment().getGlobalSettings().getMusicMuted()) {
			clip.loop();
		}
	}
}
