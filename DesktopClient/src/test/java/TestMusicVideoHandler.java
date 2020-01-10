import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;

public class TestMusicVideoHandler {

	private ProgramDataHandler dataHandler;
	private MusicVideoHandler handler;
	private Path outPath, outPath2;
	private File outFile;

	@Before
	public void setUp() throws Exception {
		dataHandler = new ProgramDataHandler();
		dataHandler.setPathList(new Path[] { new File("C:\\Users\\Manuel\\Desktop\\data").toPath() } );
		handler = new MusicVideoHandler(dataHandler);
		handler.updateMusicVideoList();
		
		int i=0;
		for (MusicVideo mv : handler.getMusicVideoList()) {
			handler.getPlaylistHandler().add(i++, mv, mv.getArtist(), "comment" + i);
		}

		outPath = new File("C:\\Users\\Manuel\\Desktop\\output").toPath();
		outPath2 = new File("C:\\Users\\Manuel\\Desktop\\output2").toPath();
		outFile = new File("C:\\Users\\Manuel\\Desktop\\output\\out.txt");
	}

	@Test
	public void testaddMusicVideoToPlaylist() {
		handler.addMusicVideoToPlaylist(1, "author", "no comment");
		assertTrue(true);
	}


	@Test
	public void testeditMusicVideoToPlaylist() {
		handler.editMusicVideoToPlaylist(1, "author new", "no more comment");
		assertTrue(true);
	}
	
	@Test
	public void testcreatePhpDirectoryWithFiles() {
		handler.createPhpDirectoryWithFiles(outPath);
	}
	
	@Test
	public void testsaveHtmlParty() {
		assertEquals(true, handler.saveHtmlParty(outPath, true));
		assertEquals(true, handler.saveHtmlParty(outPath2, false));
	}
	
	@Test
	public void testsaveCsv() {
		assertEquals(true, handler.saveCsv(outFile.toPath()));
	}
	
	@Test
	public void testsaveHtmlList() {
		assertEquals(true, handler.saveHtmlList(outPath, true));
		assertEquals(true, handler.saveHtmlList(outPath2, false));
	}
	
	@Test
	public void testsaveHtmlSearch() {
		assertEquals(true, handler.saveHtmlSearch(outPath, true));
		assertEquals(true, handler.saveHtmlSearch(outPath2, false));
	}
	
	@Test
	public void testsaveGeneric() {
		assertEquals(true, handler.saveJson(outFile.toPath()));
		assertEquals(true, handler.savePlaylist(outFile));
	}
	
	
}
