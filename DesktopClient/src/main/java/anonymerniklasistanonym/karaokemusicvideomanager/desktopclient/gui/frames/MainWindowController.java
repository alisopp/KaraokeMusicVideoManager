package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowMethods;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindowController {

	// Views
	@FXML
	private Label label;
	@FXML
	private TextField searchBox;

	@FXML
	private TableView<MusicVideoTableView> musicVideoTable;
	@FXML
	private TableColumn<MusicVideoTableView, Number> columnIndex;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnArtist;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnTitle;

	private ObservableList<MusicVideoTableView> tableData = FXCollections.observableArrayList();

	public Main mainWindow;

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnIndex.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		columnArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<MusicVideoTableView> filteredData = new FilteredList<>(tableData, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(musicVideoObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (musicVideoObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (musicVideoObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicVideoTableView> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(musicVideoTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		musicVideoTable.setItems(sortedData);
	}

	public void setMainWindow(Main window) {
		this.mainWindow = window;

		updateMusicVideoListTable();

	}

	/**
	 * Do the following when a row is selected/clicked with the mouse
	 */
	@FXML
	private void openSelectedVideoFile() {

		// ge the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			this.mainWindow.musicVideohandler.openMusicVideo(selectedEntry.getIndex() - 1);
		} else {
			// search on YouTube
			searchOnYouTube();
		}
	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	public void openTopMusicVideoFile() {

		// select the top item
		this.musicVideoTable.getSelectionModel().select(0);

		// open the music video that is selected
		openSelectedVideoFile();

		// clear the selection
		this.musicVideoTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current selection
	 */
	@FXML
	private void unSelectVideoFile() {
		// clear the current selection
		this.musicVideoTable.getSelectionModel().clearSelection();

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	public void searchOnYouTube() {

		// Text we want to search on YouTube
		String searchQuery = searchBox.getText();

		// The URL of YouTube
		String youTubeUrl = "https://www.youtube.com";

		// if text field has text try to add it to the urlToOpen
		try {
			if (searchQuery != null && searchQuery.length() > 0) {

				// encode the text to a browser query
				String textToSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");

				// add the search query to the YouTube URL
				youTubeUrl += "/results?search_query=" + textToSearchQuery;

				// open the new URL
				ExternalApplicationHandler.openUrl(youTubeUrl);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	public void addSourceFolderDialog() {
		File directory = Dialogs.chooseDirectory(this.mainWindow.primaryStage, "Add a path", null);

		if (directory != null && (directory.exists() && directory.isDirectory())) {
			this.mainWindow.musicVideohandler.addPathToPathList(directory.toPath());
			this.mainWindow.musicVideohandler.updateMusicVideoList();

			updateMusicVideoListTable();

		}
	}

	/**
	 * Open the About Window
	 */
	@FXML
	public void openAboutWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/AboutWindow.fxml"));

			Parent root1 = (Parent) loader.load();

			// Connection to the Controller from the primary Stage
			AboutWindowController aboutWindowController = loader.getController();
			aboutWindowController.setAboutWindow(this.mainWindow);

			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setResizable(false);

			stage.setTitle("About");

			// only a exit button will be shown
			stage.initStyle(StageStyle.UTILITY);

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowMethods.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the About Window
	 */
	@FXML
	public void openServerLoginWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/ServerLoginWindow.fxml"));

			Parent root1 = (Parent) loader.load();

			// Connection to the Controller from the primary Stage
			ServerLoginWindowController aboutWindowController = loader.getController();
			aboutWindowController.setServerLoginWindow(this.mainWindow);

			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setResizable(false);

			stage.setTitle("Server Login");

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowMethods.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the music video table in the window with the current music video list
	 */
	public void updateMusicVideoListTable() {
		// get music video data
		MusicVideo[] listOfVideos = this.mainWindow.musicVideohandler.getMusicVideoList();

		// add music video data
		if (listOfVideos != null) {
			tableData.clear();
			for (int i = 0; i < listOfVideos.length; i++) {
				tableData.add(new MusicVideoTableView(i + 1, listOfVideos[i].getArtist(), listOfVideos[i].getTitle()));
			}
		}
	}

}
