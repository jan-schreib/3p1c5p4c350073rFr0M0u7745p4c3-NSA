package de.rType.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;

import de.rType.main.Enviroment;
import de.rType.util.Sound;

/**
 * 
 * @author Jo
 * 
 */
public class Craft extends GameObject {

	private static final String DEFAULT_CRAFT = "/de/rType/resources/craft.png";

	private int dx;
	private int dy;
	private int firepower = 0;
	private static int X_MAX = Enviroment.getEnviroment().getResolution().getValueOne();
	private static int Y_MAX = 500;
	private static int X_MIN = 0;
	private static int Y_MIN = 0;
	private List<Laser> lasers;

	public Craft() {
		super(40, 60, DEFAULT_CRAFT, 1, 100);
		X_MAX = Enviroment.getEnviroment().getResolution().getValueOne() - hitbox.width;
		Y_MAX = Enviroment.getEnviroment().getResolution().getValueTwo() - hitbox.height;
	}

	public void setMissileList(List<Laser> missiles) {
		this.lasers = missiles;
	}

	@Override
	public void recalculate(Pair<Integer, Integer> resolution, double factorX, double factorY) {
		Image current = this.getImage();
		int newWidth = Math.round((float) (current.getWidth(null) * factorX));
		int newHeight = Math.round((float) (current.getHeight(null) * factorY));
		Image newImage = current.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		ImageIcon icon = new ImageIcon(newImage);
		this.setImage(icon.getImage());
		int newX = Math.round((float) (factorX * x));
		setX(newX);
		int newY = Math.round((float) (factorY * y));
		setY(newY);
		X_MAX = resolution.getValueOne() - (hitbox.width);
		Y_MAX = resolution.getValueTwo() - (hitbox.height);

	}

	public int getFirePower() {
		return this.firepower;
	}

	public void move() {

		if (x < X_MAX && x > X_MIN) {
			x += dx;
		} else {
			x--;
		}
		if (y < Y_MAX && y > Y_MIN) {
			y += dy;
		} else {
			y--;
		}

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
	}

	public List<Laser> getLasers() {
		return lasers;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			firepower++;
			Sound.BUILD_UP.play();
			break;
		case KeyEvent.VK_LEFT:
			dx = -3;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 3;
			break;
		case KeyEvent.VK_UP:
			dy = -3;
			break;
		case KeyEvent.VK_DOWN:
			dy = 3;
			break;
		default:
			break;
		}
	}

	public void fire() {
		Rectangle hitbox = this.getHitbox();
		lasers.add(new Laser(hitbox.x + hitbox.width, hitbox.y + (hitbox.height / 2), this.firepower));
		Sound.BUILD_UP.stop();
		if (this.firepower <= 3) {
			Sound.LASER_SMALL.play();
		} else if (this.firepower <= 6) {
			Sound.LASER_MEDIUM.play();
		} else {
			Sound.LASER_HUGE.play();
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			fire();
			firepower = 0;
			break;
		case KeyEvent.VK_LEFT:
			dx = 0;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 0;
			break;
		case KeyEvent.VK_UP:
			dy = 0;
			break;
		case KeyEvent.VK_DOWN:
			dy = 0;
			break;
		default:
			break;
		}
	}
}