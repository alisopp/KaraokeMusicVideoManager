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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import backend.ActionHandler;
import backend.objects.MusicVideo;
import frontend.ConceptJFrameGUI;

public class FileTreeWindow extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String ADD_COMMAND = "add folder/s";
	private static final String REMOVE_COMMAND = "remove folder/s";
	private static final String RELOAD_COMMAND = "reload";
	private static final String[] commands = { ADD_COMMAND, REMOVE_COMMAND, RELOAD_COMMAND };

	private static JFrame frame;

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

		add(treePanel, BorderLayout.CENTER);

		JPanel panel = new JPanel(new GridLayout(0, commands.length));

		for (String a : commands) {

			JButton button;
			if (a.equals(ADD_COMMAND) || a.equals(REMOVE_COMMAND)) {
				String pathToImageIcon = "";
				switch (a) {
				case ADD_COMMAND:
					pathToImageIcon = "/add_20x20.png";
					break;
				case REMOVE_COMMAND:
					pathToImageIcon = "/remove_20x20.png";
					break;
				}
				ImageIcon iconRandom = null;
				try {
					iconRandom = new ImageIcon(ImageIO.read(FileTreeWindow.class.getResource(pathToImageIcon)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				button = new JButton(" " + a.toString(), iconRandom);
			} else {
				button = new JButton(a.toString());
			}
			button.setActionCommand(a);
			button.addActionListener(this);
			panel.add(button);
		}

		add(panel, BorderLayout.SOUTH);

		populateTree(actionHandler.getPathList());
	}

	private void populateTree(ArrayList<Path> listOfPaths) {

		actionHandler.clearMusicVideosList();

		for (Path a : listOfPaths) {

			String pattern = Pattern.quote(System.getProperty("file.separator"));
			String[] splittedFileName = a.toString().split(pattern);
			System.out.println(a);

			String[] splittedFileName2 = new String[splittedFileName.length - 1];
			for (int i = 0; i < splittedFileName.length - 1; i++) {
				splittedFileName2[i] = splittedFileName[i];
			}

			System.out.println(splittedFileName[0]);

			DefaultMutableTreeNode b = null;

			for (String cc : splittedFileName) {

				System.out.println("\t" + cc);

				if (treePanel.searchNode(cc, splittedFileName[0]) == null) {
					DefaultMutableTreeNode bb = treePanel.addObject(b, cc);
					b = bb;
				} else {
					b = treePanel.searchNode(cc, splittedFileName[0]);
				}
			}

			// add music videos to each folder
			boolean noMusicVideosFound = true;

			for (MusicVideo videoElement : actionHandler.scanDirectory(a)) {
				noMusicVideosFound = false;

				String s = videoElement.getPath().toString();
				String file = s.substring(s.lastIndexOf("\\"));
				String extension = file.substring(file.indexOf("."));

				treePanel.addObject(b, videoElement.getArtist() + " - " + videoElement.getTitle() + extension);
			}

			if (noMusicVideosFound)
				treePanel.addObject(b, "no music videos");
		}
	}

	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case ADD_COMMAND:
			// add dialogue
			actionHandler.addToPathList(actionHandler.getPathOfDirectories());
			// update tree window
			updateTree();
			break;
		case REMOVE_COMMAND:
			// Remove button clicked
			// hi.deletePathFromPathList(flags);

			if (actionHandler.getPathList().contains(treePanel.getSelectedNodePath())) {
				int a = JOptionPane.showConfirmDialog(null,
						"Do you really want to delete the path: \"" + treePanel.getSelectedNodePath().toString() + "\"",
						"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (a == JOptionPane.YES_OPTION) {
					if (actionHandler.deletePathFromPathList(treePanel.getSelectedNodePath()) == false) {
						JOptionPane.showMessageDialog(null,
								"The path could not be deleted" + ". " + "Only added paths can be deleted.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"The path cannot be deleted" + ". " + "Only added paths can be deleted.");
			}

			updateTree();
			break;
		case RELOAD_COMMAND:
			// Reload the JTree
			updateTree();
			break;
		}
	}

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
		frame = new JFrame("Beta beta path editor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		// FileTreeWindow newContentPane = new FileTreeWindow(hi, we);
		// this.setOpaque(true); // content panes must be opaque
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