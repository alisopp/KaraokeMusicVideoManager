package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

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
	private File[] ignoredFileList;

	/**
	 * Constructor [empty]
	 */
	public ProgramData() {
		resetSettings();
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
	public void resetSettings() {
		pathList = null;
		language = null;
		acceptedFileTypes = new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" };
		userNameSftp = null;
		ipAddressSftp = null;
		workingDirectorySftp = null;
		alwaysSaveSettings = false;
		ignoredFileList = null;
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

	public void setAlwaysSaveSettings(boolean alwaysSaveSettings) {
		this.alwaysSaveSettings = alwaysSaveSettings;
	}

	public File[] getIgnoredFiles() {
		return this.ignoredFileList;
	}

	public void setIgnoredFiles(File[] filesToIgnore) {

		System.out.println(">> Set ignored files:");

		if (filesToIgnore != null && filesToIgnore.length != 0) {

			// create ArrayList to contain all non-repeated supported file types
			ArrayList<File> uniqueFileTypes = new ArrayList<File>();

			// cycle through the entire array
			for (File fileToIgnore : filesToIgnore) {
				// check if the file type is already contained in the ArrayList
				if (!uniqueFileTypes.contains(fileToIgnore)) {
					// add it
					uniqueFileTypes.add(fileToIgnore);
					System.out.println("+ Added " + fileToIgnore);
				} else {
					System.err.println("- Found duplicate: " + fileToIgnore);
				}
			}

			this.ignoredFileList = uniqueFileTypes.toArray(new File[0]);

		} else {
			System.out.println("<< ignored files list was empty!");
			this.ignoredFileList = null;
		}
	}

}
