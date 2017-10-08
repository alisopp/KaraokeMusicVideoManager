package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
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
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	@FXML
	public void openGitHubLinkProfile() {
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym");
	}

}
