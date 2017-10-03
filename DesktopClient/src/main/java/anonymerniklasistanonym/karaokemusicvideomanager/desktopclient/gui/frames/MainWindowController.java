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
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainWindowController {

	// Views
	@FXML
	private Label searchLabel;
	@FXML
	private TextField searchBox;

	@FXML
	private Menu exportMenu;

	// the music video table
	@FXML
	private TableView<MusicVideoTableView> musicVideoTable;
	@FXML
	private TableColumn<MusicVideoTableView, Number> columnIndex;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnArtist;
	@FXML
	private TableColumn<MusicVideoTableView, String> columnTitle;

	private ObservableList<MusicVideoTableView> tableDataMusicVideo = FXCollections.observableArrayList();

	// the directory path table
	@FXML
	private TableView<DirectoryPathTableView> directoryPathTable;
	@FXML
	private TableColumn<DirectoryPathTableView, String> columnFilePath;

	private ObservableList<DirectoryPathTableView> tableDataDirectory = FXCollections.observableArrayList();

	// the tabs
	@FXML
	private TabPane tabView;
	@FXML
	private Tab musicVideoTableTab;

	// context menu
	@FXML
	private MenuItem contextMenuPlay;
	@FXML
	private MenuItem contextMenuPlaylist;
	@FXML
	private MenuItem contextMenuDirectory;

	// menu bar
	@FXML
	private MenuItem aboutButton;
	@FXML
	private MenuItem helpButton;

	// network button
	@FXML
	private ToggleButton networkButton;

	// Normal buttons
	@FXML
	private Button youTubeButton;
	@FXML
	private Button randomButton;
	@FXML
	private Button addPathButton;
	@FXML
	private Button removePathButton;
	@FXML
	private Button showInExplorerButton;

	// Mouse key monitoring
	private boolean leftMouseKeyWasPressed;

	public Main mainWindow;

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
		contextMenuPlay.setGraphic(createMenuIcon("images/menu/play.png"));
		contextMenuPlaylist.setGraphic(createMenuIcon("images/menu/playlist.png"));
		contextMenuDirectory.setGraphic(createMenuIcon("images/menu/directory.png"));

		// network button
		networkButton.setGraphic(createMenuIcon("images/menu/network.png"));
		youTubeButton.setGraphic(createMenuIcon("images/menu/youTube.png"));
		aboutButton.setGraphic(createMenuIcon("images/menu/about.png"));
		randomButton.setGraphic(createMenuIcon("images/menu/random.png"));
		addPathButton.setGraphic(createMenuIcon("images/menu/add.png"));
		removePathButton.setGraphic(createMenuIcon("images/menu/remove.png"));
		showInExplorerButton.setGraphic(createMenuIcon("images/menu/directory.png"));

		// label
		searchLabel.setGraphic(createMenuIcon("images/menu/search.png"));
	}

	private ImageView createMenuIcon(String pathToImage) {
		// int imageSize = 15;
		Image userIcon = new Image(ClassResourceReaderModule.getInputStream(pathToImage));
		ImageView userView = new ImageView(userIcon);
		// userView.setFitWidth(imageSize);
		// userView.setFitHeight(imageSize);
		return userView;
	}

	public void setMainWindow(Main window) {
		this.mainWindow = window;

		updateMusicVideoListTable();
		updateDirectoryPathTable();

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
			this.mainWindow.musicVideohandler.openMusicVideo(selectedEntry.getIndex() - 1);
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
	public void mousePressed(MouseEvent e) {
		leftMouseKeyWasPressed = false;

		if (e.isPrimaryButtonDown()) {
			leftMouseKeyWasPressed = true;
			System.out.println("Primary button");
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	public void openTopMusicVideoFileLeftClick() {
		if (leftMouseKeyWasPressed == true) {
			openSelectedVideoFile();
		}
	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	public void openTopMusicVideoFile() {

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
			showInExplorer();

			// clear the selection
			this.directoryPathTable.getSelectionModel().clearSelection();
		}
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
	 * Clears the current selection
	 */
	@FXML
	private void unSelectDirectoryPath() {
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
			updateDirectoryPathTable();

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

	private void checkNetwork() {
		// TODO Auto-generated method stub
		this.networkButton.setSelected(false);

	}

	/**
	 * Update the music video table in the window with the current music video list
	 */
	public void updateMusicVideoListTable() {
		// get music video data
		MusicVideo[] listOfVideos = this.mainWindow.musicVideohandler.getMusicVideoList();

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
	 * Update the directory path table in the window with the current path list
	 */
	public void updateDirectoryPathTable() {
		// get music video data
		Path[] listOfPaths = this.mainWindow.musicVideohandler.getPathList();

		// add music video data
		if (listOfPaths != null) {
			tableDataDirectory.clear();
			for (int i = 0; i < listOfPaths.length; i++) {
				tableDataDirectory.add(new DirectoryPathTableView(listOfPaths[i].toString()));
			}
		}
	}

	@FXML
	public void removeDirectory() {
		// ge the currently selected entry
		DirectoryPathTableView selectedEntry = this.directoryPathTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			this.mainWindow.musicVideohandler.removeFromPathList(selectedEntry.getFilePath());
			updateDirectoryPathTable();
			updateMusicVideoListTable();
		}
	}

	@FXML
	public void showInExplorer() {

		// get the currently selected entry
		DirectoryPathTableView selectedEntry = this.directoryPathTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			ExternalApplicationHandler.openFile(Paths.get(selectedEntry.getFilePath()).toFile());
		}
	}

	@FXML
	public void addVideoToPlaylist() {
		System.out.println("Later");
	}

	@FXML
	public void showDirectoryOfFileInExplorer() {

		// get the currently selected entry
		MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		System.out.println("hi");
		// if entry isn't null
		if (selectedEntry != null) {
			Path filePath = this.mainWindow.musicVideohandler.getMusicVideoList()[selectedEntry.getIndex() - 1]
					.getPath();

			// open the music video file with the index
			File selectedFile = filePath.toAbsolutePath().toFile();
			System.out.println("selectedFile: " + selectedFile.getAbsolutePath());
			if (selectedFile.exists() && !selectedFile.isDirectory()) {
				System.out.println("hi 3");
				ExternalApplicationHandler.openFile(selectedFile.getParentFile());
			}

		}

	}

}
