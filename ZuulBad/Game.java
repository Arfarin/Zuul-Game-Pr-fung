package ZuulBad;

import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This class is the controller class for the graphical user interface and it
 * connects the other classes of the game. In this class the main interactions
 * are realized.
 * 
 * @author Daniel Birk
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 */

public class Game extends VBox {

	/**
	 * The room in which the player is at the moment.
	 */
	private Room currentRoom;
	/**
	 * An instance of the class Environment to use its methods.
	 */
	private Environment environment;
	/**
	 * An instance of the class Player to interact with its methods.
	 */
	private Player player;
	/**
	 * A instance of the variable Random. To make teleporting to a random room
	 * possible.
	 */
	private Random random;
	/**
	 * The Level of difficulty of the game. By default it is set to level EASY. It
	 * influences the maximum duration of the game (how often a the player can
	 * switch room), the amount of damage monsters cause on the players health when
	 * attacking and how much weight the player's backpack can hold.
	 */
	private static Level difficultyLevel = Level.EASY;

	/**
	 * Time for the player to complete the game. Measured by the number of room
	 * changes of the player.
	 * 
	 */
	private int time;

	/**
	 * Time for the player to complete the game. Gets the value of 'time'. Necessary
	 * for the according Change-Listener.
	 * 
	 */
	private SimpleIntegerProperty timeProperty;

	/**
	 * a PauseTransition for displaying the informations in popups for a definite
	 * time.
	 */
	private PauseTransition visiblePause;

	/**
	 * initializes the variables environment, timeProperty, player and visiblePause.
	 */
	public Game() {
		environment = new Environment();
		timeProperty = new SimpleIntegerProperty(time);
		player = new Player();
		visiblePause = new PauseTransition(Duration.seconds(2));
	}

	/**
	 * The label for the current room.
	 */
	@FXML
	Label roomlabel;
	/**
	 * The TextArea that displays the content of the backpack.
	 */
	@FXML
	TextArea backpacklabel;
	/**
	 * The TextArea that shows the message or hint given by the npc in a room.
	 */
	@FXML
	TextArea npcTextArea;
	/**
	 * The rectangle representing the lifeBar of the player
	 */
	@FXML
	Rectangle lifebarRectangle;
	/**
	 * The rectangle representing the foodBar of the player
	 */
	@FXML
	Rectangle foodbarRectangle;
	/**
	 * The rectangle representing the remaining time.
	 */
	@FXML
	Rectangle timeRectangle;
	/**
	 * The TextArea used in the popup Pane.
	 */
	@FXML
	TextArea popupTextArea;
	/**
	 * The 'OK' Button in the popupPane. Necessary to close the popup.
	 */
	@FXML
	Button popupButton;
	/**
	 * The imageView for the castle's floor
	 */
	@FXML
	ImageView floorImage;

	/**
	 * Sets the popup panes to invisible.
	 */
	@FXML
	private void handlePopupClose() {
		popupPane.setVisible(false);
		descriptionPane.setVisible(false);
	}

	/**
	 * Show the description of the current room in the popup.
	 */
	@FXML
	private void handleRoomPopup() {
		openDescriptionPopup();
		popupTextArea.setText(currentRoom.getLongDescription());
	}

