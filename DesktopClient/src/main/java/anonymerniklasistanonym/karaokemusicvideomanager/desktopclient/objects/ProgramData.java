package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * All the settings data of the current running program
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class ProgramData {

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
	 * SFTP - Working directory
	 */
	private Boolean alwaysSaveSettings;

	/**
	 * Files that should be ignored
	 */
	private File[] ignoredFilesList;

	/**
	 * Constructor to create a new settings data object (ProgramData)
	 * 
	 * @param acceptedFileTypes
	 *            (String[] | File types that should be accepted)
	 */
	public ProgramData(String[] acceptedFileTypes) {
		this.pathList = null;
		this.acceptedFileTypes = acceptedFileTypes;
		this.userNameSftp = null;
		this.ipAddressSftp = null;
		this.workingDirectorySftp = null;
		this.alwaysSaveSettings = false;
		this.ignoredFilesList = null;
	}

	/**
	 * @return the pathList (Path[])
	 */
	public Path[] getPathList() {
		return pathList;
	}

	/**
	 * @param pathList
	 *            (Path[] | New path list to overwrite old list)
	 */
	public void setPathList(Path[] pathList) {

		// sort the array alphabetical before setting it if there is content
		if (pathList != null) {
			Arrays.sort(pathList);
		}

		this.pathList = pathList;

	}

	/**
	 * @return the acceptedFileTypes (String[])
	 */
	public String[] getAcceptedFileTypes() {
		return acceptedFileTypes;
	}

	/**
	 * @param acceptedFileTypes
	 *            (String[] | New accepted file types to overwrite old list)
	 */
	public void setAcceptedFileTypes(String[] acceptedFileTypes) {
		this.acceptedFileTypes = acceptedFileTypes;
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
		return alwaysSaveSettings;
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
		return ignoredFilesList;
	}

	/**
	 * @param ignoredFilesList
	 *            (File[] | the ignoredFilesList to set)
	 */
	public void setIgnoredFilesList(File[] ignoredFilesList) {
		this.ignoredFilesList = ignoredFilesList;
	}

}
