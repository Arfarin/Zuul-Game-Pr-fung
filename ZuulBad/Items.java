package ZuulBad;

import java.util.ArrayList;

/**
 * This class is used to store items of the game in an arraylist as well as
 * allowing interaction with that list
 * 
 * @author Katerina Matysova
 * @version 1.0.0
 */

public class Items {

	/**
	 * ArrayList that can be used to store items
	 */
	private ArrayList<Item> itemlist;

	/**
	 * Constructor of the class Items
	 */
	public Items() {
		itemlist = new ArrayList<Item>();
	}

	/**
	 * Adds specific item to the item list
	 * 
	 * @param item specific item
	 */
	public void addItem(Item item) {
		itemlist.add(item);
	}

	/**
	 * Creates specific item from item name and adds it to the item list
	 * 
	 * @param itemName item name
	 * @return confirmation boolean
	 */
	public boolean addItem(String itemName) {
		Food food = Environment.getFood(itemName);
		Weapon weapon = Environment.getWeapon(itemName);
		Valuable valuable = Environment.getValuable(itemName);
		boolean added = false;

		if (food != null && weapon == null && valuable == null) {
			itemlist.add(food);
			added = true;
		} else if (food == null && weapon != null && valuable == null) {
			itemlist.add(weapon);
			added = true;
		} else if (food == null && weapon == null && valuable != null) {
			itemlist.add(valuable);
			added = true;
		}

		return added;
	}

	/**
	 * Takes wanted item out of the item list and returns it
	 * 
	 * @param itemName item name
	 * @return item
	 */
	public Item getItem(String itemName) {
		for (Item item : itemlist) {
			if (item.getName().toLowerCase().trim().contains(itemName.toLowerCase().trim())) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Removes item from the item list
	 * 
	 * @param item specific item
	 */
	public void removeItem(Item item) {
		itemlist.remove(item);
	}

	/**
	 * Removes item from the item list
	 * 
	 * @param itemName item name
	 */
	public void removeItem(String itemName) {
		for (Item item : itemlist) {
			if (item.getName().equals(itemName)) {
				itemlist.remove(item);
				return;
			}
		}
	}

	/**
	 * Removes weapon from the item list
	 */
	public void removeWeapon() {
		if (containsAnyWeapon()) {
			for (Item item : itemlist) {
				if (item instanceof Weapon) {
					removeItem(item);
					return;
				}
			}
		}
	}

	/**
	 * Checks if the item list is empty
	 * 
	 * @return boolean empty
	 */
	public boolean isEmpty() {
		return itemlist.isEmpty();
	}

	/**
	 * Returns a String of all items in the item list
	 * 
	 * @return item list
	 */
	public String getItemList() {
		StringBuilder itemsInRoom = new StringBuilder();

		if (isEmpty()) {
			itemsInRoom.append(" EMPTY ");
		} else {
			for (Item item : this.itemlist) {
				itemsInRoom.append(item.getName().toLowerCase().trim() + " \n");
			}
			itemsInRoom.deleteCharAt(itemsInRoom.length() - 2);
		}
		String itemstring = itemsInRoom.toString();
		return itemstring;
	}

	/**
	 * Checks if an item is in the item list
	 * 
	 * @param itemName item name
	 * @return boolean contains
	 */
	public boolean contains(String itemName) {
		itemName = itemName.trim().toLowerCase();
		if (!isEmpty() && getItemList().contains(itemName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if an item is in the item list
	 * 
	 * @param item specific item
	 * @return boolean contains
	 */
	public boolean contains(Item item) {
		return itemlist.contains(item);
	}

	/**
	 * Checks if a weapon is in the item list
	 * 
	 * @return boolean contains
	 */
	public boolean containsAnyWeapon() {

		for (Item item : itemlist) {
			if (item instanceof Weapon) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a specific food is in the item list
	 * 
	 * @param foodName food name
	 * @return boolean contains
	 */
	public boolean containsFood(String foodName) {

		foodName = foodName.trim().toLowerCase();
		for (Item item : itemlist) {
			if (item instanceof Food) {
				if (item.getName().equals(foodName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Retrieves a food from item list
	 * 
	 * @return food
	 */
	public Food getFood() {
		for (Item item : itemlist) {
			if (item instanceof Food) {
				return (Food) item;
			}
		}
		return null;
	}

	/**
	 * Retrieves a weapon from item list
	 * 
	 * @return weapon
	 */
	public Weapon getWeapon() {
		for (Item item : itemlist) {
			if (item instanceof Weapon) {
				return (Weapon) item;
			}
		}
		return null;
	}

	/**
	 * Retrieves a valuable from item list
	 * 
	 * @return valuable
	 */
	public Valuable getValuable() {
		for (Item item : itemlist) {
			if (item instanceof Valuable) {
				return (Valuable) item;
			}
		}
		return null;
	}

	/**
	 * Retrieves an accessory from the item list
	 * 
	 * @return accessory
	 */
	public Accessory getAccessory() {
		for (Item item : itemlist) {
			if (item instanceof Accessory) {
				return (Accessory) item;
			}
		}
		return null;
	}

}
