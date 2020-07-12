package ZuulBad;

/**
 * This class is responsible for food items in the game.
 * 
 * @author Katerina Matsyova
 *
 */

public class Food extends Item implements Transportable {

	public Food(String name, int weight, String description) {
		super(name, weight, description);
	}
	
	@Override
	public String getDescriptionWithWeight(){
		return super.getDescription() + " " + " Weight: " + getWeight();
	}
	
}
