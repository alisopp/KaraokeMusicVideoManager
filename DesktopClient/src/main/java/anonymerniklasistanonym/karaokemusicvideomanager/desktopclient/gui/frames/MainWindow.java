package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui.frames;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Everything about the main Window
 * 
 * @author nikla
 *
 */
public class MainWindow {

	public static JFrame createMainWindow() {
		JFrame guiMainFrame = new JFrame();
		// start the global window/JFrame

		guiMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make sure the program exits when the frame closes
		guiMainFrame.setTitle("Karaoke Desktop Client [Beta]");
		// title of the window
		guiMainFrame.setSize(500, 620);
		// size of the window at the start
		guiMainFrame.setLocationRelativeTo(null);
		// let it pop up in the middle of the screen
		guiMainFrame.setMinimumSize(new Dimension(400, 270));

		return guiMainFrame;
	}

}
