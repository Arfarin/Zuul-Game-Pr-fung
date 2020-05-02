package ZuulBad;

/**
 * Enumeration for the levels of Difficulty. At the beginning of the game one of
 * them has to be chosen. The chosen level has an impact on the life points of
 * the player at the beginning, the maximum portable weight of his/her backpack
 * and the rate of damage caused by monsters.
 * 
 * @author Daniel Birk
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 *
 */

public enum Level {
	EASY, MEAN, HEAVY;

	/**
	 * sets the values of time, damage and maximum portable weight of inventory at the beginning of the game. 
	 * @param begin the value that will be set when Level EASY is chosen
	 * @param difference the positive or negative difference of the value from one level to the next
	 * @return the value that will be set; depends on the chosen level of difficulty
	 */
	
	public static int setValue(int begin, int difference) {
		int value = begin;
		Level difficultyLevel = Game.getLevel();
		Level[] levels = Level.values();
		for (Level lev : levels) {
			if (difficultyLevel.equals(lev)) {
				break;
			} else {
				value = value + difference;
			}
		}
		return value;
	}
}