package de.rType.menu;

/**
 * 
 * @author Jo
 * 
 */
public abstract class OptionMenuItem extends MenuItem {

	public OptionMenuItem(String text, String event) {
		super(text, event);
	}

	public abstract String getValueText();

	public abstract void performChange();
}
