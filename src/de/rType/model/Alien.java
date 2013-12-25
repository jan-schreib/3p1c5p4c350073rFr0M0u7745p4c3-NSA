package de.rType.model;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

import de.rType.util.Sound;

/**
 * Base Enemy Alien
 * 
 */
public class Alien extends GameObject {

	private int startX;
	private int startY;
	private static int START_HP = 1;

	private List<Laser> lasers;

	public Alien() {
		super(0, 0, "/de/rType/resources/alien.png", -1, START_HP);
	}

	public Alien(int x, int y) {
		super(x, y, "/de/rType/resources/alien.png", -1, START_HP);
	}

	public void setLasers(List<Laser> missiles) {
		this.lasers = missiles;
	}

	public int getScore() {
		return 10;
	}

	protected void fire() {
		Laser l = new Laser(this.hitbox.x - 50, this.hitbox.y + (hitbox.height / 2), 1);
		l.setSpeed(-3);
		l.setImage(new ImageIcon(this.getClass().getResource("/de/rType/resources/beam_blue_small.png")).getImage());
		lasers.add(l);
		Sound.LASER_SMALL.play();
	}

	public void setPosition(int x, int y) {

		if (y <= 15) {
			y = 15;
		} else if (y >= resolution.getValueTwo() - 40) {
			y = resolution.getValueTwo() - 40;
		}
		super.setPosition(x, y);
		startX = x;
		startY = y;
	}

	@Override
	public void move() {
		super.move();
	}

	@Override
	public void recalculate(Pair<Integer, Integer> resolution, double factorX, double factorY) {

		Image current = this.getImage();
		int newWidth = Math.round((float) (current.getWidth(null) * factorX));
		int newHeight = Math.round((float) (current.getHeight(null) * factorY));
		Image newImage = current.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(newImage);
		this.setImage(icon.getImage());

		startX = Math.round((float) (startX * factorX));
		startY = Math.round((float) (startY * factorY));
		x = Math.round((float) (x * factorX));
		y = Math.round((float) (y * factorY));
	}

	public void reset() {
		setPosition(startX, startY);
		setHp(START_HP);
	}
}