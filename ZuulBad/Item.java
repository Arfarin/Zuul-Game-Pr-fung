package ZuulBad;

import java.util.HashMap;

public class Item {
	private int weight;
	private String type;
	private String name;
	private String description;
	
	private HashMap<String, String> descriptions;
	
	public Item(String type, String name) {
		type = this.type;
		name = this.name;
		
		if (type.equals("food")) {
			weight = 5;
		} else if (type.equals("weapon")) {
			weight = 10;
		} else {
			weight = 15;
		}
		
	}
	
	public void fillDescriptions() {
		descriptions.put("toothpick", "This item is deadly.");
		descriptions.put("apple", "This is a yummy, but boring fruit.");
		descriptions.put("old book", "The only use for this is when you go to the bathroom.");
	}
	
	
	public String getItemDescription() {
		return description;
	}
	
	public int getItemWeight() {
		return weight;
	}
}
