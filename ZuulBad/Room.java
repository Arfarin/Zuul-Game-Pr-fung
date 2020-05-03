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
    
    public void createNPC(String itemforhint) {
    	 npc = new NonPlayerCharacter(itemforhint);
    }
    
    public void fillItemList(Items...items) {
    	for (Items individualitem : items) {
    		itemlist.add(individualitem);
    	}
    }

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
	
	public void useItem(String selecteditem) {
		if (itemlist.isEmpty()) {
			System.out.println("There are no items in this room.");
		} else {
			for (Items storeditem : itemlist) {
				String itemname = storeditem.getName();

				if (itemname.equals(selecteditem)) {
					itemlist.remove(storeditem);
				}
			}
		}
	}

	public boolean containsItem(String specificitem) {
		boolean contains = false;
		
		for (Items storeditem : itemlist) {
			String itemname = storeditem.getName();
			
			if (itemname.equals(specificitem)) {
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
