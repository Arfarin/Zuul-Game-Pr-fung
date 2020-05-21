package ZuulBad;

import java.util.ArrayList;

public class Environment {

	private Items itemsOfGame;
	private static ArrayList<Food> foodsOfGame;
	private static ArrayList<Weapon> weaponsOfGame;
	private static ArrayList<Valuable> valuablesOfGame;
	private static ArrayList<MagicMuffin> magicMuffinsOfGame;

	public Environment() {
		itemsOfGame = new Items();
		foodsOfGame = new ArrayList<Food>();
		weaponsOfGame = new ArrayList<Weapon>();
		valuablesOfGame = new ArrayList<Valuable>();
		magicMuffinsOfGame = new ArrayList<MagicMuffin>();
		prepareEnvironment();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private final void prepareEnvironment() {

		setRoomExits();
		createItemsInRooms();
		addRoomConditions();
		createNPC();

	}

	private void setRoomExits() {
		// 3 different levels: Tower - Ground level - Basement
		Room.CastleCourtyard.setExit("north", Room.EntryHall);
		Room.CastleCourtyard.setExit("east", Room.CastleGarden);
		Room.CastleCourtyard.setExit("south", Room.DesertedWineStorage);
		Room.CastleCourtyard.setExit("west", Room.Warehouse);

		Room.CastleGarden.setExit("south", Room.FlowerGarden);
		Room.CastleGarden.setExit("west", Room.CastleCourtyard);

		Room.FlowerGarden.setExit("north", Room.CastleGarden);
		Room.FlowerGarden.setExit("east", Room.KingsChamber);

		Room.KingsChamber.setExit("west", Room.FlowerGarden);
		
		Room.EntryHall.setExit("east", Room.TowerStaircases);
		Room.EntryHall.setExit("south", Room.CastleCourtyard);
		Room.EntryHall.setExit("west", Room.DiningRoom);

		Room.DestroyedTower.setExit("east", Room.TeleporterRoom);
		Room.DestroyedTower.setExit("down", Room.TowerStaircases);
		
		Room.DiningRoom.setExit("east", Room.EntryHall);
		Room.DiningRoom.setExit("west", Room.Kitchen);

		Room.Kitchen.setExit("north", Room.Pastry);
		Room.Kitchen.setExit("east", Room.DiningRoom);
		Room.Kitchen.setExit("south", Room.Pantry);

		Room.Pastry.setExit("south", Room.Kitchen);

		Room.Pantry.setExit("north", Room.Kitchen);
		Room.Pantry.setExit("east", Room.Warehouse);

		Room.Warehouse.setExit("west", Room.Pantry);
		Room.Warehouse.setExit("east", Room.CastleCourtyard);

		Room.TowerStaircases.setExit("up", Room.DestroyedTower);
		Room.TowerStaircases.setExit("west", Room.EntryHall);
		
		Room.DesertedWineStorage.setExit("down", Room.BasementEntry);
		Room.DesertedWineStorage.setExit("north", Room.CastleCourtyard);

		Room.BasementEntry.setExit("south", Room.Armoury);

		Room.Armoury.setExit("north", Room.BasementEntry);
		Room.Armoury.setExit("east", Room.UndergroundHallway);
		Room.Armoury.setExit("west", Room.TreasureChamber);

		Room.TreasureChamber.setExit("east", Room.Armoury);

		Room.UndergroundHallway.setExit("east", Room.HiddenPath);
		Room.UndergroundHallway.setExit("west", Room.Armoury);


		Room.HiddenPath.setExit("north", Room.MistyRoom);
		Room.HiddenPath.setExit("west", Room.HiddenPath);

		Room.MistyRoom.setExit("north", Room.BattleZone);
		Room.MistyRoom.setExit("west", Room.HiddenPath);

		Room.BattleZone.setExit("south", Room.MistyRoom);
		Room.BattleZone.setExit("west", Room.Dungeon);


		Room.Dungeon.setExit("up", Room.KingsChamber);
		Room.Dungeon.setExit("south", Room.UndergroundHallway);
	}


	private void createItemsInRooms() {

		// create items
		Food banana = new Food("banana", 5, "This is a yummy banana");
		foodsOfGame.add(banana);
		itemsOfGame.addItem(banana);
		Food apple = new Food("apple", 5, "This is a yummy apple");
		foodsOfGame.add(apple);
		itemsOfGame.addItem(apple);
		Food starfruit = new Food("starfruit", 5, "This is a yummy starfruit");
		foodsOfGame.add(starfruit);
		itemsOfGame.addItem(starfruit);
		MagicMuffin magicMuffin = new MagicMuffin("magic muffin", 5,
				"This is a muffin made according to a special secret recipe of the castle");
		magicMuffinsOfGame.add(magicMuffin);
		foodsOfGame.add(magicMuffin);
		itemsOfGame.addItem(magicMuffin);

		Weapon toothpick = new Weapon("toothpick", 2,
				"It might not look like much, but used the right way it is a magic tool.");
		weaponsOfGame.add(toothpick);
		itemsOfGame.addItem(toothpick);
		Weapon hairbrush = new Weapon("hairbrush", 3, "Don't underestimate the hairbrush of a lady!.");
		weaponsOfGame.add(hairbrush);
		itemsOfGame.addItem(hairbrush);
		Weapon nail = new Weapon("nail", 4, "Old and rusty, but could be useful in the future.");
		weaponsOfGame.add(nail);
		itemsOfGame.addItem(nail);
		Weapon knife = new Weapon("knife", 5, "A knife is always good to protect yourself.");
		weaponsOfGame.add(knife);
		itemsOfGame.addItem(knife);
		Weapon dagger = new Weapon("dagger", 6, "Its short, double-edged blade could come in handy at some point...");
		weaponsOfGame.add(dagger);
		itemsOfGame.addItem(dagger);
		Weapon axe = new Weapon("axe", 9, "You could chop a tree with that axe.");
		weaponsOfGame.add(axe);
		itemsOfGame.addItem(axe);
		Weapon sword = new Weapon("sword", 10, "This must have belonged to a strong man and mighty soldier.");
		weaponsOfGame.add(sword);
		itemsOfGame.addItem(sword);

		Valuable key = new Valuable("key", 4, "Don't loose this one.");
		valuablesOfGame.add(key);
		itemsOfGame.addItem(key);
		Valuable dragonglass = new Valuable("dragonglass", 7, "WOW! This looks like it could even kill dragons.");
		valuablesOfGame.add(dragonglass);
		itemsOfGame.addItem(dragonglass);
		Valuable hat = new Valuable("medieval hat", 3, "Quite fashionable if you are into that sort of thing.");
		valuablesOfGame.add(hat);
		itemsOfGame.addItem(hat);
		Valuable plant = new Valuable("avocado plant", 5,
				"Try to get this to the owner ASAP, maybe they can still save it.");
		valuablesOfGame.add(plant);
		itemsOfGame.addItem(plant);
		Valuable phone = new Valuable("phone", 6,
				"Sadly you don't know the PIN. Also, what is this doing in an old castle?");
		valuablesOfGame.add(phone);
		itemsOfGame.addItem(phone);
		Valuable sock = new Valuable("green sock", 2, "This must have been useful to someone at some point.");
		valuablesOfGame.add(sock);
		itemsOfGame.addItem(sock);
		Valuable candle = new Valuable("nice smelling candle", 3, "Lavender always makes me so sleepy...");
		valuablesOfGame.add(candle);
		itemsOfGame.addItem(candle);
		Valuable book = new Valuable("old book", 5,
				"Oh look, here is recipe for a love potion. Do you believe in that sort of thing?");
		valuablesOfGame.add(book);
		itemsOfGame.addItem(book);
		Valuable bottle = new Valuable("old bottle of wine", 5,
				"This must be a very old vintage. Alas, it looks unenjoyable.");
		valuablesOfGame.add(bottle);
		itemsOfGame.addItem(bottle);

		// put items into rooms
		Room.CastleCourtyard.addItem(starfruit);
		Room.CastleGarden.addItem(apple, knife);
		Room.FlowerGarden.addItem();
		Room.KingsChamber.addItem(hairbrush);
		Room.EntryHall.addItem(banana, toothpick);
		Room.TowerStaircases.addItem();
		Room.DestroyedTower.addItem(banana, nail, key);
		Room.DiningRoom.addItem(key);
		Room.Kitchen.addItem();
		Room.Pastry.addItem(magicMuffin);
		Room.Warehouse.addItem(dagger);
		Room.Pantry.addItem();
		Room.DesertedWineStorage.addItem();
		Room.BasementEntry.addItem();
		Room.Armoury.addItem();
		Room.TreasureChamber.addItem(dragonglass);
		Room.UndergroundHallway.addItem(key, axe);
		Room.HiddenPath.addItem();
		Room.MistyRoom.addItem(sword);

		Room.Dungeon.addItem();

	}

	private void addRoomConditions() {
		// set up Monsters, locked status, and teleporter room
		Room.Pastry.lockRoom();
		Room.DesertedWineStorage.lockRoom();
		Room.TreasureChamber.lockRoom();

		Room.TeleporterRoom.makeTeleporterRoom();

		Room.TowerStaircases.putMonster();
		Room.Kitchen.putMonster();
		Room.FlowerGarden.putMonster();
		Room.Armoury.putMonster();
		Room.UndergroundHallway.putMonster();
		Room.HiddenPath.putMonster();
		Room.Dungeon.putMonster();
		Room.Dungeon.makeFinalRoom();

	}

	private void createNPC() {
		Room.CastleCourtyard.createNPC("glasses");
		Room.Pantry.createNPC("tupperware");
		Room.KingsChamber.createNPC("tiara");
		Room.DiningRoom.createNPC("spoon");

	}

	public final Room getFirstRoom() {
		return Room.CastleCourtyard;
	}

	public Item getItem(String itemName) {
		return itemsOfGame.getItem(itemName);
	}

	public static Food getFood(String foodName) {
		for (Food food : foodsOfGame) {
			if (food.getName().toLowerCase().trim().equals(foodName.toLowerCase().trim())) {
				return food;
			}
		}
		return null;
	}

	public static Weapon getWeapon(String weaponName) {
		for (Weapon weapon : weaponsOfGame) {
			if (weapon.getName().toLowerCase().trim().equals(weaponName.toLowerCase().trim())) {
				return weapon;
			}
		}
		return null;
	}

	public static Valuable getValuable(String valuableName) {
		for (Valuable valuable : valuablesOfGame) {
			if (valuable.getName().toLowerCase().trim().equals(valuableName.toLowerCase().trim())) {
				return valuable;
			}
		}
		return null;
	}

	public ArrayList<Weapon> getWeaponsOfGame() {
		return weaponsOfGame;
	}

	public ArrayList<Valuable> getValuablesOfGame() {
		return valuablesOfGame;
	}

	public Items getItemsOfGame() {
		return itemsOfGame;
	}

	public MagicMuffin getMuffin(Food food) {
		String possibleMuffin = food.getName().toLowerCase().trim();
		for (MagicMuffin muffin : magicMuffinsOfGame) {
			if (possibleMuffin.equals(muffin.getName().toLowerCase().trim())) {
				return muffin;
			}
		}
		return null;
	}

	public MagicMuffin getMuffin(String foodName) {
		String possibleMuffin = foodName.toLowerCase().replaceAll(" ","");
		for (MagicMuffin muffin : magicMuffinsOfGame) {
//			String muffinString = 
//			muffinString = muffinString.replaceAll(" ","");
			if (possibleMuffin.equals(muffin.getName().toLowerCase().replaceAll(" ",""))) {
				return muffin;
			}
		}
		return null;
	}
