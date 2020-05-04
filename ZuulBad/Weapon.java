package ZuulBad;

import java.util.HashMap;

class Weapon extends Items {

	int weight = 10;

	public Weapon(String name) {
		this.name = name;
		descriptions = new HashMap<>();
		this.fillDescriptions();
		description = descriptions.get(name);
	}

	private final void fillDescriptions() {
		descriptions.put("toothpick", "Might not look like much, but used the right way it is a magic tool.");
		descriptions.put("banana peel", "Finally, someone else is at the receiving end of the \"slippery slope\"");
		descriptions.put("piece of glass", "This must have been part of a beautiful vase in the past, but try not to step on it.");
	}
}