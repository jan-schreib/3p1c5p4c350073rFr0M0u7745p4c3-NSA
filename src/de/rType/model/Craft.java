package de.rType.model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.rType.main.Enviroment;

/**
 * 
 * @author Jo
 * 
 */
public class Craft extends GameObject {

	private static final String DEFAULT_CRAFT = "../resources/craft.png";

	private int dx;
	private int dy;
	private ArrayList<Missile> missiles;

	public Craft() {
		super(40, 60, DEFAULT_CRAFT, 1, 100);
		missiles = new ArrayList<Missile>();
	}

	public void move() {

		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	/**
	 * TODO Heraus fliegen aus dem frame muss verhindert werden
	 * && !((x + 3) > (res.getValueOne() - hitbox.width))
	 */
	/**
	 * TODO Heraus fliegen aus dem frame muss verhindert werden
	 * && !((y + 3) > (res.getValueTwo() - hitbox.height))
	 */
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		  switch(key){
	        case KeyEvent.VK_SPACE:
	        	fire();
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
		missiles.add(new Missile(this.hitbox.x + hitbox.width, this.hitbox.y + (hitbox.height / 2) - 5));
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		  switch(key){
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