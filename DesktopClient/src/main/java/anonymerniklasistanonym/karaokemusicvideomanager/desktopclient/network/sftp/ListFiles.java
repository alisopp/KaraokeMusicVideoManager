package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.network.sftp;

import java.util.Vector;

/**
 * Created on Jan 4, 2017 Copyright(c) https://kodehelp.com All Rights Reserved.
 */

// https://kodehelp.com/how-to-delete-file-on-remote-server-using-sftp-in-java/

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author https://kodehelp.com
 *
 */
public class ListFiles {

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

			@SuppressWarnings("unchecked")
			Vector<LsEntry> filelist = channelSftp.ls(SFTPWORKINGDIR);
			for (int i = 0; i < filelist.size(); i++) {
				System.out.println(filelist.get(i).toString());
			}

			// get only the file names
			// https://stackoverflow.com/a/38319814/7827128
			@SuppressWarnings("unchecked")
			Vector<ChannelSftp.LsEntry> list = channelSftp.ls("*.txt");
			for (ChannelSftp.LsEntry entry : list) {
				System.out.println(entry.getFilename());
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