package de.rType.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.rType.model.Alien;

/**
 * 
 * @author jo
 * 
 */
public class AlienRepository {

	private static AlienRepository repository;

	public static AlienRepository getInstance() {
		if (repository == null) {
			repository = new AlienRepository();
		}
		return repository;
	}

	private HashMap<Class<? extends Alien>, List<? extends Alien>> store;

	private AlienRepository() {
		store = new HashMap<Class<? extends Alien>, List<? extends Alien>>();
	}

	public <T extends Alien> Alien get(Class<T> alienClass) {
		@SuppressWarnings("unchecked")
		List<T> aliens = (List<T>) store.get(alienClass);
		if (aliens == null) {
			aliens = new ArrayList<T>();
			store.put(alienClass, aliens);
		}
		for (T entry : aliens) {
			if (!entry.isAlive()) {
				entry.reset();
				return entry;
			}
		}
		try {
			T instance = alienClass.newInstance();
			aliens.add(instance);
			return instance;
		} catch (InstantiationException e) {
			System.out.println("ERROR CREATING ALIEN!");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("ERROR CREATING ALIEN!");
			e.printStackTrace();
		}
		return null;
	}

	public <T extends Alien> void put(Class<T> alienClass, T instance) {
		@SuppressWarnings("unchecked")
		List<T> aliens = (List<T>) store.get(alienClass.getClass());
		if (aliens == null) {
			aliens = new ArrayList<T>();
			store.put(alienClass, aliens);
		}
		aliens.add(instance);
	}
}
