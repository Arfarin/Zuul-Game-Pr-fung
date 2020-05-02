package ZuulBad;

public class Monster {
	
	/**
	 * indicates how much damage an attacking monster causes and how much the player's life bar will be reduced.
	 */
	
	private int damage;
	
	public Monster() {
		setDamage();
	}
	
	/**
	 * sets the damage caused by monsters depending on the level of difficulty chosen by the user at the beginning of the game.
	 * Level EASY sets damage to 1 point of life per attack
	 * Level MEAN sets damage to 2 point of life per attack
	 * Level HEAVY sets damage to 3 point of life per attack
	 */
	
	public void setDamage() {
		Level difficultyLevel = Game.getLevel();
		Level[] levels = Level.values();
		int index = 1;
		for (Level lev : levels) {
			if (difficultyLevel.equals(lev)) {
				break;
			} else {
				index++;
			}
		}
		damage = index;
//		System.out.println("Monsters will reduce " + index + " points of life per attack.");
	}
}
