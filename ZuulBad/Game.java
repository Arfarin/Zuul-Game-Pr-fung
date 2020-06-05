package ZuulBad;

import java.util.Random;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

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

public class Game extends VBox {
	
	// all the fun graphic stuff
	@FXML
	Label roomlabel;
	@FXML
	TextArea backpacklabel;
	@FXML
	TextArea npcTextArea;
	@FXML
	Rectangle lifebarRectangle;
	@FXML
	Rectangle foodbarRectangle;
	@FXML
	Rectangle timeRectangle;

//	private SimpleStringProperty roomproperty;
//	public StringProperty roomProperty() {
//		return roomproperty;
//	}
	
	@FXML
	BorderPane instructionDisplay;
	@FXML
	SplitPane mainGameDisplay;
	@FXML
	BorderPane levelSelectionDisplay;
	@FXML
	StackPane welcomeDisplay;
	@FXML
	StackPane winnerDisplay;
	@FXML
	StackPane looserDisplay;
	@FXML
	TextArea informationTextArea;
	
	@FXML
	Button play;
	
	@FXML
	private void handleStart(ActionEvent ActionEvent){
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(true);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
		play();
	}
	@FXML
	private void handleReadInstructions(ActionEvent ActionEvent){
		instructionDisplay.setVisible(true);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
		
	}
	@FXML
	private void handleGoOnPlaying(ActionEvent ActionEvent) {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(true);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
	}
	
	@FXML
	Button easy;
	@FXML
	Button medium;
	@FXML
	Button heavy;
	
	@FXML
	private void chooseEasy(ActionEvent ActionEvent) {
		difficultyLevel = Level.EASY;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
	}
	@FXML
	private void chooseMedium(ActionEvent ActionEvent) {
		difficultyLevel = Level.MEDIUM;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
	}
	@FXML
	private void chooseHeavy(ActionEvent ActionEvent) {
		difficultyLevel = Level.HEAVY;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
	}
	
	@FXML
	MenuItem helpme;
	@FXML
	MenuItem quit;

	@FXML
	private void handleClickHelp(ActionEvent ActionEvent) {
		instructionDisplay.setVisible(true);
		mainGameDisplay.setVisible(false);
	}
	@FXML
	private void handleQuit(ActionEvent ActionEvent) {
		mainGameDisplay.setVisible(false);
		looserDisplay.setVisible(true);
	}
	
	@FXML
	Button closeButton;
	
