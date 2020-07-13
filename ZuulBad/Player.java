package ZuulBad;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class creates the character which is controlled by the user. It has properties that need to be
 * satisfied such as lifeBar and foodBar in order for the game to continue. An instance of the class 
 * Inventory is used to store the items the player can carry.
 * 
 * @author Sarah Engelmayer
 */

public class Player {
	
	private int maxFood = 10;
	private int maxLife = 10;

	Inventory backpack;
	
	private SimpleIntegerProperty lifeBar;
	private SimpleIntegerProperty foodBar;

	public Player() {
		lifeBar = new SimpleIntegerProperty(5);
		foodBar = new SimpleIntegerProperty(5);
		
		backpack = new Inventory();
	}
	
	public IntegerProperty lifeBarProperty() {
		return lifeBar;
	}
	
	public IntegerProperty foodBarProperty() {
		return foodBar;
	}

	public IntegerProperty backpackWeightProperty() {
		return backpack.backpackWeightProperty();
	}

	public void lookAround(Room currentRoom) {
		System.out.println(currentRoom.getLongDescription());
	}

	public boolean backpackContainsItem(Item specificitem) {
		return backpack.contains(specificitem);
	}

	public void eatFoodFromBackpack(Food food) {
			backpack.removeItem(food);
			increaseFoodBar();
			System.out.println("It was stored in your backpack before.");
	}
	

	public String getPowerFromMuffin() {
		backpack.setMaxWeight(Integer.MAX_VALUE);
		return "Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";
	}

	public boolean putItemIntoBackpack(Item item) {
		boolean stored = false;
		int itemWeight = 0;


		if (backpack.isFull(itemWeight)) {
			stored = false;

		} else {
			backpack.addItemToBackpack(item);
			stored = true;
		}
		return stored;
	}

	public boolean removeItemFromBackpack(Item item) {
		return backpack.removeItem(item);
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
		int currentfood = foodBar.getValue();

		if ((currentfood + 5) < maxFood) {
			foodBar.setValue(currentfood + 5);
		} else {
			foodBar.setValue(maxFood);
		}
	}

	/**
	 * reduces foodBar of player. Simulates that over time player gets hungry (used
	 * after every entry in a room).
	 */
	public void getHungry() {
		foodBar.setValue(foodBar.getValue() - 1);;
	}
	
	public boolean starvedToDeath() {
		if (foodBar.getValue() <= 0) {
			return true;
		}
		return false;
	}
	
	public boolean beaten() {
		if (lifeBar.getValue() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * increases lifeBar of player. Simulates regeneration of players health over
	 * time (used after every entry in a room)
	 */
	public void increaseLifeBar() {
		int currentlife = lifeBar.getValue();
		
		lifeBar.setValue(currentlife + 1);
		if (currentlife > maxLife) {
			lifeBar.setValue(maxLife);
		}
	}

	/**
	 * reduces lifeBar (used when monster attacks and no weapon is available)
	 * 
	 * @param amount
	 */
	public void reduceLifeBar(int amount) {
		lifeBar.setValue(lifeBar.getValue() - amount);
	}

	public int getMaxFood() {
		return maxFood;
	}

	public int getMaxLife() {
		return maxLife;
	}
	
	public int getMaxWeight() {
		return backpack.getMaxWeight();
	}
	
	public int getLifeBar() {
		return lifeBar.getValue();
	}
}