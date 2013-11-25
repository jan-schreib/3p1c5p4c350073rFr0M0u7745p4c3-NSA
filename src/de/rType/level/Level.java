package de.rType.level;

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
public abstract class Level extends Timer {

	private GameBoard gameBoard;

	private class LevelTask extends TimerTask {

		public List<Alien> alienList;
		public Alien addAlien;

		public LevelTask(List<Alien> alienList, Alien alien) {
			this.alienList = alienList;
			this.addAlien = alien;
		}

		@Override
		public void run() {
			alienList.add(addAlien);
		}
	}

	public Level(GameBoard gameBoard) {
		super();
		this.gameBoard = gameBoard;
	}

	protected void addAlien(long time, Alien alien) {
		this.schedule(new LevelTask(gameBoard.getCurrentAliens(), alien), time);
	}

	protected abstract void performLevel();

}
