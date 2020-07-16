package ZuulBad;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class creates the character which is controlled by the user. It has
 * properties that need to be satisfied such as lifeBar and foodBar in order for
 * the game to continue. An instance of the class Inventory is used to store the
 * items the player can carry.
 * 
 * @author Sarah Engelmayer
 * @version 1.0
 */

public class Player {

	/**
	 * the maximum value the FoodBar of the player can reach
	 */
	private int maxFood = 10;
	/**
	 * the maximum value the LifeBar of the player can reach
	 */
	private int maxLife = 10;

	/**
	 * an instance of Inventory presenting the backpack of the player
	 */
	private Inventory backpack;

	/**
	 * The points of life a player has. Demonstrating the health of the player. The
	 * lower the value is the weaker is a player.
	 */
	private SimpleIntegerProperty lifeBar;

	/**
	 * The points of food a player has. Demonstrating the hunger a player has. The
	 * lower the value is the hungrier the player is.
	 */
	private SimpleIntegerProperty foodBar;

	/**
	 * Initializes the lifeBar, foodBar and backpack of the player.
	 */
	public Player() {
		lifeBar = new SimpleIntegerProperty(5);
		foodBar = new SimpleIntegerProperty(5);
		backpack = new Inventory();
	}

	/**
	 * Getter for the lifeBar Property variable. It is necessary for the
	 * implementation of the according Listener (declared in class 'Game').
	 * 
	 * @return The value of lifeBar
	 */
	public IntegerProperty lifeBarProperty() {
		return lifeBar;
	}

	/**
	 * Getter for the foodBar Property variable. It is necessary for the
	 * implementation of the according Listener (declared in class 'Game').
	 * 
	 * @return The value of foodBar
	 */
	public IntegerProperty foodBarProperty() {
		return foodBar;
	}

	/**
	 * Getter for the Property variable of the actual weight of the player's
	 * backpack. It is necessary for the implementation of the according Listener
	 * (declared in class 'Game').
	 * 
	 * @return The backpack's weight
	 */
	public IntegerProperty backpackWeightProperty() {
		return backpack.backpackWeightProperty();
	}

	/**
	 * Answers the question if the player's backpack contains a specific item or
	 * not.
	 * 
	 * @param specificitem The item about which we want to know if it is in backpack
	 *                     or not.
	 * @return 'true' if the item is in the backpack, 'false' if not
	 */
	public boolean backpackContainsItem(Item specificitem) {
		return backpack.contains(specificitem);
	}

	/**
	 * A food item of the player's backpack is eaten. The player's foodBar is
	 * increased when eating a food.
	 * 
	 * @param food The food that should be eaten.
	 */
	public void eatFoodFromBackpack(Food food) {
		backpack.removeItem(food);
		increaseFoodBar();
	}

	/**
	 * The player gets the strength to carry an unlimited weight in his backpack. Called when eating a magic muffin. Sets the maxWeight of inventory to an infinite value.
	 * @return a message that says that from now on there is no limit of the weight carried in the player's backpack.
	 */
	public String getPowerFromMuffin() {
		backpack.setMaxWeight(Integer.MAX_VALUE);
		return "Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";
	}

	/**
	 * An item is put into the player's backpack provided that it does not exceed the backpacks capacity (maxWeight). 
	 * @param item The item that should be put into the player's backpack.
	 * @return 'true' if the item was stored in the player's backpack, 'false' if not.
	 */
	public boolean putItemIntoBackpack(Item item) {
		boolean stored = false;
		int itemWeight = item.getWeight();

		if (backpack.isFull(itemWeight)) {
			stored = false;

		} else {
			backpack.addItemToBackpack(item);
			stored = true;
		}
		return stored;
	}

	/**
	 * The player removes an item of his/her backpack.
	 * @param item The item that should be removed 
	 * @return 'true' if the item was removed, 'false' if not.
	 */
	public boolean removeItemFromBackpack(Item item) {
		return backpack.removeItem(item);
	}

	/**
	 * Answers the question if the player's backpack contains a arbitrary backpack or not.
	 * @return 'true' if the player's backpack contains a arbitrary backpack, 'false' if not.
	 */
	public boolean hasWeapon() {
		return backpack.containsWeapon();
	}

	/**
	 * The player removes an weapon from his/her backpack. 
	 */
	public void removeAWeaponFromBackpack() {
		backpack.removeWeapon();
	}

	/**
	 * Provides a string that lists all the items in the player's backpack at the moment.
	 * 
	 * @return the string that lists all the items in the player's backpack at the moment.
	 */
	public String getBackpackContent() {
		return backpack.getListOfContent();
	}

	/**
	 * Answers the question if the player can carry another item more in his/her backpack. If this item
	 * would exceed the maximum portable weight of the backpack, the player can't pick it up and store it in his/her backpack.
	 * @param itemWeight the weight of the item that should be put into the backpack 
	 * @return 'false' if the item can be put into the backpack, 'true' if the player can't carry it.
	 */
	public boolean cantCarryMore(int itemWeight) {
		return backpack.isFull(itemWeight);
	}

	/**
	 * A getter for the remaining free weight of the player's backpack.
	 * @return the remaining free weight of the player's backpack.
	 */
	public int getBackpacksFreeWeight() {
		return backpack.getRemainingFreeWeight();
	}

	/**
	 * increases the player's foodBar. Used when player eats food. Every food increases the player's foodBar by 5 units.
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
	 * reduces foodBar of player by 1 unit. Simulates that over time player gets hungry. Used
	 * after every entry in a room.
	 */
	public void getHungry() {
		foodBar.setValue(foodBar.getValue() - 1);
	}

	/**
	 * Answers the question if the player starved to death and his/her foodBar is less or equal than zero.
	 * @return 'true' if the player starved to death. 'False' if his/her foodBar is still greater than zero.  
	 */
	public boolean starvedToDeath() {
		if (foodBar.getValue() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Answers the question if the player is beaten by a monster and his/her lifeBar is less or equal than zero.
	 * @return 'true' if the player is beaten. 'False' if his/her lifeBar is still greater than zero.  
	 */
	public boolean beaten() {
		if (lifeBar.getValue() <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * increases lifeBar of the player. Simulates regeneration of players health over
	 * time (used after every entry in a room).
	 */
	public void increaseLifeBar() {
		int currentlife = lifeBar.getValue();

		lifeBar.setValue(currentlife + 1);
		if (currentlife > maxLife) {
			lifeBar.setValue(maxLife);
		}
	}

	/**
	 * reduces lifeBar of the player. Used when a monster attacks and no weapon is available in the player's backpack to fight it.
	 * 
	 * @param amount, how much the player's lifeBar is reduced. Corresponds to the damage value of the monster.
	 */
	public void reduceLifeBar(int amount) {
		lifeBar.setValue(lifeBar.getValue() - amount);
	}

	/**
	 * A getter for the maximum value of the player's foodBar.
	 * @return the maximum value of the player's foodBar.
	 */
	public int getMaxFood() {
		return maxFood;
	}

	/**
	 * A getter for the maximum value of the player's lifeBar.
	 * @return the maximum value of the player's lifeBar.
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * A getter for the maximum portable weight of the player's backpack.
	 * @return the maximum portable weight of the player's backpack.
	 */
	public int getMaxWeight() {
		return backpack.getMaxWeight();
	}

}