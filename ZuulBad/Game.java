package ZuulBad;

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
 * @author Michael KÃ¶lling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
	private Parser parser;
	private Room currentRoom;
	private Player player;
	private Printer printer;

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
		createRooms();
		player = new Player();
		setTime();

	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		Room outside, theater, pub, lab, office, basement;

		// create the rooms
		outside = Room.OUTSIDE;
		theater = Room.THEATER;
		pub = Room.PUB;
		lab = Room.LAB;
		office = Room.OFFICE;
		basement = Room.BASEMENT;

		// initialize room exits
		outside.setExit("east", theater);
		outside.setExit("south", lab);
		outside.setExit("west", pub);

		theater.setExit("west", outside);

		pub.setExit("east", outside);
		pub.setExit("down", basement);

		lab.setExit("north", outside);
		lab.setExit("east", office);

		office.setExit("west", lab);

		basement.setExit("up", pub);

		// initialize NPC
		outside.createNPC("old book");
		theater.createNPC("nice smelling candle");
		pub.createNPC("sock");
		lab.createNPC("phone");
		office.createNPC("dead plant");
		basement.createNPC("baseball cap");

		// add items to rooms
		outside.addItem(Food.STARFRUIT, Valuable.KEY);
		theater.addItem(Food.BANANA, Food.APPLE, Weapon.NAIL, Weapon.SWORD);
		lab.addItem(Food.BANANA, Weapon.TOOTHPICK);


		// set up Monsters and locked status
		office.lockRoom();

		currentRoom = outside; // start game outside
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
				goRoom(command);
				return;
			}
			System.out.println("The " + nextRoom.toString().toLowerCase() + " is locked!");

		} else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.getLongDescription());
			time--;
			player.getHungry();
			player.increaseLifeBar();
			printer.printRemainingTime(time);
			
		}
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

		if (currentRoom.containsItem(food.toString())) { // if item is in room, eat it
			currentRoom.removeItem(food);
			player.increaseFoodBar();
			return true;
		} else { // if item is not in room, go to inventory
			if (player.backpackContainsItem(food.toString())) {
				player.eat(food);
				return true;
			} else {
				System.out.println("This food is not available at the moment.");
				System.out.println(printer.getFoodHint());
				return false;
			}
		}

	}

	private void hint(Command command) {
		System.out.println(currentRoom.getNpcHint("old book"));
	}

	private boolean store(Command command) {
		String secondWord = command.getSecondWord();
		Object object;
		boolean isStorable = false;

		if (secondWord == null) { // check if user specified item to store
			System.out.println("Store what?");
			return false;
		}

//	else if (Food.isFood(secondWord) || Weapon.isWeapon(secondWord) || Valuable.isValuable(secondWord)) {
		try {
			object = Food.valueOf(secondWord.toUpperCase());
		} catch (IllegalArgumentException e) {

			try {
				object = Weapon.valueOf(secondWord.toUpperCase());
			} catch (IllegalArgumentException f) {

				try {
					object = Valuable.valueOf(secondWord.toUpperCase());
				} catch (IllegalArgumentException g) {
					System.out.println("You can not store that.");
					return false;

				}
			}
		}
		if (currentRoom.containsItem(object.toString())) { // if item is in current room, store it
			currentRoom.removeItem(object);
			player.putItemIntoBackpack(object);
			System.out.println(secondWord.toUpperCase() + " successfully stored");
			return true;

		} else {
			System.out.println("This item is not available at the moment.");
			System.out.println(printer.getItemHint());
			return false;
		}
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
			System.out.println(input + " ist ungültig!");
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
