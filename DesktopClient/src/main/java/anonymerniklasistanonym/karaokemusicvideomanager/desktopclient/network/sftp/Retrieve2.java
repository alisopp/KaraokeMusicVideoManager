package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.network.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class Retrieve2 {

	public static void main(String[] args) {
		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession("pi", "192.168.0.192", 22);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword("RasPerryPie100%Delicious");
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			sftpChannel.get("/home/pi/myfile.txt", "localfile.txt");
			sftpChannel.exit();
			session.disconnect();

			File f = new File("localfile.txt");

			Scanner in;
			try {
				in = new Scanner(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));

				StringBuilder sb = new StringBuilder();
				while (in.hasNext()) {
					sb.append(in.next());
				}
				in.close();
				System.out.println(sb.toString());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("cool");
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}

}
