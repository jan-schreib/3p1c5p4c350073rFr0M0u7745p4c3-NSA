package de.rType.model;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

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
	
	private static int X_MAX = 730; 
	private static int Y_MAX = 500;
	private static int X_MIN = 0;
	private static int Y_MIN = 0;
	private LinkedList<Missile> missiles;

	public Craft() {
		super(40, 60, DEFAULT_CRAFT, 1, 100);
		missiles = new LinkedList<Missile>();
	}

	public void move() {
		
		if(x < X_MAX && x > X_MIN) {
			x += dx;
		}
		else {
			x--;
		}
		if(y < Y_MAX && y > Y_MIN) {
			y += dy;
		}
		else {
			y--;
		}

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
	}

	public LinkedList<Missile> getMissiles() {
		return missiles;
	}
	
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
		Sound.fire.play();
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