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

	//Hier gewünschte soundzzz vars. erstellen
	public static final Sound fire = new Sound("../resources/laser2.wav");
	public static final Sound explosion = new Sound("../resources/explo.wav");
	
	private AudioClip clip;

	private Sound(String file) {
		try {
			clip = Applet.newAudioClip(RType.class.getResource(file));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	//Funktion zum abspielen der oben erstellent soundz
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
}