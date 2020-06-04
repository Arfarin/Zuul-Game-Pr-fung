package ZuulBad;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
	
	private int maxFood = 10;
	private int maxLife = 10;

	Inventory backpack;
	private int lifeBar;
	private int foodBar;
	private Printer printer;
	
	private SimpleIntegerProperty lifebarproperty;

	public Player() {
		lifebarproperty = new SimpleIntegerProperty(lifeBar);
		
		backpack = new Inventory();
		lifeBar = 5;
		foodBar = 5;
		printer = new Printer();
		
	}
	
	public IntegerProperty lifeBarProperty() {
		return lifebarproperty;
	}


	public void lookAround(Room currentRoom) {
		System.out.println(currentRoom.getLongDescription());
	}

	public boolean backpackContainsItem(Item specificitem) {
		return backpack.contains(specificitem);
	}
	
	public boolean backpackContainsFood(String specifiedFood) {
		return backpack.containsFood(specifiedFood);
	}

	public void eatFoodFromBackpack(Food food) {
			backpack.removeItem(food);
			eatFood(food);
			System.out.println("It was stored in your backpack before.");
	}
	
	public void eatFood(Food food) {
		increaseFoodBar();
		System.out.println("You ate the " + food.getName() + ".");
	}

	public String getPowerFromMuffin() {
		backpack.setMaxWeight(Integer.MAX_VALUE);
		return "Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";
	}

	public boolean putItemIntoBackpack(Item item) {
		boolean stored = false;
		int itemWeight = 0;


		if (backpack.isFull(itemWeight)) {
			System.out.println(printer.weightTooHighError());
			stored = false;

		} else {
			backpack.addItemToBackpack(item);
			System.out.println("Item successfully stored.");
			stored = true;
		}
		return stored;
	}

	public void removeItemFromBackpack(Item item) {
		backpack.removeItem(item);
	}


	public boolean hasWeapon() {
		return backpack.containsWeapon();
	}

	public void removeAWeaponFromBackpack() {
		backpack.removeWeapon();
	}

	public String getBackpackContent() {
		return backpack.getListOfContent();
	}
	

	public boolean cantCarryMore(int itemWeight) {
		return backpack.isFull(itemWeight);
	}
	
	public int getBackpacksWeight() {
		return backpack.getRemainingFreeWeight();
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
	
	public boolean starvedToDeath() {
		if (foodBar <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean beaten() {
		if (lifeBar <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * increases lifeBar of player. Simulates regeneration of players health over
	 * time (used after every entry in a room)
	 */
	public void increaseLifeBar() {
		if (lifeBar < maxLife) {
		lifeBar++;
		lifebarproperty.setValue(lifeBar);
		}
	}

	/**
	 * reduces lifeBar (used when monster attacks and no weapon is available)
	 * 
	 * @param amount
	 */
	public void reduceLifeBar(int amount) {
		lifeBar = lifeBar - amount;
		lifebarproperty.setValue(lifeBar);
	}

	public int getMaxFood() {
		return maxFood;
	}

	public int getMaxLife() {
		return maxLife;
	}
	
	public int getLifeBar() {
		return lifeBar;
	}
}
