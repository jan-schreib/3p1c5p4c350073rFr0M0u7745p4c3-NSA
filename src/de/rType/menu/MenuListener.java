package de.rType.menu;


/**
 * 
 * @author Jo
 * 
 */
public abstract class MenuListener {

	protected static final String START_GAME = "start";
	protected static final String PAUSE_GAME = "pause";

	public void performMenuItem(MenuItem item) {
		String command = item.getEvent();
		if (command != null && !command.isEmpty()) {
			if (command == MenuItemKeys.NEW_GAME) {
				newGame();
			} else if (command == MenuItemKeys.START) {
				startGame();
			} else if (command.equals(MenuItemKeys.PAUSE)) {
				pauseGame();
			} else if (command.equals(MenuItemKeys.OPTIONS)) {
				showOptions();
			} else if (command.equals(MenuItemKeys.EXIT)) {
				exitGame();
			}
		}
	}

	public abstract void newGame();

	public abstract void startGame();

	public abstract void pauseGame();

	public abstract void showOptions();

	public abstract void exitGame();

}
