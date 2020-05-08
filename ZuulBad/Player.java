package ZuulBad;

public class Player {

	Inventory backpack;
	private int lifeBar;
	private int foodBar;

	public Player() {
		backpack = new Inventory();
		lifeBar = 5;
		foodBar = 5;
	}

	public void lookAround(Room currentRoom) {

		System.out.println(currentRoom.getLongDescription());

	}

	public boolean backpackContainsItem(String specificitem) {

		return backpack.contains(specificitem);
	}

	public void eatFoodFromBackpack(Food food) {
		if (removeItemFromBackpack(food)== true) {
		increaseFoodBar();
	}}

	public String eatMuffin() {
		backpack.setMaxWeight(1000000000);
		return "You ate the magic muffin. Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";
	}

	public void putItemIntoBackpack(Object o) {
		if (backpack.checkIfFull() == false) {
			backpack.addItem(o);
		} else {
			System.out.println(Printer.weightTooHighError());
		}

	}

	private boolean removeItemFromBackpack(Object o) {
		if (backpack.contains(o.toString())) {
		backpack.removeItem(o);
		return true;
		}
		else {
		System.out.println("Sorry, your backpack doesn't contain that.");
		return false;
	}}

	public void useWeapon() {

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
