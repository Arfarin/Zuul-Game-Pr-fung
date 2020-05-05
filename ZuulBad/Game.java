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
	private Printer printer;
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
		
		Food banana, apple, starfruit;
		Weapon toothpick, bananapeel, glass;
		Valuable book;
		

		// create the rooms
		outside = new Room("outside the main entrance of the university");
		theater = new Room("in a lecture theater");
		pub = new Room("in the campus pub");
		lab = new Room("in a computing lab");
		office = new Room("in the computing admin office");
		basement = new Room("down in the spooky basement");
		
		//create the items
		banana = new Food("banana");
		apple = new Food("apple");
		starfruit = new Food("starfruit");
		
		toothpick = new Weapon("toothpick");
		bananapeel = new Weapon("banana peel");
		glass = new Weapon("piece of glass");
	
		book = new Valuable("old book");
		

		// initialise room exits
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
		
		//initialize NPC
		outside.createNPC("old book");
		theater.createNPC("nice smelling candle");
		pub.createNPC("sock");
		lab.createNPC("phone");
		office.createNPC("dead plant");
		basement.createNPC("baseball cap");
		
		//initialize food
		outside.fillItemList(book);
		theater.fillItemList(apple, toothpick);
		lab.fillItemList(banana, starfruit);
		

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

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printer.printHelp(parser);
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		} else if (commandWord.equals("look")) {
			player.lookAround(currentRoom);
		} else if (commandWord.equals("eat")) {
			eat(command);
		} else if (commandWord.equals("hint")) {
			hint(command);
		}
		
		wantToQuit = timeOver(time);
		
		return wantToQuit;
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
			time--;
			printer.printRemainingTime(time);
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
	
	/**
	 * Eat a food item. The food item is either picked up from the room or retrieved from the
	 * inventory if not available in the room.
	 * @param command
	 */
	
	public void eat(Command command) {
		String secondword = command.getSecondWord();
		Food food = new Food(secondword);
		
		if (food.isFood()) {
		
		if (!command.hasSecondWord()) { // check if user specified item to eat
			System.out.println("Eat what?");
		} else if (currentRoom.containsItem(food)){ // if item in room, eat it
			
			currentRoom.useItem(food);
			player.addFood();
			
		} else { // if item is not in room, go to inventory
			
			// check if item is in inventory
			player.eat();
		}
		} else {
			System.out.println("You cannot eat this.");
		}
	}
	
	private void hint(Command command) {
		System.out.println(currentRoom.getNpcHint("old book"));
	}

	private void setTime() {
		time = Level.setValue(30, -5);
		printer.printRemainingTime(time);
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

		printer.printDifficultyChoices();

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
