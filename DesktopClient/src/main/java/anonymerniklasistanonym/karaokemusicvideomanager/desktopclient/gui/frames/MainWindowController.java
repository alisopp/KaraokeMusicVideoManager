package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.DirectoryPathTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowMethods;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * The controller class for the main window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MainWindowController {

	// FXML Views

	/**
	 * input search box/field
	 */
	@FXML
	private TextField searchBox;
	/**
	 * label of the input search box/field
	 */
	@FXML
	private Label searchLabel;

	// the music video table
	@FXML
	private TableView<MusicVideoTableView> musicVideoTable;
	@FXML
	private TableColumn<MusicVideoTableView, Number> columnIndex;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnArtist;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnTitle;

	// the directory path table
	@FXML
	private TableView<DirectoryPathTableView> directoryPathTable;
	@FXML
	private TableColumn<DirectoryPathTableView, String> columnFilePath;

	// the tabs
	@FXML
	private TabPane tabView;
	@FXML
	private Tab musicVideoTableTab;

	// music video files table context menu

	/**
	 * music video files table context menu > add file to the play list
	 */
	@FXML
	private MenuItem contextMusicVideoPlaylist;
	/**
	 * music video files table context menu > open directory of file
	 */
	@FXML
	private MenuItem contextMusicVideoDirectory;
	/**
	 * music video files table context menu > ignore the current file
	 */
	@FXML
	private MenuItem contextMusicVideoIgnore;
	/**
	 * music video files table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextMusicVideoClear;
	/**
	 * music video files table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextMusicVideoRefresh;

	// music video path table context menu

	/**
	 * music video path table context menu > remove the current selected directory
	 */
	@FXML
	private MenuItem contextPathRemove;
	/**
	 * music video path table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextPathClear;
	/**
	 * music video path table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextPathRefresh;

	// menu buttons

	/**
	 * export menu button > CSV export
	 */
	@FXML
	private MenuItem menuButtonCsv;
	/**
	 * export menu button > JSON export
	 */
	@FXML
	private MenuItem menuButtonJson;
	/**
	 * export menu button > export of websites
	 */
	@FXML
	private Menu menuButtonWebsites;
	/**
	 * export menu button > HTML static list export
	 */
	@FXML
	private MenuItem menuButtonHtmlStatic;
	/**
	 * export menu button > HTML list with search export
	 */
	@FXML
	private MenuItem menuButtonHtmlSearch;
	/**
	 * export menu button > HTML list with party playlist export
	 */
	@FXML
	private MenuItem menuButtonHtmlParty;

	/**
	 * about menu button > about button
	 */
	@FXML
	private MenuItem aboutButton;
	/**
	 * about menu button > help button
	 */
	@FXML
	private MenuItem helpButton;

	// window buttons

	/**
	 * network toggle button
	 */
	@FXML
	private ToggleButton networkButton;
	/**
	 * YouTube button
	 */
	@FXML
	private Button youTubeButton;
	/**
	 * Random button
	 */
	@FXML
	private Button randomButton;

	/**
	 * Add a directory to the path list button
	 */
	@FXML
	private Button buttonAddDirectory;
	/**
	 * Open the wrong formatted files window button
	 */
	@FXML
	private Button buttonWrongFormattedFiles;

	// other

	/**
	 * table data of the table with music video files
	 */
	private ObservableList<MusicVideoTableView> tableDataMusicVideo;

	/**
	 * table data of the table with music video file directories
	 */
	private ObservableList<DirectoryPathTableView> tableDataDirectory;

	/**
	 * get if the last mouse key that was pressed was the left mouse key
	 */
	private boolean leftMouseKeyWasPressed;

	/**
	 * Main class
	 */
	private Main mainWindow;

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the music video table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnIndex.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		columnArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		columnTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataMusicVideo = FXCollections.observableArrayList();
		FilteredList<MusicVideoTableView> filteredData = new FilteredList<>(tableDataMusicVideo, p -> true);

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

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the direectory path table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnFilePath.setCellValueFactory(cellData -> cellData.getValue().getFilePathProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataDirectory = FXCollections.observableArrayList();
		FilteredList<DirectoryPathTableView> filteredDataDirectory = new FilteredList<>(tableDataDirectory, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataDirectory.setPredicate(directoryPathObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<DirectoryPathTableView> sortedDataDirectory = new SortedList<>(filteredDataDirectory);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataDirectory.comparatorProperty().bind(directoryPathTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		directoryPathTable.setItems(sortedDataDirectory);

		/**
		 * Set menu icons
		 */

		// Context menu
		contextMusicVideoPlaylist.setGraphic(WindowMethods.createMenuIcon("images/menu/playlist.png"));
		contextMusicVideoDirectory.setGraphic(WindowMethods.createMenuIcon("images/menu/directory.png"));
		contextMusicVideoIgnore.setGraphic(WindowMethods.createMenuIcon("images/menu/ignore.png"));
		contextMusicVideoClear.setGraphic(WindowMethods.createMenuIcon("images/menu/clear.png"));
		contextMusicVideoRefresh.setGraphic(WindowMethods.createMenuIcon("images/menu/refresh.png"));
		contextPathRemove.setGraphic(WindowMethods.createMenuIcon("images/menu/remove.png"));
		contextPathClear.setGraphic(WindowMethods.createMenuIcon("images/menu/clear.png"));
		contextPathRefresh.setGraphic(WindowMethods.createMenuIcon("images/menu/refresh.png"));

		// other buttons
		buttonWrongFormattedFiles.setGraphic(WindowMethods.createMenuIcon("images/menu/wrongFormattedFiles.png"));
		buttonAddDirectory.setGraphic(WindowMethods.createMenuIcon("images/menu/add.png"));
		networkButton.setGraphic(WindowMethods.createMenuIcon("images/menu/network.png"));
		youTubeButton.setGraphic(WindowMethods.createMenuIcon("images/menu/youTube.png"));
		aboutButton.setGraphic(WindowMethods.createMenuIcon("images/menu/about.png"));
		randomButton.setGraphic(WindowMethods.createMenuIcon("images/menu/random.png"));

		// menu buttons
		menuButtonWebsites.setGraphic(WindowMethods.createMenuIcon("images/menu/html_static.png"));
		menuButtonCsv.setGraphic(WindowMethods.createMenuIcon("images/menu/csv.png"));
		menuButtonJson.setGraphic(WindowMethods.createMenuIcon("images/menu/json.png"));
		menuButtonHtmlStatic.setGraphic(WindowMethods.createMenuIcon("images/menu/html_static.png"));
		menuButtonHtmlSearch.setGraphic(WindowMethods.createMenuIcon("images/menu/html_search.png"));
		menuButtonHtmlParty.setGraphic(WindowMethods.createMenuIcon("images/menu/html_playlist.png"));

		// label
		searchLabel.setGraphic(WindowMethods.createMenuIcon("images/menu/search.png"));
	}

	public void setMainWindow(Main window) {
		this.mainWindow = window;

		refreshMusicVideoFileTable();
		refreshMusicVideoPathTable();

	}

	/**
	 * Do the following when a row is selected/clicked with the mouse
	 */
	@FXML
	private void openSelectedVideoFile() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (musicVideoTableTab.isSelected() && selectedEntry != null) {
			// open the music video file with the index
			this.mainWindow.getMusicVideohandler().openMusicVideo(selectedEntry.getIndex() - 1);
		} else {
			// search on YouTube
			searchOnYouTube();
		}
	}

	/**
	 * Monitor if the left mouse key was clicked
	 * 
	 * @param e
	 *            (MouseEvent | Needed to get the mouse key)
	 */
	@FXML
	private void mousePressed(MouseEvent e) {
		leftMouseKeyWasPressed = false;

		if (e.isPrimaryButtonDown()) {
			leftMouseKeyWasPressed = true;
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openMusicVideoFileLeftClick() {
		if (leftMouseKeyWasPressed == true) {
			openSelectedVideoFile();
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openDirectoryLeftClick() {
		if (leftMouseKeyWasPressed == true) {
			showDirectoryInExplorerPathList();
		}
	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	private void openTopMusicVideoFile() {

		System.out.println("hi");

		// if the musicVideoTable is selected
		if (tabView.getSelectionModel().getSelectedItem() == musicVideoTableTab) {
			// select the top item
			this.musicVideoTable.getSelectionModel().select(0);

			// open the music video that is selected
			openSelectedVideoFile();

			// clear the selection
			this.musicVideoTable.getSelectionModel().clearSelection();
		} else {
			// select the top item
			this.directoryPathTable.getSelectionModel().select(0);

			// open the music video that is selected
			showDirectoryInExplorerPathList();

			// clear the selection
			this.directoryPathTable.getSelectionModel().clearSelection();
		}
	}

	/**
	 * Clears the current row selection of the music video file table
	 */
	@FXML
	private void clearSelectionMusicVideoFileTable() {
		// clear the current selection
		this.musicVideoTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current row selection of the music video path table
	 */
	@FXML
	private void clearSelectionMusicVideoPathTable() {
		// clear the current selection
		this.directoryPathTable.getSelectionModel().clearSelection();
	}

	/**
	 * Clears the current text in the search box
	 */
	@FXML
	private void clearSearch() {
		// clear the current selection
		this.searchBox.setText("");
		if (tabView.getSelectionModel().getSelectedItem() == musicVideoTableTab) {
			this.searchBox.setPromptText("Search for music videos...");
		} else {
			this.searchBox.setPromptText("Search for directories...");
		}

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	private void searchOnYouTube() {

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
	private void addSourceFolderDialog() {
		File directory = Dialogs.chooseDirectory(this.mainWindow.getPrimaryStage(), "Add a path", null);

		if (directory != null && (directory.exists() && directory.isDirectory())) {
			this.mainWindow.getMusicVideohandler().addPathToPathList(directory.toPath());
			this.mainWindow.getMusicVideohandler().updateMusicVideoList();

			refreshMusicVideoFileTable();
			refreshMusicVideoPathTable();

		}
	}

	/**
	 * Open the About Window
	 */
	@FXML
	private void openAboutWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/AboutWindow.fxml"));

			Parent root1 = (Parent) loader.load();

			// Connection to the Controller from the primary Stage
			loader.getController();

			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setResizable(false);

			stage.setTitle("About");

			// only a exit button will be shown
			stage.initStyle(StageStyle.UTILITY);

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the server login window
	 */
	@FXML
	private void openServerLoginWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/ServerLoginWindow.fxml"));

			Parent root1 = (Parent) loader.load();

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

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					checkNetwork();
				}

			});

			// Connection to the Controller from the primary Stage
			ServerLoginWindowController aboutWindowController = loader.getController();
			aboutWindowController.setServerLoginWindow(this.mainWindow, stage);

			stage.show();
			checkNetwork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the wrong formatted files window
	 */
	@FXML
	private void openWrongFormattedFilesWindow() {

		try {
			// load the window from file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/WrongFomattedFilesWindow.fxml"));

			// Connection to the Controller from the primary Stage
			WrongFormattedFilesWindowController wrongWindowController = loader.getController();
			wrongWindowController.setWrongFormattedFilesWindow(this.mainWindow);

			// load main element as parent
			Parent root1 = (Parent) loader.load();

			// create new Stage and scene with the main element as parent
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			// make it resizable and set minimal widths
			stage.setResizable(true);
			stage.setMinWidth(450);
			stage.setMinHeight(350);

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowMethods.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			// set a window title
			stage.setTitle("Wrong Formatted Files");

			// show the stage/window
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the server login window
	 */
	@FXML
	private void openRandomWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/RandomMusicVideoWindow.fxml"));

			Parent root1 = (Parent) loader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinHeight(300);
			stage.setMinWidth(600);

			stage.setTitle("Random music video files");

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowMethods.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					checkNetwork();
				}

			});

			// Connection to the Controller from the primary Stage
			RandomWindowController aboutWindowController = loader.getController();
			aboutWindowController.setServerLoginWindow(this.mainWindow, stage);

			stage.show();
			checkNetwork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove a directory from the path list
	 */
	@FXML
	private void removeDirectory() {

		// get the currently selected directory/path
		DirectoryPathTableView selectedEntry = this.directoryPathTable.getSelectionModel().getSelectedItem();

		// if something was selected
		if (selectedEntry != null) {
			// remove this entry from the path list
			this.mainWindow.getMusicVideohandler().removeFromPathList(selectedEntry.getFilePath());

			// update now both tables
			refreshMusicVideoPathTable();
			refreshMusicVideoFileTable();
		}
	}

	/**
	 * Show the currently selected directory in the default file manager
	 */
	@FXML
	private void showDirectoryInExplorerPathList() {

		// get the currently selected entry
		DirectoryPathTableView selectedEntry = this.directoryPathTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			ExternalApplicationHandler.openFile(Paths.get(selectedEntry.getFilePath()).toFile());
		}
	}

	/**
	 * Show the currently selected files directory in the default file manager
	 */
	@FXML
	private void showDirectoryInExplorerMusicVideoList() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {

			// get the path of the music video object over the table index - 1
			MusicVideo selectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1];

			// open the selected music video file externally
			ExternalApplicationHandler.openDirectory(selectedFile.getPath().toFile());
		}
	}

	/**
	 * Add currently selected music video file to play list
	 */
	@FXML
	private void addVideoToPlaylist() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {
			// TODO
		}
	}

	/**
	 * Refresh the music video file table
	 */
	@FXML
	private void refreshMusicVideoFileTable() {

		// update the music video data
		this.mainWindow.getMusicVideohandler().updateMusicVideoList();

		// get music video data
		MusicVideo[] listOfVideos = this.mainWindow.getMusicVideohandler().getMusicVideoList();

		// add music video data
		if (listOfVideos != null) {
			tableDataMusicVideo.clear();
			for (int i = 0; i < listOfVideos.length; i++) {
				tableDataMusicVideo
						.add(new MusicVideoTableView(i + 1, listOfVideos[i].getArtist(), listOfVideos[i].getTitle()));
			}
		}
	}

	/**
	 * Refresh the music video path table
	 */
	@FXML
	private void refreshMusicVideoPathTable() {

		// get music video data
		Path[] listOfPaths = this.mainWindow.getMusicVideohandler().getPathList();

		// add music video data
		if (listOfPaths != null) {
			tableDataDirectory.clear();
			for (int i = 0; i < listOfPaths.length; i++) {
				tableDataDirectory.add(new DirectoryPathTableView(listOfPaths[i].toString()));
			}
		}
	}

	/**
	 * Ignore the currently selected music video file
	 */
	@FXML
	private void ignoreMusicVideoFile() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {
			// TODO
		}

		// update the music video list after this
		refreshMusicVideoFileTable();
	}

	/**
	 * Add network: Check if a working network connection is established
	 */
	private void checkNetwork() {
		// TODO

		// set the network button to connected if a connection was established
		this.networkButton.setSelected(false);

	}

	/**
	 * Export the music video list as static HTML table
	 */
	@FXML
	private void exportHtmlStatic() {
		File htmlFileDestination = Dialogs.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory", null);

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlList(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as HTML table with a search
	 */
	@FXML
	private void exportHtmlSearch() {
		File htmlFileDestination = Dialogs.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory", null);

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlSearch(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as HTML table with a playlist
	 */
	@FXML
	private void exportHtmlParty() {
		File htmlFileDestination = Dialogs.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory", null);

		if (htmlFileDestination != null) {
			this.mainWindow.getMusicVideohandler().saveHtmlParty(htmlFileDestination.toPath(), true);
		}
	}

	/**
	 * Export the music video list as a CSV table
	 */
	@FXML
	private void exportCsv() {
		ExtensionFilter csvFilter = new ExtensionFilter("Csv File", "*.csv");
		File[] csvFile = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(),
				"Export music video list to a CSV file - Choose a directory and filename", null,
				new ExtensionFilter[] { csvFilter }, Dialogs.CHOOSE_ACTION.SAVE);

		if (csvFile != null) {
			this.mainWindow.getMusicVideohandler().saveCsv(csvFile[0].toPath());
		}
	}

	/**
	 * Export the music video list as a JSON file
	 */
	@FXML
	private void exportJson() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(),
				"Export music video list to a JSON file - Choose a directory and filename", null,
				new ExtensionFilter[] { jsonFilter }, Dialogs.CHOOSE_ACTION.SAVE);

		if (jsonFile != null) {
			this.mainWindow.getMusicVideohandler().saveJson(jsonFile[0].toPath());
		}
	}

}
