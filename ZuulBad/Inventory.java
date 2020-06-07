package ZuulBad;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inventory {
	/**
	 * specifies the maximum permissible weight of the inventory
	 */
	private int maxWeight;

	/**
	 * specifies the current weight of the inventory
	 */
	private SimpleIntegerProperty currentWeight;

	/**
	 * the current content of the inventory
	 */
	private Items content;

	public Inventory() {
		setMaxWeight(Level.setValue(23, -5));
		content = new Items();
		currentWeight = new SimpleIntegerProperty(0);
	}

	public IntegerProperty backpackWeightProperty() {
		return currentWeight;
	}
	
	public void addItemToBackpack(Item... items) {
		for (Item item : items) {
			content.addItem(item);
			currentWeight.setValue(currentWeight.getValue() + item.getWeight());
		}
	}

	public boolean removeItem(Item item) {
		boolean removed = true;

		if (content.contains(item)) {
			content.removeItem(item);
			currentWeight.setValue(currentWeight.getValue() - item.getWeight());
		} else {
			System.out.println("Sorry, your backpack doesn't contain that.");
			removed = false;
		}
		return removed;
	}

	public void removeWeapon() {
		currentWeight.setValue(currentWeight.getValue() - content.getWeapon().getWeight());
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
		if (currentWeight.getValue() + itemWeight > maxWeight) {
			return true;
		} else {
			return false;
		}
	}

	public int getRemainingFreeWeight() {
		return maxWeight - currentWeight.getValue();
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

}
