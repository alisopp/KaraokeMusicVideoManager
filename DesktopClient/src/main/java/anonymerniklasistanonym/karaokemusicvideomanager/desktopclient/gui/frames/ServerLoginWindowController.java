package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.SftpModule;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

	public Main mainWindow;

	public void setServerLoginWindow(Main window) {
		this.mainWindow = window;
	}

	@FXML
	public void tryToLogin() {

		if (((serverAddress.getText() != null) && (workingDirectory.getText() != null))
				&& ((userName.getText() != null) && (userPassword.getText() != null))) {
			SftpModule sftpConnect = new SftpModule(userName.getText(), userPassword.getText(),
					serverAddress.getText());

			sftpConnect.connectSFTP();

			for (String a : sftpConnect.listFiles()) {
				System.out.println(a);
			}

		}

	}
}
