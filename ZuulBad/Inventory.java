package ZuulBad;

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
		setMaxWeight(Level.setValue(20, -5));
		content = new Items();
	}

	public void addItem(Object... o) {
		for (Object object : o) {
			content.addItem(object);
		}
	}

	public boolean removeItem(Object object) {
		boolean removed = true;

		if (contains(object)) {
			content.removeItem(object);


		} else {
			System.out.println("Sorry, your backpack doesn't contain that.");
			removed = false;
		}
	
		return removed;
	}

	public boolean contains(Object specificitem) {

		return content.contains(specificitem);
	}

	public String getListOfContent() {
		return content.getItemList();
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public boolean isFull(int itemWeight) {
		if (currentWeight + itemWeight > maxWeight) {
			return true;
		} else {
			return false;
		}

	}
}
