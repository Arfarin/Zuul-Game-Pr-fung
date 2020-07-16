
package ZuulBad;

import java.util.HashMap;



 /** This enumeration represents the locations in the scenery of the game. Each room is 
 * connected to other rooms via exits. Some exits can only be entered from one room the another 
 * but not back, so these exits are trapdoors.
 * There is an instance of the class Items, which is
 * used to store items contained in the room. 
 * There are some special features of a room. E.g. a room can be a able to teleport the player.
 * 
 * @author Daniel Birk
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 * 
 * @version 1.0
 */

public enum Room{
	
	CastleCourtyard("Starting point: You are in front of a very old castle."),
	CastleGarden("You enter the castle's magnificent garden."),
	FlowerGarden("You are now surrounded by beautiful flowers."),
	KingsChamber("You reach the holy chambers of the royal family."),
	EntryHall("You enter a breathtakingly big hall."),
	TowerStaircases("You find staircases which will take you to the tower."),
	DestroyedTower("Unfortunatelly, the tower is partly destroyed."),
	DiningRoom("You enter a huge dining area."),
	Kitchen("This kitchen is massive."),
	Pastry("Here are all the yummie desserts. Enjoy!"),
	Pantry("There is still some cooked food here."),
	Warehouse("You reach a room full of boxes and stuff."),
	DesertedWineStorage("Oh yes. Here are hundreds of bottles of wine."),
	BasementEntry("You are now in a spooky basement."),
	Armoury("This room is full of armour and knight statues."),
	TreasureChamber("WOW! You enter a treasure chamber full of gold and jewels."),
	UndergroundHallway("Where does this strange hallway lead to?"),
	HiddenPath("This path looks like nobody has seen it before."),
	Dungeon("Nobody wants so be hold captive in this cold dungeon."),
	TeleporterRoom("Everything is moving... What is happening?");
	
	/**
	 * The description of the room.
	 * 
	 */
	private String description;
	
	/**
	 * Store exits of the rooms. For each exit put a String (describing the direction 
	 * in which the exit is e.g. "north") as its key and the room behind this exit as its value.
	 * 
	 */
    private HashMap<String, Room> exits;        
    /**
	 * A non player character (npc) in the room.
	 * 
	 */
    private NonPlayerCharacter npc;
    /**
	 *  A lit of items which are in the room.
	 * 
	 */
    private Items itemList;
    /**
	 * The number how often a room is entered. 	
	 * 
	 */
	private int roomEntries;
	/**
	 * A room condition describing if the room is locked. 
	 * 
	 */
	private boolean locked;
	/**
	 * A room condition describing if the room is a teleporter room. 
	 * 'True' if the room is a teleporter room, 'false' otherwise.
	 * 
	 */
	private boolean teleports;
	/**
	 * A room condition describing if a room contains monsters.
	 * 'True' if the room has a monster, 'false' otherwise.
	 * 
	 */
	private boolean monster;
	/**
	 * A room condition describing if the room is the final room. 
	 * 'True' if the room is the final room, 'false' otherwise.
	 * 
	 */
	private boolean finalroom;

    /**
     * Create a room described by its description (as a string). Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * Initialize the variables exits, itelList, roomentries, locked, teleports, monster, finalroom.
     * 
     * @param description The room's description.
     */
    Room(String description) 
    {

    	this.description = description;
       
        exits = new HashMap<>();
        itemList = new Items();
        
        roomEntries = 0;
        locked = false;
        teleports = false;
        monster = false;
        finalroom = false;
    }
    /**
	 * Get the description of the room.
	 * 
	 * @return description specific description string of the room
	 */
    public String getDescription() {
    	return description;
    }
    
