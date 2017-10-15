package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import java.util.Locale;

import com.sun.javafx.application.LauncherImpl;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames.CustomPreloader;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames.MainWindow;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;
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

	@Override
	public void init() throws Exception {

		System.out.println(">> Progam initialization started");

		// set the language in relation to the current Java Runtime
		Internationalization.setBundle(Locale.getDefault());

		// create a MusicVideoHandler
		this.musicVideoHandler = new MusicVideoHandler();
		setPreloaderProgress(10);

		// try to load settings from a "settings.json" file
		this.musicVideoHandler.loadSettingsFromFile();
		setPreloaderProgress(35);

		// create a music video list
		this.musicVideoHandler.updateMusicVideoList();
		setPreloaderProgress(55);

		// create/load the main window and transfer the MusicVideoHandler
		this.mainWindowCreator = new MainWindow(this);
		setPreloaderProgress(75);

		// create the main window scene
		this.mainWindowCreator.createScene();
		setPreloaderProgress(100);

		System.out.println("<< Progam initialization finished");

	}

	@Override
	public void start(Stage primaryStage) {

		System.out.println(">> Open main window");

		// create the main window stage and show it
		primaryStage = this.mainWindowCreator.createStage();
		primaryStage.show();

		System.out.println("MAIN WINDOW");

	}

	/**
	 * ------- MAIN METHOD ---------------------------------------------------------
	 * >> start JavaFx with a preloader (without: "launch(args);")
	 * ------------------------------------------------------------------------------
	 */
	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, CustomPreloader.class, args);
	}

	/**
	 * Set preloader progress
	 * 
	 * @param progress
	 *            (Integer | New progress [0-100])
	 */
	private void setPreloaderProgress(int progress) {
		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
	}

	/**
	 * Get the primary Stage of the program
	 * 
	 * @return (Stage | main Stage)
	 */
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	/**
	 * Get the MusicVideoHandler of the program
	 * 
	 * @return (MusicVideoHandler)
	 */
	public MusicVideoHandler getMusicVideohandler() {
		return musicVideoHandler;
	}

}
