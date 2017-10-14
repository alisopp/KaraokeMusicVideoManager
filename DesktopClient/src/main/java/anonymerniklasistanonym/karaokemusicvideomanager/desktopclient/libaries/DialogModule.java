package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

/**
 * Static methods for JavaFX window dialogs/alerts <br>
 * <br>
 * The methods in here mainly can exist because of the great
 * documentation/explanation from this website: <a href=
 * "http://code.makery.ch/blog/javafx-dialogs-official/">http://code.makery.ch/blog/javafx-dialogs-official/</a>
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class DialogModule {

	/**
	 * A generic alert window with maximum possibilities
	 * 
	 * @param title
	 *            (String | Title text of alert)
	 * @param header
	 *            (String | Header text of alert -> set null to not display this)
	 * @param text
	 *            (String | Content text of alert)
	 * @param textBox
	 *            (String | Content of expandable text box -> set null to not
	 *            display this)
	 * @param type
	 *            (AlertType | Type of icon and type of alert)
	 * @param icons
	 *            (Image[] | Icon of window -> set null to not use/display this)
	 * @param style
	 *            (StageStyle | Type/Appearance of stage/alert/window)
	 */
	public static void alertWindow(String title, String header, String text, String textBox, AlertType type,
			Image[] icons, StageStyle style) {

		// set type of alert and stage style
		Alert alert = new Alert(type);
		alert.initStyle(style);

		// make the window not resizable (expandable content will be resizable though)
		alert.setResizable(false);

		// set title, header and text
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);

		// if there is textBox content add a expandable text box
		if (textBox != null) {

			// create a text area which wraps the text and isn't editable
			TextArea textArea = new TextArea(textBox);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			// and has always the maximum size to the window
			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			// now add the text area to a GridPane (looks better)
			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(textArea, 0, 0);

			// and last but not least add this GridPane as expandable content to the alert
			alert.getDialogPane().setExpandableContent(expContent);
		}

		// if there are icons add them to the stage
		if (icons != null) {
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(icons);
		}

		System.out.println(">> Alert dialog was opened");
		alert.showAndWait();
		System.out.println("<< Alert dialog was closed");

	}

	/**
	 * A generic alert confirmation window with maximum possibilities
	 * 
	 * @param title
	 *            (String | Title text of alert)
	 * @param header
	 *            (String | Header text of alert)
	 * @param contentOfTextField
	 *            (String | Content text of alert)
	 * @param type
	 *            (AlertType | Type of icon and type of alert)
	 * @param image
	 *            (Image | Graphic/Image of alert -> set null to not use/display
	 *            this)
	 * @param icons
	 *            (Image[] | Icon of window -> set null to not use/display this)
	 * @param style
	 *            (StageStyle | Type/Appearance of stage/alert/window)
	 * @return
	 */
	public static boolean alertConfirm(String title, String header, String contentOfTextField, AlertType type,
			Image image, Image[] icons, StageStyle style) {

		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentOfTextField);

		// make the window not resizable
		alert.setResizable(false);

		alert.initStyle(style);

		if (image != null) {
			alert.setGraphic(new ImageView(image));
		}

		// add icons if there are any
		if (icons != null) {
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(WindowModule.getWindowIcons());
		}

		System.out.println(">> Confirm dialog was opened");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.out.println("<< Confirmation");
			return true;
		} else {
			System.out.println("<< No confirmation or window was closed");
			return false;
		}
	}

	/**
	 * A generic alert window with a text input with maximum possibilities
	 * 
	 * @param title
	 *            (String | Title text of alert)
	 * @param header
	 *            (String | Header text of alert)
	 * @param contentOfTextField
	 *            (String | Content text of alert)
	 * @param image
	 *            (Image | Graphic/Image of alert -> set null to not use/display
	 *            this)
	 * @param icons
	 *            (Image[] | Icon of window -> set null to not use/display this)
	 * @param style
	 *            (StageStyle | Type/Appearance of stage/alert/window)
	 * @return
	 */
	public static String alertTextInput(String title, String header, String contentOfTextField, Image image,
			Image[] icons, StageStyle style) {

		// create a new text input dialog and set the text of the text field
		TextInputDialog dialog = new TextInputDialog(contentOfTextField);

		// set style of alert
		dialog.initStyle(style);

		// make the window not resizable
		dialog.setResizable(false);

		// set alert title and header
		dialog.setTitle(title);
		dialog.setHeaderText(header);

		// if there is a image set the alert graphic to it
		if (image != null) {
			dialog.setGraphic(new ImageView(image));
		}

		// if there are icons add them to the stage
		if (icons != null) {
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(icons);
		}

		System.out.println(">> Text input dialog was opened");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {

			// If there was a input return it
			System.out.println("<< Input was: \"" + result.get() + "\"");
			return result.get();

		} else {

			// if window was closed return null
			System.out.println("<< Dialog was cancelled");
			return null;

		}

	}

	/**
	 * A generic alert window with a text input with two text boxes and maximum
	 * possibilities
	 * 
	 * @param title
	 *            (String | Title text of alert)
	 * @param header
	 *            (String | Header text of alert)
	 * @param input1
	 *            (String | Text in the first input field)
	 * @param input2
	 *            (String | Text in the second input field)
	 * @param inputPrompt1
	 *            (String | Prompt text in the first input field)
	 * @param inputPrompt2
	 *            (String | Prompt text in the second input field)
	 * @param inputLabel1
	 *            (String | Label before the first input field)
	 * @param inputLabel2
	 *            (String | Label before the second input field)
	 * @param buttonText
	 *            (String | Text of the OK button -> set null to use/display the
	 *            default OK button)
	 * @param image
	 *            (Image | Graphic/Image of alert -> set null to not use/display
	 *            this)
	 * @param icons
	 *            (Image[] | Icon of window -> set null to not use/display this)
	 * @param style
	 *            (StageStyle | Type/Appearance of stage/alert/window)
	 * @param field1NotNull
	 *            (boolean | Block OK button if the text in the first input field is
	 *            null/empty)
	 * @param field2NotNull
	 *            (boolean | Block OK button if the text in the second input field
	 *            is null/empty)
	 * @param field1TextChanged
	 *            (boolean | Block OK button if the text in the first input field is
	 *            the same as the text set on creation of this dialog)
	 * @param field2TextChanged
	 *            (boolean | Block OK button if the text in the second input field
	 *            is the same as the text set on creation of this dialog)
	 * @param textChanged
	 *            (boolean | Block OK button if the input of both windows is the
	 *            same as the text set on creation of this dialog [set therefore the
	 *            two booleans before also true])
	 * @return String[] = {input1 text, input2 text} or null
	 */
	public static String[] alertTextInputTwoBoxes(String title, String header, String input1, String input2,
			String inputPrompt1, String inputPrompt2, String inputLabel1, String inputLabel2, String buttonText,
			Image image, Image[] icons, StageStyle style, boolean field1NotNull, boolean field2NotNull,
			boolean field1TextChanged, boolean field2TextChanged, boolean textChanged) {

		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();

		// set style of alert
		dialog.initStyle(style);

		// make the window not resizable
		dialog.setResizable(false);

		// set title, header and text field
		dialog.setTitle(title);
		dialog.setHeaderText(header);

		if (image != null) {
			dialog.setGraphic(new ImageView(image));
		}

		// add icons if there are any
		if (icons != null) {
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(WindowModule.getWindowIcons());
		}

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		TextField inputField1 = new TextField();
		inputField1.setPromptText(inputPrompt1);
		inputField1.setText(input1);
		TextField inputField2 = new TextField();
		inputField2.setPromptText(inputPrompt2);
		inputField2.setText(input2);

		grid.add(new Label(inputLabel1), 0, 0);
		grid.add(inputField1, 1, 0);
		grid.add(new Label(inputLabel2), 0, 1);
		grid.add(inputField2, 1, 1);

		dialog.getDialogPane().setContent(grid);

		// Set the button types.
		Node loginButton;
		ButtonType editElement = null;
		if (buttonText != null) {
			editElement = new ButtonType(buttonText, ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(editElement, ButtonType.CANCEL);
			loginButton = dialog.getDialogPane().lookupButton(editElement);
		} else {
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			loginButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
		}
		ButtonType finalButtonType = editElement;

		if (field1NotNull) {
			if (inputField1.textProperty().getValue().trim().isEmpty() == true) {
				loginButton.setDisable(true);
			}
		}
		if (field2NotNull) {
			if (inputField2.textProperty().getValue().trim().isEmpty() == true) {
				loginButton.setDisable(true);
			}
		}
		if (textChanged || (field1TextChanged || field2TextChanged)) {
			loginButton.setDisable(true);
		}

		// Do some validation (using the Java 8 lambda syntax).
		inputField1.textProperty().addListener((observable, oldValue, newValue) -> {

			boolean disabled = false;
			if (field1NotNull) {
				if (inputField1.textProperty().getValue().trim().isEmpty() == true) {
					disabled = true;
				}
			}

			if (field1TextChanged || textChanged) {
				if (inputField1.getText().equals(input1) == true) {

					if (textChanged) {
						if (inputField2.getText().equals(input2) == true) {
							disabled = true;
						}
					} else {
						disabled = true;
					}
				}
			}

			if (disabled == false) {

				if (field2NotNull) {
					if (inputField2.textProperty().getValue().trim().isEmpty() == true) {
						disabled = true;
					}
				}

				if (field2TextChanged) {
					if (inputField2.getText().equals(input2) == true) {
						disabled = true;
					}
				}
			}
			loginButton.setDisable(disabled);
		});

		// Do some validation (using the Java 8 lambda syntax).
		inputField2.textProperty().addListener((observable, oldValue, newValue) -> {

			boolean disabled = false;
			if (field2NotNull) {
				if (inputField2.textProperty().getValue().trim().isEmpty() == true) {
					disabled = true;
				}
			}

			if (field2TextChanged || textChanged) {
				if (inputField2.getText().equals(input2) == true) {
					if (textChanged) {
						if (inputField1.getText().equals(input1) == true) {
							disabled = true;
						}
					} else {
						disabled = true;
					}
				}
			}

			if (disabled == false) {

				if (field1NotNull) {
					if (inputField1.textProperty().getValue().trim().isEmpty() == true) {
						disabled = true;
					}
				}

				if (field1TextChanged) {
					if (inputField1.getText().equals(input1) == true) {
						disabled = true;
					}
				}
			}
			loginButton.setDisable(disabled);
		});

		// Request focus on the username field by default.
		Platform.runLater(() -> inputField1.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (buttonText != null) {
				if (dialogButton == finalButtonType) {
					return new Pair<>(inputField1.getText(), inputField2.getText());
				}
			} else {
				if (dialogButton == ButtonType.OK) {
					return new Pair<>(inputField1.getText(), inputField2.getText());
				}
			}

			return null;
		});

		System.out.println(">> Text input dialog with two text boxes was opened");

		Optional<Pair<String, String>> result = dialog.showAndWait();

		if (result.isPresent()) {
			System.out.println("<< Dialog was approved with field1: \"" + result.get().getKey() + "\" and field2: \""
					+ result.get().getValue() + "\"");
			return new String[] { result.get().getKey(), result.get().getValue() };
		} else {
			System.out.println("<< Dialog was cancelled");
			return null;
		}

	}

	/**
	 * Choose a directory with a native dialog
	 * 
	 * @param mainStage
	 *            (Stage | Main window stage)
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
	 * For a simpler handling with the file chooser<br>
	 * <ul>
	 * <li><b>NORMAL</b> -> choose a file</li>
	 * <li><b>MULTI_FILE</b> -> select multiple files</li>
	 * <li><b>SAVE</b> -> save a file</li>
	 * </ul>
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

		// Examples for extensions:
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

}
