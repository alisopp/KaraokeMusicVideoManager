package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import java.util.Random;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoRandomElement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * The controller class for the random music video window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class RandomWindowController {

	// Views
	@FXML
	private Label randomLable1;
	@FXML
	private Label randomLable2;
	@FXML
	private Label randomLable3;
	@FXML
	private Label randomLable4;
	@FXML
	private Label randomLable5;
	@FXML
	private Button randomAdd1;
	@FXML
	private Button randomPlay1;
	@FXML
	private Button randomAdd2;
	@FXML
	private Button randomPlay2;
	@FXML
	private Button randomAdd3;
	@FXML
	private Button randomPlay3;
	@FXML
	private Button randomAdd4;
	@FXML
	private Button randomPlay4;
	@FXML
	private Button randomAdd5;
	@FXML
	private Button randomPlay5;

	@FXML
	private Button buttonAddAll;
	@FXML
	private Button buttonRefresh;

	private MusicVideoRandomElement[] labelContent;

	private Main mainWindow;
	private MainWindowController mainWindowController;

	public void setWindowController(Main window, MainWindowController mainWindowController) {
		this.mainWindow = window;
		this.mainWindowController = mainWindowController;

		refreshRandom();
	}

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/**
		 * Set menu icons
		 */

		randomAdd1.setGraphic(WindowModule.createMenuIcon("add"));
		randomAdd2.setGraphic(WindowModule.createMenuIcon("add"));
		randomAdd3.setGraphic(WindowModule.createMenuIcon("add"));
		randomAdd4.setGraphic(WindowModule.createMenuIcon("add"));
		randomAdd5.setGraphic(WindowModule.createMenuIcon("add"));

		buttonAddAll.setGraphic(WindowModule.createMenuIcon("add"));

		randomPlay1.setGraphic(WindowModule.createMenuIcon("play"));
		randomPlay2.setGraphic(WindowModule.createMenuIcon("play"));
		randomPlay3.setGraphic(WindowModule.createMenuIcon("play"));
		randomPlay4.setGraphic(WindowModule.createMenuIcon("play"));
		randomPlay5.setGraphic(WindowModule.createMenuIcon("play"));

		buttonRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));
	}

	@FXML
	public void addVideo1() {
		addVideo(0);
	}

	@FXML
	public void addVideo2() {
		addVideo(1);
	}

	@FXML
	public void addVideo3() {
		addVideo(2);
	}

	@FXML
	public void addVideo4() {
		addVideo(3);
	}

	@FXML
	public void addVideo5() {
		addVideo(4);
	}

	private void addVideo(int position) {

		String[] authorComment = DialogModule.playlistDialog("", "", "Create a new Playlist entry",
				"Add an author and comment", "Add to playlist");

		if (authorComment != null && authorComment[0] != null) {
			if (authorComment[1] == null) {
				authorComment[1] = "";
			}

			this.mainWindow.getMusicVideohandler().addMusicVideoToPlaylist(labelContent[position].getIndex(),
					authorComment[0], authorComment[1]);
			mainWindowController.refreshMusicVideoPlaylistTable();
		}

	}

	private void playVideo(int position) {
		try {
			ExternalApplicationModule.openFile(labelContent[position].getMusicVideo().getPath().toFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void playVideo1() {
		playVideo(0);
	}

	@FXML
	public void playVideo2() {
		playVideo(1);
	}

	@FXML
	public void playVideo3() {
		playVideo(2);
	}

	@FXML
	public void playVideo4() {
		playVideo(3);
	}

	@FXML
	public void playVideo5() {
		playVideo(4);
	}

	@FXML
	public void addVideoAll() {
		String[] authorComment = DialogModule.playlistDialog("", "", "Create 5 new Playlist entries",
				"Add an author and comment", "Add to playlist");

		if (authorComment != null && authorComment[0] != null) {
			if (authorComment[1] == null) {
				authorComment[1] = "";
			}

			for (int i = 0; i < 5; i++) {
				this.mainWindow.getMusicVideohandler().addMusicVideoToPlaylist(labelContent[i].getIndex(),
						authorComment[0], authorComment[1]);
			}
			mainWindowController.refreshMusicVideoPlaylistTable();
		}

	}

	@FXML
	public void refreshRandom() {
		MusicVideo[] allFiles = this.mainWindow.getMusicVideohandler().getMusicVideoList();

		Label[] allLabels = { randomLable1, randomLable2, randomLable3, randomLable4, randomLable5 };

		labelContent = new MusicVideoRandomElement[allLabels.length];

		Random randomGenerator = new Random();
		for (int i = 0; i < allLabels.length; i++) {
			int randomNumber = randomGenerator.nextInt(allFiles.length);
			labelContent[i] = new MusicVideoRandomElement(allFiles[randomNumber], randomNumber);
		}
		for (int i = 0; i < allLabels.length; i++) {
			allLabels[i].setText(labelContent[i].getMusicVideo().getTitle() + " from "
					+ labelContent[i].getMusicVideo().getArtist());
		}
	}

}
