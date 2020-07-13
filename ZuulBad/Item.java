package ZuulBad;

/**
 * This class is the superclass for all items of the game. Specific item classes
 * extend this class. It provides all information about each item.
 * 
 * @author Sarah Engelmayer
 */
public abstract class Item {

	/**
	 * A short description of the item
	 */
	private String description;
	/**
	 * The weight of the item
	 */
	private int weight;
	/**
	 * The name of the item
	 */
	private String name;

	/**
	 * Constructor of the class Item
	 * 
	 * @param name item name
	 * @param weight item weight
	 * @param description item description
	 */
	public Item(String name, int weight, String description) {
		this.description = description;
		this.weight = weight;
		this.name = name;
	}

	/**
	 * Returns the item description
	 * 
	 * @return description item description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the item weight
	 * 
	 * @return weight item weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Returns the item name
	 * 
	 * @return name item name
	 */
	public String getName() {
		return name;
	}
}
