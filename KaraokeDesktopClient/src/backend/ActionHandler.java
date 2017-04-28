package backend;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.libraries.FileReaderManager;
import backend.libraries.FileWriterManager;
import backend.libraries.JFileChooserManager;
import backend.objects.MusicVideo;

/**
 * In this class are all the behind the scenes algorithms that have nothing to
 * do with a console TUI or a graphical GUI. It's the core of the project.
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.3 (beta)
 *
 */
public class ActionHandler {

	/**
	 * This ArrayList<Path> contains all the source folders that contain music
	 * video files
	 */
	private ArrayList<Path> pathList;

	/**
	 * This ArrayList<MusicVideo> contains all the MusicVideo objects and each
	 * object is a music video file with information about title, artist and
	 * path of the music video files that were found in the source folders
	 */
	private ArrayList<MusicVideo> musicVideoList;

	/**
	 * The column names for the JTable and all other tables
	 */
	private String[] columnNames;

	/**
	 * The constructor creates the two essential ArrayList's for the paths and
	 * MusicVideo objects
	 */
	public ActionHandler() {

		pathList = new ArrayList<Path>();
		musicVideoList = new ArrayList<MusicVideo>();

		columnNames = new String[3];
		columnNames[0] = "#";
		columnNames[1] = "Artist";
		columnNames[2] = "Title";

		if (!Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported - this program will not run on your Computer!");
		}
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	/*
	 * (Get) Directory methods:
	 */

	/**
	 * Get through the java swing JFileChooser a path to a directory/folder
	 * 
	 * @return path of directory (Path | -> null if dialog aborted)
	 */
	public Path getPathOfDirectory() {

		return JFileChooserManager.chooseDirectoryGetPath("Choose a folder with your music videos");
	}

	/**
	 * Get through the java swing JFileChooser one or more paths to one or more
	 * directories/folders
	 * 
	 * @return paths of directory (Path[] | -> null if dialog aborted)
	 */
	public Path[] getPathOfDirectories() {

		return JFileChooserManager.chooseDirectoriesGetPaths("Choose a folder or more with your music videos");
	}

	/*
	 * File methods:
	 */

	/**
	 * Get a configuration file (type File) through the JFileChooser
	 * 
	 * @return file (File | null -> if user aborts dialog)
	 */
	public File getConfigurationFileOnComputer() {

		// add filter to the JFileChooser that only configuration files will be
		// seen by the user
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Configuration file", "abc");

		// return the configuration file
		return JFileChooserManager.chooseFile("Choose a folder with your music videos", filter);
	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public void scanDirectory(Path path) {

		// "walk" through all files in the actual directory
		try (Stream<Path> paths = Files.walk(path)) {
			paths.forEach(filePath -> {

				// if file is a normal file and a movie file we create a new
				// MusicVideo object
				if (Files.isRegularFile(filePath)
						&& ((filePath.toString().contains(".avi") || filePath.toString().contains(".mp4"))
								|| (filePath.toString().contains(".mkv") || filePath.toString().contains(".mov")))) {

					MusicVideo newMusicVideoObject = new MusicVideo();

					// set (file) path of the music video
					newMusicVideoObject.setPath(filePath);
					// System.out.println("path: " + filePath);

					/* Set name/title and artist/s name of the music video: */

					// first lets remove the file type
					String noFileType = filePath.getFileName().toString();
					if (noFileType.indexOf(".") > 0) {
						noFileType = noFileType.substring(0, noFileType.lastIndexOf("."));
					}
					// second split the new substring at " - "
					String[] artistAndTitle = noFileType.split("\\s-\\s");
					// >> set artist/s of the music video
					newMusicVideoObject.setArtist(artistAndTitle[0]);
					// >> set title/name of the music video
					newMusicVideoObject.setTitle(artistAndTitle[1]);

					// finally add the newMusicVideoObject to our
					// musicVideosList
					musicVideoList.add(newMusicVideoObject);
				}
			});
			System.out.println("Music video list was updated (+ " + path + ")");
		} catch (IOException e) {
			System.out.println("Failure while scanning the directory \"" + path + "\"");
		}
	}

	/**
	 * This method simply updates the whole music video List
	 */
	public void updateMusicVideoList() {

		// delete old entries
		clearMusicVideosList();

		// if there are paths scan them all to update the music video list
		if (pathList.isEmpty()) {
			System.err.println("There are no paths to scan for music videos!");
		} else {
			System.out.println("Scan all paths for music videos:");
			for (Path path : pathList) {
				scanDirectory(path);
			}
		}

		// sort the music video at last
		sortMusicVideoList();
	}

	/*
	 * Path list methods:
	 */

	/**
	 * Get the actual pathList
	 * 
	 * @return pathList (ArrayList<Path>)
	 */
	public ArrayList<Path> getPathList() {

		return pathList;
	}

	/**
	 * Set a pathList
	 * 
	 * @param pathList
	 */
	public void setPathList(ArrayList<Path> pathList) {

		if (pathList == null) {
			System.err.println("The handed over ArrayList<Path> was null!");
		} else if (pathList.isEmpty()) {
			System.err.println("The handed over ArrayList<Path> was empty!");
		} else {
			// only set a pathList when the handed over ArrayList<Path> is not
			// empty or null
			this.pathList = pathList;
			System.out.println("A new path list was set");
		}
	}

	/**
	 * Clear all paths from the path list
	 */
	public void clearPathList() {

		// only clear the list if it's not empty
		if (pathList.isEmpty() == false) {
			pathList.clear();
			System.out.println("The path list was cleared");
		} else {
			System.err.println("The path list was already empty!");
		}
	}

	/**
	 * Add a path to the path list
	 * 
	 * @param path
	 *            (Path)
	 */
	public void addToPathList(Path path) {

		if (path == null) {
			System.err.println("The path was null - no path was added to your path list!");
		} else if (pathList.contains(path)) {
			System.err.println("The path \"" + path + "\" was already on your path list!");
		} else if (!Files.notExists(path)) {
			// only add a path to the path list when it is not already in it or
			// doesn't exit or is null
			pathList.add(path);
			System.out.println("The path list was updated (+ " + path + ")");
		} else {
			System.err.println("The path \"" + path + "\" does not exist on this computer!");
		}
	}

	/**
	 * Add an Array of paths to the path list
	 * 
	 * @param pathArray
	 *            (Path[])
	 */
	public void addToPathList(Path[] pathArray) {

		if (pathArray == null || pathArray.length == 0) {
			System.err.println("The path Array was null - no paths were added to your path list!");
		} else {
			// only extract the paths if the array isn't null or has no length
			System.out.println("Load paths from path Array...");
			for (Path pathFromPathArray : pathArray) {
				addToPathList(pathFromPathArray);
			}
		}
	}

	/**
	 * Delete with a handed over index a path from the actual pathList
	 * 
	 * @param index
	 *            (Index of Path to delete in pathList)
	 */
	public void deletePathFromPathList(int index) {

		if (index >= pathList.size() || index < 0) {
			System.err.println("The index \"" + index + "\" does not exist!");
		} else {
			// only remove a path if the index exist!
			System.out.println("The path \"" + pathList.get(index) + "\" was removed");
			pathList.remove(index);
		}
	}

	/**
	 * Print the path list to the console
	 */
	public void printPathList() {

		if (pathList == null) {
			System.err.println("There are no music video paths - path list is null!");
		} else if (pathList.isEmpty()) {
			System.err.println("There are no music video paths - path list is empty!");
		} else {
			// only print the pathList if it isn't null or empty:
			System.out.println("Path list:");
			for (int i = 0; i < pathList.size(); i++) {
				System.out.println((i + 1) + ".\tPath: \"" + pathList.get(i));
			}
		}
	}

	/*
	 * MusicVideo list methods:
	 */

	/**
	 * Get the music video list
	 * 
	 * @return musicVideosList (ArrayList<MusicVideo>)
	 */
	public ArrayList<MusicVideo> getMusicVideosList() {
		return musicVideoList;
	}

	/**
	 * Set the music video list
	 * 
	 * @param musicVideosList
	 *            (ArrayList<MusicVideo>)
	 */
	public void setMusicVideosList(ArrayList<MusicVideo> musicVideosList) {
		this.musicVideoList = musicVideosList;
	}

	/**
	 * Clear the music video list
	 */
	public void clearMusicVideosList() {

		// only clear the list if it's not empty
		if (musicVideoList.isEmpty() == false) {
			musicVideoList.clear();
			System.out.println("The music video list was cleared");
		} else {
			System.err.println("The music video list was already empty!");
		}
	}

	/**
	 * CustomComporator to sort the MusicVideo alphabetically in the list
	 */
	public class CustomComparator implements Comparator<MusicVideo> {

		@Override
		/**
		 * Compare two MusicVideo objects for sorting
		 */
		public int compare(MusicVideo object1, MusicVideo object2) {
			// check how the artist names are compared to each other
			int areTheArtistsTheSame = object1.getArtist().compareTo(object2.getArtist());
			// if the artist are the same artist (==0)
			if (areTheArtistsTheSame == 0) {
				// return the compare value for the title to sort titles from
				// the same artist also alphabetically
				return object1.getTitle().compareTo(object2.getTitle());
			} else {
				// if the artist is not the same return the compare Integer
				// calculated before
				return areTheArtistsTheSame;
			}
		}
	}

	/**
	 * Sorts the music video list
	 */
	public void sortMusicVideoList() {
		Collections.sort(musicVideoList, new CustomComparator());
	}

	/**
	 * Add a music video object to the music video list
	 * 
	 * @param musicVideo
	 *            (MusicVideo)
	 */
	public void addMusicVideoToMusicvideoList(MusicVideo musicVideo) {

		if (musicVideo == null) {
			System.err.println("The music video object was not added because it is null!");
		} else if (musicVideocontainedInMusicVideoList(musicVideo)) {
			System.err.println("The music video list already contains this music video object!");
		} else {
			System.out.println(
					musicVideo.getTitle() + " by " + musicVideo.getArtist() + "was added to the music video list");
			musicVideoList.add(musicVideo);
		}
	}

	/**
	 * Check if the musicVideo object is already in the music video list
	 * 
	 * @param musicVideo
	 *            (MusicVideo)
	 * @return true if it is contained, otherwise false
	 */
	public boolean musicVideocontainedInMusicVideoList(MusicVideo musicVideo) {

		if (musicVideo != null) {
			String title = musicVideo.getTitle();
			String artist = musicVideo.getArtist();
			Path path = musicVideo.getPath();

			for (int a = 0; a < musicVideoList.size(); a++) {
				if (title == musicVideoList.get(a).getTitle()) {
					if (artist == musicVideoList.get(a).getArtist()) {
						if (path == musicVideoList.get(a).getPath()) {
							System.err.println(
									"The song " + title + " by " + artist + " was already in the music video list");
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Print the music video list to the console
	 */
	public void printMusicVideoList() {

		if (musicVideoList == null) {
			System.err.println("There are no music videos - music video list is null!");
		} else if (musicVideoList.isEmpty() == false) {
			System.err.println("There are no music videos - music video list is empty!");
		} else {
			// only print the musicVideosList if it isn't null or empty:
			System.out.println("Music video list:");
			for (int i = 0; i < musicVideoList.size(); i++) {
				System.out.println((i + 1) + ".\tName: \"" + musicVideoList.get(i).getTitle() + "\" - Artist: \""
						+ musicVideoList.get(i).getArtist() + "\" (" + "Path: \"" + musicVideoList.get(i).getPath()
						+ "\")");
			}
		}
	}

	/*
	 * Miscellaneous:
	 */

	/**
	 * Open with the default desktop video player a file with the path of the
	 * MusicVideo object in the music video list
	 * 
	 * @param index
	 *            (index of the MusicVideo object in the music video list)
	 */
	public void openMusicVideo(int index) {

		if (musicVideoList == null) {
			System.err.println("The music videos list is null!");
		} else if (musicVideoList.size() <= index && 0 < index) {
			System.err.println("The index \"" + index + "\" does not exist!");
		} else {
			// if the list isn't null and the index isn't outside the
			// musicVideosList
			// get the MusicVideo object and make it a file with the path
			File file = new File(musicVideoList.get(index).getPath().toString());

			// then check if Desktop is supported by Platform or not
			if (!Desktop.isDesktopSupported()) {
				System.err.println("Desktop is not supported - the Program will not run on this computer!");
			} else {
				Desktop desktop = Desktop.getDesktop();
				// if the file exists too
				if (file.exists()) {
					// open it
					try {
						System.out.println(">> Playing/Open " + musicVideoList.get(index).getTitle() + " by "
								+ musicVideoList.get(index).getArtist());
						desktop.open(file);
					} catch (IOException e) {
						System.err.println("File \"" + file.getAbsolutePath() + "\" could not be opened!");
					}
				} else {
					System.err.println("The file " + file.getAbsolutePath() + " does not exist!");
				}
			}
		}
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for the JTable
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public Object[][] musicVideoListToTable() {

		Object[][] tableData = new Object[musicVideoList.size()][columnNames.length];

		System.out.println("New updated table:");
		for (int a = 0; a < musicVideoList.size(); a++) {
			// set data to Object[][]
			tableData[a][0] = a + 1;
			tableData[a][1] = musicVideoList.get(a).getArtist();
			tableData[a][2] = musicVideoList.get(a).getTitle();

			// check it right now
			System.out.print("#: " + tableData[a][0]);
			System.out.print("\tArtist: " + tableData[a][1]);
			System.out.println("\tTitle: " + tableData[a][2]);
		}

		return tableData;
	}

	public Object[][] musicVideoListToTable(String margin) {

		Object[][] tableData = musicVideoListToTable();

		for (int a = 0; a < musicVideoList.size(); a++) {
			// set data to Object[][]
			tableData[a][1] = margin + tableData[a][1] + margin;
			tableData[a][2] = margin + tableData[a][2] + margin;
		}

		return tableData;
	}

	/**
	 * Search for a file in the same directory and extract its data. Save each
	 * line in a path list and return it.
	 * 
	 * @param file
	 *            (filename of configuration file)
	 * @return ArrayList<Path> (list with all extracted paths)
	 */
	public void configFileReader(File file) {

		String[] contentOfFile = FileReaderManager.fileReader(file);

		// if the file really exists
		if (contentOfFile == null || contentOfFile.length == 0) {

			System.err.println("No content could be extracted!");

		} else {

			pathList.clear();

			// try to read the file line for line
			for (String line : contentOfFile) {

				Path path = Paths.get(line);
				pathList.add(path);
				System.out.println("Path list was updated (+ " + path + ")");
			}
		}
	}

	/**
	 * Search for a file in the same directory and extract its data. Save each
	 * line in a path list and return it.
	 * 
	 * @param file
	 *            (filename of configuration file)
	 * @return ArrayList<Path> (list with all extracted paths)
	 */
	public ArrayList<Path> configFilePathExtracter(File file) {

		String[] contentOfFile = FileReaderManager.fileReader(file);

		// if the file really exists
		if (contentOfFile == null || contentOfFile.length == 0) {

			System.err.println("No content could be extracted!");
			return null;

		} else {

			ArrayList<Path> forComparison = new ArrayList<Path>();

			// try to read the file line for line
			for (String line : contentOfFile) {

				forComparison.add(Paths.get(line));
			}
			return forComparison;
		}
	}

	/**
	 * Simply creates a file and saves everything from the configuration in it
	 * 
	 * @param file
	 */
	public void fileWriterOfTheConfigFile(File file) {

		if (pathList.isEmpty()) {
			System.err.println("There is nothing to save!");
		} else {

			String[] content = new String[getPathList().size()];

			for (int i = 0; i < getPathList().size(); i++) {
				content[i] = getPathList().get(i).toString();
			}

			FileWriterManager.writeFile(file, content);
		}
	}

	/**
	 * Check if the file already exists and deletes it so that it can be written
	 * again
	 * 
	 * @param file
	 */
	public void fileOverWriterConfig(File file) {

		if (file.exists()) {
			fileDeleter(file);
		}
		fileWriterOfTheConfigFile(file);
	}

	/**
	 * Delete a file
	 * 
	 * @param file
	 *            (File | this file gets deleted)
	 */
	public void fileDeleter(File file) {

		if (file.exists()) {
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted");
			} else {
				System.err.println("Delete operation is failed!");
			}
		} else {
			System.err.println(file.getName() + " does not exist and could not be deleted!");
		}
	}

	/**
	 * Check if a file exists
	 * 
	 * @param file
	 *            (File | file that gets checked if it exists)
	 * @return true if it exists, false if not
	 */
	public boolean fileExists(File file) {

		if (file.exists()) {
			System.out.println("Configuration file \"" + file.getName() + "\" was found.");
			return true;
		} else {
			System.err.println("Configuration file \"" + file.getName() + "\" wasn't found.");
			return false;
		}
	}

	/**
	 * Generate a random Integer number
	 * 
	 * @param lowerLimit
	 *            (Integer)
	 * @param upperBound
	 *            (Integer)
	 * @return random Integer number between lowerLimit and upperBound
	 */
	public int getRandomNumber(int lowerLimit, int upperBound) {

		return ThreadLocalRandom.current().nextInt(lowerLimit, upperBound);
	}

	public void exportCsvFile(String fileName) {

		exportDialog("Choose a directory to save the csv file:", fileName, generateCsvContent());

	}

	public String[] generateCsvContent() {

		ArrayList<String> cache = new ArrayList<String>();
		Object[][] data = musicVideoListToTable();
		int columnNumber = columnNames.length;
		int rowNumber = data.length;

		String line = "";

		for (int a = 0; a < columnNumber - 1; a++) {
			line += columnNames[a];
			line += ",";
		}
		line += columnNames[columnNumber - 1];
		cache.add(line);

		for (int a = 0; a < rowNumber; a++) {
			line = "";

			for (int b = 0; b < columnNumber - 1; b++) {

				line += data[a][b].toString() + ",";
			}
			line += data[a][columnNumber - 1].toString();
			cache.add(line);
		}

		String[] content = new String[cache.size()];

		for (int a = 0; a < cache.size(); a++) {
			content[a] = cache.get(a);
		}

		return content;
	}

	public String generateHtmlTable(Object[][] data) {

		StringBuilder sb = new StringBuilder("<table>");
		int columnNumber = columnNames.length;
		int rowNumber = data.length;

		sb.append("<tr>");
		for (int a = 0; a < columnNumber; a++) {
			sb.append("<th>" + columnNames[a] + "</th>");
		}
		sb.append("</tr>");

		for (int a = 0; a < rowNumber; a++) {
			sb.append("<tr>");
			for (int b = 0; b < columnNumber; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	public String[] generateHtmlContent(String table) {

		ArrayList<String> cache = new ArrayList<String>();
		String[] content = null;

		try {

			InputStream in = this.getClass().getClassLoader().getResourceAsStream("htmlBegin.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = r.readLine()) != null) {
				cache.add(line);
			}
			cache.add(table);
			InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("htmlEnd.txt");
			BufferedReader r2 = new BufferedReader(new InputStreamReader(in2));
			while ((line = r2.readLine()) != null) {
				cache.add(line);
			}

			content = new String[cache.size()];

			for (int a = 0; a < cache.size(); a++) {
				content[a] = cache.get(a);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Verdammt, die html Datei konnte nicht exportiert werden!!");
		}
		return content;
	}

	public void exportHtmlFile(String fileName) {

		String[] htmlFileContent = generateHtmlContent(generateHtmlTable(musicVideoListToTable()));

		exportDialog("Choose a directory to save the html file:", fileName, htmlFileContent);

	}

	public void exportDialog(String titleJFileChooser, String fileName, String[] content) {
		String filePath = JFileChooserManager.chooseDirectoryGetPath(titleJFileChooser).toString();

		if (filePath == null) {
			System.out.println("No Directory was selected!");
		} else {

			// add filename to filepath
			filePath += "\\" + fileName;

			// create file through new path
			File file = new File(filePath);

			// overwrite or just write the file with a dialog
			FileWriterManager.overWriteFileDialog(file, content);
		}
	}
}