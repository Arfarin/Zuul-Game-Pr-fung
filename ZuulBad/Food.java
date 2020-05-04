package ZuulBad;

import java.util.HashMap;

class Food extends Items {
	int weight = 5;
	public static String[] foodlist;

	public Food(String name) {
		this.name = name;
		descriptions = new HashMap<>();
		this.fillDescriptions();
		description = descriptions.get(name);
	}

	private final void fillDescriptions() {
		descriptions.put("apple", "This is a yummy apple");
		descriptions.put("banana", "This is a yummy banana");
		descriptions.put("starfruit", "This is a yummy starfruit");
	}
	

}
