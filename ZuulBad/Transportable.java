package ZuulBad;

/**
 * This Interface represents the transportability of something. The objects of the classes which implement this interface are transportable. That means the player can e.g. pick the objects up or store them. 
 * @author Sarah Engelmayer
 * @version 1.0.0
 */
public interface Transportable {

	/**
	 * an abstract method to deliver a description of the transportable item containing an information about the item's weight. 
	 * @return the string description with the information how much the item weights. 
	 */
	public String getDescriptionWithWeight();
}

