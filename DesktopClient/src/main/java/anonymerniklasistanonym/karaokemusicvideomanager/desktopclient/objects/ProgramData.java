package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.nio.file.Path;
import java.util.ArrayList;
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
	private Path[] pathList = null;

	/**
	 * Language of the program
	 */
	private Locale language = null;

	/**
	 * Accepted file types
	 */
	private String[] acceptedFileTypes = new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" };

	/**
	 * SFTP - Login account user name
	 */
	private String userNameSftp = null;

	/**
	 * SFTP - IP address of server
	 */
	private String ipAddressSftp = null;

	/**
	 * SFTP - Working directory
	 */
	private String workingDirectorySftp = null;

	/**
	 * Constructor [empty]
	 */
	public ProgramData() {

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
	 * Set path list but also clear the list so that no doubled values are in it
	 * 
	 * @param pathList
	 *            (Path[])
	 */
	public void setPathList(Path[] pathList) {

		System.out.println(">> Set path list:");

		ArrayList<Path> uniqueAddresses = new ArrayList<Path>(); // create arraylist to contain all non-repeated
																	// addresses
		for (Path containedPath : pathList) { // cycle through the entire array
			if (!uniqueAddresses.contains(containedPath)) { // check if the address already there

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
		this.acceptedFileTypes = acceptedFileTypes;
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

}
