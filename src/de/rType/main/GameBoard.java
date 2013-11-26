package de.rType.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import de.rType.level.Level;
import de.rType.level.LevelOne;
import de.rType.model.Alien;
import de.rType.model.Craft;
import de.rType.model.Missile;
import de.rType.model.Pair;

/**
 * Main Game Class
 * 
 * @author Jo
 * 
 */
public abstract class GameBoard extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Timer timer;
	private Craft craft;
	private Level currentLevel;
	private ArrayList<Alien> aliens = new ArrayList<Alien>();

	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;
	public int points = 0;

	public GameBoard() {
		addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
					craft.keyReleased(e);
				}
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					escapePressed();
				} else {
					craft.keyPressed(e);
				}
			}
		});
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		ingame = true;
		craft = new Craft();
		timer = new Timer(5, this);

		// Temporary add level here
		currentLevel = new LevelOne(this);
	}

	public abstract void escapePressed();

	public void start() {
		currentLevel.start();
		timer.start();
	}

	public void pause() {
		currentLevel.pause();
		timer.stop();
	}

	public void addNotify() {
		super.addNotify();
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		B_WIDTH = res.getValueOne();
		B_HEIGHT = res.getValueTwo();
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (ingame) {

			Graphics2D g2d = (Graphics2D) g;

			if (craft.isAlive()) {
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
			}
			ArrayList<Missile> ms = craft.getMissiles();

			for (int i = 0; i < ms.size(); i++) {
				Missile m = (Missile) ms.get(i);
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++) {
				Alien a = (Alien) aliens.get(i);
				if (a.isAlive()) {
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
				}
			}

			g2d.setColor(Color.WHITE);
			g2d.drawString("Points: " + points, 5, 15);

		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {

		ArrayList<Missile> ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++) {
			Missile m = ms.get(i);
			if (m.isAlive())
				m.move();
			else
				ms.remove(i);
		}

		for (int i = 0; i < aliens.size(); i++) {
			Alien a = (Alien) aliens.get(i);
			if (a.isAlive()) {
				a.move();
				if (a.isGoneOut()) {
					aliens.remove(i);
				}
			} else {
				aliens.remove(i);
			}
		}

		craft.move();
		checkCollisions();
		repaint();
	}

	public void checkCollisions() {

		Rectangle hitboxCraft = craft.getHitbox();
		ArrayList<Missile> ms = craft.getMissiles();
		//Player collides with Alien.
		for (int j = 0; j < aliens.size(); j++) {
			Alien a = aliens.get(j);
			Rectangle hitboxAlien = a.getHitbox();
			if (hitboxCraft.intersects(hitboxAlien) && a.isAlive()) {
				craft.criticalHit();
				a.criticalHit();
				aliens.remove(a);
				ingame = false;
			}
			//Missile vs. Alien.
			for (int i = 0; i < ms.size(); i++) {
				Missile m = ms.get(i);

				Rectangle hitBoxMissile = m.getHitbox();

				if (hitBoxMissile.intersects(hitboxAlien) && a.isAlive()) {
					ms.remove(m);
					System.out.println("Vorher: " + a.getHp());
					a.hit(m.getDamage());
					System.out.println("Nacher: " + a.getHp());
					points += 10;
					if (!a.isAlive()) {
						aliens.remove(a);
					}
				}
			}
		}

	}

	/**
	 * Gibt die Liste der aktuell auf dem Board aktiven Alien zur�ck.
	 * 
	 * @return
	 */
	public ArrayList<Alien> getCurrentAliens() {
		return aliens;
	}
}