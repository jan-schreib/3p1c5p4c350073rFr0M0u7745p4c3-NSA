package de.rType.menu;

import global.GlobalSettings;
import de.rType.main.Enviroment;

public class MusicOptionItem extends OptionMenuItem {

	public MusicOptionItem() {
		super("Musik", MenuItemKeys.MUSIC);
	}

	@Override
	public String getValueText() {
		if (Enviroment.getEnviroment().getGlobalSettings().getMusicMuted()) {
			return "AUS";
		}
		return "AN";
	}

	@Override
	public void performChange() {
		GlobalSettings settings = Enviroment.getEnviroment()
				.getGlobalSettings();
		if (settings.getMusicMuted()) {
			settings.setMusicMuted(false);
		} else {
			settings.setMusicMuted(true);
		}
	}

}
