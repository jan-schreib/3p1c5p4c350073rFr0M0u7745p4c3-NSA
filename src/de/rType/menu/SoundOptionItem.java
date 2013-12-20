package de.rType.menu;

import global.GlobalSettings;
import de.rType.main.Enviroment;

public class SoundOptionItem extends OptionMenuItem {

	public SoundOptionItem() {
		super("Soundeffekte", MenuItemKeys.SOUND);
	}

	@Override
	public String getValueText() {
		if (Enviroment.getEnviroment().getGlobalSettings()
				.getSoundeffectsMuted()) {
			return "AUS";
		}
		return "AN";
	}

	@Override
	public void performChange() {
		GlobalSettings settings = Enviroment.getEnviroment()
				.getGlobalSettings();
		if (settings.getSoundeffectsMuted()) {
			settings.setSoundeffectsMuted(false);
		} else {
			settings.setSoundeffectsMuted(true);
		}
	}

}
