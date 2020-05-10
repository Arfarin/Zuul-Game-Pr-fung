package ZuulBad;

enum Weapon {

	TOOTHPICK(2, "Might not look like much, but used the right way it is a magic tool."),
	NAIL(4, "Old and rusty, but could be useful in the future."),
	SWORD(10, "This must have belonged to a strong man and mighty soldier.");

	private int weight;
	private String description;

	Weapon(int weight, String description) {
		this.weight = weight;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isWeapon(String name) {
		boolean isweapon = false;
		name = name.toUpperCase().trim();

		for (Weapon weapon : Weapon.values()) {
			if (weapon.name().equals(name)) { // check if the string value matches any possible food name
				isweapon = true;
			}
		}
		return isweapon;
	}

}