import java.util.ArrayList;
import java.util.Random;

/**
 * This class creates a Non Player Character who can give messages and hints.
 * The messages are used at random and the hints have a given order.
 * @author Katerina Matysova
 *
 */

public class NonPlayerCharacter {

	private static ArrayList<String> messages;
	private static ArrayList<String> hints;
	
	private Random random;
	
	public NonPlayerCharacter() {
		messages = new ArrayList<>();
		hints = new ArrayList<>();
		random = new Random();
		fillMessages();
		fillHints();
	}
	
	private void fillMessages() {
		messages.add("My name is Heinz, but feel free to call me pickle. Got something for me?");
		messages.add("Ugh, why do they always let me deal with the lost people?");
		messages.add("Hey there! Make yourself feel at home.");
		messages.add("Omg be careful, you almost stepped on my dog!");
	}
	
	private void fillHints() {
		hints.add("Oh thanks for finding this for me. Make sure you eat something every once in a while, hunger is deadly!");
		hints.add("Oh let me tell you, everyone was stunned by the princess's beauty. But then she got kidnapped by a terrible monster.");
		hints.add("The monsters might seem scary, but if you find something useful, they won't bother you.");
	}
	
	public String returnMessage() {
		String message;
		
		if (messages.isEmpty()) { // to prevent NullPointerException
			message = null;
		} else {
			int randomnumber = random.nextInt(messages.size());
			message = messages.get(randomnumber);
			messages.remove(randomnumber);
		}
		
		return message;
	}
	
	public String returnHint() {
		String hint;
		
		if (hints.isEmpty()) { // to prevent NullPointerException
			hint = "Sorry, you are on your own";
		} else {
			hint = hints.get(0);
			hints.remove(0);
		}
		
		return hint;
	}
}
