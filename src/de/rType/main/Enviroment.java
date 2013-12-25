package de.rType.main;

import javax.swing.JFrame;

import global.GlobalSettings;
import de.rType.model.Pair;

/**
 * GameEnviroment contains GlobalSettings
 * 
 * @author jo
 * 
 */
public class Enviroment {

	private static Enviroment instance;

	private Enviroment() {

	}

	public static Enviroment getEnviroment() {
		if (instance == null) {
			instance = new Enviroment();
		}
		return instance;
	}

	private GlobalSettings globalSettings;
	private JFrame frame;

	public GlobalSettings getGlobalSettings() {
		return globalSettings;
	}

	public void setGlobalSettings(GlobalSettings globalSetting) {
		this.globalSettings = globalSetting;
	}

	public Pair<Integer, Integer> getResolution() {
		return new Pair<Integer, Integer>(globalSettings.getResolution()[0], globalSettings.getResolution()[1]);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void updateFrame() {
		frame.setSize(getResolution().getValueOne(), getResolution().getValueTwo());
	}

}
