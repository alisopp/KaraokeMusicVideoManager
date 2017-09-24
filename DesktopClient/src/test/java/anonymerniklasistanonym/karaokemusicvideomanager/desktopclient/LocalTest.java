package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.io.File;
import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportImportSettings;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoHandler;

public class LocalTest {

	public static void main(String[] args) {

		MusicVideoHandler testHandler = new MusicVideoHandler();

		File savedSettings = Paths.get("settings.json").toFile();

		if (savedSettings.exists()) {
			testHandler.setSettingsData(ExportImportSettings.readSettings(savedSettings));

			// update the music video list
			testHandler.updateMusicVideoList();
		}

		testHandler.addPathToPathList(Paths.get("C:\\Users\\nikla\\Downloads\\karaoke " + "test\\1"));
		testHandler.addPathToPathList(Paths.get("..\\CreateDemoFiles\\demo_files"));
		// " + "test\\2"));
		// testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke
		// " + "test\\3"));
		testHandler.updateMusicVideoList();

		testHandler.printMusicVideoList();
		//
		// testHandler.openMusicVideo(0);
		// testHandler.openMusicVideo(2);
		// testHandler.openMusicVideo(5993);
		// testHandler.openMusicVideo(5994);
		//
		// Path htmlSite = Paths.get("index.html");
		// testHandler.saveFileHtmlBasic(htmlSite);

		testHandler.saveFileHtmlBasic(Paths.get("basic.html"));

		testHandler.saveFileHtmlSearchable(Paths.get("search.html"), Paths.get("w3.js"));

		testHandler.saveFileHtmlParty(Paths.get("party.html"), Paths.get("w3.js"));

		if (savedSettings.exists()) {
			// save changes if nothing is there
			if (ExportImportSettings.compareSettingsFileToCurrent(savedSettings, testHandler.getSettingsData())) {
				System.out.println("Files are the same");
			} else {
				System.out.println("Overwrite file because there are changes!");
				ExportImportSettings.writeSettings(savedSettings, testHandler.getSettingsData());
			}
		} else {
			System.out.println("No file found, save changes");
			ExportImportSettings.writeSettings(savedSettings, testHandler.getSettingsData());
		}
	}

}
