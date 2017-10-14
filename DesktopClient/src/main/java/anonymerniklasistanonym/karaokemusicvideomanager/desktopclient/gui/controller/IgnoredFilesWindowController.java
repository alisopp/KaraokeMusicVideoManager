package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller;

import java.io.File;
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
import javafx.scene.control.Button;
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
public class IgnoredFilesWindowController {

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

	/**
	 * Button which removes all paths from the ignored files list
	 */
	@FXML
	private Button buttonClearList;

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
	 * Main controller class
	 */
	private MainWindowController mainWindowController;

	/**
	 * Method to connect the window to use everything from the super class
	 * 
	 * @param mainClass
	 *            (Main)
	 */
	public void setWindowController(Main mainClass, MainWindowController mainWindowController) {

		// get the main class to get the music video handler
		this.mainClass = mainClass;

		// get the main window controller to update the tables from there
		this.mainWindowController = mainWindowController;

		// fill the table with the ignored files
		refreshIgnoredFileTable();

	}

	private void translateText() {
		columnFilePath.setText(Internationalization.translate("File Paths"));
		contextRename.setText(Internationalization.translate("Rename File"));
		contextExplorer.setText(Internationalization.translate("Show Directory of File"));
		contextIgnore.setText(Internationalization.translate("Remove file from list"));
		contextClear.setText(Internationalization.translate("Clear Selection"));
		contextRefresh.setText(Internationalization.translate("Refresh"));

		buttonClearList.setText(Internationalization.translate("Remove All Files from Ignored Files"));
		searchLabel.setText(Internationalization.translate("Search for ignored files") + ":");

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
		FilteredList<WrongFormattedFilesTableView> filteredDataDirectory = new FilteredList<>(
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
		SortedList<WrongFormattedFilesTableView> sortedDataDirectory = new SortedList<>(filteredDataDirectory);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedDataDirectory.comparatorProperty().bind(wrongFormattedFilesTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		wrongFormattedFilesTable.setItems(sortedDataDirectory);

		/**
		 * Set icons/images
		 */

		// Context menu icons
		contextRename.setGraphic(WindowModule.createMenuIcon("rename"));
		contextExplorer.setGraphic(WindowModule.createMenuIcon("directory"));
		contextIgnore.setGraphic(WindowModule.createMenuIcon("ignore"));
		contextClear.setGraphic(WindowModule.createMenuIcon("clear"));
		contextRefresh.setGraphic(WindowModule.createMenuIcon("refresh"));

		// button icon
		buttonClearList.setGraphic(WindowModule.createMenuIcon("ignore"));

		// label icon
		searchLabel.setGraphic(WindowModule.createMenuIcon("search"));

		translateText();
	}

	/**
	 * Update the wrong formatted files path table
	 */
	@FXML
	private void updateIgnoredFileTable() {

		// refresh the table with the ignored files
		refreshIgnoredFileTable();

		// at the end refresh the playlist and music video table of the main window
		mainWindowController.refreshMusicVideoTable();
		mainWindowController.refreshMusicVideoPlaylistTable();

	}

	/**
	 * Update/Refresh the table with the ignored files
	 */
	private void refreshIgnoredFileTable() {

		// get the current wrong formatted files list
		final File[] ignoredFiles = this.mainClass.getMusicVideohandler().getIgnoredFiles();

		// clear the whole table
		wrongFormattedFilesTableData.clear();

		// overwrite the table with the new list (if there is one)
		if (ignoredFiles != null) {

			// add an entry for every path in the list
			for (int i = 0; i < ignoredFiles.length; i++) {
				wrongFormattedFilesTableData.add(new WrongFormattedFilesTableView(ignoredFiles[i].getAbsolutePath()));
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

			// then open the file externally
			ExternalApplicationModule.openFile(new File(selectedEntry.getFilePath()));
		}

	}

	/**
	 * Open file if the left mouse key was clicked
	 */
	@FXML
	private void openFileLeftClick() {

		// check if the left mouse key was clicked
		if (leftMouseKeyWasPressed) {

			// open the file
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

			// save information
			leftMouseKeyWasPressed = true;
		}

	}

	/**
	 * Open dialog to rename the file
	 */
	@FXML
	private void renameFile() {

		// get the currently selected entry in the table
		final WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel()
				.getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// get the file of the selected entry
			final File selectedFile = new File(selectedEntry.getFilePath());

			// show dialog to rename the file
			final String newFileName = DialogHandler.renameFile(selectedFile.getName());

			// if the dialogs output isn't null or empty
			if (newFileName != null && !newFileName.isEmpty()) {

				// calculate the new file name
				final File newFile = new File(selectedFile.getParentFile(), newFileName);

				// then rename the file to this new name and check if everything worked fine
				if (FileReadWriteModule.rename(selectedFile, newFile)) {

					// then remove the file from the ignored files list
					this.mainClass.getMusicVideohandler().removeFromIgnoredFilesList(selectedFile.toPath());

					// and add the renamed file to the ignored files list
					this.mainClass.getMusicVideohandler().addIgnoredFileToIgnoredFilesList(newFile.toPath());

					// and last update the table
					updateIgnoredFileTable();
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
		final WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel()
				.getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// open the files parent directory externally
			ExternalApplicationModule.openFile(Paths.get(selectedEntry.getFilePath()).toFile().getParentFile());
		}

	}

	/**
	 * Add the file to a "ignore file" list
	 */
	@FXML
	private void ignoreFile() {

		// get the currently selected entry in the table
		final WrongFormattedFilesTableView selectedEntry = wrongFormattedFilesTable.getSelectionModel()
				.getSelectedItem();

		// if something is selected
		if (selectedEntry != null) {

			// remove the element with the ignored files index from the ignored files list
			this.mainClass.getMusicVideohandler().removeFromIgnoredFilesList(Paths.get(selectedEntry.getFilePath()));

			// then update the ignored files table
			updateIgnoredFileTable();
		}

	}

	/**
	 * Remove all ignored files from the ignored files list
	 */
	@FXML
	private void clearList() {

		// clear the ignored files list
		this.mainClass.getMusicVideohandler().clearIgnoredFilesList();

		// then update the ignored files table
		updateIgnoredFileTable();

	}
}
