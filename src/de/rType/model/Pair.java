package de.rType.model;

/**
 * Use {@link Pair} to store two values.
 * 
 * @author jo
 * 
 */
public class Pair<T, E> {

	private T valueOne;
	private E valueTwo;

	public Pair(T valueOne, E valueTwo) {
		this.valueOne = valueOne;
		this.valueTwo = valueTwo;
	}

	public T getValueOne() {
		return valueOne;
	}

	public void setValueOne(T valueOne) {
		this.valueOne = valueOne;
	}

	public E getValueTwo() {
		return valueTwo;
	}

	public void setValueTwo(E valueTwo) {
		this.valueTwo = valueTwo;
	}

}
