/**
 * 
 */
package ZuulBad;

/**
 * This class is the superclass for all items of the game. Specific item classes extend this class.
 * It provides all information about each item.
 * 
 * @author Sarah Engelmayer
 */
public abstract class Item {
	
	private String description;
	private int weight;
	private String name;
	
	public Item(String name, int weight, String description) {
		this.description = description;
		this.weight = weight;
		this.name= name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getWeight() {
		return weight;
	}
	public String getName() {
		return name;
	}
}
