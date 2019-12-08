package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;

/**
 * Handler for all the settings data of the current running program
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public final class ProgramDataHandler {

	/**
	 * All the paths to music video files
	 */
	private Path[] pathList;

	/**
	 * Accepted file types
	 */
	private String[] acceptedFileTypes;

	/**
	 * SFTP - Login account user name
	 */
	private String userNameSftp;

	/**
	 * SFTP - IP address of server
	 */
	private String ipAddressSftp;

	/**
	 * SFTP - Working directory
	 */
	private String workingDirectorySftp;

	/**
	 * Always save settings on close
	 */
	private Boolean alwaysSaveSettings;

	/**
	 * Files that should be ignored
	 */
	private File[] ignoredFilesList;

	/**
	 * Remove a started video from the playlist
	 */
	private boolean removeStartedVideoFromPlayist;
	
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
	 * Constructor [empty]
	 */
	public ProgramDataHandler() {
		this.pathList = null;
		this.acceptedFileTypes = new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" };
		this.userNameSftp = null;
		this.ipAddressSftp = null;
		this.workingDirectorySftp = null;
		this.alwaysSaveSettings = false;
		this.ignoredFilesList = null;
		this.removeStartedVideoFromPlayist = false;
		this.settingsFile = new File("settings.json");
		this.windowsSettingsFileDirectory = new File(System.getProperty("user.home") + "/KaraokeMusicVideoManager");
		this.windowsSettingsFile = new File(
				System.getProperty("user.home") + "/KaraokeMusicVideoManager/" + this.settingsFile);
	}

	/**
	 * Get all paths of MusicVideo source folders
	 * 
	 * @return pathList (Path[])
	 */
	public Path[] getPathList() {
		return this.pathList;
	}

	/**
	 * Set path list but also clear the list so that no doubled values are in it
	 * 
	 * @param pathList
	 *            (Path[])
	 */
	public void setPathList(Path[] pathList) {

		System.out.println(">> Set path list:");

		if (pathList != null && pathList.length != 0) {

			// create ArrayList to contain all non-repeated paths
			ArrayList<Path> uniqueAddresses = new ArrayList<Path>();

			// cycle through the entire array
			for (Path containedPath : pathList) {
				// check if the address is already contained in the ArrayList
				if (!uniqueAddresses.contains(containedPath)) {
					// check if the address exists
					if (containedPath.toFile().exists()) {
						uniqueAddresses.add(containedPath); // add it
						System.out.println("+ Added " + containedPath);
					} else {
						System.err.println("- Path does not exist: " + containedPath);
					}

				} else {
					System.err.println("- Found duplicate: " + containedPath);
				}
			}
			
			this.pathList = uniqueAddresses.toArray(new Path[0]);
			
			if (this.pathList != null) {
				Arrays.sort(pathList);
			}

		} else {
			System.out.println("<< Path list was empty!");

			this.pathList = null;
		}
	}

	/**
	 * Add a path to the path list
	 * 
	 * @param newPath
	 *            (Path)
	 */
	public boolean addPathToPathList(Path directoryPath) {
		
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
		
		Path newPath = directoryPath.toAbsolutePath().normalize();

		// check if path is null
		if (newPath == null) {
			System.out.println(">> Path not added because it's null!");
			return false;
		}

		// get old playlist and new element as an array
		Path[] oldPlaylist = getPathList();
		Path[] newPathList = new Path[] { newPath };

		// if there is no old playlist set it to the new array - else concat both
		if (oldPlaylist == null) {
			setPathList(newPathList);
		} else {
			setPathList(
					Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPathList)).toArray(Path[]::new));
		}
		return true;
	}

	/**
	 * Set the accepted file types list
	 * 
	 * @param acceptedFileTypes
	 *            (String[])
	 */
	public void setAcceptedFileTypes(String[] acceptedFileTypes) {

		System.out.println(">> Set accepted file types:");

		if (acceptedFileTypes != null && acceptedFileTypes.length != 0) {

			// create ArrayList to contain all non-repeated supported file types
			ArrayList<String> uniqueFileTypes = new ArrayList<String>();

			// cycle through the entire array
			for (String containedFileType : acceptedFileTypes) {
				// check if the file type is already contained in the ArrayList
				if (!uniqueFileTypes.contains(containedFileType)) {
					// add it
					uniqueFileTypes.add(containedFileType);
					System.out.println("+ Added " + containedFileType);
				} else {
					System.err.println("- Found duplicate: " + containedFileType);
				}
			}
			// sort the ArrayList
			Collections.sort(uniqueFileTypes);
			
			this.acceptedFileTypes = uniqueFileTypes.toArray(new String[0]);
		} else {
			System.out.println("<< accepted file types was empty!");
		}

	}

	/**
	 * Get the ignored files list
	 * 
	 * @return ignored files list (File[])
	 */
	public File[] getIgnoredFiles() {
		return ignoredFilesList;
	}

	/**
	 * Set the ignored files list
	 * 
	 * @param filesToIgnore
	 *            (File[])
	 */
	public void setIgnoredFiles(File[] filesToIgnore) {

		System.out.println(">> Set ignored files:");

		if (filesToIgnore != null && filesToIgnore.length != 0) {

			// create ArrayList to contain all non-repeated supported file types
			ArrayList<File> uniqueIgnoredFiles = new ArrayList<File>();

			// cycle through the entire array
			for (File fileToIgnore : filesToIgnore) {
				// check if the file even exists
				if (fileToIgnore.exists() && fileToIgnore.isFile()) {
					// check if the file type is already contained in the ArrayList
					if (!uniqueIgnoredFiles.contains(fileToIgnore)) {
						// add it
						uniqueIgnoredFiles.add(fileToIgnore);
						System.out.println("+ Added " + fileToIgnore);
					} else {
						System.err.println("- Found duplicate: " + fileToIgnore);
					}
				} else {
					System.err.println("- File does not exist or is no file: " + fileToIgnore);
				}

			}

			Collections.sort(uniqueIgnoredFiles);
			this.ignoredFilesList = uniqueIgnoredFiles.toArray(new File[0]);

		} else {
			System.out.println("<< ignored files list was empty!");
			this.ignoredFilesList = null;
		}
	}

	/**
	 * Add a path to the path list
	 * 
	 * @param newPath
	 *            (Path)
	 */
	public boolean addFileToIgnoredFilesList(Path directoryPath) {
		
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
		
		File newFileToIgnore = directoryPath.toAbsolutePath().toFile();

		// check if file is null
		if (newFileToIgnore == null) {
			System.err.println(">> File not added because it's null!");
			return false;
		}
		
		// check if file exists
		if (!newFileToIgnore.exists() || newFileToIgnore.isDirectory()) {
			System.err.println(">> File not added because it doesn't exist or is a directory!");
			return false;
		}

		// get old ignore files list and new element as an array
		File[] oldIgnoreFileList = this.ignoredFilesList;
		File[] newIgnoreFileList = new File[] { newFileToIgnore };

		// if there is no old ignore files list set it to the new array - else connect
		// them
		if (oldIgnoreFileList == null) {
			this.ignoredFilesList = newIgnoreFileList;
		} else {
			this.ignoredFilesList = Stream.concat(Arrays.stream(oldIgnoreFileList), Arrays.stream(newIgnoreFileList)).toArray(File[]::new);
		}
		return true;
	}

	/**
	 * Create a (JSON) String where all current settings are contained
	 * 
	 * @return current settings (String)
	 */
	public String createSettings() {

		try {

			if (getPathList() != null) {
				JsonObjectBuilder mainJsonBuilder = Json.createObjectBuilder();

				JsonArrayBuilder pathListArray = Json.createArrayBuilder();
				for (Path line : getPathList()) {
					pathListArray.add(line.toString());
				}
				mainJsonBuilder.add("path-list", pathListArray);

				if (getUserNameSftp() != null && getIpAddressSftp() != null) {
					JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
					sftpLogin.add("username", getUserNameSftp());
					sftpLogin.add("ip-address", getIpAddressSftp());
					if (getWorkingDirectorySftp() != null) {
						sftpLogin.add("directory", getWorkingDirectorySftp());
					}
					mainJsonBuilder.add("sftp-login", sftpLogin);
				}

				if (getAcceptedFileTypes() != null) {
					JsonArrayBuilder acceptedFileTypesArray = Json.createArrayBuilder();
					for (String fileType : getAcceptedFileTypes()) {
						acceptedFileTypesArray.add(fileType);
					}
					mainJsonBuilder.add("file-types", acceptedFileTypesArray);
				}

				if (getIgnoredFilesList() != null) {
					JsonArrayBuilder ignoredFileList = Json.createArrayBuilder();
					for (File ignoredFile : getIgnoredFilesList()) {
						ignoredFileList.add(ignoredFile.getAbsolutePath());
					}
					mainJsonBuilder.add("ignored-files", ignoredFileList);
				}

				if (getAlwaysSaveSettings() != false) {
					mainJsonBuilder.add("always-save", Boolean.TRUE);
				}

				if (getRemoveStartedVideoFromPlayist() != false) {
					mainJsonBuilder.add("remove-started-video-from-playlist", Boolean.TRUE);
				}

				return JsonModule.toString(mainJsonBuilder);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Compare current settings to a settings file
	 * 
	 * @param oldSettingsFile
	 *            (File | "Old" settings in a external file)
	 * @return false if different, true if the same (b
	 */
	public boolean compareSettingsFileToCurrent(File oldSettingsFile) {

		String oldFile = FileReadWriteModule.readTextFile(oldSettingsFile)[0];
		String newFile = createSettings();

		if (oldFile != null && newFile != null) {

			System.out.println("old file:");
			System.out.println(oldFile);
			System.out.println("new file:");
			System.out.println(newFile);

			return JsonModule.compareJsonStrings(oldFile, newFile);
		} else {
			return false;
		}

	}

	/**
	 * Remove a source folder path from the path list
	 * 
	 * @param sourceFolderToRemove
	 */
	public boolean removeFromPathList(Path sourceFolderToRemove) {

		// check if given path isn't null
		if (sourceFolderToRemove == null) {
			System.err.println("Path coudn't be removed because it is null!");
			return false;
		}

		System.out.println(">> Remove path from the path list: " + sourceFolderToRemove);

		// create a array list from all current paths
		ArrayList<Path> newPathList = new ArrayList<>(Arrays.asList(getPathList()));

		// try to remove the given path -> if not do not change the current path list
		if (newPathList.remove(sourceFolderToRemove)) {

			System.out.println("<< Path was succsessfully removed");
			setPathList(newPathList.toArray(new Path[0]));
			return true;

		} else {

			System.err.println("<< Path was not removed!");
			return false;
		}

	}

	/**
	 * Remove a file from the ignored files list
	 * 
	 * @param fileToRemove
	 *            (Path)
	 */
	public boolean removeFromIgnoredFilesList(Path fileToRemove) {

		// check if given path isn't null
		if (fileToRemove == null) {
			System.err.println("Path coudn't be removed because it is null!");
			return false;
		}

		System.out.println(">> Remove path from the ignored files list: " + fileToRemove);

		// create a array list from all current paths
		ArrayList<File> newIgnoredFilesList = new ArrayList<>(Arrays.asList(getIgnoredFiles()));

		// try to remove the given path -> if not do not change the current path list
		if (newIgnoredFilesList.remove(fileToRemove.toFile())) {

			System.out.println("<< Path was succsessfully removed");
			setIgnoredFiles(newIgnoredFilesList.toArray(new File[0]));
			return true;

		} else {

			System.err.println("<< Path was not removed!");
			return false;
		}

	}

	/**
	 * @return the acceptedFileTypes (String[])
	 */
	public String[] getAcceptedFileTypes() {
		return acceptedFileTypes;
	}

	/**
	 * @return the userNameSftp (String)
	 */
	public String getUserNameSftp() {
		return userNameSftp;
	}

	/**
	 * @param userNameSftp
	 *            (String | The user name for the SFTP access to the server)
	 */
	public void setUserNameSftp(String userNameSftp) {
		this.userNameSftp = userNameSftp;
	}

	/**
	 * @return the ipAddressSftp (String)
	 */
	public String getIpAddressSftp() {
		return ipAddressSftp;
	}

	/**
	 * @param ipAddressSftp
	 *            (String | Set the ipAddressSftp)
	 */
	public void setIpAddressSftp(String ipAddressSftp) {
		this.ipAddressSftp = ipAddressSftp;
	}

	/**
	 * @return the workingDirectorySftp (String)
	 */
	public String getWorkingDirectorySftp() {
		return workingDirectorySftp;
	}

	/**
	 * @param workingDirectorySftp
	 *            (String | Set the servers distributing web directory)
	 */
	public void setWorkingDirectorySftp(String workingDirectorySftp) {
		this.workingDirectorySftp = workingDirectorySftp;
	}

	/**
	 * @return the alwaysSaveSettings (boolean)
	 */
	public boolean getAlwaysSaveSettings() {
		return this.alwaysSaveSettings;
	}

	/**
	 * @param alwaysSaveSettings
	 *            (boolean | Set always save)
	 * @return
	 */
	public boolean setAlwaysSaveSettings(boolean alwaysSaveSettings) {
		return this.alwaysSaveSettings = alwaysSaveSettings;
	}

	/**
	 * @return the ignoredFilesList (File[])
	 */
	public File[] getIgnoredFilesList() {
		return this.ignoredFilesList;
	}

	public boolean getRemoveStartedVideoFromPlayist() {
		return this.removeStartedVideoFromPlayist;
	}

	public boolean setRemoveStartedVideoFromPlayist(boolean newValue) {
		return this.removeStartedVideoFromPlayist = newValue;
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
	 * Returns FALSE if the new settings data from the file is different to the
	 * current settings data. If they aren't the same TRUE will be returned.
	 * 
	 * @param settingsFilePathNew
	 *            (File | File that contains settingsData in JSON format)
	 * @return theyAreTheSame (Boolean)
	 */
	public boolean compareSettings() {

		if (windowsSettingsFileExists()) {
			return compareSettingsFileToCurrent(this.windowsSettingsFile);
		} else {
			return compareSettingsFileToCurrent(this.settingsFile);
		}
	}

	/**
	 * Save the current Settings to a file
	 * 
	 * @param settingsFilePath
	 *            (File | File for the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean saveSettings(File file) {
		if (file == null) {
			return false;
		}

		try {
			String content = createSettings();

			if (FileReadWriteModule.writeTextFile(file, content)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Load the current Settings from a file and set them as new settings
	 * 
	 * @param file
	 *            (File | File with the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public void loadSettings(File file) {
		System.out.println("READ SETTINGS");

		if (file == null) {
			System.err.println("<< File is null!");
			return;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readTextFile(file);

			if (contentOfFile == null) {
				return;
			}

			// read settings to one string
			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			// convert string to a JSON object
			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			// -> (try to) get path list
			JsonArray keyValuePathList = (JsonArray) JsonModule.getValue(jsonObject, "path-list");

			if (keyValuePathList != null) {

				Path[] newPathList = new Path[keyValuePathList.size()];

				for (int i = 0; i < keyValuePathList.size(); i++) {

					newPathList[i] = Paths.get(keyValuePathList.getString(i));
				}

				this.pathList = newPathList;
				
			} else {
				System.err.println(" << No saved paths");
			}

			// -> (try to) get SFTP connection properties
			JsonObject keyValueSftpLogin = (JsonObject) JsonModule.getValue(jsonObject, "sftp-login");

			if (keyValueSftpLogin != null) {

				try {
					String usernameValue = JsonModule.getValueString(keyValueSftpLogin, "username").toString();
					this.userNameSftp = usernameValue;
				} catch (Exception e) {
					System.out.println("No Sftp username");
				}

				try {
					String ipAddressValue = JsonModule.getValueString(keyValueSftpLogin, "ip-address").toString();
					this.ipAddressSftp = ipAddressValue;
				} catch (Exception e) {
					System.out.println("No Sftp Ip address");
				}

				try {
					String workingDirectoryValue = JsonModule.getValueString(keyValueSftpLogin, "directory").toString();
					this.workingDirectorySftp = workingDirectoryValue;
				} catch (Exception e) {
					System.out.println("No Sftp Ip address");
				}
			} else {
				System.out.println(" << No SFTP login data");
			}

			// -> (try to) get accepted file types list
			JsonArray keyValueFileTypes = (JsonArray) JsonModule.getValue(jsonObject, "file-types");

			if (keyValueFileTypes != null) {

				String[] newAcceptedFileTypes = new String[keyValueFileTypes.size()];

				for (int i = 0; i < keyValueFileTypes.size(); i++) {

					newAcceptedFileTypes[i] = keyValueFileTypes.getString(i);
				}

				this.acceptedFileTypes = newAcceptedFileTypes;
			} else {
				System.err.println(" << No accepted file types");
			}

			// -> (try to) get the ignored file list
			JsonArray keyValueIgnoredFiles = (JsonArray) JsonModule.getValue(jsonObject, "ignored-files");

			if (keyValueIgnoredFiles != null) {

				File[] newIgnoredFileTypes = new File[keyValueIgnoredFiles.size()];

				for (int i = 0; i < keyValueIgnoredFiles.size(); i++) {

					newIgnoredFileTypes[i] = Paths.get(keyValueIgnoredFiles.getString(i)).toFile();
				}

				this.ignoredFilesList = newIgnoredFileTypes;
			} else {
				System.err.println(" << No accepted file types");
			}

			// -> (try to) get if always the changes should be saved
			boolean keyValueAlwaysSave = JsonModule.getValueBoolean(jsonObject, "always-save");

			if (keyValueAlwaysSave != false) {
				this.alwaysSaveSettings = true;
			} else {
				System.err.println(" << No always save setting");
			}

			// -> (try to) get if always a started video should be removed from the playlist
			boolean keyValueAlwaysRemoveFromPlaylist = JsonModule.getValueBoolean(jsonObject,
					"remove-started-video-from-playlist");

			if (keyValueAlwaysRemoveFromPlaylist != false) {
				this.removeStartedVideoFromPlayist = true;
			} else {
				System.err.println(" << No always remove started video from playlist setting");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove all ignored files from the ignored files list
	 */
	public void clearIgnoredFilesList() {

		// if the list is not already empty
		if (this.ignoredFilesList != null) {

			// clear the ignored files list
			this.ignoredFilesList = null;
		}
	}
	
	public void resetSftpSettings() {
		this.ipAddressSftp = null;
		this.userNameSftp = null;
		this.workingDirectorySftp = null;
	}

	public void saveSftpLogin(String ipAddressSftp, String workingDirectorySftp, String usernameSftp) {
		this.ipAddressSftp = ipAddressSftp;
		this.workingDirectorySftp = workingDirectorySftp;
		this.userNameSftp = usernameSftp;
	}
	
	/**
	 * Rescan and get all wrong formatted files
	 * 
	 * @return
	 */
	public Path[] getWrongFormattedFiles() {

		// check if there even is a path list
		if (this.pathList == null) {
			System.err.println("There are no paths!");
			return null;
		}

		final ArrayList<Path> newMusicVideoList = new ArrayList<Path>();

		final Path[] filesInDirectory = this.pathList;

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
	 * Scan a directory for files (paths)
	 * 
	 * @param path
	 *            (Path | Path of directory)
	 * @return list of files in this directory or null (ArrayList<Path>)
	 */
	public Path[] scanDirectoryForFiles(Path path) {

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
	 * Check if a file is not correct formatted and so *no* music video file
	 * 
	 * @param filePath
	 *            (Path | Path of file)
	 * @return true if it is not a music video file (boolean)
	 */
	private boolean isFileMusicVideoButWrong(Path filePath) {

		if (this.ignoredFilesList != null) {
			final File musicVideoFile = filePath.toFile();
			for (File ignoredFilePath : this.ignoredFilesList) {
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

					for (int i = 0; i < this.acceptedFileTypes.length; i++) {
						if (this.acceptedFileTypes[i].equalsIgnoreCase(fileType)) {
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
}
