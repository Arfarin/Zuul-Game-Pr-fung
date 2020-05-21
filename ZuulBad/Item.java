/**
 * 
 */
package ZuulBad;

/**
 * @author Sarah Engelmayer
 * @author Katerina Matysova
 * @author Daniel Birk
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
