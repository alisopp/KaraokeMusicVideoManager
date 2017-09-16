package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

import java.io.File;
import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects.ProgramData;

public class ExportImportSettings {

	/**
	 * Write Json file with all the settings data
	 * 
	 * @param file
	 *            (File | File where the data will be written to)
	 * @param handler
	 *            (MusicVideoHandler | probably where all the settings will be
	 *            saved)
	 * @return writePrcoessSuccessful (true if successful)
	 */
	public static boolean writeSettings(File file, ProgramData settingsData) {

		if (file == null || settingsData == null) {
			return false;
		}

		try {
			JsonObjectBuilder mainJsonBuilder = Json.createObjectBuilder();

			JsonArrayBuilder pathListArray = Json.createArrayBuilder();
			pathListArray.add("String 1");
			pathListArray.add("String 2");
			pathListArray.add("String 3");
			pathListArray.add("String 4");

			mainJsonBuilder.add("path-list", pathListArray);

			JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
			sftpLogin.add("username", "pi");
			sftpLogin.add("address", "192.168.0.192");

			mainJsonBuilder.add("sftp-login", sftpLogin);

			mainJsonBuilder.add("name", "Falco#*äüö");
			mainJsonBuilder.add("age", BigDecimal.valueOf(3));
			mainJsonBuilder.add("biteable", Boolean.FALSE);

			String content = JsonModule.dumpStringToJson(mainJsonBuilder);

			if (FileReadWriteModule.writeFile(file, new String[] { content })) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Write Json file with all the settings data
	 * 
	 * @param file
	 *            (File | File where the data will be written to)
	 * @param handler
	 *            (MusicVideoHandler | probably where all the settings will be
	 *            saved)
	 * @return writePrcoessSuccessful (true if successful)
	 */

	/**
	 * 
	 * @param file
	 */
	public static ProgramData readSettings(File file) {

		if (file == null) {
			System.err.println("File is null!");
			return null;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readFile(file);

			if (contentOfFile == null) {
				System.err.println("File could not be read!");
				return null;
			}

			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			ProgramData settingsData = new ProgramData();

			System.out.println(JsonModule.getValue(jsonObject, "sftp-login"));
			System.out.println(JsonModule.getValue(jsonObject, "sftp"));

			return settingsData;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File a = new File("test.json");
		ProgramData b = new ProgramData();

		writeSettings(a, b);

		readSettings(a);

	}

}
