package backend.libraries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Static methods to read a file
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.6 (beta)
 *
 */
public class FileReaderManager {

	/**
	 * Search for a file and extract its data. Save each line in a path list and
	 * return it.
	 * 
	 * @param file
	 *            (filename of configuration file)
	 * @return ArrayList<Path> (list with all extracted paths)
	 */
	public static String[] fileReader(File file) {

		String[] contentOfFile = null;
		ArrayList<String> lines = new ArrayList<String>();

		// if the file really exists
		if (file == null) {

			System.err.println("The file is null!");
			return null;

		} else if (!file.exists()) {

			System.err.println("The file doesn't exist!");
			return null;

		} else {

			// try to read the file line for line
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				for (String line; (line = br.readLine()) != null;) {
					// add each read line to the ArrayList<String>
					lines.add(line);
				}

				contentOfFile = new String[lines.size()];

				for (int a = 0; a < lines.size(); a++) {
					contentOfFile[a] = lines.get(a);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// return the content of the file
			return contentOfFile;
		}
	}

	/**
	 * Check if a file exists
	 * 
	 * @param file
	 *            (File | file that gets checked if it exists)
	 * @return true if it exists, false if not
	 */
	public static boolean fileExists(File file) {

		if (file.exists()) {
			System.out.println("The file \"" + file.getName() + "\" was found.");
			return true;
		} else {
			System.err.println("The file \"" + file.getName() + "\" wasn't found.");
			return false;
		}
	}

}