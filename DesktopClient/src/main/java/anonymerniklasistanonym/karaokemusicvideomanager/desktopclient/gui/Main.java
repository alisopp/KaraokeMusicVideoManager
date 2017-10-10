package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import com.sun.javafx.application.LauncherImpl;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames.MainWindow;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

/**
 * Main method. The whole program runs here.
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 * @version 2.0.0
 */
@SuppressWarnings("restriction")
public class Main extends Application {

	/**
	 * Primary Stage (JavaFx main Stage/Canvas)
	 */
	private Stage primaryStage;

	/**
	 * Music Video Handler (manages the videos/paths/everything)
	 */
	private MusicVideoHandler musicVideoHandler;

	/**
	 * Creates the main window
	 */
	private MainWindow mainWindowCreator;

	/**
	 * Method that runs after the preloader was initialized and before the main
	 * window get's created/shown
	 */
	@Override
	public void init() throws Exception {

		System.out.println(">> App initalisation started");

		// save/add a MusicVideoHandler
		this.musicVideoHandler = new MusicVideoHandler();

		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(20));

		// try to load settings from a "settings.json" file
		this.musicVideoHandler.loadSettingsFromFile();

		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(80));

		// create a main window which will get all the data
		this.mainWindowCreator = new MainWindow(this);

		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(85));

		this.mainWindowCreator.createScene();

		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(100));

		System.out.println("<< App initalisation finished");
	}

	/**
	 * Open the main window stage
	 */
	@Override
	public void start(Stage primaryStage) {

		System.out.println(">> App open stage");

		// get the created main window Stage back and set it as main stage
		primaryStage = this.mainWindowCreator.createStage();

		// show it
		primaryStage.show();

		System.out.println("<< App open stage finished");

	}

	/**
	 * ---------------------- MAIN METHOD ----------------------
	 */
	public static void main(String[] args) {

		// start JavaFx with a preloader
		LauncherImpl.launchApplication(Main.class, CustomPreloader.class, args);

		// start JavaFx without a preloader: launch(args);

	}

	/**
	 * Get the primary Stage
	 * 
	 * @return (Stage | main Stage)
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * Get the MusicVideoHandler
	 * 
	 * @return (MusicVideoHandler)
	 */
	public MusicVideoHandler getMusicVideohandler() {
		return musicVideoHandler;
	}

}
