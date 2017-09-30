package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames.MainWindow;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main method. The whole program runs here.
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com>
 * @version 0.9.1
 */
public class Main extends Application {

	public Stage primaryStage;

	public MusicVideoHandler musicVideohandler;

	private MainWindow mainWindowCreator;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		this.musicVideohandler = new MusicVideoHandler();
		File savedSettings = Paths.get("settings.json").toFile();
		if (savedSettings.exists()) {
			this.musicVideohandler.loadSettings(savedSettings);
			this.musicVideohandler.updateMusicVideoList();
		}
		this.musicVideohandler.addPathToPathList(Paths.get("C:\\Users\\nikla\\Downloads\\karaoke " + "test\\1"));
		this.musicVideohandler.addPathToPathList(Paths.get("..\\CreateDemoFiles\\demo_files"));
		this.musicVideohandler.updateMusicVideoList();

		mainWindowCreator = new MainWindow(this);
		mainWindowCreator.createStage();
		this.primaryStage = mainWindowCreator.getMainWindowStage();
	}

	/**
	 * public void mainWindow() { try { FXMLLoader loader = new FXMLLoader();
	 * loader.setLocation(getClass().getClassLoader().getResource("windows/MainWindow.fxml"));
	 * BorderPane mainPane = loader.load();
	 * 
	 * primaryStage.setResizable(true); primaryStage.setMinWidth(400.00);
	 * primaryStage.setMinHeight(500.00); primaryStage.centerOnScreen();
	 * 
	 * // Connection to the Controller from the primary Stage MainWindowController
	 * mainWindowController = loader.getController();
	 * mainWindowController.setMainWindow(this);
	 * 
	 * Scene scene = new Scene(mainPane); primaryStage.setScene(scene);
	 * 
	 * primaryStage.show();
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	public static void main(String[] args) {

		// first recreate the main parts here
		// but after all the language thing and the rest needs to be way simpler

		// load data if there is something
		// * config file json
		// * language
		// -> show progress window with current reading file

		// create main window
		// * load table into window

		// new features:
		// paths, about, save & restore, network, export, settings
		// random video gives back list of number random videos (3, but it should be
		// editable in settings)

		// needed:
		// * FTP/SFTP write delete and scan module
		// * JSON read and write module
		// * Live window with the next (number - 10) videos -> Playlist (option to
		// delete
		// and view the video)
		// left click adds video to playlist/deletes the file -> if context menu is
		// possible
		// * tree view need's to be rewritten that the same folder name can exist two
		// times and the delete process works
		// * offline playlist alternative on left click without network
		// network exit or begin moves current playlist (local added elements are
		// labeled
		// [local], online ones are [server]
		// * simple PHP script export for server with icons and everything
		// * new HTML with JavaScript list where you can click an element, get to a new
		// site, get displayed the song and a form with descripton, author and a send
		// and abort button -> this gets send to the PHP page and returns to the main
		// list
		// * export a barcode to list site as HTML file with address input (choose table
		// size before creating)

		// Main mainApp = new Main();

		// mainApp.manager = new MusicVideoHandler();

		// mainApp.startUp();

		launch(args);

		// mainApp.onClose();

	}
	/*
	 * private void startUp() {
	 * 
	 * // load previous saved settings File savedSettings =
	 * Paths.get("settings.json").toFile();
	 * 
	 * if (savedSettings.exists()) {
	 * this.manager.setSettingsData(ExportImportSettings.readSettings(savedSettings)
	 * );
	 * 
	 * // update the music video list this.manager.updateMusicVideoList(); } }
	 * 
	 * private void onClose() {
	 * 
	 * Stage a = new Stage();
	 * 
	 * // load previous saved settings File savedSettings =
	 * Paths.get("settings.json").toFile();
	 * 
	 * if (!savedSettings.exists()) { // save changes if nothing is there
	 * ExportImportSettings.writeSettings(savedSettings,
	 * this.manager.getSettingsData()); } }
	 */
}
