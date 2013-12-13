package de.rType.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.rType.model.Alien;
import de.rType.resources.GameFonts;
import de.rType.view.GameBoard;

/**
 * Base Level class
 * 
 * @author Jo
 * 
 */
public abstract class Level {

	private GameBoard gameBoard;
	private Timer levelTimer;
	private List<LevelTask> levelTasks;
	private long levelStartTime = 0;
	private long levelTime = 0;
	private boolean initialized = false;

	private class LevelTask extends TimerTask {

		private long time;
		public List<Alien> alienList;
		public Alien addAlien;
		private boolean done = false;

		public LevelTask(List<Alien> alienList, Alien alien, long time) {
			this.alienList = alienList;
			this.addAlien = alien;
			this.time = time;
		}

		@Override
		public void run() {
			alienList.add(addAlien);
		}

		public LevelTask getExecutionTask() {
			final LevelTask parent = this;
			return new LevelTask(this.alienList, this.addAlien, this.time) {
				@Override
				public void run() {
					super.run();
					parent.setDone();
				}
			};
		}

		public boolean isDone() {
			return done;
		}

		public void setDone() {
			this.done = true;
		}

		/**
		 * @return the time
		 */
		public long getTime() {
			return time;
		}
	}

	public Level(GameBoard gameBoard) {
		super();
		this.gameBoard = gameBoard;
	}

	/**
	 * Fuegt Alien in time zum Spielfeld hinzu
	 * 
	 * @param time
	 * @param alien
	 */
	protected void addAlien(long time, Alien alien) {
		LevelTask t = new LevelTask(gameBoard.getCurrentAliens(), alien, time);
		levelTasks.add(t);
	}

	/**
	 * Pausiert das Level
	 */
	public void pause() {
		this.levelTimer.cancel();
		this.levelTimer.purge();
		this.levelTime += (GregorianCalendar.getInstance().getTimeInMillis() - this.levelStartTime);
	}

	/**
	 * Startet das Level bzw. laesst es weiterlaufen.
	 * 
	 */
	public void start() {
		if (!initialized) {
			initialize();

		}
		this.levelTimer = new Timer();
		this.levelStartTime = GregorianCalendar.getInstance().getTimeInMillis();
		for (LevelTask t : levelTasks) {
			if (!t.isDone()) {
				long delay = t.getTime() - levelTime;
				if (delay < 0) {
					delay = 0;
				}
				levelTimer.schedule(t.getExecutionTask(), delay);
			}
		}
	}

	private void initialize() {
		initialized = true;
		this.levelTasks = new ArrayList<LevelTask>();
		performLevel();
	}

	public boolean isDone() {
		if (!initialized) {
			return false;
		}
		for (LevelTask t : levelTasks) {
			if (!t.isDone()) {
				return false;
			}
		}
		return true;
	}

	public void drawLevel(Graphics g) {
		// Ersten 1.5 Sek
		// TODO LEVEL TIME + TASKS
		if ((levelStartTime + this.levelTime + 1500) > GregorianCalendar.getInstance().getTimeInMillis()) {
			Font normal = GameFonts.BIG;
			FontMetrics metrix = this.gameBoard.getFontMetrics(normal);
			g.setFont(normal);
			g.setColor(Color.YELLOW);
			drawLevelText(g, metrix);
		}
	}

	protected abstract void performLevel();

	protected abstract void drawLevelText(Graphics g, FontMetrics metrix);

}
