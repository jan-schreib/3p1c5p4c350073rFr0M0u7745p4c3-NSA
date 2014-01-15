package de.rType.menu;

import global.GlobalSettings;
import de.rType.main.Enviroment;
import de.rType.model.Pair;
import de.rType.repository.Recalculator;

/**
 * 
 * @author Jo
 * 
 */
public class ResolutionOption extends OptionMenuItem {

	private enum Resolution {

		SMALL(800, 600), MEDIUM(1024, 768), HIGH(1440, 1080);

		private Pair<Integer, Integer> res;

		private Resolution(int x, int y) {
			res = new Pair<Integer, Integer>(x, y);
		}

		public Pair<Integer, Integer> getValue() {
			return res;
		}
	}

	public ResolutionOption() {
		super("Aufl\u00F6sung", MenuItemKeys.RESULUTION);
	}

	@Override
	public String getValueText() {
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		return res.getValueOne() + "x" + res.getValueTwo();
	}

	@Override
	public void performChange() {
		GlobalSettings settings = Enviroment.getEnviroment().getGlobalSettings();
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		Resolution[] values = Resolution.values();
		Resolution newRes = null;
		for (int i = 0; i < values.length; i++) {
			if (values[i].getValue().getValueOne().equals(res.getValueOne())) {
				if (i < values.length - 1) {
					newRes = values[i + 1];
				} else {
					newRes = values[0];
				}
			}
		}
		Recalculator.getInstance().recalculate(res, newRes.getValue());
		settings.setResolution(newRes.getValue().getValueOne(), newRes.getValue().getValueTwo());
		Enviroment.getEnviroment().updateFrame();
	}
}
