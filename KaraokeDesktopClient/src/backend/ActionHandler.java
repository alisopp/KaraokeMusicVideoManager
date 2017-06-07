package backend;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.language.LanguageController;
import backend.libraries.FileReaderManager;
import backend.libraries.FileWriterManager;
import backend.libraries.JFileChooserManager;
import backend.libraries.ProbablyWrongFormattedWindow;
import backend.objects.MusicVideo;
import frontend.ConceptJFrameGUI;

/**
 * In this class are all the behind the scenes algorithms that have nothing to
 * do with a console TUI or a graphical GUI. It's the core of the project.
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.8.3 (beta)
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
	 * The accepted file extensions for music video files
	 */
	private String[] acceptedExtensions;

	/**
	 * This is how the configuration file should be named like
	 */
	private String[] fileNameConfiguration;

	/**
	 * The constructor creates the two essential ArrayList's for the paths and
	 * MusicVideo objects
	 */
	public ActionHandler(String[] columnNames, String[] fileNameConfiguration) {

		pathList = new ArrayList<Path>();

		musicVideoList = new ArrayList<MusicVideo>();

		this.columnNames = columnNames;

		acceptedExtensions = new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" };

		this.fileNameConfiguration = fileNameConfiguration;
	}

	/**
	 * Simple method that just returns the configuration file name and extension
	 * name in a String array
	 * 
	 * @return configurationFileName (String[])
	 */
	public String[] getConfigurationFileName() {
		return fileNameConfiguration;
	}

	/**
	 * Simple method that just returns the configuration file
	 * 
	 * @return configurationFile (File)
	 */
	public File getConfigurationFile() {
		return new File(getConfigurationFileName()[0] + "." + getConfigurationFileName()[1]);
	}

	/**
	 * Simple method that just returns the column names in a String array
	 * 
	 * @return columnNames (String[])
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Simple method that just returns the column names in a String array
	 * 
	 * @return columnNames (String[])
	 */
	public void setColumnNames(String[] newColumnNames) {
		columnNames = newColumnNames;
	}

	/*
	 * (Get) Directory methods:
	 */

	/**
	 * Get through the java swing JFileChooser a path to a directory/folder
	 * 
	 * @return path of directory (Path | -> null if dialog aborted)
	 */
	public Path getPathOfDirectory(String text) {
		// "Choose a folder with your music videos"
		return JFileChooserManager.chooseDirectoryGetPath(text);
	}

	/**
	 * Get through the java swing JFileChooser one or more paths to one or more
	 * directories/folders
	 * 
	 * @return paths of directory (Path[] | -> null if dialog aborted)
	 */
	public Path[] getPathOfDirectories() {
		// "Choose a folder or more with your music videos"
		String text = LanguageController.getTranslation("Choose a folder or more with your music videos");
		return JFileChooserManager.chooseDirectoriesGetPaths(text);
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Configuration file", fileNameConfiguration[1]);

		// return the configuration file
		return JFileChooserManager.chooseFile("Choose a folder with your music videos", filter);
	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<MusicVideo> scanDirectory(Path path) {
		ArrayList<MusicVideo> musicvideosinFolder = new ArrayList<MusicVideo>();

		Collection<Path> all = new ArrayList<Path>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			for (Path child : ds) {
				all.add(child);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Path a : all) {
			// System.out.println("Path: " + a);

			// file is a "normal" readable file
			if (Files.isRegularFile(a)) {

				String extension = null, justFileName = null, pathOfFile = a.getFileName().toString();
				String[] artistAndTitle = null;

				int lastIndexOfPoint = pathOfFile.lastIndexOf('.');

				if (lastIndexOfPoint > 0) {
					extension = pathOfFile.substring(lastIndexOfPoint + 1);
					// System.out.println(extension);
					// get everything after the point
					justFileName = pathOfFile.substring(0, lastIndexOfPoint);
					// get everything before the point
					artistAndTitle = justFileName.split("\\s-\\s");
					// split String at " - "
				}

				boolean fileIsAMusicVideo = false;

				if (artistAndTitle == null || artistAndTitle.length < 2) {
					fileIsAMusicVideo = false;
				} else {
					// now we compare it to our allowed extension array
					for (String ab : acceptedExtensions) {
						// System.out.println(ab + ".equalsIgnoreCase(" +
						// extension + ")");
						if (ab.equalsIgnoreCase(extension)) {
							// if we get a hit we set the variable true
							fileIsAMusicVideo = true;
						}
					}
				}

				// if the extension was accepted let's move on
				if (fileIsAMusicVideo) {

					String titleFinal = artistAndTitle[1];
					if (artistAndTitle.length > 2) {
						for (int i = 2; i < artistAndTitle.length; i++) {
							titleFinal = titleFinal + " - " + artistAndTitle[i];
						}
					}

					// finally add the newMusicVideoObject to our
					// musicVideosList
					MusicVideo musicVideo = new MusicVideo(a, titleFinal, artistAndTitle[0]);
					if (addMusicVideoToMusicvideoList(musicVideo)) {
						musicvideosinFolder.add(musicVideo);
					}
				}
			}
		}
		Collections.sort(musicvideosinFolder, new MusicVideo());
		return musicvideosinFolder;
	}

	/**
	 * Scan a directory after specific video files and map all music videos old
	 * - too deep
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public void scanDirectory2(Path path) {

		// "walk" through all files in the actual directory
		try (Stream<Path> paths = Files.walk(path).filter(Files::isRegularFile)) {

			paths.forEach(filePath -> {

				// System.out.println("filePath: " + filePath);

				// file is a "normal" readable file
				if (Files.isRegularFile(filePath)) {

					String extension = null, justFileName = null, pathOfFile = filePath.getFileName().toString();
					String[] artistAndTitle = null;

					int lastIndexOfPoint = pathOfFile.lastIndexOf('.');

					if (lastIndexOfPoint > 0) {
						extension = pathOfFile.substring(lastIndexOfPoint + 1);
						// get everything after the point
						justFileName = pathOfFile.substring(0, lastIndexOfPoint);
						// get everything before the point
						artistAndTitle = justFileName.split("\\s-\\s");
						// split String at " - "
					}

					boolean fileIsAMusicVideo = false;

					// now we compare it to our allowed extension array
					for (String a : acceptedExtensions) {
						if (a.equalsIgnoreCase(extension)) {
							// if we get a hit we set the variable true
							fileIsAMusicVideo = true;
						}
					}

					// if the extension was accepted let's move on
					if (fileIsAMusicVideo) {

						// finally add the newMusicVideoObject to our
						// musicVideosList
						addMusicVideoToMusicvideoList(new MusicVideo(filePath, artistAndTitle[1], artistAndTitle[0]));
					}
				}
			});
			System.out.println("Music video list was updated (+ " + path + ")");
		} catch (IOException e) {
			System.err.println("Failure while scanning the directory \"" + path + "\"");
			return;
		}
	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<String[]> scanDirectoryAfterProbablyMusicVideos(Path path) {
		ArrayList<String[]> musicvideosinFolder = new ArrayList<String[]>();

		Collection<Path> all = new ArrayList<Path>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			for (Path child : ds) {
				all.add(child);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Path a : all) {
			// System.out.println("Path: " + a);

			// file is a "normal" readable file
			if (Files.isRegularFile(a)) {

				String extension = null, justFileName = null, pathOfFile = a.getFileName().toString();
				String[] artistAndTitle = null;

				int lastIndexOfPoint = pathOfFile.lastIndexOf('.');

				if (lastIndexOfPoint > 0) {
					extension = pathOfFile.substring(lastIndexOfPoint + 1);
					// System.out.println(extension);
					// get everything after the point
					justFileName = pathOfFile.substring(0, lastIndexOfPoint);
					// get everything before the point
					artistAndTitle = justFileName.split("\\s-\\s");
					// split String at " - "
				}

				boolean fileIsAWrongFormattedMusicVideo = false;

				// now we compare it to our allowed extension array
				for (String ab : acceptedExtensions) {
					// System.out.println(ab + ".equalsIgnoreCase(" +
					// extension + ")");
					if (ab.equalsIgnoreCase(extension)) {
						// if we get a hit we set the variable true

						if (artistAndTitle == null || artistAndTitle.length < 2) {
							fileIsAWrongFormattedMusicVideo = true;
						} else {
							fileIsAWrongFormattedMusicVideo = false;
						}
					}
				}

				// if the extension was accepted let's move on
				if (fileIsAWrongFormattedMusicVideo) {
					String[] ahaha = new String[2];
					ahaha[0] = justFileName + "." + extension;
					ahaha[1] = a.toString();
					musicvideosinFolder.add(ahaha);
				}
			}
		}
		return musicvideosinFolder;
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
		Collections.sort(musicVideoList, new MusicVideo());
	}

	/**
	 * This method simply updates the whole music video List
	 */
	public void updateMusicVideoListOld() {

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

	/**
	 * This method simply updates the whole music video List
	 */
	public void getWrongFormattedMusicVideos() {

		ProbablyWrongFormattedWindow atrgvfgd = new ProbablyWrongFormattedWindow(
				LanguageController.getTranslation("Eventually wrong formatted music video files"));

		ArrayList<String[]> allWrongFormattedVideos = new ArrayList<String[]>();

		// if there are paths scan them all to update the music video list
		if (pathList.isEmpty()) {
			System.err.println("There are no paths to scan for wrong formatted music videos!");

		} else {

			for (Path path : pathList) {

				allWrongFormattedVideos.addAll(scanDirectoryAfterProbablyMusicVideos(path));
			}
		}

		String abc = "";
		abc += "(" + LanguageController.getTranslation("Correct format") + ": \""
				+ LanguageController.getTranslation("Artist") + "\" - \"" + LanguageController.getTranslation("Title")
				+ "\".\"" + LanguageController.getTranslation("Extension") + "\")\n\n";

		if (allWrongFormattedVideos.isEmpty()) {
			abc += LanguageController.getTranslation("Nothing was found") + " :)";
		} else {
			int count = 1;

			for (String[] hulu : allWrongFormattedVideos) {
				abc += "#" + count + ") " + hulu[0] + "\n\t" + hulu[1] + "\n";
				count++;
			}
		}
		// progress.closeJFrame();
		atrgvfgd.showMe(abc);
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
	public boolean addToPathList(Path[] pathArray) {

		if (pathArray == null || pathArray.length == 0) {
			System.err.println("The path Array was null - no paths were added to your path list!");
			return false;
		} else {
			// only extract the paths if the array isn't null or has no length
			System.out.println("Load paths from path Array...");
			for (Path pathFromPathArray : pathArray) {
				addToPathList(pathFromPathArray);
			}
			return true;
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

	public boolean deletePathFromPathList(Path path) {

		if (!pathList.contains(path)) {
			System.err.println("The path \"" + path.toString() + "\" does not exist!");
			return false;
		} else {
			// only remove a path if the index exist!
			System.out.println("The path \"" + path.toString() + "\" was removed");
			return pathList.remove(path);
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
		sortMusicVideoList();
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
	 * Sorts the music video list
	 */
	public void sortMusicVideoList() {
		Collections.sort(musicVideoList, new MusicVideo());
	}

	/**
	 * Add a music video object to the music video list
	 * 
	 * @param musicVideo
	 *            (MusicVideo)
	 */
	public boolean addMusicVideoToMusicvideoList(MusicVideo musicVideo) {

		if (musicVideo == null) {
			System.err.println("The music video object was not added because it is null!");
			return false;
		} else if (musicVideocontainedInMusicVideoList(musicVideo)) {
			System.err.println("The music video list already contains this music video object!");
			return false;
		} else {
			System.out.println(
					musicVideo.getTitle() + " by " + musicVideo.getArtist() + " was added to the music video list");
			musicVideoList.add(musicVideo);
			return true;
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
				if (title.equals(musicVideoList.get(a).getTitle())) {
					if (artist.equals(musicVideoList.get(a).getArtist())) {
						if (path.compareTo(musicVideoList.get(a).getPath()) == 0) {
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
			Collections.sort(musicVideoList, new MusicVideo());
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
			// System.out.print("#: " + tableData[a][0]);
			// System.out.print("\tArtist: " + tableData[a][1]);
			// System.out.println("\tTitle: " + tableData[a][2]);
		}

		return tableData;
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for the JTable
	 * and add a margin two the second and third row
	 * 
	 * @param margin
	 *            (String)
	 * @return Object[][] ([][#, artist, title])
	 */
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
	 * Load the contents of a configuration file in the actual directory
	 */
	public void configFileReader() {
		loadConfigData(getConfigurationFile(), false);
	}

	/**
	 * Load the contents of a configuration file at the start in the actual
	 * directory and update the music video list after that
	 */
	public void configFileReaderOnStart() {
		loadConfigData(getConfigurationFile(), true);
		// also update the music video list
		updateMusicVideoList();
	}

	/**
	 * Load the contents of a configuration file
	 * 
	 * @param file
	 *            (File)
	 */
	public void loadConfigData(File file, boolean firstStart) {

		String[] contentOfFile = FileReaderManager.fileReader(file);

		// if the file really exists
		if (contentOfFile == null || contentOfFile.length == 0) {

			System.err.println("No content could be extracted!");

		} else {

			pathList.clear();

			// try to read the file line for line
			for (String line : contentOfFile) {

				if (line.equals("en") || line.equals("de_DE") || line.equals("de")) {

					if (firstStart) {
						if (line.equals("de_DE") || line.equals("de")) {
							LanguageController.setCurrentLanguageRb(Locale.GERMAN);
						} else {
							LanguageController.setCurrentLanguageRb(Locale.ENGLISH);
						}
					} else {
						if (line.equals("de_DE") || line.equals("de")) {
							LanguageController.setCurrentLanguage(Locale.GERMAN);
						} else {
							LanguageController.setCurrentLanguage(Locale.ENGLISH);
						}
					}

				} else {
					Path path = Paths.get(line);
					addToPathList(path);
					System.out.println("Path list was updated (+ " + path + ")");
				}
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
	public ArrayList<Path> configFilePathExtracter() {

		File configurationFile = new File(getConfigurationFileName()[0] + "." + getConfigurationFileName()[1]);

		String[] contentOfFile = FileReaderManager.fileReader(configurationFile);

		// if the file really exists
		if (contentOfFile == null || contentOfFile.length == 0) {

			System.err.println("No content could be extracted!");
			return null;

		} else {

			// remove if necessary the language on position 1 to only get the
			// paths
			if (contentOfFile[0].equals("de") || contentOfFile[0].equals("en") || contentOfFile[0].equals("de_DE")) {
				String[] contentOfFile2 = new String[contentOfFile.length - 1];

				for (int i = 0; i < contentOfFile2.length; i++) {
					contentOfFile2[i] = contentOfFile[i + 1];
				}

				contentOfFile = contentOfFile2;
			}

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
	public String[] generateConfigContent() {

		if (pathList.isEmpty()) {
			System.err.println("There is no path to save!");

			return null;
		}

		String[] content = new String[getPathList().size() + 1];

		content[0] = LanguageController.getCurrentLanguage().toString();

		for (int i = 1; i < getPathList().size() + 1; i++) {
			content[i] = getPathList().get(i - 1).toString();
		}

		return content;
	}

	/**
	 * Dialog to load a configuration file from a specific place
	 */
	public boolean configFileLoaderDialog() {
		if (JOptionPane.showConfirmDialog(null,
				LanguageController
						.getTranslation("This will overwrite your old configuration! Do you really want to continue?"),
				"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			loadConfigData(getConfigurationFileOnComputer(), false);
			// now scan again all paths for music videos
			updateMusicVideoList();
			return true;
		} else {
			System.err.println("Loading of configuration file was denied by the user!");
			return false;
		}
	}

	/**
	 * Simply creates a file and saves everything from the configuration in it,
	 * also asks if the file really should be overwritten
	 */
	public void fileOverWriterConfig() {
		FileWriterManager.overWriteFileDialog(getConfigurationFile(), generateConfigContent());
	}

	/**
	 * Delete a file
	 * 
	 * @param file
	 *            (File | this file gets deleted)
	 */
	public static boolean fileDeleter(File file) {
		return FileWriterManager.fileDeleter(file);
	}

	/**
	 * Check if a file exists
	 * 
	 * @param file
	 *            (File | file that gets checked if it exists)
	 * @return true if it exists, false if not
	 */
	public static boolean fileExists(File file) {
		return FileReaderManager.fileExists(file);
	}

	/*
	 * Export Dialogs
	 */

	/**
	 * Export a CSV file of the table of scanned music videos
	 * 
	 * @param fileName
	 *            (String | name of the new file)
	 */
	public void exportCsvFile(String fileName) {

		String text = LanguageController.getTranslation("Choose a directory to save the csv file");

		exportDialog(text, fileName, generateCsvContent());
	}

	/**
	 * Generate CSV table content
	 * 
	 * @return content (String[])
	 */
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

	/**
	 * Generate the table for the HTML file
	 * 
	 * @return table (String)
	 */
	public String generateHtmlTable() {

		Object[][] data = musicVideoListToTable();

		StringBuilder sb = new StringBuilder("<table>");
		int columnNumber = columnNames.length;
		int rowNumber = data.length;

		sb.append("<thead><tr>");
		for (int a = 0; a < columnNumber; a++) {
			sb.append("<th>" + columnNames[a] + "</th>");
		}
		sb.append("</tr></thead><tbody>");

		for (int a = 0; a < rowNumber; a++) {
			sb.append("<tr>");
			for (int b = 0; b < columnNumber; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</tbody></table>");

		return sb.toString();
	}

	/**
	 * Generate the table for the HTML file with a search
	 * 
	 * @return table (String)
	 */
	public String generateHtmlTableWithSearch() {

		Object[][] data = musicVideoListToTable();

		StringBuilder sb = new StringBuilder("placeholder");
		int columnNumber = columnNames.length;
		int rowNumber = data.length;

		sb.append("=\"" + LanguageController.getTranslation("Input your search query to find your music videos")
				+ "...\"/></div>");

		sb.append("<table class=\"order-table table\">");

		sb.append("<thead><tr>" + "<th class=\"number\">#</th>" + "<th class=\"artist\">"
				+ LanguageController.getTranslation("Artist") + "</th>" + "<th class=\"title\">"
				+ LanguageController.getTranslation("Title") + "</th>" + "</tr></thead><tbody>");

		for (int a = 0; a < rowNumber; a++) {
			sb.append("<tr>");
			for (int b = 0; b < columnNumber; b++) {
				sb.append("<td>" + data[a][b] + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</tbody></table>");

		return sb.toString();
	}

	/**
	 * Generate the whole HTML file document content
	 * 
	 * @return content (String[])
	 */
	public String[] generateHtmlContent(String table) {

		ArrayList<String> cache = new ArrayList<String>();
		String[] content = null;

		try {

			InputStream in = this.getClass().getClassLoader().getResourceAsStream("html_code/htmlBegin.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = r.readLine()) != null) {
				cache.add(line);
			}
			cache.add(table);
			InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("html_code/htmlEnd.txt");
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

	/**
	 * Generate the whole HTML file with Search bar document content
	 * 
	 * @return content (String[])
	 */
	public String[] generateHtmlWithSearchContent(String table) {

		ArrayList<String> cache = new ArrayList<String>();
		String[] content = null;

		try {

			InputStream in = this.getClass().getClassLoader().getResourceAsStream("html_code/htmlWithSearchBegin.txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = r.readLine()) != null) {
				cache.add(line);
			}
			cache.add(table);
			InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("html_code/htmlWithSearchEnd.txt");
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
			JOptionPane.showMessageDialog(null, "Error - No Export??? But why... :( sry");
		}
		return content;
	}

	/**
	 * Export dialog of the HTML file
	 * 
	 * @param fileName
	 *            (String | name of the new HTML file)
	 */
	public void exportHtmlFile(String fileName) {

		String[] htmlFileContent = generateHtmlContent(generateHtmlTable());

		String text = LanguageController.getTranslation("Choose a directory to save the html file");

		exportDialog(text, fileName, htmlFileContent);

	}

	/**
	 * Export dialog of the HTML file with Search
	 * 
	 * @param fileName
	 *            (String | name of the new HTML file)
	 */
	public void exportHtmlFileWithSearch(String fileName) {

		String[] htmlFileContent = generateHtmlWithSearchContent(generateHtmlTableWithSearch());

		String text = LanguageController.getTranslation("Choose a directory to save the html file");

		exportDialog(text, fileName, htmlFileContent);

	}

	/**
	 * Export dialog for every possible file and every possible content
	 * 
	 * @param titleJFileChooser
	 *            (String | title of the window of the JFileChooser)
	 * @param fileName
	 *            (String | name of the new HTML file)
	 * @param content
	 *            (String[] | content of the new file)
	 */
	private static void exportDialog(String titleJFileChooser, String fileName, String[] content) {
		Path a = JFileChooserManager.chooseDirectoryGetPath(titleJFileChooser);

		if (a == null) {
			System.out.println("No Directory was selected!");
		} else {
			String filePath = a.toString();

			// add filename to filepath
			filePath += "\\" + fileName;

			// create file through new path
			File file = new File(filePath);

			// overwrite or just write the file with a dialog
			FileWriterManager.overWriteFileDialog(file, content);
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
	public static int getRandomNumber(int lowerLimit, int upperBound) {
		return ThreadLocalRandom.current().nextInt(lowerLimit, upperBound);
	}

	/**
	 * Change the optic if the system OS is Windows
	 */
	public static void windowsLookActivator() {

		try {
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

			if (System.getProperty("os.name").contains("Windows")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} else {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load an icon from the res folder
	 * 
	 * @param path
	 *            (String | path to file)
	 * @return ImageIcon if path exists - otherwise null
	 */
	public static ImageIcon loadImageIconFromClass(String string) {
		try {
			return new ImageIcon(ImageIO.read(ActionHandler.class.getResource(string)));
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (java.lang.IllegalArgumentException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Sets the program icon of a JFrame to the default one
	 * 
	 * @param jFrame
	 *            (JFrame | current window)
	 */
	public static void setProgramWindowIcon(JFrame jFrame) {

		int[] sizes = { 16, 32, 64, 128 };
		ArrayList<Image> imageList = new ArrayList<Image>();

		try {
			for (int size : sizes) {
				String path = "/icon_new/icon_new_" + size + ".png";
				imageList.add(ImageIO.read(ConceptJFrameGUI.class.getResource(path)));
			}

			jFrame.setIconImages(imageList);

		} catch (IOException exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * Open any String in the default web browser of the system:
	 * 
	 * inspired by http://stackoverflow.com/a/4898607 last edited from
	 * SingleShot
	 */
	public static void openUrlInDefaultBrowser(String urlToOpen) {

		// if we are on Windows
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(urlToOpen));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			// we are on Linux and open it like this:
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + urlToOpen);
				// runtime.exec("/usr/bin/firefox -new-window " + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open YouTube with the given search query
	 * 
	 * @param searchQuery
	 */
	public static void openYouTubeWithSearchQuery(String searchQuery) {
		// URL we want to open
		String urlToOpen = "https://www.youtube.com";

		// if text field has text try to add it to the urlToOpen
		try {
			if (searchQuery != null && searchQuery.length() > 0) {
				String textToSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");
				urlToOpen += "/results?search_query=" + textToSearchQuery;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// open URL in default web browser of the system
		openUrlInDefaultBrowser(urlToOpen);
	}

	public static MouseListener mouseChangeListener(JFrame a) {
		// add table mouse listener
		return (new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				a.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				a.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * Colors the actual table with two colors (every odd row)
	 * 
	 * @author bbhar | https://stackoverflow.com/a/26576892/7827128
	 */
	public static void colorTableWithTwoColors() {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		if (defaults.get("Table.alternateRowColor") == null)
			defaults.put("Table.alternateRowColor", new Color(240, 240, 240));
	}

	/**
	 * Comparator for number row (because somehow the default method doesn't
	 * work at all)
	 * 
	 * @author gustafc | https://stackoverflow.com/a/2683388/7827128
	 */
	public class NumberComparator implements Comparator<Number> {
		// compare method for comparing two numbers
		public int compare(Number a, Number b) {
			return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
		}
	}

	/**
	 * Check if a given String is a number
	 * 
	 * @param s
	 *            (String)
	 * @return true if numeric number else false
	 */
	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}
}