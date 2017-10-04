package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.SftpModule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	}

	@FXML
	public void tryToLogin() {

		SftpModule sftpConnect = new SftpModule(userName.getText(), userPassword.getText(), serverAddress.getText());

		sftpConnect.connectSFTP();

		if (sftpConnect.isConnectionEstablished()) {
			for (String a : sftpConnect.listFiles()) {
				System.out.println(a);
			}

			sftpConnect.disconnectSFTP();
			this.a.close();
		}

		sftpConnect.disconnectSFTP();

	}

	public void checkConnectButton() {
		if ((!userName.getText().equals("") && !userPassword.getText().equals(""))
				&& (!serverAddress.getText().equals("") && !workingDirectory.getText().equals(""))) {
			loginButton.setDisable(false);
		} else {
			loginButton.setDisable(true);
		}
	}
}
