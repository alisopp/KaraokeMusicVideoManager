package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;

public class LocalTest {

	public static void main(String[] args) {

		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("user.home"));

		MusicVideoHandler testHandler = new MusicVideoHandler();

		File savedSettings = Paths.get("settings.json").toFile();

		if (savedSettings.exists()) {
			testHandler.loadSettings(savedSettings);

			// update the music video list
			testHandler.updateMusicVideoList();
		}

		testHandler.addPathToPathList(Paths.get("C:\\Users\\nikla\\Downloads\\karaoke " + "test\\1"));
		testHandler.addPathToPathList(Paths.get("..\\CreateDemoFiles\\demo_files"));
		// " + "test\\2"));
		// testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke
		// " + "test\\3"));
		testHandler.updateMusicVideoList();

		testHandler.printWrongFormattedFiles();

		// testHandler.printMusicVideoList();

		// testHandler.saveHtmlParty(Paths.get("C:\\Users\\nikla\\Downloads\\XAMPP
		// Portable\\htdocs"), true);
		// testHandler.saveHtmlSearch(Paths.get("C:\\Users\\nikla\\Downloads"), true);
		// testHandler.saveHtmlList(Paths.get("C:\\Users\\nikla\\Downloads"), true);

		//
		// testHandler.openMusicVideo(0);
		// testHandler.openMusicVideo(2);
		// testHandler.openMusicVideo(5993);
		// testHandler.openMusicVideo(5994);
		//
		// Path htmlSite = Paths.get("index.html");
		// testHandler.saveFileHtmlBasic(htmlSite);

		// testHandler.saveCsv(Paths.get("basic.csv"));

		// testHandler.saveJson(Paths.get("basic.json"));

		// testHandler.saveFileHtmlBasic(Paths.get("basic.html"));

		// testHandler.saveFileHtmlSearchable(Paths.get("search.html"),
		// Paths.get("w3.js"));

		// testHandler.saveFileHtmlParty(Paths.get("party.html"), Paths.get("w3.js"));

		if (savedSettings.exists()) {
			// save changes if nothing is there
			if (testHandler.compareSettings(savedSettings)) {
				System.out.println("Files are the same");
			} else {
				System.out.println("Overwrite file because there are changes!");
				testHandler.saveSettings(savedSettings);
			}
		} else {
			System.out.println("No file found, save changes");
			testHandler.saveSettings(savedSettings);
		}
	}

}
