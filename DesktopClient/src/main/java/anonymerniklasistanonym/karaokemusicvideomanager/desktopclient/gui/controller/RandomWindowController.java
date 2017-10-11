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
 * The controller class for the random music video window.
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class RandomWindowController {

	// FXML views

	/**
	 * Random music video text label #1 of 5
	 */
	@FXML
	private Label randomLable1;
	/**
	 * Random music video text label #2 of 5
	 */
	@FXML
	private Label randomLable2;
	/**
	 * Random music video text label #3 of 5
	 */
	@FXML
	private Label randomLable3;
	/**
	 * Random music video text label #4 of 5
	 */
	@FXML
	private Label randomLable4;
	/**
	 * Random music video text label #5 of 5
	 */
	@FXML
	private Label randomLable5;

	/**
	 * Random music video add button #1 of 5
	 */
	@FXML
	private Button randomAdd1;
	/**
	 * Random music video add button #2 of 5
	 */
	@FXML
	private Button randomAdd2;
	/**
	 * Random music video add button #3 of 5
	 */
	@FXML
	private Button randomAdd3;
	/**
	 * Random music video add button #4 of 5
	 */
	@FXML
	private Button randomAdd4;
	/**
	 * Random music video add button #5 of 5
	 */
	@FXML
	private Button randomAdd5;

	/**
	 * Random music video play button #1 of 5
	 */
	@FXML
	private Button randomPlay1;
	/**
	 * Random music video play button #2 of 5
	 */
	@FXML
	private Button randomPlay2;
	/**
	 * Random music video play button #3 of 5
	 */
	@FXML
	private Button randomPlay3;
	/**
	 * Random music video play button #4 of 5
	 */
	@FXML
	private Button randomPlay4;
	/**
	 * Random music video play button #5 of 5
	 */
	@FXML
	private Button randomPlay5;

	/**
	 * Add all random music videos to the playlist
	 */
	@FXML
	private Button buttonAddAll;

	/**
	 * Refresh the current random music videos
	 */
	@FXML
	private Button buttonRefresh;

	/**
	 * All random music videos
	 */
	private MusicVideoRandomElement[] labelContent;

	/**
	 * All random music video text labels
	 */
	private Label[] allLabels;

	/**
	 * The main class to interact with the playlist
	 */
	private Main mainClass;

	/**
	 * The main window controller class to update the playlist table
	 */
	private MainWindowController mainWindowController;

	/**
	 * Setup for the window [controller] RandomWindow[Controller]
	 * 
	 * @param mainClass
	 *            (Main | The main class)
	 * @param mainWindowController
	 *            (MainWindowController | The main window controller)
	 */
	public void setWindowController(Main mainClass, MainWindowController mainWindowController) {

		// set the main class and main window controller
		this.mainClass = mainClass;
		this.mainWindowController = mainWindowController;

		// add all labels to an array
		this.allLabels = new Label[] { this.randomLable1, this.randomLable2, this.randomLable3, this.randomLable4,
				this.randomLable5 };

		// create an array that has as many elements as the labels
		this.labelContent = new MusicVideoRandomElement[allLabels.length];

		// now refresh/create the random music video list
		refreshRandom();

	}

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/**
		 * Set icons
		 */

		// "add" symbol buttons
		final Button[] allAddButtons = new Button[] { this.randomAdd1, this.randomAdd2, this.randomAdd3,
				this.randomAdd4, this.randomAdd5, this.buttonAddAll };
		for (int i = 0; i < allAddButtons.length; i++) {
			allAddButtons[i].setGraphic(WindowModule.createMenuIcon("add"));
		}

		// "play" symbol buttons
		final Button[] allPlayButtons = new Button[] { this.randomPlay1, this.randomPlay2, this.randomPlay3,
				this.randomPlay4, this.randomPlay5 };
		for (int i = 0; i < allPlayButtons.length; i++) {
			allPlayButtons[i].setGraphic(WindowModule.createMenuIcon("play"));
		}

		// "refresh" button
		buttonRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));

	}

	/**
	 * Add an entry in the random list to the playlist
	 */
	private void addVideoMain(int position, boolean addAll) {

		// create a different header for one file and all
		String header;
		if (addAll) {
			header = "Create 5 new Playlist entries";
		} else {
			header = "Create a new Playlist entry";
		}

		// open a dialog to input name and comment
		final String[] authorComment = DialogModule.playlistDialog(this.mainWindowController.getLastName(), "", header,
				"Add an author and comment", "Add to playlist");

		// if at least the author wasn't null
		if (authorComment != null && authorComment[0] != null) {

			// save the new name in the main controller class
			this.mainWindowController.setLastName(authorComment[0]);

			// set the comment to "" if it was null
			if (authorComment[1] == null) {
				authorComment[1] = "";
			}

			// if all should be added
			if (addAll) {

				// iterate through this loop through all labels
				for (int i = 0; i < this.labelContent.length; i++) {

					// and add each element
					this.mainClass.getMusicVideohandler().addMusicVideoToPlaylistRandom(i,
							this.labelContent[i].getIndex(), authorComment[0], authorComment[1]);
				}

			} else {

				// else only add the one at the given position
				this.mainClass.getMusicVideohandler().addMusicVideoToPlaylist(this.labelContent[position].getIndex(),
						authorComment[0], authorComment[1]);
			}

			// last but not least refresh the playlist table in the main window
			this.mainWindowController.refreshMusicVideoPlaylistTable();
		}

	}

	/**
	 * Add an entry in the random list to the playlist
	 */
	private void addVideo(int position) {
		addVideoMain(position, false);
	}

	/**
	 * Add an entry in the random list to the playlist
	 */
	private void addAllVideos() {
		addVideoMain(0, false);
	}

	/**
	 * Add the 1. entry to the playlist
	 */
	@FXML
	private void addVideo1() {
		addVideo(0);
	}

	/**
	 * Add the 2. entry to the playlist
	 */
	@FXML
	private void addVideo2() {
		addVideo(1);
	}

	/**
	 * Add the 3. entry to the playlist
	 */
	@FXML
	private void addVideo3() {
		addVideo(2);
	}

	/**
	 * Add the 4. entry to the playlist
	 */
	@FXML
	private void addVideo4() {
		addVideo(3);
	}

	/**
	 * Add the 5. entry to the playlist
	 */
	@FXML
	private void addVideo5() {
		addVideo(4);
	}

	/**
	 * Add all entries to the playlist
	 */
	@FXML
	private void addVideoAll() {
		addAllVideos();
	}

	/**
	 * Play the music video from the entry position
	 * 
	 * @param position
	 *            (Integer)
	 */
	private void playVideo(int position) {
		ExternalApplicationModule.openFile(this.labelContent[position].getMusicVideo().getPath().toFile());
	}

	/**
	 * Add the music video of the 1. entry
	 */
	@FXML
	private void playVideo1() {
		playVideo(0);
	}

	/**
	 * Add the music video of the 2. entry
	 */
	@FXML
	private void playVideo2() {
		playVideo(1);
	}

	/**
	 * Add the music video of the 3. entry
	 */
	@FXML
	private void playVideo3() {
		playVideo(2);
	}

	/**
	 * Add the music video of the 4. entry
	 */
	@FXML
	private void playVideo4() {
		playVideo(3);
	}

	/**
	 * Add the music video of the 5. entry
	 */
	@FXML
	private void playVideo5() {
		playVideo(4);
	}

	/**
	 * Update/Create the random music video table
	 */
	@FXML
	private void refreshRandom() {

		// get all files
		final MusicVideo[] allFiles = this.mainClass.getMusicVideohandler().getMusicVideoList();

		// create a random number generator
		final Random randomGenerator = new Random();

		// do for every label
		for (int i = 0; i < this.allLabels.length; i++) {

			// get a random Integer between 0 and label.length
			final int randomNumber = randomGenerator.nextInt(allFiles.length);

			// create a random music video element with this number and save it
			this.labelContent[i] = new MusicVideoRandomElement(allFiles[randomNumber], randomNumber);

			// set the text to each label of this entry
			this.allLabels[i].setText(this.labelContent[i].getMusicVideo().getTitle() + " from "
					+ this.labelContent[i].getMusicVideo().getArtist());
		}

	}

}
