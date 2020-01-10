import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestFileOperations {
	
	private IgnoredFilesWindowControllerTest controller;

	@Before
	public void setUp() {
		controller = new IgnoredFilesWindowControllerTest();
	}
	
	@Test
	public void testRenameFileOperation() {
		boolean result = controller.renameFile();
		assertTrue(result);
	}

}
