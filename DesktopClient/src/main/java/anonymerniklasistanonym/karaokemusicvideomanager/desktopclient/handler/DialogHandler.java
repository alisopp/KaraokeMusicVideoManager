package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.DialogModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Class that handles static dialogs for the JavaFX window
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class DialogHandler {

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
		alert.setTitle(Internationalization.translate("Save changes") + "?");
		alert.setHeaderText(Internationalization.translate("Do you want to save your changes") + "?");

		// Get the Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		// Add a custom icon.
		stage.getIcons().addAll(WindowModule.getWindowIcons());

		// with the following buttons
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		final ButtonType buttonAlways = new ButtonType(Internationalization.translate("Always"), ButtonData.RIGHT);
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

			// disconnect from server if connected
			saveTheSettings.sftpDisconnect();

			// close the program without exceptions else
			Platform.exit();
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
			ExtensionFilter[] extensionFilter, DialogModule.CHOOSE_ACTION whichDialog) {
		return DialogModule.chooseFile(mainStage, title, initialDirectory, extensionFilter, whichDialog);
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
		return DialogModule.chooseDirectory(mainStage, title, initialDirectory);
	}

	/**
	 * Confirmation dialog
	 * 
	 * @param title
	 *            (String | Window title)
	 * @param header
	 *            (String | Header title -> null if no header is wanted)
	 * @param contentOfTextField
	 *            (String | Text to display)
	 * @return boolean value of which button was pressed -> returns false on close
	 */
	public static boolean confirm(String title, String header, String contentOfTextField) {
		return DialogModule.alertConfirm(title, header, contentOfTextField, AlertType.CONFIRMATION, null,
				WindowModule.getWindowIcons(), StageStyle.UNIFIED);
	}

	/**
	 * Playlist "CREATE ENTRY" dialog
	 * 
	 * @param author
	 *            (String | Name of current user or last author)
	 * @return String[] = { new author, new comment} or null
	 */
	public static String[] createPlaylistEntry(String author) {

		String title = Internationalization.translate("Add to playlist");
		String header = Internationalization.translate("Enter an author and comment");
		String inputPrompt1 = Internationalization.translate("New author");
		String inputPrompt2 = Internationalization.translate("New comment");
		String inputLabel1 = Internationalization.translate("Author") + ":";
		String inputLabel2 = Internationalization.translate("Comment") + ":";
		String buttonText = Internationalization.translate("Add");

		return DialogModule.alertTextInputTwoBoxes(title, header, author, "", inputPrompt1, inputPrompt2, inputLabel1,
				inputLabel2, buttonText, new Image(ClassResourceReaderModule.getInputStream("images/icons/add.png")),
				WindowModule.getWindowIcons(), StageStyle.UNIFIED, true, false, false, false, false);

	}

	/**
	 * Playlist "EDIT ENTRY" dialog
	 * 
	 * @param author
	 *            (String | Present name of author)
	 * @param comment
	 *            (String | Present comment)
	 * @return String[] = { new author, new comment} or null
	 */
	public static String[] editPlaylistEntry(String author, String comment) {

		String title = Internationalization.translate("Edit the selected Playlist entry");
		String header = Internationalization.translate("Edit author and comment");
		String inputPrompt1 = Internationalization.translate("New author");
		String inputPrompt2 = Internationalization.translate("New comment");
		String inputLabel1 = Internationalization.translate("Author") + ":";
		String inputLabel2 = Internationalization.translate("Comment") + ":";
		String buttonText = Internationalization.translate("Edit");

		return DialogModule.alertTextInputTwoBoxes(title, header, author, comment, inputPrompt1, inputPrompt2,
				inputLabel1, inputLabel2, buttonText,
				new Image(ClassResourceReaderModule.getInputStream("images/icons/rename.png")),
				WindowModule.getWindowIcons(), StageStyle.UNIFIED, true, false, false, false, true);

	}

	/**
	 * Simple error alert
	 * 
	 * @param title
	 *            (String | Title of alert)
	 * @param text
	 *            (String | Text of alert)
	 */
	public static void error(String title, String text) {
		DialogModule.alertWindow(title, null, text, null, AlertType.ERROR, WindowModule.getWindowIcons(),
				StageStyle.UNIFIED);
	}

	/**
	 * Error alert with expandable stack trace
	 * 
	 * @param title
	 *            (String | Title of alert)
	 * @param header
	 *            (String | Header text of alert)
	 * @param text
	 *            (String | Text of alert)
	 * @param expandableContent
	 *            (String | Expandable content like a stack trace)
	 */
	public static void error(String title, String header, String text, String expandableContent) {
		DialogModule.alertWindow(title, header, text, expandableContent, AlertType.ERROR, WindowModule.getWindowIcons(),
				StageStyle.UNIFIED);
	}

	/**
	 * Simple information alert
	 * 
	 * @param title
	 *            (String | Title of alert)
	 * @param text
	 *            (String | Text of alert)
	 */
	public static void inform(String title, String text) {
		DialogModule.alertWindow(title, null, text, null, AlertType.INFORMATION, WindowModule.getWindowIcons(),
				StageStyle.UNIFIED);
	}

	/**
	 * Rename dialog
	 * 
	 * @param contentOfTextField
	 *            (String | Name of present file)
	 * @return new file name or null (String)
	 */
	public static String renameFile(String contentOfTextField) {

		String title = Internationalization.translate("Rename File");
		String header = Internationalization.translate("Rename the following file") + ":";
		Image image = new Image(ClassResourceReaderModule.getInputStream("images/icons/rename.png"));

		return DialogModule.alertTextInput(title, header, contentOfTextField, image, WindowModule.getWindowIcons(),
				StageStyle.UNIFIED);

	}

	/**
	 * Server confirm dialog
	 * 
	 * @return true if confirmed else false
	 */
	public static boolean confirmDialog() {
		String title = Internationalization.translate("Warning");
		String header = Internationalization.translate("Do you really want to continue") + "?";
		String contentOfTextField = Internationalization.translate(
				"Because you are connected with a server the websites music video list and the playlist won't work right if you continue")
				+ "!";
		return DialogModule.alertConfirm(title, header, contentOfTextField, AlertType.CONFIRMATION, null,
				WindowModule.getWindowIcons(), StageStyle.UNIFIED);

	}

}
