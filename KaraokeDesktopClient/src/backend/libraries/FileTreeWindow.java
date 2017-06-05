package backend.libraries;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.ActionHandler;
import backend.language.LanguageController;
import backend.objects.MusicVideo;
import frontend.ConceptJFrameGUI;

/**
 * JFrame and methods for an editable JTree
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.7 (beta)
 */
public class FileTreeWindow extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String ADD_COMMAND = LanguageController.getTranslation("add folder/s");
	private static final String REMOVE_COMMAND = LanguageController.getTranslation("remove folder");
	private static final String REMOVES_COMMAND = LanguageController.getTranslation("remove sub folders");
	private static final String RELOAD_COMMAND = LanguageController.getTranslation("reload");
	private static final String[] commands = { ADD_COMMAND, REMOVE_COMMAND, REMOVES_COMMAND, RELOAD_COMMAND };

	private JFrame frame;
	private static JLabel selectedLabel;

	private FileTree treePanel;

	private ActionHandler actionHandler;
	private ConceptJFrameGUI conceptJFrameGUI;

	public FileTreeWindow(ActionHandler actionHandler, ConceptJFrameGUI conceptJFrameGUI) {
		super(new BorderLayout());

		this.actionHandler = actionHandler;
		this.conceptJFrameGUI = conceptJFrameGUI;

		// Create the components.
		treePanel = new FileTree();
		treePanel.setPreferredSize(new Dimension(400, 250));

		this.add(treePanel, BorderLayout.NORTH);

		selectedLabel = new JLabel();
		add(selectedLabel, BorderLayout.CENTER);

		JPanel panel = new JPanel(new GridLayout(0, commands.length));

		for (String a : commands) {

			JButton button;

			String pathToImageIcon = null;
			if (a.equals(ADD_COMMAND)) {
				pathToImageIcon = "/add_20x20.png";
			} else if (a.equals(REMOVE_COMMAND) || a.equals(REMOVES_COMMAND)) {
				pathToImageIcon = "/remove_20x20.png";
			} else if (a.equals(RELOAD_COMMAND)) {
				pathToImageIcon = "/reload_20x20.png";
			}

			ImageIcon iconRandom = null;
			if (pathToImageIcon != null) {
				try {
					iconRandom = new ImageIcon(ImageIO.read(FileTreeWindow.class.getResource(pathToImageIcon)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			// and make a button with an icon (or not if null)
			button = new JButton(" " + a.toString(), iconRandom);

			// set command, Action Listener and add it to the panel
			button.setActionCommand(a);
			button.addActionListener(this);
			panel.add(button);
		}

		this.add(panel, BorderLayout.SOUTH);

		populateTree(actionHandler.getPathList());
	}

	public static void setLabel(String a) {
		selectedLabel.setText(a);
	}

	private void populateTree(ArrayList<Path> listOfPaths) {

		// first clear the music video list (not necessary, but for 100%
		// sureness) - it gets instantly filled again
		actionHandler.clearMusicVideosList();

		// for all paths:
		for (Path a : listOfPaths) {

			// convert path to usable String / String[] with all levels
			String[] splittedFileName = a.toString().split(Pattern.quote(System.getProperty("file.separator")));

			// create a walking parent node
			DefaultMutableTreeNode walkingParentNode = null;

			// walk through all folder levels
			for (String currentFolderLevel : splittedFileName) {

				// if the folder level doesn't already exist
				if (treePanel.searchNode(currentFolderLevel, splittedFileName[0]) == null) {

					// we create it
					DefaultMutableTreeNode newFolderLevelNode = treePanel.addObject(walkingParentNode,
							currentFolderLevel, true);
					// and set it to our new walking parent node
					walkingParentNode = newFolderLevelNode;

				} else {
					// else we search for the existing folder and mark him as
					// our new walking parent node
					walkingParentNode = treePanel.searchNode(currentFolderLevel, splittedFileName[0]);
				}
			}

			// add music videos to each folder:
			boolean noMusicVideosFound = true;

			// scan for each path
			for (MusicVideo videoElement : actionHandler.scanDirectory(a)) {
				noMusicVideosFound = false;

				// add to every music video file also the original file name
				treePanel.addObject(walkingParentNode, videoElement.getPath().getFileName().toString());
			}

			// add this node so that even empty folders appear as folders
			if (noMusicVideosFound)
				treePanel.addObject(walkingParentNode, LanguageController.getTranslation("no music videos"));
		}
	}

	/**
	 * Here are all commands defined
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(ADD_COMMAND)) {
			// add dialogue
			actionHandler.addToPathList(actionHandler.getPathOfDirectories());
			// update tree window
			updateTree();
		} else if (e.getActionCommand().equals(REMOVE_COMMAND)) {
			// check if selected path is in path list
			if (actionHandler.getPathList().contains(treePanel.getSelectedNodePath())) {
				// now ask the user if he wants to delete this specific folder
				int a = JOptionPane.showConfirmDialog(null,
						LanguageController.getTranslation("Do you really want to delete the path") + ": \""
								+ treePanel.getSelectedNodePath().toString() + "\"",
						LanguageController.getTranslation("Warning"), JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (a == JOptionPane.YES_OPTION) {
					// give back an error if there was an problem, else just
					// delete the path
					if (actionHandler.deletePathFromPathList(treePanel.getSelectedNodePath()) == false) {
						JOptionPane.showMessageDialog(null,
								LanguageController.getTranslation("The path could not be deleted") + "! "
										+ LanguageController.getTranslation("Only added paths can be deleted") + "!");
					}
				}
			} else {
				// if not give an error back
				JOptionPane.showMessageDialog(null, LanguageController.getTranslation("The path could not be deleted")
						+ "! " + LanguageController.getTranslation("Only added paths can be deleted") + "!");
			}
			updateTree();
		} else if (e.getActionCommand().equals(REMOVES_COMMAND)) {
			// because we have always something beneath us in the JTree from the
			// path list we don't need to check

			// now we add all selected paths to an ArrayList
			Path[] currentlySelectedPaths = treePanel.getCurrentSelectedPaths();

			// now ask the user if he really wants to delete all these sources
			int a = JOptionPane.showConfirmDialog(null,
					LanguageController.getTranslation("Do you really want to delete the path") + ": \""
							+ treePanel.getSelectedNodePath().toString() + "\" "
							+ LanguageController.getTranslation("and anything beneath it") + "?",
					LanguageController.getTranslation("Warning"), JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (a == JOptionPane.YES_OPTION) {

				// if he confirms we delete them all directly from the path list
				for (Path currentPath : currentlySelectedPaths) {
					if (actionHandler.getPathList().contains(currentPath)) {
						actionHandler.deletePathFromPathList(currentPath);
					}
				}
			}
			// and update after that the JTable and the JTree
			updateTree();
		} else if (e.getActionCommand().equals(RELOAD_COMMAND)) {
			// update the JTable and the JTree
			updateTree();
		}
	}

	/**
	 * Update the JTree in the window and the table in the main window
	 */
	private void updateTree() {
		conceptJFrameGUI.updateTable();
		treePanel.clear();
		populateTree(actionHandler.getPathList());
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public void createAndShowGUI() {

		// Get the Windows look on Windows computers
		ActionHandler.windowsLookActivator();

		// Create and set up the window.
		frame = new JFrame(LanguageController.getTranslation("Source folder editor"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		frame.setContentPane(this);

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);
	}

	public void closeIt() {
		frame.dispose();
	}
}