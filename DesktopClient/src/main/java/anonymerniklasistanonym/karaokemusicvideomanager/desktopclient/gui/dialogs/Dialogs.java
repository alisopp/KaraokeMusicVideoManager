package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.dialogs;

import java.io.File;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Dialogs {

	/**
	 * Dialog that asks if changes should be saved.
	 * 
	 * @param mainWindowEvent
	 *            (WindowEvent - needed to let the window alive if 'Cancel' get's
	 *            pressed)
	 */
	public static void mainStageClose(WindowEvent mainWindowEvent) {

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
				System.out.println("Later...");
			} else if (result == buttonAlways) {
				// add to config that always the changes should be saved on exit
				// save changes in file
				System.out.println("Later...");
			}

			// close the program without exceptions else
			Platform.exit();
		}

	}

	public static enum CHOOSE_ACTION {
		NORMAL(0), MULTI_FILE(1), SAVE(2);

		private final int value;

		CHOOSE_ACTION(final int newValue) {
			value = newValue;
		}
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
			ExtensionFilter[] extensionFilter, Integer whichDialog) {

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

		if (whichDialog == CHOOSE_ACTION.NORMAL.value) {
			selectedFiles = new File[] { fileChooser.showOpenDialog(mainStage) };
		} else if (whichDialog == CHOOSE_ACTION.MULTI_FILE.value) {
			List<File> tempList = fileChooser.showOpenMultipleDialog(mainStage);
			if (tempList != null) {
				selectedFiles = tempList.toArray(new File[0]);
			}
		} else if (whichDialog == CHOOSE_ACTION.SAVE.value) {
			selectedFiles = new File[] { fileChooser.showSaveDialog(mainStage) };
		}

		if (selectedFiles != null) {
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

}
