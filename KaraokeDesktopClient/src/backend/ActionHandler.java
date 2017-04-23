package backend;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFileChooser;

public class ActionHandler {

	private ArrayList<Path> pathList;
	private ArrayList<MusicVideo> musicVideosList;

	public ActionHandler() {
		pathList = new ArrayList<Path>();
		musicVideosList = new ArrayList<MusicVideo>();
	}

	public Path getDirectory() {

		JFileChooser chooser = new JFileChooser();
		chooser.isFocusOwner();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose a folder with your music videos");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			return chooser.getSelectedFile().toPath();

		} else {

			System.out.println("No directory was selected.");

			return null;
		}
	}

	public void scanDirectory(Path path) {

		// "walk" through all files in the actual directory
		try (Stream<Path> paths = Files.walk(path)) {
			paths.forEach(filePath -> {

				// if file is a normal file and a movie file we create a new
				// MusicVideo object
				if (Files.isRegularFile(filePath)
						&& ((filePath.toString().contains(".avi") || filePath.toString().contains(".mp4"))
								|| (filePath.toString().contains(".mkv") || filePath.toString().contains(".mov")))) {

					MusicVideo newMusicVideoObject = new MusicVideo();

					// set (file) path of the music video
					newMusicVideoObject.setPath(filePath);
					// System.out.println("path: " + filePath);

					// set name/title and artist/s name of the music video
					// first lets remove the file type
					String noFileType = filePath.getFileName().toString();
					if (noFileType.indexOf(".") > 0) {
						noFileType = noFileType.substring(0, noFileType.lastIndexOf("."));
					}
					// second split the new substring at " - "
					boolean artistNotTitle = true;
					for (String artistOrTitle : noFileType.split("\\s-\\s")) {

						if (artistNotTitle == true) {
							// the first part of the split is our artist/s

							// set artist/s of the music video
							newMusicVideoObject.setArtist(artistOrTitle);
							// System.out.println("artist/s: " +
							// artistOrTitle);

							// now that we have our artist we want to have
							// the title
							artistNotTitle = false;

						} else {
							// the second part of the split is our title

							// set title/name of the music video
							newMusicVideoObject.setName(artistOrTitle);
							// System.out.println("title/name: " +
							// artistOrTitle);
						}
					}

					// finally add the newMusicVideoObject to our
					// musicVideosList
					musicVideosList.add(newMusicVideoObject);
				}
			});

			System.out.println("Music video list was updated (+ " + path + ")");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void scanDirectories() {

		if (!pathList.isEmpty()) {
			System.out.println("Scan all paths for music videos");

			if (!musicVideosList.isEmpty()) {
				musicVideosList.clear();
				System.out.println("Music video list was cleared");
			}

			for (Path path : pathList) {
				scanDirectory(path);
			}
		} else {
			System.err.println("There are no paths to scan for music videos!");
		}
	}

	public void openMusicVideo(int index) {

		if (musicVideosList.size() > 0 && musicVideosList.size() > index) {

			// get the MusicVideo object and make it a file
			File file = new File(musicVideosList.get(index).getPath().toString());

			// first check if Desktop is supported by Platform or not
			if (!Desktop.isDesktopSupported()) {
				System.out.println("Desktop is not supported");
				return;
			} else {
				// if the file exists too
				Desktop desktop = Desktop.getDesktop();
				if (file.exists()) {
					// open it
					try {
						desktop.open(file);
						System.out.println(">> Playing/Open " + musicVideosList.get(index).getTitle() + " by "
								+ musicVideosList.get(index).getArtist());
					} catch (IOException e) {
						// File could not opened
						e.printStackTrace();
					}
					// System.out.println("File " + file.getAbsolutePath() + "
					// was opened");
				} else {
					System.out.println("File " + file.getAbsolutePath() + " does not exist");

				}
			}

		} else {
			System.out.println("This index does not exist");
		}
	}

	public ArrayList<Path> getPathList() {
		return pathList;
	}

	public void setPathList(ArrayList<Path> pathList) {
		this.pathList = pathList;
	}

	public void clearPathList() {
		if (pathList.isEmpty() == false) {
			pathList.clear();
		}
	}

	public void addToPathList(Path path) {
		if (path == null) {
			// nothing
			System.out.println("No path was added to your path list.");
		} else if (pathList.contains(path)) {
			System.out.println("The path \"" + path + "\" was already on your path list.");
		} else {
			pathList.add(path);
			System.out.println("The path list was updated (+ " + path + ")");
		}

	}

	public void printPathList() {
		if (pathList.isEmpty() == false) {
			int c = 1;
			for (Path key : pathList) {
				System.out.println(c + ".\tPath: \"" + key);
				c++;
			}
		} else {
			System.out.println("There are no saved music video paths");
		}
	}

	public void deletePathFromPathList(int a) {
		a--;
		if (pathList.size() > a) {
			pathList.remove(a);
		} else {
			System.out.println("This path does not exist");
		}
	}

	public ArrayList<MusicVideo> getMusicVideosList() {
		return musicVideosList;
	}

	public void setMusicVideosList(ArrayList<MusicVideo> musicVideosList) {
		this.musicVideosList = musicVideosList;
	}

	public void clearMusicVideosList() {
		if (musicVideosList.isEmpty() == false) {
			musicVideosList.clear();
		}
	}

	public void addMusicVideosList(MusicVideo musicVideo) {
		musicVideosList.add(musicVideo);
	}

	public void printMusicVideoList() {
		if (musicVideosList.isEmpty() == false) {
			int c = 1;
			for (MusicVideo key : musicVideosList) {
				System.out.println(c + ".\tName: \"" + key.getTitle() + "\" - Artist: \"" + key.getArtist() + "\" ("
						+ "Path: \"" + key.getPath() + "\")");
				c++;
			}
		}
	}

	public Object[][] musicVideoListToTable() {
		// String[] columnNames = { "Artist", "Title" };

		Object[][] tableData = new Object[musicVideosList.size()][3];

		for (int a = 0; a < musicVideosList.size(); a++) {
			tableData[a][0] = a + 1;
			tableData[a][1] = musicVideosList.get(a).getArtist();
			tableData[a][2] = musicVideosList.get(a).getTitle();
		}

		System.out.println("Tabledata:");
		for (int a = 0; a < tableData.length; a++) {
			System.out.print("#: " + tableData[a][0]);
			System.out.print("\tArtist: " + tableData[a][1]);
			System.out.println("\tTitle: " + tableData[a][2]);
		}

		// for (int a = 0; a < musicVideosList.size(); a++) {
		// System.out.print("Artist: " + tableData[a][0]);
		// System.out.println("\tTitle: " + tableData[a][1]);
		// }

		return tableData;

	}

	public ArrayList<Path> fileReader(File file) {

		System.out.println("Scan for config file: " + file);

		if (file.exists()) {

			ArrayList<Path> pathListForSomeSeconds = new ArrayList<Path>();

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				for (String line; (line = br.readLine()) != null;) {
					Path path = Paths.get(line);
					pathListForSomeSeconds.add(path);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			System.out.println("Configuration file was found - information extracted.");

			return pathListForSomeSeconds;

		} else {
			System.out.println("No config file was found");
			return null;
		}
	}

	public void fileWriterOfTheConfigFile(File file) {

		if (pathList.isEmpty()) {
			System.err.println("There is nothing to save!");
		} else {
			// creates the file
			try {
				file.createNewFile();

				// creates a FileWriter Object
				FileWriter writer = new FileWriter(file);

				// Writes the content to the file
				for (Path a : getPathList()) {
					writer.write(a + "\n");
				}
				writer.flush();
				writer.close();

				System.out.println("Path list was saved to " + file);

			} catch (IOException e) {
				System.out.println("Path list could not be saved.");
				e.printStackTrace();
			}
		}
	}

	public void fileOverWriter(File file) {

		if (file.exists()) {
			fileDeleter(file);
		}
		fileWriterOfTheConfigFile(file);
	}

	public void fileDeleter(File file) {

		if (file.exists()) {

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} else {
			System.out.println(file.getName() + " does not exist and could not be deleted!");
		}
	}

	public static void main(String[] args) {

		// create new KaraokeOMat
		ActionHandler testding = new ActionHandler();

		// add new path to music video library
		testding.addToPathList(testding.getDirectory());

		// scan all saved paths after music videos
		testding.scanDirectories();

		// print out all recognized music video files
		testding.printMusicVideoList();

		testding.musicVideoListToTable();

		// open a file through the console
		Scanner scanInput;
		try {
			scanInput = new Scanner(System.in);
			testding.openMusicVideo(scanInput.nextInt());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}