package de.rType.menu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import de.rType.main.Enviroment;
import de.rType.model.Pair;
import de.rType.resources.GameFonts;

/**
 * GameMenu
 * 
 * @author Jo
 * 
 */
public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<MenuItem> items;
	private List<MenuItem> visibleList;
	private MenuItem resumeItem;

	public MenuItem getResumeItem() {
		return resumeItem;
	}

	public Menu(final MenuListener listener) {
		initMenuItems();
		listener.addToMenu(this);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				for (int i = 0; i < visibleList.size(); i++) {
					MenuItem item = visibleList.get(i);
					if (item.isSelected()) {
						if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
							int idx = visibleList.indexOf(item);
							item.setSelected(false);
							if (key == KeyEvent.VK_UP) {
								if (idx > 0 && idx < visibleList.size()) {
									idx = idx - 1;
									visibleList.get(idx).setSelected(true);
								} else if (idx == 0) {
									visibleList.get(visibleList.size() - 1).setSelected(true);
								}
								repaint();
								return;
							}
							if (key == KeyEvent.VK_DOWN) {
								if (idx >= 0 && idx < visibleList.size() - 1) {
									idx++;
									visibleList.get(idx).setSelected(true);
								} else if (idx == visibleList.size() - 1) {
									visibleList.get(0).setSelected(true);
								}
								repaint();
								return;
							}
						} else if (key == KeyEvent.VK_ENTER) {
							listener.performMenuItem(item);
						} else if (key == KeyEvent.VK_ESCAPE) {
							listener.resumeGame();
						}
					}
				}
			}
		});
		setFocusable(true);
		setDoubleBuffered(true);
	}

	public void initMenuItems() {
		this.resumeItem = new MenuItem("Weiter", MenuItemKeys.RESUME, false);
		items = Arrays.asList(resumeItem, new MenuItem("New Game", MenuItemKeys.NEW_GAME), new MenuItem("Highscore",
				MenuItemKeys.HIGHSCORES), new MenuItem("Options", MenuItemKeys.OPTIONS), new MenuItem("Exit",
				MenuItemKeys.EXIT));
		items.get(1).setSelected(true);
	}

	public void paint(Graphics g) {
		super.paint(g);

		setBackground(Color.BLACK);

		FontMetrics metr = this.getFontMetrics(GameFonts.MEDIUM);
		g.setFont(GameFonts.MEDIUM);

		this.visibleList = new ArrayList<MenuItem>();
		for (MenuItem item : items) {
			if (item.isVisible()) {
				visibleList.add(item);
			}
		}
		int idx = 1;
		for (MenuItem item : visibleList) {
			drawMenuItem(item, g, metr, idx, visibleList.size());
			idx++;
		}
	}

	private void drawMenuItem(MenuItem item, Graphics g, FontMetrics metrix, int index, int count) {
		if (item.isSelected()) {
			g.setColor(GameFonts.SELECTED_COLOR);
		} else {
			g.setColor(GameFonts.DEFAULT_COLOR);
		}
		Pair<Integer, Integer> res = Enviroment.getEnviroment().getResolution();
		int y = (res.getValueTwo() / (count + 2)) * (index);
		g.drawString(item.getText(), (res.getValueOne() - metrix.stringWidth(item.getText())) / 2, y);
	}
}
