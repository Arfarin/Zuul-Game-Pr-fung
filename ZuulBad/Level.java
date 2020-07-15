package ZuulBad;

/**
 * Enumeration for the levels of Difficulty. At the beginning of the game one of
 * them has to be chosen. The chosen level has an impact how often the player can switch room (demonstrates the time until which the game must be finished), 
 * the maximum portable weight of the player's backpack
 * and the rate of damage caused by a monster.
 * There are three levels of difficulty: easy, medium and heavy.
 * 
 * @author Sarah Engelmayer
 *
 */

public enum Level {
	EASY, MEDIUM, HEAVY;

	/**
	 * sets the values of time, damage and maximum portable weight of inventory. Is used at the beginning of the game when the user has chosen a level of difficulty. 
	 * @param begin, the value that will be set when Level EASY is chosen
	 * @param difference, the positive or negative difference of the value from one level to the next (that means the difference from the value in 'easy' to the value in 'medium' and from the difference from the value in 'medium' to the value in 'heavy')
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