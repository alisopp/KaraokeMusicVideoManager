package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

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
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
 * @version 0.8.2 (beta)
 *
 */
public class ConceptJFrameGUI {

	/**
	 * All the management of the music video list and more methods for
	 * interaction with everything
	 */
	private ActionHandler actionManager;

	/**
	 * The global JFrame window
	 */
	private JFrame guiMainFrame;

	/**
	 * The global main JTable table
	 */
	private JTable table;

	/**
	 * The DefaultTableModel of the global main JTable table
	 */
	private DefaultTableModel tableModel;

	/**
	 * The TableRowSorter of the global main JTable table
	 */
	private TableRowSorter<TableModel> tableRowSorter;

	/**
	 * Global JTextField text input field
	 */
	private JTextField textInputField;

	/**
	 * The about JFrame window
	 */
	private AboutWindow newAboutWindow;

	/**
	 * The path editor JFrame window
	 */
	private FileTreeWindow fileTreeWindow;

	/**
	 * The program version
	 */
	private String version;

	/**
	 * The program release date
	 */
	private String releaseDate;

	/**
	 * Constructor: When an object of ConceptJFrameGUI gets produced this
	 * automatically will happen:
	 */
	public ConceptJFrameGUI() {

		// set version and release date
		version = "0.8.2 (beta)";
		releaseDate = LanguageController.getTranslation("June") + " 2017";

		// set the column names and configuration file name
		String[] columnNames = new String[] { "#", LanguageController.getTranslation("Artist"),
				LanguageController.getTranslation("Title") };
		String[] fileNameConfiguration = new String[] { "karaokeConfig", "cfg" };

		// start a ActionHandler for all the under the surface commands
		actionManager = new ActionHandler(columnNames, fileNameConfiguration);

		// start in the background the path editor
		fileTreeWindow = new FileTreeWindow(actionManager, this);
	}

	/**
	 * At startup this method checks if a configuration file exist and loads it
	 * if it finds one. So a fast start for already set up party's/etc. is
	 * possible.
	 */
	private void startupConfig() {
		// check if a configuration file exists
		if (ActionHandler.fileExists(actionManager.getConfigurationFile())) {
			// read it
			actionManager.configFileReaderOnStart();

			// update column heads and version date if language change
			actionManager.setColumnNames(new String[] { "#", LanguageController.getTranslation("Artist"),
					LanguageController.getTranslation("Title") });
			releaseDate = "7. " + LanguageController.getTranslation("June") + " 2017";
		}
	}

