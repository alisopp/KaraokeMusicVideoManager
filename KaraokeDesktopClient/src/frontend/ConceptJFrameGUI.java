package frontend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
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

public class ConceptJFrameGUI {

	private JFrame guiFrame;
	private ActionHandler karaokeOMat;
	private JTable table;
	private DefaultTableModel model;
	private File file;

	public static void main(String[] args) {
		ConceptJFrameGUI hi = new ConceptJFrameGUI();
		hi.startupConfig();
		hi.createWindow();
	}

	public void startupConfig() {

		if (file.exists()) {

			if (JOptionPane.showConfirmDialog(null, "Would you like to load your previous configuration? ", "Question",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				karaokeOMat.setPathList(karaokeOMat.fileReader(file));
				karaokeOMat.scanDirectories();
				// restart now? - Search for solution

			} else {
				System.out.println("No configuration was loaded. User denied it.");
			}
		} else {
			System.out.println("No configuration was found.");
		}

	}

	public void createWindow() {

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// make sure the program exits when the frame closes
		guiFrame.setTitle("Karaoke Desktop Client [Beta]"); // title
		guiFrame.setSize(500, 720); // size of the window
		guiFrame.setLocationRelativeTo(null); // middle of the screen

		JMenuBar menubar = new JMenuBar();

		// menu "Source folders" {
		JMenu submenuPath = new JMenu("Source folders");
		submenuPath.setToolTipText("Add/Change/Remove the actual folders of your music videos");
		// >>
		JMenuItem subsubmenuAdd = new JMenuItem("Add");
		subsubmenuAdd.setToolTipText("Add a new folder with new music videos to your list");
		subsubmenuAdd.addActionListener((ActionEvent event) -> {
			System.out.println("Add a new folder with new music videos to your list");
			karaokeOMat.addToPathList(karaokeOMat.getDirectory());
			karaokeOMat.scanDirectories();
		});
		submenuPath.add(subsubmenuAdd);
		// >>
		JMenuItem subsubmenuRemove = new JMenuItem("Remove");
		subsubmenuRemove.setToolTipText("Remove a folder with music videos from your list");
		subsubmenuRemove.addActionListener((ActionEvent event) -> {
			System.out.println("Remove a folder with music videos from your list");
		});
		submenuPath.add(subsubmenuRemove);
		// >>
		submenuPath.addSeparator();
		// >>
		JMenuItem subsubmenuEdit = new JMenuItem("(Future) Edit");
		subsubmenuEdit.setToolTipText("[Future] Great GUI Dialogue for this");
		subsubmenuEdit.addActionListener((ActionEvent event) -> {
			System.out.println("[Future] Great GUI Dialogue for this");
		});
		submenuPath.add(subsubmenuEdit);
		// menu "Source folders" }
		menubar.add(submenuPath);

		// menu "Save" {
		JMenu submenuSave = new JMenu("Save");
		submenuSave.setToolTipText("Save your actual source folder paths and more in a config file for later");
		// >>
		JMenuItem subsubmenuConfigurationSave = new JMenuItem("Save your actual configuration");
		subsubmenuConfigurationSave
				.setToolTipText("Saves everything so you can start instantly at the next launch of the program");
		subsubmenuConfigurationSave.addActionListener((ActionEvent event) -> {
			System.out.println("Save the actual configuration (of folders)!");
			karaokeOMat.fileOverWriter(file);
		});
		submenuSave.add(subsubmenuConfigurationSave);
		// >>
		submenuPath.addSeparator();
		// >>
		JMenuItem subsubmenuConfigurationLoad = new JMenuItem("Load it");
		subsubmenuConfigurationLoad.setToolTipText("Load configuration from a configuration file");
		subsubmenuConfigurationLoad.addActionListener((ActionEvent event) -> {
			System.out.println("dialog wich says please restart program - notify if a file even exit before this");
		});
		submenuSave.add(subsubmenuConfigurationLoad);
		// menu "Save" }
		menubar.add(submenuSave);

		guiFrame.setJMenuBar(menubar);

		// later add icons:
		// ImageIcon iconExit = new ImageIcon("res/exit.gif");
		// JMenuItem newMi = new JMenuItem("New", iconNew);

		String[] columnNames = { "#", "Artist", "Title" };
		Object[][] data = karaokeOMat.musicVideoListToTable();

		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model);

		table.setAutoCreateRowSorter(true);

		// Place the JTable object in a JScrollPane for a scrolling table
		JScrollPane tableScrollPane = new JScrollPane(table);

		guiFrame.add(tableScrollPane);
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
				karaokeOMat.openMusicVideo(row);
			}
		});

		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());

		JTextField jtfFilter = new JTextField();
		JButton jbtFilter = new JButton("Filter");

		table.setRowSorter(rowSorter);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Search for a music video:  "), BorderLayout.WEST);
		panel.add(jtfFilter, BorderLayout.CENTER);

		guiFrame.add(panel, BorderLayout.NORTH);

		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String scannedText = jtfFilter.getText();

				if (scannedText.matches("[-+]?\\d*\\.?\\d+")) {
					int a = Integer.parseInt(scannedText);

					if (a > 0 && a <= karaokeOMat.getMusicVideosList().size()) {
						karaokeOMat.openMusicVideo(a - 1);
					}
				}
			}
		};

		jtfFilter.addActionListener(action);

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

		JButton randomMusicvideoButton = new JButton("Play a random music video");

		randomMusicvideoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				int randomNum = ThreadLocalRandom.current().nextInt(0, karaokeOMat.getMusicVideosList().size());
				String randomSong = karaokeOMat.getMusicVideosList().get(randomNum).getTitle() + " by "
						+ karaokeOMat.getMusicVideosList().get(randomNum).getArtist() + "?";
				System.out.println("Random music video: ...Playing " + randomSong);

				if (JOptionPane.showConfirmDialog(null, "Would you like to play " + randomSong, "Info",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					karaokeOMat.openMusicVideo(randomNum);
				} else {
					System.out.println("\tPlaying was denied by the user.");
				}
			}
		});

		guiFrame.add(randomMusicvideoButton, BorderLayout.SOUTH);

		guiFrame.setVisible(true);

	}

	public ConceptJFrameGUI() {

		guiFrame = new JFrame();
		karaokeOMat = new ActionHandler();
		file = new File("karaoke_desktop_config.abc");
	}
}