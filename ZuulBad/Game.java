package ZuulBad;

import java.util.Scanner;

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
	private static Level difficultyLevel;
	
	/**
	 * how much time the game should last
	 */
	private int time;

	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		parser = new Parser();
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
		outside = new Room("outside the main entrance of the university");
		theater = new Room("in a lecture theater");
		pub = new Room("in the campus pub");
		lab = new Room("in a computing lab");
		office = new Room("in the computing admin office");
		basement = new Room("down in the spooky basement");

		// initialise room exits
		outside.setExit("east", theater);
		outside.setExit("south", lab);
		outside.setExit("west", pub);
		outside.createNPC("old book");

		theater.setExit("west", outside);
		theater.createNPC("nice smelling candle");
		theater.createFoods("apple", "starfruit");

		pub.setExit("east", outside);
		pub.setExit("down", basement);
		pub.createNPC("sock");
		pub.createFoods("apple");

		lab.setExit("north", outside);
		lab.setExit("east", office);
		lab.createNPC("phone");

		office.setExit("west", lab);
		office.createNPC("dead plant");

		basement.setExit("up", pub);
		basement.createNPC("baseball cap");

		currentRoom = outside; // start game outside
		currentRoom.addRoomEntry();

	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();
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
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");

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

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		}

		else if (commandWord.equals("look")) {
			player.lookAround(currentRoom);
		} else if (commandWord.equals("eat")) {
			player.eat();
		}
		wantToQuit = timeOver(time);
		return wantToQuit;
	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		System.out.println(parser.showCommands());
	}

	/**
	 * Try to in to one direction. If there is an exit, enter the new room,
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
		} else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.getLongDescription());
			System.out.println("These are the items in the room:");
			System.out.println(currentRoom.getItemList());
			System.out.println();
			System.out.println(currentRoom.getNpcMessage());
			currentRoom.addRoomEntry();
			time--;
//			System.out.println(checkLevel(time));
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
	
	private void setTime() {
		time = Level.setValue(30, -5);
		System.out.println("You can change room for " +time+ " times. Afterwards the game is over if you had not reached the goal until that time.");
	}
	
	private boolean timeOver (int time){
		if (time<1) {
			System.out.println("Time is over.");
			return true;
		}
		else {
			return false;
		}
	}
	
//	private String checkLevel(int feature) {
//		if (feature <= 5) {
//			System.out.println("Almost over. Status of "+ feature);
//		}
//	}

	public void chooseLevelOfDifficulty() {

		System.out.println();
		System.out.println(
				"Let's see how much you withstand.. You can choose between the following three levels of difficulty: ");
		System.out.println("Level 1: EASY - nice to start with");
		System.out.println("Level 2: MEAN - don't underrate it");
		System.out.println("Level 3: HEAVY - for tough guys and ladies");
		System.out.println("Please type in EASY, MEAN or HEAVY");

		String input = parser.getUserInput().trim().toUpperCase();
		try {
			difficultyLevel = Level.valueOf(input);
			System.out.println("Thank you. Level of difficulty is set to: " + input);
		} 
		catch (IllegalArgumentException e) {
			System.out.println(input + " ist ungültig!");
			System.out.println();
			chooseLevelOfDifficulty();
		}
	}
	
	/**
	 * Getter for the level of difficulty. For the classes player and monster to set the maximum weight of backpack and the damage of one attack 
	 * @return the level of difficulty set by the user at the beginning of the Game
	 */
	public static Level getLevel() {
		return difficultyLevel;
	}
}
