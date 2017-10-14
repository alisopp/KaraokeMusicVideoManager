package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.WrongFormattedFilesTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.DialogHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * The controller class for the wrong formatted files window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class WrongFormattedFilesWindowController {

	// FXML views

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

	/**
	 * table context menu > rename file
	 */
	@FXML
	private MenuItem contextRename;
	/**
	 * table context menu > show in explorer
	 */
	@FXML
	private MenuItem contextExplorer;
	/**
	 * table context menu > ignore file
	 */
	@FXML
	private MenuItem contextIgnore;
	/**
	 * table context menu > clear the current table selection
	 */
	@FXML
	private MenuItem contextClear;
	/**
	 * table context menu > refresh the table
	 */
	@FXML
	private MenuItem contextRefresh;

	/**
	 * table with wrong formatted file paths
	 */
	@FXML
	private TableView<WrongFormattedFilesTableView> wrongFormattedFilesTable;
	/**
	 * column of table with wrong formatted file paths (as Strings)
	 */
	@FXML
	private TableColumn<WrongFormattedFilesTableView, String> columnFilePath;

	// other

	/**
	 * table data of the table with wrong formatted file paths
	 */
	private ObservableList<WrongFormattedFilesTableView> wrongFormattedFilesTableData;

	/**
	 * get if the last mouse key that was pressed was the left mouse key
	 */
	private boolean leftMouseKeyWasPressed;

	/**
	 * Main class
	 */
	private Main mainClass;

	/**
	 * Main window controller class
	 */
	private MainWindowController mainWindowController;

	/**
	 * Method to connect the window to use everything from the super class
	 * 
	 * @param mainClass
	 *            (Main)
	 * @param mainWindowController
	 *            (MainWindowController)
	 */
	public void setWindowController(Main mainClass, MainWindowController mainWindowController) {

		// set the connection to the main class and the main window controller
		this.mainClass = mainClass;
		this.mainWindowController = mainWindowController;

		// then create the wrong file table
		updateWrongFileTable();
	}

	private void translateText() {
		columnFilePath.setText(Internationalization.translate("File Paths"));
		contextRename.setText(Internationalization.translate("Rename File"));
		contextExplorer.setText(Internationalization.translate("Show Directory of File"));
		contextIgnore.setText(Internationalization.translate("Ignore File"));
		contextClear.setText(Internationalization.translate("Clear Selection"));
		contextRefresh.setText(Internationalization.translate("Refresh"));
		searchLabel.setText(Internationalization.translate("Search for wrong formatted files") + ":");
	}

	/**
	 * This method get's called when the FXML file get's loaded
	 */
	@FXML
	private void initialize() {

		/*
		 * The following code is mostly copied from the wonderful tutorial by Marco
		 * Jakob from code.makery and initializes the wrong formatted files path table
		 * http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		 */

		// 0. Initialize the columns.
		columnFilePath.setCellValueFactory(cellData -> cellData.getValue().getFilePathProperty());

		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		wrongFormattedFilesTableData = FXCollections.observableArrayList();
		final FilteredList<WrongFormattedFilesTableView> filteredDataDirectory = new FilteredList<>(
				wrongFormattedFilesTableData, p -> true);

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
		final SortedList<WrongFormattedFilesTableView> sortedDataDirectory = new SortedList<>(filteredDataDirectory);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataDirectory.comparatorProperty().bind(wrongFormattedFilesTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		wrongFormattedFilesTable.setItems(sortedDataDirectory);

		/**
		 * Set icons/images
		 */

		// Context menu
		contextRename.setGraphic(WindowModule.createMenuIcon("rename"));
		contextExplorer.setGraphic(WindowModule.createMenuIcon("directory"));
		contextIgnore.setGraphic(WindowModule.createMenuIcon("ignore"));
		contextClear.setGraphic(WindowModule.createMenuIcon("clear"));
		contextRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));

		// label
		searchLabel.setGraphic(WindowModule.createMenuIcon("search"));

		translateText();
	}

	/**
	 * Refresh/Update the wrong formatted files path list
	 */
	@FXML
	private void updateWrongFileTable() {

		// get the current wrong formatted files list
		Path[] wrongFormattedFiles = this.mainClass.getMusicVideohandler().getWrongFormattedFiles();

		// overwrite the table with the new list
		if (wrongFormattedFiles != null) {

			// clear the whole table
			wrongFormattedFilesTableData.clear();

			// add an entry for every path in the list
			for (int i = 0; i < wrongFormattedFiles.length; i++) {
				wrongFormattedFilesTableData.add(new WrongFormattedFilesTableView(wrongFormattedFiles[i].toString()));
			}
		}

	}

	/**
	 * Open file on click
	 */
	private void openFile() {

		// get the currently selected entry in the table
		WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// open the file
			ExternalApplicationModule.openFile(Paths.get(selectedEntry.getFilePath()).toFile());
		}

	}

	/**
	 * Open file on click
	 */
	@FXML
	private void openTopFile() {

		// select the top row
		wrongFormattedFilesTable.getSelectionModel().select(0);

		// open the file
		openFile();

		// clear the selection again
		wrongFormattedFilesTable.getSelectionModel().clearSelection();

	}

	/**
	 * Open file on click
	 */
	@FXML
	private void openFileLeftClick() {

		// check if the left mouse key was clicked
		if (leftMouseKeyWasPressed) {
			// only then try to open the file
			openFile();
		}

	}

	/**
	 * Clear the row selection before sorting
	 */
	@FXML
	private void clearRowSelection() {
		wrongFormattedFilesTable.getSelectionModel().clearSelection();
	}

	/**
	 * Monitor left mouse key on press
	 */
	@FXML
	private void mousePressed(MouseEvent e) {

		// reset mouse key
		leftMouseKeyWasPressed = false;

		// check if left mouse key is pressed
		if (e.isPrimaryButtonDown()) {
			leftMouseKeyWasPressed = true;
		}

	}

	/**
	 * Open dialog to rename the file
	 */
	@FXML
	private void renameFile() {

		// get the currently selected entry in the table
		WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// check if the file really exists and isn't a directory
			File selectedFile = Paths.get(selectedEntry.getFilePath()).toFile();
			if (selectedFile.exists() && selectedFile.isFile()) {

				if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished()
						|| DialogHandler.confirmDialog()) {

					// show dialog to rename the file
					String a = DialogHandler.renameFile(selectedFile.getName());

					// if the dialogs response isn't null
					if (a != null) {

						// rename the selected file
						FileReadWriteModule.rename(selectedFile,
								Paths.get(selectedFile.getParentFile().getAbsolutePath() + "/" + a).toFile());

						// then update the wrong file table
						updateWrongFileTable();

						// and then update also the playlist and music video table
						mainWindowController.refreshMusicVideoTable();
						mainWindowController.refreshMusicVideoPlaylistTable();
					}
				}
			}
		}

	}

	/**
	 * Open directory of file with the default file explorer
	 */
	@FXML
	private void showInDirectory() {

		// get the currently selected entry in the table
		WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// check if the file really exists and isn't a directory
			File selectedFile = Paths.get(selectedEntry.getFilePath()).toFile();
			if (selectedFile.exists() && !selectedFile.isDirectory()) {

				// if yes then open the parent of the file (the directory it is in)
				ExternalApplicationModule.openFile(selectedFile.getParentFile());
			}
		}

	}

	/**
	 * Add the file to a "ignore file" list
	 */
	@FXML
	private void ignoreFile() {

		// get the currently selected entry in the table
		WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel().getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			if (!this.mainClass.getMusicVideohandler().sftpConnectionEstablished() || DialogHandler.confirmDialog()) {

				// add the selected entry to the ignored files list
				this.mainClass.getMusicVideohandler()
						.addIgnoredFileToIgnoredFilesList(Paths.get(selectedEntry.getFilePath()));

				// update the music video list after this
				updateWrongFileTable();

				// and then update also the playlist and music video table
				mainWindowController.refreshMusicVideoTable();
				mainWindowController.refreshMusicVideoPlaylistTable();

			}
		}
	}

}
