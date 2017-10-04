package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.json.JsonObject;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportImportSettings;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportMusicVideoData;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationHandler;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;

/**
 * Class that handles everything about music video files
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class MusicVideoHandler {

	/**
	 * All the important data about music video files and more is in here
	 */
	private ProgramData settingsData;

	/**
	 * Handles the playlist
	 */
	private MusicVideoPlaylist playlistHandler;

	/**
	 * This ArrayList<MusicVideo> contains all the MusicVideo objects and each
	 * object is a music video file with information about title, artist and path of
	 * the music video files that were found in the source folders
	 */
	private MusicVideo[] musicVideoList;

	/**
	 * The default name for the settings file
	 */
	private final File settingsFile;

	/**
	 * The table column names (needed for export)
	 */
	private String[] columnNames;

	// Constructor

	/**
	 * Constructor that creates an empty/default program data object
	 */
	public MusicVideoHandler() {
		this.settingsData = new ProgramData();
		this.settingsFile = new File("settings.json");
		this.columnNames = new String[] { "#", "Artist", "Title" };
		this.playlistHandler = new MusicVideoPlaylist();
	}

	// Methods

	/**
	 * Get the current settings data
	 * 
	 * @return settingsData (ProgramData)
	 */
	public ProgramData getSettingsData() {
		return settingsData;
	}

	/**
	 * Set the current settings (ProgramData)
	 * 
	 * @param settingsData
	 *            (ProgramData)
	 * @return true if new settings were applied
	 */
	public boolean setSettingsData(ProgramData settingsData) {

		if (settingsData != null) {
			this.settingsData = settingsData;
			System.out.println(">> New settings applied");
			return true;
		} else {
			System.out.println(">> New settings were not applied - Settings were null");
			return false;
		}
	}

	/**
	 * Get the current music video list
	 * 
	 * @return musicVideoList (MusicVideo[])
	 */
	public MusicVideo[] getMusicVideoList() {
		return musicVideoList;
	}

	/**
	 * Get if a settings file exists or nor
	 */
	public boolean settingsFileExist() {
		if (this.settingsFile.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Load settings from "settings.json" file if there is one
	 */
	public void loadSettingsFromFile() {
		if (settingsFileExist()) {
			loadSettings(settingsFile);
			// that was follows is beta and should read the files when it's installed like a
			// real program on windows
			// TODO
		} else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			File windowsSettingsFile = Paths.get(System.getProperty("user.home") + "\\" + settingsFile).toFile();
			if (windowsSettingsFile.exists()) {
				loadSettings(windowsSettingsFile);
			}
		}
	}

	/**
	 * Save settings in the default settings file
	 */
	public boolean saveSettingsToFile() {
		if (saveSettings(settingsFile)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add a directory path to the pathList
	 * 
	 * @param directoryPath
	 *            (Path)
	 * @return true if everything worked else false (Boolean)
	 */
	public boolean addPathToPathList(Path directoryPath) {

		// check if there is a settings file
		if (this.settingsData == null) {
			System.err.println("No settings found!");
			return false;
		}

		// check if the path is not null
		if (directoryPath == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		System.out.print(">> Add path " + directoryPath.toAbsolutePath() + " to path list.");

		// check if the path exists
		if (!directoryPath.toFile().exists()) {
			System.err.println(" << Path doesn't exist!");
			return false;
		}

		// check if the path is a directory
		if (directoryPath.toFile().isFile()) {
			System.err.println(" << Path is no directory!");
			return false;
		}

		// the rest does the settings class with the absolute path of the given path
		return this.settingsData.addPathToPathList(directoryPath.toAbsolutePath());

	}

	/**
	 * Add a path to the ignored files list
	 * 
	 * @param directoryPath
	 *            (Path)
	 * @return true if everything worked else false (Boolean)
	 */
	public boolean addIgnoredFileToIgnoredFilesList(Path directoryPath) {

		if (settingsData == null) {
			System.err.println("No settings found!");
			return false;
		}

		if (directoryPath == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		System.out.print(">> Add path " + directoryPath.toAbsolutePath() + " to ignored files list.");

		// check if the path exists
		if (!directoryPath.toFile().exists()) {
			System.err.println(" << Path doesn't exist!");
			return false;
		}

		// check if the path is a directory
		if (directoryPath.toFile().isDirectory()) {
			System.err.println(" << Path is no file!");
			return false;
		}

		// the rest does the settings class with the absolute path of the given path
		return this.settingsData.addFileToIgnoredFilesList(directoryPath.toAbsolutePath().toFile());
	}

	/**
	 * Print the music video list to the console in a table
	 */
	public void printMusicVideoList() {

		System.out.println(">> Print music video list:");

		if (this.musicVideoList == null) {
			System.err.println("<< Music video list doesn't exist!");
			return;
		}

		if (this.musicVideoList.length == 0) {
			System.err.println("<< Music video list is empty!");
			return;
		}

		MusicVideo[] musicVideoList = this.musicVideoList;

		int numberOfLinesNumber = String.valueOf(musicVideoList.length).length();
		int numberOfLinesTitle = 22;
		int numberOfLinesArtist = 15;
		int numberOfLinesPath = 80;

		String linesNumber = String.join("", Collections.nCopies(numberOfLinesNumber + 2, "-"));
		String linesTitle = String.join("", Collections.nCopies(numberOfLinesTitle + 2, "-"));
		String linesArtist = String.join("", Collections.nCopies(numberOfLinesArtist + 2, "-"));
		String linesPath = String.join("", Collections.nCopies(numberOfLinesPath + 2, "-"));

		String titleForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesTitle)));
		String artistForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesArtist)));
		String pathForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesPath)));

		String tableDataFormat = "| %-" + numberOfLinesNumber + "d | %-" + titleForm + "s | %-" + artistForm + "s | %-"
				+ pathForm + "s | %n";
		String tableInfoFormat = "| %-" + numberOfLinesNumber + "s | %-" + titleForm + "s | %-" + artistForm + "s | %-"
				+ pathForm + "s | %n";

		String tableInfo1 = ("+" + linesNumber + "+" + linesTitle + "+" + linesArtist + "+" + linesPath + "+%n");

		int numberShowInfo = 100, numberShowInfoCount = 0;
		for (int i = 0; i < musicVideoList.length; i++) {

			if (i == numberShowInfoCount) {
				System.out.format(tableInfo1);
				System.out.format(tableInfoFormat, "#", "Title", "Artist", "Path");
				System.out.format(tableInfo1);
				numberShowInfoCount += numberShowInfo;
			}

			System.out.format(tableDataFormat, i + 1, musicVideoList[i].getTitle(), musicVideoList[i].getArtist(),
					musicVideoList[i].getPath());
		}
		System.out.format(tableInfo1);

	}

	/**
	 * Print the music video list to the console in a table
	 */
	public void printWrongFormattedFiles() {

		System.out.println(">> Print wrong formatted files list:");

		Path[] wrongFormattedFiles = getWrongFormattedFiles();

		if (wrongFormattedFiles == null) {
			System.err.println("<< There are no files!");
			return;
		}

		if (wrongFormattedFiles.length == 0) {
			System.err.println("<< There were no wrong formatted files found!");
			return;
		}

		int numberOfLinesNumber = String.valueOf(wrongFormattedFiles.length).length();
		int numberOfLinesPath = 80;

		String linesNumber = String.join("", Collections.nCopies(numberOfLinesNumber + 2, "-"));
		String linesPath = String.join("", Collections.nCopies(numberOfLinesPath + 2, "-"));
		String pathForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesPath)));

		String tableDataFormat = "| %-" + numberOfLinesNumber + "d | %-" + pathForm + "s | %n";
		String tableInfoFormat = "| %-" + numberOfLinesNumber + "s | %-" + pathForm + "s | %n";

		String tableInfo1 = ("+" + linesNumber + "+" + linesPath + "+%n");

		int numberShowInfo = 100, numberShowInfoCount = 0;
		for (int i = 0; i < wrongFormattedFiles.length; i++) {

			if (i == numberShowInfoCount) {
				System.out.format(tableInfo1);
				System.out.format(tableInfoFormat, "#", "Wrong formatted files (Paths)");
				System.out.format(tableInfo1);
				numberShowInfoCount += numberShowInfo;
			}

			System.out.format(tableDataFormat, i + 1, wrongFormattedFiles[i]);
		}
		System.out.format(tableInfo1);

	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<MusicVideo> scanDirectory(Path path) {

		if (path == null || !path.toFile().isDirectory()) {
			System.err.println("Path is null or no directory!");
			return null;
		}

		// get all files in the directory
		Collection<Path> filesInDirectory = new ArrayList<Path>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {

			System.out.print(">> Scan " + path + " for files");
			for (Path child : ds) {
				filesInDirectory.add(child);
			}
			System.out.println(" << Scan finished.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(" << Problem while searching for Files!");
			return null;
		}

		ArrayList<MusicVideo> musicvideosinFolder = new ArrayList<MusicVideo>();

		System.out.println(">> Finding MusicVideo files");

		for (Path filePath : filesInDirectory) {

			MusicVideo musicVideoFile = isFileMusicVideo(filePath);

			if (musicVideoFile != null) {
				musicvideosinFolder.add(musicVideoFile);
			}

		}

		// return sorted Array
		return musicvideosinFolder;

	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<Path> scanDirectoryForWrongFiles(Path path) {

		if (path == null || !path.toFile().isDirectory()) {
			System.err.println("Path is null or no directory!");
			return null;
		}

		// get all files in the directory
		Collection<Path> filesInDirectory = new ArrayList<Path>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {

			System.out.print(">> Scan " + path + " for wrong files");
			for (Path child : ds) {
				filesInDirectory.add(child);
			}
			System.out.println(" << Scan finished.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(" << Problem while searching for Files!");
			return null;
		}

		ArrayList<Path> wrongFormattedFiles = new ArrayList<Path>();

		System.out.println(">> Finding wrong formatted files");

		for (Path filePath : filesInDirectory) {

			if (isFileMusicVideoButWrong(filePath)) {
				wrongFormattedFiles.add(filePath);
			}

		}

		// return sorted Array
		return wrongFormattedFiles;

	}

	/**
	 * Rescan and sort all files in the saved directories in the musicVideoList
	 * 
	 * @return
	 */
	public void updateMusicVideoList() {

		if (this.settingsData.getPathList() == null) {
			this.musicVideoList = null;
			System.out.println("There are no paths!");
			return;
		}

		ArrayList<MusicVideo> newMusicVideoList = new ArrayList<MusicVideo>();

		for (Path directory : this.settingsData.getPathList()) {
			newMusicVideoList.addAll(scanDirectory(directory));
		}

		MusicVideo[] newList = new MusicVideo[newMusicVideoList.size()];
		newList = newMusicVideoList.toArray(newList);

		// sort array over implemented comparator
		Arrays.sort(newList, new MusicVideo());

		this.musicVideoList = newList;

	}

	/**
	 * Rescan and get all wrong formatted files
	 * 
	 * @return
	 */
	public Path[] getWrongFormattedFiles() {

		if (this.settingsData.getPathList() == null) {
			System.err.println("There are no paths!");
			return null;
		}

		ArrayList<Path> newMusicVideoList = new ArrayList<Path>();

		for (Path directory : this.settingsData.getPathList()) {
			newMusicVideoList.addAll(scanDirectoryForWrongFiles(directory));
		}

		Path[] newList = new Path[newMusicVideoList.size()];
		newList = newMusicVideoList.toArray(newList);

		Arrays.sort(newList);
		return newList;

	}

	/**
	 * Open with the default desktop video player a file with the path of the
	 * MusicVideo object in the music video list
	 * 
	 * @param index
	 *            (index of the MusicVideo object in the music video list)
	 */
	public boolean openMusicVideo(int index) {

		if (this.musicVideoList == null) {
			System.err.println("The music videos list is null!");
			return false;
		}

		if (this.musicVideoList.length <= index || 0 > index) {
			System.err.println("The index " + index + " is out of bound!");
			return false;
		}

		MusicVideo videoToOpen = musicVideoList[index];
		File fileToOpen = videoToOpen.getPath().toFile();

		if (videoToOpen == null || fileToOpen == null) {
			System.err.println("The music video object's path is null!");
			return false;
		}

		System.out.println(">> Opening \"" + videoToOpen.getTitle() + "\" by \"" + videoToOpen.getArtist() + "\"");

		if (ExternalApplicationHandler.openFile(fileToOpen)) {
			System.out.println("<< File succsessfully opened");
			return true;
		} else {
			System.err.println("<< File was not opened!");
		}
		return false;

	}

	/**
	 * Check if a file is a correct formatted music video file
	 * 
	 * @param filePath
	 *            (Path | Path of file)
	 * @return true if it is a correct formatted music video file (boolean)
	 */
	private MusicVideo isFileMusicVideo(Path filePath) {

		if (this.settingsData.getIgnoredFiles() != null) {
			final File musicVideoFile = filePath.toFile();
			for (File ignoredFilePath : this.settingsData.getIgnoredFiles()) {
				try {
					if (ignoredFilePath.getCanonicalPath().equals(musicVideoFile.getCanonicalPath())) {
						System.err.println("File is a ignored File! (" + filePath + ")");
						return null;
					}
				} catch (IOException e) {
					System.err.println("Error");
					e.printStackTrace();
				}
			}
		}

		// file is a "normal" readable file
		if (Files.isRegularFile(filePath))

		{

			String fileType = null, pathOfFile = filePath.getFileName().toString();
			String[] artistAndTitle = null;

			int lastIndexOfPoint = pathOfFile.lastIndexOf('.');
			boolean containsArtistAndTitle = pathOfFile.contains(" - ");

			if (lastIndexOfPoint > 0 && containsArtistAndTitle) {

				fileType = pathOfFile.substring(lastIndexOfPoint + 1);
				artistAndTitle = pathOfFile.substring(0, lastIndexOfPoint).split(" - ", 2);

			} else {
				// -1 if '.' is not found or file doesn't have " - " for support of artist/title
				System.err.println("Incompatible filename! (" + filePath + ")");
				return null;
			}

			// now we compare it to our allowed extension array
			for (String supportedFileTypes : this.settingsData.getAcceptedFileTypes()) {
				if (supportedFileTypes.equalsIgnoreCase(fileType)) {

					// finally add the newMusicVideoObject to our
					// musicVideosList
					return new MusicVideo(filePath, artistAndTitle[1], artistAndTitle[0]);
				}
			}

		}
		return null;
	}

	/**
	 * Check if a file is not correct formatted and so *no* music video file
	 * 
	 * @param filePath
	 *            (Path | Path of file)
	 * @return true if it is not a music video file (boolean)
	 */
	private boolean isFileMusicVideoButWrong(Path filePath) {

		if (this.settingsData.getIgnoredFiles() != null) {
			final File musicVideoFile = filePath.toFile();
			for (File ignoredFilePath : this.settingsData.getIgnoredFiles()) {
				try {
					if (ignoredFilePath.getCanonicalPath().equals(musicVideoFile.getCanonicalPath())) {
						System.err.println("File is a ignored File! (" + filePath + ")");
						return false;
					}
				} catch (IOException e) {
					System.err.println("Error");
					e.printStackTrace();
				}
			}
		}

		// file is a "normal" readable file
		if (Files.isRegularFile(filePath)) {

			String fileType = null, pathOfFile = filePath.getFileName().toString();

			int lastIndexOfPoint = pathOfFile.lastIndexOf('.');
			boolean containsArtistAndTitle = pathOfFile.contains(" - ");

			boolean rightFileEnd = false;

			if (lastIndexOfPoint > 0) {
				fileType = pathOfFile.substring(lastIndexOfPoint + 1);
			} else {
				return false;
			}

			for (String supportedFileTypes : this.settingsData.getAcceptedFileTypes()) {
				if (supportedFileTypes.equalsIgnoreCase(fileType)) {

					rightFileEnd = true;
				}
			}

			if (rightFileEnd && !containsArtistAndTitle) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for tables
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public Object[][] musicVideoListToTable() {

		if (this.musicVideoList == null) {
			System.err.println("The music video list is null!");
			return null;
		}

		if (this.musicVideoList.length == 0) {
			System.err.println("The music video list is empty!");
			return null;
		}

		Object[][] tableData = new Object[this.musicVideoList.length][this.columnNames.length];

		System.out.println("New updated table:");
		for (int i = 0; i < this.musicVideoList.length; i++) {
			// set data to Object[][]
			tableData[i][0] = i + 1;
			tableData[i][1] = this.musicVideoList[i].getArtist();
			tableData[i][2] = this.musicVideoList[i].getTitle();

			// check it right now
			// System.out.print("#: " + tableData[a][0]);
			// System.out.print("\tArtist: " + tableData[a][1]);
			// System.out.println("\tTitle: " + tableData[a][2]);
		}

		return tableData;
	}

	/**
	 * Create the favicon folder with all the icons in a given directory
	 * 
	 * @param outputFolder
	 *            (Path | Path of directory in which the favicon folder should be
	 *            created)
	 * @return
	 */
	private boolean copyFavicons(Path outputFolder) {

		if (outputFolder == null) {
			System.err.println("Path is null!");
		}

		if (!outputFolder.toFile().exists()) {
			System.err.println("Folder to copy does not exist!");
		}

		try {

			outputFolder = outputFolder.toAbsolutePath();

			String faviconFolder = outputFolder.toString() + "/favicons";

			// create the favicon directory
			FileReadWriteModule.createDirectory(new File(faviconFolder));

			// copy all .png images
			Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

			for (Integer size : sizes) {
				String inputPath = "websites/favicons/favicon-" + size + "x" + size + ".png";
				Path outpuPath = Paths.get(faviconFolder + "/favicon-" + size + "x" + size + ".png");
				FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream(inputPath), outpuPath);
			}

			// copy .svg image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("websites/favicons/favicon.svg"),
					Paths.get(faviconFolder + "/favicon.svg"));

			// copy .ico image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("websites/favicons/icon.ico"),
					Paths.get(faviconFolder + "/icon.ico"));

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private String generateHeadName() {

		// string builder for all links
		StringBuilder specialHead = new StringBuilder("");

		String name = "MusicVideoManager";
		specialHead.append("<title>" + name + "</title>");
		specialHead.append("<meta name=\"apple-mobile-web-app-title\" content=\"" + name + "\">");
		specialHead.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
		specialHead.append("<meta name=\"application-name\" content=\"" + name + "\">");

		return specialHead.toString();
	}

	private String generateFaviconLinks() {

		// string builder for all links
		StringBuilder faviconLinks = new StringBuilder("");

		// firefox svg link
		faviconLinks.append("<link rel=\"icon\" type=\"image/svg+xml\" href=\"favicons/favicon.svg\">");

		// apple links
		faviconLinks.append("<link rel=\"apple-touch-icon\" href=\"favicons/favicon-180x180.png\">");
		faviconLinks.append("<link rel=\"mask-icon\" href=\"favicons/favicon.svg\" color=\"#000000\">");

		// add all .png images
		Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

		for (Integer size : sizes) {
			faviconLinks.append("<link rel=\"icon\" type=\"image/png\" href=\"favicons/favicon-" + size + "x" + size
					+ ".png\" sizes=\"" + size + "x" + size + "\">");
		}

		return faviconLinks.toString();
	}

	private String generateHtmlSearchPartyMain(boolean party) {
		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);
		JsonObject jsJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/js.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks());

		// add js
		htmlStatic.append("<script>");
		htmlStatic.append(JsonModule.getValueString(jsJsonContent, "w3-js"));

		// add css
		htmlStatic.append("</script><style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_searchable"));

		if (party) {
			htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_party"));
		}

		// close head and open body
		htmlStatic.append("</style></head><body>");

		if (party) {
			htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "floating-button-html_party"));
		}

		// add section begin
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party"));

		// add the overlay / table header
		String tableHeader = JsonModule.getValueString(htmlJsonContent, "overlay-html_party");
		tableHeader = tableHeader.replaceFirst("#", this.columnNames[0]);
		tableHeader = tableHeader.replaceFirst("Artist", this.columnNames[1]);
		tableHeader = tableHeader.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader);

		// add the second table header (for print)
		String tableHeader2 = JsonModule.getValueString(htmlJsonContent, "table-header-html_party");
		tableHeader2 = tableHeader2.replaceFirst("#", this.columnNames[0]);
		tableHeader2 = tableHeader2.replaceFirst("Artist", this.columnNames[1]);
		tableHeader2 = tableHeader2.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader2);

		if (party) {
			htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "form-start-html_party"));
		}

		// table data
		if (party) {
			htmlStatic.append(ExportMusicVideoData.generateHtmlTableDataParty(musicVideoListToTable()));
		} else {
			htmlStatic.append(ExportMusicVideoData.generateHtmlTableDataSearch(musicVideoListToTable()));
		}

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "before-form-html_party"));

		if (party) {
			htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "form-end-html_party"));
		}

		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_party"));

		htmlStatic.append("</body></html>");

		return htmlStatic.toString();
	}

	private String generateHtmlParty() {
		return generateHtmlSearchPartyMain(true);
	}

	private String generateHtmlSearch() {
		return generateHtmlSearchPartyMain(false);
	}

	private String generateHtmlStatic() {

		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_static"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks());

		// add css
		htmlStatic.append("<style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));

		// close head and open body
		htmlStatic.append("</style></head><body>");
		// add section begin: section-start-html_static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_static"));

		// add table header
		String tableHeader = JsonModule.getValueString(htmlJsonContent, "table-header-html_static");
		tableHeader = tableHeader.replaceFirst("#", this.columnNames[0]);
		tableHeader = tableHeader.replaceFirst("Artist", this.columnNames[1]);
		tableHeader = tableHeader.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader);

		// table data
		htmlStatic.append(ExportMusicVideoData.generateHtmlTableDataStatic(musicVideoListToTable()));

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_static"));

		return htmlStatic.toString();
	}

	public boolean saveHtmlList(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { generateHtmlStatic() });
	}

	public boolean saveHtmlSearch(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { generateHtmlSearch() });
	}

	public boolean saveHtmlParty(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		// now add a folder with the PHP files
		// + change the PHP link in HTML form

		// replace "html/html_party_live" with "live.html"
		// export live view as "live.html" next to index.html

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { generateHtmlParty() });
	}

	/**
	 * Save the music video list in CSV format (Excel)
	 * 
	 * @param whereToWriteTheFile
	 *            (Path)
	 * @return writeWasSuccsessful (Boolean)
	 */
	public boolean saveCsv(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeTextFile(whereToWriteTheFile.toFile(),
				ExportMusicVideoData.generateCsvContent(this.musicVideoList, this.columnNames).split("\n"));
	}

	/**
	 * Save the music video list in JSON format
	 * 
	 * @param whereToWriteTheFile
	 *            (Path)
	 * @return writeWasSuccsessful (Boolean)
	 */
	public boolean saveJson(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeTextFile(whereToWriteTheFile.toFile(),
				new String[] { ExportMusicVideoData.generateJsonContent(this.musicVideoList, this.columnNames) });
	}

	/**
	 * Load the current Settings from a file and set them as new settings
	 * 
	 * @param settingsFilePath
	 *            (File | File with the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean loadSettings(File settingsFilePath) {

		// read the settingsData
		if (setSettingsData(ExportImportSettings.readSettings(settingsFilePath))) {
			updateMusicVideoList();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Save the current Settings to a file
	 * 
	 * @param settingsFilePath
	 *            (File | File for the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean saveSettings(File settingsFilePath) {
		return ExportImportSettings.writeSettings(settingsFilePath, this.settingsData);
	}

	/**
	 * Returns FALSE if the new settings data from the file is different to the
	 * current settings data. If they aren't the same TRUE will be returned.
	 * 
	 * @param settingsFilePathNew
	 *            (File | File that contains settingsData in JSON format)
	 * @return theyAreTheSame (Boolean)
	 */
	public boolean compareSettings(File settingsFilePathNew) {
		return ExportImportSettings.compareSettingsFileToCurrent(settingsFilePathNew, this.settingsData);
	}

	/**
	 * Returns FALSE if the new settings data from the file is different to the
	 * current settings data. If they aren't the same TRUE will be returned.
	 * 
	 * @param settingsFilePathNew
	 *            (File | File that contains settingsData in JSON format)
	 * @return theyAreTheSame (Boolean)
	 */
	public boolean compareSettings() {
		return ExportImportSettings.compareSettingsFileToCurrent(this.settingsFile, this.settingsData);
	}

	public void setAlwaysSave(boolean b) {
		this.settingsData.setAlwaysSaveSettings(b);

	}

	public boolean getAlwaysSave() {
		return this.settingsData.getAlwaysSaveSettings();
	}

	public Path[] getPathList() {
		return this.settingsData.getPathList();
	}

	public void removeFromPathList(String filePathToRemove) {
		Path[] pathList = this.settingsData.getPathList();
		ArrayList<Path> newPathList = new ArrayList<Path>();

		for (Path path : pathList) {
			Path filePathToRemovePath = Paths.get(filePathToRemove);
			try {
				if (!Files.isSameFile(path, filePathToRemovePath)) {
					newPathList.add(path);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				newPathList.add(path);
			}
		}
		this.settingsData.setPathList(newPathList.toArray(new Path[0]));

		// update the music video list now
		updateMusicVideoList();
	}

	public void reset() {
		this.settingsData.resetSettings();
		// updateMusicVideoList();
	}

	public File[] getIgnoredFiles() {
		return this.settingsData.getIgnoredFiles();
	}

	public void removeFromIgnoredFilesList(File selectedFile) {
		File[] pathList = this.settingsData.getIgnoredFiles();
		ArrayList<File> newPathList = new ArrayList<File>();

		for (File path : pathList) {
			try {
				if (!Files.isSameFile(path.toPath(), selectedFile.toPath())) {
					newPathList.add(path);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				newPathList.add(path);
			}
		}
		this.settingsData.setIgnoredFiles(newPathList.toArray(new File[0]));

		// update the music video list now
		updateMusicVideoList();

	}

	public MusicVideoPlaylist getPlaylistHandler() {
		return playlistHandler;
	}

	public void setPlaylistHandler(MusicVideoPlaylist playlistHandler) {
		this.playlistHandler = playlistHandler;
	}

	public void addMusicVideoToPlaylist(int index, String author, String comment) {
		this.playlistHandler.add(this.musicVideoList[index], author, comment);
	}

	public void removeEnrtryFromPlaylist(int index) {
		this.playlistHandler.remove(index);
	}

}
