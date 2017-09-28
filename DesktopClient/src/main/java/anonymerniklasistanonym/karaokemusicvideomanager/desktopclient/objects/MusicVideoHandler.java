package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import javax.json.JsonObject;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportImportSettings;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportMusicVideoData;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.ClassResourceReaderModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.JsonModule;

public class MusicVideoHandler {

	/**
	 * All the important data about music video files and more is in here
	 */
	private ProgramData settingsData;

	public ProgramData getSettingsData() {
		return settingsData;
	}

	public boolean setSettingsData(ProgramData settingsData) {

		if (settingsData != null) {
			this.settingsData = settingsData;
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This ArrayList<MusicVideo> contains all the MusicVideo objects and each
	 * object is a music video file with information about title, artist and path of
	 * the music video files that were found in the source folders
	 */
	private MusicVideo[] musicVideoList;

	private final String[] columnNames = new String[] { "#", "Artist", "Title" };

	/**
	 * Constructor that creates an empty/default program data object
	 */
	public MusicVideoHandler() {
		settingsData = new ProgramData();
	}

	public void loadMusicVideoFiles() {

		if (settingsData == null) {
			System.err.println("No settings found!");
			return;
		}

		Path[] allFoldersWithMusicVideos = settingsData.getPathList();

		if (allFoldersWithMusicVideos == null || allFoldersWithMusicVideos.length == 0) {
			System.err.println("No path list found in settings!");
			return;
		}

		int percentCounter = 0;
		int precentToAdd = allFoldersWithMusicVideos.length / 100;

		for (Path directoryPath : settingsData.getPathList()) {

			addPathToPathList(directoryPath);

			System.out.println(percentCounter);
			percentCounter += precentToAdd;
		}

		percentCounter = 100;
		System.out.println(percentCounter);
	}

	/**
	 * Add a directory path to the pathList
	 * 
	 * @param directoryPath
	 *            (Path)
	 * @return true if everything worked else false (Boolean)
	 */
	public boolean addPathToPathList(Path directoryPath) {

		if (settingsData == null) {
			System.err.println("No settings found!");
			return false;
		}

		if (directoryPath == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		System.out.print(">> Add path " + directoryPath.toAbsolutePath() + " to path list.");

		if (!directoryPath.toFile().isDirectory()) {
			System.err.println(" << Path is no directory!");
			return false;
		}

		try {

			directoryPath = directoryPath.toAbsolutePath();

			Path[] oldPathList = this.settingsData.getPathList();

			if (oldPathList == null) {
				this.settingsData.setPathList(new Path[] { directoryPath });
			} else {

				for (Path containedPaths : oldPathList) {
					if (containedPaths.compareTo(directoryPath) == 0) {
						System.err.println(" << Path is already in the PathList!");
						return false;
					}
				}

				Path[] newPathList = Stream.concat(Arrays.stream(this.settingsData.getPathList()),
						Arrays.stream(new Path[] { directoryPath })).toArray(Path[]::new);
				this.settingsData.setPathList(newPathList);
			}

			System.out.println(" << Path added to path list.");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Print the music video list to the console in a table
	 */
	public void printMusicVideoList() {

		System.out.println(">> Print music video list:");

		if (this.musicVideoList == null) {
			System.err.println("<< Music video list doesn't exist!");
			return;
		}

		if (this.musicVideoList.length == 0) {
			System.err.println("<< Music video list is empty!");
			return;
		}

		MusicVideo[] musicVideoList = this.musicVideoList;

		int numberOfLinesNumber = String.valueOf(musicVideoList.length).length();
		int numberOfLinesTitle = 22;
		int numberOfLinesArtist = 15;
		int numberOfLinesPath = 80;

		String linesNumber = String.join("", Collections.nCopies(numberOfLinesNumber + 2, "-"));
		String linesTitle = String.join("", Collections.nCopies(numberOfLinesTitle + 2, "-"));
		String linesArtist = String.join("", Collections.nCopies(numberOfLinesArtist + 2, "-"));
		String linesPath = String.join("", Collections.nCopies(numberOfLinesPath + 2, "-"));

		String titleForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesTitle)));
		String artistForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesArtist)));
		String pathForm = String.join(".", Collections.nCopies(2, String.valueOf(numberOfLinesPath)));

		String tableDataFormat = "| %-" + numberOfLinesNumber + "d | %-" + titleForm + "s | %-" + artistForm + "s | %-"
				+ pathForm + "s | %n";
		String tableInfoFormat = "| %-" + numberOfLinesNumber + "s | %-" + titleForm + "s | %-" + artistForm + "s | %-"
				+ pathForm + "s | %n";

		String tableInfo1 = ("+" + linesNumber + "+" + linesTitle + "+" + linesArtist + "+" + linesPath + "+%n");

