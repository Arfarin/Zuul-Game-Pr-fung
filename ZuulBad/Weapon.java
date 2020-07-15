package ZuulBad;

/**
 * This class is responsible for weapon items in the game.
 * 
 * @author Katerina Matsyova
 * @version 1.0
 *
 */

public class Weapon extends Item implements Transportable {

	/**
	 * Constructor of the class Weapon
	 * 
	 * @param name item name
	 * @param weight item weight
	 * @param description item description
	 */

	Weapon(String name, int weight, String description) {
		super(name, weight, description);
	}

	/**
	 * Returns a description with the weight of the weapon item.
	 * 
	 * @return description with the weight of the weapon item
	 */
	@Override
	public String getDescriptionWithWeight() {
		return super.getDescription() + " " + " Weight: " + getWeight();
	}

}
