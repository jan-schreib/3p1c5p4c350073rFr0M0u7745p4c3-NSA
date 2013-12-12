package de.rType.level;

import java.awt.FontMetrics;
import java.awt.Graphics;

import de.rType.main.Enviroment;
import de.rType.model.Alien;
import de.rType.model.AlienOne;
import de.rType.model.AlienThree;
import de.rType.model.AlienTwo;
import de.rType.model.Pair;
import de.rType.repository.AlienRepository;
import de.rType.view.GameBoard;

/**
 * 
 * @author Jo
 * 
 */
public class LevelTwo extends Level {

	private static final String LEVEL_TEXT = "Level 2";

	public LevelTwo(GameBoard gameBoard) {
		super(gameBoard);
	}

	@Override
	protected void performLevel() {
		long time = 600;
		Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();
		Alien arr[] = { new AlienOne(), new AlienTwo(), new AlienThree() };

		for (int i = 0; i < 5; i++) {
			long t = time * i;
			int randAlien = (int) (Math.random() * 3);
			int randPos = (int) (Math.random() * resolution.getValueTwo());
			Alien a = AlienRepository.getInstance().get(arr[randAlien].getClass());// AlienThree.class);
			a.setPosition(resolution.getValueOne(), randPos);
			this.addAlien(t, a);
		}
	}

	@Override
	protected void drawLevelText(Graphics g, FontMetrics metrix) {
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		int y = (res.getValueTwo() / 2);
		g.drawString(LEVEL_TEXT, (res.getValueOne() - metrix.stringWidth(LEVEL_TEXT)) / 2, y);
	}
}
