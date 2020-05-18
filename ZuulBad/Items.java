package ZuulBad;

import java.util.ArrayList;

public class Items {

	private ArrayList<Object> itemlist;

	public Items() {
		itemlist = new ArrayList<>();
	}

	public void addItem(Object item) {
		itemlist.add(item);
	}

	public void removeItem(Object item) {
		itemlist.remove(item);
	}

	public boolean isEmpty() {
		return itemlist.isEmpty();
	}

	public String getItemList() {
		StringBuilder itemsinroom = new StringBuilder();

		if (isEmpty()) {
			return "There are no items in here.";
		} else {
			for (Object item : itemlist) {
				itemsinroom.append(item.toString() + " ");
			}
		}
		String itemstring = itemsinroom.toString();
		return itemstring;

	}

	public boolean contains(Object object) {

		return itemlist.contains(object);
	}

	public Object toItsType(String name) {

		try {

			for (Food food : Food.values()) {
				if (food.toString().toLowerCase().equals(name)) {
					return Food.valueOf(name.toUpperCase());
				}
			}

			for (Weapon weapon : Weapon.values()) {
				if (weapon.toString().toLowerCase().equals(name)) {
					return Weapon.valueOf(name.toUpperCase());
				}
			}
			for (Valuable valuable : Valuable.values()) {
				if (valuable.toString().toLowerCase().equals(name)) {
					return Valuable.valueOf(name.toUpperCase());
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("This is not an Item.");
		}
		return null;
	}
}
