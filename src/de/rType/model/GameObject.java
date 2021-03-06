package de.rType.model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import de.rType.main.Enviroment;

/**
 * Base class for all game objects.
 * 
 * @author Jo
 * 
 */
abstract public class GameObject implements Recalculable {

	protected int x;
	protected int y;
	protected Rectangle hitbox;
	private Image image;
	private int speed;
	protected int hp; // Healthpoints
	protected Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();

	public GameObject(int x, int y, String image, int speed, int hp) {
		this.x = x;
		this.y = y;
		ImageIcon i = new ImageIcon(this.getClass().getResource(image));
		this.image = i.getImage();
		this.speed = speed;
		this.hp = hp;
		this.hitbox = new Rectangle(x, y, this.image.getWidth(null), this.image.getHeight(null));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.hitbox.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		y = y - hitbox.height;
		this.y = y;
		this.hitbox.y = y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getHitbox() {
		hitbox.setLocation(x, y);
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
		this.hitbox = new Rectangle(x, y, this.image.getWidth(null), this.image.getHeight(null));
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void move() {
		x += speed;
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		if (x > res.getValueOne() || x < 0) {
			hp = 0;
		}
	}

	public boolean isGoneOut() {
		return x <= 0;
	}

	public boolean isAlive() {
		return (hp > 0);
	}

	public void hit(int damage) {
		this.hp = this.hp - damage;
	}

	public void criticalHit() {
		this.hp = -1;
	}
}
