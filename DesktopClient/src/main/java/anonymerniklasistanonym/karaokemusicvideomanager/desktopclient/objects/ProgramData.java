package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * All the settings data of the current running program
 * 
 * @author AnonymerNiklasistanonym
 * @version 1.1 (beta)
 *
 */
public final class ProgramData {

	/**
	 * All the paths to music video files
	 */
	private Path[] pathList;

	/**
	 * Language of the program
	 */
	private Locale language;

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
	 * SFTP - Working directory
	 */
	private Boolean alwaysSaveSettings;

	/**
	 * Files that should be ignored
	 */
	private File[] ignoredFilesList;

	/**
	 * Constructor [empty]
	 */
	public ProgramData() {
		reset();
	}

	/**
	 * Get all paths of MusicVideo source folders
	 * 
	 * @return pathList (Path[])
	 */
	public Path[] getPathList() {
		return pathList;
	}

	/**
	 * Get all paths of MusicVideo source folders
	 * 
	 * @return pathList (Path[])
	 */
	public void reset() {
		pathList = null;
		language = null;
		acceptedFileTypes = new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" };
		userNameSftp = null;
		ipAddressSftp = null;
		workingDirectorySftp = null;
		alwaysSaveSettings = false;
		ignoredFilesList = null;
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
			this.pathList = uniqueAddresses.toArray(new Path[0]);

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
		Path[] oldPlaylist = this.pathList;
		Path[] newPathList = new Path[] { newPath };

		// if there is no old playlist set it to the new array - else concat both
		if (oldPlaylist == null) {
			setPathList(newPathList);
		} else {
			setPathList(Stream.concat(Arrays.stream(oldPlaylist), Arrays.stream(newPathList)).toArray(Path[]::new));
		}
		return true;
	}

	/**
	 * Get the language of the program
	 * 
	 * @return language (Locale)
	 */
	public Locale getLanguage() {
		return this.language;
	}

	public void setLanguage(Locale language) {
		this.language = language;
	}

	public String[] getAcceptedFileTypes() {
		return this.acceptedFileTypes;
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
			this.acceptedFileTypes = uniqueFileTypes.toArray(new String[0]);

		} else {
			System.out.println("<< accepted file types was empty!");
		}

	}

	public String getUsernameSftp() {
		return this.userNameSftp;
	}

	public void setUsernameSftp(String usernameSftp) {
		this.userNameSftp = usernameSftp;
	}

	public String getIpAddressSftp() {
		return this.ipAddressSftp;
	}

	public void setIpAddressSftp(String ipAddressSftp) {
		this.ipAddressSftp = ipAddressSftp;
	}

	public String getWorkingDirectorySftp() {
		return this.workingDirectorySftp;
	}

	public void setWorkingDirectorySftp(String workingDirectorySftp) {
		this.workingDirectorySftp = workingDirectorySftp;
	}

	public boolean getAlwaysSaveSettings() {
		return alwaysSaveSettings;
	}

	public boolean setAlwaysSaveSettings(boolean alwaysSaveSettings) {
		return this.alwaysSaveSettings = alwaysSaveSettings;
	}

	public File[] getIgnoredFiles() {
		return this.ignoredFilesList;
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
		File[] oldIgnoreFileList = this.ignoredFilesList;
		File[] newIgnoreFileList = new File[] { newFileToIgnore };

		// if there is no old ignore files list set it to the new array - else connect
		// them
		if (oldIgnoreFileList == null) {
			setIgnoredFiles(newIgnoreFileList);
		} else {
			setIgnoredFiles(Stream.concat(Arrays.stream(oldIgnoreFileList), Arrays.stream(newIgnoreFileList))
					.toArray(File[]::new));
		}
		return true;
	}

	/**
	 * Reset the ignored files list
	 */
	public void resetIgnoredFilesList() {
		this.ignoredFilesList = null;
	}

}
