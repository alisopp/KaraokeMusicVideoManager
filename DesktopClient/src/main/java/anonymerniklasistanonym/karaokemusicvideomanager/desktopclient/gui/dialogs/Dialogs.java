package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

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
		ButtonType buttonAlways = new ButtonType("Always", ButtonData.RIGHT);
		ButtonType ButtonNever = new ButtonType("Never", ButtonData.RIGHT);
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

	public static void createServerConnection() {
		// Create the custom dialog.
		Alert dialog = new Alert(AlertType.WARNING);
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Look, a Custom Login Dialog");

		// Set the icon (must be included in the project).
		// dialog.setGraphic(new
		// ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}

	public static void main(String[] args) {
		createServerConnection();
	}

}
