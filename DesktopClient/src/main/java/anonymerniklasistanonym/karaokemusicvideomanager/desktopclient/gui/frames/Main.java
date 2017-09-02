package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainLayout.fxml"));
			primaryStage.setTitle("Title");
			primaryStage.setScene(new Scene(root, 450, 300));
			primaryStage.setMinWidth(450);
			primaryStage.setMinHeight(300);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
