package ZuulBad;

import java.util.HashMap;

public class Valuable extends Items {
	
	int weight = 5;

	public Valuable(String name) {
		this.name = name;
		descriptions = new HashMap<>();
		fillDescriptions();
		description = descriptions.get(name);
	}

	private final void fillDescriptions() {
		descriptions.put("old book", "Oh look, here is recipe for a love potion. Do you believe in that sort of thing?");
		descriptions.put("nice smelling candle", "Lavender always makes me so sleepy...");
		descriptions.put("sock", "This must have been useful to someone at some point.");
		descriptions.put("phone", "Sadly you don't know the PIN. Also, what is this doing in an old castle?");
		descriptions.put("dead plant", "Try to get this to the owner ASAP, maybe they can still save it.");
		descriptions.put("baseball cap", "Quite fashionable if you are into that sort of thing.");
	}
	
	public boolean isValuable() {
		
		if (descriptions.containsKey(name)) {
			return true;
		} else {
		System.out.println("This item is not a valuable!");
		return false;
		}
	}

}
