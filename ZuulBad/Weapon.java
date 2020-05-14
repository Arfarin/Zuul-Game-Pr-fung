package ZuulBad;

enum Weapon {

	TOOTHPICK(2, "It might not look like much, but used the right way it is a magic tool."),
	HAIRBRUSH(3, "Don't underestimate the hairbrush of a lady!."),
	NAIL(4, "Old and rusty, but could be useful in the future."),
	KNIFE(5, "A knife is always good to protect yourself."),
	DAGGER(6, "Its short, double-edged blade could come in handy at some point..."),
	AXE(9, "You could chop a tree with that axe."),
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
