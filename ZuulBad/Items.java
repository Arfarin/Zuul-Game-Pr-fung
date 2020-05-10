package ZuulBad;

import java.util.ArrayList;

public class Items {
	
		private ArrayList<Food> foodlist;
		private ArrayList<Weapon> weaponlist;
		private ArrayList<Valuable> valuablelist;
	
		public Items() {
			foodlist = new ArrayList<>();
			weaponlist = new ArrayList<>();
			valuablelist = new ArrayList<>();
		}
		
		public void addFood(Food food) {
			foodlist.add(food);
		}
		
		public void addWeapon(Weapon weapon) {
			weaponlist.add(weapon);
		}
		
		public void addValuable(Valuable valuable) {
			valuablelist.add(valuable);
		}
		
		public void removeFood(Food food) {
			foodlist.remove(food);
		}
		
		public void removeWeapon(Weapon weapon) {
			weaponlist.remove(weapon);
		}
		
		public void removeValuable(Valuable valuable) {
			valuablelist.remove(valuable);
		}
		
		public boolean isEmpty() {
			if(foodlist.isEmpty() && weaponlist.isEmpty() && valuablelist.isEmpty()) {
				// checks if any of the lists has an item
				return true;
			} else {
				return false;
			}
		}
		
		public String getItemList() {
			StringBuilder itemsinroom = new StringBuilder();

			if (isEmpty()) {
				return "There are no items in here.";
			} else {
				if (!foodlist.isEmpty()) {
					for (Food food : foodlist) {
						itemsinroom.append(food.toString() + " ");
					}
				}

				if (!weaponlist.isEmpty()) {
					for (Weapon weapon : weaponlist) {
						itemsinroom.append(weapon.toString() + " ");
					}
				}
				if (!valuablelist.isEmpty()) {
					for (Valuable valuable : valuablelist) {
						itemsinroom.append(valuable.toString() + " ");
					}
				}
			}

			String itemstring = itemsinroom.toString();
			return itemstring;

		}

		public boolean contains(String name) {
			// The parameter is String so that it can be used for all Item types
			boolean contains = false;
			
			if (getItemList().contains(name.toUpperCase())) { //check if name of item is in item list
				contains = true;
			}
			
			return contains;
		}
}

