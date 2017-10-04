package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs;

import java.io.File;
import java.util.List;
import java.util.Optional;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowMethods;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Dialogs {

	/**
	 * Dialog that asks if changes should be saved.
	 * 
	 * @param mainWindowEvent
	 *            (WindowEvent - needed to let the window alive if 'Cancel' get's
	 *            pressed)
	 */
	public static void mainStageClose(WindowEvent mainWindowEvent, MusicVideoHandler saveTheSettings) {

		// create a JavaFX Alert pop-up window
		Alert alert = new Alert(AlertType.WARNING);

		// with the following title and text
		alert.setTitle("Save changes?");
		alert.setHeaderText("Do you want to save your changes?");

		// with the following buttons
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		final ButtonType buttonAlways = new ButtonType("Always", ButtonData.RIGHT);
		alert.getButtonTypes().addAll(buttonAlways);

		// which button was pressed getter
		ButtonType result = alert.showAndWait().get();

		// what should be done now
		if (result == ButtonType.CANCEL) {

			// let the window live on cancel
			mainWindowEvent.consume();

		} else {

			// quit the program and do also:
			if (result == ButtonType.YES) {
				// save changes in file
				saveTheSettings.saveSettingsToFile();
			} else if (result == buttonAlways) {
				// add to config that always the changes should be saved on exit
				// save changes in file
				saveTheSettings.setAlwaysSave(true);
				saveTheSettings.saveSettingsToFile();
			}

			// close the program without exceptions else
			Platform.exit();
		}

	}

	/**
	 * For a simpler handling with the file chooser -> use this instead of
	 * Integers<br>
	 * (CHOOSE_ACTION.NORMAL.value => 0, CHOOSE_ACTION.MULTI_FILE.value => 1,...)
	 */
	public static enum CHOOSE_ACTION {
		NORMAL, MULTI_FILE, SAVE;
	}

	/**
	 * Choose a file or more with a native dialog
	 * 
	 * @param mainStage
	 *            (Main Stage | Main Class)
	 * @param title
	 *            (String | Title of the window)
	 * @param initialDirectory
	 *            (File | Start directory if not null)
	 * @param extensionFilter
	 *            (ExtensionFilter[] | Which files should be displayed)
	 * @param whichDialog
	 *            (Integer | Which dialog is desired -> CHOOSE_ACTION enum)
	 * @return File[] or null of selected file(s)
	 */
	public static File[] chooseFile(Stage mainStage, String title, File initialDirectory,
			ExtensionFilter[] extensionFilter, CHOOSE_ACTION whichDialog) {

		System.out.print(">> Started file chooser");

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);

		if (initialDirectory != null) {
			fileChooser.setInitialDirectory(initialDirectory);
		}

		if (extensionFilter != null) {
			fileChooser.getExtensionFilters().addAll(extensionFilter);
		}
		// new ExtensionFilter("Text Files", "*.txt"),
		// new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		// new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
		// new ExtensionFilter("All Files", "*.*"));

		// create file that we want
		File[] selectedFiles = null;

		if (whichDialog == CHOOSE_ACTION.NORMAL) {
			selectedFiles = new File[] { fileChooser.showOpenDialog(mainStage) };
		} else if (whichDialog == CHOOSE_ACTION.MULTI_FILE) {
			List<File> tempList = fileChooser.showOpenMultipleDialog(mainStage);
			if (tempList != null) {
				selectedFiles = tempList.toArray(new File[0]);
			}
		} else if (whichDialog == CHOOSE_ACTION.SAVE) {
			selectedFiles = new File[] { fileChooser.showSaveDialog(mainStage) };
		}

		if (selectedFiles != null && selectedFiles[0] != null) {
			System.out.println("\n");
			for (File selectedFile : selectedFiles) {
				System.out.println("<< " + selectedFile.getAbsoluteFile().toPath() + " was selected");
			}
			return selectedFiles;
		} else {
			System.out.println(" << No file was selected");
			return null;
		}

	}

	/**
	 * Choose a directory with a native dialog
	 * 
	 * @param mainStage
	 *            (Main Stage | Main Class)
	 * @param title
	 *            (String | Title of the window)
	 * @param initialDirectory
	 *            (File | Start directory if not null)
	 * @return File or null of selected directory
	 */
	public static File chooseDirectory(Stage mainStage, String title, File initialDirectory) {

		System.out.print(">> Started directory chooser");

		// start a JavaFx DirectoryChooser
		DirectoryChooser directoryChooser = new DirectoryChooser();

		// set title
		directoryChooser.setTitle(title);

		// set initial directory if it isn't null
		if (initialDirectory != null) {
			directoryChooser.setInitialDirectory(initialDirectory);
		}

		// ask for a selected file
		File selectedFile = directoryChooser.showDialog(mainStage);
		if (selectedFile != null) {
			System.out.println(" << " + selectedFile.getAbsoluteFile().toPath() + " was selected");
			return selectedFile;
		} else {
			System.out.println(" << No directory was selected");
			return null;
		}

	}

	/**
	 * 
	 * @param mainStage
	 */
	/**
	 * Display an exception in a pop-up window
	 * 
	 * @param mainStage
	 *            (Main Stage | Main Class)
	 * @param title
	 *            (String | Title of the window)
	 * @param header
	 *            (String | Header text of the window)
	 * @param content
	 *            (String | Content text of the window)
	 * @param stacktrace
	 *            (String | Exception text to display -> see below)
	 *            <li>StringWriter stringWriter = new StringWriter();</li>
	 *            <li>PrintWriter printWriter = new PrintWriter(stringWriter);</li>
	 *            <li>exception.printStackTrace(printWriter);</li>
	 *            <li>String exceptionText = stringWriter.toString();</li>
	 */
	public static void exceptionDialog(Stage mainStage, String title, String header, String content,
			String stacktrace) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.initStyle(StageStyle.UTILITY);

		TextArea textArea = new TextArea(stacktrace);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 0);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();

	}

	/**
	 * Text input dialog
	 */
	public static String textInputDialog(Stage mainStage, String title, String titleInTextFiel, String header,
			String labelOfTextField) {
		TextInputDialog dialog = new TextInputDialog(titleInTextFiel);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(labelOfTextField);
		dialog.setResizable(true);
		dialog.initStyle(StageStyle.UNIFIED);

		// Get the Stage.
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().addAll(WindowMethods.getWindowIcons());

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println("Input was: \"" + result.get() + "\"");
			return result.get();
		}
		return null;
	}

}
