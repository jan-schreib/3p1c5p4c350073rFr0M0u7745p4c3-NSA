package de.rType.model;

public class Calculation {

	public double[] calcFly(int height) {
		
		double[] array = new double[1201];
		for(int i = 1200; i >= 0; i--) {
			array[i] = (Math.sin(i * 1/50)*100 + height);
			System.out.println("i: " + i + " Wert: " + array[i]);
		}
		return array;
	}
}
