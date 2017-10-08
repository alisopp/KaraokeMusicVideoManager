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

	private Stage a;

	public Main mainWindow;

	public void setServerLoginWindow(Main window, Stage a) {
		this.mainWindow = window;
		this.a = a;
		String address = this.mainWindow.getMusicVideohandler().getSftpIpAddress();
		String directory = this.mainWindow.getMusicVideohandler().getSftpDirectory();
		String username = this.mainWindow.getMusicVideohandler().getSftpUsername();
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

		if (this.mainWindow.getMusicVideohandler().sftpConnect(userName.getText(), userPassword.getText(),
				serverAddress.getText(), workingDirectory.getText())) {
			this.mainWindow.getMusicVideohandler().saveSftpLogin(serverAddress.getText(), workingDirectory.getText(),
					userName.getText());
			this.mainWindow.getMusicVideohandler().sftpRetrievePlaylist();
			this.a.close();

			this.a.fireEvent(new WindowEvent(this.a, WindowEvent.WINDOW_CLOSE_REQUEST));
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
