package de.rType.menu;

import global.GlobalSettings;
import de.rType.main.Enviroment;
import de.rType.util.Sound;

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
		GlobalSettings settings = Enviroment.getEnviroment().getGlobalSettings();
		if (settings.getMusicMuted()) {
			settings.setMusicMuted(false);
			Sound.TITLE_MUSIC.stop();
		} else {
			settings.setMusicMuted(true);
			Sound.TITLE_MUSIC.play_music();
		}
	}

}
