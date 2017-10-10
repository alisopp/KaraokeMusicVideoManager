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
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com>
 * @version 2.0
 */
public class Main extends Application {

	/**
	 * Primary Stage (JavaFx main Stage/Canvas)
	 */
	private Stage primaryStage;

	/**
	 * Music Video Handler (manages the videos/paths/etc.)
	 */
	private MusicVideoHandler musicVideoHandler;

	private Stage mainWindowStage;

	private MainWindow mainWindowCreator;

	@Override
	@SuppressWarnings("restriction")
	public void init() throws Exception {
		System.out.println(Main.STEP() + "MyApplication#init (doing some heavy lifting), thread: "
				+ Thread.currentThread().getName());

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

	}

	@Override
	public void start(Stage primaryStage) {

		// get the created main window Stage back and set it as main stage
		primaryStage = this.mainWindowCreator.createStage();

	}

	private static int stepCount = 1;

	// Used to demonstrate step couns.
	public static String STEP() {
		return stepCount++ + ". ";
	}

	@SuppressWarnings("restriction")
	public static void main(String[] args) {

		// start JavaFx with a preloader
		LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);

		// start JavaFx
		// launch(args);

	}

	/**
	 * Get the primary Stage
	 * 
	 * @return (Stage | main Stage)
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Set the primary Stage
	 * 
	 * @param primaryStage
	 *            (Stage)
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Get the MusicVideoHandler
	 * 
	 * @return (MusicVideoHandler)
	 */
	public MusicVideoHandler getMusicVideohandler() {
		return musicVideoHandler;
	}

	/**
	 * Set the MusicVideoHandler
	 * 
	 * @param musicVideoHandler
	 *            (MusicVideoHandler)
	 */
	public void setMusicVideoHandler(MusicVideoHandler musicVideoHandler) {
		this.musicVideoHandler = musicVideoHandler;
	}

}
