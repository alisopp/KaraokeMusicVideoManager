package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The controller class for the server login window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class ServerLoginWindowController {

	// FXML views

	/**
	 * Text field for the servers IP address
	 */
	@FXML
	private TextField serverIpAddress;

	/**
	 * Text field for the servers web distribution directory
	 */
	@FXML
	private TextField workingDirectory;

	/**
	 * Text field for the servers account user name
	 */
	@FXML
	private TextField userName;

	/**
	 * Text field for the servers account user password
	 */
	@FXML
	private PasswordField userPassword;

	/**
	 * Button to submit all the data and try to login to the server
	 */
	@FXML
	private Button loginButton;

	@FXML
	private Label labelLogin;
	@FXML
	private Label labelAddress;
	@FXML
	private Label labelFilePath;
	@FXML
	private Label labelUserName;
	@FXML
	private Label labelPassword;

	/**
	 * The own stage (connection to be able to close it within a method)
	 */
	private Stage serverStage;

	/**
	 * The main class (connection to be able to communicate with it)
	 */
	private Main mainClass;

	/**
	 * Setup for the window [controller] ServerLoginWindow[Controller]
	 * 
	 * @param mainClass
	 *            (Main | The main class)
	 * @param serverStage
	 *            (Stage | The own stage)
	 */
	public void setServerLoginWindow(Main mainClass, Stage serverStage) {

		// set the connection to main class and the own stage
		this.mainClass = mainClass;
		this.serverStage = serverStage;

		// get the currently available server data informations from the settings
		final String address = this.mainClass.getMusicVideohandler().getSftpIpAddress();
		final String directory = this.mainClass.getMusicVideohandler().getSftpDirectory();
		final String username = this.mainClass.getMusicVideohandler().getSftpUsername();

		// check if any of them are null and if not set them as text to their specific
		// text field
		if (address != null) {
			this.serverIpAddress.setText(address);
		}
		if (directory != null) {
			this.workingDirectory.setText(directory);
		}
		if (username != null) {
			this.userName.setText(username);
		}

	}

	private void translateText() {

		labelLogin.setText(Internationalization.translate("Welcome to the server login") + "!");
		labelAddress.setText(Internationalization.translate("IP address") + ":");
		labelFilePath.setText(Internationalization.translate("Working directory") + ":");
		labelUserName.setText(Internationalization.translate("User name") + ":");
		labelPassword.setText(Internationalization.translate("SFTP password") + ":");

		loginButton.setText(Internationalization.translate("Connect"));

		serverIpAddress.setPromptText(Internationalization.translate("Server address"));
		workingDirectory.setPromptText(Internationalization.translate("Path of the website folder"));
		userName.setPromptText(Internationalization.translate("SFTP user name"));
		userPassword.setPromptText(Internationalization.translate("SFTP password"));
	}

	@FXML
	private void initialize() {
		translateText();
	}

	/**
	 * Try to login to the server
	 */
	@FXML
	private void tryToLogin() {

		// because the form itself only works when everything isn't empty we can
		// directly try to connect with the information from all text fields
		if (this.mainClass.getMusicVideohandler().sftpConnect(this.userName.getText(), this.userPassword.getText(),
				this.serverIpAddress.getText(), this.workingDirectory.getText())) {

			// if everything worked (the login) save all login information to the settings
			this.mainClass.getMusicVideohandler().saveSftpLogin(this.serverIpAddress.getText(),
					this.workingDirectory.getText(), this.userName.getText());

			// then try to retrieve a existing playlist
			this.mainClass.getMusicVideohandler().sftpRetrievePlaylist();

			// then request a close the stage event
			this.serverStage.fireEvent(new WindowEvent(this.serverStage, WindowEvent.WINDOW_CLOSE_REQUEST));

		} else {

			// if the login didn't worked out show a dialog
			DialogModule.informationAlert("SFTP connection could not bes established", "Please try to login again",
					AlertType.INFORMATION);
		}

	}

	/**
	 * Check if the connect button can be activated
	 */
	@FXML
	private void checkConnectButton() {

		// check if all text fields are not empty
		if ((!userName.getText().equals("") && !userPassword.getText().equals(""))
				&& (!serverIpAddress.getText().equals("") && !workingDirectory.getText().equals(""))) {

			// if not enable the login button
			loginButton.setDisable(false);

		} else {

			// else disable the login button
			loginButton.setDisable(true);
		}

	}
}
