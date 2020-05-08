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

//	/**
//	 * stores the amount (as value) and the name/kind (as key) of the items in the
//	 * inventory
//	 */
//	private HashMap<String, Integer> contents;

	// Konstruktor
	public Inventory() {
//		contents = new HashMap<String, Integer>();
		makeSettings();
		content = new Items();
	}

	public void addItem(Object ...o) {
		for (Object object : o) {
			if (object instanceof Food){
				content.addFood((Food)object);
				currentWeight += ((Food)object).getWeight();
			}
			else if (object instanceof Weapon) {
				content.addWeapon((Weapon)object);
				currentWeight += ((Weapon)object).getWeight();
			}
			else if (object instanceof Valuable) {
				content.addValuable((Valuable)object);
				currentWeight += ((Valuable)object).getWeight();
			}
			else {
				System.out.println("You can not put that into your backpack." );
			}
		}
	}
	

	public void removeItem(Object object) {
			if (object instanceof Food){
				content.removeFood((Food)object);
			}
			else if (object instanceof Weapon) {
				content.removeWeapon((Weapon)object);
			}
			else if (object instanceof Valuable) {
				content.removeValuable((Valuable)object);
			}
			else {
				System.out.println(object.toString() + " is not in your backpack.");
			}
	}
	
	public boolean contains(String specificitem){
		
		return content.contains(specificitem);
	}

	/**
	 * sets the maximum weight of the inventory depending on the value set by the
	 * user at the very beginning of the game.
	 */
	private void makeSettings() {

		setMaxWeight(Level.setValue(20, -5));
		System.out.println(
				"Here you get a backback. You can put things you find into it witch you will need later. It's maximum portable weight is "
						+ maxWeight + " kilo.");
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
