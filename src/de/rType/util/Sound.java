package de.rType.util;

import java.applet.Applet;
import java.applet.AudioClip;

import de.rType.main.RType;

/**
 * 
 * @author rickuz 
 * 		   Zugriff auf Sound-Effekte einfach per
 *         "Sound.[static final var].play();" BUG: ca. jeder 10 Sound hackt,
 *         ohne thread noch schlimmer
 */

public class Sound {

	//Hier gewuenschte soundzzz vars. erstellen
	public static final Sound laser_small = new Sound("/de/rType/resources/laser_small.wav");
	public static final Sound laser_medium = new Sound("/de/rType/resources/laser_medium.wav");
	public static final Sound laser_huge = new Sound("/de/rType/resources/laser2.wav");
	public static final Sound explosion = new Sound("/de/rType/resources/explo2.wav");
	public static final Sound build_up = new Sound("/de/rType/resources/up_new.wav");
	
	private AudioClip clip;

	private Sound(String file) {
		try {
			clip = Applet.newAudioClip(RType.class.getResource(file));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	//Funktion zum abspielen der oben erstellent sounds
	public void play() {
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
//	public void play() {
//					clip.play();
//	}
}
