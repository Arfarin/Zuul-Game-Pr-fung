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

	/**
	 * stores the amount (as value) and the name/kind (as key) of the items in the
	 * inventory
	 */
	private HashMap<String, Integer> contents;

	// Konstruktor
	public Inventory() {
		contents = new HashMap<String, Integer>();
		makeSettings();
		content = new Items();
	}

	/**
	 * sets the maximum weight of the inventory depending on the value set by the
	 * user at the very beginning of the game.
	 */
	private void makeSettings() {
		
		setMaxWeight(Level.setValue(20, -5));
		System.out.println("Here you get a backback. You can put things you find into it witch you will need later. It's maximum portable weight is " + maxWeight + " kilo.");
	}

	private void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public HashMap<String, Integer> getInventory() {
		return contents;
	}

	public boolean checkIfFull() {
		if (currentWeight > maxWeight) {
			return true;
		} else {
			return false;
		}

//	public void storeItem(Item item){ 
//		     if (checkIfFull() == false) {
//		                 currentWeight += item.getWeight();
//		} 
//		     else{
//		    	 System.out.println(Printer.weightTooHighError());
//		     }
//			
//		}
	}
}
