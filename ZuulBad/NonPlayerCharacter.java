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
		messages.put("book", "Welcome to the castle!");
		messages.put("candle", "Ugh, why do they always let me deal with the lost people?");
		messages.put("sock", "Hey there! Make yourself feel at home.");
		messages.put("phone", "Omg be careful, you almost stepped on my dog!");
		messages.put("plant", "empty string");
		messages.put("cap", "empty string");
	}
	
	private final void fillHints() {
		hints.put("book", "Oh thanks for finding this for me. Make sure you eat something every once in a while, hunger is deadly!");
		hints.put("candle", "Oh let me tell you, everyone was stunned by the princess's beauty. But then she got kidnapped by a terrible monster.");
		hints.put("sock", "The monsters might seem scary, but if you find something useful, they won't bother you.");
		hints.put("phone", "empty string");
		hints.put("plant", "empty string");
		hints.put("cap", "empty string");
	}
	
	
	public String getMessage() {

		String message = messages.get(wanteditem);
		return message;
	}
	
	public String getHint(String itemname) {
		if (itemname.equals(wanteditem)) {
			return hints.get(wanteditem);
		} else {
			return "This is not the item I wanted.";
		}
	}
}
