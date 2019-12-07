package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentPartyGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentSearchGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlContentStaticGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.generator.HtmlPartyGenerator;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ExternalApplicationModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideo;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.MusicVideoPlaylistElement;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.translations.Internationalization;

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
	private ProgramDataHandler programDataHandler;

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
	 * The default file for the settings file
	 */
	private final File settingsFile;

	/**
	 * The default directory for the settings file when installed on windows
	 */
	private final File windowsSettingsFileDirectory;

	/**
	 * The default file for the settings file when installed on windows
	 */
	private final File windowsSettingsFile;

	/**
	 * The default name for the servers file to save the IP addresses
	 */
	private final String ipAddressFileName;

	/**
	 * The default name for the servers directory for all PHP data and scripts
	 */
	private final String phpDirectoryName;

	/**
	 * The table column names (needed for export)
	 */
	private String[] columnNames;

	/**
	 * The sizes of the browser icons
	 */
	private final int[] faviconSizes;

	/**
	 * Handles the SFTP
	 */
	private SftpHandler sftpController;

	// Constructor

	/**
	 * Constructor that creates an empty/default program data object
	 */
	public MusicVideoHandler() {

		System.out.println(">> MusicVideoHandler constructor");

		this.programDataHandler = new ProgramDataHandler();
		this.settingsFile = new File("settings.json");
		this.windowsSettingsFileDirectory = new File(System.getProperty("user.home") + "/KaraokeMusicVideoManager");
		this.windowsSettingsFile = new File(
				System.getProperty("user.home") + "/KaraokeMusicVideoManager/" + this.settingsFile);
		this.columnNames = new String[] { "#", Internationalization.translate("Artist"),
				Internationalization.translate("Title") };
		this.playlistHandler = new MusicVideoPlaylistHandler();
		this.sftpController = new SftpHandler();
		this.faviconSizes = new int[] { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };
		this.ipAddressFileName = "ipBook.json";
		this.phpDirectoryName = "php";
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

		if (this.sftpController != null && this.sftpController.isConnectionEstablished()) {
			return true;
		} else {
			return false;
		}
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
			this.programDataHandler = settingsData;
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
	 * Get if a settings file exists or not
	 */
	public boolean settingsFileExist() {
		return this.settingsFile.exists();
	}

	/**
	 * Get if a settings file exists in the users home directory if he installed it
	 * on windows
	 */
	public boolean windowsSettingsFileExists() {

		// check if the program is installed on Windows
		if (isInstalledOnWindows()) {

			// and then check if the windows settings file exists
			if (this.windowsSettingsFile.exists()) {
				return true;
			}

		}
		return false;
	}

	/**
	 * Get if a settings file exists in the users home directory if he installed it
	 * on windows
	 */
	public boolean isInstalledOnWindows() {

		// check if the system is windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {

			// check if this is executed in the install directory
			if (Paths.get(".").toAbsolutePath().toString().contains("(x86)")
					|| Paths.get(".").toAbsolutePath().toString().contains("(x64)")) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Load settings from "settings.json" file if there is one
	 */
	public void loadSettingsFromFile() {

		System.out.println(">> Search for a settings file (\"" + settingsFile.toString() + "\")");

		if (settingsFileExist()) {

			System.out.println("<< Settings file found");
			loadSettings(this.settingsFile);
			return;

		} else if (windowsSettingsFileExists()) {

			System.out.println("<< Windows settings file found");

			loadSettings(this.windowsSettingsFile);
			return;

		}
		System.out.println("<< Settings file not found");
	}

	/**
	 * Save settings in the default settings file
	 */
	public boolean saveSettingsToFile() {

		if (isInstalledOnWindows()) {
			FileReadWriteModule.createDirectory(this.windowsSettingsFileDirectory);
			return saveSettings(this.windowsSettingsFile);
		} else {
			return saveSettings(this.settingsFile);
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
		if (this.programDataHandler == null) {
			System.err.println("No settings found!");
			return false;
		}

		// check if the path is not null
		if (directoryPath == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		System.out.print(">> Add path " + directoryPath + " to path list.");

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
		return this.programDataHandler.addPathToPathList(directoryPath.toAbsolutePath().normalize());

	}

	/**
	 * Add a path to the ignored files list
	 * 
	 * @param directoryPath
	 *            (Path)
	 * @return true if everything worked else false (Boolean)
	 */
	public boolean addIgnoredFileToIgnoredFilesList(Path directoryPath) {

		if (programDataHandler == null) {
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
		return this.programDataHandler.addFileToIgnoredFilesList(directoryPath.toAbsolutePath().toFile());
	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<MusicVideo> scanDirectory(Path path) {

		final ArrayList<MusicVideo> musicvideosinFolder = new ArrayList<MusicVideo>();

		System.out.println(">> Finding MusicVideo files");

		final Path[] filesInDirectory = scanDirectoryForFiles(path);

		for (int i = 0; i < filesInDirectory.length; i++) {

			final MusicVideo musicVideoFile = isFileMusicVideo(filesInDirectory[i]);

			if (musicVideoFile != null) {
				musicvideosinFolder.add(musicVideoFile);
			}
		}

		// return ArrayList with music video files
		return musicvideosinFolder;

	}

	/**
	 * Scan a directory for files (paths)
	 * 
	 * @param path
	 *            (Path | Path of directory)
	 * @return list of files in this directory or null (ArrayList<Path>)
	 */
	private Path[] scanDirectoryForFiles(Path path) {

		// check if path is a real existing directory
		if (path == null || path.toFile().isFile()) {
			System.err.println("Path is null or no directory!");
			return null;
		}

		// get all files in the directory by saving it in this list
		final ArrayList<Path> filesInDirectory = new ArrayList<Path>();

		// and collecting it from this stream
		try (final DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {

			System.out.print(">> Scan " + path + " for files");
			for (Path child : ds) {
				// if the file is a "regular" file -> no directory/link/etc.
				if (Files.isRegularFile(child)) {
					filesInDirectory.add(child);
				}
			}
			System.out.println(" << Scan finished.");

			return filesInDirectory.toArray(new Path[0]);
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

		final ArrayList<Path> wrongFormattedFiles = new ArrayList<Path>();

		System.out.println(">> Finding wrong formatted files");

		final Path[] filesInDirectory = scanDirectoryForFiles(path);

		for (int i = 0; i < filesInDirectory.length; i++) {
			if (isFileMusicVideoButWrong(filesInDirectory[i])) {
				wrongFormattedFiles.add(filesInDirectory[i]);
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
		if (this.programDataHandler.getPathList() == null) {
			System.out.println("There are no paths!");
			this.musicVideoList = null;
			return this.musicVideoList;
		}

		// collect music video files with an ArrayList
		final ArrayList<MusicVideo> collectMusicVideos = new ArrayList<MusicVideo>();

		// by iterating over the path list
		final Path[] pathList = this.programDataHandler.getPathList();
		for (int i = 0; i < pathList.length; i++) {
			collectMusicVideos.addAll(scanDirectory(pathList[i]));
		}

		// convert then the ArrayList to a normal Array
		final MusicVideo[] newMusicVideoList = collectMusicVideos.toArray(new MusicVideo[0]);

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
		if (this.programDataHandler.getPathList() == null) {
			System.err.println("There are no paths!");
			return null;
		}

		final ArrayList<Path> newMusicVideoList = new ArrayList<Path>();

		final Path[] filesInDirectory = this.programDataHandler.getPathList();

		for (int i = 0; i < filesInDirectory.length; i++) {
			newMusicVideoList.addAll(scanDirectoryForWrongFiles(filesInDirectory[i]));
		}

		// convert ArrayList to a normal Array
		final Path[] newList = newMusicVideoList.toArray(new Path[0]);

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

		final MusicVideo videoToOpen = musicVideoList[index];

		if (videoToOpen == null) {
			System.err.println("The music video object is null!");
			return false;
		}

		final File fileToOpen = videoToOpen.getPath().toFile();

		if (fileToOpen == null) {
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
		if (this.programDataHandler.getIgnoredFiles() != null) {
			try {
				final File musicVideoFile = filePath.toFile();
				for (File ignoredFilePath : this.programDataHandler.getIgnoredFiles()) {
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

		if (filePath != null) {

			final Path pathOfFile = filePath.getFileName();

			if (pathOfFile != null) {

				final String pathOfFileString = pathOfFile.toString();

				final int lastIndexOfPoint = pathOfFileString.lastIndexOf('.');

				if (lastIndexOfPoint > 0 && pathOfFileString.contains(" - ")) {

					final String fileType = pathOfFileString.substring(lastIndexOfPoint + 1);
					final String[] artistAndTitle = pathOfFileString.substring(0, lastIndexOfPoint).split(" - ", 2);

					// now we compare it to our allowed extension array
					for (String supportedFileTypes : this.programDataHandler.getAcceptedFileTypes()) {
						if (supportedFileTypes.equalsIgnoreCase(fileType)) {

							// finally add the newMusicVideoObject to our musicVideosList
							return new MusicVideo(filePath, artistAndTitle[1], artistAndTitle[0]);
						}
					}
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

		if (this.programDataHandler.getIgnoredFiles() != null) {
			final File musicVideoFile = filePath.toFile();
			for (File ignoredFilePath : this.programDataHandler.getIgnoredFiles()) {
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

		if (filePath != null) {

			final Path pathOfFile = filePath.getFileName();

			if (pathOfFile != null) {

				final String pathOfFileString = pathOfFile.toString();

				final int lastIndexOfPoint = pathOfFileString.lastIndexOf('.');
				final boolean containsArtistAndTitle = pathOfFileString.contains(" - ");

				if (lastIndexOfPoint > 0) {
					final String fileType = pathOfFileString.substring(lastIndexOfPoint + 1);

					for (int i = 0; i < this.programDataHandler.getAcceptedFileTypes().length; i++) {
						if (this.programDataHandler.getAcceptedFileTypes()[i].equalsIgnoreCase(fileType)) {
							if (!containsArtistAndTitle) {
								return true;
							}
						}
					}
				}
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
			return false;
		}

		if (!outputFolder.toFile().exists()) {
			System.err.println("Folder to copy does not exist!");
			return false;
		}

		try {

			outputFolder = outputFolder.toAbsolutePath();

			String faviconFolder = outputFolder.toString() + "/favicons";

			// create the favicon directory
			FileReadWriteModule.createDirectory(new File(faviconFolder));

			// copy all .png images
			Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

			for (Integer size : sizes) {
				String inputPath = "images/favicons/favicon-" + size + "x" + size + ".png";
				Path outpuPath = Paths.get(faviconFolder + "/favicon-" + size + "x" + size + ".png");
				FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream(inputPath), outpuPath);
			}

			// copy .svg image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("images/favicons/favicon.svg"),
					Paths.get(faviconFolder + "/favicon.svg"));

			// copy .ico image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("images/favicons/icon.ico"),
					Paths.get(faviconFolder + "/icon.ico"));

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean saveHtmlList(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		HtmlContentGenerator generator = new HtmlContentStaticGenerator();
		Object[][] musicVideoTable = musicVideoListToTable();
		
		String htmlContent = generator.generateHtml(this.phpDirectoryName, musicVideoTable, columnNames);
		
		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { htmlContent });
	}

	public boolean saveHtmlSearch(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}
		
		HtmlContentGenerator generator = new HtmlContentSearchGenerator();
		Object[][] musicVideoTable = musicVideoListToTable();
		
		String htmlContent = generator.generateHtml(this.phpDirectoryName, musicVideoTable, columnNames);

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { htmlContent });
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
		
		HtmlContentGenerator contentGenerator = new HtmlContentPartyGenerator();
		Object[][] musicVideoTable = musicVideoListToTable();
		
		String htmlContent = contentGenerator.generateHtml(phpDirectoryName, musicVideoTable, columnNames);

		FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/list.html"),
				new String[] { htmlContent });
		
		HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
		String partyPlaylist = partyGenerator.generateHtmlPartyPlaylist(null, true, true);

		FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.php"),
				new String[] { partyPlaylist });
		
		partyPlaylist = partyGenerator.generateHtmlPartyPlaylist(phpDirectoryName, true, true);

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index2.php"),
				new String[] { partyPlaylist });
	}

	private void createPhpDirectoryWithFiles(Path outputFolder) {

		if (outputFolder == null) {
			System.err.println("Path is null!");
			return;
		}

		if (!outputFolder.toFile().exists()) {
			System.err.println("Folder to copy does not exist!");
		}

		try {

			outputFolder = outputFolder.toAbsolutePath();

			String phpFolder = outputFolder.toString() + "/" + this.phpDirectoryName;

			// create the favicon directory
			FileReadWriteModule.createDirectory(new File(phpFolder));
			
			// paste process.php
			HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
			String partyProcess = partyGenerator.generateHtmlPartyProcess();
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/process.php"),
					new String[] { partyProcess });

			// paste form.php
			String partyForm = partyGenerator.generateHtmlPartyForm();
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/form.php"),
					new String[] { partyForm });

			// paste view.php
			String partyView = partyGenerator.generateHtmlPartyView(true);
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/live.php"),
					new String[] { partyView });

			// paste view.php
			String partyVote = partyGenerator.generateHtmlPartyVote();
			FileReadWriteModule.writeTextFile(new File(phpFolder.toString() + "/vote.php"),
					new String[] { partyVote });

		} catch (Exception e) {
			e.printStackTrace();
		}

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
				MusicVideoDataExportHandler.generateCsvContent(this.musicVideoList, columnNames).split("\n"));
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
				MusicVideoDataExportHandler.generateJsonContentTable(this.musicVideoList));
	}

	/**
	 * Load the current Settings from a file and set them as new settings
	 * 
	 * @param settingsFilePath
	 *            (File | File with the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean loadSettings(File settingsFilePath) {
		return setSettingsData(this.programDataHandler.readSettings(settingsFilePath));
	}

	/**
	 * Save the current Settings to a file
	 * 
	 * @param settingsFilePath
	 *            (File | File for the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean saveSettings(File settingsFilePath) {
		return this.programDataHandler.writeSettings(settingsFilePath);
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

		if (windowsSettingsFileExists()) {
			return this.programDataHandler.compareSettingsFileToCurrent(this.windowsSettingsFile);
		} else {
			return this.programDataHandler.compareSettingsFileToCurrent(this.settingsFile);
		}

	}

	/**
	 * Set always save option
	 * 
	 * @param newValue
	 *            (boolean)
	 * @return new value (boolean)
	 */
	public boolean setAlwaysSave(boolean newValue) {
		return this.programDataHandler.setAlwaysSaveSettings(newValue);
	}

	/**
	 * Get always save option
	 * 
	 * @return value (boolean)
	 */
	public boolean getAlwaysSave() {
		return this.programDataHandler.getAlwaysSaveSettings();
	}

	/**
	 * Get the current path list
	 * 
	 * @return path list (Path[])
	 */
	public Path[] getPathList() {
		return this.programDataHandler.getPathList();
	}

	/**
	 * Remove a path from the path list
	 * 
	 * @param filePathToRemove
	 */
	public void removeFromPathList(Path filePathToRemove) {

		// try to remove the path
		if (this.programDataHandler.removeFromPathList(filePathToRemove)) {

			// update the music video list if path list now different
			updateMusicVideoList();
		}

	}

	/**
	 * Reset everything
	 */
	public void reset() {
		this.programDataHandler = new ProgramDataHandler();
		this.playlistHandler = new MusicVideoPlaylistHandler();
		this.sftpController = new SftpHandler();
		updateMusicVideoList();
	}

	/**
	 * Get the ignored files in a list
	 * 
	 * @return ignored files list
	 */
	public File[] getIgnoredFiles() {
		return this.programDataHandler.getIgnoredFiles();
	}

	/**
	 * Remove a file from the ignored Files list
	 * 
	 * @param selectedFile
	 *            (Path)
	 */
	public void removeFromIgnoredFilesList(Path selectedFile) {

		// try to remove the file
		if (this.programDataHandler.removeFromIgnoredFilesList(selectedFile)) {

			// update the music video list if the new ignored files list is different
			updateMusicVideoList();
		}

	}

	/**
	 * @return MusicVideoPlaylistHandler which handles the playlist
	 */
	public MusicVideoPlaylistHandler getPlaylistHandler() {
		return this.playlistHandler;
	}

	/**
	 * Add a music video to the current playlist
	 * 
	 * @param index
	 *            (Integer | Position of music video in the musicVideoList)
	 * @param author
	 *            (String | Author of playlist entry)
	 * @param comment
	 *            (String | Comment of author)
	 */
	public void addMusicVideoToPlaylist(int index, String author, String comment) {

		System.out.println(">> Playlist: Add " + this.musicVideoList[index - 1].getTitle() + " from " + author);

		// add MusicVideoPlaylistElement to the playlist
		MusicVideoPlaylistElement newElement = this.playlistHandler.add(index, this.musicVideoList[index - 1], author,
				comment);

		// if connected to server upload playlist entry
		if (sftpConnectionEstablished()) {
			uploadPlaylistEntry(newElement);
		}
	}

	public void editMusicVideoToPlaylist(int index, String author, String comment) {

		MusicVideoPlaylistElement newElement = this.playlistHandler.edit(index, author, comment);
		if (sftpConnectionEstablished()) {

			// remove the old playlist entry
			deletePlaylistEntrySftp(newElement);

			// then upload the edited playlist entry
			uploadPlaylistEntry(newElement);
		}

	}

	public void removeEntryFromPlaylist(int index) {

		MusicVideoPlaylistElement newElement = this.playlistHandler.remove(index);

		if (sftpConnectionEstablished()) {
			deletePlaylistEntrySftp(newElement);
		}

	}

	public void sftpDisconnect() {
		this.sftpController.disconnectSFTP();
	}

	public void sftpRetrievePlaylist() {

		// check if a connection was established
		if (sftpConnectionEstablished()) {

			// go into the default directory
			this.sftpController.changeDirectory(this.programDataHandler.getWorkingDirectorySftp());

			// then check if a directory named PHP exists
			boolean existingPhpDirectory = false;
			for (String file : this.sftpController.listFiles()) {
				if (file.equals(this.phpDirectoryName)) {
					System.out.println("Found php directory");
					existingPhpDirectory = true;
				}
			}

			// if yes
			if (existingPhpDirectory) {

				// go into it
				this.sftpController.changeDirectory(this.phpDirectoryName);

				// now clean the list
				this.playlistHandler.setPlaylistElements(null);

				// and collect all files
				for (String file : this.sftpController.listFiles(".json")) {

					// if the file has the format of a playlist entry file
					if (file.matches("\\d+.json")) {

						System.out.println("Found a playlist file (" + file + ")");

						// get the content of the file
						String contentOfFile = this.sftpController.retrieveFileInputStreamString(file);

						// and convert the data to a playlist entry
						Object[] contentData = this.playlistHandler.readPlaylistEntryFile(contentOfFile);
						if (contentData != null) {
							System.out.println("Musicvideolist length: " + this.musicVideoList.length);
							System.out.println("number: " + contentData[1]);
							// last but not least import it to the playlist

							if (this.musicVideoList.length > (int) contentData[1] - 1 && (int) contentData[1] > 0) {
								// create new entry
								long unixTime = (long) contentData[0];
								int musicVideoIndex = (int) contentData[1];
								MusicVideo musicVideo = this.musicVideoList[(int) contentData[1] - 1];
								String author = (String) contentData[2];
								String comment = (String) contentData[3];
								boolean isLocally = (boolean) contentData[4];
								int votes = (int) contentData[5];
								
								MusicVideoPlaylistElement newEntry = new MusicVideoPlaylistElement(unixTime, musicVideoIndex, musicVideo,
										author, comment, isLocally, votes, file);
								
								this.playlistHandler.load(newEntry, file);
							} else {
								if (DialogHandler.confirm(Internationalization.translate("Index problem"),
										Internationalization.translate(
												"Press yes to solve this automatically by doing the following"),
										Internationalization.translate(
												"The server list seems to be another one than your local list. Setup the server again and clear the playlist to continue."))) {
									
									HtmlContentGenerator generator = new HtmlContentPartyGenerator();
									transferHtml(generator);
									clearPlaylist();

								}
							}
						}
					}
				}
			}
		}
	}

	public void saveSftpLogin(String ipAddressSftp, String workingDirectorySftp, String usernameSftp) {
		this.programDataHandler.setIpAddressSftp(ipAddressSftp);
		this.programDataHandler.setUserNameSftp(usernameSftp);
		this.programDataHandler.setWorkingDirectorySftp(workingDirectorySftp);
	}

	public String getSftpIpAddress() {
		return this.programDataHandler.getIpAddressSftp();
	}

	public String getSftpUsername() {
		return this.programDataHandler.getUserNameSftp();
	}

	public String getSftpDirectory() {
		return this.programDataHandler.getWorkingDirectorySftp();
	}

	/**
	 * Upload a playlist entry to SFTP server
	 * 
	 * @param element
	 *            (MusicVideoPlaylistElement)
	 */
	public void uploadPlaylistEntry(MusicVideoPlaylistElement element) {

		System.out.println("Upload MusicVideoPlaylistEntry...");

		// then change into the php directory
		this.sftpController.changeDirectory(this.programDataHandler.getWorkingDirectorySftp());
		this.sftpController.changeDirectory(this.phpDirectoryName);

		final String[] listFiles = this.sftpController.listFiles(".json");

		int highestNumber = -1;

		if (listFiles != null) {

			int tempNumber = highestNumber;

			for (int i = 0; i < listFiles.length; i++) {

				if (listFiles[i].matches("\\d+.json")) {
					tempNumber = Integer.parseInt(listFiles[i].substring(0, listFiles[i].lastIndexOf('.')));
					if (highestNumber < tempNumber) {
						highestNumber = tempNumber;
					}
				}
			}
		}

		// then transfer the new file
		// by crating an input stream of the content string
		final InputStream stream = FileReadWriteModule
				.stringToInputStream(this.playlistHandler.writePlaylistEntryFile(element));

		// and then sending it with a filename to the server
		this.sftpController.transferFile(stream, (Integer.toString(highestNumber + 1) + ".json"));

		// change permissions
		this.sftpController.changeDirectory("../");
		// this.sftpController.changePermissions(this.phpDirectoryName, 509);
		this.sftpController.changePermissions(this.phpDirectoryName, 777);
		this.sftpController.changeDirectory(this.phpDirectoryName);
		final String[] fileList = this.sftpController.listFiles(".json");
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].matches("\\d+.json")) {
				this.sftpController.changePermissions(fileList[i], 777);
			}
		}

	}

	/**
	 * Delete a playlist entry
	 * 
	 * @param element
	 *            (MusicVideoPlaylistElement)
	 */
	public void deletePlaylistEntrySftp(MusicVideoPlaylistElement element) {

		// go into the playlist directory
		this.sftpController.changeDirectory(this.programDataHandler.getWorkingDirectorySftp());
		this.sftpController.changeDirectory(this.phpDirectoryName);
		this.sftpController.removeFile(element.getFileName());
	}

	/**
	 * Clear the playlist
	 */
	public void clearPlaylist() {

		// check if there is even a playlist and if there are even entries
		if (this.playlistHandler != null && this.playlistHandler.getPlaylistElements() != null) {

			// clear local playlist
			this.playlistHandler.setPlaylistElements(null);
		}
		// clear network playlist if a connection is established
		if (this.sftpController != null && this.sftpController.isConnectionEstablished()) {

			// change the directory to the one with all the playlist JSON files
			this.sftpController.changeDirectory(this.getSftpDirectory());
			this.sftpController.changeDirectory(this.phpDirectoryName);

			// get all JSON files and remove the ones that are playlist files
			final String[] fileList = this.sftpController.listFiles(".json");
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].matches("\\d+.json")) {
					this.sftpController.removeFile(fileList[i]);
				}
			}

		}
	}

	/**
	 * Save the current playlist in a JSON file
	 * 
	 * @param filePath
	 *            (File | Destination of the file)
	 */
	public boolean savePlaylist(File filePath) {
		return FileReadWriteModule.writeTextFile(filePath,
				MusicVideoDataExportHandler.generateJsonContentPlaylist(this.playlistHandler.getPlaylistElements()));
	}

	/**
	 * Load a playlist
	 * 
	 * @param file
	 */
	public boolean loadPlaylist(File file) {
		return loadPlaylistString(FileReadWriteModule.readTextFile(file)[0]);

	}

	private boolean loadPlaylistString(String stringWithPlaylistData) {

		// check if the content is not null
		if (stringWithPlaylistData != null) {

			// load JSON data from given text
			final JsonObject playlist = JsonModule.loadJsonFromString(stringWithPlaylistData);

			// check if the JSON data exists
			if (playlist != null) {

				// load all the playlist entries
				final JsonArray playlistContent = playlist.getJsonArray("playlist");

				// check if the JSON holds content
				if (playlistContent != null) {

					clearPlaylist();

					// for every playlist entry
					for (JsonValue jsonObject : playlistContent) {

						// check if the saved paths is in the music video list
						final String pathToFile = jsonObject.asJsonObject().getString("file-path");

						// check if the path exists and isn't null
						if (pathToFile != null && Paths.get(pathToFile).toFile().exists()) {

							// get the index of the file in the music video list
							final int indexInList = inMusicVideoPlaylist(Paths.get(pathToFile));

							// if a index was found
							if (indexInList != -1) {

								// get the content of the playlist entry
								final String author = jsonObject.asJsonObject().getString("author");
								final String comment = jsonObject.asJsonObject().getString("comment");
								final long unixTime = Long.parseLong(jsonObject.asJsonObject().get("time").toString());
								final boolean createdLocally = jsonObject.asJsonObject().getBoolean("created-locally");

								MusicVideoPlaylistElement element = new MusicVideoPlaylistElement(unixTime, 
										indexInList + 1, musicVideoList[indexInList],
										author, comment, createdLocally, 0, null);
								// then add the music video to the playlist
								playlistHandler.loadMusicVideoToPlaylist(element);
							}
						}
					}
					System.out.println("Playlist was loaded");
					return true;
				}
			}
		}
		System.err.println("Playlist could not be loaded!");
		return false;

	}

	/**
	 * Search if a given path points to a file from the music video list
	 * 
	 * @param searchPath
	 *            (Path to a MusicVideo file)
	 * @return -1 if not found else a index of the MusicVideoFile (Integer)
	 */
	public int inMusicVideoPlaylist(Path searchPath) {

		// if there is a music video list
		if (this.musicVideoList != null && searchPath != null) {
			try {
				// check now for every element in the list if it points to the same File
				for (int i = 0; i < this.musicVideoList.length; i++) {
					// and check for every element if the path points to the same file
					if (Files.isSameFile(this.musicVideoList[i].getPath(), searchPath)) {
						// return the found file
						return i;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return -1;

	}

	/**
	 * Search the music video list for a MusicVideo object
	 * 
	 * @param pathOfMusicVideo
	 *            (Path to a music video file)
	 * @return MusicVideo object if found or null (MusicVideo)
	 */
	public MusicVideo getMusicVideoOfPlaylistItem(Path pathOfMusicVideo) {

		// if there is a music video list
		if (this.musicVideoList != null && pathOfMusicVideo != null) {
			try {
				// iterate through all elements
				for (int i = 0; i < this.musicVideoList.length; i++) {
					// and check for every element if the path points to the same file
					if (Files.isSameFile(this.musicVideoList[i].getPath(), pathOfMusicVideo)) {
						// return the found file
						return this.musicVideoList[i];
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Remove all ignored files from the ignored files list
	 */
	public void clearIgnoredFilesList() {

		// if the list is not already empty
		if (this.programDataHandler.getIgnoredFiles() != null) {

			// clear the ignored files list
			this.programDataHandler.setIgnoredFiles(null);

			updateMusicVideoList();
		}
	}

	public void resetSftp() {
		if (sftpConnectionEstablished()) {
			this.sftpController.disconnectSFTP();
		}
		this.programDataHandler.setIpAddressSftp(null);
		this.programDataHandler.setUserNameSftp(null);
		this.programDataHandler.setWorkingDirectorySftp(null);

	}

	/**
	 * Main method to transfer HTML documents (collections) directly to a server
	 * into a specific directory (the main directory)
	 * 
	 * @param type
	 *            (HtmlGenerator | which kind of html content should be transfered)
	 */
	public void transferHtml(HtmlContentGenerator generator) {

		// check if there even is a connection
		if (sftpConnectionEstablished()) {

			// change into default directory from login
			this.sftpController.changeDirectory(this.programDataHandler.getWorkingDirectorySftp());

			// remove top files
			this.sftpController.removeFile("index.html");
			this.sftpController.removeFile("index.php");

			// create a directory for the icons and change into it
			this.sftpController.makeDirectory("favicons");
			this.sftpController.changeDirectory("favicons");

			// copy all PNG icons
			for (int i = 0; i < this.faviconSizes.length; i++) {
				final String nameEnd = this.faviconSizes[i] + "x" + this.faviconSizes[i] + ".png";
				this.sftpController.transferFile(
						ClassResourceReaderModule.getInputStream("images/favicons/favicon-" + nameEnd),
						"favicon-" + nameEnd);
			}

			// copy the vector graphic icon
			this.sftpController.transferFile(
					ClassResourceReaderModule.getInputStream("images/favicons/favicon.svg"), "favicon.svg");

			// open the "Windows" graphic icon
			this.sftpController.transferFile(ClassResourceReaderModule.getInputStream("images/favicons/icon.ico"),
					"icon.ico");

			// change into default directory from login
			this.sftpController.changeDirectory("..");
			
			Object[][] musicVideoTable = musicVideoListToTable();
			
			String htmlContent = generator.generateHtml(this.phpDirectoryName, musicVideoTable, columnNames);
			
			if (!generator.isTypeParty()) {
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(htmlContent),	"index.html");
			} else {
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(htmlContent),	"list.html");
				
				boolean withVotes = ((HtmlContentPartyGenerator)generator).getWithVotes();
				
				HtmlPartyGenerator partyGenerator = new HtmlPartyGenerator();
				String indexContent = partyGenerator.generateHtmlPartyPlaylist(null, true, withVotes);
				String index2Content = partyGenerator.generateHtmlPartyPlaylist(phpDirectoryName, false, withVotes);
				// playlist view PHP list
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(indexContent),	"index.php");
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(index2Content), "index2.php");

				// create a directory for the PHP documents and change into it
				this.sftpController.makeDirectory(this.phpDirectoryName);
				this.sftpController.changeDirectory(this.phpDirectoryName);

				// PHP music video submit form
				String partyForm = partyGenerator.generateHtmlPartyForm();
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(partyForm), "form.php");

				// PHP process document to add a submit to the playlist
				String partyProcess = partyGenerator.generateHtmlPartyProcess();
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(partyProcess),	"process.php");

				String partyView = partyGenerator.generateHtmlPartyView(withVotes);
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(partyView), "live.php");
				
				String partyVote = partyGenerator.generateHtmlPartyVote();
				// PHP process document to add a submit to the playlist
				this.sftpController.transferFile(FileReadWriteModule.stringToInputStream(partyVote), "vote.php");
			}
		}
	}

	/**
	 * Reset plalyist voting
	 */
	public void resetVotingSftp() {

		// check if a connection was established
		if (sftpConnectionEstablished()) {

			// go into the default directory
			this.sftpController.changeDirectory(this.programDataHandler.getWorkingDirectorySftp());

			// then check if a directory named php exists
			boolean existingPhpDirectory = false;
			for (String file : this.sftpController.listFiles()) {
				if (file.equals(this.phpDirectoryName)) {
					System.out.println("Found php directory");
					existingPhpDirectory = true;
				}
			}

			// if yes
			if (existingPhpDirectory) {

				// go into it
				this.sftpController.changeDirectory(this.phpDirectoryName);

				this.sftpController.removeFile(this.ipAddressFileName);

				sftpRetrievePlaylist();

				this.playlistHandler.resetPlaylistVoting();

				loadPlaylistString(MusicVideoDataExportHandler
						.generateJsonContentPlaylist(this.playlistHandler.getPlaylistElements()));

			}
		}

	}

	public void setVotes(int index, int voteNumber) {

		// check if a connection was established
		if (sftpConnectionEstablished()) {
			MusicVideoPlaylistElement editedPlaylistEntry = this.playlistHandler.setVotes(voteNumber, index);
			// remove the old playlist entry
			removeEntryFromPlaylist(index);

			// then upload the edited playlist entry
			uploadPlaylistEntry(editedPlaylistEntry);
		}
	}

	public boolean getPlaylistRemoveStartedVideo() {
		return this.programDataHandler.getRemoveStartedVideoFromPlayist();
	}

	public boolean setPlaylistRemoveStartedVideo(boolean newValue) {
		return this.programDataHandler.setRemoveStartedVideoFromPlayist(newValue);
	}

}
