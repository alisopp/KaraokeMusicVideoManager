package frontend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import backend.ActionHandler;

/**
 * The graphical user interface of this program. It combines the already written
 * back end code with a user friendly and feature rich graphical java swing
 * interface.
 * 
 * @author Niklas
 * @version 0.2
 *
 */
public class ConceptJFrameGUI {

	// All the management of the list and data already written
	private ActionHandler actionManager;

	// global window where the main things will happen
	private JFrame guiMainFrame;
	// a global JTable and DefaultTableModel;
	private JTable table;
	private DefaultTableModel model;
	// global file (for the configuration file)
	private File file;

	/**
	 * Constructor: When an object of ConceptJFrameGUI gets produced this
	 * automatically will happen:
	 */
	public ConceptJFrameGUI() {

		// start a new window/JFrame
		guiMainFrame = new JFrame();
		// start a ActionHandler for all the under the surface commands
		actionManager = new ActionHandler();
		// create a global filename for the configuration file
		file = new File("karaoke_desktop_config.abc");
	}

	/**
	 * At startup this method checks if a configuration file exist and loads it
	 * if it finds one. So a fast start for already set up party's/etc. is
	 * possible.
	 */
	public void startupConfig() {

		// if a configuration file exists
		if (actionManager.fileExists(file)) {
			// ask the user if he also wants to load saved data
			if (JOptionPane.showConfirmDialog(null, "Would you like to load your previous saved configuration? ",
					"Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				// if he accepts to load the data read the file and set the
				// content as your new path list
				actionManager.setPathList(actionManager.fileReader(file));
				// now scan again all paths for music videos
				actionManager.scanDirectories();
			}
		}
	}

	/**
	 * This method manages the main graphic user interface. Here you can find
	 * all features.
	 */
	public void createWindow() {

		guiMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make sure the program exits when the frame closes
		guiMainFrame.setTitle("Karaoke Desktop Client [Beta]");
		// title of the window
		guiMainFrame.setSize(500, 620);
		// size of the window at the start
		guiMainFrame.setLocationRelativeTo(null);
		// let it pop up in the middle of the screen

		// add a menu bar to the window
		JMenuBar menuBar = new JMenuBar();

		// with the sub menu "Source folders"
		JMenu subMenuPath = new JMenu("Source folders");
		subMenuPath.setToolTipText("Add/Change/Remove the actual folders of your music videos");
		// and the sub sub menu "Add" >>
		JMenuItem subSubMenuAddSourceFolder = new JMenuItem("Add");
		subSubMenuAddSourceFolder.setToolTipText("Add a new folder with new music videos to your list");
		subSubMenuAddSourceFolder.addActionListener((ActionEvent event) -> {
			System.out.println("Add a new folder with new music videos to your list");
			System.out.println("Remove a folder with music videos from your list");
			actionManager.addToPathList(actionManager.getDirectory());
			updateTable();
		});
		// and the sub sub menu "Remove" >>
		JMenuItem subSubMenuRemoveSourceFolder = new JMenuItem("Remove");
		subSubMenuRemoveSourceFolder.setToolTipText("Remove a folder with music videos from your list");
		subSubMenuRemoveSourceFolder.addActionListener((ActionEvent event) -> {
			pathEditorJFrame();
		});

		// with the sub menu "More"
		JMenu subMenuMore = new JMenu("More");
		subMenuMore.setToolTipText("Save your actual source folder paths and more in a config file for later");
		// and the sub sub menu "Save configuration" >>
		JMenuItem subSubMenuConfigurationSave = new JMenuItem("Save configuration");
		subSubMenuConfigurationSave
				.setToolTipText("Saves everything so you can start instantly at the next launch of the program");
		subSubMenuConfigurationSave.addActionListener((ActionEvent event) -> {
			System.out.println("Save the actual configuration (of folders)!");
			actionManager.fileOverWriter(file);
		});
		// and the sub sub menu "Load configuration" >>
		JMenuItem subSubMenuConfigurationLoad = new JMenuItem("Load configuration");
		subSubMenuConfigurationLoad.setToolTipText("Load configuration from a configuration file");
		subSubMenuConfigurationLoad.addActionListener((ActionEvent event) -> {
			System.out.println("dialog wich says please restart program - notify if a file even exit before this");
		});
		// and the sub sub menu "Export" >>
		JMenu subSubMenuExport = new JMenu("Export");
		subSubMenuExport.setToolTipText("Export your list to the following formats: CSV, HTML (, PDF)");
		// with the sub sub sub menu "Export to CSV" >>
		JMenuItem subSubSubMenuExportCSV = new JMenuItem("Export to CSV");
		subSubSubMenuExportCSV
				.setToolTipText("Saves everything so you can start instantly at the next launch of the program");
		subSubSubMenuExportCSV.addActionListener((ActionEvent event) -> {
		});
		// with the sub sub sub menu "Export to HTML" >>
		JMenuItem subSubSubMenuExportHTML = new JMenuItem("Export to HTML");
		subSubSubMenuExportHTML.setToolTipText("Load configuration from a configuration file");
		subSubSubMenuExportHTML.addActionListener((ActionEvent event) -> {
			System.out.println("Coming soon: HTML Export");
		});

		// add the menu buttons to the menu bar to the JFrame
		subMenuPath.add(subSubMenuAddSourceFolder);
		subMenuMore.addSeparator();
		subMenuPath.add(subSubMenuRemoveSourceFolder);
		menuBar.add(subMenuPath);
		subMenuMore.add(subSubMenuConfigurationSave);
		subMenuMore.addSeparator();
		subMenuMore.add(subSubMenuConfigurationLoad);
		subMenuMore.addSeparator();
		subSubMenuExport.add(subSubSubMenuExportCSV);
		subSubMenuExport.addSeparator();
		subSubMenuExport.add(subSubSubMenuExportHTML);
		subMenuMore.add(subSubMenuExport);
		menuBar.add(subMenuMore);
		guiMainFrame.setJMenuBar(menuBar);

		// later add icons:
		// ImageIcon iconExit = new ImageIcon("res/exit.gif");
		// JMenuItem newMi = new JMenuItem("New", iconNew);

		String[] columnNames = { "#", "Artist", "Title" };
		Object[][] data = actionManager.musicVideoListToTable();

		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);

		table.setAutoCreateRowSorter(true);

		// Place the JTable object in a JScrollPane for a scrolling table
		JScrollPane tableScrollPane = new JScrollPane(table);

		guiMainFrame.add(tableScrollPane);
		// guiFrame.setVisible(true);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.getSelectedRow();

				// int col = table.columnAtPoint(evt.getPoint());
				// int row = rowAtPoint(evt.getPoint());
				// int row2 = table.getSelectedRow();
				// int col2 = table.getSelectedColumn();
				// System.out.println("row: " + row + "\tcol: " + col);

				// System.out.println(karaokeOMat.getMusicVideosList().get(row).getPath());
				actionManager.openMusicVideo(row);
			}
		});

		// add a search bar with filter and input option
		JTextField jtfFilter = new JTextField();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Search for a music video:  "), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);
		guiMainFrame.add(panel, BorderLayout.NORTH);

		// action listener if the enter key was pressed
		Action action = new AbstractAction() {
			// eclipse was this, i dont't like orange light bulbs
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// get the actual text of the text field
				String scannedText = jtfFilter.getText();

				// check if it is a number (a String only containing digits)
				if (scannedText.matches("[-+]?\\d*\\.?\\d+")) {
					// if yes parse the String to an Integer
					int a = Integer.parseInt(scannedText);
					// and if the Integer is a number of the list
					if (a > 0 && a <= actionManager.getMusicVideosList().size()) {
						// open the music video on this position
						actionManager.openMusicVideo(a - 1);
					}
				}
			}
		};
		jtfFilter.addActionListener(action);

		// create a row sorter: But NOT to sort the data in the table manually
		// later, this is enabled by default! We need the row sorter for our
		// filter in the search bar
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

		});

		// play a random music video button
		JButton randomMusicvideoButton = new JButton("Play a random music video");
		randomMusicvideoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				int randomNum = actionManager.getRandomNumber(0, actionManager.getMusicVideosList().size());
				String randomSong = actionManager.getMusicVideosList().get(randomNum).getTitle() + " by "
						+ actionManager.getMusicVideosList().get(randomNum).getArtist() + "?";
				System.out.println("Random music video: ...Playing " + randomSong);

				if (JOptionPane.showConfirmDialog(null, "Would you like to play " + randomSong, "Info",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					actionManager.openMusicVideo(randomNum);
				} else {
					System.out.println("\tPlaying was denied by the user.");
				}
			}
		});
		guiMainFrame.add(randomMusicvideoButton, BorderLayout.SOUTH);

		// make the frame visible for the user (you can do this only if every
		// graphical component was added - otherwise some components will not be
		// displayed.
		guiMainFrame.setVisible(true);

		// when the user wants to close the window (do something)
		guiMainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// check if there already exist a saved configuration that is
				// different from the actual configuration or if there exist no
				// configuration at all

				boolean fileExistButPathListIsNotTheSame = actionManager.fileExists(file)
						&& (!actionManager.fileReader(file).equals(actionManager.getPathList()));
				boolean fileDoesNotExist = !(actionManager.fileExists(file));

				if (fileExistButPathListIsNotTheSame || fileDoesNotExist) {
					// ask the user if he wants to close without saving his
					// actual configuration
					if (JOptionPane.showConfirmDialog(guiMainFrame,
							"Are you sure to close the program without saving your music video folder paths to a configuration file?",
							"Really Closing?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
						if (fileDoesNotExist) {
							// simply save a new configuration file when no
							// configuration exist at this point
							actionManager.fileWriterOfTheConfigFile(file);
							// but if a different one exist ask the user if he
							// really wants to overwrite the old configuration
							// with new data:
						} else if (JOptionPane.showConfirmDialog(guiMainFrame,
								"Do you want to overwrite your previous saved configuration with your actual configuration?",
								"Overwrite old data?", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
							// overwrite the actual file
							actionManager.fileOverWriter(file);
						}
					}
				}
			}
		});
	}

	/**
	 * External JFrame to manage/delete added source folder paths of music
	 * videos. Paths that can be deleted with a click immediately update the
	 * table (/music video list and path list) on their own.
	 */
	public void pathEditorJFrame() {

		JFrame pathEditorWindow = new JFrame("Edit the source folder paths:");
		// set title
		pathEditorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// only close on exit - don't end the whole program
		pathEditorWindow.setSize(300, 400);
		// size of the window

		// create a grid layout for all paths as a visual button list
		pathEditorWindow.setLayout(new GridLayout(actionManager.getPathList().size(), 1));
		// for all actual saved paths create one button
		for (int i = 0; i < actionManager.getPathList().size(); i++) {

			JButton removePathButton = new JButton("<< Click to remove " + actionManager.getPathList().get(i) + " >>");
			removePathButton.setVerticalTextPosition(AbstractButton.CENTER);
			// removePathButton.setHorizontalTextPosition(AbstractButton.LEADING);
			removePathButton.setActionCommand("remove path from path list");
			int indexOfPath = i;
			removePathButton.addActionListener((ActionEvent event) -> {
				if (JOptionPane.showConfirmDialog(null,
						"Do you really want to delete this path from the path list?\n"
								+ actionManager.getPathList().get(indexOfPath),
						"Question", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					actionManager.deletePathFromPathList(indexOfPath + 1);
					removePathButton.setEnabled(false);
					System.out.println(indexOfPath + 1);
					updateTable();
				} else {
					System.out.println("User denied. Path was not deleted from list.");
				}

			});
			pathEditorWindow.add(removePathButton);

			// question the user if he wants this changes to happen when he
			// closes the window and only then delete the paths and update the
			// table
		}

		// no pack all components together for the perfect size
		pathEditorWindow.pack();
		// make it visible for the user
		pathEditorWindow.setVisible(true);
		// and let it pop up in the middle of the screen
		pathEditorWindow.setLocationRelativeTo(null);
	}

	/**
	 * This method removes all rows of our table and updates them with new rows
	 * created of a new read of a updated music video list.
	 */
	public void updateTable() {

		// delete all actual rows
		for (int a = 0; a < actionManager.getMusicVideosList().size(); a++) {
			model.removeRow(0);
		}
		// scan each path of the path list after music videos and update so the
		// music video list
		actionManager.scanDirectories();
		// now add for each entry in the new updated music video list a row to
		// the empty table
		for (int a = 0; a < actionManager.getMusicVideosList().size(); a++) {
			model.addRow(new Object[] { actionManager.musicVideoListToTable()[a][0],
					actionManager.musicVideoListToTable()[a][1], actionManager.musicVideoListToTable()[a][2] });
		}
	}

	/**
	 * This happens when the program gets executed: We create a new
	 * ConceptJFrameGUI and the constructor does the rest :)
	 */
	public static void main(String[] args) {

		// create a new Object of our class
		ConceptJFrameGUI hi = new ConceptJFrameGUI();
		// look after configuration file before starting
		hi.startupConfig();
		// open/show the window
		hi.createWindow();
	}

}