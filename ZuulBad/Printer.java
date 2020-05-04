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
	public void printHelp(Parser parser) {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}
	
	public void printDifficultyChoices() {
		System.out.println();
		System.out.println(
				"Let's see how much you withstand.. You can choose between the following three levels of difficulty: ");
		System.out.println("Level 1: EASY - nice to start with");
		System.out.println("Level 2: MEAN - don't underrate it");
		System.out.println("Level 3: HEAVY - for tough guys and ladies");
		System.out.println("Please type in EASY, MEAN or HEAVY");
	}
	
	public static String weightTooHighError() {
		return "Your backpack is full. You can't put another item into it";
	}

	public static String printFoodHint() {
		return "You can't put food in your backpack. Eat it instead.";
	}
	
	public void printRemainingTime(int time) {
		System.out.println("You can change room for " +time+ " times. Afterwards the game is over if you had not reached the goal until that time.");
	}
}
