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
import de.rType.model.BossEnemy;
import de.rType.model.Pair;
import de.rType.repository.AlienRepository;
import de.rType.resources.GameFonts;
import de.rType.view.GameBoard;

/**
 * 
 * @author Jo
 * 
 */
public class LevelOne extends LevelBase {

	private static final String LEVEL_TEXT = "Level 1";

	public LevelOne(GameBoard gameBoard) {
		super(gameBoard);
	}

	@Override
	protected void initializeEnemys() {
		long time = 600;
		Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();
		Alien arr[] = { new AlienOne(), new AlienTwo(), new AlienThree() };
		long t = 0;
		for (int i = 0; i < 15; i++) {
			t = time * i;
			int randAlien = (int) (Math.random() * 3);
			int randPos = (int) (Math.random() * resolution.getValueTwo());
			Alien a = AlienRepository.getInstance().get(arr[randAlien].getClass());
			a.setPosition(resolution.getValueOne(), randPos);
			this.addAlien(t, a);
		}
		Alien boss = AlienRepository.getInstance().get(BossEnemy.class);
		this.addAlien(t + 250, boss);
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
