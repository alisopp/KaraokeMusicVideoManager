package backend.libraries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Static methods to write files
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.4 (beta)
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

			// if the content is null don't write the file
			System.err.println("There is nothing to save!");

		} else {

			// else try to create the file
			try {

				file.createNewFile();
				// creates the handed over file

				FileWriter writer = new FileWriter(file);
				// now let's create a FileWriter Object

				for (int i = 0; i < content.length - 1; i++) {

					// which writes for every content line a line to the file
					writer.write(content[i] + "\n");
				}

				writer.write(content[content.length - 1]);
				// this is outside the loop because at the last line we do not
				// want a "\n"

				// now let's close the FileWriter:
				writer.flush();
				writer.close();

				System.out.println("File was saved to " + file.getAbsolutePath());

			} catch (IOException e) {

				// if something happens print out that the file could not be
				// saved
				System.err.println("File could not be saved!");

				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a file and overwrites the old file with the same name if such a
	 * file exists (optional with dialogs)
	 * 
	 * @param file
	 *            (File | filename + path to save)
	 * @param content
	 *            (String[] | content that should be saved into it)
	 */
	private static void overWriteFileDialog(File file, String[] content, boolean noDialogs) {

		// if the file exist
		if (file.exists()) {

			// ask the user if he really wants to overwrite the old file
			if (noDialogs || JOptionPane.showConfirmDialog(null,
					"This will overwrite your old \"" + file.getName() + "\" file! Do you really want to continue?",
					"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

				// because of the user approved we try to delete the file
				if (file.delete()) {

					System.out.println(file.getName() + " is deleted");

					// because the file was deleted we can now write the new
					// file
					FileWriterManager.writeFile(file, content);

				} else {

					// if the delete process fails print this out
					System.err.println("Delete operation is failed, writing aborted!");
				}
			}
		} else {

			// because the file does not exist we only need to write it
			FileWriterManager.writeFile(file, content);
		}
	}

	/**
	 * Creates a file and overwrites the old file with the same name if such a
	 * file exists with dialogs
	 * 
	 * @param file
	 *            (File | filename + path to save)
	 * @param content
	 *            (String[] | content that should be saved into it)
	 */
	public static void overWriteFileDialog(File file, String[] content) {

		overWriteFileDialog(file, content, false);
	}

	/**
	 * Creates a file and overwrites the old file with the same name if such a
	 * file exists
	 * 
	 * @param file
	 *            (File | filename + path to save)
	 * @param content
	 *            (String[] | content that should be saved into it)
	 */
	public static void overWriteFile(File file, String[] content) {

		overWriteFileDialog(file, content, true);
	}

	/**
	 * Delete a file
	 * 
	 * @param file
	 *            (File | this file gets deleted)
	 */
	public static boolean fileDeleter(File file) {

		if (file.exists()) {

			if (file.delete()) {

				System.out.println(file.getName() + " is deleted");
				return true;

			} else {

				System.err.println("Delete operation is failed!");
				return false;
			}
		} else {

			System.err.println(file.getName() + " does not exist and could not be deleted!");
			return false;
		}
	}

}