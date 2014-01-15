package de.rType.menu;


/**
 * 
 * @author Jo
 * 
 */
public abstract class MenuListener {

	protected Menu menu;

	public MenuListener() {
	}

	public void addToMenu(Menu menu) {
		this.menu = menu;
	}

	public void performMenuItem(MenuItem item) {
		String command = item.getEvent();
		if (command != null && !command.isEmpty()) {
			if (command.equals(MenuItemKeys.NEW_GAME)) {
				newGame();
			} else if (command.equals(MenuItemKeys.RESUME)) {
				resumeGame();
			} else if (command.equals(MenuItemKeys.OPTIONS)) {
				showOptions();
			} else if(command.equals(MenuItemKeys.HIGHSCORES)){
				showHighscores();
			}else if (command.equals(MenuItemKeys.EXIT)) {
				exitGame();
			}
		}
	}

	public abstract void showHighscores();

	public abstract void newGame();

	public abstract void resumeGame();

	public abstract void showOptions();

	public abstract void exitGame();

}