	/**
	 * Let the food show up in the room.
	 */
	@FXML
	private void handleFoodPopup() {
		if (currentRoom.getFood() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getFood().getDescriptionWithWeight());
		}
	}

	/**
	 * Let the weapons show up in the room.
	 */
	@FXML
	private void handleWeaponPopup() {
		if (currentRoom.getWeapon() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getWeapon().getDescriptionWithWeight());
		}
	}

	/**
	 * Let the valuables show up in the room.
	 */
	@FXML
	private void handleValuablePopup() {
		if (currentRoom.getValuable() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getValuable().getDescriptionWithWeight());
		}
	}

	/**
	 * Let the static items shop up in the room.
	 */
	@FXML
	private void handleStaticItemPopup() {
		if (currentRoom.getAccessory() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getAccessory().getDescription());
		}
	}

	/**
	 * Show the description popup and set the other popups to invisible.
	 */
	private void openDescriptionPopup() {
		popupPane.setVisible(true);
		descriptionPane.setVisible(true);
		monsterKilledPopup.setVisible(false);
		monsterDamagePopup.setVisible(false);
		roomLockedPopup.setVisible(false);
		roomUnlockedPopup.setVisible(false);
	}

	/**
	 * The label displaying information that a monster was killed.
	 */
	@FXML
	Label monsterKilledPopup;
	/**
	 * The label displaying information that a monster caused damage on the player.
	 */
	@FXML
	Label monsterDamagePopup;
	/**
	 * The label displaying information that a room is locked.
	 */
	@FXML
	Label roomLockedPopup;
	/**
	 * The label displaying information that a room was unlocked.
	 */
	@FXML
	Label roomUnlockedPopup;

	/**
	 * 
	 */

	/**
	 * plays the PauseTransition-variable. When the time ends the popup is closed.
	 */
	private void waitAndEnd() {
		visiblePause.play();
		visiblePause.setOnFinished(event -> popupPane.setVisible(false));
	}

	/**
	 * Show that the player killed the monster in a popup. Show the popup for a
	 * definite time which is determined in the variable 'visiblePause'.
	 */
	private void KillMonsterPopup() {
		monsterDamagePopup.setVisible(false);
		roomLockedPopup.setVisible(false);
		roomUnlockedPopup.setVisible(false);
		descriptionPane.setVisible(false);

		popupPane.setVisible(true);
		monsterKilledPopup.setVisible(true);

		waitAndEnd();
	}

	/**
	 * Show that the monster caused damage to the player in a popup. Show the popup
	 * for a definite time which is determined in the variable 'visiblePause'.
	 */
	private void monsterDamagePopup() {
		roomLockedPopup.setVisible(false);
		roomUnlockedPopup.setVisible(false);
		monsterKilledPopup.setVisible(false);
		descriptionPane.setVisible(false);

		popupPane.setVisible(true);
		monsterDamagePopup.setVisible(true);

		waitAndEnd();
	}

	/**
	 * Show that the room is locked in a popup. Show the popup for a definite time
	 * which is determined in the variable 'visiblePause'.
	 */
	private void roomLockedPopup() {
		roomUnlockedPopup.setVisible(false);
		monsterKilledPopup.setVisible(false);
		monsterDamagePopup.setVisible(false);
		descriptionPane.setVisible(false);

		popupPane.setVisible(true);
		roomLockedPopup.setVisible(true);

		waitAndEnd();
	}

	/**
	 * Show that the room is unlocked in a popup. Show the popup for a definite time
	 * which is determined in the variable 'visiblePause'.
	 */
	private void roomUnlockedPopup() {
		monsterKilledPopup.setVisible(false);
		monsterDamagePopup.setVisible(false);
		roomLockedPopup.setVisible(false);
		descriptionPane.setVisible(false);

		popupPane.setVisible(true);
		roomUnlockedPopup.setVisible(true);

		waitAndEnd();
	}

	/**
	 * displays the game-instructions.
	 */
	@FXML
	BorderPane instructionDisplay;
	/**
	 * displays the main game.
	 */
	@FXML
	SplitPane mainGameDisplay;
	/**
	 * displays the level selection
	 */
	@FXML
	BorderPane levelSelectionDisplay;
	/**
	 * the first display showing a welcome message
	 */
	@FXML
	StackPane welcomeDisplay;
	/**
	 * the last display when winning
	 */
	@FXML
	StackPane winnerDisplay;
	/**
	 * the last display when loosing
	 */
	@FXML
	StackPane looserDisplay;
	/**
	 * the Pane for the popups
	 */
	@FXML
	Pane popupPane;
	/**
	 * the Pane for the description popups
	 */
	@FXML
	SplitPane descriptionPane;
	/**
	 * The TextArea for general information
	 */
	@FXML
	TextArea informationTextArea;
	/**
	 * Button to play the game
	 */
	@FXML
	Button play;
	/**
	 * the display shown before the winner display
	 */
	@FXML
	StackPane endSceneDisplay;
	/**
	 * The Button to go on to the winner display
	 */
	@FXML
	Button nextButton;
	/**
	 * an imageView for the picture of a labyrinth as a logo for the game 'ZuulBad'
	 */
	@FXML
	ImageView labyrinth;

	/**
	 * sets only the maingameDisplay visible.
	 */
	@FXML
	private void handleStart(ActionEvent ActionEvent) {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(true);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
		play();
	}

	/**
	 * sets only the pane for the instructions visible.
	 */
	@FXML
	private void handleReadInstructions(ActionEvent ActionEvent) {
		instructionDisplay.setVisible(true);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
		play();

	}

	@FXML
	Button easy;
	@FXML
	Button medium;
	@FXML
	Button heavy;

	/**
	 * Show the welcome display when the player chose the difficulty 'EASY'.
	 */
	@FXML
	private void chooseEasy(ActionEvent ActionEvent) {
		difficultyLevel = Level.EASY;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
		rotateImage(labyrinth);
	}

	/**
	 * Show the welcome display when the player chose the difficulty 'MEDIUM'.
	 */
	@FXML
	private void chooseMedium(ActionEvent ActionEvent) {
		difficultyLevel = Level.MEDIUM;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
		rotateImage(labyrinth);
	}

	/**
	 * Show the welcome display when the player chose the difficulty 'HEAVY'.
	 */
	@FXML
	private void chooseHeavy(ActionEvent ActionEvent) {
		difficultyLevel = Level.HEAVY;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
		rotateImage(labyrinth);
	}

	/**
	 * MenuItem for 'help'
	 */
	@FXML
	MenuItem helpme;
	/**
	 * MenuItem for 'quit'
	 */
	@FXML
	MenuItem quit;

	/**
	 * Show a popup with hints by click on 'help'.
	 */
	@FXML
	private void handleClickHelp(ActionEvent ActionEvent) {
		popupPane.setVisible(true);
		descriptionPane.setVisible(true);
		popupTextArea.setText("Click on the brown rectangles (doors) to move from one room to another."
				+ " To enter a locked room there must be a key in your backpack." + " \n"
				+ "If you have any kind of weapon in your backpack you can beat all monsters except the boss monster. You need a specific item to beat that one and to win the game.");
	}

	/**
	 * Show the looser-display when the player clicks on 'quit'.
	 */
	@FXML
	private void handleQuit(ActionEvent ActionEvent) {
		mainGameDisplay.setVisible(false);
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(true);
		welcomeDisplay.setVisible(false);
	}

	/**
	 * A button to close the game
	 */
	@FXML
	Button closeButton;

	/**
	 * Exit the game. Used when the player clicks the close-button.
	 */
	@FXML
	private void handleClose(ActionEvent ActionEvent) {
		Platform.exit();
		System.exit(0);
	}

	/**
	 * a button to restart the game when loosing.
	 */
	@FXML
	Button restartButton;

	/**
	 * Show the difficulty selection display when the player wants to restart the
	 * game.
	 */
	@FXML
	private void handleRestart(ActionEvent ActionEvent) {
		levelSelectionDisplay.setVisible(true);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
	}

	/**
	 * a rectangle representing the door of the room to the north
	 */
	@FXML
	Rectangle northDoorRectangle;
	/**
	 * a rectangle representing the door of the room to the south
	 */
	@FXML
	Rectangle southDoorRectangle;
	/**
	 * a rectangle representing the door of the room to the east
	 */
	@FXML
	Rectangle eastDoorRectangle;
	/**
	 * a rectangle representing the door of the room to the west
	 */
	@FXML
	Rectangle westDoorRectangle;
	/**
	 * a rectangle representing a way up in the room
	 */
	@FXML
	Rectangle upDoorRectangle;
	/**
	 * a rectangle representing a way down in the room
	 */
	@FXML
	Rectangle downDoorRectangle;
	/**
	 * label for the upDoorRectangle
	 */
	@FXML
	Label upDoorLabel;
	/**
	 * label for the downDoorRectangle
	 */
	@FXML
	Label downDoorLabel;
	/**
	 * an image of a padlock for the door to the north
	 */
	@FXML
	ImageView lockNorth;
	/**
	 * an image of a padlock for the door to the east
	 */
	@FXML
	ImageView lockEast;
	/**
	 * an image of a padlock for the door to the south
	 */
	@FXML
	ImageView lockSouth;
	/**
	 * an image of a padlock for the door to the west
	 */
	@FXML
	ImageView lockWest;

	/**
	 * Change to the next room in the north. This is called clicking on the
	 * northDoorRectangle.
	 */
	@FXML
	private void handleGoNorthByClickOnRectangle() {
		goRoom("north");
	}

	/**
	 * Change to the next room in the south. This is called clicking on the
	 * southDoorRectangle.
	 */
	@FXML
	private void handleGoSouthByClickOnRectangle() {
		goRoom("south");
	}

	/**
	 * Change to the next room in the east. This is called clicking on the
	 * EastDoorRectangle.
	 */
	@FXML
	private void handleGoEastByClickOnRectangle() {
		goRoom("east");
	}

	/**
	 * Change to the next room in the West. This is called clicking on the
	 * westDoorRectangle.
	 */
	@FXML
	private void handleGoWestByClickOnRectangle() {
		goRoom("west");
	}

	/**
	 * Change to the next room into the direction 'up'. This is called clicking on
	 * upDoorRectangle.
	 */
	@FXML
	private void handleGoUpByClickOnStairsUp() {
		goRoom("up");
	}

	/**
	 * Change to the next room into the direction 'down'. This is called clicking on
	 * downDoorRectangle.
	 */
	@FXML
	private void handleGoDownByClickOnStairsDown() {
		goRoom("down");
	}

	/**
	 * TextField for backpack. The user can type in the name of items stored in the
	 * backpack to destroy, eat or drop the item.
	 */
	@FXML
	TextField backpackTextFieldToType;
	/**
	 * a button 'eat' to eat a food item of backpack
	 */
	@FXML
	Button eatFromBackpackButton;
	/**
	 * a button 'drop' to drop an item of backpack
	 */
	@FXML
	Button dropButton;
	/**
	 * a button 'destroy' to destroy or "delete" items stored in the backpack
	 */
	@FXML
	Button destroyButton;
	/**
	 * a label to display the current weight and the maximum portable weight of the
	 * backpack.
	 */
	@FXML
	Label backpackWeightLabel;

	/**
	 * Refresh the backpack so that items disappear when they are dropped/consumed
	 * or appear when they are taken.
	 */
	private void refreshBackpack() {
		backpacklabel.setText(player.getBackpackContent());
		backpackTextFieldToType.clear();
		setItemLabels();
	}

	/**
	 * By click on the backpack's button 'eat' the string in the textfield is
	 * analysed and if there is a food item with that name it is eaten and removed
	 * from backpack. Refresh the content of the backpack displayed in the label
	 * after eating.
	 * 
	 * @param ActionEvent click on the button 'eat'
	 */
	@FXML
	private void handleClickEat(ActionEvent ActionEvent) {
		eatFromBackpack(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	/**
	 * By click on the backpack's button 'drop' the string in the textfield is
	 * analysed and if there is an item with that name it is dropped and removed
	 * from backpack. Refresh the content of the backpack displayed in the label
	 * after dropping.
	 * 
	 * @param ActionEvent click on the button 'drop'
	 */
	@FXML
	private void handleClickDrop(ActionEvent ActionEvent) {
		drop(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	/**
	 * By click on the backpack's button 'destroy' the string in the textfield is
	 * analysed and if there is an item with that name it is removed from backpack.
	 * Refresh the content of the backpack displayed in the label after destroying.
	 * 
	 * @param ActionEvent click on the button 'destroy'
	 */
	@FXML
	private void handleClickDestroy(ActionEvent ActionEvent) {
		destroy(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	/**
	 * A button to get a hint from an npc
	 */
	@FXML
	Button hintButton;

	/**
	 * Display the hint from a NPC.
	 */
	@FXML
	private void handleClickHint(ActionEvent ActionEvent) {
		String npchint = hint();
		npcTextArea.setText(npchint);
	}

	/**
	 * a label for the food item in a room
	 */
	@FXML
	Label foodLabel;

	/**
	 * an imageView for a general image of a food. For every food item there is the
	 * same picture.
	 */
	@FXML
	ImageView foodImage;

	/**
	 * a button to eat the food item of a room
	 */
	@FXML
	Button eatButton;

	/**
	 * a button to store a food item lying in a room
	 */
	@FXML
	Button storeFoodButton;

	/**
	 * a label for the weapon item in a room
	 */
	@FXML
	Label weaponLabel;
	/**
	 * an imageView for a general image of a weapon. For every weapon there is the
	 * same picture.
	 */
	@FXML
	ImageView weaponImage;
	/**
	 * a button to store a weapon item lying in a room
	 */
	@FXML
	Button storeWeaponButton;
	/**
	 * a label for the valuable item in a room
	 */
	@FXML
	Label valuableLabel;
	/**
	 * an imageView for a general image of a valuable item. For every valuable item
	 * there is the same picture.
	 */
	@FXML
	ImageView valuableImage;
	/**
	 * a button to store a valuable item lying in a room
	 */
	@FXML
	Button storeValuableButton;
	/**
	 * a label for an accessory item in a room
	 */
	@FXML
	Label staticItemLabel;
	/**
	 * an imageView for a general image of a accessory item. For every accessory
	 * item there is the same picture.
	 */
	@FXML
	ImageView staticItemImage;
	/**
	 * an imageView for a general image of a person (NPC). For every npc there is
	 * the same picture.
	 */
	@FXML
	ImageView humanImage;
	/**
	 * A Border Pane for the npc and its textArea in the room
	 */
	@FXML
	BorderPane npcBorderPane;

	/**
	 * Let the food disappear from the room after eating it. Check first if the
	 * current room contains a food item.
	 * 
	 * @param ActionEvent click on 'eat' button in the room
	 */
	@FXML
	private void handleEatFoodClick(ActionEvent ActionEvent) {
		if (foodLabel.getText() != "") {
			eatFromRoom(foodLabel.getText());
			foodLabel.setText("");
		}
		refreshBackpack();
	}

	/**
	 * Display the food in the backpack after taking it from the room. * @param
	 * ActionEvent click on 'store' button in the room next to the foodImage
	 */
	@FXML
	private void handleStoreFoodClick(ActionEvent ActionEvent) {
		if (foodLabel.getText() != "") {
			store(foodLabel.getText());
			foodLabel.setText("");
		}
		refreshBackpack();
	}

	/**
	 * Display the weapon in the backpack after taking it from the room. * @param
	 * ActionEvent click on 'store' button in the room next to the weaponImage
	 */
	@FXML
	private void handleStoreWeaponClick(ActionEvent ActionEvent) {
		if (weaponLabel.getText() != "") {
			store(weaponLabel.getText());
			weaponLabel.setText("");
		}
		refreshBackpack();
	}

	/**
	 * Display the valuable in the backpack after taking it from the room.
	 * 
	 * @param ActionEvent click on 'store' button in the room next to the valuable
	 *                    item image
	 */
	@FXML
	private void handleStoreValuableClick(ActionEvent ActionEvent) {
		if (valuableLabel.getText() != "") {
			store(valuableLabel.getText());
			valuableLabel.setText("");
		}
		refreshBackpack();
	}

	/**
	 * Starts playing the game. Creating items and distribute them to the rooms,
	 * creates an instance of Player, sets the time depending on the difficulty
	 * Level, sets the first room, sets up the first room and sets up the
	 * Change-Listeners for the variables lifeBar and foodBar (for class Player),
	 * time (for Game) and maxWeight (for inventory).
	 */
	public void play() {
		environment.prepareEnvironment();
		time = Level.setValue(35, -5);

		currentRoom = environment.getFirstRoom();
		setUpRoom();

		// set up all the listeners

		player.lifeBarProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxlife = player.getMaxLife();
				double length = (player.lifeBarProperty().doubleValue() / maxlife) * maxwidth;
				lifebarRectangle.setWidth(length);
			}
		});

		player.foodBarProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxfood = player.getMaxFood();
				double length = (player.foodBarProperty().doubleValue() / maxfood) * maxwidth;
				foodbarRectangle.setWidth(length);
			}
		});

		this.timeProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				int maxwidth = 387;
				int maxtime = Level.setValue(35, -5);
				double length = (timeProperty().doubleValue() / maxtime) * maxwidth;
				timeRectangle.setWidth(length);
			}
		});

		player.backpackWeightProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				int usedWeight = player.getMaxWeight() - player.getBackpacksFreeWeight();
				backpackWeightLabel.setText("Backpack Weight: " + usedWeight + " / " + player.getMaxWeight());
			}
		});

	}

	/**
	 * Getter for time-Property. Necessary for the listener to control the variable
	 * 'time'.
	 * 
	 * @return the timeProperty variable
	 */

	public IntegerProperty timeProperty() {
		return timeProperty;
	}

	/**
	 * set the labels of the items in a room. If there are items set its labels to
	 * visible and display its name. Otherwise set the itemImage, related buttons
	 * and label to invisible.
	 * 
	 */

	private void setItemLabels() {
		if (currentRoom.getFood() != null) {
			foodLabel.setText(currentRoom.getFood().getName());
			foodImage.setVisible(true);
			eatButton.setVisible(true);
			storeFoodButton.setVisible(true);
		} else {
			foodLabel.setText("");
			foodImage.setVisible(false);
			eatButton.setVisible(false);
			storeFoodButton.setVisible(false);
		}
		if (currentRoom.getWeapon() != null) {
			weaponLabel.setText(currentRoom.getWeapon().getName());
			weaponImage.setVisible(true);
			storeWeaponButton.setVisible(true);
		} else {
			weaponLabel.setText("");
			weaponImage.setVisible(false);
			storeWeaponButton.setVisible(false);
		}
		if (currentRoom.getValuable() != null) {
			valuableLabel.setText(currentRoom.getValuable().getName());
			valuableImage.setVisible(true);
			storeValuableButton.setVisible(true);
		} else {
			valuableLabel.setText("");
			valuableImage.setVisible(false);
			storeValuableButton.setVisible(false);
		}
		if (currentRoom.getAccessory() != null) {
			staticItemLabel.setText(currentRoom.getAccessory().getName());
			staticItemImage.setVisible(true);
		} else {
			staticItemLabel.setText("");
			staticItemImage.setVisible(false);
		}
		if (currentRoom.getNpc() != null) {
			npcBorderPane.setVisible(true);
		} else {
			npcBorderPane.setVisible(false);
		}

	}

	/**
	 * Show the rectangles (doors) to next rooms if there are according exits.
	 */
	private void setExitLabels() {

		lockNorth.setVisible(false);
		lockSouth.setVisible(false);
		lockEast.setVisible(false);
		lockWest.setVisible(false);

		Room northRoom = currentRoom.getExit("north");
		if (northRoom == null) {
			northDoorRectangle.setVisible(false);
		} else {
			northDoorRectangle.setVisible(true);
			if (northRoom.isLocked()) {
				lockNorth.setVisible(true);
			}
		}

		Room southRoom = currentRoom.getExit("south");
		if (southRoom == null) {
			southDoorRectangle.setVisible(false);
		} else {
			southDoorRectangle.setVisible(true);
			if (southRoom.isLocked()) {
				lockSouth.setVisible(true);
			}
		}

		Room eastRoom = currentRoom.getExit("east");
		if (eastRoom == null) {
			eastDoorRectangle.setVisible(false);
		} else {
			eastDoorRectangle.setVisible(true);
			if (eastRoom.isLocked()) {
				lockEast.setVisible(true);
			}
		}

		Room westRoom = currentRoom.getExit("west");
		if (westRoom == null) {
			westDoorRectangle.setVisible(false);
		} else {
			westDoorRectangle.setVisible(true);
			if (westRoom.isLocked()) {
				lockWest.setVisible(true);
			}
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

	/**
	 * Display the label of the backpack. Display its current weight and maximum
	 * portable weight.
	 */
	private void setOtherLabels() {

		int usedWeight = player.getMaxWeight() - player.getBackpacksFreeWeight();
		backpackWeightLabel.setText("Backpack Weight: " + usedWeight + " / " + player.getMaxWeight());
	}

	/**
	 * Label that describes why a user lost the game. Displayed on the looser
	 * display.
	 */
	@FXML
	Label reasonForLoosingLabel;

	/**
	 * Check if the player is still alive (time, hunger and life points). If not,
	 * set an according message into the reasonForLoosingLabel.
	 */
	private void checkVitals() {
		if (timeOver(time)) {
			reasonForLoosingLabel.setText("Time was over");
			looseGame();
		} else if (player.starvedToDeath()) {
			reasonForLoosingLabel.setText("You starved to death");
			looseGame();
		} else if (player.beaten()) {
			reasonForLoosingLabel.setText("You were beaten by a monster");
			looseGame();
		}
	}

	/**
	 * Sets up the room. Displays the items, the non player character, the images
	 * and labels of the current room and clear the Text-Area which displays general
	 * information. Increments the number of entries of the current room.
	 */
	private void setUpRoom() {
		setItemLabels();
		setExitLabels();
		setOtherLabels();

		roomlabel.setText(currentRoom.toString());
		backpacklabel.setText(player.getBackpackContent());
		npcTextArea.setText(currentRoom.getNpcMessage() + "\n");
		informationTextArea.clear();
		this.setRoomOnMapVisible();

		if (currentRoom.getNpc() != null) {
			npcTextArea.appendText("Can you please get me my " + currentRoom.getWantedNPCItem() + "?");
		}
		currentRoom.addRoomEntry();

	}

	/**
	 * Show the display for loosing the game
	 */
	private void looseGame() {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(true);
		welcomeDisplay.setVisible(false);
	}

	/**
	 * an image of a crown for the winnerDisplay
	 */
	@FXML
	ImageView crownImage;

	/**
	 * Show the display before the winner-Display. Called when the game's goal is
	 * reached. It tells the story that the princess is rescued from a monster now.
	 */
	private void winGame() {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		endSceneDisplay.setVisible(true);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);

	}

	/**
	 * Show the winner display and rotate an image of a crown. This is the last
	 * display of the game when winning.
	 */
	@FXML
	private void handleChangeToWinnerDisplay(ActionEvent ActionEvent) {
		winnerDisplay.setVisible(true);
		endSceneDisplay.setVisible(false);
		rotateImage(crownImage);

	}

	/**
	 * rotates an ImageView to 360 degrees and back for several times.
	 * 
	 * @param image; the image that should be rotated
	 */
	private void rotateImage(ImageView image) {
		RotateTransition rt = new RotateTransition(Duration.millis(3000), image);
		rt.setByAngle(360);
		rt.setCycleCount(4);
		rt.setAutoReverse(true);
		rt.play();
	}

	/**
	 * Try to move into one direction. If there is an exit, enter the new room. 
	 * If the next room is locked, try to unlock it.
	 * If the next room is the teleporter room, get teleported.
	 * If there is a monster in the next room, try to beat it.
	 * If the room is the final room, try to rescue the princess. 
	 * Otherwise print an error message in the information text field.
	 * Afterwards check if player is still alive.  
	 */
	private void goRoom(String direction) {
		informationTextArea.clear();

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

	/**
	 * Switch the room and update the statics: decrease time, increase hunger,
	 * increase life points
	 */
	private void switchRoom(Room nextRoom) {
		currentRoom = nextRoom;

		time--;
		player.getHungry();
		player.increaseLifeBar();
		timeProperty.setValue(time);

		setUpRoom();
		checkVitals();

		if (currentRoom.getRoomEntries() > 1) {
			informationTextArea.setText("You have already been in this room before. \r \n");
		}
	}

	/**
	 * Unlock a locked room if the player has a key in his backpack. Otherwise print
	 * a message that the room is locked.
	 */
	private void tryUnlockRoom(Room nextRoom) {
		Item key = environment.getItem("key");

		if (player.backpackContainsItem(key)) {
			roomUnlockedPopup();
			nextRoom.unlockRoom();
			player.removeItemFromBackpack(key);
			informationTextArea.setText(nextRoom + " was unlocked.");
			switchRoom(nextRoom);
		} else {
			informationTextArea.setText("The " + nextRoom.toString().toLowerCase() + " is locked!");
			roomLockedPopup();
		}
	}

	/**
	 * Kill the monster in the room if the player has a weapon in the backpack.
	 * Otherwise receive damage and flee back to the previous room.
	 */
	private void tryKillMonster(Room nextRoom) {

		if (player.hasWeapon()) {
			player.removeAWeaponFromBackpack();
			informationTextArea.setText("You killed the monster in the room.\n");

			KillMonsterPopup();
			backpacklabel.setText(player.getBackpackContent());
			nextRoom.killMonster();
			switchRoom(nextRoom);
		} else {
			int damage = Level.setValue(1, 1);
			monsterDamagePopup();
			player.reduceLifeBar(damage);
			informationTextArea.setText("The Monster hurt you. You have to flee back to the previous room.\n");
		}
	}

	/**
	 * Rescue the princess and win the game if the player has the valuable
	 * 'dragonglass' in the backpack. Otherwise receive double damage and flee back
	 * to the previous room.
	 */
	private void rescuePrincess() {
		Valuable dragonGlass = Environment.getValuable("dragonglass");

		if (player.backpackContainsItem(dragonGlass)) {
			winGame();
		} else {
			int damage = Level.setValue(1, 1) * 2; // final boss makes double damage (amount of damage depends on
													// selected level)
			player.reduceLifeBar(damage);
			informationTextArea.setText("The Monster hurt you badly. You have to flee back to the previous room.\n");
		}
	}

	/**
	 * Eat a food item lying in the current room. Eating something the player's
	 * FoodBar increases and the item is removed from the room. Called when clicking
	 * on the 'eat' button in the room. If the food is a magic muffin than the
	 * player gets the power to carry a unlimited weight in his/her backpack.
	 * 
	 * @param foodstring the food that is eaten.
	 */

	public void eatFromRoom(String foodstring) {
		Item fooditem;

		// check if this item exists in the game and store the object in variable
		fooditem = environment.getItem(foodstring);
		// check if the item is a food
		if (fooditem instanceof Food) {

			if (foodstring.matches("magic muffin")) { // check if user wants to eat a magic muffin
				informationTextArea.setText(player.getPowerFromMuffin());
				backpackWeightLabel.setText("Portable weight of backpack is unlimited.");
			}
			currentRoom.removeItem(fooditem);
			player.increaseFoodBar();
		} else {
			informationTextArea.setText("This is not a food. You can't eat it.");
		}
	}

	/**
	 * Eat a food which is stored in the backpack. Eating something the player's
	 * FoodBar increases and the item is removed from the backpack. Called when
	 * clicking on the 'eat' button of backpack after typing in the name of the food
	 * in the text-field.
	 * 
	 * @param foodstring the food that should be eaten
	 */
	public void eatFromBackpack(String foodstring) {
		Food food;
		Item item;

		// check if this food exists in the game and store food object in variable
		food = Environment.getFood(foodstring);
		item = environment.getItem(foodstring);

		if (food != null) {

			if (player.backpackContainsItem(item)) {
				player.increaseFoodBar();
				player.removeItemFromBackpack(item);

			} else {
				informationTextArea.setText("Sorry, currently your backpack does not contain that.\n"
						+ " You can only eat food which lies in the room you are in or which is stored in your backpack.");
			}
		} else {
			informationTextArea.setText("Sorry, this is not a food item of this game.");
		}
	}

	/**
	 * Return the NPC hint if there is one and the player has the suitable valuable
	 * in the backpack. Otherwise return a message that says the player should find
	 * the right valuable for him/her.
	 *
	 * @return hint or message
	 */
	private String hint() {

		if (currentRoom.getNpc() == null) {
			return "Here is nobody to talk with.";

		} else {
			System.out.println();
			Valuable valuable = Environment.getValuable(currentRoom.getWantedNPCItem());

//			String response = currentRoom.getNpcHint(specificValuable);
			if (player.backpackContainsItem(valuable)) {

				return currentRoom.getNpcHint(valuable.getName());
			} else {
				return "Please find " + currentRoom.getWantedNPCItem() + " for me first";
			}
		}
	}

	/**
	 * Put a portable item lying in the current room into the inventory. The item
	 * can be fetched later when needed. To use the function the player has to type
	 * in the command word "store" an the name of the specified item which should be
	 * stored.
	 * 
	 * @param command the second word of the player's input
	 * @return true when storing was successful
	 */

	private void store(String secondWord) {
		Item item;

		// check if this item exists in the game and if it is transportable; store it in
		// variable
		item = environment.getItem(secondWord);
		if (item == null || !(item instanceof Transportable)) {
			informationTextArea.setText("Sorry. Storing is not possible.");

		} else if (player.cantCarryMore(item.getWeight())) { // check if there is free capacity to store the item

			informationTextArea.setText(
					"Sorry, you can't put that into your backpack because it would exceed the maximum portable weight you can carry.");

		} else if (!currentRoom.containsItem(item)) {
			informationTextArea.setText("This item is not available at the moment.");
			informationTextArea.setText("You can only store portable things you find in the room you're in.");
		} else {
			// if there was no issue, store item
			currentRoom.removeItem(item);
			player.putItemIntoBackpack(item);
		}
	}

	/**
	 * Drop an item from the backback into the room. To use the function the player
	 * has to type in the command word "drop" an the name of the specified item
	 * which should be dropped.
	 * 
	 * @param command the second word of the player's input
	 * @return true when dropping was successful
	 */
	public void drop(String typedWord) {
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(typedWord);

		if (typedWord.equals("")) {
			informationTextArea.setText("You have to enter the item you want to drop.");
		} else if (item == null) {
			informationTextArea.setText("Sorry. This is not an item of this game.");
		} else if (!player.backpackContainsItem(item)) {
			informationTextArea.setText("You cannot drop that. Your backpack doesn't contain it.");
		} else if (item instanceof Food && !foodLabel.getText().equals("")
				|| item instanceof Weapon && !weaponLabel.getText().equals("")
				|| item instanceof Valuable && !valuableLabel.getText().equals("")) {
			informationTextArea.setText("You can't drop " + typedWord.toLowerCase().trim()
					+ " here. At the moment there is no free space for it in this room.");
		} else {
			// if there are no issues, drop item
			player.removeItemFromBackpack(item);
			currentRoom.addItem(item);
			informationTextArea.setText("You have dropped " + typedWord.toLowerCase().trim());
		}
	}

	/**
	 * Destroy an item from the backback into the room. To use the function the
	 * player has to type in the command word "destroy" an the name of the specified
	 * item which should be destroyed.
	 * 
	 * @param command the second word of the player's input
	 * @return true when destroying was successful
	 */
	public void destroy(String typedWord) {
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(typedWord);

		if (typedWord.equals("")) {
			informationTextArea.setText("You have to enter the item you want to destroy.");
		} else if (item == null) {
			informationTextArea.setText("Sorry. This is not an item of this game.");
		} else if (player.removeItemFromBackpack(item) == false) {
			informationTextArea.setText("You cannot destroy that. Your backpack doesn't contain it.");
		} else {
			informationTextArea.setText("You have destroyed " + typedWord.toLowerCase().trim());
		}
	}

	/**
	 * Define that the time is over if the time is < 1.
	 * 
	 * @param int time
	 * @return true if the time if < 1
	 */
	private boolean timeOver(int time) {
		if (time < 1) {
			System.out.println("Time is over.");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Chooses an arbitrary room of the game except the teleporter-room.
	 * 
	 * @return randomroom, a random room of the game. It can be every room of the
	 *         game except the teleporter-room itself.
	 */
	private Room getRandomRoom() {
		random = new Random();

		// the '-1' makes sure that the player won't be teleported to the
		// teleporter-room itself (which doesn't have any exits).
		int randomnumber = random.nextInt(Room.values().length - 1);
		Room randomroom = Room.values()[randomnumber];

		return randomroom;
	}

	/**
	 * Getter for the level of difficulty. For the classes player and monster to set
	 * the maximum weight of backpack and the damage of one attack.
	 * 
	 * @return the level of difficulty set by the user at the beginning of the Game
	 */
	public static Level getLevel() {
		return difficultyLevel;
	}

	@FXML
	Rectangle pastry;
	@FXML
	Label pastryLabel;
	@FXML
	Rectangle kitchen;
	@FXML
	Label kitchenLabel;
	@FXML
	Rectangle diningRoom;
	@FXML
	Label diningRoomLabel;
	@FXML
	Rectangle entryHall;
	@FXML
	Label entryHallLabel;
	@FXML
	Rectangle pantry;
	@FXML
	Label pantryLabel;
	@FXML
	Rectangle warehouse;
	@FXML
	Label warehouseLabel;
	@FXML
	Rectangle castleCourtyard;
	@FXML
	Label castleCourtyardLabel;
	@FXML
	Rectangle castleGarden;
	@FXML
	Label castleGardenLabel;
	@FXML
	Rectangle desWineStorage;
	@FXML
	Label desWineStorageLabel;
	@FXML
	Rectangle flowerGarden;
	@FXML
	Label flowerGardenLabel;
	@FXML
	Rectangle kingsChamber;
	@FXML
	Label kingsChamberLabel;
	@FXML
	Rectangle towerStaircases;
	@FXML
	Label towerStaircasesLabel;

	@FXML
	GridPane mapGroundFloor;
	@FXML
	GridPane mapSecondFloor;
	@FXML
	GridPane mapBasement;

	@FXML
	Rectangle basementEntry;
	@FXML
	Label basementEntryLabel;

	@FXML
	Rectangle armoury;
	@FXML
	Label armouryLabel;

	@FXML
	Rectangle treasureChamber;
	@FXML
	Label treasureChamberLabel;

	@FXML
	Rectangle undergroundHallway;
	@FXML
	Label undergroundHallwayLabel;

	@FXML
	Rectangle hiddenPath;
	@FXML
	Label hiddenPathLabel;

	@FXML
	Rectangle dungeon;
	@FXML
	Label dungeonLabel;

	@FXML
	Rectangle teleporterRoom;
	@FXML
	Label teleporterRoomLabel;

	@FXML
	Rectangle destroyedTower;
	@FXML
	Label destroyedTowerLabel;

	/**
	 * Display the room the player is in and add that room to the small map on the
	 * screen.
	 */
	public void setRoomOnMapVisible() {

		mapBasement.setVisible(false);
		mapGroundFloor.setVisible(false);
		mapSecondFloor.setVisible(false);

		switch (currentRoom) {
		case CastleCourtyard:
			mapGroundFloor.setVisible(true);
			castleCourtyard.setVisible(true);
			castleCourtyardLabel.setVisible(true);
			break;
		case CastleGarden:
			mapGroundFloor.setVisible(true);
			castleGarden.setVisible(true);
			castleGardenLabel.setVisible(true);
			break;
		case FlowerGarden:
			mapGroundFloor.setVisible(true);
			flowerGarden.setVisible(true);
			flowerGardenLabel.setVisible(true);
			break;
		case KingsChamber:
			mapGroundFloor.setVisible(true);
			kingsChamber.setVisible(true);
			kingsChamberLabel.setVisible(true);
			break;
		case EntryHall:
			mapGroundFloor.setVisible(true);
			entryHall.setVisible(true);
			entryHallLabel.setVisible(true);
			break;
		case TowerStaircases:
			mapGroundFloor.setVisible(true);
			towerStaircases.setVisible(true);
			towerStaircasesLabel.setVisible(true);
			break;
		case DiningRoom:
			mapGroundFloor.setVisible(true);
			diningRoom.setVisible(true);
			diningRoomLabel.setVisible(true);
			break;
		case Kitchen:
			mapGroundFloor.setVisible(true);
			kitchen.setVisible(true);
			kitchenLabel.setVisible(true);
			break;
		case Pastry:
			mapGroundFloor.setVisible(true);
			pastry.setVisible(true);
			pastryLabel.setVisible(true);
			break;
		case Pantry:
			mapGroundFloor.setVisible(true);
			pantry.setVisible(true);
			pantryLabel.setVisible(true);
			break;
		case Warehouse:
			mapGroundFloor.setVisible(true);
			warehouse.setVisible(true);
			warehouseLabel.setVisible(true);
			break;
		case DesertedWineStorage:
			mapGroundFloor.setVisible(true);
			desWineStorage.setVisible(true);
			desWineStorageLabel.setVisible(true);
			break;
		case BasementEntry:
			mapBasement.setVisible(true);
			basementEntry.setVisible(true);
			basementEntryLabel.setVisible(true);
			break;
		case Armoury:
			mapBasement.setVisible(true);
			armoury.setVisible(true);
			armouryLabel.setVisible(true);
			break;
		case TreasureChamber:
			mapBasement.setVisible(true);
			treasureChamber.setVisible(true);
			treasureChamberLabel.setVisible(true);
			break;
		case UndergroundHallway:
			mapBasement.setVisible(true);
			undergroundHallway.setVisible(true);
			undergroundHallwayLabel.setVisible(true);
			break;
		case HiddenPath:
			mapBasement.setVisible(true);
			hiddenPath.setVisible(true);
			hiddenPathLabel.setVisible(true);
			break;
		case Dungeon:
			mapBasement.setVisible(true);
			dungeon.setVisible(true);
			dungeonLabel.setVisible(true);
			break;
		case TeleporterRoom:
			mapSecondFloor.setVisible(true);
			teleporterRoom.setVisible(true);
			teleporterRoomLabel.setVisible(true);
			break;
		case DestroyedTower:
			mapSecondFloor.setVisible(true);
			destroyedTower.setVisible(true);
			destroyedTowerLabel.setVisible(true);
			break;
		}
	}

}
