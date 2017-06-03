package backend.libraries;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import backend.language.LanguageController;

/**
 * Editable window with a JLabel and a JProgressbar
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.5 (beta)
 */
public class JProgressBarWindow extends JFrame {

	// Eclipse annoys me...
	private static final long serialVersionUID = 1L;

	/**
	 * JProgressBar (global because of get and set)
	 */
	private JProgressBar progressBar;

	/**
	 * JLabel (global because of get and set)
	 */
	private JLabel label;

	/**
	 * Create a JFrame with a JProgressBar
	 */
	public JProgressBarWindow(String title) {

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

		// set title
		this.setTitle(title);
		this.setBounds(100, 90, 440, 150);
		// do not close the program if the User hits exit
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.getContentPane().setLayout(null);

		// create a label to determine the actual process
		label = new JLabel("Text");

		// create a JProgressBar
		progressBar = new JProgressBar();
		// progressBar.setBounds(0, 0, 400, 20);

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		panel2.add(label);
		panel2.add(new JLabel(""));
		panel2.add(progressBar);
		panel2.setBounds(0, 0, 400, 150);
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		this.getContentPane().add(panel2);
		this.setLocationRelativeTo(null);
		// make the JFrame visible
		this.setResizable(false);
		this.setVisible(true);
	}

	public void setProgressBar(int a) {
		if (a >= 0 && a <= 100) {
			progressBar.setValue(a);
			System.out.println("Progress bar set to " + progressBar.getValue());
		}
	}

	public void addProgressToProgressBar(int a) {
		if (a >= 0 && progressBar.getValue() + a <= 100) {
			progressBar.setValue(progressBar.getValue() + a);
			System.out.println("Progress bar set to " + progressBar.getValue());
		}
	}

	public void setLabelText(String text) {
		label.setText(text);
	}

	public void closeJFrame() {
		dispose();
	}

	public static void main(String[] args) {
		JProgressBarWindow hallo = new JProgressBarWindow(LanguageController.getTranslation("Processing") + "...");

		Thread a = new Thread();
		a.start();
		// hallo.setProgressBar(4);
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(100);
				hallo.setProgressBar(i);
				hallo.setLabelText(i + " " + LanguageController.getTranslation("percent"));
			}
		} catch (InterruptedException e) {

		}

		hallo.closeJFrame();
	}
}