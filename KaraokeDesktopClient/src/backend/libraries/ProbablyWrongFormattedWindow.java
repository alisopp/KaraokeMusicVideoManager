package backend.libraries;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.ActionHandler;

/**
 * A simple text area JFrame (just a temporal solution)
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.8 (beta)
 */
public class ProbablyWrongFormattedWindow {

	/**
	 * The JFrame in which everything will be displayed
	 */
	private JFrame frame;

	/**
	 * The JTextArea in which we can display text
	 */
	private JTextArea textArea;

	private String titleOfTheJFrame;

	public ProbablyWrongFormattedWindow(String title) {
		this.titleOfTheJFrame = title;
	}

	public void showMe(String textToDisplay) {

		// Create a normal text field area
		textArea = new JTextArea();

		// Create a JFrame with title
		frame = new JFrame(titleOfTheJFrame);

		// set the default icon of the program as window icon
		ActionHandler.setProgramWindowIcon(frame);

		// Set default close operation for JFrame
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set JFrame layout
		frame.setLayout(new BorderLayout());

		// Set text for textArea
		textArea.setText(textToDisplay);

		// Create a JScrollPane which contains the textArea
		JScrollPane scrolltxt = new JScrollPane(textArea);

		// Now we add everything to the JFrame
		frame.add(scrolltxt, BorderLayout.CENTER);

		// Set JFrame size
		// frame.setSize(700, 300);

		// Automatically tries to fit everything in place
		frame.pack();

		// Set it to the middle
		frame.setLocationRelativeTo(null);

		// Make JFrame visible. So we can see it.
		frame.setVisible(true);
	}
}