		int numberShowInfo = 100, numberShowInfoCount = 0;
		for (int i = 0; i < musicVideoList.length; i++) {

			if (i == numberShowInfoCount) {
				System.out.format(tableInfo1);
				System.out.format(tableInfoFormat, "#", "Title", "Artist", "Path");
				System.out.format(tableInfo1);
				numberShowInfoCount += numberShowInfo;
			}

			System.out.format(tableDataFormat, i + 1, musicVideoList[i].getTitle(), musicVideoList[i].getArtist(),
					musicVideoList[i].getPath());
		}
		System.out.format(tableInfo1);

	}

	/**
	 * Scan a directory after specific video files and map all music videos
	 * 
	 * @param path
	 *            (Path | path of the directory)
	 */
	public ArrayList<MusicVideo> scanDirectory(Path path) {

		if (path == null || !path.toFile().isDirectory()) {
			System.err.println("Path is null or no directory!");
			return null;
		}

		// get all files in the directory
		Collection<Path> filesInDirectory = new ArrayList<Path>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {

			System.out.print(">> Scan " + path + " for files");
			for (Path child : ds) {
				filesInDirectory.add(child);
			}
			System.out.println(" << Scan finished.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(" << Problem while searching for Files!");
			return null;
		}

		ArrayList<MusicVideo> musicvideosinFolder = new ArrayList<MusicVideo>();

		System.out.println(">> Finding MusicVideo files");

		for (Path filePath : filesInDirectory) {

			MusicVideo musicVideoFile = isFileMusicVideo(filePath);

			if (musicVideoFile != null) {
				musicvideosinFolder.add(musicVideoFile);
			}

		}

		// return sorted Array
		return musicvideosinFolder;

	}

	/**
	 * Rescan and sort all files in the saved directories in the musicVideoList
	 * 
	 * @return
	 */
	public void updateMusicVideoList() {

		if (this.settingsData.getPathList() == null) {
			System.err.println("There are no paths!");
			return;
		}

		ArrayList<MusicVideo> newMusicVideoList = new ArrayList<MusicVideo>();

		for (Path directory : this.settingsData.getPathList()) {
			newMusicVideoList.addAll(scanDirectory(directory));
		}

		MusicVideo[] newList = new MusicVideo[newMusicVideoList.size()];
		newList = newMusicVideoList.toArray(newList);

		// sort array over implemented comparator
		Arrays.sort(newList, new MusicVideo());

		this.musicVideoList = newList;

	}

	/**
	 * Open with the default desktop video player a file with the path of the
	 * MusicVideo object in the music video list
	 * 
	 * @param index
	 *            (index of the MusicVideo object in the music video list)
	 */
	public boolean openMusicVideo(int index) {

		if (!Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported - the program will not open videos on this computer!");
			return false;
		}

		if (this.musicVideoList == null) {
			System.err.println("The music videos list is null!");
			return false;
		}

		if (this.musicVideoList.length <= index || 0 > index) {
			System.err.println("The index " + index + " is out of bound!");
			return false;
		}

		// get the MusicVideo object and make it a file with the path
		File file = musicVideoList[index].getPath().toFile();

		if (!file.exists()) {
			System.err.println("<< The file " + file.getPath() + " ford not exist!");
			return false;
		}

		// open it
		try {
			Desktop.getDesktop().open(file);
			System.out.println(
					">>> Opened " + musicVideoList[index].getTitle() + " by " + musicVideoList[index].getArtist());
			return true;
		} catch (IOException e) {
			System.err.println("<< File \"" + file.getAbsolutePath() + "\" could not be opened!");
			return false;
		} catch (Exception e) {
			System.err.println("<< File \"" + file.getAbsolutePath() + "\" could not be opened because of Desktop!");
			e.printStackTrace();
			return false;
		}

	}

	private MusicVideo isFileMusicVideo(Path filePath) {

		// file is a "normal" readable file
		if (Files.isRegularFile(filePath)) {

			String fileType = null, pathOfFile = filePath.getFileName().toString();
			String[] artistAndTitle = null;

			int lastIndexOfPoint = pathOfFile.lastIndexOf('.');
			boolean containsArtistAndTitle = pathOfFile.contains(" - ");

			if (lastIndexOfPoint > 0 && containsArtistAndTitle) {

				fileType = pathOfFile.substring(lastIndexOfPoint + 1);
				artistAndTitle = pathOfFile.substring(0, lastIndexOfPoint).split(" - ", 2);

			} else {
				// -1 if '.' is not found or file doesn't have " - " for support of artist/title
				System.err.println("Incompatible filename! (" + filePath + ")");
				return null;
			}

			// now we compare it to our allowed extension array
			for (String supportedFileTypes : this.settingsData.getAcceptedFileTypes()) {
				if (supportedFileTypes.equalsIgnoreCase(fileType)) {

					// finally add the newMusicVideoObject to our
					// musicVideosList
					return new MusicVideo(filePath, artistAndTitle[1], artistAndTitle[0]);
				}
			}

		}
		return null;
	}

	/**
	 * Convert all the data of the musicVideoList to a Object[][] for tables
	 * 
	 * @return Object[][] ([][#, artist, title])
	 */
	public Object[][] musicVideoListToTable() {

		if (this.musicVideoList == null) {
			System.err.println("The music video list is null!");
			return null;
		}

		if (this.musicVideoList.length == 0) {
			System.err.println("The music video list is empty!");
			return null;
		}

		Object[][] tableData = new Object[this.musicVideoList.length][this.columnNames.length];

		System.out.println("New updated table:");
		for (int i = 0; i < this.musicVideoList.length; i++) {
			// set data to Object[][]
			tableData[i][0] = i + 1;
			tableData[i][1] = this.musicVideoList[i].getArtist();
			tableData[i][2] = this.musicVideoList[i].getTitle();

			// check it right now
			// System.out.print("#: " + tableData[a][0]);
			// System.out.print("\tArtist: " + tableData[a][1]);
			// System.out.println("\tTitle: " + tableData[a][2]);
		}

		return tableData;
	}

	/**
	 * Create the favicon folder with all the icons in a given directory
	 * 
	 * @param outputFolder
	 *            (Path | Path of directory in which the favicon folder should be
	 *            created)
	 * @return
	 */
	private boolean copyFavicons(Path outputFolder) {

		if (outputFolder == null) {
			System.err.println("Path is null!");
		}

		if (!outputFolder.toFile().exists()) {
			System.err.println("Folder to copy does not exist!");
		}

		try {

			outputFolder = outputFolder.toAbsolutePath();

			String faviconFolder = outputFolder.toString() + "/favicons";

			// create the favicon directory
			FileReadWriteModule.createDirectory(new File(faviconFolder));

			// copy all .png images
			Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

			for (Integer size : sizes) {
				String inputPath = "websites/favicons/favicon-" + size + "x" + size + ".png";
				Path outpuPath = Paths.get(faviconFolder + "/favicon-" + size + "x" + size + ".png");
				FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream(inputPath), outpuPath);
			}

			// copy .svg image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("websites/favicons/favicon.svg"),
					Paths.get(faviconFolder + "/favicon.svg"));

			// copy .ico image
			FileReadWriteModule.copy(ClassResourceReaderModule.getInputStream("websites/favicons/icon.ico"),
					Paths.get(faviconFolder + "/icon.ico"));

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private String generateHeadName() {

		// string builder for all links
		StringBuilder specialHead = new StringBuilder("");

		String name = "MusicVideoManager";
		specialHead.append("<title>" + name + "</title>");
		specialHead.append("<meta name=\"apple-mobile-web-app-title\" content=\"" + name + "\">");
		specialHead.append("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">");
		specialHead.append("<meta name=\"application-name\" content=\"" + name + "\">");

		return specialHead.toString();
	}

	private String generateFaviconLinks() {

		// string builder for all links
		StringBuilder faviconLinks = new StringBuilder("");

		// firefox svg link
		faviconLinks.append("<link rel=\"icon\" type=\"image/svg+xml\" href=\"favicons/favicon.svg\">");

		// apple links
		faviconLinks.append("<link rel=\"apple-touch-icon\" href=\"favicons/favicon-180x180.png\">");
		faviconLinks.append("<link rel=\"mask-icon\" href=\"favicons/favicon.svg\" color=\"#000000\">");

		// add all .png images
		Integer[] sizes = { 16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512 };

		for (Integer size : sizes) {
			faviconLinks.append("<link rel=\"icon\" type=\"image/png\" href=\"favicons/favicon-" + size + "x" + size
					+ ".png\" sizes=\"" + size + "x" + size + "\">");
		}

		return faviconLinks.toString();
	}

	private String generateHtmlSearch() {
		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);
		JsonObject jsJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/js.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_party"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks());

		// add js
		htmlStatic.append("<script>");
		htmlStatic.append(JsonModule.getValueString(jsJsonContent, "w3-js"));

		// add css
		htmlStatic.append("</script><style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_searchable"));

		// close head and open body
		htmlStatic.append("</style></head><body>");
		// add section begin
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_party"));

		// add the overlay / table header
		String tableHeader = JsonModule.getValueString(htmlJsonContent, "overlay-html_party");
		tableHeader = tableHeader.replaceFirst("#", this.columnNames[0]);
		tableHeader = tableHeader.replaceFirst("Artist", this.columnNames[1]);
		tableHeader = tableHeader.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader);

		// add the second table header (for print)
		String tableHeader2 = JsonModule.getValueString(htmlJsonContent, "table-header-html_party");
		tableHeader2 = tableHeader2.replaceFirst("#", this.columnNames[0]);
		tableHeader2 = tableHeader2.replaceFirst("Artist", this.columnNames[1]);
		tableHeader2 = tableHeader2.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader2);

		// table data
		htmlStatic.append(ExportMusicVideoData.generateHtmlTableDataSearch(musicVideoListToTable()));

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "before-form-html_party"));
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_party"));

		return htmlStatic.toString();
	}

	private String generateHtmlStatic() {

		// string builder for the whole site
		StringBuilder htmlStatic = new StringBuilder("");

		// json data html file
		JsonObject htmlJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/html.json")[0]);
		JsonObject cssJsonContent = JsonModule
				.loadJsonFromString(ClassResourceReaderModule.getTextContent("websites/css.json")[0]);

		// add default head
		htmlStatic.append("<!DOCTYPE html><html><head>");
		// add generic head
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "head"));
		// add custom head for static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "custom-head-html_static"));

		// add title and more
		htmlStatic.append(generateHeadName());

		// add links to all the images
		htmlStatic.append(generateFaviconLinks());

		// add css
		htmlStatic.append("<style>");
		htmlStatic.append(JsonModule.getValueString(cssJsonContent, "styles_static"));

		// close head and open body
		htmlStatic.append("</style></head><body>");
		// add section begin: section-start-html_static
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "section-start-html_static"));

		// add table header
		String tableHeader = JsonModule.getValueString(htmlJsonContent, "table-header-html_static");
		tableHeader = tableHeader.replaceFirst("#", this.columnNames[0]);
		tableHeader = tableHeader.replaceFirst("Artist", this.columnNames[1]);
		tableHeader = tableHeader.replaceFirst("Title", this.columnNames[2]);
		htmlStatic.append(tableHeader);

		// table data
		htmlStatic.append(ExportMusicVideoData.generateHtmlTableDataStatic(musicVideoListToTable()));

		// add after table data
		htmlStatic.append(JsonModule.getValueString(htmlJsonContent, "after-table-html_static"));

		return htmlStatic.toString();
	}

	public boolean saveHtmlList(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { generateHtmlStatic() });
	}

	public boolean saveHtmlSearch(Path outputDirectory, boolean faviconsOn) {

		if (faviconsOn) {
			copyFavicons(outputDirectory);
		}

		return FileReadWriteModule.writeTextFile(new File(outputDirectory.toString() + "/index.html"),
				new String[] { generateHtmlSearch() });
	}

	public boolean saveHtmlStructureSearchable(Path outputDirectory) {

		copyFavicons(outputDirectory);
		// return htmlAndPhpExport(outputDirectory, WEBSITE_TYPE.HTML_SEARCHABLE);
		return true;
	}

	public boolean saveFileHtmlBasic(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeTextFile(whereToWriteTheFile.toFile(), new String[] {
				ExportMusicVideoData.generateHtmlSiteStatic(musicVideoListToTable(), this.columnNames) });
	}

	/**
	 * Save the music video list in CSV format (Excel)
	 * 
	 * @param whereToWriteTheFile
	 *            (Path)
	 * @return writeWasSuccsessful (Boolean)
	 */
	public boolean saveCsv(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeTextFile(whereToWriteTheFile.toFile(),
				ExportMusicVideoData.generateCsvContent(this.musicVideoList, this.columnNames).split("\n"));
	}

	/**
	 * Save the music video list in JSON format
	 * 
	 * @param whereToWriteTheFile
	 *            (Path)
	 * @return writeWasSuccsessful (Boolean)
	 */
	public boolean saveJson(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeTextFile(whereToWriteTheFile.toFile(),
				new String[] { ExportMusicVideoData.generateJsonContent(this.musicVideoList, this.columnNames) });
	}

	/**
	 * Load the current Settings from a file and set them as new settings
	 * 
	 * @param settingsFilePath
	 *            (File | File with the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean loadSettings(File settingsFilePath) {

		// read the settingsData
		if (setSettingsData(ExportImportSettings.readSettings(settingsFilePath))) {
			updateMusicVideoList();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Save the current Settings to a file
	 * 
	 * @param settingsFilePath
	 *            (File | File for the settingsData)
	 * @return saveOperationSuccsessful (Boolean)
	 */
	public boolean saveSettings(File settingsFilePath) {
		return ExportImportSettings.writeSettings(settingsFilePath, this.settingsData);
	}

	/**
	 * Returns FALSE if the new settings data from the file is different to the
	 * current settings data. If they aren't the same TRUE will be returned.
	 * 
	 * @param settingsFilePathNew
	 *            (File | File that contains settingsData in JSON format)
	 * @return theyAreTheSame (Boolean)
	 */
	public boolean compareSettings(File settingsFilePathNew) {
		return ExportImportSettings.compareSettingsFileToCurrent(settingsFilePathNew, this.settingsData);
	}

}
