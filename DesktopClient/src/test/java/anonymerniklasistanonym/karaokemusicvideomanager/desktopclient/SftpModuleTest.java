package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.SftpModule;

public class SftpModuleTest {

	public static void main(String[] args) {
		SftpModule test = new SftpModule("username", "1234", "192.168.0.192");

		test.connectSFTP();

		long millis = System.currentTimeMillis() % 1000;

		System.out.println(Arrays.toString(test.listFiles()));

		test.changeDirectory("/home/pi");

		System.out.println(Arrays.toString(test.listFiles(".rb")));

		test.retrieveFile("Test.rb", test.listFiles(".rb")[0]);

		test.transferFile(Paths.get("c:\\Video\\aha"));

		test.removeFile("aha/a/myfile.txt");
		test.removeEmptyDirectory("aha/a");

		long millis2 = System.currentTimeMillis() % 1000;
		System.out.println((millis2 - millis) + "ms");

		test.disconnectSFTP();

		File a = new File("Test2.rb");

		System.out.println(Arrays.toString(FileReadWriteModule.readTextFile(a)));
	}

}