	/**
	 * This method manages the main graphic user interface. Here you can find
	 * all features.
	 */
	private void createWindow() {

		/*
		 * [JFrame setup (1)]
		 * ---------------------------------------------------------------------
		 */

		guiMainFrame = new JFrame();
		// start the global window/JFrame

		guiMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make sure the program exits when the frame closes
		guiMainFrame.setTitle(LanguageController.getTranslation("Karaoke Desktop Client [Beta]"));
		// title of the window
		guiMainFrame.setSize(500, 620);
		// size of the window at the start
		guiMainFrame.setLocationRelativeTo(null);
		// let it pop up in the middle of the screen
		ActionHandler.setProgramWindowIcon(guiMainFrame);
		// set the default icon of the program as window icon

		JPanel mainPanel = new JPanel(new BorderLayout());
		// create a panel in which we fit all our components

		/*
		 * [Load all the text and icons for the menu]
		 * ---------------------------------------------------------------------
		 */

		String textSource = LanguageController.getTranslation("Source folders");
		String tooltipSource = LanguageController.getTranslation("Edit the source folders of your music videos");

		ImageIcon iconAdd = ActionHandler.loadImageIconFromClass("/icons/add_20x20.png");
		String textAdd = LanguageController.getTranslation("Add");
		String tooltipAdd = LanguageController.getTranslation("Add a new folder with new music videos to your list");
		tooltipAdd += " ([control] + [a])";

		ImageIcon iconRemove = ActionHandler.loadImageIconFromClass("/icons/remove_20x20.png");
		String textRemove = LanguageController.getTranslation("Remove and more");
		String tooltipRemove = LanguageController.getTranslation("Remove a folder with music videos from your list");
		tooltipRemove += " ([control] + [e])";

		String textExport = LanguageController.getTranslation("Export");
		String tooltipExport = LanguageController.getTranslation("Export to the following formats") + ": CSV, HTML";

		ImageIcon iconExportCsv = ActionHandler.loadImageIconFromClass("/icons/csv_20x20.png");
		String textExportCsv = LanguageController.getTranslation("Export to") + " CSV";
		String tooltipExportCsv = LanguageController.getTranslation("Export your data to") + " CSV ("
				+ LanguageController.getTranslation("spreadsheet") + ")";
		tooltipExportCsv += " ([control] + [t])";

		ImageIcon iconExportHtml = ActionHandler.loadImageIconFromClass("/icons/html_20x20.png");
		String textExportHtml = LanguageController.getTranslation("Export to") + " HTML";
		String tooltipExportHtml = LanguageController.getTranslation("Export your data to") + " HTML ("
				+ LanguageController.getTranslation("web browser") + ")";
		tooltipExportHtml += " ([control] + [h])";

		ImageIcon iconExportHtmlSearch = ActionHandler.loadImageIconFromClass("/icons/htmlSearch_20x20.png");
		String textExportHtmlSearch = LanguageController.getTranslation("Export to") + " HTML "
				+ LanguageController.getTranslation("with a search");
		String tooltipExportHtmlSearch = LanguageController.getTranslation("Export your data to") + " HTML ("
				+ LanguageController.getTranslation("web browser") + ")";
		tooltipExportHtmlSearch += " ([control] + [s])";

		String textLanguage = LanguageController.getTranslation("Language");
		String tooltipLanguage = LanguageController.getTranslation("Change the language of the program") + ": GER, ENG";

		ImageIcon iconLanguageEng = ActionHandler.loadImageIconFromClass("/icons/eng_30x20.png");
		String textLanguageEng = LanguageController.getTranslation("English");
		String tooltipLanguageEng = LanguageController.getTranslation("Change the language of the program to") + " "
				+ LanguageController.getTranslation("English");

		ImageIcon iconLanguageGer = ActionHandler.loadImageIconFromClass("/icons/de_30x20.png");
		String textLanguageGer = LanguageController.getTranslation("German");
		String tooltipLanguageGer = LanguageController.getTranslation("Change the language of the program to") + " "
				+ LanguageController.getTranslation("German");

		String textMore = LanguageController.getTranslation("More");
		String tooltipMore = LanguageController.getTranslation("Save configuration, about and more");

		ImageIcon iconSaveConfiguration = ActionHandler.loadImageIconFromClass("/icons/save_20x20.png");
		String textSaveConfiguration = LanguageController.getTranslation("Save configuration");
		String tooltipSaveConfiguration = LanguageController
				.getTranslation("Saves configuration in a configuration file");

		ImageIcon iconLoadConfiguration = ActionHandler.loadImageIconFromClass("/icons/load_20x20.png");
		String textLoadConfiguration = LanguageController.getTranslation("Load configuration");
		String tooltipLoadConfiguration = LanguageController
				.getTranslation("Load configuration from a configuration file");

		ImageIcon iconAbout = ActionHandler.loadImageIconFromClass("/icons/info_20x20.png");
		String textAbout = LanguageController.getTranslation("About");
		String tooltipAbout = LanguageController.getTranslation("About this program");

		/*
		 * [Add a JMenu Bar to the JFrame with JMenuItems]
		 * ---------------------------------------------------------------------
		 */

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
			guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			fileTreeWindow.closeIt(); // if there is an open window close it
			fileTreeWindow.createAndShowGUI(); // and open a new one

			// color the table special - needs somehow to be here
			ActionHandler.colorTableWithTwoColors();
			guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

			// color the table special - needs somehow to be here
			ActionHandler.colorTableWithTwoColors();
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

		// add to all the buttons a mouse click effect (MouseListener)
		Object[] menus = { subMenuSource, subMenuExport, subMenuLanguage, subMenuMore, subSubMenuAddSourceFolder,
				subSubMenuRemoveSourceFolder, subSubMenuExportCsv, subSubMenuExportHtml, subSubMenuExportHtmlWithSearch,
				subSubMenuGerman, subSubMenuEnglish, subSubMenuConfigurationSave, subSubMenuConfigurationLoad,
				subSubMenuAbout };
		for (Object a : menus) {
			try {
				JMenu b = (JMenu) a;
				b.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
			} catch (ClassCastException e) {
				JMenuItem b = (JMenuItem) a;
				b.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
			}
		}

		/*
		 * [Create the table and load the data]
		 * ---------------------------------------------------------------------
		 */

		tableModel = new DefaultTableModel(actionManager.musicVideoListToTable("  "), actionManager.getColumnNames());
		table = new JTable(tableModel);

		// for Linux? table background
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

		// color the table special
		ActionHandler.colorTableWithTwoColors();

		JTableHeader header = table.getTableHeader();
		DefaultTableCellRenderer headRenderer = new DefaultTableCellRenderer();
		headRenderer.setHorizontalAlignment(JLabel.CENTER);

		// does not work but still...
		headRenderer.setFont(new Font("Dialog", Font.BOLD, 18));
		headRenderer.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		// ...

		header.setDefaultRenderer(headRenderer);

		// place the JTable object in a JScrollPane for a scrolling table
		JScrollPane tableScrollPane = new JScrollPane(table);

		mainPanel.add(tableScrollPane, BorderLayout.CENTER);

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

		/*
		 * [Create the top panel with the search]
		 * ---------------------------------------------------------------------
		 */

		// create a panel in which we fit all our components
		JPanel topNorthPanel = new JPanel(new BorderLayout());

		// add the search label
		ImageIcon iconSearch = ActionHandler.loadImageIconFromClass("/icons/search.png");
		String textSearch = LanguageController.getTranslation("Search for a music video") + ":  ";
		String tooltipSearch = LanguageController
				.getTranslation("Type in the field to instantly find your music video");

		JLabel searchLabel = new JLabel(textSearch);
		searchLabel.setIcon(iconSearch);
		searchLabel.setToolTipText(tooltipSearch);

		// create our JTextField as a text input field
		textInputField = new JTextField();

		// add the search on YouTube button
		ImageIcon iconYoutubeButton = ActionHandler.loadImageIconFromClass("/icons/youtube.png");
		String tooltipYoutubeButton = LanguageController.getTranslation("Start a video search on YouTube");

		JButton youTubeButtonIcon = new JButton(iconYoutubeButton);
		youTubeButtonIcon.setToolTipText(tooltipYoutubeButton);

		// make the whole area this icon red color
		youTubeButtonIcon.setBackground(new Color(224, 47, 47));
		youTubeButtonIcon.setContentAreaFilled(false);
		youTubeButtonIcon.setOpaque(true);
		// and add a MouseListener for the cursor icon
		youTubeButtonIcon.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
		// add a MouseListener for the case if the button gets clicked
		youTubeButtonIcon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				// URL we want to open
				String urlToOpen = "https://www.youtube.com";

				// get text of JTextField
				String currentInputFieldText = textInputField.getText();

				// if text field has text try to add it to the urlToOpen
				try {
					if (currentInputFieldText != null && currentInputFieldText.length() > 0) {
						String textToSearchQuery = URLEncoder.encode(currentInputFieldText, "UTF-8");
						urlToOpen += "/results?search_query=" + textToSearchQuery;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				// open URL in default web browser of the system
				ActionHandler.openUrlInDefaultBrowser(urlToOpen);
			}
		});

