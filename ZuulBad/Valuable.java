package ZuulBad;

enum Valuable {

	BOOK(5, "Oh look, here is recipe for a love potion. Do you believe in that sort of thing?"),
	CANDLE(3, "Lavender always makes me so sleepy..."),
	SOCK(2, "This must have been useful to someone at some point."),
	PHONE(6, "Sadly you don't know the PIN. Also, what is this doing in an old castle?"),
	PLANT(5, "Try to get this to the owner ASAP, maybe they can still save it."),
	HAT(3, "Quite fashionable if you are into that sort of thing."),
	KEY(4, "Don't loose this one.");
	
	private int weight;
	private String description;

	Valuable(int weight, String description) {
		this.weight = weight;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isValuable(String name) {
		boolean isvaluable = false;

		for (Valuable valuable : Valuable.values()) {
			if (valuable.name().equals(name)) { // check if the string value matches any possible food name
				isvaluable = true;
			}
		}
		return isvaluable;
	}

}
