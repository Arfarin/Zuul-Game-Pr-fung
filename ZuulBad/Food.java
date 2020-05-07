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
	STARFRUIT(5, "This is a yummy starfruit");
	
	private int weight;
	private String description;

	Food (int weight, String description) {
		this.weight = weight;
		this.description = description;

	}

	public String getDescription() {
		return description;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean isFood(String name) {
		boolean isfood = false;
		
		for (Food food : Food.values()) {
			if (food.name().equals(name)) { // check if the string value matches any possible food name
				isfood = true;
			}
		}
		return isfood;
	}

}
