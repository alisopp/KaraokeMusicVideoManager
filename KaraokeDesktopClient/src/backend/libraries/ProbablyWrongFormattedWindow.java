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

	private String b;

	public ProbablyWrongFormattedWindow(String a) {
		this.b = a;
	}

	public void showMe(String a) {

		// Create a normal text field area
		textArea = new JTextArea();

		// Create a JFrame with title
		frame = new JFrame(b);

		// set the default icon of the program as window icon
		ActionHandler.setProgramWindowIcon(frame);

		// Set default close operation for JFrame
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set JFrame layout
		frame.setLayout(new BorderLayout());

		// Set text for textArea
		textArea.setText(a);

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