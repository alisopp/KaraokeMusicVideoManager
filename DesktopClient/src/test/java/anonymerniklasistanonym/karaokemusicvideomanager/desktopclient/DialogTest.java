package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import javafx.application.Application;
import javafx.stage.Stage;

public class DialogTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		Dialogs.chooseFile(primaryStage, "title", null, null, 1);
		Dialogs.chooseDirectory(primaryStage, "title", null);
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
