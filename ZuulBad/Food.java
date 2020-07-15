package ZuulBad;

/**
 * This class is responsible for food items in the game
 * 
 * @author Katerina Matsyova
 *
 */

public class Food extends Item implements Transportable {

	/**
	 * Constructor for the class Food
	 * 
	 * @param name item name
	 * @param weight item weight
	 * @param description item description
	 */
	public Food(String name, int weight, String description) {
		super(name, weight, description);
	}

	/**
	 * Returns a description with the weight of the food item
	 * 
	 * @return weight
	 */
	@Override
	public String getDescriptionWithWeight() {
		return super.getDescription() + " " + " Weight: " + getWeight();
	}

}
