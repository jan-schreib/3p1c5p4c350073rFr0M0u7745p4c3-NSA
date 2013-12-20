package de.rType.model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missile extends GameObject {

	protected int damage = 1;
	private static final String LASER_UNCHARGED = "/de/rType/resources/beam_red_small.png";
	private static final String LASER_CHARGED = "/de/rType/resources/craft.png";
	private static final String LASER_FULL = "/de/rType/resources/alien_small.png";
	private ImageIcon charged;
	private ImageIcon full;
	
	public Missile(int x, int y, int damage) {
		super(x, y, LASER_UNCHARGED, 2, damage);
		this.damage = damage;
		charged = new ImageIcon(this.getClass().getResource(LASER_CHARGED));
		full = new ImageIcon(this.getClass().getResource(LASER_FULL));
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
	@Override
	public Image getImage() {
		Image temp;
		if (this.damage <= 3){
			temp = super.getImage();
		}
		else if (this.damage <= 6){
			temp = charged.getImage();
			
		}
		else {
			temp =full.getImage();
		}
		this.hitbox.setSize(temp.getWidth(null), temp.getHeight(null));
		return temp;
	}
}
