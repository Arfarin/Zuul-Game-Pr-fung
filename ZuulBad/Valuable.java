package ZuulBad;

enum Valuable {

	GLASSES(3, "Oh look there's a pair of glasses. The lenses are so thick... the person they belong to has to be blind without them."),
	TIARA(5, "This is a very beautiful golden tiara."),
	SPOON(2, "Just a dirty spoon. Probably a hungry person would still use it."),
	TUPPERWARE(4, "This looks like your moms tupperware. I didn't know that tupperware already existed in this era."),
	
	KEY(4, "Don't loose this one. You'll need it to unlock doors."),
	DRAGONGLASS(7, "WOW! This looks like it could even kill the mightiest goblin!");
	
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
		name = name.toUpperCase().trim();

		for (Valuable valuable : Valuable.values()) {
			if (valuable.name().equals(name)) { // check if the string value matches any possible food name
				isvaluable = true;
			}
		}
		return isvaluable;
	}

}
