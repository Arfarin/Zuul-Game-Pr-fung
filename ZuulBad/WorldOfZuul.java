package ZuulBad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorldOfZuul extends Application {
	
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		VBox root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
		
		Game game = new Game();
		Scene scene = new Scene(root, 867, 708);stage.setScene(scene);
		root = game;
		
		
		stage.show();
		
	}

}

	

