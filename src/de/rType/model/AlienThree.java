package de.rType.model;


public class AlienThree extends Alien {

	private boolean stay = false;


	public AlienThree(int x, int y) {
		super(x,y);
	}

	public AlienThree() {
		super();
	}

	@Override
	public void move() {
		super.move();
		if(!stay) {
			if((getX()%3) == 0) {
				this.y++;
			}
			if(getY()>=resolution.getValueTwo()-50) {
				setY(resolution.getValueTwo()-50);
				stay = true;
			}
		}
	}
}
