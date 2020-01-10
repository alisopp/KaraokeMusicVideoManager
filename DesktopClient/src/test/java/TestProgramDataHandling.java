import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;

public class TestProgramDataHandling {
	
	private ProgramDataHandler programHandler;
	
	@Before
	public void setUp() throws Exception {
		programHandler = new ProgramDataHandler();
	}

	@Test
	public void testAcceptedFileTypes() {
		String[] acceptedFileTypes = new String[] { "avi", "mkv", "mp4" };
		programHandler.setAcceptedFileTypes(acceptedFileTypes);

		assertArrayEquals(acceptedFileTypes, programHandler.getAcceptedFileTypes());
	}
	
	@Test
	public void testUserName() {
		String userName = "User1";
		programHandler.setUserNameSftp(userName);

		assertEquals(userName, programHandler.getUserNameSftp());
	}
	
	@Test
	public void testWorkingDirectory() {
		String workingDir = "C:\\Users\\User\\data";
		programHandler.setWorkingDirectorySftp(workingDir);

		assertEquals(workingDir, programHandler.getWorkingDirectorySftp());
	}
	
	@Test
	public void testAlwaysSaveSettings() {
		boolean alwaysSave = true;
		
		programHandler.setAlwaysSaveSettings(alwaysSave);

		assertEquals(alwaysSave, programHandler.getAlwaysSaveSettings());
	}
	
	@Test
	public void testIgnoredFileList() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\mus - vid1.mp4");
		File f2 = new File("C:\\Users\\Manuel\\Desktop\\data\\mus - vid2.mp4");
		File f3 = new File("C:\\Users\\Manuel\\Desktop\\data\\mus - vid3.mp4");
		File f4 = new File("C:\\Users\\Manuel\\Desktop\\data\\mus - vid4.mp4");
		File[] ignoredFiles = new File[] { f1, f2, f3, f4 };
		
		programHandler.setIgnoredFiles(ignoredFiles);

		assertArrayEquals(ignoredFiles, programHandler.getIgnoredFilesList());
	}

}
