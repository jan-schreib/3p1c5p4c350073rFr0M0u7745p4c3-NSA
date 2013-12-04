package de.rType.model;


public class AlienThree extends Alien {
	

	public AlienThree(int x, int y) {
		super(x,y);
	}
	
	public AlienThree() {
		super();
	}
	
	@Override
	public void move() {
		super.move();
		if((getX()%3) == 0) {
			this.y++;
		}
		if(getY()<resolution.getValueTwo()) {
			setY(resolution.getValueTwo());
		}
	}
}
