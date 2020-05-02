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
		
		damage = Level.setValue(1, 1);
	//	System.out.println("Monsters will reduce " + damage + " points of life per attack.");
	}
}
