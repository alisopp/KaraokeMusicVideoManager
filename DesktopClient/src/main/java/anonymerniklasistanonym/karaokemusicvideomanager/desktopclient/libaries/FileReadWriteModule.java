package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Static methods to read a file
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.8 (beta)
 *
 */
public class FileReadWriteModule {

	/**
	 * Search for a file and extract its data. Save each line in a path list and
	 * return it.
	 * 
	 * @param file
	 *            (filename of configuration file)
	 * @return ArrayList<Path> (list with all extracted paths)
	 */
	public static String[] readFile(File file) {

		if (file == null) {
			System.err.println("File could not be read because of it's null!");
			return null;
		}

		System.out.println(">> Read file: " + file.getAbsolutePath());

		if (!file.exists()) {
			System.err.println("File could not be read because of it doesn't exists!");
			return null;
		}

		ArrayList<String> lines = new ArrayList<String>();

		// try to read the file line for line
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				// add each read line to the ArrayList<String>
				lines.add(line);
			}

			String[] contentOfFile = lines.toArray(new String[0]);

			int lineNumber = 0, contentNumber = String.valueOf(contentOfFile.length).length();
			for (String line : contentOfFile)
				System.out.println("\t" + String.format("%0" + contentNumber + "d", lineNumber++) + " " + line);

			System.out.println("<< File was read.");

			return contentOfFile;

		} catch (FileNotFoundException e) {
			System.err.println("<< The file could not be found!");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.err.println("<< File could not be read!");
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Creates a text file and writes content into it
	 * 
	 * @param file
	 *            (File | filename + path to save)
	 * @param content
	 *            (String[] | content that should be saved into it)
	 * @return writeFileSuccssessfull (Boolean)
	 */
	public static boolean writeFile(File file, String[] content) {

		if (file == null || content == null || content.length == 0) {
			System.err.println("File could not be written because of it's null!");
			return false;
		}

		System.out.println(">> Write file: " + file.getAbsolutePath());

		int lineNumber = 0, contentNumber = String.valueOf(content.length).length();
		for (String line : content)
			System.out.println("\t" + String.format("%0" + contentNumber + "d", lineNumber++) + " " + line);

		try {

			file.createNewFile();

			try (FileWriter writer = new FileWriter(file)) {

				for (int i = 0; i < content.length - 1; i++) {
					writer.write(content[i] + "\n");
				}
				// this is outside the loop because at the last line we do not want a "\n"
				writer.write(content[content.length - 1]);

				writer.flush();
				writer.close();

				System.out.println("<< File was saved.");

				return true;

			} catch (IOException e) {
				System.err.println("<< File could not be saved!");
				e.printStackTrace();
				return false;
			}

		} catch (IOException e) {
			System.err.println("<< File could not be saved!");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.err.println("<< File could not be saved!");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Deletes a file
	 * 
	 * @param file
	 *            (File | this file gets deleted)
	 * @return
	 */
	public static boolean deleteFile(File file) {

		if (file == null) {
			System.err.println("File could not be deleted because of it's null!");
			return false;
		}

		System.out.print(">> Delete file: " + file.getAbsolutePath());

		if (file.exists()) {

			try {
				if (file.delete()) {

					System.out.println("<< file succsessfully deleted.");
					return true;

				} else {

					System.err.println("<< Delete operation failed!");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		} else {

			System.err.println("File does not exist!");
			return false;
		}
	}

}
