package de.rType.main;

import de.collectionGame.GlobalSettings;
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
	private Pair<Integer, Integer> resolution;

	public GlobalSettings getGlobalSettings() {
		return globalSettings;
	}

	public void setGlobalSettings(GlobalSettings globalSetting) {
		this.globalSettings = globalSetting;
		this.resolution = new Pair<Integer, Integer>(globalSetting.getResolution()[0], globalSetting.getResolution()[1]);
	}

	public Pair<Integer, Integer> getResolution() {
		return resolution;
	}

}
