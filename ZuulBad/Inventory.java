package ZuulBad;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
	/**
	 * specifies the maximum permissible weight of the inventory
	 */
	private int maxWeight;

	/**
	 * specifies the current weight of the inventory
	 */
	private int currentWeight;

	/**
	 * the current content of the inventory
	 */
	private Items content;

	public Inventory() {
		setMaxWeight(Level.setValue(23, -5));
		content = new Items();
	}

	public void addItemToBackpack(Item... items) {
		for (Item item : items) {
			content.addItem(item);
			currentWeight = +item.getWeight();
		}
	}

	public boolean removeItem(Item item) {
		boolean removed = true;

		if (content.contains(item)) {
			content.removeItem(item);
			currentWeight = -item.getWeight();
		} else {
			System.out.println("Sorry, your backpack doesn't contain that.");
			removed = false;
		}
		return removed;
	}

	public void removeWeapon() {
		content.removeWeapon();
	}

	public boolean contains(Item specificitem) {
		return content.contains(specificitem);
	}

	public boolean containsFood(String foodName) {
		return content.containsFood(foodName);
	}

	public boolean containsWeapon() {
		return content.containsAnyWeapon();
	}

	public String getListOfContent() {
		return content.getItemList();
	}

	public final void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public boolean isFull(int itemWeight) {
		if (currentWeight + itemWeight > maxWeight) {
			return true;
		} else {
			return false;
		}
	}

	public int getRemainingFreeWeight() {
		return maxWeight - currentWeight;
	}

}
