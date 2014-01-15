package de.rType.model;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author jan schreib
 * @author Jo
 * 
 */
public class Laser extends GameObject {

	protected int damage = 4;
	private static final String LASER_UNCHARGED = "/de/rType/resources/beam_red_small.png";
	private static final String LASER_CHARGED = "/de/rType/resources/beam_red.png";
	private static final String LASER_FULL = "/de/rType/resources/beam_full.png";

	public Laser(int x, int y, int damage) {
		super(x, y, LASER_UNCHARGED, 4, damage);
		this.damage = damage;
		Image temp;
		if (this.damage <= 3) {
			temp = super.getImage();
		} else if (this.damage <= 6) {
			temp = new ImageIcon(this.getClass().getResource(LASER_CHARGED)).getImage();

		} else {
			temp = new ImageIcon(this.getClass().getResource(LASER_FULL)).getImage();
		}
		this.setImage(temp);
		this.hitbox.setSize(temp.getWidth(null), temp.getHeight(null));
		this.y -= (int) (this.hitbox.getHeight() / 2);
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void move() {
		super.move();
		if (getX() > resolution.getValueOne()) {
			hp = 0;
		}
	}

	@Override
	public void recalculate(Pair<Integer, Integer> resolution, double factorX, double factorY) {
		x = (int) Math.round(x * factorX);
		y = (int) Math.round(y * factorY);
	}
}
