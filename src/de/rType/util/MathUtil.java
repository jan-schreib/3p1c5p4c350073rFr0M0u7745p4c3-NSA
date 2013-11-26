package de.rType.util;

import de.rType.model.Pair;

public class MathUtil {

	/**
	 * 
	 * 
	 * @param maxX
	 * @param minX
	 * @param height
	 * @return
	 */
	public static Pair<Integer, Integer> getMinMaxSinus(int maxX, int minX, int height, int y) {
		int minY = maxX;
		int maxY = 0;
		for (int i = minX; i < maxX; i++) {
			int sinY = (int) (Math.sin(i * 1 / 50) * 100);
			if (sinY < minY) {
				minY = sinY;
			} else if (sinY > maxY) {
				maxY = sinY;
			}
		}
		return new Pair<Integer, Integer>(y + minY, y + maxY);
	}

}
