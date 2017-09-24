package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.functions.ExportMusicVideoData;
import anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries.FileReadWriteModule;

public class MusicVideoHandler {

	/**
	 * All the important data about music video files and more is in here
	 */
	private ProgramData settingsData;

	public ProgramData getSettingsData() {
		return settingsData;
	}

	public void setSettingsData(ProgramData settingsData) {
		this.settingsData = settingsData;
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

	public boolean saveFileHtmlBasic(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeFile(whereToWriteTheFile.toFile(), new String[] {
				ExportMusicVideoData.generateHtmlSiteStatic(musicVideoListToTable(), this.columnNames) });
	}

	public boolean saveFileHtmlSearchable(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeFile(whereToWriteTheFile.toFile(), new String[] {
				ExportMusicVideoData.generateHtmlSiteDynamic(musicVideoListToTable(), this.columnNames) });
	}

	public boolean saveFileHtmlParty(Path whereToWriteTheFile) {

		if (whereToWriteTheFile == null) {
			System.err.println("Path can't be null!");
			return false;
		}

		return FileReadWriteModule.writeFile(whereToWriteTheFile.toFile(),
				new String[] { ExportMusicVideoData.generateHtmlSiteParty(musicVideoListToTable(), this.columnNames) });
	}

}
