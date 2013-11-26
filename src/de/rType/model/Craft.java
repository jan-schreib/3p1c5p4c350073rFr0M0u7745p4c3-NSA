package de.rType.model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.rType.main.Enviroment;
import de.rType.util.Sound;

/**
 * 
 * @author Jo
 * 
 */
public class Craft extends GameObject {

	private static final String DEFAULT_CRAFT = "../resources/craft.png";

	private int dx;
	private int dy;
	
	private static int X_MAX = 730; 
	private static int Y_MAX = 500;
	private static int X_MIN = 0;
	private static int Y_MIN = 0;
	private ArrayList<Missile> missiles;

	public Craft() {
		super(40, 60, DEFAULT_CRAFT, 1, 100);
		missiles = new ArrayList<Missile>();
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
	        	System.out.println(this.getX());
	        	System.out.println(this.getY());
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