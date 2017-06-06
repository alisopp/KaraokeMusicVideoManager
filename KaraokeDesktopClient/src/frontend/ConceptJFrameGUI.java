package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import backend.ActionHandler;
import backend.language.LanguageController;
import backend.libraries.AboutWindow;
import backend.libraries.FileTreeWindow;

/**
 * The graphical user interface of this program. It combines the already written
 * back end code with a user friendly and feature rich graphical java swing
 * interface.
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.7 (beta)
 *
 */
public class ConceptJFrameGUI {

	/**
	 * All the management of the list and data already written
	 */
	private ActionHandler actionManager;

	/**
	 * global window where the main things will happen
	 */
	private JFrame guiMainFrame;

	// a global JTable and DefaultTableModel;
	private JTable table;
	private DefaultTableModel model;
	TableRowSorter<TableModel> rowSorter;

	private AboutWindow newAboutWindow;
	private FileTreeWindow fileTreeWindow;

	private String version;
	private String releaseDate;

	/**
	 * Constructor: When an object of ConceptJFrameGUI gets produced this
	 * automatically will happen:
	 */
	public ConceptJFrameGUI() {

		LanguageController.setDefaultLanguage();

		version = "0.7.4 (beta)";
		releaseDate = LanguageController.getTranslation("June") + " 2017";

		String[] columnNames = new String[] { "#", LanguageController.getTranslation("Artist"),
				LanguageController.getTranslation("Title") };
		String[] fileNameConfiguration = new String[] { "karaokeConfig", "cfg" };

		// start a new window/JFrame
		guiMainFrame = new JFrame();
		// start a ActionHandler for all the under the surface commands
		actionManager = new ActionHandler(columnNames, fileNameConfiguration);

		fileTreeWindow = new FileTreeWindow(actionManager, this);
	}

	/**
	 * At startup this method checks if a configuration file exist and loads it
	 * if it finds one. So a fast start for already set up party's/etc. is
	 * possible.
	 */
	private void startupConfig() {

		// if a configuration file exists
		if (actionManager.fileExists(actionManager.getConfigurationFile())) {
			// ask the user if he also wants to load saved data
			// if (JOptionPane.showConfirmDialog(null,
			// LanguageController.getTranslation("Would you like to load your
			// previous saved configuration") + "?",
			// LanguageController.getTranslation("Question"),
			// JOptionPane.YES_NO_OPTION,
			// JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			// if he accepts to load the data read the file and set the
			// content as your new path list
			actionManager.configFileReaderOnStart();
			// now scan again all paths for music videos
			actionManager.updateMusicVideoList();
			// }
			fileTreeWindow = new FileTreeWindow(actionManager, this);
		}
	}

