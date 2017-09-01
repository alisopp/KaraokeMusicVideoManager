package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.network.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author https://kodehelp.com
 *
 */
public class DeleteFile {

	static Session session = null;
	static Channel channel = null;
	static ChannelSftp channelSftp = null;

	public static void main(String[] args) {

		String SFTPHOST = "192.168.0.192"; // SFTP Host Name or SFTP Host IP
											// Address
		int SFTPPORT = 22; // SFTP Port Number
		String SFTPUSER = "pi"; // User Name
		String SFTPPASS = "RasPerryPie100%Delicious"; // Password
		String SFTPWORKINGDIR = "/home/pi"; // Source Directory on SFTP server
											// in which the file is located on
											// remote server
		boolean deletedflag = false;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(); // Create SFTP Session
			channel = session.openChannel("sftp"); // Open SFTP Channel
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR); // Change Directory on SFTP Server
			System.out.println(channelSftp.ls(SFTPWORKINGDIR));
			channelSftp.rm("myfile.txt"); // This method removes the file
											// from remote server
			deletedflag = true;
			if (deletedflag) {
				System.out.println("File deleted successfully.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (channelSftp != null)
				channelSftp.disconnect();
			if (channel != null)
				channel.disconnect();
			if (session != null)
				session.disconnect();

		}

	}

}