package ZuulBad;

import java.util.Random;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
	private Parser parser;
	private Room currentRoom;
	private Environment environment;
	private Player player;
	private Printer printer;
	private Random random;

	private static Level difficultyLevel;

	/**
	 * how much time the game should last. Measured by the number of how often the
	 * player moves from one room to another
	 */
	private int time;

	/**
	 * Create the game. Select a Level of Difficulty, initialize the rooms with
	 * their content and set the time-limit.
	 */
	public Game() {
		parser = new Parser();
		printer = new Printer();
		chooseLevelOfDifficulty();
		player = new Player();
		environment = new Environment();

		currentRoom = environment.getFirstRoom(); // start game outside
		setTime();
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printer.printWelcome();
		System.out.println(currentRoom.getLongDescription());

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */

	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		CommandWords commandWord = command.getCommandWord();

		switch (commandWord) {
		case HELP:
			printer.printHelp(parser);
			break;
		case GO:
			goRoom(command);
			break;
		case QUIT:
			wantToQuit = quit(command);
			break;
		case LOOK:
			player.lookAround(currentRoom);
			break;
		case EAT:
			eat(command);
			break;
		case HINT:
			hint(command);
			break;
		case STORE:
			store(command);
			break;
		case BAGGAGE:
			System.out.println("Backpack contains: " + player.getBackpackContent() + "\n" + "You can carry another "
					+ player.getBackpacksWeight() + " kilo more.");
			break;
		case DROP:
			drop(command);
			break;
		}

		if (timeOver(time)) {
			wantToQuit = true;
			System.out.println("Time is over.");
		}
		if (player.starvedToDeath() == true) {
			System.out.println("You starved to death. Game over.");
			wantToQuit = true;
		}
		if (player.beaten() == true) {
			System.out.println("You are beaten. Game over.");
			wantToQuit = true;
		}

		return wantToQuit;
	}

	/**
	 * Try to move into one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return;
		}

		String direction = command.getSecondWord();
		Valuable key = Environment.getValuable("key");

		// Try to leave current room.
		Room nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null) {
			System.out.println("There is no door!");
			return;

		} else if (nextRoom.isLocked()) {
			if (player.backpackContainsItem(key)) {
				nextRoom.unlockRoom();
				player.removeItemFromBackpack(key);
				System.out.println(nextRoom + " was unlocked.");
				currentRoom = nextRoom;
			} else {
				System.out.println("The " + nextRoom.toString().toLowerCase() + " is locked!");
				return;
			}

		} else if (nextRoom.isTeleporterRoom()) {
			System.out.println("You were randomly teleported." + "\n");
			currentRoom = getRandomRoom();

		} else if (nextRoom.hasMonster()) {
			if (killedMonster()) {
				nextRoom.killMonster();
				currentRoom = nextRoom;
			}
		} else if (nextRoom.isFinalRoom()) {
			rescuedPrincess();
			return;

		} else {
			currentRoom = nextRoom;
		}

		System.out.println(currentRoom.getLongDescription());
		time--;
		player.getHungry();
		player.increaseLifeBar();
		printer.printRemainingTime(time);
	}

	private boolean killedMonster() {

		if (player.hasWeapon()) {
			player.removeAWeaponFromBackpack();
			System.out.println("You killed the monster in the room.\n");
			return true;
		}
		int damage = Level.setValue(1, 1);
		player.reduceLifeBar(damage);
		System.out.println("The Monster hurt you. You have to flee back to the previous room.\n");
		return false;
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we really
	 * quit the game.
	 * 
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		if (command.hasSecondWord() && command.getSecondWord().trim().toLowerCase().equals("game")) {
			return true; // signal that we want to quit

		} else {// when user doesn't type in "quit game" (e.g. only "quit") we are not sure if
				// he really wants to quit and make a call back

			System.out.println("Quit what?");
			return false;
		}
	}

	/**
	 * Eat a food item. The food item is either picked up from the room or retrieved
	 * from the inventory if not available in the room.
	 * 
	 * @param command
	 */

	public boolean eat(Command command) {
		String secondWord;
		String thirdWord;
		Food food;

		// check if user specified item to eat
		if (command.hasSecondWord()) {
			secondWord = command.getSecondWord().trim().toLowerCase();
		} else {
			System.out.println("Eat what?");
			return false;
		}

		// check if this food exists in the game and store food object in variable
		food = Environment.getFood(secondWord);
		if (food == null) {

			// check if user wants to eat a magic muffin
			if (secondWord.contains("magic") && command.hasThirdWord()) {

				try {
					thirdWord = command.getThirdWord().trim().toLowerCase();
				} catch (NullPointerException e) {
					System.out.println("magic what?");
					return false;
				}
				if (thirdWord.equals("muffin")) {
					return eatMuffin(environment.getMuffin(secondWord + thirdWord));
				}
			} else
				System.out.println("Sorry. This is not an item of this game.");
			return false;
		}

		if (currentRoom.containsFood(secondWord)) { // if item is in room, eat it
			currentRoom.removeItem(food);
			player.eatFood(food);
			return true;
		} else if (player.backpackContainsFood(secondWord)) { // if item is not in room, go to inventory
			player.eatFoodFromBackpack(food);
			return true;

		} else {
			System.out.println("Sorry, that's not possible.");
			System.out.println(printer.getFoodHint());
			return false;
		}

	}

	private boolean eatMuffin(MagicMuffin muffin) {
		if (currentRoom.containsMuffin()) {
			player.eatFood(muffin);
			System.out.println(player.getPowerFromMuffin());
			currentRoom.removeItem(muffin);
			return true;

		} else if (!(currentRoom.containsItem(muffin)) && player.backpackContainsItem(muffin)) {
			player.eatFoodFromBackpack(muffin);
			System.out.println(player.getPowerFromMuffin());
			return true;
		} else {
			return false;
		}
	}

	private void hint(Command command) {

		if (currentRoom.getNpc() == null) {
			System.out.println("Here is nobody to talk with.");

		} else {
			System.out.println("Do you have " + currentRoom.getWantedNPCItem() +  " for me?");
			String specificValuable = parser.getUserInput().trim().toLowerCase();
			Valuable valuable = Environment.getValuable(specificValuable);

			String response = currentRoom.getNpcHint(specificValuable);

			if (player.backpackContainsItem(valuable)) {

				if (response != null) {
					System.out.println(response);
					player.removeItemFromBackpack(valuable);

				} else {
					System.out.println("This is not what I want");
				}
			} else {
				System.out.println("Your backpack doesn't contain '" + specificValuable
						+ "' so you can't deliver that to anyone.");
			}
		}
	}

	/**
	 * Put a portable item lying in the current room into the inventory. The item
	 * can be fetched later when needed. To use the function the user has to type in
	 * the command word "store" an the name of the specified item which should be
	 * stored.
	 * 
	 * @param command the second word of the user's input
	 * @return true when storing was successful
	 */

	private boolean store(Command command) {
		String secondWord;
		Item item;

		if (command.hasSecondWord()) {
			secondWord = command.getSecondWord().trim().toLowerCase();
		} else {
			System.out.println("Eat what?");
			return false;
		}

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		if (item == null) {
			System.out.println("Sorry. This is not a food item of this game.");
			return false;
		}

//		if (secondWord == null) { // check if user specified item to store
//			System.out.println("Store what?");
//			return false;
//		}

		// check if there is free capacity to store the item
		if (player.cantCarryMore(item.getWeight()) == true) {
			System.out.println(printer.weightTooHighError());
			return false;
		} else {

			// if item is in current room, store it
			if (currentRoom.containsItem(item)) {
				currentRoom.removeItem(item);
				player.putItemIntoBackpack(item);
				return true;
			} else {
				System.out.println("This item is not available at the moment.");
				System.out.println(printer.getItemHint());
				return false;
			}
		}
	}

	public boolean drop(Command command) {
		String secondWord;
		Item item;

		if (command.hasSecondWord()) {
			secondWord = command.getSecondWord().trim().toLowerCase();
		} else {
			System.out.println("Drop what?");
			return false;
		}

		item = environment.getItem(secondWord);
		if (item == null) {
			System.out.println("Sorry. This is not a food item of this game.");
			return false;
		}
//		if (secondWord == null) { // check if user specified item to store
//			System.out.println("Drop what?");
//			return;

		if (player.backpackContainsItem(item)) {
			player.removeItemFromBackpack(item);
			currentRoom.addItem(item);
			System.out.println("You have dropped " + command.getSecondWord());
			return true;
		} else {
			System.out.println("You cannot drop that. Your backpack doesn't contain it.");
			return false;
		}
	}

	private final void setTime() {
		time = Level.setValue(30, -5);
		printer.printRemainingTime(time);
	}

	private boolean timeOver(int time) {
		if (time < 1) {
			System.out.println("Time is over.");
			return true;
		} else {
			return false;
		}
	}

	private Room getRandomRoom() {
		random = new Random();

		int randomnumber = random.nextInt(Room.values().length);
		Room randomroom = Room.values()[randomnumber];

		return randomroom;
	}

	private boolean rescuedPrincess() {

		Valuable dragonGlass = Environment.getValuable("dragonglass");
		if (player.backpackContainsItem(dragonGlass)) {

			player.removeItemFromBackpack(dragonGlass);

			System.out.println("You killed the monster in the room.\n"
					+ "Princess: Thanks for saving me. Here's a kiss on the cheek for that.\n"
					+ "I have wanted to be independent for so long, but because of this monster I was stuck here.\n"
					+ "But now I can go to college and become a Data Scientist. Bye!");
			return true;
		}

		int damage = Level.setValue(1, 1) * 2;
		player.reduceLifeBar(damage);
		System.out.println("The Monster hurt you badly. You have to flee back to the previous room.\n");

		return false;
	}

//// Check status of lifeBar, time or foodBar 		
//	private String checkLevel(int feature) {
//		if (feature <= 5) {
//			System.out.println("Almost over. Status of "+ feature);
//		}
//	}

	public final void chooseLevelOfDifficulty() {

		printer.printDifficultyChoices();

		String input = parser.getUserInput().trim().toUpperCase();
		try {
			difficultyLevel = Level.valueOf(input);
			System.out.println("Thank you. Level of difficulty is set to: " + input);
		} catch (IllegalArgumentException e) {
			System.out.println(input + " ist not valid!");
			System.out.println();
			chooseLevelOfDifficulty();
		}
	}

	/**
	 * Getter for the level of difficulty. For the classes player and monster to set
	 * the maximum weight of backpack and the damage of one attack
	 * 
	 * @return the level of difficulty set by the user at the beginning of the Game
	 */
	public static Level getLevel() {
		return difficultyLevel;
	}
}
