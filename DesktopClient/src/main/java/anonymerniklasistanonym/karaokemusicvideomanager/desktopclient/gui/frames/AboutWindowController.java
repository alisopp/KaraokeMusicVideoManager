package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import javafx.fxml.FXML;

/**
 * The controller class for the about window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class AboutWindowController {

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {
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
