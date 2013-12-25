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
 * @author Jo
 */

public class Sound {

	// Hier gewuenschte soundzzz vars. erstellen
	public static final Sound LASER_SMALL = new Sound("/de/rType/resources/laser_small.wav");
	public static final Sound LASER_MEDIUM = new Sound("/de/rType/resources/laser_medium.wav");
	public static final Sound LASER_HUGE = new Sound("/de/rType/resources/laser2.wav");
	public static final Sound EXPLOSION = new Sound("/de/rType/resources/explo2.wav");
	public static final Sound BUILD_UP = new Sound("/de/rType/resources/up_new.wav", true);

	private String url;
	private AudioClip clip;

	private Sound(String file) {
		this(file, false);
	}

	private Sound(String url, boolean single) {
		if (single) {
			clip = Applet.newAudioClip(RType.class.getResource(url));
		}
		this.url = url;
	}

	// Funktion zum abspielen der oben erstellent soundz
	public void play() {
		if (!Enviroment.getEnviroment().getGlobalSettings().getSoundeffectsMuted()) {
			try {
				new Thread() {
					public void run() {
						if (clip == null) {
							Applet.newAudioClip(RType.class.getResource(url)).play();
						} else {
							clip.play();
						}
					}
				}.start();
			} catch (Throwable e) {
				e.printStackTrace();
				System.out.println("FAILED PLAYING AUDIOCLIP: " + url);
			}
		}
	}

	public void stop() {
		if (clip != null) {
			clip.stop();
		}
	}
}