	/**
	 * This method manages the main graphic user interface. Here you can find
	 * all features.
	 * 
	 * @throws IOException
	 */
	private void createWindow() {

		guiMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make sure the program exits when the frame closes
		guiMainFrame.setTitle(LanguageController.getTranslation("Karaoke Desktop Client [Beta]"));
		// title of the window
		guiMainFrame.setSize(500, 620);
		// size of the window at the start
		guiMainFrame.setLocationRelativeTo(null);
		// let it pop up in the middle of the screen

		// update column heads and version date if language change
		actionManager.setColumnNames(new String[] { "#", LanguageController.getTranslation("Artist"),
				LanguageController.getTranslation("Title") });
		releaseDate = LanguageController.getTranslation("June") + " 2017";

		ActionHandler.setProgramWindowIcon(guiMainFrame);

		String textSource = LanguageController.getTranslation("Source folders");
		String tooltipSource = LanguageController.getTranslation("Edit the source folders of your music videos");

		ImageIcon iconAdd = ActionHandler.loadImageIconFromClass("/add_20x20.png");
		String textAdd = LanguageController.getTranslation("Add");
		String tooltipAdd = LanguageController.getTranslation("Add a new folder with new music videos to your list");

		ImageIcon iconRemove = ActionHandler.loadImageIconFromClass("/remove_20x20.png");
		String textRemove = LanguageController.getTranslation("Remove and more");
		String tooltipRemove = LanguageController.getTranslation("Remove a folder with music videos from your list");

		String textExport = LanguageController.getTranslation("Export");
		String tooltipExport = LanguageController.getTranslation("Export to the following formats") + ": CSV, HTML";

		ImageIcon iconExportCsv = ActionHandler.loadImageIconFromClass("/csv_20x20.png");
		String textExportCsv = LanguageController.getTranslation("Export to") + " CSV";
		String tooltipExportCsv = LanguageController.getTranslation("Export your data to") + " CSV ("
				+ LanguageController.getTranslation("spreadsheet") + ")";

		ImageIcon iconExportHtml = ActionHandler.loadImageIconFromClass("/html_20x20.png");
		String textExportHtml = LanguageController.getTranslation("Export to") + " HTML";
		String tooltipExportHtml = LanguageController.getTranslation("Export your data to") + " HTML ("
				+ LanguageController.getTranslation("web browser") + ")";

		ImageIcon iconExportHtmlSearch = ActionHandler.loadImageIconFromClass("/htmlSearch_20x20.png");
		String textExportHtmlSearch = LanguageController.getTranslation("Export to") + " HTML "
				+ LanguageController.getTranslation("with a search");
		String tooltipExportHtmlSearch = LanguageController.getTranslation("Export your data to") + " HTML ("
				+ LanguageController.getTranslation("web browser") + ")";

		String textLanguage = LanguageController.getTranslation("Language");
		String tooltipLanguage = LanguageController.getTranslation("Change the language of the program") + ": GER, ENG";

		ImageIcon iconLanguageEng = ActionHandler.loadImageIconFromClass("/eng_30x20.png");
		String textLanguageEng = LanguageController.getTranslation("English");
		String tooltipLanguageEng = LanguageController.getTranslation("Change the language of the program to") + " "
				+ LanguageController.getTranslation("English");

		ImageIcon iconLanguageGer = ActionHandler.loadImageIconFromClass("/de_30x20.png");
		String textLanguageGer = LanguageController.getTranslation("German");
		String tooltipLanguageGer = LanguageController.getTranslation("Change the language of the program to") + " "
				+ LanguageController.getTranslation("German");

		String textMore = LanguageController.getTranslation("More");
		String tooltipMore = LanguageController.getTranslation("Save configuration, about and more");

		ImageIcon iconSaveConfiguration = ActionHandler.loadImageIconFromClass("/save_20x20.png");
		String textSaveConfiguration = LanguageController.getTranslation("Save configuration");
		String tooltipSaveConfiguration = LanguageController
				.getTranslation("Saves configuration in a configuration file");

		ImageIcon iconLoadConfiguration = ActionHandler.loadImageIconFromClass("/load_20x20.png");
		String textLoadConfiguration = LanguageController.getTranslation("Load configuration");
		String tooltipLoadConfiguration = LanguageController
				.getTranslation("Load configuration from a configuration file");

		ImageIcon iconAbout = ActionHandler.loadImageIconFromClass("/info_20x20.png");
		String textAbout = LanguageController.getTranslation("About");
		String tooltipAbout = LanguageController.getTranslation("About this program");

		// add a menu bar to the window
		JMenuBar menuBar = new JMenuBar();

		// >> with the sub menu "Source folders"
		JMenu subMenuSource = new JMenu(textSource);
		subMenuSource.setToolTipText(tooltipSource);

		// >> >> and the sub sub menu "Add"
		JMenuItem subSubMenuAddSourceFolder = new JMenuItem(textAdd, iconAdd);
		subSubMenuAddSourceFolder.setToolTipText(tooltipAdd);
		subSubMenuAddSourceFolder.addActionListener((ActionEvent event) -> {
			if (actionManager.addToPathList(actionManager.getPathOfDirectories()))
				updateTable(); // update table if new paths are here
		});

		// >> >> and the sub sub menu "Remove and more" >>
		JMenuItem subSubMenuRemoveSourceFolder = new JMenuItem(textRemove, iconRemove);
		subSubMenuRemoveSourceFolder.setToolTipText(tooltipRemove);
		subSubMenuRemoveSourceFolder.addActionListener((ActionEvent event) -> {
			fileTreeWindow.closeIt(); // if there is an open window close it
			fileTreeWindow.createAndShowGUI(); // and open a new one
		});

		// >> with the sub menu "Export"
		JMenu subMenuExport = new JMenu(textExport);
		subMenuExport.setToolTipText(tooltipExport);

		// >> >> and the sub sub menu "Export to CSV"
		JMenuItem subSubMenuExportCsv = new JMenuItem(textExportCsv, iconExportCsv);
		subSubMenuExportCsv.setToolTipText(tooltipExportCsv);
		subSubMenuExportCsv.addActionListener((ActionEvent event) -> {
			actionManager.exportCsvFile("musicvideolist.csv");
		});

		// >> >> and the sub sub menu "Export to HTML"
		JMenuItem subSubMenuExportHtml = new JMenuItem(textExportHtml, iconExportHtml);
		subSubMenuExportHtml.setToolTipText(tooltipExportHtml);
		subSubMenuExportHtml.addActionListener((ActionEvent event) -> {
			actionManager.exportHtmlFile("table.html");
		});

		// >> >> and the sub sub menu "Export to HTML with a search"
		JMenuItem subSubMenuExportHtmlWithSearch = new JMenuItem(textExportHtmlSearch, iconExportHtmlSearch);
		subSubMenuExportHtmlWithSearch.setToolTipText(tooltipExportHtmlSearch);
		subSubMenuExportHtmlWithSearch.addActionListener((ActionEvent event) -> {
			actionManager.exportHtmlFileWithSearch("tableWithSearch.html");
		});

		// >> with the sub menu "Export"
		JMenu subMenuLanguage = new JMenu(textLanguage);
		subMenuLanguage.setToolTipText(tooltipLanguage);

		// >> >> and the sub sub menu "Export to HTML with a search"
		JMenuItem subSubMenuEnglish = new JMenuItem(textLanguageEng, iconLanguageEng);
		subSubMenuEnglish.setToolTipText(tooltipLanguageEng);
		subSubMenuEnglish.addActionListener((ActionEvent event) -> {
			if (LanguageController.changeLanguageRestart(Locale.ENGLISH))
				actionManager.fileOverWriterConfig();
		});

		// >> >> and the sub sub menu "Export to HTML with a search"
		JMenuItem subSubMenuGerman = new JMenuItem(textLanguageGer, iconLanguageGer);
		subSubMenuGerman.setToolTipText(tooltipLanguageGer);
		subSubMenuGerman.addActionListener((ActionEvent event) -> {
			if (LanguageController.changeLanguageRestart(Locale.GERMAN))
				actionManager.fileOverWriterConfig();
		});

		// >> with the sub menu "More"
		JMenu subMenuMore = new JMenu(textMore);
		subMenuMore.setToolTipText(tooltipMore);

		// >> >> and the sub sub menu "Save configuration"
		JMenuItem subSubMenuConfigurationSave = new JMenuItem(textSaveConfiguration, iconSaveConfiguration);
		subSubMenuConfigurationSave.setToolTipText(tooltipSaveConfiguration);
		subSubMenuConfigurationSave.addActionListener((ActionEvent event) -> {
			actionManager.fileOverWriterConfig();
		});

		// >> >> and the sub sub menu "Load configuration"
		JMenuItem subSubMenuConfigurationLoad = new JMenuItem(textLoadConfiguration, iconLoadConfiguration);
		subSubMenuConfigurationLoad.setToolTipText(tooltipLoadConfiguration);
		subSubMenuConfigurationLoad.addActionListener((ActionEvent event) -> {
			if (actionManager.configFileLoaderDialog()) // if a new
				updateTable(); // configuration was loaded update the table
		});

		// >> >> and last but not least the sub sub menu "About"
		JMenuItem subSubMenuAbout = new JMenuItem(textAbout, iconAbout);
		subSubMenuAbout.setToolTipText(tooltipAbout);
		subSubMenuAbout.addActionListener((ActionEvent event) -> {
			if (newAboutWindow != null)
				newAboutWindow.closeIt();
			newAboutWindow = new AboutWindow(version, releaseDate);
		});

		// add the menu buttons to the menu bar to the JFrame
		subMenuSource.add(subSubMenuAddSourceFolder);
		subMenuSource.addSeparator();
		subMenuSource.add(subSubMenuRemoveSourceFolder);
		menuBar.add(subMenuSource);
		subMenuExport.add(subSubMenuExportCsv);
		subMenuExport.addSeparator();
		subMenuExport.add(subSubMenuExportHtml);
		subMenuExport.addSeparator();
		subMenuExport.add(subSubMenuExportHtmlWithSearch);
		menuBar.add(subMenuExport);
		subMenuLanguage.add(subSubMenuEnglish);
		subMenuLanguage.add(subSubMenuGerman);
		menuBar.add(subMenuLanguage);
		subMenuMore.add(subSubMenuConfigurationSave);
		subMenuMore.addSeparator();
		subMenuMore.add(subSubMenuConfigurationLoad);
		subMenuMore.addSeparator();
		subMenuMore.add(subSubMenuAbout);
		menuBar.add(subMenuMore);
		guiMainFrame.setJMenuBar(menuBar);

		Object[] menüs = { subMenuSource, subMenuExport, subMenuLanguage, subMenuMore, subSubMenuAddSourceFolder,
				subSubMenuRemoveSourceFolder, subSubMenuExportCsv, subSubMenuExportHtml, subSubMenuExportHtmlWithSearch,
				subSubMenuGerman, subSubMenuEnglish, subSubMenuConfigurationSave, subSubMenuConfigurationLoad,
				subSubMenuAbout };
		for (Object a : menüs) {
			try {
				JMenu b = (JMenu) a;
				b.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
			} catch (ClassCastException e) {
				JMenuItem b = (JMenuItem) a;
				b.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
			}
		}

		model = new DefaultTableModel(actionManager.musicVideoListToTable("  "), actionManager.getColumnNames());
		table = new JTable(model);

		// for Linux
		table.getTableHeader().setBackground(new Color(240, 240, 240));

		// set the color of the border
		MatteBorder border = new MatteBorder(0, 0, 0, 0, Color.BLACK);
		table.setBorder(border);

		// if you select a cell the whole row will be selected
		table.setRowSelectionAllowed(true);

		// only one row can be selected
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// set the specific width of the columns
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(5);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setPreferredWidth(225);

		// center text in the first column
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		// automatic row sorter
		table.setAutoCreateRowSorter(true);

		// from bbhar - https://stackoverflow.com/a/26576892/7827128
		// 2 colors (every second row gets colored)
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));

		// place the JTable object in a JScrollPane for a scrolling table
		JScrollPane tableScrollPane = new JScrollPane(table);
		guiMainFrame.add(tableScrollPane);

		// add table mouse listener
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				actionManager.openMusicVideo((int) table.getValueAt(table.getSelectedRow(), 0) - 1);
			}

			public void mouseEntered(MouseEvent e) {
				guiMainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				guiMainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// add a search bar with filter and input option
		JTextField jtfFilter = new JTextField();
		JPanel panel = new JPanel(new BorderLayout());

		ImageIcon iconSearch = null;
		try {
			iconSearch = new ImageIcon(ImageIO.read(ConceptJFrameGUI.class.getResource("/search.png")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		JLabel thumb = new JLabel(LanguageController.getTranslation("Search for a music video") + ":  ");
		thumb.setIcon(iconSearch);
		thumb.setToolTipText(LanguageController.getTranslation("Type in the field to instantly find your music video"));
		panel.add(thumb);

		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(ConceptJFrameGUI.class.getResource("/youtube.png")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		JButton button234 = new JButton(icon);
		button234.setBackground(new Color(224, 47, 47));
		button234.setContentAreaFilled(false);
		button234.setOpaque(true);
		button234.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				String scannedText = jtfFilter.getText();

				String url = "https://www.youtube.com";
				try {
					if (scannedText != null && scannedText.length() > 0) {
						url = "https://www.youtube.com/results?search_query=" + URLEncoder.encode(scannedText, "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// inspired by http://stackoverflow.com/a/4898607

				if (Desktop.isDesktopSupported()) {
					// Windows
					try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				} else {
					// Linux
					Runtime runtime = Runtime.getRuntime();
					try {
						runtime.exec("xdg-open " + url);
						// runtime.exec("/usr/bin/firefox -new-window " + url);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			public void mouseEntered(MouseEvent e) {
				guiMainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				guiMainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		button234.setToolTipText(LanguageController.getTranslation(
				"Click the button to start a video search on YouTube with the input of the text field"));

		panel.add(thumb, BorderLayout.WEST);
		panel.add(button234, BorderLayout.EAST);
		panel.add(jtfFilter, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(500, 55));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		guiMainFrame.add(panel, BorderLayout.NORTH);

		// action listener if the enter key was pressed
		Action action = new AbstractAction() {
			// eclipse was this, i dont't like orange light bulbs
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				// // get the actual text of the text field
				// String scannedText = jtfFilter.getText();
				//
				// // check if it is a number (a String only containing digits)
				// if (scannedText.matches("[-+]?\\d*\\.?\\d+")) {
				// // if yes parse the String to an Integer and
				// // open the music video on this position
				// actionManager.openMusicVideo(Integer.parseInt(scannedText) -
				// 1);
				// } else {
				// when at least on row exists
				if (table.getRowCount() > 0) {
					// algorithm that finds the top result in the actual
					// table and gets the number of it
					actionManager.openMusicVideo((int) table.getValueAt(0, 0) - 1);
				}
				// }
			}
		};
		jtfFilter.addActionListener(action);

		// create a row sorter: But NOT to sort the data in the table manually
		// later, this is enabled by default! We need the row sorter for our
		// filter in the search bar
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// update filter if something in text field gets added
				updateFilter(jtfFilter.getText());
				if (table.getSelectedRow() != 0) {
					table.changeSelection(0, 0, true, false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// update filter if something in text field gets removed
				updateFilter(jtfFilter.getText());
				if (table.getSelectedRow() != 0) {
					table.changeSelection(0, 0, true, false);
				}

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// needs to be implemented, but we don't need it

			}
		});

		// play a random music video button
		ImageIcon iconRandom = null;
		try {
			iconRandom = new ImageIcon(ImageIO.read(ConceptJFrameGUI.class.getResource("/random.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JButton randomMusicvideoButton = new JButton(LanguageController.getTranslation("Play a random music video"),
				iconRandom);

		randomMusicvideoButton.setText(LanguageController.getTranslation("Play a random music video"));
		randomMusicvideoButton.setPreferredSize(new Dimension(500, 40));
		randomMusicvideoButton.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
		randomMusicvideoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				if (actionManager.getMusicVideosList().size() > 0) {

					int randomNum = actionManager.getRandomNumber(0, actionManager.getMusicVideosList().size());
					String randomSong = actionManager.getMusicVideosList().get(randomNum).getTitle() + " "
							+ LanguageController.getTranslation("by") + " "
							+ actionManager.getMusicVideosList().get(randomNum).getArtist() + "?";
					System.out.println("Random music video: ...Playing " + randomSong);

					if (JOptionPane.showConfirmDialog(null,
							LanguageController.getTranslation("Would you like to play") + " " + randomSong, "Info",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						actionManager.openMusicVideo(randomNum);
					} else {
						System.out.println("\tPlaying was denied by the user.");
					}

				} else {
					JOptionPane.showMessageDialog(guiMainFrame,
							LanguageController.getTranslation("You first need to add music videos to use this feature")
									+ "!");
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

				boolean fileExistButPathListIsNotTheSame = actionManager
						.fileExists(actionManager.getConfigurationFile())
						&& (!actionManager.configFilePathExtracter().equals(actionManager.getPathList()));
				boolean fileDoesNotExist = !(actionManager.fileExists(actionManager.getConfigurationFile()));
				boolean noPathsExist = actionManager.getPathList().isEmpty();

				if (!noPathsExist && (fileExistButPathListIsNotTheSame || fileDoesNotExist)) {
					// ask the user if he wants to close without saving his
					// actual configuration
					if (JOptionPane.showConfirmDialog(guiMainFrame,
							LanguageController.getTranslation(
									"Are you sure to close the program without saving your music video folder paths to a configuration file?"),
							"Really Closing?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
						actionManager.fileOverWriterConfig();
					}
				}
			}
		});
	}

	/**
	 * This method removes all rows of our table and updates them with new rows
	 * created of a new read of an up to date music video list.
	 */
	public void updateTable() {

		// delete all actual rows if there are even any rows!
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		// scan each path of the path list after music videos and update so the
		// music video list
		actionManager.updateMusicVideoList();

		// now add for each entry in the new updated music video list a row to
		// the empty table
		for (Object[] a : actionManager.musicVideoListToTable("  ")) {
			model.addRow(a);
		}

		// from bbhar - https://stackoverflow.com/a/26576892/7827128
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
	}

	/**
	 * This method updates our TableRowSorter after our filter.
	 * 
	 * @param searchString
	 *            (String | search command from text field)
	 */
	public void updateFilter(String searchString) {
		if (searchString.trim().length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString));
		}
	}

	/**
	 * This happens when the program gets executed: We create a new
	 * ConceptJFrameGUI and the constructor does the rest :)
	 */
	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Get the Windows look on Windows computers
				ActionHandler.windowsLookActivator();

				// create a new Object of our class
				ConceptJFrameGUI mainFrame = new ConceptJFrameGUI();

				// look after configuration file before starting
				mainFrame.startupConfig();

				// open/show the window
				mainFrame.createWindow();
			}
		});

	}

}