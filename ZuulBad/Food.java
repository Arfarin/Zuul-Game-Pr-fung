package ZuulBad;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for food items in the game.
 * 
 * @author Katerina Matsyova
 *
 */

class Food extends Items {
	int weight = 5;

	public Food(String name) {
		this.name = name;
		descriptions = new HashMap<>();
		fillDescriptions();
		description = descriptions.get(name);
	}

	private final void fillDescriptions() {
		descriptions.put("apple", "This is a yummy apple");
		descriptions.put("banana", "This is a yummy banana");
		descriptions.put("starfruit", "This is a yummy starfruit");
	}
	
	public boolean isFood() {
		
		if (descriptions.containsKey(name)) {
			return true;
		} else {
		return false;
		}
	}
}
