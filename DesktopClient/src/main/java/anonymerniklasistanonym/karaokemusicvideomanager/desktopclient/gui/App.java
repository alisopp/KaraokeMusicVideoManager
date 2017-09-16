package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportImportSettings;
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
public class App extends Application {

	private MainWindow frame;

	private MusicVideoHandler manager;

	@Override
	public void start(Stage primaryStage) {
		this.frame = new MainWindow(primaryStage);
		this.frame.createStage();

	}

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

		App mainApp = new App();

		mainApp.manager = new MusicVideoHandler();

		mainApp.startUp();

		launch(args);

		mainApp.onClose();

	}

	private void startUp() {

		// load previous saved settings
		File savedSettings = Paths.get("settings.json").toFile();

		if (savedSettings.exists()) {
			this.manager.setSettingsData(ExportImportSettings.readSettings(savedSettings));

			// update the music video list
			this.manager.updateMusicVideoList();
		}
	}

	private void onClose() {

		// load previous saved settings
		File savedSettings = Paths.get("settings.json").toFile();

		if (!savedSettings.exists()) {
			// save changes if nothing is there
			ExportImportSettings.writeSettings(savedSettings, this.manager.getSettingsData());
		}
	}
}
