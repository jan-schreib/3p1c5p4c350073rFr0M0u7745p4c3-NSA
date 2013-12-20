package de.rType.model;

public class Missile extends GameObject {

	protected int damage = 1;

	public Missile(int x, int y, int damage) {
		super(x, y, "/de/rType/resources/beam_red_small.png", 2, damage);
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int firepower) {
		damage = firepower;
	}

	@Override
	public void move() {
		super.move();
		if (getX() > resolution.getValueOne()) {
			damage = 0;
		}
	}

}
