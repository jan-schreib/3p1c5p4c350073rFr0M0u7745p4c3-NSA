package de.rType.model;

public class Missile extends GameObject {

	protected int damage = 1;

	public Missile(int x, int y) {
		super(x, y, "/de/rType/resources/beam_red_small.png", 2, 1);
	}

	public int getDamage() {
		return damage;
	}
	
	@Override
	public void move() {
		super.move();
		if(getX() > resolution.getValueOne()){
			damage = 0;
		}
	}

}
