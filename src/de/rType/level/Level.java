package de.rType.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.rType.main.GameBoard;
import de.rType.model.Alien;

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
			done = true;
		}

		public LevelTask getExecutionTask() {
			final LevelTask parent = this;
			return new LevelTask(this.alienList, this.addAlien, this.time) {
				@Override
				public void run() {
					super.run();
					parent.done = done;
				}
			};
		}

		public boolean isDone() {
			return done;
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
		this.levelTasks = new ArrayList<LevelTask>();
		this.levelTimer = new Timer();
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
	}

	/**
	 * Startet das Level bzw. laesst es weiterlaufen.
	 * 
	 */
	public void start() {
		this.levelTimer = new Timer();
		for (LevelTask t : levelTasks) {
			if (!t.isDone()) {
				levelTimer.schedule(t.getExecutionTask(), t.getTime());
			}
		}
	}

	protected abstract void performLevel();

}
