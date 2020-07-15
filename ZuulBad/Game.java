package ZuulBad;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Popup;
import javafx.util.Duration;

/**
 * This class is the controller class for the graphical user interface and it
 * connects the other classes of the game.
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
	 *
	 *
	 */
	private Environment environment;
	/**
	 *
	 *
	 */
	private Player player;
	/**
	 *
	 *
	 */
	private Random random;
	/**
	 *
	 *
	 */
	private static Level difficultyLevel = Level.EASY;

	/**
	 * Time for the player to complete the game.
	 * Measured by the number of room changes of the player.
	 * 
	 * @param int time 
	 */
	private int time;
	/**
	 *
	 *
	 */
	private SimpleIntegerProperty timeproperty;
	/**
	 *
	 *
	 */
	public Game() {
		environment = new Environment();
		timeproperty = new SimpleIntegerProperty(time);
	}

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
	ImageView floorImage;
	
	/**
	 *Set the popup to invisible.
	 */
	@FXML
	private void handlePopupClose() {
		popupPane.setVisible(false);
		descriptionPane.setVisible(false);
	}
	
	/**
	 *Show the room.
	 */
	@FXML
	private void handleRoomPopup() {
		openDescriptionPopup();
		popupTextArea.setText(currentRoom.getLongDescription());
	}
	
	/**
	 *Let the food show up in the room.
	 */
	@FXML
	private void handleFoodPopup() {
		if (currentRoom.getFood() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getFood().getDescriptionWithWeight());
		}
	}

	/**
	 *Let the weapons show up in the room.
	 */
	@FXML
	private void handleWeaponPopup() {
		if (currentRoom.getWeapon() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getWeapon().getDescriptionWithWeight());
		}
	}

	/**
	 *Let the valuables show up in the room.
	 */
	@FXML
	private void handleValuablePopup() {
		if (currentRoom.getValuable() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getValuable().getDescriptionWithWeight());
		}
	}

	/**
	 *Let the static items shop up in the room.
	 */
	@FXML
	private void handleStaticItemPopup() {
		if (currentRoom.getAccessory() != null) {
			openDescriptionPopup();
			popupTextArea.setText(currentRoom.getAccessory().getDescription());
		}
	}

	/**
	 *Show the description popup and set the other popups to invisible. 
	 */
	private void openDescriptionPopup() {
		popupPane.setVisible(true);
		descriptionPane.setVisible(true);
		monsterKilledPopup.setVisible(false);
		monsterDamagePopup.setVisible(false);
		roomLockedPopup.setVisible(false);
		roomUnlockedPopup.setVisible(false);
	}

	// other popups

	@FXML
	Label monsterKilledPopup;
	@FXML
	Label monsterDamagePopup;
	@FXML
	Label roomLockedPopup;
	@FXML
	Label roomUnlockedPopup;

	PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
	
	/**
	 *Set a display time for the popups.
	 */
	private void waitAndEnd() {
		visiblePause.play();
		visiblePause.setOnFinished(event -> popupPane.setVisible(false));
	}
	/**
	 *Show that the player killed the monster.
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
	 *Show that the monster caused damage to the player.
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
	 *Show that the room is locked.
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
	 *Show that the room is unlocked.
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
	SplitPane descriptionPane;
	@FXML
	TextArea informationTextArea;
	@FXML
	Button play;
	@FXML 
	StackPane endSceneDisplay;
	@FXML
	Button nextButton;

	/**
	 *
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
	 *
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

	/**
	 *
	 */
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

	/**
	 *Show the welcome display when the player chose the difficulty 'EASY'.
	 */
	@FXML
	private void chooseEasy(ActionEvent ActionEvent) {
		difficultyLevel = Level.EASY;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
	}

	/**
	 *Show the welcome display when the player chose the difficulty 'MEDIUM'.
	 */
	@FXML
	private void chooseMedium(ActionEvent ActionEvent) {
		difficultyLevel = Level.MEDIUM;
		levelSelectionDisplay.setVisible(false);
		welcomeDisplay.setVisible(true);
	}

	/**
	 *Show the welcome display when the player chose the difficulty 'HEAVY'.
	 */
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

	/**
	 *Show the instructions display when the player clicks on 'help'.
	 */
	@FXML
	private void handleClickHelp(ActionEvent ActionEvent) {
		instructionDisplay.setVisible(true);
		mainGameDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
		welcomeDisplay.setVisible(false);
	}

	/**
	 *Show the you loose display when the player clicks on 'quit'.
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

	@FXML
	Button closeButton;

	/**
	 *Exit the game when the player closes it.
	 */
	@FXML
	private void handleClose(ActionEvent ActionEvent) {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	Button restartButton;

	/**
	 *Show the difficulty selection display when the player wants to restart the game.
	 */
	@FXML
	private void handleRestart(ActionEvent ActionEvent) {
		levelSelectionDisplay.setVisible(true);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(false);
	}
	
	/**
	 *Show the winner display and rotate it when the player wins the game.
	 */
	@FXML
	private void handleChangeToWinnerDisplay(ActionEvent ActionEvent) {
		winnerDisplay.setVisible(true);
		endSceneDisplay.setVisible(false);
		
		RotateTransition rt = new RotateTransition(Duration.millis(3000), crownImage);
		rt.setByAngle(360);
		rt.setCycleCount(4);
		rt.setAutoReverse(true);

		rt.play();
	}
	/**
	 *Show the kiss princess display after the winner display as end scene.
	 */
	@FXML 
	private void handleKissPrincess(ActionEvent ActionEvent) {
		endSceneDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
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
	@FXML
	ImageView lockNorth;
	@FXML
	ImageView lockEast;
	@FXML
	ImageView lockSouth;
	@FXML
	ImageView lockWest;

	/**
	 *Change the room that is displayed in the direction 'north' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoNorthByClickOnRectangle() {
		goRoom("north");
	}

	/**
	 *Change the room that is displayed in the direction 'south' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoSouthByClickOnRectangle() {
		goRoom("south");
	}

	/**
	 *Change the room that is displayed in the direction 'east' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoEastByClickOnRectangle() {
		goRoom("east");
	}

	/**
	 *Change the room that is displayed in the direction 'west' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoWestByClickOnRectangle() {
		goRoom("west");
	}

	/**
	 *Change the room that is displayed in the direction 'up' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoUpByClickOnStairsUp() {
		goRoom("up");
	}

	/**
	 *Change the room that is displayed in the direction 'down' 
	 *when the player clicks on the given rectangle.
	 */
	@FXML
	private void handleGoDownByClickOnStairsDown() {
		goRoom("down");
	}

	@FXML
	TextField backpackTextFieldToType;
	@FXML
	Button eatFromBackpackButton;
	@FXML
	Button dropButton;
	@FXML
	Button destroyButton;
	@FXML
	Label backpackWeightLabel;

	/**
	 * Refresh the backpack so that items disappear when they
	 * are dropped/consumed or appear when they are taken. 
	 */
	private void refreshBackpack() {
		backpacklabel.setText(player.getBackpackContent());
		backpackTextFieldToType.clear();
		setItemLabels();
	}

	/**
	 *Refresh the backpack after eating food from the backpack.
	 * @param ActionEvent
	 */
	@FXML
	private void handleClickEat(ActionEvent ActionEvent) {
		eatFromBackpack(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	/**
	 * Refresh the backpack after dropping items.
	 * @param ActionEvent
	 */
	@FXML
	private void handleClickDrop(ActionEvent ActionEvent) {
		drop(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	/**
	 * Refresh the backpack after fighting a monster (destroying the weapon).
	 * @param ActionEvent
	 */
	@FXML
	private void handleClickDestroy(ActionEvent ActionEvent) {
		destroy(backpackTextFieldToType.getText());

		refreshBackpack();
	}

	@FXML
	Button hintButton;
	
	/**
	 *Display the hint from a NPC.
	 */
	@FXML
	private void handleClickHint(ActionEvent ActionEvent) {
		String npchint = hint();
		npcTextArea.setText(npchint);
	}

	@FXML
	Label foodLabel;
	@FXML
	ImageView foodImage;
	@FXML
	Button eatButton;
	@FXML
	Button storeFoodButton;
	@FXML
	Label weaponLabel;
	@FXML
	ImageView weaponImage;
	@FXML
	Button storeWeaponButton;
	@FXML
	Label valuableLabel;
	@FXML
	ImageView valuableImage;
	@FXML
	Button storeValuableButton;
	@FXML
	Label staticItemLabel;
	@FXML
	ImageView staticItemImage;
	@FXML
	ImageView humanImage;
	@FXML
	BorderPane npcBorderPane;

	/**
	 *Let the food disappear from the room after eating it directly.
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
	 *Display the food in the backpack after taking it from the room.
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
	 *Display the weapon in the backpack after taking it from the room.
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
	 *Display the valuable in the backpack after taking it from the room.
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
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		environment.prepareEnvironment();
		player = new Player();
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
	 *Show the fields for changing the room if there are exits. 
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
	 *Display the label of the backpack (weight). 
	 */
	private void setOtherLabels() {

		int usedWeight = player.getMaxWeight() - player.getBackpacksWeight();
		backpackWeightLabel.setText("Backpack Weight: " + usedWeight + " / " + player.getMaxWeight());
	}

	@FXML
	Label reasonForLoosingLabel;

	/**
	 *Check if the player is still alive (time, hunger and life points). 
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
	 *Display the different rooms.
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
	 *Show the looser display to the player.
	 */
	private void looseGame() {
		instructionDisplay.setVisible(false);
		levelSelectionDisplay.setVisible(false);
		mainGameDisplay.setVisible(false);
		winnerDisplay.setVisible(false);
		looserDisplay.setVisible(true);
		welcomeDisplay.setVisible(false);
	}

	@FXML
	ImageView crownImage;

	/**
	 *Show the winner display to the player.
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
	 * Try to move into one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
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
	 *Switch the room and update the statics:
	 *decrease time, increase hunger, increase life points
	 */
	private void switchRoom(Room nextRoom) {
		currentRoom = nextRoom;

		time--;
		player.getHungry();
		player.increaseLifeBar();
		timeproperty.setValue(time);

		setUpRoom();
		checkVitals();

		if (currentRoom.getRoomEntries() > 1) {
			informationTextArea.setText("You have already been in this room before. \r \n");
		}
	}
	/**
	 *Unlock a locked room if the player has a key in his backpack. 
	 *Otherwise print a message that the room is locked.
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
	 *Kill the monster in the room if the player has a weapon in the backpack.
	 *Otherwise receive damage and flee back to the previous room.
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
	 *Rescue the princess and win the game if the player has 
	 *the valuable 'dragonglass' in the backpack.
	 *Otherwise receive double damage and flee back to the previous room.
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
				informationTextArea
						.setText("Sorry, currently your backpack does not contain that.\n" + " You can only eat food which lies in the room you are in or which is stored in your backpack.");
			}
		} else {
			informationTextArea.setText("Sorry, this is not a food item of this game.");
		}
	}

	/**
	 *Return the NPC hint if there is one and the player has the suitable valuable in the backpack.
	 *Otherwise print one of the two messages.
	 *
	 *@return hint or message 
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
	 * can be fetched later when needed. To use the function the player has to type in
	 * the command word "store" an the name of the specified item which should be
	 * stored.
	 * 
	 * @param command the second word of the player's input
	 * @return true when storing was successful
	 */

	private void store(String secondWord) {
		Item item;

		// check if this item exists in the game and store it in variable
		item = environment.getItem(secondWord);
		if (item == null || !(item instanceof Transportable)) {
			System.out.println("This won't work.");

		} else if (player.cantCarryMore(item.getWeight())) { // check if there is free capacity to store the item

			informationTextArea.setText("Sorry, you can't put that into your backpack because it would exceed the maximum portable weight you can carry.");

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
	 * Drop an item from the backback into the room. To use the function the player has to type in
	 * the command word "drop" an the name of the specified item which should be
	 * dropped.
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
	 * Destroy an item from the backback into the room. To use the function the player has to type in
	 * the command word "destroy" an the name of the specified item which should be
	 * destroyed.
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
	 * 
	 */
	private Room getRandomRoom() {
		random = new Random();

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
	 * Display the room the player is in and add that room to the small map on the screen.
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
