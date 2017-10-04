package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Static methods to read, write and copy files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
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
	public static String[] readTextFile(File file) {

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
	public static boolean writeTextFile(File file, String[] content) {

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

					System.out.println("<< File succsessfully deleted.");
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

	/**
	 * Creates a directory
	 * 
	 * @param directory
	 *            (File | name of the directory that should be created)
	 * 
	 * @return directoryCouldBeCreated (Boolean)
	 */
	public static boolean createDirectory(File directory) {

		if (directory == null) {
			System.err.println("Directory could not be created because of it's null!");
			return false;
		}

		System.out.print(">> Create directory: " + directory.getAbsolutePath());

		if (!directory.exists()) {

			try {

				if (directory.mkdir()) {
					System.out.println("<< Directory succsessfully created.");
					return true;

				} else {

					System.err.println("<< Directory creation failed!");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		} else {

			System.err.println("Directory already exists!");
			return false;
		}
	}

	/**
	 * Deletes a directory
	 * 
	 * @param directory
	 *            (File | name of the directory that should be removed)
	 * 
	 * @return directoryCouldBeCreated (Boolean)
	 */
	public static boolean deleteDirectory(File directory) {

		if (directory == null) {
			System.err.println("Directory could not be deleted because of it's null!");
			return false;
		}

		System.out.print(">> Delete directory: " + directory.getAbsolutePath());

		if (directory.exists()) {

			try {

				if (directory.delete()) {
					System.out.println("<< Directory succsessfully removed.");
					return true;

				} else {

					System.err.println("<< Directory deletion failed!");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		} else {

			System.err.println("Directory doesn't exist!");
			return false;
		}
	}

	/**
	 * Deletes a directory and all it's content (only non directories! - depth = 1!)
	 * 
	 * @param directory
	 *            (File | name of the directory that should be removed)
	 * 
	 * @return directoryCouldBeCreated (Boolean)
	 */
	public static boolean deleteDirectoryWithFiles(File directory) {

		if (directory == null) {
			System.err.println("Directory could not be deleted because of it's null!");
			return false;
		}

		System.out.print(">> Delete directory: " + directory.getAbsolutePath());

		if (directory.exists()) {

			try {

				// collect the files in the directory
				String[] entries = directory.list();
				for (String currentFile : entries) {
					// and delete them
					deleteFile(new File(directory.getPath(), currentFile));
				}

				// then delete the directory
				if (directory.delete()) {
					System.out.println("<< Directory succsessfully removed.");
					return true;

				} else {
					System.err.println("<< Directory deletion failed!");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		} else {

			System.err.println("Directory doesn't exist!");
			return false;
		}
	}

	/**
	 * Copy any file to a destination (especially important for the res files)
	 * 
	 * --- inspired by https://stackoverflow.com/a/44077426/7827128 ---
	 *
	 * @param sourceFile
	 *            (InputStream | for example a picture from res)
	 * @param destinationPath
	 *            (Path | here the copy should be saved)
	 * @return ifEverythingWorked (Boolean)
	 */
	public static boolean copy(InputStream sourceFile, Path destinationPath) {

		System.out.println(">> Copying to -> " + destinationPath.toString());

		try {
			destinationPath = destinationPath.toAbsolutePath();
			Files.copy(sourceFile, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
