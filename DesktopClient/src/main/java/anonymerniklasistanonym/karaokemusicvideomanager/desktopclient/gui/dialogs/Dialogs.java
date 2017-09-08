package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

public class Dialogs {

	public static void mainStageClose(WindowEvent e) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Save changes?");
		alert.setHeaderText("Do you want to save your changes?");

		ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
		ButtonType buttonAlways = new ButtonType("Always", ButtonData.RIGHT);
		ButtonType ButtonNever = new ButtonType("Never", ButtonData.RIGHT);
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonAlways, ButtonNever, buttonCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes || result.get() == buttonAlways) {
			// ... user chose "One"
		} else if (result.get() == buttonNo) {
			// ... user chose "Two"
		} else if (result.get() == ButtonNever) {
			// ... user chose "Three"
		}

		if (result.get() == buttonCancel) {
			e.consume();
			// ... user chose CANCEL or closed the dialog
		} else {
			Platform.exit();
		}
	}

}
