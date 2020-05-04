package ZuulBad;

import java.util.Set;
import java.util.ArrayList;
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

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    
    private NonPlayerCharacter npc;
    private ArrayList<Items> itemlist= new ArrayList<>();
	
	private int roomentries;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        itemlist = new ArrayList<>();
        
        roomentries = 0;

    }
    
    /**
     * Creates a Non Player Character in the room
     * 
     * @param itemforhint The item that the NPC wants in return for a hint
     */
    
    public void createNPC(String itemforhint) {
    	 npc = new NonPlayerCharacter(itemforhint);
    }
    
    /**
     * Creates an item list for the room
     * 
     * @param items List of items to be added to the room
     */
    
    public void fillItemList(Items...items) {
    	for (Items individualitem : items) {
    		itemlist.add(individualitem);
    	}
    }

    /**
     * Converts the list with items into a String.
     * 
     * @return String with all items currently in the room
     */
    
	public String getItemList() {
		String itemstring;
		if (itemlist.isEmpty()) {
			itemstring = "There are no items in this room.";
		} else {
			StringBuilder itemsinroom = new StringBuilder();

			for (Items item : itemlist) {
				itemsinroom.append(item.getName() + " ");
			}
			itemstring = itemsinroom.toString();
		}
		return itemstring;
	}
	

	/**
	 * Causes a deletion of the item from the room's item list.
	 * 
	 * @param selecteditem Item to be used
	 * @return boolean whether item was removed
	 */
	
	public boolean useItem(Items specificitem) {

		for (Items item : itemlist) { // for all items in room check if name is the same as the user input
			if (specificitem.getName().equals(item.getName())) { 
				itemlist.remove(item);
				System.out.println(specificitem.getName() + " was used.");
				return true; 
				// this return statement has the function of terminating the loop when value is found
			}
		}
		return false;
	}
	
	
	/**
	 * Checks if the specified item is in the room.
	 * 
	 * @param specificitem Item to be checked
	 * @return boolean whether item is in room or not
	 */

	public boolean containsItem(Items specificitem) {
		
		boolean contains = false;
		
		for (Items item : itemlist) { // for all items in room check if name is the same as the user input
			if (specificitem.getName().equals(item.getName())) { 
				contains = true;
			}
		}
		return contains;
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
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
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
		String message;
		if (roomentries == 0) {
			message = npc.getMessage();
		} else {
			message = "You have already entered this room.";
		}
		addRoomEntry();
		return message;
	}
    
    /**
	 * Gets hint from NPC.
	 * 
	 * @param Item that unlocks hint
	 * @return Hint from NPC
	 */
	public String getNpcHint(String item) {
		return npc.getHint(item);
	}

    /**
     * Counts times the room has been entered.
     */
    public void addRoomEntry() {
    	roomentries++;
    }
    
}