    /**
     * Define an exit of the room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
	 * Lock the room so that the player needs a key to open it.
	 * 
	 */
    public void lockRoom() {
    	locked = true;
    }
    /**
	 * Unlock the room so that the player can enter it without a key.
	 * 
	 */
    public void unlockRoom() {
    	locked = false;
    }
    /**
	 * Check if the room is locked.
	 * @return 'true' if the room is locked, 'false' if not.
	 */
    public boolean isLocked() {
    	return locked;
    }
    /**
	 * Make the room to a teleporter room. It teleports the player when he/she enters the room.
	 * 
	 */
    public void makeTeleporterRoom() {
    	teleports = true;
    }
    /**
	 * Check if the room is a teleporter room.
	 * @return 'true' if the room is a teleporter room, 'false' if not.
	 */
    public boolean isTeleporterRoom() {
    	return teleports;
    }
    /**
	 * Create a monster in the room.
	 * 
	 */
    public void putMonster() {
    	monster = true;
    }
    /**
	 * Remove the monster from the room. E.g. called when the player kills the monster of the room.
	 * 
	 */
    public void killMonster() {
    	monster = false;
    }
    /**
	 * Check if the room has a monster.
	 * @return 'true' if there is a monster in the room, 'false' if not
	 */
    public boolean hasMonster() {
    	return monster;
    }
    /**
	 * Set up final room.
	 * 
	 */
    public void makeFinalRoom() {
    	finalroom = true;
    }
    /**
	 * Check if the room is the final room.
	 * @return 'true' if the room is the final room, 'false' if not
	 */
    public boolean isFinalRoom() {
    	return finalroom;
    }
    
    /**
     * Getter for the Non Player Character in the room.
     * 
     * @return the non player character; returns null if there is not a npc in the room.  
     */
    public NonPlayerCharacter getNpc() {
    	return npc;
    }
    /**
     * Get the name of the item which the non player character in the room wants.
     * @return the name of the item which the non player character in the room wants
     */
    public String getWantedNPCItem() {
		return npc.getWantedItem();
	}
   /**
    * Create a non player character in the room
    * @param itemforhint the name of the item which the npc wants 
    */
    public void createNPC(String itemforhint) {
    	 npc = new NonPlayerCharacter(itemforhint);
    }
    
    /**
	 * Add items to the item list of the room.
	 *@param item the item to add to the list
	 */

	public void addItem(Item ...items) {
		for (Item item : items) {
			itemList.addItem(item);
		}
	}
	
	/**
	 * Checks if the specified item is in the room.
	 * 
	 * @return boolean whether item is in room or not
	 */

	public boolean containsItem(Item specificItem) {
		
		return itemList.contains(specificItem);
	}

    /**
     * Get a string that contains the names of all items in the room.
     * 
     * @return String with all items currently in the room
     */
    
	public String getItemList() {
		String items = itemList.getItemList();
		return items;
	}
	/**
	 *Remove an item from the room. 
	 *@param item the item to remove
	 *
	 */
	public void removeItem(Item item) {
		itemList.removeItem(item);
}

    /**
     * Return a description of the room, e.g.:
     *     You are in the kitchen.
     *     Exits: north west
     *     These are the items in the room: apple 
     *     
     * @return A long description of this room.
     */
    public String getLongDescription()
    {
        return description + ".\n" + getExitString() + "\n\n" +
        "These are the items in the room:" + "\n" +
        itemList.getItemList();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";

        for(String exit : exits.keySet()) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Get a message from the non player character of the room. 
     * This is what the npc says before he receives the item he/she/it wants. 
     * @return The message given by the NPC of the room. 
     */
    
	public String getNpcMessage() {
		if (npc == null) {
			return "";
		} else {
			return  npc.getMessage();
		}
	}
    
    /**
	 * Get the hint from the NPC in the room.
	 * 
	 * @param Item that unlocks hint
	 * @return Hint from NPC
	 */
	public String getNpcHint(String item) {
		if (npc != null) {
		return npc.getHint(item);
		} 
		else {
			return ("There is nobody to give you a hint");	
		}
	}
	/**
	 * Get the food which is in the room. Returns null if the room doesn't contain food.
	 * @return the Food object which is in the room 
	 */
	public Food getFood() {
		return itemList.getFood();
	}
	/**
	 * Get the weapon which is in the room. Returns null if the room doesn't contain a weapon.
	 * @return the weapon which is in the room
	 */
	public Weapon getWeapon() {
		return itemList.getWeapon();
	}
	/**
	 * Get the valuable which is in the room. Returns null if the room doesn't contain a valuable.
	 * @return the valuable item which is in the room
	 */
	public Valuable getValuable() {
		return itemList.getValuable();
	}
	/**
	 * Get the accessory which is in the room. Returns null if the room doesn't contain a accessory.
	 * @return the accessory which is in the room
	 */
	public Accessory getAccessory() {
		return itemList.getAccessory();
	}
	
    /**
     * The room is entered another time. Increase the counter of the entries of the room.
     */
    public void addRoomEntry() {
    	roomEntries++;
    }
    /**
	 *Get the number how often the room has been entered so far.
	 *
	 *@return roomEntries Number of room entries
	 */
    public int getRoomEntries() {
    	return roomEntries;
    }
    
}

