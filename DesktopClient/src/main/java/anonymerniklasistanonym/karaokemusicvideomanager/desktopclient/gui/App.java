package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Main method. The whole program runs here.
 *
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com>
 * @version 0.9.1
 */
public class App {

	public static void main(String[] args) {

		// first recreate the main parts here
		// but after all the language thing and the rest needs to be way simpler

		// load data if there is something
		// * config file json
		// * language
		// -> show progress window with current reading file

		// create main window
		// * load table into window

		// new features:
		// paths, about, save & restore, network, export, settings
		// random video gives back list of number random videos (3, but it should be
		// editable in settings)

		// needed:
		// * FTP/SFTP write delete and scan module
		// * JSON read and write module
		// * Live window with the next (number - 10) videos -> Playlist (option to
		// delete
		// and view the video)
		// left click adds video to playlist/deletes the file -> if context menu is
		// possible
		// * tree view need's to be rewritten that the same folder name can exist two
		// times and the delete process works
		// * offline playlist alternative on left click without network
		// network exit or begin moves current playlist (local added elements are
		// labeled
		// [local], online ones are [server]
		// * simple PHP script export for server with icons and everything
		// * new HTML with JavaScript list where you can click an element, get to a new
		// site, get displayed the song and a form with descripton, author and a send
		// and abort button -> this gets send to the PHP page and returns to the main
		// list
		// * export a barcode to list site as HTML file with address input (choose table
		// size before creating)

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
		// set a minimum size
		guiMainFrame.setVisible(true);
	}
}