		// add all components to our top panel
		topNorthPanel.add(searchLabel, BorderLayout.WEST);
		topNorthPanel.add(youTubeButtonIcon, BorderLayout.EAST);
		topNorthPanel.add(textInputField, BorderLayout.CENTER);
		// create a transparent border for better usability
		topNorthPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 20));

		// add the top panel at the top of our main panel
		mainPanel.add(topNorthPanel, BorderLayout.NORTH);

		/*
		 * [Add Action Listener(s) to react to text input]
		 * ---------------------------------------------------------------------
		 */

		// if enter was pressed do this
		Action action = new AbstractAction() {
			private static final long serialVersionUID = 2735007834058697821L;

			public void actionPerformed(ActionEvent e) {
				// because we always want to open the top result on enter:
				if (table.getRowCount() > 0) {
					// we open the top music video if there are any music videos
					actionManager.openMusicVideo((int) table.getValueAt(0, 0) - 1);
				} else if (table.getRowCount() == 0) {
					// open YouTube with text field text as search query if
					// there are no elements in the table
					ActionHandler.openYouTubeWithSearchQuery(textInputField.getText());
				}
			}
		};
		textInputField.addActionListener(action);

		// also look up if the text in the input field changes
		textInputField.getDocument().addDocumentListener(new DocumentListener() {

			// update filter if something in text field gets added
			public void insertUpdate(DocumentEvent e) {
				updateFilter(textInputField.getText());
				selectTopRowOfTable();
			}

			// update filter if something in text field gets removed
			public void removeUpdate(DocumentEvent e) {
				updateFilter(textInputField.getText());
				selectTopRowOfTable();
			}

			public void changedUpdate(DocumentEvent arg0) {
			}
		});

		// key shortcuts [control] + [key]
		textInputField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// control is pressed
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
					if (e.getKeyCode() == KeyEvent.VK_A) { // add folder
						if (actionManager.addToPathList(actionManager.getPathOfDirectories()))
							updateTable();
					} else if (e.getKeyCode() == KeyEvent.VK_B) {
						// bad formatted video files
						guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						actionManager.getWrongFormattedMusicVideos();
						// color the table special - needs somehow to be here
						ActionHandler.colorTableWithTwoColors();
						guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					} else if (e.getKeyCode() == KeyEvent.VK_T) {
						actionManager.exportCsvFile("musicvideolist.csv");
					} else if (e.getKeyCode() == KeyEvent.VK_H) {
						actionManager.exportHtmlFile("table.html");
					} else if (e.getKeyCode() == KeyEvent.VK_R) {
						openRandomVideoDialog(); // random video
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						actionManager.exportHtmlFileWithSearch("tableWithSearch.html");
					} else if (e.getKeyCode() == KeyEvent.VK_E) { // editor
						guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						fileTreeWindow.closeIt();
						fileTreeWindow.createAndShowGUI();
						ActionHandler.colorTableWithTwoColors();
						guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		/*
		 * [Add TableRowSorter to our table]
		 * ---------------------------------------------------------------------
		 */

		// create a row sorter (especially important for our search)
		tableRowSorter = new TableRowSorter<>(table.getModel());
		// add row sorter to the table
		table.setRowSorter(tableRowSorter);
		// set every column able to sort
		for (int i = 0; i < actionManager.getColumnNames().length; i++) {
			tableRowSorter.setSortable(i, true);
		}
		// set a special number comparator to the number column
		tableRowSorter.setComparator(0, actionManager.new NumberComparator());

		/*
		 * [Add a open random music video from table button at the bottom]
		 * ---------------------------------------------------------------------
		 */

		// play a random music video button
		ImageIcon iconRandomButton = ActionHandler.loadImageIconFromClass("/icons/random.png");
		String textRandomButton = LanguageController.getTranslation("Play a random music video");
		String tooltipRandomButton = " ([control] + [r])";

		JButton randomMusicvideoButton = new JButton(textRandomButton, iconRandomButton);
		randomMusicvideoButton.setToolTipText(tooltipRandomButton);
		// make it change the cursor symbol
		randomMusicvideoButton.addMouseListener(ActionHandler.mouseChangeListener(guiMainFrame));
		// add special click event
		randomMusicvideoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				openRandomVideoDialog();
			}
		});
		mainPanel.add(randomMusicvideoButton, BorderLayout.SOUTH);

		/*
		 * [JFrame setup (2)]
		 * ---------------------------------------------------------------------
		 */

		// add the panel with everything to the JFrame window
		guiMainFrame.add(mainPanel);

		// make the frame visible for the user
		// (you can do this only if every graphical component was added -
		// otherwise some components will not be displayed)
		guiMainFrame.setVisible(true);

		// when the user wants to close the window (we want to do something)
		guiMainFrame.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				/*
				 * check if there already exist a saved configuration that is
				 * different from the actual configuration or if there exist no
				 * configuration at all
				 */

				boolean fileExistButPathListIsNotTheSame = ActionHandler
						.fileExists(actionManager.getConfigurationFile())
						&& (!actionManager.configFilePathExtracter().equals(actionManager.getPathList()));
				boolean fileDoesExist = ActionHandler.fileExists(actionManager.getConfigurationFile());
				boolean noPathsExist = actionManager.getPathList().isEmpty();

				if (!noPathsExist && (fileExistButPathListIsNotTheSame || !fileDoesExist)) {
					// then ask the user if he wants to close without saving
					if (JOptionPane.showConfirmDialog(guiMainFrame,
							LanguageController.getTranslation(
									"Are you sure to close the program without saving your music video folder paths to a configuration file?"),
							LanguageController.getTranslation("Really Closing") + "?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
						actionManager.fileOverWriterConfig();
					}
				}
			}
		});
	}

	/**
	 * This method updates our TableRowSorter after our filter.
	 * 
	 * @param searchString
	 *            (String | search command from text field)
	 */
	public void updateFilter(String searchString) {
		if (searchString.trim().length() == 0) {
			tableRowSorter.setRowFilter(null);
		} else {
			tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchString));
		}
	}

	/**
	 * Selects automatically the top row of the table
	 */
	private void selectTopRowOfTable() {
		if (table.getSelectedRow() != 0) {
			table.changeSelection(0, 0, true, false);
		}
	}

	/**
	 * This method removes all rows of our table and updates them with new rows
	 * created of a new read of an up to date music video list.
	 */
	public void updateTable() {

		guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		// delete all actual rows if there are even any rows!
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}

		// update the whole music video list (rescan all paths)
		actionManager.updateMusicVideoList();

		// now add for each entry of the updated music video list a row to
		// the empty table
		for (Object[] a : actionManager.musicVideoListToTable("  ")) {
			tableModel.addRow(a);
		}

		// color the table special
		ActionHandler.colorTableWithTwoColors();

		guiMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void openRandomVideoDialog() {
		if (actionManager.getMusicVideosList().size() > 0) {

			int randomNum = ActionHandler.getRandomNumber(0, actionManager.getMusicVideosList().size());
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
			JOptionPane.showMessageDialog(null,
					LanguageController.getTranslation("You first need to add music videos to use this feature") + "!");
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