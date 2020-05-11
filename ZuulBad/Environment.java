package ZuulBad;

public class Environment {

	
	public Environment() {
		prepareEnvironment();
	}
	
	/**
	 * Create all the rooms and link their exits together.
	 */
	private void prepareEnvironment() {
		
		setRoomExits();
		addRoomItems();
		addRoomConditions();
		createNPC();

	}
	
	private void setRoomExits() {

		Room.OUTSIDE.setExit("east", Room.THEATER);
		Room.OUTSIDE.setExit("south", Room.LAB);
		Room.OUTSIDE.setExit("west", Room.PUB);

		Room.THEATER.setExit("west", Room.OUTSIDE);

		Room.PUB.setExit("east", Room.OUTSIDE);
		Room.PUB.setExit("down", Room.BASEMENT);

		Room.LAB.setExit("north", Room.OUTSIDE);
		Room.LAB.setExit("east", Room.OFFICE);

		Room.OFFICE.setExit("west", Room.LAB);

		Room.BASEMENT.setExit("up", Room.PUB);
	}
	
	private void addRoomItems() {

		Room.OUTSIDE.addItem(Food.STARFRUIT, Valuable.KEY, Valuable.PHONE);
		Room.PUB.addItem(Food.BANANA, Weapon.TOOTHPICK);
		Room.THEATER.addItem(Food.BANANA, Food.APPLE, Food.MUFFIN,
							Weapon.NAIL, Weapon.SWORD);
		Room.LAB.addItem(Food.BANANA, 
					Weapon.TOOTHPICK);
	}

	private void addRoomConditions() {
		// set up Monsters, locked status, and teleporter room
		Room.OFFICE.lockRoom();
		Room.PUB.makeTeleporterRoom();
		Room.THEATER.putMonster();
	}
	
	private void createNPC() {
		Room.OUTSIDE.createNPC("book");
		Room.THEATER.createNPC("candle");
		Room.PUB.createNPC("sock");
		Room.LAB.createNPC("phone");
		Room.OFFICE.createNPC("plant");
		Room.BASEMENT.createNPC("cap");
	}
	
	public Room getFirstRoom() {
		return Room.OUTSIDE;
	}
}
