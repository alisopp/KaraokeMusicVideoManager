import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;

public class TestProgramDataHandler {

	private ProgramDataHandler dataHandler;
	private Path outPath;

	@Before
	public void setUp() throws Exception {
		dataHandler = new ProgramDataHandler();
		outPath = new File("C:\\Users\\Manuel\\Desktop\\output").toPath();
	}

	@Test
	public void testcreateSettings() {
		dataHandler.createSettings();
	}

	@Test
	public void testaddPathToPathList() {
		assertEquals(true, dataHandler.addPathToPathList(outPath));
	}

	@Test
	public void testloadSettingsFromFile() {
		dataHandler.loadSettingsFromFile();
	}

	@Test
	public void testsaveSettingsToFile() {
		dataHandler.loadSettingsFromFile();
		assertEquals(true, dataHandler.saveSettingsToFile());
	}

	@Test
	public void testcompareSettings() {
		dataHandler.loadSettingsFromFile();
		assertEquals(true, dataHandler.compareSettings());
	}

}
