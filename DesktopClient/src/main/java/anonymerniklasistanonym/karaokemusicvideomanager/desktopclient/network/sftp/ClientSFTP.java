package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.network.sftp;

import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ClientSFTP {

	private String userName;
	private String userPassword;

	private String serverAddress;
	private String serverFilePath;

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;

	private boolean connectionEstablished = false;

	public ClientSFTP(String userName, String userPassword, String serverAddress, String serverFilePath) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.serverAddress = serverAddress;
		this.serverFilePath = serverFilePath;
	}

	public void connectSFTP() {
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

		} catch (Exception ex) {
			ex.printStackTrace();
			this.connectionEstablished = false;

			try {
				disconnectSFTP();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnectSFTP() {
		if (this.channelSftp != null)
			this.channelSftp.disconnect();
		if (this.channel != null)
			this.channel.disconnect();
		if (this.session != null)
			this.session.disconnect();
	}

	public void listFiles() {

		if (connectionEstablished) {

			try {

				this.channelSftp.cd(this.serverFilePath); // Change Directory on SFTP Server

				@SuppressWarnings("unchecked")
				Vector<LsEntry> filelist = this.channelSftp.ls(this.serverFilePath);
				for (int i = 0; i < filelist.size(); i++) {
					System.out.println(filelist.get(i).toString());
				}

				// get only the file names
				// https://stackoverflow.com/a/38319814/7827128
				@SuppressWarnings("unchecked")
				Vector<ChannelSftp.LsEntry> list = this.channelSftp.ls("*.txt");
				for (ChannelSftp.LsEntry entry : list) {
					System.out.println(entry.getFilename());
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("You need first to establish a connection!");
		}

	}

	public void addFile() {

	}

	public void removeFile() {

	}

	public void transferFile() {

	}

}
