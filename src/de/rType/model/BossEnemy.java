package de.rType.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import de.rType.main.Enviroment;

/**
 * 
 * @author Jo
 * 
 */
public class BossEnemy extends Alien implements ActionListener {

	private Timer timer;

	private int minY = 50;
	private int maxY = 0;

	private enum DIR {
		UP, DOWN
	}

	private DIR currentDirection = DIR.UP;

	public BossEnemy() {
		super();
		timer = new Timer(1000, this);
		setImage(new ImageIcon(this.getClass().getResource("/de/rType/resources/alien90.png")).getImage());
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		minY = Math.round((float) (res.getValueTwo() * 0.3));
		maxY = Math.round((float) (res.getValueTwo() * 0.7));
		setPosition(res.getValueOne() - 150, Math.round((float) (res.getValueTwo() * 0.5)));
		setHp(20);
	}

	@Override
	public void recalculate(Pair<Integer, Integer> resolution, double factorX, double factorY) {
		super.recalculate(resolution, factorX, factorY);
		minY = Math.round((float) (minY * factorX));
		maxY = Math.round((float) (maxY * factorX));
	}

	public int getScore() {
		return 50;
	}

	@Override
	public void move() {
		timer.start();
		int moveSpeed = getSpeed() * -1 * 2;
		if (currentDirection.equals(DIR.UP)) {
			if (y > minY) {
				y -= moveSpeed;
			} else {
				currentDirection = DIR.DOWN;
			}
		} else if (currentDirection.equals(DIR.DOWN)) {
			if (y < maxY) {
				y += moveSpeed;
			} else {
				currentDirection = DIR.UP;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isAlive() && !isGoneOut()) {
			fire();
		} else {
			timer.stop();
		}
	}
}
