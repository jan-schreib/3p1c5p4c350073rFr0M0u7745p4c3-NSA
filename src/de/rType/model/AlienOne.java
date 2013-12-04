package de.rType.model;

import de.rType.util.MathUtil;

public class AlienOne extends Alien {

	private boolean up = true;
	protected Pair<Integer, Integer> sinus;

	public AlienOne(int x, int y) {
		super(x, y);
		sinus = MathUtil.getMinMaxSinus(x, 0, 128, y);
	}
	
	public AlienOne() {
		super();
	}
	@Override
	public void move() {
		super.move();		
		if (up) {
			if (this.y > sinus.getValueOne()) {
				this.y--;
			}
			if (this.y == sinus.getValueOne()) {
				up = false;
			}
		} else {
			if (this.y < sinus.getValueTwo()) {
				this.y++;
			}
			if (this.y == sinus.getValueTwo()) {
				up = true;
			}
		}
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		sinus = MathUtil.getMinMaxSinus(x, 0, 128, y);
		if(sinus.getValueOne() < 0) {
			sinus.setValueOne(5);
		}
		else if(sinus.getValueTwo() > resolution.getValueTwo()+10) {
			sinus.setValueTwo(resolution.getValueTwo()-20);
		}
	}
}
