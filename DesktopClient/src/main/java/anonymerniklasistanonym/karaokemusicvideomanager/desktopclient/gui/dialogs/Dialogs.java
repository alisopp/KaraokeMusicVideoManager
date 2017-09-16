package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

public class Dialogs {

	/**
	 * Dialog that asks if changes should be saved.
	 * 
	 * @param mainWindowEvent
	 *            (WindowEvent - needed to let the window alive if 'Cancel' get's
	 *            pressed)
	 */
	public static void mainStageClose(WindowEvent mainWindowEvent) {

		// create a JavaFX Alert pop-up window
		Alert alert = new Alert(AlertType.WARNING);

		// with the following title and text
		alert.setTitle("Save changes?");
		alert.setHeaderText("Do you want to save your changes?");

		// with the following buttons
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		final ButtonType buttonAlways = new ButtonType("Always", ButtonData.RIGHT);
		final ButtonType ButtonNever = new ButtonType("Never", ButtonData.RIGHT);
		alert.getButtonTypes().addAll(buttonAlways, ButtonNever);

		// which button was pressed getter
		ButtonType result = alert.showAndWait().get();

		// what should be done now
		if (result == ButtonType.CANCEL) {

			// let the window live on cancel
			mainWindowEvent.consume();

		} else {

			// quit the program and do also:
			if (result == ButtonType.YES) {
				// save changes in file
			} else if (result == buttonAlways) {
				// add to config that always the changes should be saved on exit
				// save changes in file
			} else if (result == ButtonNever) {
				// add to config that the changes never should be saved on exit
				// save changes in file
			}

			// close the program without exceptions else
			Platform.exit();
		}

	}

	public static void main(String[] args) {
	}

}
