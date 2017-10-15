package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * The controller class for the about window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class AboutWindowController {

	/**
	 * The program is open source...
	 */
	@FXML
	private Text about01;

	/**
	 * Author link...
	 */
	@FXML
	private Text about02;

	/**
	 * Code link...
	 */
	@FXML
	private Text about03;

	/**
	 * Version number link...
	 */
	@FXML
	private Text about04;

	@FXML
	private void initialize() {
		translateText();
	}

	/**
	 * Window text that should be translated on language change/load
	 */
	private void translateText() {

		about01.setText(Internationalization.translate("This program is completely open source on Github"));
		about02.setText(
				Internationalization.translate("Author") + ": Niklas | https://github.com/AnonymerNiklasistanonym");
		about03.setText(Internationalization.translate("Code")
				+ ": https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
		about04.setText(Internationalization.translate("Version") + ": v2.0.0");

	}

	/**
	 * Open the link to the GitHub repository in the default browser
	 */
	@FXML
	private void openGitHubLinkRepository() {
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	/**
	 * Open the link to the authors GitHub profile in the default browser
	 */
	@FXML
	private void openGitHubLinkProfile() {
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym");
	}

}
