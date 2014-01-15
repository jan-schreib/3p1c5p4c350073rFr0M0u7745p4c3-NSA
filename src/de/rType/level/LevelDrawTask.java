package de.rType.level;

import java.awt.Graphics;

/**
 * 
 * @author Jo
 * 
 */
public abstract class LevelDrawTask {

	private long startTime;
	private long endTime;

	public LevelDrawTask(long startTime, long endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public abstract void drawTask(Graphics g);

	public boolean isActive(long levelTime) {
		if (levelTime >= startTime && levelTime <= endTime) {
			return true;
		}
		return false;
	}

}
