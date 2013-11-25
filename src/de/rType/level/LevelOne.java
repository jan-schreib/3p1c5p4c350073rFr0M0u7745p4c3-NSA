package de.rType.level;

import de.rType.main.Enviroment;
import de.rType.main.GameBoard;
import de.rType.model.Alien;
import de.rType.model.Pair;
import de.rType.repository.AlienRepository;

/**
 * 
 * @author Jo
 * 
 */
public class LevelOne extends Level {

	public LevelOne(GameBoard gameBoard) {
		super(gameBoard);
		performLevel();
	}

	@Override
	protected void performLevel() {
		long time = 400;

		for (int i = 0; i < 100; i++) {
			long t = time * i;
			if (i > 10) {
				t += (300 * i);
			}
			Alien a = AlienRepository.getInstance().get(Alien.class);
			Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();
			a.setPosition(resolution.getValueOne(), resolution.getValueTwo() - 100);
			this.addAlien(t, a);
		}
	}
}
