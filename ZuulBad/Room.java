
package ZuulBad;

import java.util.HashMap;


/**
 * An instance of this class represents one location in the scenery of the game. It is 
 * connected to other rooms via exits. There is an instance of the class Items, which is
 * used to store items contained in the room. 
 * 
 * @author Daniel Birk
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 */

/**
 * Set up the different rooms and give them a description.
 * 
 */
enum Room{
	
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
	 * Create a description for the rooms.
	 * 
	 */
	private String description;
	
	/**
	 * Store exits of the rooms.
	 * 
	 */
    private HashMap<String, Room> exits;        // stores exits of this room.
    /**
	 * Create NPCs.
	 * 
	 */
    private NonPlayerCharacter npc;
    /**
	 * Create the item list.
	 * 
	 */
    private Items itemlist;
    /**
	 * Create room entries.
	 * 
	 */
	private int roomentries;
	/**
	 * For room condition if the room is locked. 
	 * 
	 */
	private boolean locked;
	/**
	 * For room condition if the room is a teleporter room.
	 * 
	 */
	private boolean teleports;
	/**
	 * For room condition if a room contains monsters.
	 * 
	 */
	private boolean monster;
	/**
	 * For room condition if the room is the final room.
	 * 
	 */
	private boolean finalroom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    Room(String description) 
    {

    	this.description = description;
       
        exits = new HashMap<>();
        itemlist = new Items();
        
        roomentries = 0;
        locked = false;
        teleports = false;
        monster = false;
        finalroom = false;
    }
    /**
	 * Assign the description to the room.
	 * 
	 * @return description specific description for the room
	 */
    public String getDescription() {
    	return description;
    }
    
    /**
     * Define an exit from this room.
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
	 * Unlock the room so that the player can enter it.
	 * 
	 */
    public void unlockRoom() {
    	locked = false;
    }
    /**
	 * Check if the room is locked.
	 * 
	 */
    public boolean isLocked() {
    	return locked;
    }
    /**
	 * Set up the teleporter room.
	 * 
	 */
    public void makeTeleporterRoom() {
    	teleports = true;
    }
    /**
	 * Check if the room is a teleporter room.
	 * 
	 */
    public boolean isTeleporterRoom() {
    	return teleports;
    }
    /**
	 * Set up a monster in the room.
	 * 
	 */
    public void putMonster() {
    	monster = true;
    }
    /**
	 * Remove the monster from the room after killing it.
	 * 
	 */
    public void killMonster() {
    	monster = false;
    }
    /**
	 * Check if the room has a monster in it.
	 * 
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
	 * 
	 */
    public boolean isFinalRoom() {
    	return finalroom;
    }
    
    /**
     * Creates a Non Player Character in the room
     * 
     * @param itemforhint The item that the NPC wants in return for a hint
     */
    public NonPlayerCharacter getNpc() {
    	return npc;
    }
    /**
	 *
	 *
	 */
    public String getWantedNPCItem() {
		return npc.getWantedItem();
	}
    /**
	 *
	 *
	 */
    public void createNPC(String itemforhint) {
    	 npc = new NonPlayerCharacter(itemforhint);
    }
    
    /**
	 * Add items to the item list.
	 *
	 */

	public void addItem(Item ...items) {
		for (Item item : items) {
			itemlist.addItem(item);
		}
	}
	/**
	 * 
	 *
	 */
	public void addItem(String item) {
			itemlist.addItem(item);
	}
	
	/**
	 * Checks if the specified item is in the room.
	 * 
	 * @return boolean whether item is in room or not
	 */

	public boolean containsItem(Item specificitem) {
		
		return itemlist.contains(specificitem);
	}
	/**
	 * Checks if the specified item is in the room.
	 * 
	 * @return boolean whether item is in room or not
	 */

	public boolean containsItem(String specificitem) {
		return itemlist.contains(specificitem);
	}
	/**
	 * Checks if the specified food is in the room.
	 * 
	 * @return boolean whether food is in room or not
	 */

	public boolean containsFood(String specificFood) {
		return itemlist.containsFood(specificFood);
	}

    /**
     * Converts the list with items into a String.
     * 
     * @return String with all items currently in the room
     */
    
	public String getItemList() {
		String items = itemlist.getItemList();
		return items;
	}
	/**
	 *
	 *
	 */
	public void removeItem(Item item) {
		itemlist.removeItem(item);
}
	/**
	 *
	 *
	 */
	public void removeItem(String item) {
		itemlist.removeItem(item);
	}

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return description + ".\n" + getExitString() + "\n\n" +
        "These are the items in the room:" + "\n" +
        itemlist.getItemList();
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
     * Return a message at first room entry. If the room has been entered already,
     * return an according message.
     * 
     * @return Message from NPC
     */
    
	public String getNpcMessage() {
		if (npc == null) {
			return "";
		} else {
			return  npc.getMessage();
		}
	}
    
    /**
	 * Gets hint from NPC.
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
	 *
	 *
	 */
	public Food getFood() {
		return itemlist.getFood();
	}
	/**
	 *
	 *
	 */
	public Weapon getWeapon() {
		return itemlist.getWeapon();
	}
	/**
	 *
	 *
	 */
	public Valuable getValuable() {
		return itemlist.getValuable();
	}
	/**
	 *
	 *
	 */
	public Accessory getAccessory() {
		return itemlist.getAccessory();
	}
	

    /**
     * Counts times the room has been entered.
     */
    public void addRoomEntry() {
    	roomentries++;
    }
    /**
	 *Give the number of room entries.
	 *
	 *@return roomentries Number of room entries
	 */
    public int getRoomEntries() {
    	return roomentries;
    }
    
}

