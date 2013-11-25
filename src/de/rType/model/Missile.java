package de.rType.model;

public class Missile extends GameObject {
	
	protected int damage = 2;
	
	public Missile(int x, int y) {
		super(x,y,"../resources/missile.png",2,1);
	}
	
	public int getDamage(){
		return damage;
	}

}
