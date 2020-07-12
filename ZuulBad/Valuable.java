package ZuulBad;

/**
 * This class is responsible for valuable items in the game (p.e. to return to NPCs).
 * 
 * @author Katerina Matsyova
 *
 */
public class Valuable extends Item implements Transportable  {


	Valuable(String name, int weight, String description) {
		super(name, weight, description);
	}
	
	@Override
	public String getDescriptionWithWeight(){
		return super.getDescription() + " " + " Weight: " + getWeight();
	}

}
