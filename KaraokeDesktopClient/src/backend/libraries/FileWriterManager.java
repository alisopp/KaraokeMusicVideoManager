package backend.libraries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * These library contains static methods to write a file
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.3 (beta)
 *
 */
public class FileWriterManager {

	/**
	 * Creates a file and writes the content into it
	 * 
	 * @param file
	 *            (File | filename + path to save)
	 * @param content
	 *            (String[] | content that should be saved into it)
	 */
	public static void writeFile(File file, String[] content) {

		if (content == null || content.length == 0) {
			System.err.println("There is nothing to save!");
		} else {
			// creates the file
			try {
				file.createNewFile();

				// creates a FileWriter Object
				FileWriter writer = new FileWriter(file);

				// Writes the content to the file
				for (int i = 0; i < content.length - 1; i++) {
					writer.write(content[i] + "\n");
				}
				writer.write(content[content.length - 1]);

				writer.flush();
				writer.close();

				System.out.println("File was saved to " + file.getAbsolutePath());

			} catch (IOException e) {
				System.err.println("File could not be saved!");
				e.printStackTrace();
			}
		}
	}

	public static void overWriteFileDialog(File file, String[] content) {

		if (file.exists()) {
			if (JOptionPane.showConfirmDialog(null,
					"This will overwrite your old \"" + file.getName() + "\" file! Do you really want to continue?",
					"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

				if (file.delete()) {

					System.out.println(file.getName() + " is deleted");
					FileWriterManager.writeFile(file, content);

				} else {
					System.err.println("Delete operation is failed, writing aborted!");
					JOptionPane.showMessageDialog(null, "File was not saved!");
				}
			}
		} else {
			FileWriterManager.writeFile(file, content);
		}
	}

}