package ZuulBad;

/**
 * This class is responsible for items of the game which the player cannot store or move. It is a subclass of the class 'Item'.
 * 
 * @author Sarah Engelmayer
 */

public class Accessory extends Item {

	/**
	 * initializes the name, weight and description of the accessory-object.
	 * @param name The name of the accessory
	 * @param weight The weight of the accessory
	 * @param description The description of the accessory
	 */
	public Accessory(String name, int weight, String description) {
		super(name, weight, description);
	}

}
