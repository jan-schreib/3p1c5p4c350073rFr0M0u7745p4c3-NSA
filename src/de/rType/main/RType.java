package de.rType.main;

import javax.swing.JFrame;

import de.collectionGame.CollectionGame;
import de.collectionGame.GlobalSettings;
import de.collectionGame.MainMenuInterface;
import de.collectionGame.Score;
import de.rType.menu.Menu;
import de.rType.menu.MenuListener;

/**
 * 
 * @author Jo
 * 
 */
public class RType extends JFrame implements CollectionGame {

	private static final long serialVersionUID = 1L;

	private GameBoard gameBoard;
	private Menu menu;

	public void initialize() {
		Enviroment env = Enviroment.getEnviroment();
		setSize(env.getResolution().getValueOne(), env.getResolution().getValueTwo());

		MenuListener listener = new MenuListener() {

			public void newGame() {
				if (gameBoard != null) {
					gameBoard.setVisible(false);
				}
				menu.setVisible(false);
				gameBoard = new GameBoard() {

					private static final long serialVersionUID = 1L;

					@Override
					public void escapePressed() {
						pauseGame();
					}
				};
				add(gameBoard);
				gameBoard.grabFocus();
				gameBoard.start();
			}

			public void pauseGame() {
				if (gameBoard != null) {
					gameBoard.pause();
					menu.setVisible(true);
					menu.getResumeItem().setVisible(true);
					menu.grabFocus();
				}
			}

			@Override
			public void exitGame() {
				// TODO Return to MainMenu
				System.exit(0);
			}

			@Override
			public void showOptions() {
				// TODO Show Options
			}

			@Override
			public void resumeGame() {
				if (gameBoard != null) {
					menu.getResumeItem().setVisible(false);
					menu.setVisible(false);
					gameBoard.grabFocus();
					gameBoard.start();
				}
			}
		};
		this.menu = new Menu(listener);
		add(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("R - Type");
		setResizable(false);
	}

	public static void main(String[] args) {
		new RType().runGame(new GlobalSettings(false, false, new int[] { 800, 600 }), null);
	}

	@Override
	public void runGame(GlobalSettings globalSettings, MainMenuInterface mainMenuRef) {
		Enviroment.getEnviroment().setGlobalSettings(globalSettings);
		initialize();
		setVisible(true);
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