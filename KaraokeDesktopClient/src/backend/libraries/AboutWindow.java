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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import frontend.ConceptJFrameGUI;

/**
 * Editable window with a JLabel and a JProgressbar
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.4 (beta)
 */
public class AboutWindow extends JFrame {

	// Eclipse annoys me...
	private static final long serialVersionUID = 1L;

	/**
	 * Create a JFrame with a JProgressBar
	 */
	public AboutWindow(String versionNumber, String releaseDate, String title, String authorText, String text) {

		// Get the Windows look on Windows computers
		try {
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			if (System.getProperty("os.name").contains("Windows")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setTitle(title);
		// "About Karaoke Desktop Client [Beta]"
		// set title
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// only close on exit - don't end the whole program

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		ImageIcon iconLogo;
		JLabel icon = new JLabel();
		try {
			iconLogo = new ImageIcon(ImageIO.read(ConceptJFrameGUI.class.getResource("/logo.png")));
			icon.setIcon(iconLogo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel author = new JLabel(authorText + ": Niklas | https://github.com/AnonymerNiklasistanonym ");
		author.addMouseListener((MouseListener) new OpenUrlAction());

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));

		panel2.add(new JLabel(text));
		// "This program is completely open source on Github"
		panel2.add(author);
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

		// // set title
		// this.setTitle(title);
		// this.setBounds(100, 90, 440, 150);
		// // do not close the program if the User hits exit
		// this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// // this.getContentPane().setLayout(null);
		//
		// // create a label to determine the actual process
		// label = new JLabel("Text");
		//
		// // create a JProgressBar
		// progressBar = new JProgressBar();
		// // progressBar.setBounds(0, 0, 400, 20);
		//
		// JPanel panel2 = new JPanel(new GridLayout(3, 1));
		// panel2.add(label);
		// panel2.add(new JLabel(""));
		// panel2.add(progressBar);
		// panel2.setBounds(0, 0, 400, 150);
		// panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		//
		// this.getContentPane().add(panel2);
		// this.setLocationRelativeTo(null);
		// // make the JFrame visible
		// // this.pack();
		// this.setVisible(true);
	}

	/**
	 * Open URL to GitHub profile listener
	 */
	class OpenUrlAction implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			URI uri = null;
			try {
				uri = new URI("https://github.com/AnonymerNiklasistanonym");
			} catch (URISyntaxException e1) {
			}

			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(uri);
				} catch (IOException e) {
				}
			}

		}

		public void mouseEntered(MouseEvent e) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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