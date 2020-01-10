import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;

public class TestSettingsFileHandling {

	private ProgramDataHandler dataHandler;

	@Before
	public void setUp() throws Exception {
		dataHandler = new ProgramDataHandler();
	}

	@Test
	public void testRemoveFromPathList() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\");
		File f2 = new File("C:\\Users\\Manuel\\Desktop\\reference\\");
		File f3 = new File("C:\\Users\\Manuel\\Desktop\\output\\");
		File f4 = new File("C:\\Users\\Manuel\\Desktop\\temp\\");

		dataHandler.addPathToPathList(f1.toPath());
		dataHandler.addPathToPathList(f2.toPath());
		dataHandler.addPathToPathList(f3.toPath());
		dataHandler.addPathToPathList(f4.toPath());
		
		dataHandler.removeFromPathList(f3.toPath());
		
		for (Path path : dataHandler.getPathList()) {
			assertNotSame(f3.toPath().toString(), path.toString());
		}
			
	}
	
	@Test
	public void testRemoveFromPathListEmpty() {
		boolean result = dataHandler.removeFromPathList(null);
		
		assertEquals(false, result);			
	}
	
	@Test
	public void testRemoveFromPathListWrong() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\");
		File f2 = new File("C:\\Users\\Manuel\\Desktop\\reference\\");

		dataHandler.addPathToPathList(f1.toPath());
		boolean result = dataHandler.removeFromPathList(f2.toPath());
		
		assertEquals(false, result);			
	}
	
	@Test
	public void testRemoveFromPathListOne() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\");

		dataHandler.addPathToPathList(f1.toPath());
		boolean result = dataHandler.removeFromPathList(f1.toPath());
		
		assertEquals(true, result);			
	}
	
	@Test
	public void testRemoveFromPathListAll() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\");
		File f2 = new File("C:\\Users\\Manuel\\Desktop\\reference\\");
		File f3 = new File("C:\\Users\\Manuel\\Desktop\\output\\");
		File f4 = new File("C:\\Users\\Manuel\\Desktop\\temp\\");

		dataHandler.addPathToPathList(f1.toPath());
		dataHandler.addPathToPathList(f2.toPath());
		dataHandler.addPathToPathList(f3.toPath());
		dataHandler.addPathToPathList(f4.toPath());
		
		dataHandler.removeFromPathList(f1.toPath());
		dataHandler.removeFromPathList(f2.toPath());
		dataHandler.removeFromPathList(f3.toPath());
		dataHandler.removeFromPathList(f4.toPath());
		
		assertArrayEquals (null, dataHandler.getPathList());
	}
	
	@Test
	public void testRemoveFromPathListAllExceptOne() {
		File f1 = new File("C:\\Users\\Manuel\\Desktop\\data\\");
		File f2 = new File("C:\\Users\\Manuel\\Desktop\\reference\\");
		File f3 = new File("C:\\Users\\Manuel\\Desktop\\output\\");
		File f4 = new File("C:\\Users\\Manuel\\Desktop\\temp\\");

		dataHandler.addPathToPathList(f1.toPath());
		dataHandler.addPathToPathList(f2.toPath());
		dataHandler.addPathToPathList(f3.toPath());
		dataHandler.addPathToPathList(f4.toPath());
		
		dataHandler.removeFromPathList(f1.toPath());
		dataHandler.removeFromPathList(f2.toPath());
		dataHandler.removeFromPathList(f4.toPath());
		
		assertEquals(1, dataHandler.getPathList().length);
	}

}
