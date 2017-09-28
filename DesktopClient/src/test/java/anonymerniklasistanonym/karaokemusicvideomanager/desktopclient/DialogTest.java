package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import javafx.application.Application;
import javafx.stage.Stage;

public class DialogTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Dialogs.chooseFile(primaryStage, "title", null, null, 1);
		// Dialogs.chooseDirectory(primaryStage, "title", null);

		Exception ex = new FileNotFoundException("Could not find file blabla.txt");
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		String exceptionText = stringWriter.toString();

		Dialogs.exceptionDialog(primaryStage, "A exception was thrown!", "short descripton", "detailed descripton",
				exceptionText);
		// System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
