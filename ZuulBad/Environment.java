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
		Room.HiddenPath.setExit("west", Room.HiddenPath);
		
		Room.Dungeon.setExit("up", Room.KingsChamber);
		Room.Dungeon.setExit("south", Room.UndergroundHallway);
	}
	
	private void addRoomItems() {
		// 7 weapons, 8 foods, 3 keys, muffin, dragonglass added
		Room.CastleCourtyard.addItem(Food.STARFRUIT);
		Room.CastleGarden.addItem(Food.APPLE, Weapon.KNIFE);
		Room.FlowerGarden.addItem();
		Room.KingsChamber.addItem(Weapon.HAIRBRUSH);
		Room.EntryHall.addItem(Food.BANANA, Weapon.TOOTHPICK);
		Room.TowerStaircases.addItem();
		Room.DestroyedTower.addItem(Food.BEANS, Weapon.NAIL, Valuable.KEY);
		Room.DiningRoom.addItem(Valuable.KEY);
		Room.Kitchen.addItem(Food.LEFTOVERS);
		Room.Pastry.addItem(Food.MUFFIN);
		Room.Warehouse.addItem(Weapon.DAGGER);
		Room.Pantry.addItem();
		Room.DesertedWineStorage.addItem();
		Room.BasementEntry.addItem();
		Room.Armoury.addItem(Food.TOMATOES, Weapon.AXE);
		Room.TreasureChamber.addItem(Valuable.DRAGONGLASS);
		Room.UndergroundHallway.addItem(Valuable.KEY, Weapon.SWORD);
		Room.HiddenPath.addItem(Food.BREAD);
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
		Room.CastleCourtyard.createNPC("old book");
		Room.Pantry.createNPC("nice smelling candle");
		Room.KingsChamber.createNPC("sock");
		Room.BasementEntry.createNPC("old bottle of wine");
		Room.DiningRoom.createNPC("spoon");

	}
	
	
	public Room getFirstRoom() {
		return Room.CastleCourtyard;
	}
	
	
}
