package de.rType.level;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

import de.rType.model.Alien;
import de.rType.view.GameBoard;

/**
 * Baseclass for Levels.
 * 
 * @author Jo
 * 
 */
public abstract class LevelBase implements ActionListener {

	private static final int LEVEL_INTERVAL = 5;

	protected GameBoard gameBoard;
	private long levelTime = 0;
	private Timer timer;
	private List<EnemyTask> enemyTasks = new ArrayList<EnemyTask>();
	private List<LevelDrawTask> drawTasks = new ArrayList<LevelDrawTask>();

	private class EnemyTask {

		private long time;
		public Alien addAlien;

		public EnemyTask(Alien alien, long time) {
			this.addAlien = alien;
			this.time = time;
		}

		private void perform(List<Alien> aliens) {
			aliens.add(this.addAlien);
		}

		public long getTime() {
			return this.time;
		}
	}

	public LevelBase(GameBoard gameBoard) {
		super();
		this.gameBoard = gameBoard;
		this.timer = new Timer(LEVEL_INTERVAL, this);
		this.initializeEnemys();
		this.initializeDrawTasks();
	}

	protected abstract void initializeEnemys();

	protected abstract void initializeDrawTasks();

	/**
	 * Fuegt Alien in time zum Spielfeld hinzu
	 * 
	 * @param time
	 * @param alien
	 */
	protected void addAlien(long time, Alien alien) {
		// Check time is on interval
		if (time % LEVEL_INTERVAL != 0) {
			time = time - (time % LEVEL_INTERVAL);
		} else if (time == 0) {
			// Time 0 is not allowed!
			time = LEVEL_INTERVAL;
		}
		alien.setLasers(gameBoard.getCurrentLasers());
		enemyTasks.add(new EnemyTask(alien, time));
	}

	protected void addDrawTask(LevelDrawTask task) {
		this.drawTasks.add(task);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		levelTime += LEVEL_INTERVAL;
		List<EnemyTask> openList = new LinkedList<EnemyTask>();
		for (EnemyTask t : enemyTasks) {
			if (t.getTime() == levelTime) {
				t.perform(this.gameBoard.getCurrentAliens());
			} else {
				openList.add(t);
			}
		}
		// Clear done tasks from list.
		enemyTasks = openList;
	}

	/**
	 * Pausiert das Level
	 */
	public void pause() {
		this.timer.stop();
	}

	/**
	 * Startet das Level bzw. laesst es weiterlaufen.
	 * 
	 */
	public void start() {
		this.timer.start();
	}

	public boolean isDone() {
		return enemyTasks.size() == 0;
	}

	public void drawLevel(Graphics g) {
		for (LevelDrawTask t : drawTasks) {
			if (t.isActive(levelTime)) {
				t.drawTask(g);
			}
		}
	}
}
