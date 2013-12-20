package de.rType.main;

import global.CollectionGame;
import global.GlobalSettings;
import global.MainMenuInterface;
import global.Score;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.rType.menu.Menu;
import de.rType.menu.MenuListener;
import de.rType.repository.HighScores;
import de.rType.view.GameBoard;
import de.rType.view.HighScorePanel;

/**
 * 
 * @author Jo
 * 
 */
public class RType extends JFrame implements CollectionGame, WindowListener {

	private static final long serialVersionUID = 1L;
	private static final String GAME_PICS_PATH = "/de/rType/resources/gamepics/";
	private MainMenuInterface collectionMenu;
	private GameBoard gameBoard;
	private HighScorePanel highScorePanel;
	private Menu menu;

	public void initialize() {
		Enviroment env = Enviroment.getEnviroment();
		env.setFrame(this);
		env.updateFrame();
		final RType me = this;
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

					@Override
					public void onGameEnd(int score, boolean complete) {
						gameBoard.pause();
						gameBoard.setVisible(false);
						highScorePanel = new HighScorePanel() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onComplete() {
								showMenu(false);
							}
						};
						if (HighScores.getInstance().isHighScore(
								gameBoard.points)) {
							highScorePanel.readPlayerName(gameBoard.points);
							getContentPane().add(highScorePanel);
							highScorePanel.requestFocusInWindow();
						} else {
							highScorePanel.showNoHighscore();
							getContentPane().add(highScorePanel);
							highScorePanel.requestFocusInWindow();
						}
					}
				};
				getContentPane().add(gameBoard);
				gameBoard.requestFocusInWindow();
				gameBoard.start();
				me.repaint();
			}

			public void pauseGame() {
				showMenu(true);
			}

			@Override
			public void exitGame() {
				returnToMainMenu();
			}

			@Override
			public void showOptions() {
				menu.setOptions(true);
			}

			@Override
			public void showHighscores() {
				menu.setVisible(false);
				highScorePanel = new HighScorePanel() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onComplete() {
						showMenu(gameBoard != null && gameBoard.isInGame());
					}
				};
				getContentPane().add(highScorePanel);
				highScorePanel.showHighscores();
				highScorePanel.requestFocusInWindow();
			}

			@Override
			public void resumeGame() {
				if (gameBoard != null) {

					setSize(Enviroment.getEnviroment().getResolution()
							.getValueOne(), Enviroment.getEnviroment()
							.getResolution().getValueTwo());
					menu.getResumeItem().setVisible(false);
					menu.setVisible(false);
					gameBoard.requestFocusInWindow();
					gameBoard.start();
				}
			}
		};
		this.menu = new Menu(listener);
		getContentPane().add(menu);
		addWindowListener(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("R - Type");
		setResizable(false);
		showMenu(false);
	}

	private void showMenu(boolean pause) {
		if (gameBoard != null) {
			gameBoard.pause();
		}
		menu.setVisible(true);
		menu.getResumeItem().setVisible(pause);
		menu.requestFocusInWindow();

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
		List<Score> scores = HighScores.getInstance().getHighScores();
		Score[] s = new Score[scores.size()];
		for (int i = 0; i < scores.size(); i++) {
			s[i] = scores.get(i);
		}
		return s;
	}

	@Override
	public BufferedImage[] getGamePics() {
		try {

			BufferedImage[] images = new BufferedImage[3];
			images[0] = ImageIO.read(this.getClass().getResource(
					GAME_PICS_PATH + "eins.png"));
			images[1] = ImageIO.read(this.getClass().getResource(
					GAME_PICS_PATH + "zwei.png"));
			images[2] = ImageIO.read(this.getClass().getResource(
					GAME_PICS_PATH + "drei.png"));

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
		this.highScorePanel = null;
		/**
		 * DEF MODE EXIT GAME
		 */
		System.exit(0);
		this.collectionMenu.returnToMainMenu();
	}

	/**
	 * Window Listener Methods
	 */

	@Override
	public void windowClosing(WindowEvent e) {
		returnToMainMenu();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