	@FXML
	private void handleClose(ActionEvent ActionEvent) {
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	Button north;
	@FXML
	Button south;
	@FXML
	Button east;
	@FXML
	Button west;
	@FXML
	Button up;
	@FXML
	Button down;
	
	@FXML
	private void handleGoNorth(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "north", null);
		goRoom(command);
	}
	@FXML
	private void handleGoSouth(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "south", null);
		goRoom(command);
	}
	@FXML
	private void handleGoEast(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "east", null);
		goRoom(command);
	}
	@FXML
	private void handleGoWest(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "west", null);
		goRoom(command);
	}
	@FXML
	private void handleGoUp(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "up", null);
		goRoom(command);
	}
	@FXML
	private void handleGoDown(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.GO, "down", null);
		goRoom(command);
	}
	
	@FXML
	TextField backpackTextFieldToType;
	@FXML
	Button eatFromBackpackButton;
	@FXML
	Button dropButton;
	
	@FXML
	private void handleClickEat(ActionEvent ActionEvent) {
		eatFromBackpack(backpackTextFieldToType.getText());
		
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleClickDrop(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.DROP, backpackTextFieldToType.getText(), null);
		drop(command);
		
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	
	@FXML
	Button hintButton;
	@FXML
	private void handleClickHint(ActionEvent ActionEvent) {
		Command command = new Command(CommandWords.HINT, null, null);
		String npchint = hint(command);
		npcTextArea.setText(npchint);
	}

	@FXML
	Label foodLabel;
	@FXML
	Button eatButton;
	@FXML
	Button storeFoodButton;
	@FXML
	Label weaponLabel;
	@FXML
	Button storeWeaponButton;
	@FXML
	Label valuableLabel;
	@FXML
	Button storeValuableButton;
	
	@FXML
	private void handleEatFoodClick(ActionEvent ActionEvent) {
		if (foodLabel.getText() != "") {
			eatFromRoom(foodLabel.getText());
			foodLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleStoreFoodClick(ActionEvent ActionEvent) {
		if (foodLabel.getText() != "") {
			Command command = new Command(CommandWords.STORE, foodLabel.getText(), null);
			store(command);
			foodLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleStoreWeaponClick(ActionEvent ActionEvent) {
		if (weaponLabel.getText() != "") {
			Command command = new Command(CommandWords.STORE, weaponLabel.getText(), null);
			store(command);
			weaponLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleStoreValuableClick(ActionEvent ActionEvent) {
		if (valuableLabel.getText() != "") {
			Command command = new Command(CommandWords.STORE, valuableLabel.getText(), null);
			store(command);
			valuableLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	
	
	private Parser parser;
	private Room currentRoom;
	private Environment environment;
	private Player player;
	private Printer printer;
	private Random random;

	private static Level difficultyLevel = Level.EASY;

	/**
	 * how much time the game should last. Measured by the number of how often the
	 * player moves from one room to another
	 */
	private int time;
	
	private SimpleIntegerProperty timeproperty;

	/**
	 * Create the game. Select a Level of Difficulty, initialize the rooms with
	 * their content and set the time-limit.
	 */
	public Game() {
		parser = new Parser();
		printer = new Printer();
//		chooseLevelOfDifficulty();
		player = new Player();
		environment = new Environment();

		currentRoom = environment.getFirstRoom(); // start game outside
		time = Level.setValue(30, -5);

		timeproperty = new SimpleIntegerProperty(time);
	}
	
	

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
//		roomproperty = new SimpleStringProperty();
//		roomproperty.setValue(currentRoom.toString());
//		
		player.lifeBarProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue <? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxlife = player.getMaxLife();
				double length = (player.lifeBarProperty().doubleValue() / maxlife) * maxwidth;
				lifebarRectangle.setWidth(length);
			}
		});
		
		player.foodBarProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue <? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxfood= player.getMaxFood();
				double length = (player.foodBarProperty().doubleValue() / maxfood) * maxwidth;
				foodbarRectangle.setWidth(length);
			}
		});
		
		this.timeProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue <? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxtime = Level.setValue(30, -5);
				double length = (timeProperty().doubleValue() / maxtime) * maxwidth;
				timeRectangle.setWidth(length);
			}
		});
		
	}
	

	public IntegerProperty timeProperty() {
		return timeproperty;
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
			informationTextArea.setText("There is no door!");
			return;

		} else if (nextRoom.isLocked()) {
			if (player.backpackContainsItem(key)) {
				nextRoom.unlockRoom();
				player.removeItemFromBackpack(key);
				informationTextArea.setText(nextRoom + " was unlocked.");
				switchRoom(nextRoom);
			} else {
				informationTextArea.setText("The " + nextRoom.toString().toLowerCase() + " is locked!");
				return;
			}

		} else if (nextRoom.isTeleporterRoom()) {
			informationTextArea.setText("You were randomly teleported." + "\n");
			switchRoom( getRandomRoom());

		} else if (nextRoom.hasMonster()) {
			if (killedMonster()) {
				nextRoom.killMonster();
				switchRoom(nextRoom);
			} else {
				return;
			}
		} else if (nextRoom.isFinalRoom()) {
			rescuedPrincess();
			return;

		} else {
			switchRoom(nextRoom);
		}
		
		if (timeOver(time) || player.starvedToDeath() == true || player.beaten() == true) {
			mainGameDisplay.setVisible(false);
			looserDisplay.setVisible(true);
			System.out.println("Game is over.");
		
		}

	}
	
	private void switchRoom(Room nextRoom) {
		currentRoom = nextRoom;
		
		System.out.println(currentRoom.getLongDescription());
		time--;
		player.getHungry();
		player.increaseLifeBar();
		
		
		roomlabel.setText(currentRoom.toString());
		backpacklabel.setText(player.getBackpackContent());
		npcTextArea.setText(currentRoom.getNpcMessage());
		timeproperty.setValue(time);
		
		setItemLabels();
		System.out.println(player.getLifeBar());
	}
	
	private void setItemLabels() {
		if (currentRoom.getFood() != null) {
			foodLabel.setText(currentRoom.getFood().getName());
		} else {
			foodLabel.setText("");
		}
		if (currentRoom.getWeapon() != null) {
			weaponLabel.setText(currentRoom.getWeapon().getName());
		} else {
			weaponLabel.setText("");
		}
		if (currentRoom.getValuable() != null) {
			valuableLabel.setText(currentRoom.getValuable().getName());
		} else {
			valuableLabel.setText("");
		}

	}
	

	private boolean killedMonster() {

		if (player.hasWeapon()) {
			player.removeAWeaponFromBackpack();
			informationTextArea.setText("You killed the monster in the room.\n");
			
			backpacklabel.setText(player.getBackpackContent());
			return true;
		}
		int damage = Level.setValue(1, 1);
		player.reduceLifeBar(damage);
		informationTextArea.setText("The Monster hurt you. You have to flee back to the previous room.\n");
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
	
	public void eatFromRoom(String foodstring) {
		Food food;
		Item fooditem;

		// check if this food exists in the game and store food object in variable
		food = Environment.getFood(foodstring);
		fooditem = Environment.getFood(foodstring);
		
		if (foodstring.matches("magic muffin")) { // check if user wants to eat a magic muffin
			informationTextArea.setText(player.getPowerFromMuffin());
			}
		currentRoom.removeItem(fooditem);
		player.eatFood(food);
	}

	public void eatFromBackpack(String foodstring) {
		Food food;
		Item item;

		// check if this food exists in the game and store food object in variable
		food = Environment.getFood(foodstring);
		item = environment.getItem(foodstring);
		
		if (food != null) {

			if (player.backpackContainsItem(item)) { // if item is not in room, go to inventory
				player.eatFood(food);
				player.removeItemFromBackpack(item);

			} else {
				informationTextArea.setText("Sorry, that's not possible.\n"
						+ printer.getFoodHint());

			}
		}
	}

	private String hint(Command command) {

		if (currentRoom.getNpc() == null) {
			return "Here is nobody to talk with.";

		} else {
			System.out.println();
			Valuable valuable = Environment.getValuable(currentRoom.getWantedNPCItem());

//			String response = currentRoom.getNpcHint(specificValuable);
			if (player.backpackContainsItem(valuable)) {

				return currentRoom.getNpcHint(valuable.toString());
			} else {
				return "Please find " + currentRoom.getWantedNPCItem() +  " for me first";
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
		String secondWord = command.getSecondWord();
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		if (item == null) {
			System.out.println("Sorry. This is not an item of this game.");
			return false;
		} else if (item instanceof Transportable) {

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
		} else {
			System.out.println("This item is not transportable.");
			return false;
		}
	}

	public boolean drop(Command command) {
		Item item;

		String secondWord = command.getSecondWord();

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		if (item == null) {
			informationTextArea.setText("Sorry. This is not an item of this game.");
			return false;
		}

		if (player.backpackContainsItem(item)) {
			player.removeItemFromBackpack(item);
			currentRoom.addItem(item);
			informationTextArea.setText("You have dropped " + command.getSecondWord());
			return true;
		} else {
			informationTextArea.setText("You cannot drop that. Your backpack doesn't contain it.");
			return false;
		}
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

		int randomnumber = random.nextInt(Room.values().length - 1);
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