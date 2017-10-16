package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;

public class MusicVideoHandlerText {

	public static void main(String[] args) {

		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("user.home"));

		MusicVideoHandler testHandler = new MusicVideoHandler();

		// load settings
		if (testHandler.settingsFileExist()) {
			testHandler.loadSettingsFromFile();

			// update the music video list
			testHandler.updateMusicVideoList();
		}

		// add file paths
		testHandler.addPathToPathList(Paths.get("..\\CreateDemoFiles\\demo_files"));
		testHandler.addPathToPathList(Paths.get("..\\CreateDemoFiles\\demo_files_small"));

		// generate/update videos from all paths
		testHandler.updateMusicVideoList();

		// print wrong formatted files
		testHandler.printWrongFormattedFiles();

		// print current list to console
		testHandler.printMusicVideoList();

		// test saving current list
		testHandler.saveHtmlList(Paths.get("C:\\Users\\nikla\\Downloads\\XAMPP Portable\\htdocs"), true);
		testHandler.saveHtmlSearch(Paths.get("C:\\Users\\nikla\\Downloads\\XAMPP Portable\\htdocs"), true);
		testHandler.saveHtmlParty(Paths.get("C:\\Users\\nikla\\Downloads\\XAMPP Portable\\htdocs"), true);

		// test open a video
		testHandler.openMusicVideo(0);
		testHandler.openMusicVideo(2);
		testHandler.openMusicVideo(5993);
		testHandler.openMusicVideo(5994);

		// test saving current list to different file formats
		testHandler.saveCsv(Paths.get("test.csv"));
		testHandler.saveJson(Paths.get("test.json"));

		// save settings
		if (testHandler.settingsFileExist()) {

			// save changes if nothing is there
			if (testHandler.compareSettings()) {
				System.out.println("Files are the same");
			} else {
				System.out.println("Overwrite file because there are changes!");
				testHandler.saveSettingsToFile();
			}
		} else {
			System.out.println("No file found, save changes");
			testHandler.saveSettingsToFile();
		}
	}

}
