import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoPlaylistHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

public class TestPlaylistHandling {

	private final String DAT_DIR = "C:\\Users\\Manuel\\Desktop\\data\\";
	private MainWindowControllerTest windowController;
	private MusicVideoHandler musicVideoHandler;
	private MusicVideoPlaylistHandler musicVideoPlaylistHandler;
	private MusicVideoPlaylistElement element;
	
	@Before
	public void setUp() throws Exception {
		windowController = new MainWindowControllerTest();
		MusicVideo video = new MusicVideo(new File(DAT_DIR + "mus - vid1.mp4").toPath(), "title 1", "artist 1");
		element = new MusicVideoPlaylistElement(0, video, "author 1", "no comment", true);
		musicVideoHandler = windowController.getMusicVideoHandler();
		musicVideoPlaylistHandler = musicVideoHandler.getPlaylistHandler();
		musicVideoPlaylistHandler.load(element);
	}

	@Test
	public void testAddRemovedPlaylistEntryReset() {
		windowController.addRemovedPlaylistEntry();
		MusicVideoHandler handler = windowController.getMusicVideoHandler();
		MusicVideoPlaylistHandler playlistHandler = handler.getPlaylistHandler();
		MusicVideoPlaylistElement element = playlistHandler.getLatestStartedMusicVideoPlaylistEntry();
		assertTrue(element == null);
	}

	@Test
	public void testAddRemovedPlaylistEntryRemoval() {
		windowController.addRemovedPlaylistEntry();
		MusicVideoPlaylistElement removedEntry = windowController.getMusicVideoHandler().removeEntryFromPlaylist(0);
		
		assertEquals(element.getFileName(), removedEntry.getFileName());
	}
	
	@Test
	public void testAddRemovedPlaylistEntryFailure() {
		windowController.addRemovedPlaylistEntry();
		MusicVideoPlaylistElement removedEntry = windowController.getMusicVideoHandler().removeEntryFromPlaylist(-1);
		
		assertEquals(null, removedEntry);
	}

	@Test
	public void testAddRemovedPlaylistEntryViolation() {
		windowController.addRemovedPlaylistEntry();
		MusicVideoPlaylistElement removedEntry = windowController.getMusicVideoHandler().removeEntryFromPlaylist(10);
		
		assertEquals(null, removedEntry);
	}

	@Test
	public void testAddRemovedPlaylistEntryTwice() {
		windowController.addRemovedPlaylistEntry();
		MusicVideoPlaylistElement removedEntry = windowController.getMusicVideoHandler().removeEntryFromPlaylist(10);
		
		musicVideoPlaylistHandler.load(element);
		windowController.addRemovedPlaylistEntry();
		
		assertEquals(null, removedEntry);
	}
}
