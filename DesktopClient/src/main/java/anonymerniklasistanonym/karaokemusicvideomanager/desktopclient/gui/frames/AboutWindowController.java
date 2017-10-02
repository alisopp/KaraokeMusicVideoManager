package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import javafx.fxml.FXML;

public class AboutWindowController {

	// Views

	public Main mainWindow;

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {
	}

	public void setAboutWindow(Main window) {
		this.mainWindow = window;
	}

	@FXML
	public void openGitHubLinkRepository() {
		ExternalApplicationHandler.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	@FXML
	public void openGitHubLinkProfile() {
		ExternalApplicationHandler.openUrl("https://github.com/AnonymerNiklasistanonym");
	}

}
