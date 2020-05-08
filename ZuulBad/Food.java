package ZuulBad;

/**
 * This class is responsible for food items in the game.
 * 
 * @author Katerina Matsyova
 *
 */

enum Food  {
	BANANA(5, "This is a yummy banana"),
	APPLE(5, "This is a yummy apple"),
	STARFRUIT(5, "This is a yummy starfruit"),
	MUFFIN(5, "This is a muffin made according to a special secret recipe of the castle");
	
	private int weight;
	private String description;

	Food (int weight, String description) {
		this.weight = weight;
		this.description = description;

	}
	
	
	public boolean isMuffin () {
		if (this.equals(Food.MUFFIN) ) {
			return true;
		}
		return false;
	}

	public String getDescription() {
		return description;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean isFood(String name) {
		boolean isfood = false;
		name = name.toUpperCase().trim();
		
		for (Food food : Food.values()) {
			if (food.name().equals(name)) { // check if the string value matches any possible food name
				isfood = true;
			}
		}
		return isfood;
	}

}
