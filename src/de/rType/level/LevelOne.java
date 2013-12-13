package de.rType.level;

import de.rType.main.Enviroment;
import de.rType.main.GameBoard;
import de.rType.model.Alien;
import de.rType.model.AlienOne;
import de.rType.model.AlienThree;
import de.rType.model.AlienTwo;
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
		long time = 600;
		Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();
		Alien arr[] = {new AlienOne(),new AlienTwo(), new AlienThree()};

		for (int i = 0; i < 100; i++) {
			long t = time * i;
			int randAlien = (int)(Math.random() * 3);
			System.out.println(randAlien);
			int randPos = (int)(Math.random()* (resolution.getValueTwo()));
			Alien a = AlienRepository.getInstance().get(arr[randAlien].getClass());	
			a.setPosition(resolution.getValueOne(), randPos);
			this.addAlien(t, a);
		}
	}
}
