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
			if (object instanceof Food) {
				content.addFood((Food) object);
				currentWeight += ((Food) object).getWeight();
			} else if (object instanceof Weapon) {
				content.addWeapon((Weapon) object);
				currentWeight += ((Weapon) object).getWeight();
			} else if (object instanceof Valuable) {
				content.addValuable((Valuable) object);
				currentWeight += ((Valuable) object).getWeight();
			} else {
				System.out.println("You can not put that into your backpack.");
			}
		}
	}

	public boolean removeItem(Object object) {
		boolean removed = true;
		if (contains(object.toString())) {
			if (object instanceof Food) {
				content.removeFood((Food) object);
			} else if (object instanceof Weapon) {
				content.removeWeapon((Weapon) object);
			} else if (object instanceof Valuable) {
				content.removeValuable((Valuable) object);
			} else {
				System.out.println(object.toString() + " is not a storable thing.");
				removed = false;
			}
		} else {
			System.out.println("Sorry, your backpack doesn't contain that.");
			removed = false;
		}
		return removed;

	}

	public boolean contains(String specificitem) {

		return content.contains(specificitem);
	}
	
	public String getListOfContent(){
		return content.getItemList();
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public boolean checkIfFull() {
		if (currentWeight > maxWeight) {
			return true;
		} else {
			return false;
		}

	}
}
