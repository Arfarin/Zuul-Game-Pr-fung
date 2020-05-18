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

	public boolean backpackContainsItem(Object specificitem) {
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

		return "You ate the magic muffin. Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";

	}

	public boolean putItemIntoBackpack(Object object) {
		Items item = new Items();
		boolean stored = false;
		int itemWeight = 0;
		String itemname = object.toString();
		
		try {
		
		if (item.toItsType(itemname) instanceof Food) {
			itemWeight = Food.valueOf(itemname).getWeight();
		}
		else if (item.toItsType(itemname) instanceof Weapon) {
			itemWeight = Weapon.valueOf(itemname).getWeight();
		}
		else if (item.toItsType(itemname) instanceof Valuable) {
			itemWeight = Valuable.valueOf(itemname).getWeight();
		} }
		
		catch (IllegalArgumentException e) {
			System.out.println("You cannot put that in your backpack.");
			return false;
		}
		
		if (backpack.isFull(itemWeight)) {
			System.out.println(printer.weightTooHighError());
			stored = false;
			
		} else {
			backpack.addItem(object);
			System.out.println("Item successfully stored.");
			stored = true;
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
		return backpack.isFull(itemWeight);
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
