package ZuulBad;

/**
 * This class is responsible for valuable items in the game
 * 
 * @author Katerina Matsyova
 * @version 1.0
 *
 */
public class Valuable extends Item implements Transportable {

	/**
	 * Constructor of the class Valuable.
	 * 
	 * @param name item name
	 * @param weight item weight
	 * @param description item description
	 */

	Valuable(String name, int weight, String description) {
		super(name, weight, description);
	}

	/**
	 * Returns a description with the weight of the valuable.
	 * 
	 * @return description with the weight of the valuable
	 */
	@Override
	public String getDescriptionWithWeight() {
		return super.getDescription() + " " + " Weight: " + getWeight();
	}

}
