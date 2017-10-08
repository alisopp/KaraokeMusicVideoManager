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
	private String userName = null;
	/**
	 * SFTP password for user name for login
	 */
	private String userPassword = null;

	/**
	 * IP address of SFTP server for login
	 */
	private String serverAddress = null;

	/**
	 * The current working directory of the SFTP server
	 */
	private String currentWorkingDirectory = null;

	/**
	 * Login/out to/of STFP server class
	 */
	private Session session = null;
	/**
	 * Connect with session class to SFTP server
	 */
	private Channel channel = null;
	/**
	 * SFTP connection channel
	 */
	private ChannelSftp channelSftp = null;

	/**
	 * True if a connection is currently established
	 */
	private boolean connectionEstablished = false;

	public boolean isConnectionEstablished() {
		return connectionEstablished;
	}

	public SftpHandler() {

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
	 *            (working directory | String)
	 */
	public SftpHandler(String userName, String userPassword, String serverAddress, String serverFilePath) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.serverAddress = serverAddress;
		this.currentWorkingDirectory = serverFilePath;
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
	 */
	public SftpHandler(String userName, String userPassword, String serverAddress) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.serverAddress = serverAddress;
		this.currentWorkingDirectory = null;
	}

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

		} catch (Exception ex) {
			ex.printStackTrace();
			this.connectionEstablished = false;

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
			this.connectionEstablished = false;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Change the working directory
	 * 
	 * @param path
	 *            (String ["/home", "/home/user", ...])
	 */
	public void changeDirectory(String path) {

		System.out.print(">> Change Directory to " + path);

		if (!connectionEstablished) {
			System.err.println(" << You need first to establish a connection!");
			return;
		}

		if (path.equals("")) {
			System.err.println(" << Directory doesn't need to be changed!");
			return;
		}

		try {

			this.channelSftp.cd(path);
			this.currentWorkingDirectory = this.channelSftp.pwd();
			System.out.println(" << Directory changed to " + path);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Create a new directory in the working directory
	 * 
	 * @param directoryName
	 *            (String)
	 */
	public void makeDirectory(String directoryName) {

		System.out.print(">> Create directory " + this.currentWorkingDirectory + "/" + directoryName);

		if (!connectionEstablished) {
			System.err.println(" << You need first to establish a connection!");
			return;
		}

		if (directoryName.equals("")) {
			System.err.println(" << Directory has no name!");
			return;
		}

		try {
			channelSftp.lstat(directoryName);
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

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
			return null;
		}

		try {

			// this.channelSftp.cd(this.serverFilePath); // Change Directory on SFTP Server

			// String list for entries:
			List<String> fileEntries = new LinkedList<String>();

			// get only the file names
			// https://stackoverflow.com/a/38319814/7827128
			@SuppressWarnings("unchecked")
			Vector<LsEntry> list = this.channelSftp.ls("*" + filetype);
			for (LsEntry entry : list) {
				fileEntries.add(entry.getFilename());
			}

			return fileEntries.toArray(new String[0]);

		} catch (Exception ex) {
			ex.printStackTrace();
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
	 * Retrieves remote file and writes it to a local file
	 * 
	 * @param localFile
	 *            (String | path to local new file)
	 * @param remoteFile
	 *            (String | path to remote to copy file)
	 * @return everythingWorked (Boolean)
	 */
	public boolean retrieveFile(String localFile, String remoteFile) {

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
			return false;
		}

		if ((localFile == null || remoteFile == null) || (localFile.isEmpty() || remoteFile.isEmpty())) {
			System.err.println("File could not be written because of it's null or empty!");
			return false;
		}

		System.out.print(">> Retrieve file " + remoteFile + " to " + localFile);

		try {

			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(remoteFile));
			File newFile = new File(localFile);
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();

			System.out.println(" << File succssessfully retrieved.");

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * Removes a remote file
	 * 
	 * @param pathOfRemoteFile
	 *            (String)
	 * @return deletionSuccsessfull (Boolean)
	 */
	public boolean removeFile(String pathOfRemoteFile) {

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
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

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" << Unknown Error.");
			return false;
		}

	}

	/**
	 * Removes an empty remote directory
	 * 
	 * @param pathOfRemoteDirectory
	 *            (String)
	 * @return deletionSuccsessfull (Boolean)
	 */
	public boolean removeEmptyDirectory(String pathOfRemoteDirectory) {

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
			return false;
		}

		if ((pathOfRemoteDirectory == null || pathOfRemoteDirectory.isEmpty())) {
			System.err.println("Directory could not be written because of it's null or empty!");
			return false;
		}

		System.out.print(">> Remove directory " + pathOfRemoteDirectory);

		try {
			channelSftp.rmdir(pathOfRemoteDirectory);

			System.out.println(" << Directory succssessfully removed.");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" << Unknown Error.");
			return false;
		}

	}

	/**
	 * Transfer a local file to the current working directory
	 * 
	 * @param pathLocalFile
	 *            (String)
	 * @param folderPath
	 *            (String)
	 */
	private void transferFile(String pathLocalFile, String folderPath) {

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
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
	public void transferFile(InputStream fileInputStream, String filename) {

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
			return;
		}

		System.out.println(">>> Transfer " + filename + " to " + this.currentWorkingDirectory);

		if (filename != null && fileInputStream != null) {

			try {

				this.channelSftp.put(fileInputStream, filename);

				System.out.println("<<< file succsessfully transferred");

			} catch (Exception ex) {
				System.err.println("Exception found while transfer the response.");
				ex.printStackTrace();
			} finally {

			}
		}
	}

	/**
	 * Transfer files with paths to working directory
	 * 
	 * @param pathLocalFile
	 */
	public void transferFile(Path pathLocalFile) {
		transferFile(pathLocalFile.toString(), "");
	}

	/**
	 * Transfer files with strings to working directory
	 * 
	 * @param pathLocalFile
	 */
	public void transferFile(String pathLocalFile) {
		transferFile(pathLocalFile.toString(), "");
	}

	/**
	 * Transfer whole folder via SFTP to the current working directory
	 */
	private void transferFolder(File directory, String directoryString) {

		for (File fileInDirectory : directory.listFiles()) {

			transferFile(fileInDirectory.getAbsolutePath(), directoryString);

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

		if (!connectionEstablished) {
			System.out.println("You need first to establish a connection!");
			return null;
		}

		if (remoteFile == null || remoteFile.isEmpty()) {
			System.err.println("File could not be written because of it's null or empty!");
			return null;
		}

		System.out.print(">> Retrieve file " + remoteFile);

		try {

			java.util.Scanner s = new java.util.Scanner(channelSftp.get(remoteFile)).useDelimiter("\\A");
			String content = s.hasNext() ? s.next() : "";
			s.close();
			System.out.println("Content");
			return content;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
}
