package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.nio.file.Paths;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoHandler;

public class LocalTest {

	public static void main(String[] args) {

		MusicVideoHandler testHandler = new MusicVideoHandler();
		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke test\\1"));
		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke test\\2"));
		testHandler.addPathToPathList(Paths.get("c:\\Users\\nikla\\Downloads\\karaoke test\\3"));
		testHandler.updateMusicVideoList();

		testHandler.printMusicVideoList();

		testHandler.openMusicVideo(0);
		testHandler.openMusicVideo(2);
		testHandler.openMusicVideo(5993);
		testHandler.openMusicVideo(5994);
	}

}
