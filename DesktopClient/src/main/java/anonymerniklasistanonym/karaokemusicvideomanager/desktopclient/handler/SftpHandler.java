package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Class to create s SFTP connection and transfer files back and for
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class SftpHandler {

	/**
	 * SFTP user name for login
	 */
	private String userName;
	/**
	 * SFTP password for user name for login
	 */
	private String userPassword;

	/**
	 * IP address of SFTP server for login
	 */
	private String serverAddress;

	/**
	 * The current working directory of the SFTP server
	 */
	private String currentWorkingDirectory;

	/**
	 * Login/out to/of STFP server class
	 */
	private Session session;
	/**
	 * Connect with session class to SFTP server
	 */
	private Channel channel;
	/**
	 * SFTP connection channel
	 */
	private ChannelSftp channelSftp;

	/**
	 * True if a connection is currently established
	 */
	private boolean connectionEstablished;

	/**
	 * Empty constructor
	 */
	public SftpHandler() {
		this.userName = null;
		this.userPassword = null;
		this.serverAddress = null;
		this.currentWorkingDirectory = null;
		this.session = null;
		this.channel = null;
		this.channelSftp = null;
		this.connectionEstablished = false;
	}

	/**
	 * Setup SFTP source without starting a connection
	 * 
	 * @param userName
	 *            (user name | String)
	 * @param userPassword
	 *            (password for user name | String)
	 * @param serverAddress
	 *            (IP address of server | String)
	 * @param serverFilePath
	 *            (Directory of index.html file | String)
	 */
	public void connect(String userName, String userPassword, String serverAddress, String serverFilePath) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.serverAddress = serverAddress;
		this.currentWorkingDirectory = serverFilePath;

		connectSFTP();
	}

	/**
	 * Connect to SFTP source
	 */
	public void connectSFTP() {

		// always disconnect SFTP source before connecting
		disconnectSFTP();

		try {
			JSch jsch = new JSch();

			this.session = jsch.getSession(this.userName, this.serverAddress, 22);
			this.session.setPassword(this.userPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			this.session.setConfig(config);
			this.session.connect(); // Create SFTP Session
			this.channel = session.openChannel("sftp"); // Open SFTP Channel
			this.channel.connect();
			this.channelSftp = (ChannelSftp) channel;

			this.connectionEstablished = true;

			if (this.currentWorkingDirectory != null) {
				changeDirectory(currentWorkingDirectory);
			}

		} catch (JSchException e) {
			e.printStackTrace();
			this.connectionEstablished = false;
			System.err.println(" << User/Host name is invalid or session could not be opened!");

			disconnectSFTP();

		} catch (Exception ex) {
			ex.printStackTrace();
			this.connectionEstablished = false;
			System.err.println(" << Unknown error!");

			disconnectSFTP();
		}
	}

	/**
	 * Disconnect SFTP source
	 */
	public void disconnectSFTP() {

		try {

			if (this.channelSftp != null)
				this.channelSftp.disconnect();
			if (this.channel != null)
				this.channel.disconnect();
			if (this.session != null)
				this.session.disconnect();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.connectionEstablished = false;
		}

	}

	/**
	 * Change the working directory
	 * 
	 * @param path
	 *            (String ["/home", "/home/user", ...])
	 * @return Program now in new directory? (Boolean)
	 */
	public boolean changeDirectory(String path) {

		if (!this.connectionEstablished) {
			System.err.println(" << You need first to establish a connection!");
			return false;
		}

		if (path == null || path.isEmpty()) {
			System.err.println("Directory not changed because argument null or empty");
			return false;
		}

		System.out.print(">> Change Directory to " + path);

		if (path.equals("")) {
			System.err.println(" << Directory doesn't need to be changed!");
			return true;
		}

		try {

			this.channelSftp.cd(path);
			this.currentWorkingDirectory = this.channelSftp.pwd();
			System.out.println(" << Directory changed to " + path);
			return true;

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return false;
		}

	}

	/**
	 * Create a new directory in the working directory
	 * 
	 * @param directoryName
	 *            (String)
	 */
	public void makeDirectory(String directoryName) {

		if (!this.connectionEstablished) {
			System.err.println(" << You need first to establish a connection!");
			return;
		}

		if (directoryName == null || directoryName.isEmpty()) {
			System.err.println("Directory not created because argument null or empty");
			return;
		}

		if (directoryName.equals("")) {
			System.err.println(" << Directory has no name!");
			return;
		}

		System.out.print(">> Create directory " + this.currentWorkingDirectory + "/" + directoryName);

		try {
			this.channelSftp.lstat(directoryName);
			System.out.println(" << Directory already exists!");
			return;

		} catch (SftpException e) {
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				try {
					this.channelSftp.mkdir(directoryName);
					System.out.println(" << Directory was created.");
				} catch (SftpException e1) {
					System.err.println(" << Unknown error!");
					e1.printStackTrace();
				}

			} else {
				e.printStackTrace();
			}
		}

	}

	/**
	 * List files main method
	 * 
	 * @param filetype
	 * @return list of requested files (String[])
	 */
	private String[] listFilesMain(String filetype) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return null;
		}

		if (filetype == null) {
			System.err.println("Filetype can't be null!");
			return null;
		}

		System.out.println(">> Retrieve file list of all following files: *." + filetype);

		try {

			// String list for entries:
			List<String> fileEntries = new LinkedList<String>();

			// get only the file names
			// https://stackoverflow.com/a/38319814/7827128
			@SuppressWarnings("unchecked")
			Vector<LsEntry> list = this.channelSftp.ls("*" + filetype);
			for (LsEntry entry : list) {
				fileEntries.add(entry.getFilename());
			}

			System.out.println(" << File list succssessfully retrieved.");
			return fileEntries.toArray(new String[0]);

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return null;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return null;
		}

	}

	/**
	 * List files in the console
	 * 
	 * @return list of requested files (String[])
	 */
	public String[] listFiles() {
		return listFilesMain("");
	}

	/**
	 * List only files of a specific file type
	 * 
	 * @param fileType
	 *            (String | file type [".txt", ".py", ...])
	 * @return list of requested files (String[])
	 */
	public String[] listFiles(String fileType) {
		return listFilesMain(fileType);
	}

	/**
	 * Removes a remote file
	 * 
	 * @param pathOfRemoteFile
	 *            (String)
	 * @return deletionSuccsessfull (Boolean)
	 */
	public boolean removeFile(String pathOfRemoteFile) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return false;
		}

		if ((pathOfRemoteFile == null || pathOfRemoteFile.isEmpty())) {
			System.err.println("File could not be written because of it's null or empty!");
			return false;
		}

		System.out.print(">> Remove file " + pathOfRemoteFile);

		try {

			channelSftp.rm(pathOfRemoteFile);
			System.out.println(" << File succssessfully removed.");
			return true;

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return false;
		}

	}

	/**
	 * Transfer a local file to the current working directory
	 * 
	 * @param pathLocalFile
	 *            (String | Local file path)
	 * @param folderPath
	 *            (String | File/Directory path on SFTP server)
	 */
	private void transferFile(String pathLocalFile, String folderPath) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return;
		}

		if (pathLocalFile == null || folderPath == null) {
			System.err.println("File transfer not possible because either location or source is null!");
			return;
		}

		System.out.println(">>> Transfer " + pathLocalFile + " to " + this.currentWorkingDirectory + folderPath);

		String oldWorkingDirectory = this.currentWorkingDirectory;

		try {

			File file = new File(pathLocalFile);

			if (!file.exists()) {
				System.err.println("File does not exist!");
				return;
			}

			changeDirectory(folderPath);

			if (file.isFile()) {

				this.channelSftp.put(new FileInputStream(file), file.getName());

				System.out.println("<<< " + file.getName() + " transferred to " + this.currentWorkingDirectory);

			} else if (file.isDirectory()) {

				changeDirectory(oldWorkingDirectory);

				System.out.println(
						">>>> Transfer the directory " + file.getName() + " to " + this.currentWorkingDirectory);

				makeDirectory(folderPath + file.getName() + "/");
				transferFolder(file, folderPath + file.getName() + "/");

				System.out
						.println("<<<< Directory " + file.getName() + " transfered to " + this.currentWorkingDirectory);

			}

		} catch (FileNotFoundException ex) {
			System.err.println("File not found on local file system!");
		} catch (Exception ex) {
			System.err.println("Exception found while transfer the response.");
			ex.printStackTrace();
		} finally {

			changeDirectory(oldWorkingDirectory);

		}
	}

	/**
	 * Transfer files with paths to working directory
	 * 
	 * @param pathLocalFile
	 */
	public boolean transferFile(InputStream fileInputStream, String filename) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return false;
		}

		if (filename == null || filename.isEmpty()) {
			System.err.println("File could not be transfered because of it's null or empty!");
			return false;
		}

		if (fileInputStream == null) {
			System.err.println("File could not be transfered because of its InputStream is null or empty!");
			return false;
		}

		System.out.println(">> Transfer " + filename + " to " + this.currentWorkingDirectory);

		try {

			this.channelSftp.put(fileInputStream, filename);
			System.out.println(" << File succsessfully transferred");
			return true;

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return false;
		}

	}

	/**
	 * Transfer whole folder via SFTP to the current working directory
	 */
	private void transferFolder(File directory, String directoryString) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return;
		}

		if (directory == null || directoryString == null) {
			System.err.println("File transfer not possible because either location or source is null!");
			return;
		}

		if (directory.isFile()) {
			System.err.println("File transfer not possible because directory is not a directory!");
			return;
		}

		try {

			// list all files of the directory
			final File[] fileList = directory.listFiles();

			// if the list is not null transfer all files in this directory
			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					transferFile(fileList[i].getAbsolutePath(), directoryString);
				}
			}

		} catch (SecurityException e) {
			e.printStackTrace();
			System.err.println(" << SFTP connection security error!");
			return;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return;
		}

	}

	/**
	 * Retrieves remote file and writes it to a local file
	 * 
	 * @param localFile
	 *            (String | path to local new file)
	 * @param remoteFile
	 *            (String | path to remote to copy file)
	 * @return everythingWorked (Boolean)
	 */
	public String retrieveFileInputStreamString(String remoteFile) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return null;
		}

		if (remoteFile == null || remoteFile.isEmpty()) {
			System.err.println("File could not be written because of it's null or empty!");
			return null;
		}

		System.out.print(">> Retrieve file " + remoteFile);

		try {

			// get file text content
			@SuppressWarnings("resource")
			java.util.Scanner s = new java.util.Scanner(this.channelSftp.get(remoteFile)).useDelimiter("\\A");
			String content = s.hasNext() ? s.next() : "";
			s.close();
			System.out.println("Content");
			return content;

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return null;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return null;
		}

	}

	/**
	 * Return if a connection is established
	 * 
	 * @return true if this is the case else false
	 */
	public boolean isConnectionEstablished() {
		return this.connectionEstablished;
	}

	/**
	 * Change the permissions of a file
	 * 
	 * @param pathOfRemoteFile
	 *            (String | File that should have the new permissions)
	 * @param permissions
	 *            (Integer | Octal representation of the new permissions)
	 * @return deletionSuccsessfull (Boolean)
	 */
	public boolean changePermissions(String pathOfRemoteDirectory, int permissions) {

		if (!this.connectionEstablished) {
			System.err.println("You need first to establish a connection!");
			return false;
		}

		if (pathOfRemoteDirectory == null || pathOfRemoteDirectory.isEmpty()) {
			System.err.println("Permissions could not be changed because of the file is null or empty!");
			return false;
		}

		if (String.valueOf(permissions).length() != 3) {
			System.err.println("The permissions need to have 3 digits! (777, 775, ...)");
			return false;
		}

		System.out.print(">> Change the permissions of the file " + pathOfRemoteDirectory + " (" + permissions + ")");

		try {

			// parse the given permission number to a "normal" format on UNIX
			channelSftp.chmod(Integer.parseInt(Integer.toString(permissions), 8), pathOfRemoteDirectory);
			System.out.println(" << File has new permissions.");
			return true;

		} catch (SftpException ex) {
			ex.printStackTrace();
			System.err.println(" << SFTP connection error!");
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" << Unknown error!");
			return false;
		}

	}

}
