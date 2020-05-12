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
	 * how much time the game should last
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
		}

		if (timeOver(time)) {
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

		// Try to leave current room.
		Room nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null) {
			System.out.println("There is no door!");

		} else if (nextRoom.isLocked()) {
			if (player.backpackContainsItem("key")) {
				nextRoom.unlockRoom();
				player.removeItemFromBackpack(Valuable.KEY);
				System.out.println(nextRoom + " was unlocked.");
				goRoom(command);
				return;
			}
			System.out.println("The " + nextRoom.toString().toLowerCase() + " is locked!");

		} else if (nextRoom.isTeleporterRoom()) {
			System.out.println("You were randomly teleported." + "\n");
			currentRoom = getRandomRoom();

		} else if (nextRoom.hasMonster()) {
			if (killedMonster()) {
				currentRoom = nextRoom;
			}

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

		for (Weapon weapon : Weapon.values()) {
			if (player.backpackContainsItem(weapon.toString())) {
				currentRoom.killMonster();
				player.removeItemFromBackpack(weapon);
				System.out.println("You killed the monster in the room.\n");
				return true;
			}
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
		if (command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		} else {
			return true; // signal that we want to quit
		}
	}

	/**
	 * Eat a food item. The food item is either picked up from the room or retrieved
	 * from the inventory if not available in the room.
	 * 
	 * @param command
	 */

	public boolean eat(Command command) {
		String secondword = command.getSecondWord();
		Food food;

		if (secondword == null) { // check if user specified item to eat
			System.out.println("Eat what?");
			return false;
		}

		try {
			food = Food.valueOf(secondword.toUpperCase());

		} catch (IllegalArgumentException e) { // check if this String can be a food

			System.out.println("You can not eat '" + secondword + "'.");
			return false;
		}

		if (eatMuffin(food) == true) {
			return true;
		} else {

			if (currentRoom.containsItem(food.toString())) { // if item is in room, eat it
				currentRoom.removeItem(food);
				player.increaseFoodBar();
				return true;
			} else { // if item is not in room, go to inventory
				if (player.backpackContainsItem(food.toString())) {
					player.eatFoodFromBackpack(food);
					return true;
				} else {
					System.out.println("This food is not available at the moment.");
					System.out.println(printer.getFoodHint());
					return false;
				}
			}

		}
	}

	private boolean eatMuffin(Food food) {
		if ((food.isMuffin()) && (currentRoom.containsItem(food.toString()))) {
			System.out.println(player.eatMuffin());
			currentRoom.removeItem(food);
			return true;

		} else if (food.isMuffin() && !((currentRoom.containsItem(food.toString())))
				&& player.backpackContainsItem(food.toString())) {
			System.out.println(player.eatMuffin());
			return true;
		} else {
			return false;
		}
	}

	private void hint(Command command) {

		System.out.println("Do you have the item I want?");
		String item = parser.getUserInput().trim().toLowerCase();

		String response = currentRoom.getNpcHint(item);
		Valuable wantedItem;

		try {
			wantedItem = Valuable.valueOf(item.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println("Sorry, '" + item + "' is not a valid item.");
			return;
		}
		if (player.backpackContainsItem(item.toUpperCase())) {

			if (response != null) {
				System.out.println(response);
				player.removeItemFromBackpack(wantedItem);

			} else {
				System.out.println("This is not what I want");
			}
		} else {
			System.out.println("Your backpack doesn't contain '" + item + "' so you can't deliver that to anyone.");
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
		String secondWord = command.getSecondWord();
		Food food = null;
		Weapon weapon = null;
		Valuable valuable = null;

		if (secondWord == null) { // check if user specified item to store
			System.out.println("Store what?");
			return false;
		}

//	else if (Food.isFood(secondWord) || Weapon.isWeapon(secondWord) || Valuable.isValuable(secondWord)) {
		try {
			food = Food.valueOf(secondWord.toUpperCase());

		} catch (IllegalArgumentException e) {

			try {
				weapon = Weapon.valueOf(secondWord.toUpperCase());
			} catch (IllegalArgumentException f) {

				try {
					valuable = Valuable.valueOf(secondWord.toUpperCase());
				} catch (IllegalArgumentException g) {
					System.out.println("You can not store that.");
					return false;

				}
			}
		}
		try {
			if (currentRoom.containsItem(food.toString())) { // if item is in current room, store it
				currentRoom.removeItem(food);
				player.putItemIntoBackpack(food);
				System.out.println(secondWord.toUpperCase() + " successfully stored");
				return true;
			}
		} catch (NullPointerException h) {
		}

		try {
			if (currentRoom.containsItem(weapon.toString())) {
				currentRoom.removeItem(weapon);
				player.putItemIntoBackpack(weapon);
				System.out.println(secondWord.toUpperCase() + " successfully stored");
				return true;
			}
		} catch (NullPointerException h) {
		}

		try {
			if (currentRoom.containsItem(valuable.toString())) {
				currentRoom.removeItem(valuable);
				player.putItemIntoBackpack(valuable);
				System.out.println(secondWord.toUpperCase() + " successfully stored");
				return true;
			}
		} catch (NullPointerException h) {
		}

		System.out.println("This item is not available at the moment.");
		System.out.println(printer.getItemHint());
		return false;

	}

	private void setTime() {
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

//// Check status of lifeBar, time or foodBar 		
//	private String checkLevel(int feature) {
//		if (feature <= 5) {
//			System.out.println("Almost over. Status of "+ feature);
//		}
//	}

	public void chooseLevelOfDifficulty() {

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
