package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoPlaylistTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoSourceDirectoriesTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.MusicVideoTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.DialogHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private TableView<MusicVideoSourceDirectoriesTableView> directoryPathTable;
	@FXML
	private TableColumn<MusicVideoSourceDirectoriesTableView, String> columnFilePath;

	// the playlist table
	@FXML
	private TableView<MusicVideoPlaylistTableView> playlistTable;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistTime;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistTitle;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistArtist;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistAuthor;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, String> columnPlaylistComment;
	@FXML
	private TableColumn<MusicVideoPlaylistTableView, Number> columnPlaylistVotes;

	// the tabs

	/**
	 * The tab of all tabs
	 */
	@FXML
	private TabPane tabView;
	/**
	 * The music video list table tab
	 */
	@FXML
	private Tab musicVideoListTab;
	/**
	 * The music video playlist table tab
	 */
	@FXML
	private Tab musicVideoPlaylistTab;
	/**
	 * The music video source folder table tab
	 */
	@FXML
	private Tab musicVideoSourceFolderTab;

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
	/**
	 * music video playlist table context menu > rename file
	 */
	@FXML
	private MenuItem contextPlaylistVotes;
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
	/**
	 * music video playlist table context menu > add removed music video entry to
	 * the table
	 */
	@FXML
	private MenuItem contextPlaylistUndo;

	// Buttons

	/**
	 * Network toggle button
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
	/**
	 * Save the current playlist button
	 */
	@FXML
	private Button buttonSavePlaylist;
	/**
	 * Load a playlist button
	 */
	@FXML
	private Button buttonLoadPlaylist;
	/**
	 * Clear the current playlist button
	 */
	@FXML
	private Button buttonClearPlaylist;

	// Menu bar

	/**
	 * Export music video list menu
	 */
	@FXML
	private Menu menuExport;
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
	 * Save the current configuration
	 */
	@FXML
	private MenuItem menuButtonSaveConfiguration;
	/**
	 * Load a configuration
	 */
	@FXML
	private MenuItem menuButtonLoadConfiguration;
	/**
	 * Save the current configuration custom (with dialog)
	 */
	@FXML
	private MenuItem menuButtonSaveConfigurationCustom;
	/**
	 * Load a configuration custom (with dialog)
	 */
	@FXML
	private MenuItem menuButtonLoadConfigurationCustom;
	/**
	 * Reset everything
	 */
	@FXML
	private MenuItem menuButtonResetConfiguration;

	/**
	 * Network menu
	 */
	@FXML
	private Menu menuNetwork;
	/**
	 * Setup menu for the SFTP server connection -> formats to export on expand
	 */
	@FXML
	private Menu menuButtonSftp;
	/**
	 * Export static music video list to server
	 */
	@FXML
	private MenuItem menuButtonSftpStatic;
	/**
	 * Export static music video list to server with search
	 */
	@FXML
	private MenuItem menuButtonSftpSearch;
	/**
	 * Export static music video list to server with search and playlist
	 */
	@FXML
	private MenuItem menuButtonSftpParty;
	/**
	 * Export static music video list to server with search and playlist without
	 * voting
	 */
	@FXML
	private MenuItem menuButtonSftpPartyWithoutVotes;
	/**
	 * Reset saved server settings
	 */
	@FXML
	private MenuItem menuButtonSftpReset;
	/**
	 * Reset server voting playlist
	 */
	@FXML
	private MenuItem menuButtonSftpVotingReset;
	/**
	 * Open website of connected/last connected IP address
	 */
	@FXML
	private MenuItem menuButtonOpenWebsite;
	/**
	 * Open website of connected/last connected IP address with auto updates
	 */
	@FXML
	private MenuItem menuButtonOpenWebsiteUpdate;

	/**
	 * Configuration menu (settings save/load)
	 */
	@FXML
	private Menu menuConfiguration;

	/**
	 * Additional settings menu
	 */
	@FXML
	private Menu menuSettings;
	/**
	 * Save current settings on save
	 */
	@FXML
	private CheckMenuItem menuButtonAlwaysSave;
	/**
	 * Remove a started video from the playlist
	 */
	@FXML
	private CheckMenuItem menuButtonPlaylistRemove;

	/**
	 * About this program menu
	 */
	@FXML
	private Menu menuAbout;
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

	// other

	/**
	 * table data of the table with music video files
	 */
	private ObservableList<MusicVideoTableView> tableDataMusicVideo;

	/**
	 * table data of the table with the playlist
	 */
	private ObservableList<MusicVideoPlaylistTableView> tableDataPlaylist;

	/**
	 * table data of the table with music video file directories
	 */
	private ObservableList<MusicVideoSourceDirectoriesTableView> tableDataDirectory;

	/**
	 * get if the last mouse key that was pressed was the left mouse key
	 */
	private boolean leftMouseKeyWasPressed;

	/**
	 * Last inputed name and to begin the user name
	 */
	private String nameOfAuthor;

	/**
	 * Main class
	 */
	private Main mainClass;
	/**
	 * Window text that should be translated on language change/load
	 */
	private void translateText() {

		// search
		this.searchLabel.setText(Internationalization.translate("Search") + ":");

		// table columns
		this.columnArtist.setText(Internationalization.translate("Artist"));
		this.columnTitle.setText(Internationalization.translate("Title"));

		this.columnPlaylistTime.setText(Internationalization.translate("Time"));
		this.columnPlaylistTitle.setText(Internationalization.translate("Title"));
		this.columnPlaylistArtist.setText(Internationalization.translate("Artist"));
		this.columnPlaylistAuthor.setText(Internationalization.translate("Author"));
		this.columnPlaylistComment.setText(Internationalization.translate("Comment"));
		this.columnPlaylistVotes.setText(Internationalization.translate("Votes"));

		this.columnFilePath.setText(Internationalization.translate("Directory path"));

		// context menu
		this.contextMusicVideoPlaylist.setText(Internationalization.translate("Add to playlist"));
		this.contextMusicVideoDirectory.setText(Internationalization.translate("Show Directory of File"));
		this.contextMusicVideoIgnore.setText(Internationalization.translate("Ignore File"));
		this.contextMusicVideoClear.setText(Internationalization.translate("Clear Selection"));
		this.contextMusicVideoRefresh.setText(Internationalization.translate("Refresh"));
		this.contextMusicVideoRename.setText(Internationalization.translate("Rename File"));

		this.contextPathRemove.setText(Internationalization.translate("Remove directory"));
		this.contextPathClear.setText(Internationalization.translate("Clear Selection"));
		this.contextPathRefresh.setText(Internationalization.translate("Refresh"));

		this.contextPlaylistRemove.setText(Internationalization.translate("Remove playlist entry"));
		this.contextPlaylistEdit.setText(Internationalization.translate("Edit playlist entry"));
		this.contextPlaylistVotes.setText(Internationalization.translate("Set votes"));
		this.contextPlaylistClear.setText(Internationalization.translate("Clear Selection"));
		this.contextPlaylistRefresh.setText(Internationalization.translate("Refresh"));
		this.contextPlaylistUndo.setText(Internationalization.translate("Add removed playlist entry"));

		// buttons
		this.networkButton.setText(Internationalization.translate("Network"));
		this.randomButton.setText(Internationalization.translate("Random"));

		this.buttonLoadPlaylist.setText(Internationalization.translate("Load playlist"));
		this.buttonSavePlaylist.setText(Internationalization.translate("Save playlist"));
		this.buttonClearPlaylist.setText(Internationalization.translate("Clear playlist"));

		this.buttonWrongFormattedFiles.setText(Internationalization.translate("Wrong Formatted Files"));
		this.buttonIgnoredFiles.setText(Internationalization.translate("Ignored Files"));
		this.buttonAddDirectory.setText(Internationalization.translate("Add directory"));

		// menu bar
		this.menuExport.setText(Internationalization.translate("Export"));
		this.menuButtonWebsites.setText(Internationalization.translate("Websites"));
		this.menuButtonHtmlStatic.setText(Internationalization.translate("Static website"));
		this.menuButtonHtmlSearch.setText(Internationalization.translate("Searchable website"));
		this.menuButtonHtmlParty.setText(Internationalization.translate("Party website"));

		this.menuConfiguration
				.setText(Internationalization.translate("Save") + " & " + Internationalization.translate("Restore"));
		this.menuButtonSaveConfiguration.setText(Internationalization.translate("Save configuration"));
		this.menuButtonLoadConfiguration.setText(Internationalization.translate("Load configuration"));
		this.menuButtonSaveConfigurationCustom.setText(
				Internationalization.translate("Save configuration") + " " + Internationalization.translate("custom"));
		this.menuButtonLoadConfigurationCustom.setText(
				Internationalization.translate("Load configuration") + " " + Internationalization.translate("custom"));
		this.menuButtonResetConfiguration.setText(Internationalization.translate("Reset configuration"));

		this.menuNetwork.setText(Internationalization.translate("Network"));
		this.menuButtonSftp.setText(Internationalization.translate("Setup the server"));
		this.menuButtonSftpVotingReset.setText(Internationalization.translate("Voting reset"));
		this.menuButtonSftpReset.setText(Internationalization.translate("Reset network configuration"));
		this.menuButtonSftpStatic.setText(Internationalization.translate("Static website"));
		this.menuButtonSftpSearch.setText(Internationalization.translate("Searchable website"));
		this.menuButtonSftpParty.setText(Internationalization.translate("Party website"));
		this.menuButtonSftpPartyWithoutVotes.setText(Internationalization.translate("Party website") + " ("
				+ Internationalization.translate("no votes") + ")");

		this.menuButtonOpenWebsite.setText(Internationalization.translate("Open website"));
		this.menuButtonOpenWebsiteUpdate.setText(Internationalization.translate("Open website") + " ("
				+ Internationalization.translate("playlist auto update") + ")");

		this.menuSettings.setText(Internationalization.translate("Settings"));
		this.menuButtonAlwaysSave.setText(Internationalization.translate("Always save Settings on Exit"));
		this.menuButtonPlaylistRemove.setText(Internationalization.translate("Remove playlist entry after playing it"));

		this.menuAbout.setText(Internationalization.translate("About"));
		this.aboutButton.setText(Internationalization.translate("About"));
		this.helpButton.setText(Internationalization.translate("Help"));

		// tabs
		this.musicVideoListTab.setText(Internationalization.translate("Music Video List"));
		this.musicVideoPlaylistTab.setText(Internationalization.translate("Music Video Playlist"));
		this.musicVideoSourceFolderTab.setText(Internationalization.translate("Source Directories"));

	}

	@FXML
	private void initialize() {

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the music video table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		this.columnIndex.setCellValueFactory(cellData -> cellData.getValue().getIndexProperty());
		this.columnArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		this.columnTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		this.tableDataMusicVideo = FXCollections.observableArrayList();
		final FilteredList<MusicVideoTableView> filteredData = new FilteredList<>(this.tableDataMusicVideo, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		this.searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(musicVideoObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				final String lowerCaseFilter = newValue.toLowerCase();

				// try to recognize numbers and find them in the search too
				try {
					final int number = Integer.parseInt(newValue);

					if (musicVideoObject.getIndex() == number) {
						return true;
					}

				} catch (Exception e) {

				}

				if (musicVideoObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (musicVideoObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		final SortedList<MusicVideoTableView> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(this.musicVideoTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		this.musicVideoTable.setItems(sortedData);

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the directory path table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		this.columnFilePath.setCellValueFactory(cellData -> cellData.getValue().getFilePathProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		this.tableDataDirectory = FXCollections.observableArrayList();
		final FilteredList<MusicVideoSourceDirectoriesTableView> filteredDataDirectory = new FilteredList<>(
				this.tableDataDirectory, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		this.searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataDirectory.setPredicate(directoryPathObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				final String lowerCaseFilter = newValue.toLowerCase();

				if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				} else if (directoryPathObject.getFilePath().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		final SortedList<MusicVideoSourceDirectoriesTableView> sortedDataDirectory = new SortedList<>(
				filteredDataDirectory);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataDirectory.comparatorProperty().bind(this.directoryPathTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		this.directoryPathTable.setItems(sortedDataDirectory);

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the playlist table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		this.columnPlaylistTime.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
		this.columnPlaylistTitle.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		this.columnPlaylistArtist.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		this.columnPlaylistAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
		this.columnPlaylistComment.setCellValueFactory(cellData -> cellData.getValue().getCommentProperty());
		this.columnPlaylistVotes.setCellValueFactory(cellData -> cellData.getValue().getVotesProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		this.tableDataPlaylist = FXCollections.observableArrayList();
		FilteredList<MusicVideoPlaylistTableView> filteredDataParty = new FilteredList<>(this.tableDataPlaylist,
				p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		this.searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataParty.setPredicate(playlistEntryObject -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (Integer.toString(playlistEntryObject.getVotes()).contains(lowerCaseFilter)) {
					return true;
				} else if (playlistEntryObject.getTime().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (playlistEntryObject.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (playlistEntryObject.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (playlistEntryObject.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (playlistEntryObject.getComment().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					// nothing matches
					return false;
				}
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicVideoPlaylistTableView> sortedDataPlaylist = new SortedList<>(filteredDataParty);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataPlaylist.comparatorProperty().bind(this.playlistTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		this.playlistTable.setItems(sortedDataPlaylist);

		// set menu icons
		this.searchLabel.setGraphic(WindowModule.createMenuIcon("search"));

		this.contextMusicVideoPlaylist.setGraphic(WindowModule.createMenuIcon("playlist"));
		this.contextMusicVideoDirectory.setGraphic(WindowModule.createMenuIcon("directory"));
		this.contextMusicVideoIgnore.setGraphic(WindowModule.createMenuIcon("ignore"));
		this.contextMusicVideoClear.setGraphic(WindowModule.createMenuIcon("clear"));
		this.contextMusicVideoRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));
		this.contextMusicVideoRename.setGraphic(WindowModule.createMenuIcon("rename"));

		this.contextPathRemove.setGraphic(WindowModule.createMenuIcon("remove"));
		this.contextPathClear.setGraphic(WindowModule.createMenuIcon("clear"));
		this.contextPathRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));

		this.contextPlaylistRemove.setGraphic(WindowModule.createMenuIcon("remove"));
		this.contextPlaylistEdit.setGraphic(WindowModule.createMenuIcon("rename"));
		this.contextPlaylistVotes.setGraphic(WindowModule.createMenuIcon("upvote"));
		this.contextPlaylistClear.setGraphic(WindowModule.createMenuIcon("clear"));
		this.contextPlaylistRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));
		this.contextPlaylistUndo.setGraphic(WindowModule.createMenuIcon("add"));

		this.networkButton.setGraphic(WindowModule.createMenuIcon("network"));
		this.youTubeButton.setGraphic(WindowModule.createMenuIcon("youTube"));
		this.randomButton.setGraphic(WindowModule.createMenuIcon("random"));

		this.buttonWrongFormattedFiles.setGraphic(WindowModule.createMenuIcon("wrongFormattedFiles"));
		this.buttonIgnoredFiles.setGraphic(WindowModule.createMenuIcon("ignore"));
		this.buttonAddDirectory.setGraphic(WindowModule.createMenuIcon("add"));

		this.buttonLoadPlaylist.setGraphic(WindowModule.createMenuIcon("load"));
		this.buttonSavePlaylist.setGraphic(WindowModule.createMenuIcon("save"));
		this.buttonClearPlaylist.setGraphic(WindowModule.createMenuIcon("remove"));

		this.menuButtonWebsites.setGraphic(WindowModule.createMenuIcon("html_static"));
		this.menuButtonCsv.setGraphic(WindowModule.createMenuIcon("csv"));
		this.menuButtonJson.setGraphic(WindowModule.createMenuIcon("json"));
		this.menuButtonHtmlStatic.setGraphic(WindowModule.createMenuIcon("html_static"));
		this.menuButtonHtmlSearch.setGraphic(WindowModule.createMenuIcon("html_search"));
		this.menuButtonHtmlParty.setGraphic(WindowModule.createMenuIcon("html_playlist"));

		this.menuButtonSaveConfiguration.setGraphic(WindowModule.createMenuIcon("save"));
		this.menuButtonLoadConfiguration.setGraphic(WindowModule.createMenuIcon("load"));
		this.menuButtonSaveConfigurationCustom.setGraphic(WindowModule.createMenuIcon("save"));
		this.menuButtonLoadConfigurationCustom.setGraphic(WindowModule.createMenuIcon("load"));
		this.menuButtonResetConfiguration.setGraphic(WindowModule.createMenuIcon("reset"));

		this.menuButtonSftp.setGraphic(WindowModule.createMenuIcon("network"));
		this.menuButtonSftpVotingReset.setGraphic(WindowModule.createMenuIcon("reset"));
		this.menuButtonSftpReset.setGraphic(WindowModule.createMenuIcon("remove"));
		this.menuButtonSftpStatic.setGraphic(WindowModule.createMenuIcon("html_static"));
		this.menuButtonSftpSearch.setGraphic(WindowModule.createMenuIcon("html_search"));
		this.menuButtonSftpParty.setGraphic(WindowModule.createMenuIcon("html_playlist"));
		this.menuButtonSftpPartyWithoutVotes.setGraphic(WindowModule.createMenuIcon("html_playlist"));

		this.menuButtonOpenWebsite.setGraphic(WindowModule.createMenuIcon("html_static"));
		this.menuButtonOpenWebsiteUpdate.setGraphic(WindowModule.createMenuIcon("html_playlist"));

		this.aboutButton.setGraphic(WindowModule.createMenuIcon("about"));
		this.helpButton.setGraphic(WindowModule.createMenuIcon("help"));

		// disable some buttons on start
		this.menuButtonSftp.setDisable(true);
		this.menuButtonSftpVotingReset.setDisable(true);
		this.contextPlaylistUndo.setVisible(false);

		// set last name to current user name
		this.nameOfAuthor = System.getProperty("user.name");

		// translate the text in the main window
		translateText();

	}

	/**
	 * Connect Main class with this class
	 * 
	 * @param mainClass
	 *            (Main)
	 */
	public void setMainWindow(Main mainClass) {
		this.mainClass = mainClass;

		refreshMusicVideoTableWithoutUpdate();
		refreshMusicVideoDirectoryTable();
		refreshMusicVideoPlaylistTable();

		this.menuButtonAlwaysSave.setSelected(this.mainClass.getMusicVideohandler().getAlwaysSave());
		this.menuButtonPlaylistRemove
				.setSelected(this.mainClass.getMusicVideohandler().getPlaylistRemoveStartedVideo());

		// select source path tab if there are no music videos
		if (this.mainClass.getMusicVideohandler().getMusicVideoList() == null) {
			tabView.getSelectionModel().select(this.musicVideoSourceFolderTab);
		}
	}

	/**
	 * Do the following when a row is selected/clicked with the mouse
	 */
	@FXML
	private void openSelectedVideoFile() {

		// get the selected tab
		final Tab selectedTab = tabView.getSelectionModel().getSelectedItem();

		if (selectedTab == musicVideoListTab) {

			// get the currently selected entry
			final MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

			// if entry isn't null
			if (selectedEntry != null) {
				// open the music video file with the index
				this.mainClass.getMusicVideohandler().openMusicVideo(selectedEntry.getIndex() - 1);
			} else {
				// search on YouTube
				searchOnYouTube();
			}

		} else if (selectedTab == musicVideoPlaylistTab) {

			// select the top item
			this.playlistTable.getSelectionModel().select(0);

			// open the selected item externally
			if (!openSelectedPlaylistVideoFile()) {
				// search on YouTube
				searchOnYouTube();
			}

			// clear the selection
			this.playlistTable.getSelectionModel().clearSelection();

		} else if (selectedTab == musicVideoSourceFolderTab) {

			// select the top item
			this.directoryPathTable.getSelectionModel().select(0);

			// open the selected directory entry externally
			if (!showSelectedDirectoryInExplorer()) {
				// search on YouTube
				searchOnYouTube();
			}

			// clear the selection
			this.directoryPathTable.getSelectionModel().clearSelection();
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

		this.leftMouseKeyWasPressed = false;

		if (e.isPrimaryButtonDown()) {
			this.leftMouseKeyWasPressed = true;
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openMusicVideoFileLeftClick() {
		if (this.leftMouseKeyWasPressed == true) {
			openSelectedVideoFile();

			// update playlist
			refreshMusicVideoPlaylistTable();
		}
	}

	/**
	 * Open a video file only when the left mouse key was clicked
	 */
	@FXML
	private void openDirectoryLeftClick() {
		if (this.leftMouseKeyWasPressed == true) {
			showSelectedDirectoryInExplorer();
		}
	}

	/**
	 * Open on enter the video file that is on the top of the table
	 */
	@FXML
	private void openTopMusicVideoFile() {

		// get the selected tab
		final Tab selectedTab = tabView.getSelectionModel().getSelectedItem();

		if (selectedTab == musicVideoListTab) {

			// select the top item
			this.musicVideoTable.getSelectionModel().selectFirst();

			// open the selected item externally
			openSelectedVideoFile();

			// clear the selection
			this.musicVideoTable.getSelectionModel().clearSelection();

		} else if (selectedTab == musicVideoPlaylistTab) {

			// select the top item
			this.playlistTable.getSelectionModel().select(0);

			// open the selected item externally
			openSelectedPlaylistVideoFile();

			// clear the selection
			this.playlistTable.getSelectionModel().clearSelection();

		} else if (selectedTab == musicVideoSourceFolderTab) {

			// select the top item
			this.directoryPathTable.getSelectionModel().select(0);

			// open the selected directory entry externally
			showSelectedDirectoryInExplorer();

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

		// clear the text in the search box
		this.searchBox.setText("");

		// get the selected tab
		final Tab selectedTab = tabView.getSelectionModel().getSelectedItem();

		// change the text in the search box respective to the selected tab
		if (selectedTab == musicVideoListTab) {
			this.searchBox.setPromptText(Internationalization.translate("Search for") + " "
					+ Internationalization.translate("music videos") + "...");
		} else if (selectedTab == musicVideoPlaylistTab) {
			this.searchBox.setPromptText(Internationalization.translate("Search for") + " "
					+ Internationalization.translate("playlist entries") + "...");
		} else if (selectedTab == musicVideoSourceFolderTab) {
			this.searchBox.setPromptText(Internationalization.translate("Search for") + " "
					+ Internationalization.translate("directories") + "...");
		}

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	private void searchOnYouTube() {

		// Text we want to search on YouTube
		final String searchQuery = searchBox.getText();

		// The URL of YouTube
		String youTubeUrl = "https://www.youtube.com";

		// if text field has text try to add it to the urlToOpen
		if (searchQuery != null && searchQuery.length() > 0) {

			try {
				// encode the text to a browser query
				final String textToSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");
				// add the search query to the YouTube URL
				youTubeUrl += "/results?search_query=" + textToSearchQuery;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		// open the new URL
		ExternalApplicationModule.openUrl(youTubeUrl);

	}

	/**
	 * Search for the text in the input field on YouTube with the external default
	 * browser
	 */
	@FXML
	private void addSourceFolderDialog() {

		if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished() || DialogHandler.confirmDialog()) {

			// get a directory
			final File directory = DialogHandler.chooseDirectory(this.mainClass.getPrimaryStage(),
					Internationalization.translate("Select a new source directory"), null);

			// if the directory isn't null
			if (directory != null) {

				// add it to the path list
				this.mainClass.getMusicVideohandler().addPathToPathList(directory.toPath());

				// update the music video table list
				this.mainClass.getMusicVideohandler().updateMusicVideoList();

				// update all JavaFx tables
				refreshMusicVideoTable();
				refreshMusicVideoPlaylistTable();
				refreshMusicVideoDirectoryTable();
			}
		}
	}

	/**
	 * Open the About Window
	 */
	@FXML
	private void openAboutWindow() {
		try {

			// load the whole FXML window
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/AboutWindow.fxml"));

			// >> load the window itself
			final Parent root1 = (Parent) loader.load();

			// >> load the controller to the window
			loader.getController();

			// create a stage
			final Stage stage = new Stage(StageStyle.UTILITY);
			stage.setScene(new Scene(root1));
			stage.setResizable(false);
			stage.setTitle(Internationalization.translate("About"));

			// show the stage
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

		if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished()) {

			if ((this.mainClass.getMusicVideohandler().getPlaylistHandler() != null
					&& this.mainClass.getMusicVideohandler().getPlaylistHandler().getPlaylistElements() == null)
					|| DialogHandler.confirm(Internationalization.translate("Do you really want to continue") + "?",
							Internationalization
									.translate("If you login to the server you will lose your current playlist") + "!",
							Internationalization.translate(
									"If you do not want to loose this playlist save it before you log yourself in. Load it after the connection was established to overwrite the server data."))) {

				this.networkButton.setSelected(true);
				try {

					// load the whole FXML window
					final FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getClassLoader().getResource("windows/ServerLoginWindow.fxml"));

					// >> load the window itself
					final Parent root1 = (Parent) loader.load();

					// create a stage
					final Stage stage = new Stage(StageStyle.DECORATED);
					stage.setScene(new Scene(root1));
					stage.setResizable(false);
					stage.setWidth(400);
					stage.setTitle(Internationalization.translate("Server Login"));

					// try to add a window icon
					try {
						stage.getIcons().addAll(WindowModule.getWindowIcons());
					} catch (Exception e) {
						System.err.println("Exception while loding icons");
					}

					// do this on a close request
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent event) {
							checkNetwork();
						}
					});

					// Connection to the Controller to the stage
					final ServerLoginWindowController windowController = loader.getController();
					windowController.setServerLoginWindow(this.mainClass, stage);

					// show the stage
					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (DialogHandler.confirm(Internationalization.translate("Logout") + "?",
					Internationalization.translate("Do you want to log out") + "?",
					Internationalization.translate("You are already logged in. Click OK to logout."))) {
				this.mainClass.getMusicVideohandler().sftpDisconnect();
				this.networkButton.setSelected(false);
				refreshMusicVideoPlaylistTable();
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

			// load the whole FXML window
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/WrongFomattedFilesWindow.fxml"));

			// >> load the window itself
			final Parent root1 = (Parent) loader.load();

			// >> load the controller to the window and give him the main window
			final WrongFormattedFilesWindowController windowController = loader.getController();
			windowController.setWindowController(this.mainClass, this);

			// create a stage
			final Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinWidth(500);
			stage.setMinHeight(350);
			stage.setTitle(Internationalization.translate("Wrong Formatted Files"));

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

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

			// load the whole FXML window
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/IgnoredFilesWindow.fxml"));

			// >> load the window itself
			final Parent root1 = (Parent) loader.load();

			// >> load the controller to the window and give him the main window
			final IgnoredFilesWindowController windowController = loader.getController();
			windowController.setWindowController(this.mainClass, this);

			// create a stage
			final Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(root1));
			stage.setResizable(true);
			stage.setMinWidth(450);
			stage.setMinHeight(350);
			stage.setTitle(Internationalization.translate("Ignored Music Video Files"));

			// try to add a window icon
			try {
				stage.getIcons().addAll(WindowModule.getWindowIcons());
			} catch (Exception e) {
				System.err.println("Exception while loding icons");
			}

			// show the stage/window
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the random music video files window
	 */
	@FXML
	private void openRandomWindow() {

		if (this.mainClass.getMusicVideohandler().getMusicVideoList() != null) {

			if (this.mainClass.getMusicVideohandler().getMusicVideoList().length >= 5) {
				try {

					// load the whole FXML window
					final FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getClassLoader().getResource("windows/RandomMusicVideoWindow.fxml"));

					// >> load the window itself
					final Parent root1 = (Parent) loader.load();

					// >> load the controller to the window and give him the main window
					final RandomWindowController windowController = loader.getController();
					windowController.setWindowController(this.mainClass, this);

					// create a stage
					final Stage stage = new Stage(StageStyle.DECORATED);
					stage.setScene(new Scene(root1));
					stage.setResizable(true);
					stage.setMinHeight(360);
					stage.setMinWidth(650);

					stage.setTitle(Internationalization.translate("Random music video files"));

					// try to add a window icon
					try {
						stage.getIcons().addAll(WindowModule.getWindowIcons());
					} catch (Exception e) {
						System.err.println("Exception while loding icons");
					}

					stage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (this.mainClass.getMusicVideohandler().getMusicVideoList().length >= 1) {

				final Random randomGenerator = new Random();
				final int randomNumber = randomGenerator
						.nextInt(this.mainClass.getMusicVideohandler().getMusicVideoList().length);
				this.mainClass.getMusicVideohandler().openMusicVideo(randomNumber);

			}
		}

	}

	/**
	 * Remove a directory from the path list
	 */
	@FXML
	private void removeDirectory() {

		// get the currently selected directory/path
		final MusicVideoSourceDirectoriesTableView selectedEntry = this.directoryPathTable.getSelectionModel()
				.getSelectedItem();

		// if something was selected
		if (selectedEntry != null) {

			if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished() || DialogHandler.confirmDialog()) {

				// remove this entry from the path list
				this.mainClass.getMusicVideohandler().removeFromPathList(Paths.get(selectedEntry.getFilePath()));

				// update now both tables
				refreshMusicVideoDirectoryTable();
				refreshMusicVideoTable();
			}
		}
	}

	/**
	 * Show the currently selected directory in the default file manager
	 */
	@FXML
	private boolean showSelectedDirectoryInExplorer() {

		// get the currently selected entry
		final MusicVideoSourceDirectoriesTableView selectedEntry = this.directoryPathTable.getSelectionModel()
				.getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {
			// open the music video file with the index
			ExternalApplicationModule.openFile(Paths.get(selectedEntry.getFilePath()).toFile());
			return true;
		}

		return false;
	}

	/**
	 * Show the currently selected files directory in the default file manager
	 */
	@FXML
	private void showDirectoryInExplorerMusicVideoList() {

		// get the currently selected entry
		final MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry isn't null
		if (selectedEntry != null) {

			// get the path of the music video object over the table index - 1
			final MusicVideo selectedFile = this.mainClass.getMusicVideohandler()
					.getMusicVideoList()[selectedEntry.getIndex() - 1];

			// open the selected music video file externally
			ExternalApplicationModule.openDirectory(selectedFile.getPath().toFile());
		}
	}

	/**
	 * Add currently selected music video file to play list
	 */
	@FXML
	private void addVideoToPlaylist() {

		// get the currently selected entry
		final MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		// if entry not null
		if (selectedEntry != null) {

			// get the table index
			final int playlistEntryIndex = selectedEntry.getIndex();

			// dialog to get author and optional a comment
			final String[] authorAndComment = DialogHandler.createPlaylistEntry(nameOfAuthor);

			// if author not null add it to the playlist
			if (authorAndComment != null && authorAndComment[0] != null) {
				this.nameOfAuthor = authorAndComment[0];
				if (authorAndComment[1] == null) {
					authorAndComment[1] = "";
				}

				this.mainClass.getMusicVideohandler().addMusicVideoToPlaylist(playlistEntryIndex, authorAndComment[0],
						authorAndComment[1]);
			}

		}

		// update playlist anyhow
		refreshMusicVideoPlaylistTable();

	}

	/**
	 * Refresh the music video file table
	 */
	@FXML
	public void refreshMusicVideoTable() {

		// update the music video data
		this.mainClass.getMusicVideohandler().updateMusicVideoList();

		refreshMusicVideoTableWithoutUpdate();
	}

	/**
	 * Refresh the music video file table
	 */
	private void refreshMusicVideoTableWithoutUpdate() {

		// get music video data
		final MusicVideo[] listOfVideos = this.mainClass.getMusicVideohandler().getMusicVideoList();

		// clear table
		this.tableDataMusicVideo.clear();

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
	public void refreshMusicVideoDirectoryTable() {

		// get music video data
		final Path[] listOfPaths = this.mainClass.getMusicVideohandler().getPathList();

		// clear table
		this.tableDataDirectory.clear();

		// add music video data
		if (listOfPaths != null) {
			for (int i = 0; i < listOfPaths.length; i++) {
				tableDataDirectory.add(new MusicVideoSourceDirectoriesTableView(listOfPaths[i].toString()));
			}
		}
	}

	/**
	 * Refresh the music video playlist table
	 */
	@FXML
	public void refreshMusicVideoPlaylistTable() {

		// clear the table
		this.tableDataPlaylist.clear();

		if (this.mainClass.getMusicVideohandler().sftpConnectionEstablished()) {
			// retrieve SFTP playlist
			this.mainClass.getMusicVideohandler().sftpRetrievePlaylist();
		}

		// get music video data
		final MusicVideoPlaylistElement[] listOfEntries = this.mainClass.getMusicVideohandler().getPlaylistHandler()
				.getPlaylistElements();

		// add music video data
		if (listOfEntries != null) {
			for (int i = 0; i < listOfEntries.length; i++) {
				System.out.println("Refresh and add file by " + listOfEntries[i].getAuthor());
				final MusicVideo playlistMusicVideoFile = this.mainClass.getMusicVideohandler()
						.getMusicVideoOfPlaylistItem(listOfEntries[i].getMusicVideoFile().getPath());

				if (playlistMusicVideoFile != null) {
					tableDataPlaylist.add(new MusicVideoPlaylistTableView(i, listOfEntries[i].getMusicVideoIndex(),
							listOfEntries[i].getUnixTimeString(), playlistMusicVideoFile.getTitle(),
							playlistMusicVideoFile.getArtist(), listOfEntries[i].getAuthor(),
							listOfEntries[i].getComment(), listOfEntries[i].getVotes()));
				} else {
					System.err.println(
							"File not added because it's either ignored or not in the current music video list!");
				}
			}

			// reset table sort
			this.playlistTable.getSortOrder().clear();

			// check if connected else set votes invisible
			if (this.mainClass.getMusicVideohandler().sftpConnectionEstablished()) {

				this.contextPlaylistVotes.setVisible(true);

				this.columnPlaylistVotes.setVisible(true);
				this.columnPlaylistVotes.setSortType(TableColumn.SortType.DESCENDING);
				this.playlistTable.getSortOrder().add(this.columnPlaylistVotes);
				this.columnPlaylistVotes.setSortable(true);
			} else {
				this.columnPlaylistVotes.setVisible(false);
				this.contextPlaylistVotes.setVisible(false);
			}

			// sort the table in a specific order:
			this.columnPlaylistTime.setSortType(TableColumn.SortType.ASCENDING);
			this.playlistTable.getSortOrder().add(this.columnPlaylistTime);
			this.columnPlaylistTime.setSortable(true);
			this.columnPlaylistTitle.setSortType(TableColumn.SortType.DESCENDING);
			this.playlistTable.getSortOrder().add(this.columnPlaylistTitle);
			this.columnPlaylistTitle.setSortable(true);
			this.columnPlaylistArtist.setSortType(TableColumn.SortType.DESCENDING);
			this.playlistTable.getSortOrder().add(this.columnPlaylistArtist);
			this.columnPlaylistArtist.setSortable(true);
			this.columnPlaylistAuthor.setSortType(TableColumn.SortType.DESCENDING);
			this.playlistTable.getSortOrder().add(this.columnPlaylistAuthor);
			this.columnPlaylistAuthor.setSortable(true);
			this.playlistTable.sort();
		}

	}

	/**
	 * Ignore the currently selected music video file
	 */
	@FXML
	private void ignoreMusicVideoFile() {

		// get the currently selected entry
		final MusicVideoTableView selectedEntry = this.musicVideoTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {

			if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished() || DialogHandler.confirmDialog()) {
				final MusicVideo selectedFile = this.mainClass.getMusicVideohandler()
						.getMusicVideoList()[selectedEntry.getIndex() - 1];
				this.mainClass.getMusicVideohandler().addIgnoredFileToIgnoredFilesList(selectedFile.getPath());

				// update the music video list after this
				refreshMusicVideoTable();
			}
		}

	}

	/**
	 * Add network: Check if a working network connection is established
	 */
	private void checkNetwork() {
		if (this.mainClass.getMusicVideohandler().sftpConnectionEstablished()) {
			this.networkButton.setSelected(true);
			this.menuButtonSftp.setDisable(false);
			this.menuButtonSftpVotingReset.setDisable(false);
			// update the table
			refreshMusicVideoPlaylistTable();

		} else {
			this.networkButton.setSelected(false);
			this.menuButtonSftp.setDisable(true);
			this.menuButtonSftpVotingReset.setDisable(true);

		}

	}

	/**
	 * Export the music video list as static HTML table
	 */
	@FXML
	private void exportHtmlStatic() {

		final File htmlFileDestination = DialogHandler.chooseDirectory(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Export music video list to") + " "
						+ Internationalization.translate("a static HTML website"),
				null);

		if (htmlFileDestination != null) {
			this.mainClass.getMusicVideohandler().saveHtmlList(htmlFileDestination.toPath(), true);
		}

	}

	/**
	 * Export the music video list as HTML table with a search
	 */
	@FXML
	private void exportHtmlSearch() {

		final File htmlFileDestination = DialogHandler.chooseDirectory(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Export music video list to") + " "
						+ Internationalization.translate("a static HTML website with a search"),
				null);

		if (htmlFileDestination != null) {
			this.mainClass.getMusicVideohandler().saveHtmlSearch(htmlFileDestination.toPath(), true);
		}

	}

	/**
	 * Export the music video list as HTML table with a playlist
	 */
	@FXML
	private void exportHtmlParty() {

		final File htmlFileDestination = DialogHandler.chooseDirectory(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Export music video list to") + " "
						+ Internationalization.translate("a party HTML/PHP website structure"),
				null);

		if (htmlFileDestination != null) {
			this.mainClass.getMusicVideohandler().saveHtmlParty(htmlFileDestination.toPath(), true);
		}

	}

	/**
	 * Export the music video list as a CSV table
	 */
	@FXML
	private void exportCsv() {

		final ExtensionFilter csvFilter = new ExtensionFilter(Internationalization.translate("CSV file"), "*.csv");
		final File[] csvFile = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Export music video list to") + " "
						+ Internationalization.translate("a CSV file"),
				null, new ExtensionFilter[] { csvFilter }, DialogModule.CHOOSE_ACTION.SAVE);

		if (csvFile != null && csvFile[0] != null) {
			this.mainClass.getMusicVideohandler().saveCsv(csvFile[0].toPath());
		}

	}

	/**
	 * Export the music video list as a JSON file
	 */
	@FXML
	private void exportJson() {

		final ExtensionFilter jsonFilter = new ExtensionFilter(Internationalization.translate("JSON file"), "*.json");
		final File[] jsonFile = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Export music video list to") + " "
						+ Internationalization.translate("a JSON file"),
				null, new ExtensionFilter[] { jsonFilter }, DialogModule.CHOOSE_ACTION.SAVE);

		if (jsonFile != null && jsonFile[0] != null) {
			this.mainClass.getMusicVideohandler().saveJson(jsonFile[0].toPath());
		}

	}

	/**
	 * Save configuration in the default file
	 */
	@FXML
	private void saveConfiguartion() {
		this.mainClass.getMusicVideohandler().saveSettingsToFile();
	}

	/**
	 * Load configuration from the default file
	 */
	@FXML
	private void loadConfiguartion() {
		this.mainClass.getMusicVideohandler().loadSettingsFromFile();
		refreshMusicVideoTable();
		refreshMusicVideoDirectoryTable();
	}

	/**
	 * Save configuration/settings file with the system dialog
	 */
	@FXML
	private void saveConfiguartionCustom() {

		final ExtensionFilter jsonFilter = new ExtensionFilter(Internationalization.translate("JSON file"), "*.json");
		final File[] jsonFile = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Save configuration"), null, new ExtensionFilter[] { jsonFilter },
				DialogModule.CHOOSE_ACTION.SAVE);
		if (jsonFile != null && jsonFile[0] != null) {
			final File saveToThis = Paths.get(jsonFile[0].getAbsolutePath() + ".json").toFile();
			this.mainClass.getMusicVideohandler().saveSettings(saveToThis);
		}

	}

	/**
	 * Load with a dialog a saved settings file from the file system
	 */
	@FXML
	private void loadConfiguartionCustom() {

		final ExtensionFilter jsonFilter = new ExtensionFilter(Internationalization.translate("JSON file"), "*.json");
		final File[] jsonFile = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
				Internationalization.translate("Load configuration"), null, new ExtensionFilter[] { jsonFilter },
				DialogModule.CHOOSE_ACTION.NORMAL);
		if (jsonFile != null && jsonFile[0] != null) {
			this.mainClass.getMusicVideohandler().loadSettings(jsonFile[0]);
		}
		refreshMusicVideoTable();
		refreshMusicVideoDirectoryTable();

	}

	/**
	 * Reset the configuration/settings of the program
	 */
	@FXML
	private void resetConfiguartion() {

		// ask the user if he really wants that
		if (DialogHandler.confirm(Internationalization.translate("Confirm to continue"),
				Internationalization.translate("Reset Everything"),
				Internationalization.translate("Do you really want to reset EVERYTHING") + "!?")) {
			this.mainClass.getMusicVideohandler().reset();

			// update tables so that the tables are empty
			refreshMusicVideoTable();
			refreshMusicVideoDirectoryTable();
			refreshMusicVideoPlaylistTable();
		}

	}

	/**
	 * Open the GitHub link to the repository
	 */
	@FXML
	private void openGitHubHelpLink() {
		ExternalApplicationModule.openUrl("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
	}

	/**
	 * Open a dialog to change the current selected music video files file name
	 */
	@FXML
	private void renameFile() {

		// get the currently selected entry in the table
		final MusicVideoTableView selectedEntry = musicVideoTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished() || DialogHandler.confirmDialog()) {
				final Path pathOfSelectedFile = this.mainClass.getMusicVideohandler()
						.getMusicVideoList()[selectedEntry.getIndex() - 1].getPath();
				// check if the file really exists and isn't a directory
				final File selectedFile = pathOfSelectedFile.toFile();
				if (selectedFile.exists() && selectedFile.isFile()) {
					// show dialog to rename the file
					final String newFileName = DialogHandler.renameFile(selectedFile.getName());
					if (newFileName != null) {
						FileReadWriteModule.rename(selectedFile,
								Paths.get(selectedFile.getParentFile().getAbsolutePath() + "/" + newFileName).toFile());
						refreshMusicVideoTable();
					}
				}
			}
		}

	}

	/**
	 * Open a music video on click of the row - but only if the left muse key was
	 * clicked
	 */
	@FXML
	private void openMusicVideoPlaylistFileLeftClick() {
		if (this.leftMouseKeyWasPressed) {
			openSelectedPlaylistVideoFile();
		}
	}

	/**
	 * Open a selected music video
	 * 
	 * @return if video was opened (boolean)
	 */
	private boolean openSelectedPlaylistVideoFile() {

		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainClass.getMusicVideohandler().openMusicVideo(selectedEntry.getMusicVideoIndex() - 1);
			if (this.mainClass.getMusicVideohandler().getPlaylistRemoveStartedVideo()) {
				this.mainClass.getMusicVideohandler().getPlaylistHandler().setLatestElementIndex(selectedEntry.getIndex());
				this.contextPlaylistUndo.setVisible(true);
				this.mainClass.getMusicVideohandler().removeEntryFromPlaylist(selectedEntry.getIndex());
				refreshMusicVideoPlaylistTable();
			}
			return true;
		}
		return false;

	}

	/**
	 * Save the current playlist with a dialog to choose a file/create one
	 */
	@FXML
	private void savePlaylistDialog() {
		if (this.tableDataPlaylist.isEmpty()) {
			DialogHandler.error(Internationalization.translate("Operation failed"),
					Internationalization.translate("There is no playlist") + "!");
		} else {
			ExtensionFilter jsonFilter = new ExtensionFilter(Internationalization.translate("Json File"), "*.json");
			File[] fileName = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
					Internationalization.translate("Save current playlist"), null, new ExtensionFilter[] { jsonFilter },
					DialogModule.CHOOSE_ACTION.SAVE);
			if (fileName != null && fileName[0] != null) {
				File realFileName = new File(fileName[0].getParent() + "/" + fileName[0].getName());
				this.tableDataPlaylist.clear();
				this.refreshMusicVideoPlaylistTable();
				this.mainClass.getMusicVideohandler().savePlaylist(realFileName);
			}
		}

	}

	/**
	 * Open a dialog to choose a settings file
	 */
	@FXML
	private void loadPlaylistDialog() {

		if (this.tableDataPlaylist.isEmpty()
				|| DialogHandler.confirm(Internationalization.translate("This action has consequences"),
						Internationalization.translate("Do you really want to clear your current playlist") + "?",
						Internationalization.translate("The current playlist will be cleared if you continue"))) {
			ExtensionFilter jsonFilter = new ExtensionFilter(Internationalization.translate("JSON file"), "*.json");
			File[] fileName = DialogHandler.chooseFile(this.mainClass.getPrimaryStage(),
					Internationalization.translate("Load a saved playlist"), null, new ExtensionFilter[] { jsonFilter },
					DialogModule.CHOOSE_ACTION.NORMAL);
			if (fileName != null && fileName[0] != null) {
				this.mainClass.getMusicVideohandler().loadPlaylist(fileName[0]);
			}
			refreshMusicVideoPlaylistTable();
		}

	}

	/**
	 * Clear the selection of the playlist entries
	 */
	@FXML
	private void clearSelectionPlaylistTable() {
		this.playlistTable.getSelectionModel().clearSelection();
	}

	/**
	 * Remove the current selected music video playlist entry
	 */
	@FXML
	private void removeEntry() {

		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			this.mainClass.getMusicVideohandler().getPlaylistHandler().setLatestElementIndex(selectedEntry.getIndex());
			this.contextPlaylistUndo.setVisible(true);
			this.mainClass.getMusicVideohandler().removeEntryFromPlaylist(selectedEntry.getIndex());

			refreshMusicVideoPlaylistTable();
		}

	}

	/**
	 * Edit the current playlist entry (author/comment)
	 */
	@FXML
	private void editEntry() {

		// get the currently selected entry in the table
		MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {
			String[] authorComment = DialogHandler.editPlaylistEntry(selectedEntry.getAuthor(),
					selectedEntry.getComment());

			if (authorComment != null && authorComment[0] != null) {
				this.nameOfAuthor = authorComment[0];
				if (authorComment[1] == null) {
					authorComment[1] = "";
				}
				this.mainClass.getMusicVideohandler().editMusicVideoToPlaylist(selectedEntry.getIndex(),
						authorComment[0], authorComment[1]);
				refreshMusicVideoPlaylistTable();
			}
		}

	}

	/**
	 * Reset the voting on the SFTP server
	 */
	@FXML
	private void sftpVotingReset() {
		this.mainClass.getMusicVideohandler().resetVotingSftp();
		refreshMusicVideoPlaylistTable();
	}

	/**
	 * Reset all SFTP settings
	 */
	@FXML
	private void sftpReset() {
		this.mainClass.getMusicVideohandler().resetSftp();
		checkNetwork();
		refreshMusicVideoPlaylistTable();
	}

	/**
	 * Transfer all files to the SFTP server for setting up a "party"
	 */
	@FXML
	private void sftpParty() {
		this.mainClass.getMusicVideohandler().transferHtmlParty();
	}

	/**
	 * Transfer all files to the SFTP server for setting up a "party" without votes
	 */
	@FXML
	private void sftpPartyWithoutVotes() {
		this.mainClass.getMusicVideohandler().transferHtmlPartyWithoutVotes();
	}

	/**
	 * Transfer all files to the SFTP server for setting up a list
	 */
	@FXML
	private void sftpStatic() {
		this.mainClass.getMusicVideohandler().transferHtmlStatic();
	}

	/**
	 * Transfer all files to the SFTP server for setting up a list and a search
	 */
	@FXML
	private void sftpSearch() {
		this.mainClass.getMusicVideohandler().transferHtmlSearch();
	}

	/**
	 * Change/Toggle the always save setting
	 */
	@FXML
	public void toggleAlwaysSave() {
		this.menuButtonAlwaysSave.setSelected(this.mainClass.getMusicVideohandler()
				.setAlwaysSave(!this.mainClass.getMusicVideohandler().getAlwaysSave()));
	}

	/**
	 * Change/Toggle the always remove a started video from the playlist setting
	 */
	@FXML
	public void togglePlaylistRemoveStartedVideo() {
		this.menuButtonPlaylistRemove.setSelected(this.mainClass.getMusicVideohandler()
				.setPlaylistRemoveStartedVideo(!this.mainClass.getMusicVideohandler().getPlaylistRemoveStartedVideo()));
	}

	/**
	 * Change/Toggle the always remove a started video from the playlist setting
	 */
	@FXML
	public void addRemovedPlaylistEntry() {

		MusicVideoPlaylistElement element = this.mainClass.getMusicVideohandler().getPlaylistHandler().loadMusicVideoToPlaylist(null);
		
		// if connected to server upload playlist entry
		if (this.mainClass.getMusicVideohandler().sftpConnectionEstablished()) {
			this.mainClass.getMusicVideohandler().uploadPlaylistEntry(element);
		}
		
		this.contextPlaylistUndo.setVisible(false);
		refreshMusicVideoPlaylistTable();
	}

	/**
	 * Clear the video playlist table (remove all elements/entries)
	 */
	@FXML
	private void clearMusicVideoPlaylistTable() {

		if (!this.tableDataPlaylist.isEmpty() && DialogHandler.confirm(Internationalization.translate("Clear playlist"),
				Internationalization.translate("Do you really want to clear your current playlist") + "?",
				Internationalization.translate("If you enter yes all playlist elements will be removed") + "!")) {
			this.mainClass.getMusicVideohandler().clearPlaylist();
			this.tableDataPlaylist.clear();
		}

	}

	/**
	 * @return (String) name of the author (of the last change/account atht he first
	 *         time)
	 */
	public String getNameOfAuthor() {
		return this.nameOfAuthor;
	}

	/**
	 * Set the new author name
	 * 
	 * @param newName
	 *            (String | Name of author)
	 */
	public void setNameOfAuthor(String newName) {
		this.nameOfAuthor = newName;
	}

	/**
	 * Set the vote number for the current selected entry (Open a dialog for setting
	 * a new number)
	 */
	@FXML
	public void setVotes() {

		final MusicVideoPlaylistTableView selectedEntry = this.playlistTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// get index of playlist element
			final int index = selectedEntry.getIndex();

			// get vote number as a String
			final String voteNumber = DialogHandler.setVotesPlaylist(Integer.toString(selectedEntry.getVotes()));

			if (index >= 0 && voteNumber != null) {

				try {

					// try to get a number from the String
					int voteNumberNotString = Integer.parseInt(voteNumber);

					// if the number is bigger or zero set it as new vote number
					if (voteNumberNotString >= 0) {

						this.mainClass.getMusicVideohandler().setVotes(index, voteNumberNotString);

						// then refresh the table
						refreshMusicVideoPlaylistTable();

					}
				} catch (Exception e) {
					e.printStackTrace();
					DialogHandler.error(Internationalization.translate("Wrong input"),
							Internationalization.translate("You need to enter a number greater or equal to 0") + "!");
				}
			}
		}

	}

	@FXML
	public void openMusicVideoFileEnter(KeyEvent key) {

		if (key.getCode() == KeyCode.ENTER) {
			openSelectedVideoFile();
		}

	}

	@FXML
	public void openPlaylistVideoFileEnter(KeyEvent key) {

		if (key.getCode() == KeyCode.ENTER) {
			openSelectedPlaylistVideoFile();
		}

	}

	/**
	 * Open the default website
	 */
	@FXML
	private void openWebsite() {

		// open the new URL
		ExternalApplicationModule.openUrl("http://" + this.mainClass.getMusicVideohandler().getSftpIpAddress());

	}

	/**
	 * Open the playlist website with updates
	 */
	@FXML
	private void openWebsiteUpdate() {

		// open the new URL
		ExternalApplicationModule
				.openUrl("http://" + this.mainClass.getMusicVideohandler().getSftpIpAddress() + "/index2.php");

	}

}
