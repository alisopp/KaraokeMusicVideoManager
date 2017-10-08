package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.json.JsonObject;
import javax.json.JsonValue;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;

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
	private ProgramDataHandler settingsData;

	/**
	 * Handles the playlist
	 */
	private MusicVideoPlaylistHandler playlistHandler;

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

	/**
	 * Handles the SFTP
	 */
	private SftpHandler sftpController;

	// Constructor

	/**
	 * Constructor that creates an empty/default program data object
	 */
	public MusicVideoHandler() {
		this.settingsData = new ProgramDataHandler();
		this.settingsFile = new File("settings.json");
		this.columnNames = new String[] { "#", "Artist", "Title" };
		this.playlistHandler = new MusicVideoPlaylistHandler();
		this.sftpController = new SftpHandler();
	}

	// Methods

	/**
	 * Connect to SFTP source
	 * 
	 * @param userName
	 *            (user name | String)
	 * @param userPassword
	 *            (password for user name | String)
	 * @param serverAddress
	 *            (IP address of server | String)
	 * @param serverFilePath
	 *            (working directory | String)
	 * @return true if connection was established (boolean)
	 */
	public boolean sftpConnect(String userName, String userPassword, String serverAddress, String serverFilePath) {
		this.sftpController.connect(userName, userPassword, serverAddress, serverFilePath);
		return this.sftpController.isConnectionEstablished();
	}

	/**
	 * Get if SFTP connection is established
	 * 
	 * @return true if connected (boolean)
	 */
	public boolean sftpConnectionEstablished() {
		return this.sftpController.isConnectionEstablished();
	}

	/**
	 * Get the current settings data
	 * 
	 * @return settingsData (ProgramData)
	 */
	public ProgramDataHandler getSettingsData() {
		return settingsData;
	}

	/**
	 * Set the current settings (ProgramData)
	 * 
	 * @param settingsData
	 *            (ProgramData)
	 * @return true if new settings were applied
	 */
	public boolean setSettingsData(ProgramDataHandler settingsData) {

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
	 * Get if a settings file exists or nor
	 */
	public boolean installedOnWindows() {

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

		ArrayList<MusicVideo> musicvideosinFolder = new ArrayList<MusicVideo>();

		System.out.println(">> Finding MusicVideo files");

		for (Path filePath : scanDirectoryForFiles(path)) {

			MusicVideo musicVideoFile = isFileMusicVideo(filePath);

			if (musicVideoFile != null) {
				musicvideosinFolder.add(musicVideoFile);
			}
		}

		// return ArrayList with music video files
		return musicvideosinFolder;

	}

	private ArrayList<Path> scanDirectoryForFiles(Path path) {

		// check if path is a real existing directory
		if (path == null || path.toFile().isFile()) {
			System.err.println("Path is null or no directory!");
			return null;
		}

		// get all files in the directory by saving it in this list
		ArrayList<Path> filesInDirectory = new ArrayList<Path>();

		// and collecting it from this stream
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {

			System.out.print(">> Scan " + path + " for files");
			for (Path child : ds) {
				// if the file is a "regular" file -> no directory/link/etc.
				if (Files.isRegularFile(child)) {
					filesInDirectory.add(child);
				}
			}
			System.out.println(" << Scan finished.");

			return filesInDirectory;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(" << Problem while searching for Files!");
			return null;
		}
	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<Path> scanDirectoryForWrongFiles(Path path) {

		ArrayList<Path> wrongFormattedFiles = new ArrayList<Path>();

		System.out.println(">> Finding wrong formatted files");

		for (Path filePath : scanDirectoryForFiles(path)) {
			if (isFileMusicVideoButWrong(filePath)) {
				wrongFormattedFiles.add(filePath);
			}
		}

		// return ArrayList with wrong files
		return wrongFormattedFiles;

	}

	/**
	 * Rescan and sort all files in the saved directories in the musicVideoList
	 * 
	 * @return
	 */
	public MusicVideo[] updateMusicVideoList() {

		// check if there even is a path list
		if (this.settingsData.getPathList() == null) {
			System.out.println("There are no paths!");
			this.musicVideoList = null;
			return this.musicVideoList;
		}

		// collect music video files with an ArrayList
		ArrayList<MusicVideo> collectMusicVideos = new ArrayList<MusicVideo>();

		// by iterating over the path list
		for (Path directory : this.settingsData.getPathList()) {
			collectMusicVideos.addAll(scanDirectory(directory));
		}

		// convert then the ArrayList to a normal Array
		MusicVideo[] newMusicVideoList = collectMusicVideos.toArray(new MusicVideo[0]);

		// sort it
		Arrays.sort(newMusicVideoList, new MusicVideo());

		// set it as a class variable
		this.musicVideoList = newMusicVideoList;

		// and return it
		return this.musicVideoList;
	}

	/**
	 * Rescan and get all wrong formatted files
	 * 
	 * @return
	 */
	public Path[] getWrongFormattedFiles() {

		// check if there even is a path list
		if (this.settingsData.getPathList() == null) {
			System.err.println("There are no paths!");
			return null;
		}

		ArrayList<Path> newMusicVideoList = new ArrayList<Path>();

		for (Path directory : this.settingsData.getPathList()) {
			newMusicVideoList.addAll(scanDirectoryForWrongFiles(directory));
		}

		// convert ArrayList to a normal Array
		Path[] newList = newMusicVideoList.toArray(new Path[0]);

		// sort all paths
		Arrays.sort(newList);

		// return sorted array with all paths to wrong files
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

		if (ExternalApplicationModule.openFile(fileToOpen)) {
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

		// check if the file is already in the list
		if (this.settingsData.getIgnoredFiles() != null) {
			try {
				final File musicVideoFile = filePath.toFile();
				for (File ignoredFilePath : this.settingsData.getIgnoredFiles()) {
					if (ignoredFilePath.getCanonicalPath().equals(musicVideoFile.getCanonicalPath())) {
						System.err.println("File is a ignored File! (" + filePath + ")");
						return null;
					}
				}
			} catch (IOException e) {
				System.err.println("Error by comparing the file to the ignored files");
				e.printStackTrace();
			}
		}

		final String pathOfFile = filePath.getFileName().toString();
		final int lastIndexOfPoint = pathOfFile.lastIndexOf('.');

		if (lastIndexOfPoint > 0 && pathOfFile.contains(" - ")) {

			final String fileType = pathOfFile.substring(lastIndexOfPoint + 1);
			final String[] artistAndTitle = pathOfFile.substring(0, lastIndexOfPoint).split(" - ", 2);

			// now we compare it to our allowed extension array
			for (String supportedFileTypes : this.settingsData.getAcceptedFileTypes()) {
				if (supportedFileTypes.equalsIgnoreCase(fileType)) {

					// finally add the newMusicVideoObject to our musicVideosList
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

		return false;
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for tables
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public Object[][] musicVideoListToTable() {
		return MusicVideoDataExportHandler.musicVideoListToObjectArray(this.musicVideoList);
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

	private String generateFaviconLinks(String getBack) {

		// string builder for all links
		StringBuilder faviconLinks = new StringBuilder("");

		// firefox svg link
		faviconLinks.append("<link rel=\"icon\" type=\"image/svg+xml\" href=\"" + getBack + "favicons/favicon.svg\">");

		// apple links
		faviconLinks.append("<link rel=\"apple-touch-icon\" href=\"" + getBack + "favicons/favicon-180x180.png\">");
		faviconLinks.append("<link rel=\"mask-icon\" href=\"" + getBack + "favicons/favicon.svg\" color=\"#000000\">");

		// add all .png images
		Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

		for (Integer size : sizes) {
			faviconLinks.append("<link rel=\"icon\" type=\"image/png\" href=\"" + getBack + "favicons/favicon-" + size
					+ "x" + size + ".png\" sizes=\"" + size + "x" + size + "\">");
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
		htmlStatic.append("<!DOCTYPE html><html lang=\"en\"><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks(""));

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
			htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "floating-button-html_party")
					.replace("html_party_live.html", "index.php"));
		}

		// add section begin
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party"));

		// add the overlay / table header
		htmlStatic.append(
				JsonModule.getValueString(htmlJsonContent, "overlay-html_party").replaceFirst("#", this.columnNames[0])
						.replaceFirst("Artist", this.columnNames[1]).replaceFirst("Title", this.columnNames[2]));

		// add the second table header (for print)
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "table-header-html_party")
				.replaceFirst("#", this.columnNames[0]).replaceFirst("Artist", this.columnNames[1])
				.replaceFirst("Title", this.columnNames[2]));

		if (party) {
			htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "form-start-html_party"));
		}

		// table data
		if (party) {
			htmlStatic.append(MusicVideoDataExportHandler.generateHtmlTableDataParty(musicVideoListToTable()));
		} else {
			htmlStatic.append(MusicVideoDataExportHandler.generateHtmlTableDataSearch(musicVideoListToTable()));
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
		htmlStatic.append("<!DOCTYPE html><html lang=\"en\"><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_static"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks(""));

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
		htmlStatic.append(MusicVideoDataExportHandler.generateHtmlTableDataStatic(musicVideoListToTable()));

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_static"));

		htmlStatic.append("</body></html>");

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

		createPhpDirectoryWithFiles(outputDirectory);

		// now add a folder with the PHP files
		// + change the PHP link in HTML form

		// replace "HTML/html_party_live" with "live.html"
		// export live view as "live.html" next to index.html

		// delete old index.html file
		FileReadWriteModule.deleteFile(new File(outputDirectory.toString() + "/index.html"));

		FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/list.html"),
				new String[] { generateHtmlParty() });

		System.out.println(generateHtmlPartyPlaylist());

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.php"),
				new String[] { generateHtmlPartyPlaylist() });
	}

	private void createPhpDirectoryWithFiles(Path outputFolder) {

		if (outputFolder == null) {
			System.err.println("Path is null!");
		}

		if (!outputFolder.toFile().exists()) {
			System.err.println("Folder to copy does not exist!");
		}

		try {

			outputFolder = outputFolder.toAbsolutePath();

			String phpFolder = outputFolder.toString() + "/php";

			// create the favicon directory
			FileReadWriteModule.createDirectory(new File(phpFolder));

			// paste process.php
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/process.php"),
					new String[] { generateHtmlPartyProcess() });

			// paste form.php
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/form.php"),
					new String[] { generateHtmlPartyForm() });

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String generateHtmlPartyForm() {
		// string builder for the whole site
		StringBuilder phpForm = new StringBuilder("");

		// JSON data HTML file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/php.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);

		// add PHP before everything
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-html-form"));

		// add default head
		phpForm.append("<!DOCTYPE html><html lang=\"en\"><head>");
		// add generic head
		phpForm.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for php form
		phpForm.append(JsonModule.getValueString(phpJsonContent, "custom-head-form"));

		// add title and more
		phpForm.append(generateHeadName());

		// add links to all the images
		phpForm.append(generateFaviconLinks("../"));

		// add css
		phpForm.append("<style>");
		phpForm.append(JsonModule.getValueString(cssJsonContent, "styles_php_form"));

		// close head and open body
		phpForm.append("</style></head><body>");

		// add floating button for php form
		phpForm.append(JsonModule.getValueString(phpJsonContent, "floating-button-form"));

		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-title-form"));
		phpForm.append("Submit this song to the playlist:");
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-artist-form"));
		phpForm.append("from&nbsp;");
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-input-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "input-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "before-submit-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "submit-form"));
		phpForm.append(JsonModule.getValueString(phpJsonContent, "after-submit-form"));

		phpForm.append("</body></html>");

		return phpForm.toString();
	}

	private String generateHtmlPartyProcess() {

		// string builder for the whole site
		StringBuilder phpProcess = new StringBuilder("");

		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/php.json")[0]);

		// add php before everything
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "before-link-process")
				.replaceAll("html/html_party_live.html", "index.php"));
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "link-process").replace("html/html_party_live.html",
				"index.php"));
		phpProcess.append(JsonModule.getValueString(phpJsonContent, "after-link-process"));

		return phpProcess.toString();
	}

	private String generateHtmlPartyPlaylist() {

		// string builder for the whole site
		StringBuilder phpPlaylist = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);
		JsonObject phpJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/php.json")[0]);

		// add default head
		phpPlaylist.append("<!DOCTYPE html><html lang=\"en\"><head>");
		// add generic head
		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party_live"));

		// add title and more
		phpPlaylist.append(generateHeadName());

		// add links to all the images
		phpPlaylist.append(generateFaviconLinks(""));

		phpPlaylist.append("<style>");
		phpPlaylist.append(JsonModule.getValueString(cssJsonContent, "styles_party_live"));

		// close head and open body
		phpPlaylist.append("</style></head><body>");

		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "floating-button-html_party_live")
				.replace("html_party.html", "list.html"));

		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party_live"));

		phpPlaylist.append(JsonModule.getValueString(phpJsonContent, "php-data-live"));

		phpPlaylist.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_party_live"));

		phpPlaylist.append("</body></html>");
		return phpPlaylist.toString();
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
				MusicVideoDataExportHandler.generateCsvContent(this.musicVideoList, this.columnNames).split("\n"));
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
				new String[] { MusicVideoDataExportHandler.generateJsonContentTable(this.musicVideoList, this.columnNames) });
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
		if (setSettingsData(ProgramDataHandler2.readSettings(settingsFilePath))) {
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
		return ProgramDataHandler2.writeSettings(settingsFilePath, this.settingsData);
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
		return ProgramDataHandler2.compareSettingsFileToCurrent(settingsFilePathNew, this.settingsData);
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
		return ProgramDataHandler2.compareSettingsFileToCurrent(this.settingsFile, this.settingsData);
	}

	public boolean setAlwaysSave(boolean newValue) {
		return this.settingsData.setAlwaysSaveSettings(newValue);
	}

	public boolean getAlwaysSave() {
		return this.settingsData.getAlwaysSaveSettings();
	}

	public Path[] getPathList() {
		return this.settingsData.getPathList();
	}

	public void removeFromPathList(String filePathToRemove) {

		// new path list that has the capacity of the old one - 1
		ArrayList<Path> newPathList = new ArrayList<Path>(this.settingsData.getPathList().length - 1);

		for (Path path : this.settingsData.getPathList()) {
			try {
				if (!Files.isSameFile(path, Paths.get(filePathToRemove))) {
					newPathList.add(path);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// convert path list array list to array without sorting (not needed)
		this.settingsData.setPathList(newPathList.toArray(new Path[0]));

		// update the music video list now
		updateMusicVideoList();
	}

	/**
	 * Reset everything
	 */
	public void reset() {
		this.settingsData.reset();
		this.playlistHandler.reset();
		updateMusicVideoList();
	}

	public File[] getIgnoredFiles() {
		return this.settingsData.getIgnoredFiles();
	}

	public void removeFromIgnoredFilesList(File selectedFile) {

		// new ignored files list that has the capacity of the old one - 1
		ArrayList<File> newPathList = new ArrayList<File>(this.settingsData.getIgnoredFiles().length - 1);

		for (File path : this.settingsData.getIgnoredFiles()) {
			try {
				if (!Files.isSameFile(path.toPath(), selectedFile.toPath())) {
					newPathList.add(path);
				}
			} catch (IOException e) {
				e.printStackTrace();
				newPathList.add(path);
			}
		}
		this.settingsData.setIgnoredFiles(newPathList.toArray(new File[0]));

		// update the music video list now
		updateMusicVideoList();

	}

	public MusicVideoPlaylistHandler getPlaylistHandler() {
		return playlistHandler;
	}

	public void addMusicVideoToPlaylist(int index, String author, String comment) {

		MusicVideoPlaylistElement newElement = this.playlistHandler.add(index, this.musicVideoList[index - 1], author,
				comment);
		if (sftpController.isConnectionEstablished()) {
			syncPlaylist(newElement);
		}
	}

	public void editMusicVideoToPlaylist(int index, String author, String comment) {

		MusicVideoPlaylistElement newElement = this.playlistHandler.edit(index, author, comment);
		if (sftpController.isConnectionEstablished()) {
			editSyncPlaylist(newElement);
		}

	}

	public void removeEntryFromPlaylist(int index) {

		MusicVideoPlaylistElement newElement = this.playlistHandler.remove(index);

		if (sftpController.isConnectionEstablished()) {
			deleteSyncPlaylist(newElement);
		}

	}

	public void sftpDisconnect() {
		this.sftpController.disconnectSFTP();

	}

	public void sftpRetrievePlaylist() {

		if (sftpConnectionEstablished()) {
			FileReadWriteModule.deleteDirectoryWithFiles(new File("php"));
			this.sftpController.changeDirectory(this.settingsData.getWorkingDirectorySftp());
			boolean existingPhpDirectory = false;
			for (String file : this.sftpController.listFiles()) {
				if (file.equals("php")) {
					System.out.println("Found php directory");
					existingPhpDirectory = true;
				}
			}
			if (existingPhpDirectory) {
				FileReadWriteModule.createDirectory(Paths.get("php").toFile());
				this.sftpController.changeDirectory("php");
				for (String file : this.sftpController.listFiles(".json")) {
					this.sftpController.retrieveFile("php/" + file, file);
				}
				this.sftpController.changeDirectory(this.settingsData.getWorkingDirectorySftp());

				// get information from retrieved files
				File folder = FileSystems.getDefault().getPath("php").toFile(); // . for this directory

				for (File file : folder.listFiles()) {
					if (file.isFile()) {
						if (file.getName().matches("\\d+.json")) {
							System.out.println(file.getAbsolutePath() + " matched!");
							loadPlaylistData(file);
							file.delete();
						}
					}
				}
				FileReadWriteModule.deleteDirectoryWithFiles(new File("php"));

			}
		}
	}

	public void saveSftpLogin(String ipAddressSftp, String workingDirectorySftp, String usernameSftp) {
		this.settingsData.setIpAddressSftp(ipAddressSftp);
		this.settingsData.setUsernameSftp(usernameSftp);
		this.settingsData.setWorkingDirectorySftp(workingDirectorySftp);
	}

	public String getSftpIpAddress() {
		return this.settingsData.getIpAddressSftp();
	}

	public String getSftpUsername() {
		return this.settingsData.getUsernameSftp();
	}

	public String getSftpDirectory() {
		return this.settingsData.getWorkingDirectorySftp();
	}

	public void loadPlaylistData(File file) {
		if (file == null || file.isDirectory()) {
			System.err.println("Playlist element could not be loaded because the file doesn't exist!");
			return;
		}
		Object[] data = ProgramDataHandler2.readPlaylistEntryFile(file);

		if (data != null) {

			this.playlistHandler.load((long) data[0], (int) data[1] + 1, this.musicVideoList[(int) data[1]],
					(String) data[2], (String) data[3], (boolean) data[4]);

			return;
		}
		System.err.println("Playlist element could not be loaded");
	}

	public void syncPlaylist(MusicVideoPlaylistElement element) {
		FileReadWriteModule.createDirectory(new File("php"));
		File whereToWrite = new File("php/" + Long.toString(element.getUnixTime()) + ".json");
		FileReadWriteModule.writeTextFile(whereToWrite,
				new String[] { ProgramDataHandler2.writePlaylistEntryFile(element) });
		this.sftpController.changeDirectory(this.getSftpDirectory());
		this.sftpController.changeDirectory("php");
		this.sftpController.transferFile(whereToWrite.getAbsolutePath());
		FileReadWriteModule.deleteFile(whereToWrite);
		FileReadWriteModule.deleteDirectory(new File("php"));

	}

	public void editSyncPlaylist(MusicVideoPlaylistElement element) {
		this.sftpController.changeDirectory(this.getSftpDirectory());
		this.sftpController.changeDirectory("php");
		String fileLocation = Long.toString(element.getUnixTime()) + ".json";
		this.sftpController.removeFile(fileLocation);
		FileReadWriteModule.createDirectory(new File("php"));
		File whereToWrite = new File("php/" + fileLocation);
		FileReadWriteModule.writeTextFile(whereToWrite,
				new String[] { ProgramDataHandler2.writePlaylistEntryFile(element) });
		this.sftpController.transferFile(whereToWrite.getAbsolutePath());
		FileReadWriteModule.deleteFile(whereToWrite);
	}

	public void deleteSyncPlaylist(MusicVideoPlaylistElement element) {
		this.sftpController.changeDirectory(this.getSftpDirectory());
		this.sftpController.changeDirectory("php");
		String fileLocation = Long.toString(element.getUnixTime()) + ".json";
		this.sftpController.removeFile(fileLocation);
	}

	public void clearPlaylist() {
		this.playlistHandler.reset();

	}

	public void savePlaylist(File filePath) {
		FileReadWriteModule.writeTextFile(filePath, new String[] {
				MusicVideoDataExportHandler.generateJsonContentPlaylist(this.playlistHandler.getPlaylistElements()) });
	}

	public void loadPlaylist(File file) {
		String[] playlistTextContent = FileReadWriteModule.readTextFile(file);

		if (playlistTextContent != null) {
			this.playlistHandler.reset();
			JsonObject playlist = JsonModule.loadJsonFromString(playlistTextContent[0]);
			for (JsonValue jsonObject : playlist.getJsonArray("playlist")) {
				Path pathToFile = Paths.get(jsonObject.asJsonObject().getString("file-path"));

				int indexInList = inMusicVideoPlaylist(pathToFile);
				if (indexInList != -1) {
					System.out.println("File was recognized");
					String author = jsonObject.asJsonObject().getString("author");
					String comment = jsonObject.asJsonObject().getString("comment");
					long unixTime = Long.parseLong(jsonObject.asJsonObject().get("time").toString());
					boolean createdLocally = jsonObject.asJsonObject().getBoolean("created-locally");

					this.playlistHandler.load(unixTime, indexInList + 1, this.musicVideoList[indexInList], author,
							comment, createdLocally);
				} else {
					System.err.println("Playlist entry file not found in music video list!");
				}

			}
		}
	}

	public int inMusicVideoPlaylist(Path searchPath) {
		File fileToFind = searchPath.toFile();
		try {
			for (int i = 0; i < this.musicVideoList.length; i++) {
				if (this.musicVideoList[i].getPath().toFile().getCanonicalPath()
						.equals(fileToFind.getCanonicalPath())) {
					return i;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public MusicVideo getMusicVideoOfPlaylistItem(Path pathOfMusicVideo) {

		if (this.musicVideoList == null) {
			return null;
		}

		try {
			for (MusicVideo musicVideoElement : this.musicVideoList) {
				if (Files.isSameFile(musicVideoElement.getPath(), pathOfMusicVideo)) {
					return musicVideoElement;
				}
			}
		} catch (IOException e) {

		}
		return null;
	}

	public void clearIgnoredFilesList() {
		this.settingsData.resetIgnoredFilesList();
		// update the music video list now
		updateMusicVideoList();

	}

}
