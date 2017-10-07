package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs.Dialogs;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.DirectoryPathTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.PlaylistTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowMethods;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
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

	// the playlist table
	@FXML
	private TableView<PlaylistTableView> playlistTable;
	@FXML
	private TableColumn<PlaylistTableView, String> columnPlaylistTime;
	@FXML
	private TableColumn<PlaylistTableView, String> columnPlaylistTitle;
	@FXML
	private TableColumn<PlaylistTableView, String> columnPlaylistArtist;
	@FXML
	private TableColumn<PlaylistTableView, String> columnPlaylistAuthor;
	@FXML
	private TableColumn<PlaylistTableView, String> columnPlaylistComment;

	// the tabs
	@FXML
	private TabPane tabView;
	@FXML
	private Tab musicVideoTableTab;
	@FXML
	private Tab playlistTab;
	@FXML
	private Tab sourceTab;

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
	/**
	 * music video file table context menu > rename file
	 */
	@FXML
	private MenuItem contextMusicVideoRename;

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

	// context menu playlist

	/**
	 * music video playlist table context menu > rename file
	 */
	@FXML
	private MenuItem contextPlaylistRemove;

	// music video playlist table context menu

	/**
	 * music video playlist table context menu > remove the current selected
	 * directory
	 */
	@FXML
	private MenuItem contextPlaylistEdit;
	/**
	 * music video playlist table context menu > clear the current selection
	 */
	@FXML
	private MenuItem contextPlaylistClear;
	/**
	 * music video playlist table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextPlaylistRefresh;

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
	/**
	 * Open the ignored files window button
	 */
	@FXML
	private Button buttonIgnoredFiles;

	@FXML
	private Button buttonSavePlaylist;
	@FXML
	private Button buttonLoadPlaylist;
	@FXML
	private Button buttonRefreshPlaylist;
	@FXML
	private Button buttonClearPlaylist;

	@FXML
	private MenuItem menuButtonSaveConfiguration;
	@FXML
	private MenuItem menuButtonLoadConfiguration;
	@FXML
	private MenuItem menuButtonSaveConfigurationCustom;
	@FXML
	private MenuItem menuButtonLoadConfigurationCustom;
	@FXML
	private MenuItem menuButtonResetConfiguration;

	@FXML
	private MenuItem menuButtonSftpStatic;
	@FXML
	private MenuItem menuButtonSftpSearch;
	@FXML
	private MenuItem menuButtonSftpParty;
	@FXML
	private MenuItem menuButtonSftpReset;
	@FXML
	private Menu menuButtonSftp;

	@FXML
	private CheckMenuItem menuButtonAlwaysSave;

	// other

	/**
	 * table data of the table with music video files
	 */
	private ObservableList<MusicVideoTableView> tableDataMusicVideo;

	/**
	 * table data of the table with the playlist
	 */
	private ObservableList<PlaylistTableView> tableDataPlaylist;

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
		 * Jakob from code.makery and initializes the directory path table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnPlaylistTime.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
		columnPlaylistTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		columnPlaylistArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		columnPlaylistAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		columnPlaylistComment.setCellValueFactory(cellData -> cellData.getValue().getCommentProperty());

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

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the playlist table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnFilePath.setCellValueFactory(cellData -> cellData.getValue().getFilePathProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		tableDataPlaylist = FXCollections.observableArrayList();
		FilteredList<PlaylistTableView> filteredDataParty = new FilteredList<>(tableDataPlaylist, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataParty.setPredicate(directoryPathObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (directoryPathObject.getTime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (directoryPathObject.getComment().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					// nothing matches
					return false;
				}
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<PlaylistTableView> sortedDataPlaylist = new SortedList<>(filteredDataParty);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataPlaylist.comparatorProperty().bind(playlistTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		playlistTable.setItems(sortedDataPlaylist);

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
		contextMusicVideoRename.setGraphic(WindowMethods.createMenuIcon("images/menu/rename.png"));
		contextPlaylistRemove.setGraphic(WindowMethods.createMenuIcon("images/menu/remove.png"));
		contextPlaylistEdit.setGraphic(WindowMethods.createMenuIcon("images/menu/rename.png"));
		contextPlaylistClear.setGraphic(WindowMethods.createMenuIcon("images/menu/clear.png"));
		contextPlaylistRefresh.setGraphic(WindowMethods.createMenuIcon("images/menu/refresh.png"));

		// other buttons
		buttonWrongFormattedFiles.setGraphic(WindowMethods.createMenuIcon("images/menu/wrongFormattedFiles.png"));
		buttonIgnoredFiles.setGraphic(WindowMethods.createMenuIcon("images/menu/ignore.png"));
		buttonAddDirectory.setGraphic(WindowMethods.createMenuIcon("images/menu/add.png"));
		networkButton.setGraphic(WindowMethods.createMenuIcon("images/menu/network.png"));
		youTubeButton.setGraphic(WindowMethods.createMenuIcon("images/menu/youTube.png"));
		aboutButton.setGraphic(WindowMethods.createMenuIcon("images/menu/about.png"));
		randomButton.setGraphic(WindowMethods.createMenuIcon("images/menu/random.png"));
		helpButton.setGraphic(WindowMethods.createMenuIcon("images/menu/help.png"));
		buttonLoadPlaylist.setGraphic(WindowMethods.createMenuIcon("images/menu/load.png"));
		buttonSavePlaylist.setGraphic(WindowMethods.createMenuIcon("images/menu/save.png"));
		buttonRefreshPlaylist.setGraphic(WindowMethods.createMenuIcon("images/menu/refresh.png"));
		buttonClearPlaylist.setGraphic(WindowMethods.createMenuIcon("images/menu/remove.png"));

		// menu buttons
		menuButtonWebsites.setGraphic(WindowMethods.createMenuIcon("images/menu/html_static.png"));
		menuButtonCsv.setGraphic(WindowMethods.createMenuIcon("images/menu/csv.png"));
		menuButtonJson.setGraphic(WindowMethods.createMenuIcon("images/menu/json.png"));
		menuButtonHtmlStatic.setGraphic(WindowMethods.createMenuIcon("images/menu/html_static.png"));
		menuButtonHtmlSearch.setGraphic(WindowMethods.createMenuIcon("images/menu/html_search.png"));
		menuButtonHtmlParty.setGraphic(WindowMethods.createMenuIcon("images/menu/html_playlist.png"));
		menuButtonSaveConfiguration.setGraphic(WindowMethods.createMenuIcon("images/menu/save.png"));
		menuButtonLoadConfiguration.setGraphic(WindowMethods.createMenuIcon("images/menu/load.png"));
		menuButtonSaveConfigurationCustom.setGraphic(WindowMethods.createMenuIcon("images/menu/save.png"));
		menuButtonLoadConfigurationCustom.setGraphic(WindowMethods.createMenuIcon("images/menu/load.png"));
		menuButtonResetConfiguration.setGraphic(WindowMethods.createMenuIcon("images/menu/reset.png"));

		// label
		searchLabel.setGraphic(WindowMethods.createMenuIcon("images/menu/search.png"));
	}

	public void setMainWindow(Main window) {
		this.mainWindow = window;

		refreshMusicVideoFileTable();
		refreshMusicVideoPathTable();
		refreshMusicVideoPlaylistTable();

		this.menuButtonAlwaysSave.setSelected(this.mainWindow.getMusicVideohandler().getAlwaysSave());
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

		// if the musicVideoTable is selected
		if (tabView.getSelectionModel().getSelectedItem() == musicVideoTableTab) {
			// select the top item
			this.musicVideoTable.getSelectionModel().select(0);

			// open the music video that is selected
			openSelectedVideoFile();

			// clear the selection
			this.musicVideoTable.getSelectionModel().clearSelection();
		} else if (tabView.getSelectionModel().getSelectedItem() == sourceTab) {
			// select the top item
			this.directoryPathTable.getSelectionModel().select(0);

			// open the music video that is selected
			showDirectoryInExplorerPathList();

			// clear the selection
			this.directoryPathTable.getSelectionModel().clearSelection();
		} else if (tabView.getSelectionModel().getSelectedItem() == playlistTab) {
			// select the top item
			this.playlistTable.getSelectionModel().select(0);

			// open the music video that is selected
			openTopMusicVideoPlaylist();

			// clear the selection
			this.playlistTable.getSelectionModel().clearSelection();
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

		// change the text in the search box
		if (tabView.getSelectionModel().getSelectedItem() == musicVideoTableTab) {
			this.searchBox.setPromptText("Search for music videos...");
		} else if (tabView.getSelectionModel().getSelectedItem() == sourceTab) {
			this.searchBox.setPromptText("Search for directories...");
		} else {
			this.searchBox.setPromptText("Search for playlist entries...");
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

		if (!this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {
			this.networkButton.setSelected(true);
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
						refreshMusicVideoPlaylistTable();
					}
				});

				// Connection to the Controller from the primary Stage
				ServerLoginWindowController aboutWindowController = loader.getController();
				aboutWindowController.setServerLoginWindow(this.mainWindow, stage);

				stage.show();
				checkNetwork();
				refreshMusicVideoPlaylistTable();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (Dialogs.yesNoDialog("Logout?", "Do you want to log out?",
					"You are already logged in. Click OK to logout.")) {
				this.mainWindow.getMusicVideohandler().sftpDisconnect();
				this.networkButton.setSelected(false);
			} else {
				this.networkButton.setSelected(true);
			}

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

			// Connection to the Controller from the primary Stage
			WrongFormattedFilesWindowController wrongWindowController = loader.getController();
			wrongWindowController.setWrongFormattedFilesWindow(this.mainWindow);

			// show the stage/window
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the wrong formatted files window
	 */
	@FXML
	private void openIgnoredFilesWindow() {

		try {
			// load the window from file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/IgnoredFilesWindow.fxml"));

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
			stage.setTitle("Ignored Music Video Files");

			// Connection to the Controller from the primary Stage
			IgnoredFilesWindowController wrongWindowController = loader.getController();
			wrongWindowController.setWrongFormattedFilesWindow(this.mainWindow, this);

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
			int a = selectedEntry.getIndex();

			String[] authorComment = Dialogs.playlistDialog("", "", "Create a new Playlist entry",
					"Add an author and comment", "Add to playlist");

			// String author = Dialogs.textInputDialog("Enter your name", "", "Enter your
			// name", "Your name:");
			// String comment = Dialogs.textInputDialog("Enter your comment", "", "Enter a
			// comment", "Your comment:");
			if (authorComment != null && authorComment[0] != null) {
				if (authorComment[1] == null) {
					authorComment[1] = "";
				}

				this.mainWindow.getMusicVideohandler().addMusicVideoToPlaylist(a, authorComment[0], authorComment[1]);
				refreshMusicVideoPlaylistTable();
			}

		}
	}

	/**
	 * Refresh the music video file table
	 */
	@FXML
	public void refreshMusicVideoFileTable() {

		// update the music video data
		this.mainWindow.getMusicVideohandler().updateMusicVideoList();

		// get music video data
		MusicVideo[] listOfVideos = this.mainWindow.getMusicVideohandler().getMusicVideoList();

		// clear table
		tableDataMusicVideo.clear();

		// add music video data
		if (listOfVideos != null) {
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
	public void refreshMusicVideoPathTable() {

		// get music video data
		Path[] listOfPaths = this.mainWindow.getMusicVideohandler().getPathList();

		// clear table
		tableDataDirectory.clear();

		// add music video data
		if (listOfPaths != null) {
			for (Path directory : listOfPaths) {
				tableDataDirectory.add(new DirectoryPathTableView(directory.toString()));
			}
		}
	}

	/**
	 * Refresh the music video playlist table
	 */
	@FXML
	public void refreshMusicVideoPlaylistTable() {

		if (this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {
			this.mainWindow.getMusicVideohandler().clearPlaylist();
			this.mainWindow.getMusicVideohandler().sftpRetrievePlaylist();
		}

		// get music video data
		MusicVideoPlaylistElement[] listOfEntries = this.mainWindow.getMusicVideohandler().getPlaylistHandler()
				.getPlaylistElements();

		// clear table
		tableDataPlaylist.clear();

		// add music video data
		if (listOfEntries != null) {
			int i = 0;
			for (MusicVideoPlaylistElement element : listOfEntries) {
				System.out.println("Refresh and add file by " + element.getAuthor());
				MusicVideo musicVideoFile = element.getMusicVideoFile();
				tableDataPlaylist.add(new PlaylistTableView(i, element.getMusicVideoIndex(),
						element.getUnixTimeString(), musicVideoFile.getTitle(), musicVideoFile.getArtist(),
						element.getAuthor(), element.getComment()));
				i++;
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

			MusicVideo selectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1];
			this.mainWindow.getMusicVideohandler().addIgnoredFileToIgnoredFilesList(selectedFile.getPath());
		}

		// update the music video list after this
		refreshMusicVideoFileTable();
	}

	/**
	 * Add network: Check if a working network connection is established
	 */
	private void checkNetwork() {
		if (this.mainWindow.getMusicVideohandler().sftpConnectionEstablished()) {
			this.networkButton.setSelected(true);
			this.mainWindow.getMusicVideohandler().sftpRetrievePlaylist();
			// update the table
			refreshMusicVideoPlaylistTable();
		} else {
			this.networkButton.setSelected(false);
		}

	}

	/**
	 * Export the music video list as static HTML table
	 */
	@FXML
	private void exportHtmlStatic() {
		File htmlFileDestination = Dialogs.chooseDirectory(this.mainWindow.getPrimaryStage(),
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

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
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

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
				"Export music video list to static HTML table - Choose a directory",
				FileSystems.getDefault().getPath(".").toFile());

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
				"Export music video list to a CSV file - Choose a directory and filename",
				FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { csvFilter },
				Dialogs.CHOOSE_ACTION.SAVE);

		if (csvFile != null && csvFile[0] != null) {
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
				"Export music video list to a JSON file - Choose a directory and filename",
				FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
				Dialogs.CHOOSE_ACTION.SAVE);

		if (jsonFile != null && jsonFile[0] != null) {
			this.mainWindow.getMusicVideohandler().saveJson(jsonFile[0].toPath());
		}
	}

	@FXML
	private void saveConfiguartion() {
		this.mainWindow.getMusicVideohandler().saveSettingsToFile();
	}

	@FXML
	private void loadConfiguartion() {
		this.mainWindow.getMusicVideohandler().loadSettingsFromFile();
		refreshMusicVideoFileTable();
		refreshMusicVideoPathTable();
	}

	@FXML
	private void saveConfiguartionCustom() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(),
				"Save a Custom Named Configuration File", FileSystems.getDefault().getPath(".").toFile(),
				new ExtensionFilter[] { jsonFilter }, Dialogs.CHOOSE_ACTION.SAVE);
		if (jsonFile != null && jsonFile[0] != null) {
			File saveToThis = Paths.get(jsonFile[0].getAbsolutePath() + ".json").toFile();
			this.mainWindow.getMusicVideohandler().saveSettings(saveToThis);
		}
	}

	@FXML
	private void loadConfiguartionCustom() {
		ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
		File[] jsonFile = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(),
				"Load a Custom Named Configuration File", FileSystems.getDefault().getPath(".").toFile(),
				new ExtensionFilter[] { jsonFilter }, Dialogs.CHOOSE_ACTION.NORMAL);
		if (jsonFile != null && jsonFile[0] != null) {
			this.mainWindow.getMusicVideohandler().loadSettings(jsonFile[0]);
		}
		refreshMusicVideoFileTable();
		refreshMusicVideoPathTable();
	}

	@FXML
	private void resetConfiguartion() {
		if (Dialogs.yesNoDialog("Confirm to Continue", "Reset Everything", "Do you really want to reset EVERYTHING?")) {
			this.mainWindow.getMusicVideohandler().reset();
			refreshMusicVideoFileTable();
			refreshMusicVideoPathTable();
			refreshMusicVideoPlaylistTable();
		}

	}

	@FXML
	public void openGitHubHelpLink() {
		ExternalApplicationHandler.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	@FXML
	private void renameFile() {

		// get the currently selected entry in the table
		MusicVideoTableView selectedEntry = musicVideoTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			Path pathOfSelectedFile = this.mainWindow.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1].getPath();
			// check if the file really exists and isn't a directory
			File selectedFile = pathOfSelectedFile.toFile();
			if (selectedFile.exists() && selectedFile.isFile()) {
				// show dialog to rename the file
				String a = Dialogs.textInputDialog("Rename wrong formatted file", selectedFile.getName(),
						"Rename the file", "Enter a new name:");
				if (a != null) {
					FileReadWriteModule.rename(selectedFile,
							Paths.get(selectedFile.getParentFile().getAbsolutePath() + "/" + a).toFile());
					refreshMusicVideoFileTable();
				}
			}
		}

	}

	@FXML
	private void openMusicVideoPlaylistFileLeftClick() {
		if (this.leftMouseKeyWasPressed) {
			openTopMusicVideoPlaylist();
		}
	}

	private void openTopMusicVideoPlaylist() {

		// get the currently selected entry in the table
		PlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainWindow.getMusicVideohandler().openMusicVideo(selectedEntry.getMusicVideoIndex() - 1);
		}

	}

	@FXML
	private void savePlaylistDialog() {
		if (this.tableDataPlaylist.isEmpty()) {
			Dialogs.informationAlert("Operation failed", "There is no playlist!", AlertType.ERROR);
		} else {
			ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
			File[] fileName = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(), "Save current playlist",
					FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
					Dialogs.CHOOSE_ACTION.SAVE);
			if (fileName != null && fileName[0] != null) {
				File realFileName = new File(fileName[0].getParent() + "/" + fileName[0].getName());
				this.tableDataPlaylist.clear();
				this.refreshMusicVideoPlaylistTable();
				this.mainWindow.getMusicVideohandler().savePlaylist(realFileName);
			}
		}

	}

	@FXML
	private void loadPlaylistDialog() {
		if (this.tableDataPlaylist.isEmpty() || Dialogs.yesNoDialog("This action has consequences",
				"Do you really want to clear your current playlist?",
				"The current playlist will be cleared if you continue")) {
			ExtensionFilter jsonFilter = new ExtensionFilter("Json File", "*.json");
			File[] fileName = Dialogs.chooseFile(this.mainWindow.getPrimaryStage(), "Load a saved playlist",
					FileSystems.getDefault().getPath(".").toFile(), new ExtensionFilter[] { jsonFilter },
					Dialogs.CHOOSE_ACTION.NORMAL);
			if (fileName != null && fileName[0] != null) {
				this.mainWindow.getMusicVideohandler().loadPlaylist(fileName[0]);
			}
			refreshMusicVideoPlaylistTable();
		}
	}

	@FXML
	private void clearSelectionPlaylistTable() {
		this.playlistTable.getSelectionModel().clearSelection();
	}

	@FXML
	private void removeEntry() {
		// get the currently selected entry in the table
		PlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainWindow.getMusicVideohandler().removeEntryFromPlaylist(selectedEntry.getIndex());
		}
		refreshMusicVideoPlaylistTable();

	}

	@FXML
	private void editEntry() {
		// get the currently selected entry in the table
		PlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			String[] authorComment = Dialogs.playlistEditDialog(selectedEntry.getAuthor(), selectedEntry.getComment(),
					"Edit the selected Playlist entry", "Edit author and comment", "Save Changes");
			// String author = Dialogs.textInputDialog("Enter new name", "", "Enter a new
			// name", "New name:");
			// String comment = Dialogs.textInputDialog("Enter new comment", "", "Enter a
			// new comment", "New comment:");
			if (authorComment != null && authorComment[0] != null) {
				if (authorComment[1] == null) {
					authorComment[1] = "";
				}
				this.mainWindow.getMusicVideohandler().editMusicVideoToPlaylist(selectedEntry.getIndex(),
						authorComment[0], authorComment[1]);
				refreshMusicVideoPlaylistTable();
			}

		}
	}

	@FXML
	private void sftpReset() {
		// TODO
	}

	@FXML
	private void sftpParty() {
		// TODO
	}

	@FXML
	private void sftpStatic() {
		// TODO
	}

	@FXML
	private void sftpSearch() {
		// TODO
	}

	@FXML
	public void changeAlwaysSave() {
		System.out.println(this.mainWindow.getMusicVideohandler());
		this.menuButtonAlwaysSave.setSelected(this.mainWindow.getMusicVideohandler()
				.setAlwaysSave(!this.mainWindow.getMusicVideohandler().getAlwaysSave()));
		System.out.println(this.mainWindow.getMusicVideohandler());
	}

	@FXML
	private void clearMusicVideoPlaylistTable() {
		if (!this.tableDataPlaylist.isEmpty()
				&& Dialogs.yesNoDialog("Clear the playlist", "Do you really want to clear the playlist?",
						"If you enter yes all playlist elements will be removed!")) {
			this.mainWindow.getMusicVideohandler().getPlaylistHandler().reset();
			this.tableDataPlaylist.clear();
		}

	}

}
