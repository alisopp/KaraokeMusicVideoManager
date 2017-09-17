package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonArray;
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

			if (settingsData.getPathList() != null) {
				JsonObjectBuilder mainJsonBuilder = Json.createObjectBuilder();

				JsonArrayBuilder pathListArray = Json.createArrayBuilder();
				for (Path line : settingsData.getPathList()) {
					pathListArray.add(line.toString());
				}
				mainJsonBuilder.add("path-list", pathListArray);

				if (settingsData.getUsernameSftp() != null && settingsData.getIpAddressSftp() != null) {
					JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
					sftpLogin.add("username", settingsData.getUsernameSftp());
					sftpLogin.add("ip-address", settingsData.getIpAddressSftp());
					if (settingsData.getWorkingDirectorySftp() != null) {
						sftpLogin.add("directory", settingsData.getWorkingDirectorySftp());
					}
					mainJsonBuilder.add("sftp-login", sftpLogin);
				}

				if (settingsData.getAcceptedFileTypes() != null) {
					JsonArrayBuilder acceptedFileTypesArray = Json.createArrayBuilder();
					for (String fileType : settingsData.getAcceptedFileTypes()) {
						acceptedFileTypesArray.add(fileType);
					}
					mainJsonBuilder.add("file-types", acceptedFileTypesArray);
				}

				String content = JsonModule.dumpStringToJson(mainJsonBuilder);

				if (FileReadWriteModule.writeFile(file, new String[] { content })) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

			// JsonArrayBuilder pathListArray = Json.createArrayBuilder();
			// pathListArray.add("String 1");
			// pathListArray.add("String 2");
			// pathListArray.add("String 3");
			// pathListArray.add("String 4");
			// mainJsonBuilder.add("path-list", pathListArray);

			// JsonObjectBuilder sftpLogin = Json.createObjectBuilder();
			// sftpLogin.add("username", "pi");
			// sftpLogin.add("address", "192.168.0.192");
			// mainJsonBuilder.add("sftp-login", sftpLogin);

			// mainJsonBuilder.add("name", "Falco#*äüö");
			// mainJsonBuilder.add("age", BigDecimal.valueOf(3));
			// mainJsonBuilder.add("biteable", Boolean.FALSE);

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

		System.out.println("second");

		if (file == null) {
			System.err.println("File is null!");
			return null;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readFile(file);

			// if (!Arrays.equals(settingsData.getAcceptedFileTypes(),
			// new String[] { "avi", "mp4", "mkv", "wmv", "mov", "mpg", "mpeg" })) {
			// }

			if (contentOfFile == null) {
				System.err.println("File could not be read!");
				return null;
			}

			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			ProgramData settingsData = new ProgramData();

			JsonArray keyValue = (JsonArray) JsonModule.getValue(jsonObject, "path-list");

			if (keyValue != null) {

				Path[] newPathList = new Path[keyValue.size()];

				System.out.println(keyValue);

				for (int i = 0; i < keyValue.size(); i++) {

					newPathList[i] = Paths.get(keyValue.getString(i));
				}

				settingsData.setPathList(newPathList);
			}

			// System.out.println(JsonModule.getValue(jsonObject, "sftp-login"));
			// System.out.println(JsonModule.getValue(jsonObject, "sftp"));

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
