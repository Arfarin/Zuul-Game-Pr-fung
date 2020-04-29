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
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
	private Parser parser;
	private Room currentRoom;
	private Player player;

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		createRooms();
		parser = new Parser();
		player = new Player();

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

		pub.setExit("east", outside);
		pub.setExit("down", basement);
		pub.createNPC("sock");

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
		getUsersBackpackSettings();
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
			System.out.println();
			System.out.println(currentRoom.returnNpcMessage());
			currentRoom.addRoomEntry();
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

	public void getUsersBackpackSettings() {

		boolean answer;
		answer = false;
		int limitWeight;

		System.out.println();
		System.out.println(
				"Please choose a backpack: Type in 10, 15 or 20 to get a backpack with the maximum portable weight of 10, 15 or 20 kilogram during this game.");
		while (answer == false) {
			String input = parser.getUserInput().trim();
			if (input.equals("10") || input.equals("15") || input.equals("20")) {
				limitWeight = Integer.parseInt(input);
			} else {
				limitWeight = 0;
			}

			if (limitWeight == 10 || limitWeight == 15 || limitWeight == 20) {
				player.chooseBackpack(limitWeight);
				System.out.println("All right. The maximum portable weight of your backpack is set to " + limitWeight
						+ " kilogram.");
				System.out.println();
				answer = true;
			} else {
				System.out.println(
						"You have to choose between 10, 15 and 20 kilogram. Please type in one of these numbers.");
			}
		}
	}

	// getUsersTimeLimitSettings()

	// getUsersSettingsRateOfDamageCausedByMonsters

}
