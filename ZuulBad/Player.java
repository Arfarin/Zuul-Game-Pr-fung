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
		if (backpack.removeItem(food)== true) {
		increaseFoodBar();
		System.out.println("You ate the " + food.toString() + " that was stored in your backpack.");
	}}

	public String eatMuffin(Food food) {
			increaseFoodBar();
			backpack.setMaxWeight(Integer.MAX_VALUE);
		return "You ate the magic muffin. Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";

	}
	public void putItemIntoBackpack(Object o) {
		if (backpack.checkIfFull() == false && o != null) {
			backpack.addItem(o);
		} else {
			System.out.println(Printer.weightTooHighError());
		}

	}

	private void removeItemFromBackpack(Object o) {
		backpack.removeItem(o);
		}
	

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
