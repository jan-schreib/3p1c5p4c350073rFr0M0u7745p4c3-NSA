package de.rType.main;

import global.CollectionGame;
import global.GlobalSettings;
import global.MainMenuInterface;
import global.Score;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.rType.menu.Menu;
import de.rType.menu.MenuListener;

/**
 * 
 * @author Jo
 * 
 */
public class RType extends JFrame implements CollectionGame {

	private static final long serialVersionUID = 1L;
	private static final String GAME_PICS_PATH = "/de/rType/resources/gamepics/";
	private MainMenuInterface collectionMenu;
	private GameBoard gameBoard;
	private Menu menu;

	public void initialize() {
		Enviroment env = Enviroment.getEnviroment();
		setSize(env.getResolution().getValueOne(), env.getResolution()
				.getValueTwo());

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
				returnToMainMenu();
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
		new RType().runGame(new GlobalSettings(), null);
	}

	@Override
	public void runGame(GlobalSettings globalSettings,
			MainMenuInterface mainMenuRef) {
		Enviroment.getEnviroment().setGlobalSettings(globalSettings);
		collectionMenu = mainMenuRef;
		initialize();
		setVisible(true);
	}

	@Override
	public Score[] getHighscore() {
		// Temporary random
		// TODO
		Score[] highscores = new Score[3];
		highscores[0] = new Score("Peter Pan", 10000000);
		highscores[0] = new Score("Pipi Langstrumpf", 10000001);
		highscores[0] = new Score("Raeuber Hotzenplotz", 10000002);
		return highscores;
	}

	@Override
	public BufferedImage[] getGamePics() {
		try {

			BufferedImage[] images = new BufferedImage[3];
			images[0] = ImageIO.read(this.getClass().getResource(GAME_PICS_PATH + "eins.png"));
			images[1] = ImageIO.read(this.getClass().getResource(GAME_PICS_PATH + "zwei.png"));
			images[2] = ImageIO.read(this.getClass().getResource(GAME_PICS_PATH + "drei.png"));
			
			return images;
		} catch (IOException e) {
			e.printStackTrace();
			// Error reading files TODO Exceptionhandling?
		}
		return null;
	}

	private void returnToMainMenu() {
		// Clear Frame and Instances and call the collectionMenu to show
		this.setVisible(false);
		this.removeAll();
		this.gameBoard = null;
		this.menu = null;
		this.collectionMenu.returnToMainMenu();
	}
}
