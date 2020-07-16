package ZuulBad;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * World of Zuul is a very exciting adventure game. The player is lost in an old
 * castle and has to find and save the princess before he starves to death and
 * before the time runs out. On the way he faces bloodthirsty monsters and his
 * only help are his own abilities and the hints from residents of the castle.
 * If he succeeds, he can marry the princess, if not, he dies a cruel death and
 * nobody will find his body ever again.
 * 
 * This class sets up the scene for the game and initializes the class Game,
 * which is the controller for the graphical user interface.
 * 
 * @author Daniel Birk
 * @author Katerina Matysova
 * @author Sarah Engelmayer
 * 
 * @version 1.0
 */

public class WorldOfZuul extends Application {

	/**
	 * The main method is the starting point of the game.
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This class sets up the graphical scene and connects it to the game.
	 */
	@Override
	public void start(Stage stage) throws Exception {

		VBox root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
		Scene scene = new Scene(root, 850, 700);
		Game game = new Game();

		root = game;

		stage.setScene(scene);
		stage.show();
	}
}
