package de.rType.model;

import de.rType.util.MathUtil;

/**
 * Base Enemy Alien
 * 
 */
public class Alien extends GameObject {

	private int startX;
	private int startY;
	private static int START_HP = 0;

	private Pair<Integer, Integer> sinus;
	private boolean up = true;

	public Alien() {
		super(0, 0, "../resources/alien_small.png", -1, START_HP);
	}

	public Alien(int x, int y) {
		super(x, y, "../resources/alien_small.png", -1, START_HP);
		sinus = MathUtil.getMinMaxSinus(x, 0, 128, y);
	}

	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		sinus = MathUtil.getMinMaxSinus(x, 0, 128, y);
		startX = x;
		startY = y;
	}

	@Override
	public void move() {
		super.move();
		if (up) {
			if (this.y > sinus.getValueOne()) {
				this.y--;
			}
			if (this.y == sinus.getValueOne()) {
				up = false;
			}
		} else {
			if (this.y < sinus.getValueTwo()) {
				this.y++;
			}
			if (this.y == sinus.getValueTwo()) {
				up = true;
			}
		}
	}

	public void reset() {
		setPosition(startX, startY);
		setHp(START_HP);
	}
}