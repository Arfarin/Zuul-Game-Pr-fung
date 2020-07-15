package ZuulBad;

import java.util.HashMap;
import java.util.Random;

/**
 * This class creates a Non Player Character who can give messages when player
 * enters a room and hints when player carries specific item
 * 
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 * @author Daniel Birk
 */

public class NonPlayerCharacter {

	/**
	 * The HashMap containing all NPC Messages
	 */
	private static HashMap<String, String> messages;
	/**
	 * The HashMap containing all NPC Hints
	 */
	private static HashMap<String, String> hints;
	/**
	 * The item the NPC needs to give a hint
	 */
	private String wanteditem;

	/**
	 * Constructor for the class NPC. In the constructor a HashMap with messages and
	 * a HashMap with hints are created. The values can be retrieved when the
	 * correct item is handed over.
	 * 
	 * @param wanteditem the item wanted by NPC
	 */
	public NonPlayerCharacter(String wanteditem) {
		this.wanteditem = wanteditem;
		messages = new HashMap<>();
		hints = new HashMap<>();

		fillMessages();
		fillHints();
	}

	/**
	 * This method fills NPC messages into the HashMap
	 */
	private final void fillMessages() {
		messages.put("glasses",
				"Old Lady: Hi there, welcome to the castle. I'm looking for my glasses. Have you seen them?");
		messages.put("tiara", "Queen: Oh everything is so awful... *crying noises*");
		messages.put("spoon", "Hungry Man: Are you the brave hero this castle is looking for?");
		messages.put("tupperware", "Maid: Hello. Do you know where I can store all the leftovers?");
	}

	/**
	 * This method fills NPC hints into the HashMap
	 */
	private final void fillHints() {
		hints.put("glasses",
				"Old Lady: Thank you for finding my glasses. Beware, I saw a strong goblin in the castle but my husband doesn't believe me. He says that I always imagine silly things when I don't wear my glasses.");
		hints.put("tiara",
				"Queen: Thank you! This belonged to my daughter. She was kidnapped by a strange creature and I don't know where she is...");
		hints.put("spoon",
				"Hungry Man: Perfect! Now I can finally eat my soup. Don't forget to eat once in a while or you'll pass out.");
		hints.put("tupperware",
				"Maid: Oh this tupperware is very helpful. Would be good to store the food in the basement under the wine storage but some people say that you cannot return once you are down there...");
	}

	/**
	 * This method checks, which item the NPC wants
	 * 
	 * @return wanted item
	 */
	public String getWantedItem() {
		return wanteditem;
	}

	/**
	 * This method returns a message specific to the NPC
	 * 
	 * @return message
	 */
	public String getMessage() {
		String message = messages.get(wanteditem);
		return message;
	}

	/**
	 * This method returns the NPC hint, if the given item is correct
	 * 
	 * @param itemname specific item
	 * @return hint
	 */
	public String getHint(String itemname) {
		if (itemname.equals(wanteditem)) {
			return hints.get(wanteditem);
		} else {
			return ("You do not have the item I want.");
		}
	}
}
