package ZuulBad;

import java.util.ArrayList;
import java.util.Collections;

public class Environment{

	private Items itemsOfGame;
	private static ArrayList<Food> foodsOfGame;
	private static ArrayList<Weapon> weaponsOfGame;
	private static ArrayList<Valuable> valuablesOfGame;
	private ArrayList<Accessory> accessories = new ArrayList<Accessory>(); 

	public Environment() {
		itemsOfGame = new Items();
		foodsOfGame = new ArrayList<Food>();
		weaponsOfGame = new ArrayList<Weapon>();
		valuablesOfGame = new ArrayList<Valuable>();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	public void prepareEnvironment() {

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

		Room.HiddenPath.setExit("north", Room.Dungeon);
		Room.HiddenPath.setExit("west", Room.UndergroundHallway);

		Room.Dungeon.setExit("up", Room.KingsChamber);
		Room.Dungeon.setExit("south", Room.HiddenPath);
	}

	private void createItemsInRooms() {

		// create items
		//Foods
		Food banana = new Food("banana", 2, "Eat this banana and it will give you lots of energy.");
		foodsOfGame.add(banana);
		itemsOfGame.addItem(banana);
		
		Food beans = new Food("beans", 3, "Those beans are very healthy.");
		foodsOfGame.add(beans);
		itemsOfGame.addItem(beans);
		
		Food bread = new Food("bread", 4, "Just some old bread but it is better than nothing...");
		foodsOfGame.add(bread);
		itemsOfGame.addItem(bread);
		
		Food leftovers = new Food("leftovers", 5, "Leftovers are always a good snack.");
		foodsOfGame.add(leftovers);
		itemsOfGame.addItem(leftovers);
		
		Food tomatoes = new Food("tomatoes", 2, "These tomatoes look really tasty!");
		foodsOfGame.add(tomatoes);
		itemsOfGame.addItem(tomatoes);
		
		Food apple = new Food("apple", 3, "An apple is always good when you are hungry.");
		foodsOfGame.add(apple);
		itemsOfGame.addItem(apple);
		
		Food starfruit = new Food("starfruit", 3, "This fruit looks like it is from another planet.");
		foodsOfGame.add(starfruit);
		itemsOfGame.addItem(starfruit);
		
		//Magic muffin
		Food magicMuffin = new Food("magic muffin", 5,
				"This magically looking muffin is made according to a special recipe of the castle.");
		foodsOfGame.add(magicMuffin);
		itemsOfGame.addItem(magicMuffin);

		//Weapons
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
		
		Weapon axe = new Weapon("axe", 4, "You could chop a tree with that axe.");
		weaponsOfGame.add(axe);
		itemsOfGame.addItem(axe);
		
		Weapon sword = new Weapon("sword", 6, "This must have belonged to a strong man and mighty soldier.");
		weaponsOfGame.add(sword);
		itemsOfGame.addItem(sword);

		// Valuables
		Valuable key = new Valuable("key", 2, "Don't loose this one.");
		valuablesOfGame.add(key);
		itemsOfGame.addItem(key);
		
		Valuable dragonglass = new Valuable("dragonglass", 5, "WOW! This looks like it could even kill dragons.");
		valuablesOfGame.add(dragonglass);
		itemsOfGame.addItem(dragonglass);
		
		Valuable tiara = new Valuable("tiara", 5, "This is a very beautiful golden tiara.");
		valuablesOfGame.add(tiara);
		itemsOfGame.addItem(tiara);
		
		Valuable glasses = new Valuable("glasses", 3, "Oh look there's a pair of glasses. The lenses are so thick... the person they belong to has to be blind without them.");
		valuablesOfGame.add(glasses);
		itemsOfGame.addItem(glasses);
		
		Valuable spoon = new Valuable("spoon", 2, "Just a dirty spoon. Probably a hungry person would still use it.");
		valuablesOfGame.add(spoon);
		itemsOfGame.addItem(spoon);
		
		Valuable tupperware = new Valuable("tupperware", 4, "This looks like your moms tupperware. I didn't know that tupperware already existed in this era.");
		valuablesOfGame.add(tupperware);
		itemsOfGame.addItem(tupperware);
		
//		Valuable hat = new Valuable("medieval hat", 3, "Quite fashionable if you are into that sort of thing.");
//		valuablesOfGame.add(hat);
//		itemsOfGame.addItem(hat);
//		
//		Valuable plant = new Valuable("avocado plant", 5,
//				"Try to get this to the owner ASAP, maybe they can still save it.");
//		valuablesOfGame.add(plant);
//		itemsOfGame.addItem(plant);
//		
//		Valuable phone = new Valuable("phone", 6,
//				"Sadly you don't know the PIN. Also, what is this doing in an old castle?");
//		valuablesOfGame.add(phone);
//		itemsOfGame.addItem(phone);
//		
//		Valuable sock = new Valuable("green sock", 2, "This must have been useful to someone at some point.");
//		valuablesOfGame.add(sock);
//		itemsOfGame.addItem(sock);
//		
//		Valuable candle = new Valuable("nice smelling candle", 3, "Lavender always makes me so sleepy...");
//		valuablesOfGame.add(candle);
//		itemsOfGame.addItem(candle);
//		
//		Valuable book = new Valuable("old book", 5,
//				"Oh look, here is recipe for a love potion. Do you believe in that sort of thing?");
//		valuablesOfGame.add(book);
//		itemsOfGame.addItem(book);
//		
//		Valuable bottle = new Valuable("old bottle of wine", 5,
//				"This must be a very old vintage. Alas, it looks unenjoyable.");
//		valuablesOfGame.add(bottle);
//		itemsOfGame.addItem(bottle);
//		
		//Accessoires
		Accessory chair = new Accessory("chair", 10, "a dark wooden chair");
		Accessory couch = new Accessory("blue couch", 10, "a blue and very cosy couch");
		Accessory desk = new Accessory("old desk", 10, "a desk with a quill and a vial of black ink on it");
		Accessory vase = new Accessory("giant vase", 10, "a giant vase decorated with mother-of-pearl and rose paintings");
		Accessory candlestick = new Accessory("huge candle holder", 50, "a golden three-armed candlestick");
		Accessory armour = new Accessory("armour", 40, "a heavy and shiny armour");
		Accessory bookcase = new Accessory("bookcase", 150, "a dusty, enchanted bookcase");
		Accessory chest = new Accessory("empty treasure chest", 20, "Alas, someone else was faster..");
//		Accessory bricks = new Accessory("bricks", 1, "scrolled paraffin lamp");
//		Accessory glass = new Accessory("sharp broken glass", 1, "a piece of a broken glass painting");
		Collections.addAll(accessories, chair, couch, desk, vase, candlestick, armour, bookcase, chest);

		// put items into rooms
		Room.CastleCourtyard.addItem(starfruit, chair);
		Room.CastleGarden.addItem(apple, knife);
		Room.FlowerGarden.addItem(couch);
		Room.KingsChamber.addItem(hairbrush, chest);
		Room.EntryHall.addItem(banana, toothpick, glasses);
		Room.TowerStaircases.addItem();
		Room.DestroyedTower.addItem(beans, nail, key);
		Room.DiningRoom.addItem(key);
		Room.Kitchen.addItem(leftovers);
		Room.Pastry.addItem(magicMuffin, candlestick);
		Room.Warehouse.addItem(dagger, couch);
		Room.Pantry.addItem(tiara);
		Room.DesertedWineStorage.addItem(tupperware, desk);
		Room.BasementEntry.addItem(spoon, vase);
		Room.Armoury.addItem(tomatoes, axe, armour);
		Room.TreasureChamber.addItem(dragonglass);
		Room.UndergroundHallway.addItem(key, sword, bookcase);
		Room.HiddenPath.addItem(bread);
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
	
	public ArrayList<Accessory> getListOfAccessories(){
		return accessories;
	}

}
