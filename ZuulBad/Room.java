package ZuulBad;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

enum Room{
	
	OUTSIDE("outside the main entrance of the university"),
	THEATER("in a lecture theater"),
	PUB("in the campus pub"),
	LAB("in a computing lab"),
	OFFICE("in the computing admin office"),
	BASEMENT("down in the spooky basement");
	
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    
    private NonPlayerCharacter npc;
    private Items itemlist;
	
	private int roomentries;
	private boolean locked;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        itemlist = new Items();
        
        roomentries = 0;
        locked = false;
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
    
    public void lockRoom() {
    	locked = true;
    }
    
    public void unlockRoom() {
    	locked = false;
    }
    
    public boolean isLocked() {
    	return locked;
    }
    
    /**
     * Creates a Non Player Character in the room
     * 
     * @param itemforhint The item that the NPC wants in return for a hint
     */
    
    public void createNPC(String itemforhint) {
    	 npc = new NonPlayerCharacter(itemforhint);
    }
    
	public void addFood(Food... fooditems) {
		for (Food item : fooditems) {
			itemlist.addFood(item);
		}
	}
	
	public void addWeapons(Weapon...weapons) {
		for (Weapon weapon : weapons) {
			itemlist.addWeapon(weapon);
		}
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
     * Converts the list with items into a String.
     * 
     * @return String with all items currently in the room
     */
    
	public String getItemList() {
		String items = itemlist.getItemList();
		return items;
	}
	
	
	public void eat(Food food) {
		itemlist.removeFood(food);
	}
	
	
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n\n" +
        "These are the items in the room:" + "\n" +
		getItemList() + "\n\n"+ getNpcMessage();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
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
     * @return Message from NPC
     */
    
	public String getNpcMessage() {
		
		if (npc == null) {
			return "";
		} else if (roomentries > 0) {
			return "You have already entered this room.";
		} else {
			addRoomEntry();
			return npc.getMessage();
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
		} else {
			return "There is nobody to give you a hint";
		}
	}
	

    /**
     * Counts times the room has been entered.
     */
    public void addRoomEntry() {
    	roomentries++;
    }
    
}
