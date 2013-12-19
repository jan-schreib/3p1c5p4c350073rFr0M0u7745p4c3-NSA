package de.rType.repository;

import global.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Store and manage Scores.
 * 
 * @author Jo
 * 
 */
public class HighScores {

	private static HighScores instance;

	public static HighScores getInstance() {
		if (instance == null) {
			instance = new HighScores();
		}
		return instance;
	}

	private static final String SEPARATOR = ";";

	private List<Score> scores = new ArrayList<Score>(10);

	private HighScores() {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		for (int i = 0; i < 10; i++) {
			String score = prefs.get(Integer.toString(i), null);
			if (score != null) {
				String[] data = score.split(SEPARATOR);
				if (data.length == 2) {
					int value = Integer.parseInt(data[1]);
					scores.add(new Score(data[0], value));
				}
			} else {
				break;
			}
		}
	}

	public List<Score> getHighScores() {
		return scores;
	}

	private void saveData() {
		try {
			Collections.sort(scores, new Comparator<Score>() {
				@Override
				public int compare(Score o1, Score o2) {
					return o2.getScore() - o1.getScore();
				}
			});
			// Jeder benutzer hat eigene highscores.
			Preferences prefs = Preferences.userNodeForPackage(this.getClass());
			for (int i = 0; i < scores.size(); i++) {
				Score s = scores.get(i);
				prefs.put(Integer.toString(i), s.getPlayerName() + SEPARATOR + s.getScore());
			}
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if highscores reached and score saved.
	 * 
	 * @param score
	 * @return
	 */
	public boolean performGameResult(Score score) {
		if (scores.size() > 0 && scores.size() == 10) {
			for (Score actual : scores) {
				if (score.getScore() >= actual.getScore()) {
					addScore(score, scores.indexOf(actual));
					return true;
				}
			}
		} else {
			addScore(score, scores.size());
			return true;
		}
		return false;
	}

	private void addScore(Score newScore, int idx) {
		List<Score> newHighScores = new ArrayList<Score>(10);
		if (scores.size() > 0) {
			if (scores.size() < 10) {
				newHighScores.addAll(scores);
				newHighScores.add(newScore);
			} else {
				for (int i = 0; i < scores.size(); i++) {
					if (i < idx) {
						newHighScores.add(scores.get(i));
					} else if (i == idx) {
						newHighScores.add(newScore);
						if (i < scores.size() - 1) {
							newHighScores.add(scores.get(i));
						}
					} else if (i < scores.size() - 1) {
						newHighScores.add(scores.get(i));
					}
				}
			}
		} else {
			newHighScores.add(newScore);
		}
		this.scores = newHighScores;
		saveData();
	}

	public boolean isHighScore(int point) {
		for (Score s : scores) {
			if (s.getScore() < point) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		HighScores s = new HighScores();
		StringBuilder str = new StringBuilder();
		for (Score sc : s.getHighScores()) {
			str.append(sc.getPlayerName() + " - " + sc.getScore() + "\n");
		}
		return str.toString();
	}

}
