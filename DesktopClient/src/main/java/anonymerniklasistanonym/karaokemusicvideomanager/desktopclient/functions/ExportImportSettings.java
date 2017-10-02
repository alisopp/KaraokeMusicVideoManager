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
			String content = createSettings(settingsData);

			if (FileReadWriteModule.writeTextFile(file, new String[] { content })) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public static String createSettings(ProgramData settingsData) {

		if (settingsData == null) {
			return null;
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

				if (settingsData.getAlwaysSaveSettings() != false) {
					mainJsonBuilder.add("always-save", Boolean.TRUE);
				}

				return JsonModule.dumpJsonObjectToString(mainJsonBuilder);

			} else {
				return null;
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
			return null;
		}

	}

	/**
	 * Read JSON data and extract all settings information.
	 * 
	 * @param file
	 *            (File | file with 'ProgramData')
	 * @return extracted settings (ProgramData)
	 */
	public static ProgramData readSettings(File file) {

		System.out.println("READ SETTINGS");

		if (file == null) {
			System.err.println("<< File is null!");
			return null;
		}

		try {

			String[] contentOfFile = FileReadWriteModule.readTextFile(file);

			if (contentOfFile == null) {

				return null;
			}

			// read settings to one string
			StringBuilder strBuilder = new StringBuilder();
			for (String line : contentOfFile)
				strBuilder.append(line);

			// convert string to a JSON object
			JsonObject jsonObject = JsonModule.loadJsonFromString(strBuilder.toString());

			// create an 'empty' settings data file
			ProgramData settingsData = new ProgramData();

			// -> (try to) get path list
			JsonArray keyValuePathList = (JsonArray) JsonModule.getValue(jsonObject, "path-list");

			if (keyValuePathList != null) {

				Path[] newPathList = new Path[keyValuePathList.size()];

				for (int i = 0; i < keyValuePathList.size(); i++) {

					newPathList[i] = Paths.get(keyValuePathList.getString(i));
				}

				settingsData.setPathList(newPathList);

			} else {
				System.err.println(" << No saved paths");
			}

			// -> (try to) get SFTP connection properties
			JsonObject keyValueSftpLogin = (JsonObject) JsonModule.getValue(jsonObject, "sftp-login");

			if (keyValueSftpLogin != null) {

				try {
					String usernameValue = JsonModule.getValue(keyValueSftpLogin, "username").toString();
					settingsData.setUsernameSftp(usernameValue);
				} catch (Exception e) {
					System.out.println("No Sftp username");
				}

				try {
					String ipAddressValue = JsonModule.getValue(keyValueSftpLogin, "ip-address").toString();
					settingsData.setIpAddressSftp(ipAddressValue);
				} catch (Exception e) {
					System.out.println("No Sftp Ip address");
				}

				try {
					String workingDirectoryValue = JsonModule.getValue(keyValueSftpLogin, "directory").toString();
					settingsData.setWorkingDirectorySftp(workingDirectoryValue);
				} catch (Exception e) {
					System.out.println("No Sftp Ip address");
				}
			} else {
				System.out.println(" << No SFTP login data");
			}

			// -> (try to) get accepted file types list
			JsonArray keyValueFileTypes = (JsonArray) JsonModule.getValue(jsonObject, "file-types");

			if (keyValueFileTypes != null) {

				String[] newAcceptedFileTypes = new String[keyValueFileTypes.size()];

				for (int i = 0; i < keyValueFileTypes.size(); i++) {

					newAcceptedFileTypes[i] = keyValueFileTypes.getString(i);
				}

				settingsData.setAcceptedFileTypes(newAcceptedFileTypes);
			} else {
				System.err.println(" << No accepted file types");
			}

			// -> (try to) get if always the changes should be saved
			boolean keyValueAlwaysSave = JsonModule.getValueBoolean(jsonObject, "always-save");

			if (keyValueAlwaysSave != false) {
				settingsData.setAlwaysSaveSettings(true);
			} else {
				System.err.println(" << No always save setting");
			}

			return settingsData;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean compareSettings(File oldSettingsFile, File newSettingsFile) {

		String[] oldFile = FileReadWriteModule.readTextFile(oldSettingsFile);
		String[] newFile = FileReadWriteModule.readTextFile(newSettingsFile);

		if (oldFile != null && newFile != null) {
			return JsonModule.compareJsonStrings(oldFile.toString(), newFile.toString());
		} else {
			return false;
		}

	}

	public static boolean compareSettingsFileToCurrent(File oldSettingsFile, ProgramData newSettingsFile) {

		String oldFile = FileReadWriteModule.readTextFile(oldSettingsFile)[0];
		String newFile = createSettings(newSettingsFile);

		if (oldFile != null && newFile != null) {
			System.out.println("old file:");
			System.out.println(oldFile);
			System.out.println("new file:");
			System.out.println(newFile);
			return JsonModule.compareJsonStrings(oldFile, newFile);
		} else {
			return false;
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
