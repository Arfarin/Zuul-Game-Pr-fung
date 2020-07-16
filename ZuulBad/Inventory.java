package ZuulBad;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class handles the inventory of the player. It creates an instance of the
 * class Items and provides methods the player can call to interact with items
 * stored in backpack (e.g. drop, eat).
 * 
 * @author Sarah Engelmayer
 * @version 1.0
 */

public class Inventory {
	/**
	 * specifies the maximum permissible weight of the backpack.
	 */
	private int maxWeight;

	/**
	 * specifies the current weight of the backpack.
	 */
	private SimpleIntegerProperty currentWeight;

	/**
	 * the current content of the backpack.
	 */
	private Items content;

	/**
	 * initializes the variables 'content' and 'currentWeight' and sets the maximum
	 * portable weight to a value depending on the level of difficulty.
	 * 
	 */
	public Inventory() {
		setMaxWeight(Level.setValue(18, -5));
		content = new Items();
		currentWeight = new SimpleIntegerProperty(0);
	}

	/**
	 * A Getter for the property variable for the current weight of the backpack. It
	 * is necessary for the implementation of the according Listener (declared in
	 * class 'Game').
	 * 
	 * @return currentWeight the weight of the backpack at the moment
	 */
	public IntegerProperty backpackWeightProperty() {
		return currentWeight;
	}

	/**
	 * One or more items are put into the backpack. The weight of the item(s) is
	 * added to the weight of the backpack.
	 * 
	 * @param items the items that should be put in the backpack.
	 */
	public void addItemToBackpack(Item... items) {
		for (Item item : items) {
			content.addItem(item);
			currentWeight.setValue(currentWeight.getValue() + item.getWeight());
		}
	}

	/**
	 * An item is removed from the backpack. If the backpack contains the specified
	 * item, it will be removed and the weight of the backpack decreased.
	 * 
	 * @param item The item that should be removed from the backpack
	 * @return 'true' if the item was removed from the backpack, 'false' if the item
	 *         was not removed
	 */
	public boolean removeItem(Item item) {
		boolean removed = true;

		if (content.contains(item)) {
			content.removeItem(item);
			currentWeight.setValue(currentWeight.getValue() - item.getWeight());
		} else {
			removed = false;
		}
		return removed;
	}

	/**
	 * An arbitrary weapon is removed from the backpack. If there is no weapon in the backpack, 
	 * nothing happens.
	 */
	public void removeWeapon() {
		this.removeItem(content.getWeapon());
	}

	/**
	 * Answers the question if a specified item is in the backpack or not.
	 * 
	 * @param specificitem The item about which we want to know if it is in the
	 *                     backpack or not.
	 * @return 'true' if the backpack contains the item, 'false' if not.
	 */
	public boolean contains(Item specificitem) {
		return content.contains(specificitem);
	}

	/**
	 * Answers the question if a specified food is in the backpack or not.
	 * 
	 * @param foodName the food about which we want to know if it is in the backpack
	 *                 or not.
	 * @return 'true' if the backpack contains the food, 'false' if not.
	 */
	public boolean containsFood(String foodName) {
		return content.containsFood(foodName);
	}

	/**
	 * Answers the question if the backpack contains an arbitrary weapon or not.
	 * 
	 * @return 'true' if the backpack contains a weapon, 'false' if not.
	 */
	public boolean containsWeapon() {
		return content.containsAnyWeapon();
	}

	/**
	 * Provides a string that lists all the items in backpack at the moment.
	 * 
	 * @return the String that lists all the items in backpack at the moment.
	 */
	public String getListOfContent() {
		return content.getItemList();
	}

	/**
	 * A Setter for the maximum portable weight of the backpack. It is used after
	 * the difficulty level is selected.
	 * 
	 * @param maxWeight the value of the weight to which the maximum weight should be set 
	 */
	public final void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * Answers the question if another item can be put into the backpack or if this
	 * would exceed the maximum portable weight of the backpack.
	 * 
	 * @param itemWeight the weight of the item that should be put into the
	 *                   backpack.
	 * @return 'false' if the item can be put into the backpack, 'true' if not.
	 */
	public boolean isFull(int itemWeight) {
		if (currentWeight.getValue() + itemWeight > maxWeight) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * provides the weight which the backpack could still take, so to say the 'free weight'.
	 * @return the value of the weight that is still free
	 */
	public int getRemainingFreeWeight() {
		return maxWeight - currentWeight.getValue();
	}

	/**
	 * A Getter for the maximum portable weight of the backpack
	 * @return the maximum portable weight of the backpack
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

}
