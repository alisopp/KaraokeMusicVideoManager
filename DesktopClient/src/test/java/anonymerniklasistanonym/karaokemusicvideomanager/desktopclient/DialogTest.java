package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.DialogHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogTest extends Application {

	@Override
	public void start(Stage primaryStage) {

		DialogHandler.confirm("information", "This", "Text");
		DialogHandler.inform("information", "This");
		DialogHandler.error("ERROR", "there was an Error");
		exceptionDialog();

		DialogModule.alertTextInputTwoBoxes("title", "head", "input 1", "input 2", "prompt1", "prompt2", "Label1",
				"Label2", "Button text", null, WindowModule.getWindowIcons(), StageStyle.UNIFIED, true, true, false,
				false, true);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void exceptionDialog() {

		Exception ex = new FileNotFoundException("Could not find file blabla.txt");
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		String exceptionText = stringWriter.toString();

		DialogHandler.error("ERROR", "there was an Error", "This error:", exceptionText);

	}

}
