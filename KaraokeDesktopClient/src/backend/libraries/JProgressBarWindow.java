package backend.libraries;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import backend.ActionHandler;
import backend.language.LanguageController;

/**
 * Editable window with a JLabel and a JProgressbar || does not work - if
 * somebody has any ideas this would be cool :)
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.8.1 (beta)
 */
public class JProgressBarWindow {

	/**
	 * JProgressBar (global because of get and set)
	 */
	private static JProgressBar progressBar;

	/**
	 * JLabel (global because of get and set)
	 */
	private static JLabel label;

	private JFrame frame;

	/**
	 * Create a JFrame with a JProgressBar
	 */
	public JProgressBarWindow(String title) {

		// Get the Windows look on Windows computers
		ActionHandler.windowsLookActivator();

		frame = new JFrame();
		// set title
		frame.setTitle(title);
		frame.setBounds(100, 90, 440, 150);
		// do not close the program if the User hits exit
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.getContentPane().setLayout(null);

		// create a label to determine the actual process
		label = new JLabel("Text");

		// create a JProgressBar
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);

		JPanel panel2 = new JPanel(new GridLayout(3, 1));
		panel2.add(label);
		panel2.add(new JLabel(""));
		panel2.add(progressBar);
		panel2.setBounds(0, 0, 400, 150);
		panel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		frame.add(panel2);
		frame.setLocationRelativeTo(null);
		// frame.pack();
		// make the JFrame visible
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

	public void showWindow() {

	}

	public static void setProgressBar(int a) {
		if (a >= 0 && a <= 100) {
			progressBar.setValue(a);
			System.out.println(progressBar.getValue());
		}
	}

	public void setProgressBar2(int a) {
		if (a >= 0 && a <= 100) {
			progressBar.setValue(a);
			System.out.println(progressBar.getValue());
		}
	}

	public static void addProgressToProgressBar(int a) {
		if (a >= 0 && progressBar.getValue() + a <= 100) {
			progressBar.setValue(progressBar.getValue() + a);
		}
	}

	public void closeJFrame() {
		frame.dispose();
	}

	public static void main(String[] args) {
		JProgressBarWindow hallo = new JProgressBarWindow(LanguageController.getTranslation("Processing") + "...");
		Thread a = new Thread();
		a.start();
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(100);
				setProgressBar(i);
				String aha = i + " " + LanguageController.getTranslation("percent");
				setLabelText(aha);
			}
		} catch (InterruptedException e) {
		}
		hallo.closeJFrame();
	}

	public static void setLabelText(String text) {
		label.setText(text);
	}
}