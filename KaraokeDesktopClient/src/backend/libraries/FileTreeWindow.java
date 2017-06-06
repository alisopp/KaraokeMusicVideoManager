package backend.libraries;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

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

	private static String ADD_COMMAND = LanguageController.getTranslation("add folder");
	private static String REMOVE_COMMAND = LanguageController.getTranslation("remove folder");
	private static String REMOVES_COMMAND = LanguageController.getTranslation("remove sub folders");
	private static String RELOAD_COMMAND = LanguageController.getTranslation("reload");
	private static String GET_BAD_FILES_COMMAND = LanguageController.getTranslation("get bad files");
	private static String[] command1 = { ADD_COMMAND, REMOVE_COMMAND };
	private static String[] command2 = { REMOVES_COMMAND, RELOAD_COMMAND, GET_BAD_FILES_COMMAND };
	private static String[][] commands = { command1, command2 };

	private JFrame frame;
	private static JLabel selectedLabel;

	private FileTree treePanel;

	private ActionHandler actionHandler;
	private ConceptJFrameGUI conceptJFrameGUI;

	private boolean windowOpen = false;

	public FileTreeWindow(ActionHandler actionHandler, ConceptJFrameGUI conceptJFrameGUI) {
		super(new BorderLayout());

		this.actionHandler = actionHandler;
		this.conceptJFrameGUI = conceptJFrameGUI;

		// Create the components.
		treePanel = new FileTree();
		treePanel.setPreferredSize(new Dimension(400, 250));

		this.add(treePanel, BorderLayout.CENTER);

		JPanel panelBig = new JPanel(new GridLayout(3, 0));
		selectedLabel = new JLabel();
		panelBig.add(selectedLabel);

		for (String[] currentCommand : commands) {
			JPanel panel = new JPanel(new GridLayout(0, currentCommand.length));
			for (String a : currentCommand) {
				JButton button;

				String pathToImageIcon = null;
				if (a.equals(ADD_COMMAND)) {
					pathToImageIcon = "add_20x20.png";
				} else if (a.equals(REMOVE_COMMAND) || a.equals(REMOVES_COMMAND)) {
					pathToImageIcon = "remove_20x20.png";
				} else if (a.equals(RELOAD_COMMAND)) {
					pathToImageIcon = "reload_20x20.png";
				} else if (a.equals(GET_BAD_FILES_COMMAND)) {
					pathToImageIcon = "wrongFilenameFormat_20x20.png";
				}

				ImageIcon iconRandom = null;
				if (pathToImageIcon != null) {
					iconRandom = ActionHandler.loadImageIconFromClass("/icons/" + pathToImageIcon);
				}

				// and make a button with an icon (or not if null)
				button = new JButton(" " + a.toString(), iconRandom);

				// set command, Action Listener and add it to the panel
				button.setActionCommand(a);
				button.addActionListener(this);
				button.setPreferredSize(new Dimension(200, 40));
				panel.add(button);
			}
			panelBig.add(panel);
		}

		this.add(panelBig, BorderLayout.SOUTH);
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
			if (noMusicVideosFound && walkingParentNode.isLeaf())
				treePanel.addObject(walkingParentNode, LanguageController.getTranslation("no music videos"));
		}
	}

	/**
	 * Here are all commands defined
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(ADD_COMMAND)) {
			// add dialogue
			if (actionHandler.addToPathList(actionHandler.getPathOfDirectories()))
				updateTree(); // update tree window
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
					} else {
						updateTree();
					}
				}
			} else {
				// if not give an error back
				JOptionPane.showMessageDialog(null, LanguageController.getTranslation("The path could not be deleted")
						+ "! " + LanguageController.getTranslation("Only added paths can be deleted") + "!");
			}

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
				// and update after that the JTable and the JTree
				updateTree();
			}
		} else if (e.getActionCommand().equals(RELOAD_COMMAND)) {
			// update the JTable and the JTree
			updateTree();
		} else if (e.getActionCommand().equals(GET_BAD_FILES_COMMAND)) {
			// give back the not correct formatted probably music videos
			actionHandler.getWrongFormattedMusicVideos();
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
		windowOpen = true;

		// Get the Windows look on Windows computers
		ActionHandler.windowsLookActivator();

		// Create and set up the window.
		frame = new JFrame(LanguageController.getTranslation("Source folder editor"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// get the default program icon
		ActionHandler.setProgramWindowIcon(frame);

		// Create and set up the content pane.
		frame.setContentPane(this);

		// fill the tree
		treePanel.clear();
		populateTree(actionHandler.getPathList());

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);
	}

	public void closeIt() {
		if (windowOpen == true) {
			frame.dispose();
		} else {
			System.err.println("Window wasn't even open!");
		}
		windowOpen = false;
	}

	public boolean getIfWindowOpen() {
		return windowOpen;
	}
}