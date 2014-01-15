package de.rType.level;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import de.rType.main.Enviroment;
import de.rType.model.Alien;
import de.rType.model.AlienOne;
import de.rType.model.AlienThree;
import de.rType.model.AlienTwo;
import de.rType.model.BadBossEnemy;
import de.rType.model.BossEnemy;
import de.rType.model.Pair;
import de.rType.repository.AlienRepository;
import de.rType.resources.GameFonts;
import de.rType.util.Sound;
import de.rType.view.GameBoard;

/**
 * 
 * @author Jo
 * 
 */
public class LevelTwo extends LevelBase {

	private static final String LEVEL_TEXT = "Level 2";

	public LevelTwo(GameBoard gameBoard) {
		super(gameBoard);
	}

	@Override
	protected void initializeEnemys() {
		long time = 600;
		Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();
		Alien arr[] = { new AlienOne(), new AlienTwo(), new AlienThree() };

		long t = 0;
		for (int i = 0; i < 45; i++) {
			t = time * i;
			int randAlien = (int) (Math.random() * 3);
			int randPos = (int) (Math.random() * resolution.getValueTwo());
			Alien a = AlienRepository.getInstance().get(arr[randAlien].getClass());
			a.setPosition(resolution.getValueOne(), randPos);
			this.addAlien(t, a);
		}
	}

	@Override
	public void start() {
		super.start();
		Sound.LVL2.play_music();
	}

	@Override
	public void pause() {
		super.pause();
		Sound.LVL2.stop();
	}

	@Override
	protected BossEnemy createLevelBoss() {
		BossEnemy enemy = (BossEnemy) AlienRepository.getInstance().get(BadBossEnemy.class);
		return enemy;
	}

	@Override
	protected void initializeDrawTasks() {
		this.addDrawTask(new LevelDrawTask(0, 3000) {

			@Override
			public void drawTask(Graphics g) {
				Font normal = GameFonts.BIG;
				FontMetrics metrix = gameBoard.getFontMetrics(normal);
				g.setFont(normal);
				g.setColor(Color.YELLOW);
				Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
				int y = (res.getValueTwo() / 2);
				g.drawString(LEVEL_TEXT, (res.getValueOne() - metrix.stringWidth(LEVEL_TEXT)) / 2, y);
			}
		});
	}
}
