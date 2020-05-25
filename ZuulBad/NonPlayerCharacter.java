package ZuulBad;
import java.util.HashMap;
import java.util.Random;

/**
 * This class creates a Non Player Character who can give messages and hints.
 * The messages are used at random and the hints have a given order.
 * @author Katerina Matysova
 *
 */

public class NonPlayerCharacter {

	private static HashMap<String, String> messages;
	private static HashMap<String, String> hints;
	private String wanteditem;
	
	public NonPlayerCharacter(String wanteditem) {
		this.wanteditem = wanteditem;
		messages = new HashMap<>();
		hints = new HashMap<>();

		fillMessages();
		fillHints();
	}
	
	private final void fillMessages() {
		messages.put("glasses", "Old Lady: Hi there, welcome to the castle. I'm looking for my glasses. Have you seen them?");
		messages.put("tiara", "Queen: Oh everything is so aweful... *crying noises*");
		messages.put("spoon", "Hungry Man: Are you the brave hero this castle is looking for?");
		messages.put("tupperware", "Maid: Hello. Do you know where I can store all the leftovers?");
	}
	
	private final void fillHints() {
		hints.put("glasses", "Old Lady: Thank you for finding my glasses. Beware, I saw a strong goblin in the castle but my husband doesn't believe me. He says that I always imagine silly things when I don't wear my glasses.");
		hints.put("tiara", "Queen: Thank you! This belonged to my daughter. She was kidnapped by a strange creature and I don't know where she is...");
		hints.put("spoon", "Hungry Man: Perfect! Now I can finally eat my soup. Don't forget to eat once in a while or you'll pass out.");
		hints.put("tupperware", "Maid: Oh this tupperware is very helpful. Would be good to store the food in the basement under the wine storage but some people say that you cannot return once you are down there...");
	}
	
	
	public String getMessage() {
		String message = messages.get(wanteditem);
		return message;
	}
	
	public boolean getHint(String itemname) {
		if (itemname.equals(wanteditem)) {
			System.out.println(hints.get(wanteditem));
			return true; 
		
		} else {
			System.out.println("I don't know what I should do with this item.");
			return false;
		}
	}
}
