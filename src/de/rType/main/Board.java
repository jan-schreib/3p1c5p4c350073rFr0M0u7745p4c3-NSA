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
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.Timer;

import de.rType.model.Alien;
import de.rType.model.Craft;
import de.rType.model.Missile;

/**
 * Main Game Class
 * 
 * @author Jo
 * 
 */
public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Timer timer;
	private Craft craft;
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;
	public int points = 0;

	public Board() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ingame = true;

		setSize(1200, 720);

		craft = new Craft();

		java.util.Timer t = new java.util.Timer();
        t.schedule(
                new TimerTask(){
                    
                    @Override
                    public void run() {
                    	initAliens(5 + (int) (Math.random() * 10)
                        );
                    }
                
                }, 
                0, 
                5000);

		timer = new Timer(5, this);
		timer.start();
	}

	public void addNotify() {
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
	}

	public void initAliens(int count) {

		int pos = 0 + (int)(Math.random() * 720);

		for (int i = 0; i < count; i++) {
			aliens.add(new Alien(1200+(i*50), pos));
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (ingame) {

			Graphics2D g2d = (Graphics2D) g;

			if (craft.isVisible())
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),
						this);

			ArrayList<Missile> ms = craft.getMissiles();

			for (int i = 0; i < ms.size(); i++) {
				Missile m = (Missile) ms.get(i);
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++) {
				Alien a = (Alien) aliens.get(i);
				if (a.isAlive())
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
			}

			g2d.setColor(Color.WHITE);
			g2d.drawString("Points: " + points, 5, 15);

		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
					B_HEIGHT / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {

		// initAliens(1 + (int)(Math.random() * 20));

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
			if (a.isAlive())
				a.move();
			else {
				aliens.remove(i);
				points += 10;
			}
		}

		craft.move();
		checkCollisions();
		repaint();
	}

	public void checkCollisions() {

		Rectangle r3 = craft.getBounds();

		for (int j = 0; j < aliens.size(); j++) {
			Alien a = (Alien) aliens.get(j);
			Rectangle r2 = a.getHitbox();

			if (r3.intersects(r2)) {
				craft.setVisible(false);
				aliens.remove(a); // a.setVisible(false);
				ingame = false;
			}
		}

		ArrayList<Missile> ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++) {
			Missile m = ms.get(i);

			Rectangle r1 = m.getHitbox();

			for (int j = 0; j < aliens.size(); j++) {
				Alien a = (Alien) aliens.get(j);
				Rectangle r2 = a.getHitbox();

				if (r1.intersects(r2)) {
					ms.remove(m); // m.setVisible(false)
					a.hit();
				}
			}
		}
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			craft.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			craft.keyPressed(e);
		}
	}
}