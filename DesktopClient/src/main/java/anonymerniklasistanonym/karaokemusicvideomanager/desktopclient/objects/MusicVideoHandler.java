package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.objects;

import java.nio.file.Path;
import java.util.Collections;

public class MusicVideoHandler {

	/**
	 * All the important data about music video files and more is in here
	 */
	private ProgramData settingsData;

	/**
	 * This ArrayList<MusicVideo> contains all the MusicVideo objects and each
	 * object is a music video file with information about title, artist and path of
	 * the music video files that were found in the source folders
	 */
	private MusicVideo[] musicVideoList;

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

		System.out.print(">> Add path " + directoryPath.toAbsolutePath() + "to path list.");

		if (!directoryPath.toFile().isDirectory()) {
			System.err.println(" << Path is no directory!");
			return false;
		}

		try {

			Path[] oldPathList = this.settingsData.getPathList();

			Path[] newPathList = new Path[oldPathList.length + 1];

			newPathList[oldPathList.length] = directoryPath.toAbsolutePath();

			this.settingsData.setPathList(newPathList);

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
		int numberOfLinesTitle = 15;
		int numberOfLinesArtist = 13;
		int numberOfLinesPath = 25;

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
		for (int i = 0; i < musicVideoList.length + 1; i++) {

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

}
