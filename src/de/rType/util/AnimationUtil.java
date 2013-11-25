package de.rType.util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.rType.model.Alien;

public class AnimationUtil {

	/**
	 * Fügt die gegebenen Aliens im Abstand von startDifference zum Spiel
	 * hinzu.
	 * 
	 * @param currentList
	 * @param startList
	 * @param startDifference
	 */
	public static void startAliens(final List<Alien> currentList,
			final List<Alien> startList, int startDifference) {
		final Timer t = new Timer();
		int idx = 0;
		for (final Alien start : startList) {
			t.schedule(new TimerTask() {

				@Override
				public void run() {
					currentList.add(start);
				}
			}, startDifference * idx, startDifference);
			idx++;
		}
	}
}
