package ZuulBad;

public class Printer {
	
	/**
	 * Print out the opening message for the player.
	 */
	public void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");

	}
	
	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */

	
	public void printDifficultyChoices() {
		System.out.println();
		System.out.println(
				"Please choose between the following three levels of difficulty: ");
		System.out.println("Level 1: EASY - nice to start with");
		System.out.println("Level 2: MEDIUM - don't underrate it");
		System.out.println("Level 3: HEAVY - for tough guys and ladies");
		System.out.println("Please type in EASY, MEDIUM or HEAVY");
	}
	
	public String weightTooHighError() {
		return "Sorry, you can't put that it into your backpack because it would exceed the maximum portable weight you can carry.";
	}

	public static String notForBackpackError() {
		return "You can't put this into your backpack. It is stationary.";
	}
	
	public void printRemainingTime(int time) {
		System.out.println("You can change room for " +time+ " times. Afterwards the game is over if you had not reached the goal until that time.");
	}
	
	public String getFoodHint(){
		return "You can only eat food which lies in the room you are in or which is stored in your backpack.";
	}
	public String eatMagicMuffin() {
	return "You ate the magic muffin. Now you are so strong that you can carry an infinite weight and amount of things in your backpack.";
	}
	
	public String getItemHint() {
		return "You can only store portable things you find in the room you're in.";
	}
	
	public String printItemsForNPCsTip() {
		return "You can only deliver items that are stored in your backpack. You can't deliver food or weapons to any persons.";
	}
}
