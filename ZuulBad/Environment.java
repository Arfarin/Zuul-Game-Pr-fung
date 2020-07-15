package ZuulBad;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class initializes all objects in the game, such as rooms, items, characters.
 * It also sets up the starting point of the game.
 * 
 * @author Sarah Engelmayer
 * @author Katerina Matysova
 * @author Daniel Birk
 */

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

	/**
	 * Create all the exits for the different rooms and where they lead to.
	 */
	
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

	/**
	 * Create items, defines their weight, gives them a description and distribute them to the room.
	 */
	
	private void createItemsInRooms() {

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
		
		
		//Accessoires
		Accessory chair = new Accessory("chair", 10, "a dark wooden chair");
		Accessory couch = new Accessory("blue couch", 10, "a blue and very cozy couch");
		Accessory desk = new Accessory("old desk", 10, "a desk with a quill and a vial of black ink on it");
		Accessory vase = new Accessory("giant vase", 10, "a giant vase decorated with mother-of-pearl and rose paintings");
		Accessory candlestick = new Accessory("huge candle holder", 50, "a golden three-armed candlestick");
		Accessory armour = new Accessory("armour", 40, "a heavy and shiny armour");
		Accessory bookcase = new Accessory("bookcase", 150, "a dusty, enchanted bookcase");
		Accessory chest = new Accessory("empty treasure chest", 20, "Alas, someone else was faster..");
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

	/**
	 * Give the room a condition:
	 * Possibilities are setting up monsters, locking rooms
	 * and changing a normal room to a teleporter room.
	 */
	
	private void addRoomConditions() {
		
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
	
	/**
	 * Create NPCs and put them in the intended room.
	 */
	
	private void createNPC() {
		Room.CastleCourtyard.createNPC("glasses");
		Room.Pantry.createNPC("tupperware");
		Room.KingsChamber.createNPC("tiara");
		Room.DiningRoom.createNPC("spoon");

	}

	/**
	 * Set up the starting point of the game.
	 * 
	 * @return room
	 */
	
	public final Room getFirstRoom() {
		return Room.CastleCourtyard;
	}
	
	/**
	 * 
	 * Getter for all items available in the game 
	 * @return 
	 */
	
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
	
	/**
	 * 
	 * 
	 * @return accessories specific accessory
	 */
	public ArrayList<Accessory> getListOfAccessories(){
		return accessories;
	}

}
