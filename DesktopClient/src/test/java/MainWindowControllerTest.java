import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.controller.MainWindowController;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.MusicVideoHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler.ProgramDataHandler;

public class MainWindowControllerTest extends MainWindowController {
	
	private ProgramDataHandler dataHandler;
	private MusicVideoHandler handler;
	
	public MainWindowControllerTest() {
		dataHandler = new ProgramDataHandler();
		handler = new MusicVideoHandler(dataHandler);
	}
	public MusicVideoHandler getMusicVideoHandler() 			{		return handler; 	}
	public void setContextPlaylistUndoVisibility(boolean value) {		int i=0; i++;	}
	public void refreshMusicVideoPlaylistTable() 				{		int j=0; j++;	}
}
