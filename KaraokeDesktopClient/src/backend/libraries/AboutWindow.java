package backend.libraries;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionHandler;
import backend.language.LanguageController;

/**
 * Editable window with a JLabel and a JProgressbar
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.8.1 (beta)
 */
public class AboutWindow extends JFrame {

	// Eclipse annoys me...
	private static final long serialVersionUID = 1L;

	/**
	 * Create a JFrame with a JProgressBar
	 */
	public AboutWindow(String versionNumber, String releaseDate) {

		// Get the Windows look on Windows computers
		ActionHandler.windowsLookActivator();

		// Set icon to the default one
		ActionHandler.setProgramWindowIcon(this);

		this.setTitle(LanguageController.getTranslation("About Karaoke Desktop Client [Beta]"));
		// "About Karaoke Desktop Client [Beta]"
		// set title
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// only close on exit - don't end the whole program

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel icon = new JLabel();
		boolean hi = false;
		// System.getProperty("os.name").contains("Windows")
		if (hi) {
			icon.setIcon(ActionHandler.loadImageIconFromClass("/images/logo_windows.png"));
		} else {
			icon.setIcon(ActionHandler.loadImageIconFromClass("/images/logo_new.png"));
		}

		JLabel author = new JLabel(
				LanguageController.getTranslation("Author") + ": Niklas | https://github.com/AnonymerNiklasistanonym ");
		URI aha = null;
		try {
			aha = new URI("https://github.com/AnonymerNiklasistanonym");
		} catch (URISyntaxException e1) {
		}
		author.addMouseListener((MouseListener) new OpenUrlAction(aha));
		JLabel project = new JLabel("Code: https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager ");
		URI aha2 = null;
		try {
			aha2 = new URI("https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager");
		} catch (URISyntaxException e1) {
		}
		project.addMouseListener((MouseListener) new OpenUrlAction(aha2));

		JPanel panel2 = new JPanel(new GridLayout(4, 1));
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));

		panel2.add(new JLabel(LanguageController.getTranslation("This program is completely open source on Github")));
		// "This program is completely open source on Github"
		panel2.add(author);
		panel2.add(project);
		panel2.add(new JLabel("\u00a9 " + releaseDate + " >> v" + versionNumber));

		panel.add(icon, BorderLayout.WEST);
		panel.add(panel2, BorderLayout.EAST);
		this.add(panel);

		// make everything fitting perfect
		this.pack();
		// make it visible for the user
		this.setVisible(true);
		// and let it pop up in the middle of the screen
		this.setLocationRelativeTo(null);
		// let nobody change the size of it
		this.setResizable(false);
	}

	/**
	 * Open URL to GitHub profile listener
	 */
	class OpenUrlAction implements MouseListener {

		private URI aha;

		public OpenUrlAction(URI a) {
			aha = a;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(aha);
				} catch (IOException e) {
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		// Not needed:
		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	public void closeIt() {
		dispose();
	}
}