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
import javafx.stage.Popup;

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
	
	@FXML
	TextArea popupTextArea;
	@FXML
	Button popupButton;
	
	@FXML
	private void handlePopupClose() {
		popupPane.setVisible(false);
	}
	@FXML
	private void handleRoomPopup() {
		popupPane.setVisible(true);
		popupTextArea.setText(currentRoom.getLongDescription());
	}
	
	@FXML
	private void handleFoodPopup() {
		if (currentRoom.getFood() != null) {
			popupPane.setVisible(true);
			popupTextArea.setText(currentRoom.getFood().getDescription());
		}
	}

	@FXML
	private void handleWeaponPopup() {
		if (currentRoom.getWeapon() != null) {
			popupPane.setVisible(true);
			popupTextArea.setText(currentRoom.getWeapon().getDescription());
		}
	}

	@FXML
	private void handleValuablePopup() {
		if (currentRoom.getValuable() != null) {
			popupPane.setVisible(true);
			popupTextArea.setText(currentRoom.getValuable().getDescription());
		}
	}
	@FXML
	private void handleStaticItemPopup() {
		if (currentRoom.getAccessory() != null) {
			popupPane.setVisible(true);
			popupTextArea.setText(currentRoom.getAccessory().getDescription());
		}
	}
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
	Pane popupPane;
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
		play();
		
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
		levelSelectionDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
	}
	@FXML
	private void handleQuit(ActionEvent ActionEvent) {
		mainGameDisplay.setVisible(false);
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(true);
		welcomeDisplay.setVisible(false);
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
	Label backpackWeightLabel;
	
	@FXML
	private void handleClickEat(ActionEvent ActionEvent) {
		eatFromBackpack(backpackTextFieldToType.getText());
		
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleClickDrop(ActionEvent ActionEvent) {
		drop(backpackTextFieldToType.getText());
		
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
	Label staticItemLabel;
	
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
			store(foodLabel.getText());
			foodLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleStoreWeaponClick(ActionEvent ActionEvent) {
		if (weaponLabel.getText() != "") {
			store(weaponLabel.getText());
			weaponLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	@FXML
	private void handleStoreValuableClick(ActionEvent ActionEvent) {
		if (valuableLabel.getText() != "") {
			store(valuableLabel.getText());
			valuableLabel.setText("");
		}
		backpacklabel.setText(player.getBackpackContent());
		setItemLabels();
	}
	
	@FXML
	Rectangle northDoorRectangle;
	@FXML
	Rectangle southDoorRectangle;
	@FXML
	Rectangle eastDoorRectangle;
	@FXML
	Rectangle westDoorRectangle;
	@FXML
	Rectangle upDoorRectangle;
	@FXML
	Rectangle downDoorRectangle;
	@FXML
	Label upDoorLabel;
	@FXML
	Label downDoorLabel;
	
	
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
		environment = new Environment();
		timeproperty = new SimpleIntegerProperty(time);
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		environment.prepareEnvironment();
		player = new Player();
		time = Level.setValue(30, -5);
		
		currentRoom = environment.getFirstRoom();
		setUpRoom();

		// set up all the listeners
		
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
		
		player.backpackWeightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue <? extends Object> observable, Object oldValue, Object newValue) {
				int usedWeight = player.getMaxWeight() - player.getBackpacksWeight();
				backpackWeightLabel.setText("Backpack Weight: " + usedWeight + " / " + player.getMaxWeight());
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
		if (currentRoom.getAccessory() != null) {
			staticItemLabel.setText(currentRoom.getAccessory().getName());
		} else {
			staticItemLabel.setText("");
		}

	}
	
	private void setExitLabels() {
		if (currentRoom.getExit("north") == null) {
			northDoorRectangle.setVisible(false);
		} else {
			northDoorRectangle.setVisible(true);
		}
		if (currentRoom.getExit("south") == null) {
			southDoorRectangle.setVisible(false);
		} else {
			southDoorRectangle.setVisible(true);
		}
		if (currentRoom.getExit("east") == null) {
			eastDoorRectangle.setVisible(false);
		} else {
			eastDoorRectangle.setVisible(true);
		}
		if (currentRoom.getExit("west") == null) {
			westDoorRectangle.setVisible(false);
		} else {
			westDoorRectangle.setVisible(true);
		}
		if (currentRoom.getExit("up") == null) {
			upDoorRectangle.setVisible(false);
			upDoorLabel.setVisible(false);
		} else {
			upDoorRectangle.setVisible(true);
			upDoorLabel.setVisible(true);
		}
		if (currentRoom.getExit("down") == null) {
			downDoorRectangle.setVisible(false);
			downDoorLabel.setVisible(false);
		} else {
			downDoorRectangle.setVisible(true);
			downDoorLabel.setVisible(true);
		}
	}
	
	private void setOtherLabels() {
//	// static item label
//		random = new Random();
//		Accessory[] accessories = new Accessory[environment.getListOfAccessories().size()];
//		environment.getListOfAccessories().toArray(accessories);
//		
//		int i = random.nextInt(accessories.length);
//		staticItemLabel.setText(accessories[i].getName());
			
		// backpack label
		int usedWeight = player.getMaxWeight() - player.getBackpacksWeight();
		backpackWeightLabel.setText("Backpack Weight: " + usedWeight + " / " + player.getMaxWeight());
	}
	
	private void checkVitals() {
		if (timeOver(time) || player.starvedToDeath() || player.beaten()) {
			looseGame();
			System.out.println("Time is over.");
		}
	}
	
	private void setUpRoom() {
		setItemLabels();
		setExitLabels();
		setOtherLabels();
		
		roomlabel.setText(currentRoom.toString());
		backpacklabel.setText(player.getBackpackContent());
		npcTextArea.setText(currentRoom.getNpcMessage()+ "\n");
		
		if (currentRoom.getNpc() != null) {
			npcTextArea.appendText("Can you please get me my " + currentRoom.getWantedNPCItem() + "?");
		}
	}
	
	private void looseGame() {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(true);
		welcomeDisplay.setVisible(false);
	}
	
	private void winGame() {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(true);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
	}

	/**
	 * Try to move into one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {

		String direction = command.getSecondWord();

		// Try to leave current room.
		Room nextRoom = currentRoom.getExit(direction);

		if (nextRoom == null) {
			informationTextArea.setText("There is no door!");

		} else if (nextRoom.isLocked()) {
			tryUnlockRoom(nextRoom);

		} else if (nextRoom.isTeleporterRoom()) {
			informationTextArea.setText("You were randomly teleported." + "\n");
			switchRoom(getRandomRoom());

		} else if (nextRoom.hasMonster()) {
			tryKillMonster(nextRoom);
			
		} else if (nextRoom.isFinalRoom()) {
			rescuePrincess();

		} else {
			switchRoom(nextRoom);
		}
		
		checkVitals();
	}
	
	private void switchRoom(Room nextRoom) {
		currentRoom = nextRoom;
		
		System.out.println(currentRoom.getLongDescription());
		time--;
		player.getHungry();
		player.increaseLifeBar();
		
		timeproperty.setValue(time);
		
		setUpRoom();
		checkVitals();
	}
	
	private void tryUnlockRoom(Room nextRoom) {
		Item key = environment.getItem("key");
		
		if (player.backpackContainsItem(key)) {
			nextRoom.unlockRoom();
			player.removeItemFromBackpack(key);
			informationTextArea.setText(nextRoom + " was unlocked.");
			switchRoom(nextRoom);
		} else {
			informationTextArea.setText("The " + nextRoom.toString().toLowerCase() + " is locked!");
		}
	}

	private void tryKillMonster(Room nextRoom) {

		if (player.hasWeapon()) {
			player.removeAWeaponFromBackpack();
			informationTextArea.setText("You killed the monster in the room.\n");
			
			backpacklabel.setText(player.getBackpackContent());
			nextRoom.killMonster();
			switchRoom(nextRoom);
		} else {
		int damage = Level.setValue(1, 1);
		player.reduceLifeBar(damage);
		informationTextArea.setText("The Monster hurt you. You have to flee back to the previous room.\n");
		}
	}
	
	private void rescuePrincess() {
		Valuable dragonGlass = Environment.getValuable("dragonglass");
		
		if (player.backpackContainsItem(dragonGlass)) {
			winGame();
		} else {
		int damage = Level.setValue(1, 1) * 2;
		player.reduceLifeBar(damage);
		informationTextArea.setText("The Monster hurt you badly. You have to flee back to the previous room.\n");
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
		fooditem = environment.getItem(foodstring);
		
		if (foodstring.matches("magic muffin")) { // check if user wants to eat a magic muffin
			informationTextArea.setText(player.getPowerFromMuffin());
			backpackWeightLabel.setText("Portable weight of backpack is unlimited.");
			}
		currentRoom.removeItem(fooditem);
		player.increaseFoodBar();
	}

	public void eatFromBackpack(String foodstring) {
		Food food;
		Item item;

		// check if this food exists in the game and store food object in variable
		food = Environment.getFood(foodstring);
		item = environment.getItem(foodstring);
		
		if (food != null) {

			if (player.backpackContainsItem(item)) { // if item is not in room, go to inventory
				player.increaseFoodBar();
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

	private void store(String secondWord) {
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		if (item == null || !(item instanceof Transportable)) {
			System.out.println("This won't work.");

		} else if (player.cantCarryMore(item.getWeight())) { // check if there is free capacity to store the item

			informationTextArea.setText(printer.weightTooHighError());

		} else if (!currentRoom.containsItem(item)) {
			System.out.println("This item is not available at the moment.");
			System.out.println(printer.getItemHint());
		} else {
			// if there was no issue, store item
			currentRoom.removeItem(item);
			player.putItemIntoBackpack(item);
		}
	}

	public void drop(String secondWord) {
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		
		if(secondWord.equals("")) {
			informationTextArea.setText("You have to enter the item you want to drop.");
		}
		else if (item == null) {
			informationTextArea.setText("Sorry. This is not an item of this game.");
		} else if (!player.backpackContainsItem(item)) {
			informationTextArea.setText("You cannot drop that. Your backpack doesn't contain it.");
		} else if (item instanceof Food && !foodLabel.getText().equals("") || item instanceof Weapon && !weaponLabel.getText().equals("") || item instanceof Valuable && !valuableLabel.getText().equals("")) {
			informationTextArea.setText("You can't drop " + secondWord.toLowerCase().trim() + " here. At the moment there is no free space for it in this room.");	
		} else {
			// if there are no issues, drop item
			player.removeItemFromBackpack(item);
			currentRoom.addItem(item);
			informationTextArea.setText("You have dropped " + secondWord.toLowerCase().trim());
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


//	public final void chooseLevelOfDifficulty() {
//
//		printer.printDifficultyChoices();
//
//		String input = parser.getUserInput().trim().toUpperCase();
//		try {
//			difficultyLevel = Level.valueOf(input);
//			System.out.println("Thank you. Level of difficulty is set to: " + input);
//		} catch (IllegalArgumentException e) {
//			System.out.println(input + " ist not valid!");
//			System.out.println();
//			chooseLevelOfDifficulty();
//		}
//	}

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