package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.network.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class TransferFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Path path = Paths.get("c:\\Video\\myfile.txt");

		TransferFile aha = new TransferFile();
		aha.send(path.toString());
	}

	public void send(String fileName) {
		String SFTPHOST = "192.168.0.192";
		int SFTPPORT = 22;
		String SFTPUSER = "pi";
		String SFTPPASS = "RasPerryPie100%Delicious";

		String SFTPWORKINGDIR = "/home/pi";

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			System.out.println("hi");

			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			System.out.println("hi");
			File f = new File(fileName);

			System.out.println("hi");

			// new InputStreamReader(new FileInputStream(f),
			// StandardCharsets.UTF_8)

			Scanner in = new Scanner(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder();
			while (in.hasNext()) {
				sb.append(in.next());
			}
			in.close();
			System.out.println(sb.toString());

			channelSftp.put(new FileInputStream(f), f.getName());
			// log.info("File transfered successfully to host.");
		} catch (Exception ex) {
			System.out.println("Exception found while tranfer the response.");
		} finally {

			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

}
