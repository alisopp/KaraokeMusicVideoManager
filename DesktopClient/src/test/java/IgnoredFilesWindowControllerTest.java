import java.io.File;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller.IgnoredFilesWindowController;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.tables.WrongFormattedFilesTableView;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;

public class IgnoredFilesWindowControllerTest extends IgnoredFilesWindowController {
	
	private ProgramDataHandlerTest dataHandler;
	
	public IgnoredFilesWindowControllerTest() {
		dataHandler = new ProgramDataHandlerTest();
		dataHandler.setIgnoredFiles(new File[] { new File("C:\\Users\\Manuel\\Desktop\\data\\mus - vid1.mp4") } );
	}

	public WrongFormattedFilesTableView getWrongFormattedFilesTableView() {
		return new WrongFormattedFilesTableView("C:\\Users\\Manuel\\Desktop\\data");
	}
	public MusicVideoHandler getMusicVideoHandler() {		
		return new MusicVideoHandler(dataHandler);	
	}
	public ProgramDataHandler getDataHandler()		{		return dataHandler;	}
	public boolean getConfirmDialogResult() 		{		return true;	}
	public String getRenamedFile(File selectedFile) {		return selectedFile.getName();	}
	public void updateIgnoredFileTable() 			{		/* do nothing */	}
}
