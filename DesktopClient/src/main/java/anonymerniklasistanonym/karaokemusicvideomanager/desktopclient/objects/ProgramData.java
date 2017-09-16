package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.nio.file.Path;
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
	private String[] acceptedFileTypes = null;

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
	 * Get all paths of MusicVideo source folders
	 * 
	 * @return pathList (Path[])
	 */
	public Path[] getPathList() {
		return pathList;
	}

	public void setPathList(Path[] pathList) {
		this.pathList = pathList;
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
