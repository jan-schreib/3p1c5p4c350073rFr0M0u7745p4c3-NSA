package de.rType.model;


/**
 * Base Enemy Alien
 * 
 */
public class Alien extends GameObject {

	private int startX;
	private int startY;
	private static int START_HP = 1;
	

	public Alien() {
		super(0, 0, "/de/rType/resources/alien.png", -1, START_HP);
	}

	public Alien(int x, int y) {
		super(x, y, "/de/rType/resources/alien.png", -1, START_HP);
	}

	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		startX = x;
		startY = y;
	}

	@Override
	public void move() {
		super.move();		
	}
	

	
	public void reset() {
		setPosition(startX, startY);
		setHp(START_HP);
	}
}