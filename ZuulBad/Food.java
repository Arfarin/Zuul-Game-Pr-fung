package ZuulBad;

/**
 * This class is responsible for food items in the game.
 * 
 * @author Katerina Matsyova
 *
 */

enum Food  {
	BANANA(2, "Bananas are always good to gain some energy."),
	APPLE(3, "A healthy and good looking apple."),
	STARFRUIT(3, "This fruit looks really cool but how does it taste...?"),
	BREAD(5, "Old bread is better than nothing."),
	BEANS(2, "Oh these beans look delicious."),
	LEFTOVERS(6, "Mmmh there are some good looking leftovers."),
	TOMATOES(2, "Juicy tomatoes are sooo good."),
	MUFFIN(4, "This muffin has a magical appearance...It must be made with lots of love!");
	
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
