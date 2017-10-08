package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames.MainWindow;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import javafx.application.Application;
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

	@Override
	public void start(Stage primaryStage) {

		// save/add the primary/main Stage
		this.setPrimaryStage(primaryStage);

		// save/add a MusicVideoHandler
		this.musicVideoHandler = new MusicVideoHandler();

		// try to load settings from a "settings.json" file
		this.musicVideoHandler.loadSettingsFromFile();

		// create a main window which will get all the data
		MainWindow mainWindowCreator = new MainWindow(this);

		// get the created main window Stage back and set it as main stage
		this.setPrimaryStage(mainWindowCreator.getMainWindowStage());
	}

	public static void main(String[] args) {

		// start JavaFx
		launch(args);

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
