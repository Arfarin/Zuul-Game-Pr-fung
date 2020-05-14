package ZuulBad;

public class Player {

	Inventory backpack;
	private int lifeBar;
	private int foodBar;
	private Printer printer;

	public Player() {
		backpack = new Inventory();
		lifeBar = 5;
		foodBar = 5;
		printer = new Printer();
	}

	public void lookAround(Room currentRoom) {

		System.out.println(currentRoom.getLongDescription());

	}

	public boolean backpackContainsItem(String specificitem) {
		return backpack.contains(specificitem);
	}

	public void eatFoodFromBackpack(Food food) {
		if (backpack.removeItem(food) == true) {
			increaseFoodBar();
			System.out.println("You ate the " + food.toString() + " that was stored in your backpack.");
		}
	}

	public String eatMuffin() {

		increaseFoodBar();
		backpack.setMaxWeight(Integer.MAX_VALUE);
		return printer.eatMagicMuffin();

	}

	public boolean putItemIntoBackpack(Object object) {
		boolean stored = false;
		int itemWeight = 0;
		try {
			itemWeight = ((Food) object).getWeight();
		} catch (ClassCastException e) {
			try {
				itemWeight = ((Weapon) object).getWeight();
			} catch (ClassCastException f) {
				try {
					itemWeight = ((Valuable) object).getWeight();
				} catch (ClassCastException g) {
				}
			}
		}

		if (backpack.checkIfFull(itemWeight) == false && object != null && itemWeight != 0) {
			backpack.addItem(object);
			System.out.println("Item successfully stored.");
			stored = true;

		} else if (backpack.checkIfFull(itemWeight) == true) {
			System.out.println(printer.weightTooHighError());
			stored = false;
		}
		return stored;

	}

	public void removeItemFromBackpack(Object o) {
		backpack.removeItem(o);
	}

	public String getBackpackContent() {
		return backpack.getListOfContent();
	}

	public boolean cantCarryMore(int itemWeight) {
		return backpack.checkIfFull(itemWeight);
	}

	/**
	 * increases foodBar. Used when player eats food.
	 */
	public void increaseFoodBar() {
		foodBar++;
	}

	/**
	 * reduces foodBar of player. Simulates that over time player gets hungry (used
	 * after every entry in a room).
	 */
	public void getHungry() {
		foodBar--;
	}

	/**
	 * increases lifeBar of player. Simulates regeneration of players health over
	 * time (used after every entry in a room)
	 */
	public void increaseLifeBar() {
		lifeBar++;
	}

	/**
	 * reduces lifeBar (used when monster attacks and no weapon is available)
	 * 
	 * @param amount
	 */
	public void reduceLifeBar(int amount) {
		lifeBar = lifeBar - amount;
	}
}
