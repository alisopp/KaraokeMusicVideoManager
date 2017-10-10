package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

	// Views
	@FXML
	private TextField serverAddress;
	@FXML
	private TextField workingDirectory;
	@FXML
	private TextField userName;
	@FXML
	private PasswordField userPassword;

	@FXML
	private Button loginButton;

	private Stage serverStage;

	public Main mainClass;

	public void setServerLoginWindow(Main mainClass, Stage serverStage) {
		this.mainClass = mainClass;
		this.serverStage = serverStage;
		final String address = this.mainClass.getMusicVideohandler().getSftpIpAddress();
		final String directory = this.mainClass.getMusicVideohandler().getSftpDirectory();
		final String username = this.mainClass.getMusicVideohandler().getSftpUsername();
		if (address != null) {
			this.serverAddress.setText(address);
		}
		if (directory != null) {
			this.workingDirectory.setText(directory);
		}
		if (username != null) {
			this.userName.setText(username);
		}

	}

	@FXML
	public void tryToLogin() {

		if (this.mainClass.getMusicVideohandler().sftpConnect(this.userName.getText(), this.userPassword.getText(),
				this.serverAddress.getText(), this.workingDirectory.getText())) {
			this.mainClass.getMusicVideohandler().saveSftpLogin(this.serverAddress.getText(),
					this.workingDirectory.getText(), this.userName.getText());
			this.mainClass.getMusicVideohandler().sftpRetrievePlaylist();
			this.serverStage.close();

			this.serverStage.fireEvent(new WindowEvent(this.serverStage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} else {
			DialogModule.informationAlert("SFTP connection could not bes established", "Please try to login again",
					AlertType.INFORMATION);
		}

	}

	@FXML
	public void checkConnectButton() {
		if ((!userName.getText().equals("") && !userPassword.getText().equals(""))
				&& (!serverAddress.getText().equals("") && !workingDirectory.getText().equals(""))) {
			loginButton.setDisable(false);
		} else {
			loginButton.setDisable(true);
		}
	}
}
