package ZuulBad;

import java.util.HashMap;

class Food extends Items{
	int weight = 5;
	private String name;
	private HashMap<String, String> descriptions;
	
	public Food(String name) {
		this.name = name;
		descriptions = new HashMap<>();
		this.fillDescriptions();
	}
	
	private final void fillDescriptions() {
		descriptions.put("apple", "This is a yummy apple");
		descriptions.put("banana", "This is a yummy banana");
		descriptions.put("starfruit", "This is a yummy starfruit");
	}
	
	public String getDescription() {
		return descriptions.get(name);
	}
	
	public String getName() {
		return name;
	}
	
	public int getWeight() {
		return weight;
	}
}