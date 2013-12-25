package de.rType.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.rType.level.LevelBase;
import de.rType.level.LevelOne;
import de.rType.level.LevelThree;
import de.rType.level.LevelTwo;
import de.rType.main.Enviroment;
import de.rType.model.Alien;
import de.rType.model.Craft;
import de.rType.model.Laser;
import de.rType.model.Pair;
import de.rType.model.Recalculable;
import de.rType.model.RecalculableContainer;
import de.rType.repository.Recalculator;
import de.rType.resources.GameFonts;
import de.rType.util.Sound;

/**
 * Main Game Class
 * 
 * @author Jo
 * 
 */
public abstract class GameBoard extends JPanel implements ActionListener, RecalculableContainer {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Craft craft;
	private LevelBase currentLevel;
	private ArrayList<LevelBase> levels = new ArrayList<LevelBase>(3);
	private LinkedList<Alien> aliens = new LinkedList<Alien>();
	private List<Laser> lasers = new LinkedList<Laser>();
	private long levelEndTime = 0;
	private Image bg;
	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;
	public int points = 0;

	public GameBoard() {
		addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				if (ingame && (e.getKeyCode() != KeyEvent.VK_ESCAPE)) {
					craft.keyReleased(e);
				}
			}

			public void keyPressed(KeyEvent e) {
				if (ingame) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						escapePressed();
					} else {
						craft.keyPressed(e);
					}
				}
			}
		});
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/de/rType/resources/bg.jpg"));
		bg = icon.getImage();
		setFocusable(true);
		setBackground(Color.GREEN);

		// setBack
		setDoubleBuffered(true);

		ingame = true;
		craft = new Craft();
		craft.setMissileList(lasers);

		// add levels
		LevelBase level = new LevelOne(this);
		levels.add(level);
		levels.add(new LevelTwo(this));
		levels.add(new LevelThree(this));
		currentLevel = level;
		Recalculator.getInstance().register(this);
	}

	@Override
	public List<Recalculable> getRecalculableItems() {
		List<Recalculable> items = new LinkedList<Recalculable>();
		items.add(craft);
		items.addAll(lasers);
		// Aliens recalculated by the repository.
		return items;
	}

	public boolean isInGame() {
		return ingame;
	}

	public void start() {
		if (timer == null) {
			timer = new Timer(5, this);
		}
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

			Pair<Integer, Integer> resolution = Enviroment.getEnviroment().getResolution();

			Graphics2D g2d = (Graphics2D) g;
			// add the bg graphic!
			g2d.drawImage(bg, 0, 0, null);
			currentLevel.drawLevel(g2d);
			if (craft.isAlive()) {
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
			}

			for (Laser m : lasers) {
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++) {
				Alien a = (Alien) aliens.get(i);
				if (a.isAlive()) {
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
				}
			}

			// Draw Points
			g2d.setColor(Color.WHITE);
			g.setFont(GameFonts.SMALL);
			g2d.drawString("Points: " + points, 5, 15);

			// Draw Firepower Indicator
			int firePower = craft.getFirePower();
			if (firePower > 10) {
				firePower = 10;
			}

			int indicatorY = resolution.getValueTwo() - 60;
			int indicatorWidth = 150;
			g2d.setColor(Color.WHITE);
			g2d.drawRect(10, indicatorY, indicatorWidth, 20);
			g2d.setColor(Color.YELLOW);
			if (firePower > 6) {
				g2d.setColor(Color.WHITE);
			}
			g2d.fillRect(10, indicatorY, (indicatorWidth / 10 * firePower), 20);

		} else {
			String msg = "Game Over";
			Font small = GameFonts.SMALL;
			FontMetrics metr = this.getFontMetrics(small);
			g.setColor(Color.WHITE);
			g.setFont(small);

			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (ingame) {
			// Missile handling

			List<Laser> removeList = new LinkedList<Laser>();
			for (Laser m : lasers) {
				if (m.isAlive()) {
					m.move();
				} else {
					removeList.add(m);
				}
			}
			lasers.removeAll(removeList);

			// Alien handling
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

			// If all aliens done go to next level
			if (aliens.size() == 0 && currentLevel.isDone()) {
				startNextLevel();
			}
		} else if ((levelEndTime + 3000) > GregorianCalendar.getInstance().getTimeInMillis()) {
			onGameEnd(this.points, false);
		}
		repaint();
	}

	private void startNextLevel() {
		int currentIdx = this.levels.indexOf(currentLevel);
		if (currentIdx == this.levels.size() - 1) {
			onGameEnd(this.points, true);
		} else {
			LevelBase next = this.levels.get(currentIdx + 1);
			currentLevel.pause();
			currentLevel = next;
			currentLevel.start();
		}
	}

	public void checkCollisions() {

		Rectangle hitboxCraft = craft.getHitbox();

		// Player collides with Alien.
		for (int j = 0; j < aliens.size(); j++) {
			Alien a = aliens.get(j);
			Rectangle hitboxAlien = a.getHitbox();
			if (hitboxCraft.intersects(hitboxAlien) && a.isAlive()) {
				craft.criticalHit();
				a.criticalHit();
				Sound.EXPLOSION.play();
				aliens.remove(a);
				ingame = false;
				levelEndTime = GregorianCalendar.getInstance().getTimeInMillis();
			}
			// Laser vs. Alien.
			List<Laser> removeList = new LinkedList<Laser>();
			for (Laser m : lasers) {

				Rectangle hitBoxMissile = m.getHitbox();

				if (hitBoxMissile.intersects(hitboxAlien) && a.isAlive()) {

					m.hit(1);
					a.hit(m.getDamage());

					if (!m.isAlive() || m.isGoneOut()) {
						removeList.add(m);
					}
					if (!a.isAlive()) {
						points += a.getScore();
						aliens.remove(a);
						Sound.EXPLOSION.play();
					}
				}

				if (m.isAlive() && hitBoxMissile.intersects(hitboxCraft)) {
					craft.criticalHit();
					m.criticalHit();
					Sound.EXPLOSION.play();
					ingame = false;
					levelEndTime = GregorianCalendar.getInstance().getTimeInMillis();
				}
			}
			lasers.removeAll(removeList);
		}
	}

	public abstract void escapePressed();

	public abstract void onGameEnd(int score, boolean complete);

	/**
	 * Gibt die Liste der aktuell auf dem Board aktiven Alien zurueck.
	 * 
	 * @return
	 */
	public List<Alien> getCurrentAliens() {
		return aliens;
	}

	/**
	 * Gibt die Liste der aktuell auf dem Board aktiven Laser zurueck.
	 * 
	 * @return
	 */
	public List<Laser> getCurrentLasers() {
		return lasers;
	}
}
