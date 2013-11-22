package de.rType.main;

import javax.swing.JFrame;

import de.collectionGame.CollectionGame;
import de.collectionGame.GlobalSettings;
import de.collectionGame.MainMenuInterface;
import de.collectionGame.Score;

/**
 * 
 * @author Jo
 * 
 */
public class RType extends JFrame implements CollectionGame {

	private static final long serialVersionUID = 1L;
	Menu test;

	public RType() {
		test = new Menu();
		
		add(test);
		//add(new Board());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 768);
		setLocationRelativeTo(null);
		setTitle("R - Type");
		setResizable(false);
	}

	public static void main(String[] args) {
		new RType().runGame(null, null);
	}

	@Override
	public void runGame(GlobalSettings globalSettings,
			MainMenuInterface mainMenuRef) {
		setVisible(true);
		while(!test.getClicked()) {System.out.println("warte");};
		add(new Board());
		test.setVisible(false);
	}

	@Override
	public Score[] getHighscore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getGamePics() {
		// TODO Auto-generated method stub
		return null;
	}
}