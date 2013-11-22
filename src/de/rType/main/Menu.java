package de.rType.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private int select = 1;
	private boolean clicked = false;

	public Menu() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);
	}

	public void paint(Graphics g){
		super.paint(g);

		setBackground(Color.BLACK);
		String game = "New Game";
		String option ="Option";
		String exit = "Exit";
		Font normal = new Font("Helvetica", Font.BOLD, 20);
		FontMetrics metr = this.getFontMetrics(normal);
		g.setFont(normal);

		switch(select) {
		case 1: 			
			g.drawString(option, (1200 - metr.stringWidth(option)) / 2,
					720 / 2 + 50);

			g.drawString(exit, (1200 - metr.stringWidth(exit)) / 2,
					720 / 2 + 100);
			g.setColor(Color.WHITE);
			g.drawString(game, (1200 - metr.stringWidth(game)) / 2,
					720 / 2);
			break;
		case 2:
			g.drawString(game, (1200 - metr.stringWidth(game)) / 2,
					720 / 2);

			g.drawString(exit, (1200 - metr.stringWidth(exit)) / 2,
					720 / 2 + 100);
			g.setColor(Color.WHITE);
			g.drawString(option, (1200 - metr.stringWidth(option)) / 2,
					720 / 2 + 50);

			break;	
		case 3:
			g.drawString(game, (1200 - metr.stringWidth(game)) / 2,
					720 / 2);
			g.drawString(option, (1200 - metr.stringWidth(option)) / 2,
					720 / 2 + 50);			
			g.setColor(Color.WHITE);
			g.drawString(exit, (1200 - metr.stringWidth(exit)) / 2,
					720 / 2 + 100);

			break;


		}
	}

	public int getSelect() {
		return select;
	}
	
	public boolean getClicked() {
		return clicked;
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();
			if (key == KeyEvent.VK_UP) {
				select--;
				if(select<1) {
					select = 3;
				}
				repaint();
			}
			if (key == KeyEvent.VK_DOWN) {
				select++;
				if(select>3) {
					select = 1;
				}
				repaint();
			}
			if (key == KeyEvent.VK_ENTER) {
				clicked = true;
			}
		}
	}

}
