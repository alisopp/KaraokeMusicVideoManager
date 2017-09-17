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
			System.out.println("first");
			testHandler.setSettingsData(ExportImportSettings.readSettings(savedSettings));

			// update the music video list
			testHandler.updateMusicVideoList();
		}

		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke " + "test\\1"));
		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke " + "test\\2"));
		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke " + "test\\3"));
		testHandler.updateMusicVideoList();

		testHandler.printMusicVideoList();

		testHandler.openMusicVideo(0);
		testHandler.openMusicVideo(2);
		testHandler.openMusicVideo(5993);
		testHandler.openMusicVideo(5994);
		if (!savedSettings.exists()) {
			// save changes if nothing is there
			ExportImportSettings.writeSettings(savedSettings, testHandler.getSettingsData());
		}
	}

}
