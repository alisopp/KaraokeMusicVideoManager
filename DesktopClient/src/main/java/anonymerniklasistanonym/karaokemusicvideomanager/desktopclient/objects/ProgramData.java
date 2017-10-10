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
	 * Constructor [empty]
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
	 * @return the pathList
	 */
	public Path[] getPathList() {
		return pathList;
	}

	/**
	 * @param pathList
	 *            the pathList to set
	 */
	public void setPathList(Path[] pathList) {
		Arrays.sort(pathList);
		this.pathList = pathList;
	}

	/**
	 * @return the acceptedFileTypes
	 */
	public String[] getAcceptedFileTypes() {
		return acceptedFileTypes;
	}

	/**
	 * @param acceptedFileTypes
	 *            the acceptedFileTypes to set
	 */
	public void setAcceptedFileTypes(String[] acceptedFileTypes) {
		this.acceptedFileTypes = acceptedFileTypes;
	}

	/**
	 * @return the userNameSftp
	 */
	public String getUserNameSftp() {
		return userNameSftp;
	}

	/**
	 * @param userNameSftp
	 *            the userNameSftp to set
	 */
	public void setUserNameSftp(String userNameSftp) {
		this.userNameSftp = userNameSftp;
	}

	/**
	 * @return the ipAddressSftp
	 */
	public String getIpAddressSftp() {
		return ipAddressSftp;
	}

	/**
	 * @param ipAddressSftp
	 *            the ipAddressSftp to set
	 */
	public void setIpAddressSftp(String ipAddressSftp) {
		this.ipAddressSftp = ipAddressSftp;
	}

	/**
	 * @return the workingDirectorySftp
	 */
	public String getWorkingDirectorySftp() {
		return workingDirectorySftp;
	}

	/**
	 * @param workingDirectorySftp
	 *            the workingDirectorySftp to set
	 */
	public void setWorkingDirectorySftp(String workingDirectorySftp) {
		this.workingDirectorySftp = workingDirectorySftp;
	}

	/**
	 * @return the alwaysSaveSettings
	 */
	public Boolean getAlwaysSaveSettings() {
		return alwaysSaveSettings;
	}

	/**
	 * @param alwaysSaveSettings
	 *            the alwaysSaveSettings to set
	 * @return
	 */
	public boolean setAlwaysSaveSettings(Boolean alwaysSaveSettings) {
		return this.alwaysSaveSettings = alwaysSaveSettings;
	}

	/**
	 * @return the ignoredFilesList
	 */
	public File[] getIgnoredFilesList() {
		return ignoredFilesList;
	}

	/**
	 * @param ignoredFilesList
	 *            the ignoredFilesList to set
	 */
	public void setIgnoredFilesList(File[] ignoredFilesList) {
		this.ignoredFilesList = ignoredFilesList;
	}

}
