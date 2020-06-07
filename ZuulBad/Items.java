package ZuulBad;

import java.util.ArrayList;

public class Items {

	private ArrayList<Item> itemlist;

	public Items() {
		itemlist = new ArrayList<Item>();
	}

	public void addItem(Item item) {
		itemlist.add(item);
	}

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

	public Item getItem(String itemName) {
		for (Item item : itemlist) {
			if (item.getName().toLowerCase().trim().contains(itemName.toLowerCase().trim())) {
				return item;
			}
		}	
		return null;
	}
	
	public void removeItem(Item item) {
		itemlist.remove(item);
	}

	public void removeItem(String itemName) {
		for (Item item : itemlist) {
			if (item.getName().equals(itemName)) {
				itemlist.remove(item);
				return;
			}
		}
	}

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

	public boolean isEmpty() {
		return itemlist.isEmpty();
	}

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
	

	public boolean contains(String itemName) {
		itemName = itemName.trim().toLowerCase();
		if (!isEmpty() && getItemList().contains(itemName)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(Item item) {
		return itemlist.contains(item);
	}

	public boolean containsAnyWeapon() {

		for (Item item : itemlist) {
			if (item instanceof Weapon) {
				return true;
			}
		}
		return false;
	}

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


	public Food getFood() {
		for (Item item : itemlist) {
			if (item instanceof Food) {
				return (Food) item;
			} 
		}
		return null;
	}
		
		public Weapon getWeapon() {
			for (Item item : itemlist) {
				if (item instanceof Weapon) {
					return (Weapon) item;
				} 
			}
			return null;
		}
		
		public Valuable getValuable() {
			for (Item item : itemlist) {
				if (item instanceof Valuable) {
					return (Valuable) item;
				} 
			}
			return null;
		}
		public Accessory getAccessory() {
			for (Item item : itemlist) {
				if (item instanceof Accessory) {
					return (Accessory) item;
				} 
			}
			return null;
		}
	

}
