package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.Main;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller.MainWindowController;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.DialogHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.WindowModule;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Main window creator class.
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MainWindow {

	/**
	 * The main class
	 */
	private Main mainClass;

	/**
	 * The main window scene
	 */
	private Scene mainWindowScene;

	/**
	 * The title of the window
	 */
	private final String windowTitle;

	/**
	 * The normal window size
	 */
	private final int[] normalWindowSize;

	/**
	 * The minimal window size
	 */
	private final int[] minimalWindowSize;
	
	/**
	 * Constructor
	 * 
	 * @param mainClass
	 *            (Main | needed to use the resources from the main class in here)
	 */
	public MainWindow(Main mainClass) {

		System.out.println(">> Main window constructor");

		// connect to the main class
		this.mainClass = mainClass;

		// get the program name
		this.windowTitle = Main.PROGRAM_NAME;

		// set the window size constraints
		this.normalWindowSize = new int[] { 700, 600 };
		this.minimalWindowSize = new int[] { 540, 450 };

	}

	/**
	 * Create/Load the main windows scene (during preloader)
	 */
	public void createScene() {

		try {

			// load the whole FXML window
			final FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("windows/MainWindow.fxml"));

			// load the window itself
			final Parent mainPane = (Parent) loader.load();

			// Connection to the Controller from the primary Stage
			final MainWindowController mainWindowController = loader.getController();
			mainWindowController.setMainWindow(this.mainClass);

			// set the scene with all these informations
			this.mainWindowScene = new Scene(mainPane, this.normalWindowSize[0], this.normalWindowSize[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create the main windows stage
	 * 
	 * @return mainWindowStage (Stage)
	 */
	public Stage createStage() {

		try {

			// window looks like a normal application window
			Stage mainWindowStage = new Stage(StageStyle.DECORATED);

			// set a title for the window
			mainWindowStage.setTitle(this.windowTitle);

			// add the scene to the stage
			mainWindowStage.setScene(this.mainWindowScene);

			// set the window resizable
			mainWindowStage.setResizable(true);

			// add minimal window sizes
			mainWindowStage.setMinWidth(this.minimalWindowSize[0]);
			mainWindowStage.setMinHeight(this.minimalWindowSize[1]);

			// center the stage to the screen
			mainWindowStage.centerOnScreen();

			// try to add a window icon
			mainWindowStage.getIcons().addAll(WindowModule.getWindowIcons());

			// do the following if the stage get a close request (user presses X)
			mainWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent e) {
					// on close do the "on-close-dialog"
					onCloseDialog(e);
				}
			});

			// now return the stage
			return mainWindowStage;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Do this on request of closing the main window
	 */
	private void onCloseDialog(WindowEvent e) {

		// check if there is no existing settings file
		if (this.mainClass.getMusicVideohandler().getAlwaysSave()
				|| (!this.mainClass.getMusicVideohandler().settingsFileExist()
						&& !this.mainClass.getMusicVideohandler().windowsSettingsFileExists())) {

			// if there is no one save the settings to the default file
			this.mainClass.getMusicVideohandler().saveSettingsToFile();

		} else {

			// if there is an existing settings file compare the settings
			if (!this.mainClass.getMusicVideohandler().compareSettings()) {

				// if they are different open a special dialog
				DialogHandler.mainStageClose(e, this.mainClass.getMusicVideohandler());
				return;
			}
		}

		// last disconnect from the server and exit the whole program
		this.mainClass.getMusicVideohandler().sftpDisconnect();
		Platform.exit();

	}
}
