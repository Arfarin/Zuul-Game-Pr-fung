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

public class WorldOfZuul extends Application {
	
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		VBox root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
		Scene scene = new Scene(root, 850, 650);
		Game game = new Game();
		
		root = game;
		
		stage.setScene(scene);
		stage.show();
		
	}

}

	

