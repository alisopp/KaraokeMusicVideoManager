import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoPlaylistHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

public class TestMusicVideoPlaylistHandler {

	private final String DAT_DIR = "C:\\Users\\Manuel\\Desktop\\data\\";
	private MusicVideoPlaylistHandler playlistHandler;

	@Before
	public void setUp() throws Exception {
		playlistHandler = new MusicVideoPlaylistHandler();
	}

	@Test
	public void testwritePlaylistEntryFile() {
		MusicVideo video = new MusicVideo(new File(DAT_DIR + "mus - vid1.mp4").toPath(), "title 1", "artist 1");
		MusicVideoPlaylistElement element = new MusicVideoPlaylistElement(0, video, "author 1", "no comment", true);
		playlistHandler.writePlaylistEntryFile(element);
	}
	
	@Test
	public void testreadPlaylistEntryFile() {
		playlistHandler.readPlaylistEntryFile(null);
	}

}
