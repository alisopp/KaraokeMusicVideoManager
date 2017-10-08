package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.File;
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
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.ProgramData;

/**
 * All the settings data of the current running program
 * 
 * @author AnonymerNiklasistanonym
 * @version 1.1 (beta)
 *
 */
public final class ProgramDataHandler {

	/**
	 * Contains all the settings/program data
	 */
	private ProgramData settings;

	/**
	 * Constructor
	 */
	public ProgramDataHandler(String[] acceptedFileTypes) {
		this.settings = new ProgramData(acceptedFileTypes);
	}

	/**
	 * Constructor [empty]
	 */
	public ProgramDataHandler() {
		this.settings = new ProgramData(new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" });
	}

	/**
	 * Get all paths of MusicVideo source folders
	 * 
	 * @return pathList (Path[])
	 */
	public Path[] getPathList() {
		return this.settings.getPathList();
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
			Collections.sort(uniqueAddresses);
			this.settings.setPathList(uniqueAddresses.toArray(new Path[0]));

		} else {
			System.out.println("<< Path list was empty!");
		}
	}

	/**
	 * Add a path to the path list
	 * 
	 * @param newPath
	 *            (Path)
	 */
	public boolean addPathToPathList(Path newPath) {

		// check if path is null
		if (newPath == null) {
			System.out.println(">> Path not added because it's null!");
			return false;
		}

		// get old playlist and new element as an array
		Path[] oldPlaylist = this.settings.getPathList();
		Path[] newPathList = new Path[] { newPath };

		// if there is no old playlist set it to the new array - else concat both
		if (oldPlaylist == null) {
			this.settings.setPathList(newPathList);
		} else {
			this.settings.setPathList(
					Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPathList)).toArray(Path[]::new));
		}
		return true;
	}

	public String[] getAcceptedFileTypes() {
		return this.settings.getAcceptedFileTypes();
	}

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
			this.settings.setAcceptedFileTypes(uniqueFileTypes.toArray(new String[0]));

		} else {
			System.out.println("<< accepted file types was empty!");
		}

	}

	public String getUsernameSftp() {
		return this.settings.getUserNameSftp();
	}

	public void setUsernameSftp(String usernameSftp) {
		this.settings.setUserNameSftp(usernameSftp);
	}

	public String getIpAddressSftp() {
		return this.settings.getIpAddressSftp();
	}

	public void setIpAddressSftp(String ipAddressSftp) {
		this.settings.setIpAddressSftp(ipAddressSftp);
	}

	public String getWorkingDirectorySftp() {
		return this.settings.getWorkingDirectorySftp();
	}

	public void setWorkingDirectorySftp(String workingDirectorySftp) {
		this.settings.setWorkingDirectorySftp(workingDirectorySftp);
	}

	public boolean getAlwaysSaveSettings() {
		return this.settings.getAlwaysSaveSettings();
	}

	public boolean setAlwaysSaveSettings(boolean alwaysSaveSettings) {
		return this.settings.setAlwaysSaveSettings(alwaysSaveSettings);
	}

	public File[] getIgnoredFiles() {
		return this.settings.getIgnoredFilesList();
	}

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
			this.settings.setIgnoredFilesList(uniqueIgnoredFiles.toArray(new File[0]));

		} else {
			System.out.println("<< ignored files list was empty!");
			this.settings.setIgnoredFilesList(null);
		}
	}

	/**
	 * Add a path to the path list
	 * 
	 * @param newPath
	 *            (Path)
	 */
	public boolean addFileToIgnoredFilesList(File newFileToIgnore) {

		// check if file is null
		if (newFileToIgnore == null) {
			System.err.println(">> File not added because it's null!");
			return false;
		}

		// check if file is null
		if (!newFileToIgnore.exists() || newFileToIgnore.isDirectory()) {
			System.err.println(">> File not added because it doesn't exist or is a directory!");
			return false;
		}

		// get old ignore files list and new element as an array
		File[] oldIgnoreFileList = this.settings.getIgnoredFilesList();
		File[] newIgnoreFileList = new File[] { newFileToIgnore };

		// if there is no old ignore files list set it to the new array - else connect
		// them
		if (oldIgnoreFileList == null) {
			this.settings.setIgnoredFilesList(newIgnoreFileList);
		} else {
			this.settings.setIgnoredFilesList(Stream
					.concat(Arrays.stream(oldIgnoreFileList), Arrays.stream(newIgnoreFileList)).toArray(File[]::new));
		}
		return true;
	}

	/**
	 * Reset the ignored files list
	 */
	public void resetIgnoredFilesList() {
		this.settings.setIgnoredFilesList(null);
	}

	/**
	 * Write Json file with all the settings data
	 * 
	 * @param file
	 *            (File | File where the data will be written to)
	 * @param handler
	 *            (MusicVideoHandler | probably where all the settings will be
	 *            saved)
	 * @return writePrcoessSuccessful (true if successful)
	 */
	public boolean writeSettings(File file) {

		if (file == null) {
			return false;
		}

		try {
			String content = createSettings();

			if (FileReadWriteModule.writeTextFile(file, new String[] { content })) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public String createSettings() {

		try {

			if (this.settings.getPathList() != null) {
				JsonObjectBuilder mainJsonBuilder = Json.createObjectBuilder();

				JsonArrayBuilder pathListArray = Json.createArrayBuilder();
				for (Path line : this.settings.getPathList()) {
					pathListArray.add(line.toString());
				}
				mainJsonBuilder.add("path-list", pathListArray);

				if (this.settings.getUserNameSftp() != null && this.settings.getIpAddressSftp() != null) {
					JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
					sftpLogin.add("username", this.settings.getUserNameSftp());
					sftpLogin.add("ip-address", this.settings.getIpAddressSftp());
					if (this.settings.getWorkingDirectorySftp() != null) {
						sftpLogin.add("directory", this.settings.getWorkingDirectorySftp());
					}
					mainJsonBuilder.add("sftp-login", sftpLogin);
				}

				if (this.settings.getAcceptedFileTypes() != null) {
					JsonArrayBuilder acceptedFileTypesArray = Json.createArrayBuilder();
					for (String fileType : this.settings.getAcceptedFileTypes()) {
						acceptedFileTypesArray.add(fileType);
					}
					mainJsonBuilder.add("file-types", acceptedFileTypesArray);
				}

				if (this.settings.getIgnoredFilesList() != null) {
					JsonArrayBuilder ignoredFileList = Json.createArrayBuilder();
					for (File ignoredFile : this.settings.getIgnoredFilesList()) {
						ignoredFileList.add(ignoredFile.getAbsolutePath());
					}
					mainJsonBuilder.add("ignored-files", ignoredFileList);
				}

				if (this.settings.getAlwaysSaveSettings() != false) {
					mainJsonBuilder.add("always-save", Boolean.TRUE);
				}

				return JsonModule.dumpJsonObjectToString(mainJsonBuilder);

			} else {
				return null;
			}

			// JsonArrayBuilder pathListArray = Json.createArrayBuilder();
			// pathListArray.add("String 1");
			// pathListArray.add("String 2");
			// pathListArray.add("String 3");
			// pathListArray.add("String 4");
			// mainJsonBuilder.add("path-list", pathListArray);

			// JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
			// sftpLogin.add("username", "pi");
			// sftpLogin.add("address", "192.168.0.192");
			// mainJsonBuilder.add("sftp-login", sftpLogin);

			// mainJsonBuilder.add("name", "Falco#*äüö");
			// mainJsonBuilder.add("age", BigDecimal.valueOf(3));
			// mainJsonBuilder.add("biteable", Boolean.FALSE);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Read JSON data and extract all settings information.
	 * 
	 * @param file
	 *            (File | file with 'ProgramData')
	 * @return extracted settings (ProgramData)
	 */
	public ProgramDataHandler readSettings(File file) {

		System.out.println("READ SETTINGS");

		if (file == null) {
			System.err.println("<< File is null!");
			return null;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readTextFile(file);

			if (contentOfFile == null) {

				return null;
			}

			// read settings to one string
			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			// convert string to a JSON object
			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			// create an 'empty' settings data file
			ProgramDataHandler newSettingsData = new ProgramDataHandler();

			// -> (try to) get path list
			JsonArray keyValuePathList = (JsonArray) JsonModule.getValue(jsonObject, "path-list");

			if (keyValuePathList != null) {

				Path[] newPathList = new Path[keyValuePathList.size()];

				for (int i = 0; i < keyValuePathList.size(); i++) {

					newPathList[i] = Paths.get(keyValuePathList.getString(i));
				}

				newSettingsData.setPathList(newPathList);

			} else {
				System.err.println(" << No saved paths");
			}

			// -> (try to) get SFTP connection properties
			JsonObject keyValueSftpLogin = (JsonObject) JsonModule.getValue(jsonObject, "sftp-login");

			if (keyValueSftpLogin != null) {

				try {
					String usernameValue = JsonModule.getValueString(keyValueSftpLogin, "username").toString();
					newSettingsData.setUsernameSftp(usernameValue);
				} catch (Exception e) {
					System.out.println("No Sftp username");
				}

				try {
					String ipAddressValue = JsonModule.getValueString(keyValueSftpLogin, "ip-address").toString();
					newSettingsData.setIpAddressSftp(ipAddressValue);
				} catch (Exception e) {
					System.out.println("No Sftp Ip address");
				}

				try {
					String workingDirectoryValue = JsonModule.getValueString(keyValueSftpLogin, "directory").toString();
					newSettingsData.setWorkingDirectorySftp(workingDirectoryValue);
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

				newSettingsData.setAcceptedFileTypes(newAcceptedFileTypes);
			} else {
				System.err.println(" << No accepted file types");
			}

			// -> (try to) get the ignored file list
			JsonArray keyValueIgnoredFiles = (JsonArray) JsonModule.getValue(jsonObject, "ignored-files");

			if (keyValueIgnoredFiles != null) {

				File[] newAcceptedFileTypes = new File[keyValueIgnoredFiles.size()];

				for (int i = 0; i < keyValueIgnoredFiles.size(); i++) {

					newAcceptedFileTypes[i] = Paths.get(keyValueIgnoredFiles.getString(i)).toFile();
				}

				newSettingsData.setIgnoredFiles(newAcceptedFileTypes);
			} else {
				System.err.println(" << No accepted file types");
			}

			// -> (try to) get if always the changes should be saved
			boolean keyValueAlwaysSave = JsonModule.getValueBoolean(jsonObject, "always-save");

			if (keyValueAlwaysSave != false) {
				newSettingsData.setAlwaysSaveSettings(true);
			} else {
				System.err.println(" << No always save setting");
			}

			return newSettingsData;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

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

}
