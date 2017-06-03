package backend.libraries;

import java.io.File;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Static methods to use the JFileChooser
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.5 (beta)
 *
 */
public class JFileChooserManager {

	/**
	 * Opens a JFileChooser and gives back the chosen directory
	 * 
	 * @param title
	 *            (String | title of the JFileChooser)
	 * @return file (File | the user selected file - or null if user aborts)
	 */
	private static File chooseDirectory(String title) {

		JFileChooser chooser = new JFileChooser();
		// create a new JFileChooser

		chooser.isFocusOwner();
		// that it is in the front

		chooser.setCurrentDirectory(new java.io.File("."));
		// get the current directory of the program

		chooser.setDialogTitle(title);
		// set the title of the pop up window

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// file selection mode = show only directories

		// open the JFileChooser dialog
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// if the user pressed enter and a file was selected - return it
			System.out.println("The directory \"" + chooser.getSelectedFile().getPath() + "\" was selected");
			return chooser.getSelectedFile();

		} else {

			// otherwise return null
			System.err.println("No directory was selected!");
			return null;
		}
	}

	/**
	 * Get the path of a directory on the computer
	 * 
	 * @return path (Path | or null, if user aborts)
	 */
	public static Path chooseDirectoryGetPath(String title) {

		File file = chooseDirectory(title);

		if (file == null) {
			System.err.println("The path of the directory is null!");
			return null;
		} else {
			return file.toPath();
		}
	}

	/**
	 * Get a directory on the computer
	 * 
	 * @return file (File | or null, if user aborts)
	 */
	public static File chooseDirectoryGetFile(String title) {

		File file = chooseDirectory(title);

		if (file == null) {
			System.err.println("The file of the directory is null!");
			return null;
		} else {
			return file;
		}
	}

	/**
	 * Get a file (type File) through the JFile Chooser
	 * 
	 * @return file (File | null -> if user aborts dialog)
	 */
	public static File chooseFile(String title) {

		// simply just return a selected file, but without having a
		// FileNameExtensionFilter
		return chooseFile(title, null);
	}

	/**
	 * Get a file (type File) through the JFileChooser
	 * 
	 * @param title
	 *            (String | title of the JFileChooser)
	 * @param filter
	 *            (FileNameExtensionFilter | show only files of this type)
	 * @return file (File | null -> if user aborts dialog)
	 */
	public static File chooseFile(String title, FileNameExtensionFilter filter) {

		JFileChooser chooser = new JFileChooser();
		// create a new JFileChooser

		chooser.isFocusOwner();
		// that it is in the front

		chooser.setCurrentDirectory(new java.io.File("."));
		// get the current directory of the program

		chooser.setDialogTitle(title);
		// set the title of the pop up window

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// file selection mode = only allow to select files

		// if there is a filter
		if (filter != null) {

			chooser.setAcceptAllFileFilterUsed(false);
			// needs to be false, so that the user is forced to see only files
			// that match the filter

			chooser.addChoosableFileFilter(filter);
			// add it to the JFileChooser
		}

		// open the JFileChooser dialog
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// if the user pressed enter and a file was selected - return it
			System.out.println("The file \"" + chooser.getSelectedFile().getName() + "\" was selected (Path: \""
					+ chooser.getSelectedFile().getPath() + "\")");
			return chooser.getSelectedFile();

		} else {

			// otherwise return null
			System.err.println("No file was selected!");
			return null;
		}
	}

	/**
	 * Get through the JFileChooser one or more Files
	 * 
	 * @return files (File[] | -> null if dialog aborted)
	 */
	public static File[] chooseFiles(String title) {

		JFileChooser chooser = new JFileChooser();
		// create a new JFileChooser

		chooser.isFocusOwner();
		// that it is in the front

		chooser.setCurrentDirectory(new java.io.File("."));
		// get the current directory of the program

		chooser.setDialogTitle(title);
		// set the title of the pop up window

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// file selection mode = show only directories

		chooser.setMultiSelectionEnabled(true);
		// enable multiple folder selection

		// open the JFileChooser dialog
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			// if the user pressed enter and at least one file was selected -
			// return it
			File[] selectedFiles = chooser.getSelectedFiles();
			for (File a : selectedFiles) {
				System.out.println("The directory \"" + a.getPath() + "\" was selected");
			}

			return selectedFiles;

		} else {

			// otherwise return null
			System.err.println("No directory was selected!");
			return null;
		}
	}

	/**
	 * Get through the java swing JFileChooser one or more paths to one or more
	 * directories/folders
	 * 
	 * @return paths of directory (Path[] | -> null if dialog aborted)
	 */
	public static Path[] chooseDirectoriesGetPaths(String title) {

		// get through chooseFiles a file array
		File[] files = chooseFiles(title);

		// if the user has not selected any files
		if (files == null) {

			// return null
			System.err.println("No files selected to convert to paths!");
			return null;

		} else {

			// but if he has
			// create a empty path array with the same length as the file array
			Path[] pathArray = new Path[files.length];

			// now transfer every file path from every file to this array
			for (int i = 0; i < files.length; i++) {
				pathArray[i] = files[i].toPath();
			}

			// return the now full path (of each file) array
			return pathArray;
		}
	}

}