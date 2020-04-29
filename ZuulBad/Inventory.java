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
	 * stores the amount (as value) and the name/kind (as key) of the items in the
	 * inventory
	 */
	private HashMap<String, Integer> contents;

	// Konstruktor
	public Inventory() {
		contents = new HashMap<String, Integer>();
//		maxWeight = getUsersWeightSelection();
	}

	public void setMaxWeight(int maxWeight) {
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
