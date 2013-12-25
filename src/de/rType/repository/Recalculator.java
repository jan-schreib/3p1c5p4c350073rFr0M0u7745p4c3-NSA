package de.rType.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rType.model.Pair;
import de.rType.model.Recalculable;
import de.rType.model.RecalculableContainer;

/**
 * 
 * @author Jo
 * 
 */
public class Recalculator {

	private Map<String, RecalculableContainer> data;

	private static Recalculator instance;

	public static Recalculator getInstance() {
		if (instance == null) {
			instance = new Recalculator();
		}
		return instance;
	}

	private Recalculator() {
		data = new HashMap<String, RecalculableContainer>();
	}

	public void register(RecalculableContainer item) {
		data.put(item.getClass().getName(), item);
	}

	public void recalculate(Pair<Integer, Integer> oldResolution, Pair<Integer, Integer> newResolution) {
		double factorX = (double) newResolution.getValueOne() / (double) oldResolution.getValueOne();
		double factorY = (double) newResolution.getValueTwo() / (double) oldResolution.getValueTwo();

		for(RecalculableContainer con : data.values()){
			recalculate(con.getRecalculableItems(), newResolution, factorX, factorY);
		}
	}

	private static void recalculate(List<Recalculable> items, Pair<Integer, Integer> newResolution, double factorX, double factorY) {
		for (Recalculable r : items) {
			r.recalculate(newResolution, factorX, factorY);
		}
	}
}
