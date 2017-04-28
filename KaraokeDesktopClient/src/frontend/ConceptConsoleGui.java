package frontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import backend.ActionHandler;

/**
 * Beta test of this program in a TUI
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 1.0
 *
 */
public class ConceptConsoleGui {

	private ActionHandler karaokeOMat;
	private Scanner scannerConsoleGui;
	private boolean guiIsRunning;
	private boolean firstStart;
	private String[] menuText;
	private String[] menuTextInstructions;
	private File file;

	public ConceptConsoleGui() {
		karaokeOMat = new ActionHandler();
		scannerConsoleGui = new Scanner(System.in);
		guiIsRunning = true;
		firstStart = true;
		menuText = new String[6];
		menuText[0] = "1.\tAdd a new music video folder";
		menuText[1] = "2.\tShow music video list";
		menuText[2] = "3.\tPlay music video";
		menuText[3] = "4.\tPlay a random music video";
		menuText[4] = "5.\tEdit folder paths";
		menuText[5] = "6.\tClose program\n";
		menuTextInstructions = new String[6];
		menuTextInstructions[0] = "Just choose your folder and press ok so that this directory gets\n"
				+ "scanned and the music videos can be added to your music video list:";
		menuTextInstructions[1] = "All scanned and found music videos:";
		menuTextInstructions[2] = "Just input the number of the music video and press enter to play it";
		menuTextInstructions[3] = "Searching for random music video:";
		menuTextInstructions[4] = "Here are all scanned directories, if you want you can delete a path:";
		menuTextInstructions[5] = "The program gets closed now:";
		file = new File("karaoke_desktop_config.abc");
	}

	private void addFolder() {
		try {
			karaokeOMat.addToPathList(karaokeOMat.getPathOfDirectory());
			karaokeOMat.clearMusicVideosList();
			karaokeOMat.updateMusicVideoList();
		} catch (Exception f) {
			System.out.println("The process was shutted down.");
		}
	}

	private void showMusicVideoList() {
		karaokeOMat.printMusicVideoList();
	}

	private void showPathList() {
		karaokeOMat.printPathList();

		if (!karaokeOMat.getPathList().isEmpty()) {

			if (scanYesOrNo("Do you want to remove any paths from the list?")) {
				karaokeOMat.deletePathFromPathList(scanNumber(1, karaokeOMat.getPathList().size(),
						"Please enter the number of the path you want to remove:"));
			}
		}
	}

	private void playMusicVideo() {
		karaokeOMat.openMusicVideo(scanNumber(1, karaokeOMat.getMusicVideosList().size(), "") - 1);
	}

	private void saveConfiguration() {
		if (scanYesOrNo("Do you want to save your configuration?")) {
			configFileWriter();
		}
	}

	private void playRandomMusicVideo() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, karaokeOMat.getMusicVideosList().size() + 1);
		System.out.println("...Playing " + karaokeOMat.getMusicVideosList().get(randomNum).getTitle() + " by "
				+ karaokeOMat.getMusicVideosList().get(randomNum).getArtist() + "?");

		if (scanYesOrNo("Do you want to open this music file?")) {
			karaokeOMat.openMusicVideo(randomNum);
		}

	}

	private void printMenu() {
		if (firstStart == true) {
			System.out.println("Welcome to KaraokeOMat!\nInput through numbers what you want to do:");
			firstStart = false;
		}
		System.out.println("");
		for (String menu : menuText) {
			System.out.println(menu);
		}
	}

	private void actionMenu(int menuNumber) {

		System.out.println(menuText[menuNumber - 1] + "\n" + menuTextInstructions[menuNumber - 1]);

		switch (menuNumber) {
		case 1:
			addFolder();
			break;
		case 2:
			showMusicVideoList();
			break;
		case 3:
			playMusicVideo();
			break;
		case 4:
			playRandomMusicVideo();
			break;
		case 5:
			showPathList();
			break;
		case 6:
			guiIsRunning = false;
			saveConfiguration();
			break;
		default:
			System.out.println("Not yet implemented");
			break;
		}
	}

	public int scanNumber(int underBoundary, int upperBoundary, String message) {

		if (!message.equals("")) {
			System.out.println(message);
		}

		int returnInt = menuText.length - 1;

		try {
			do {
				System.out.println("Input a number between " + underBoundary + " and " + upperBoundary + ":");
				while (!scannerConsoleGui.hasNextInt()) {
					System.out.println("Please input a number:");
					scannerConsoleGui.next();
				}
				returnInt = scannerConsoleGui.nextInt();
			} while (!(returnInt >= underBoundary && upperBoundary >= returnInt));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("A failure happened while scanning the number");
		}

		return returnInt;
	}

	public boolean scanYesOrNo(String message) {

		boolean yesOrNo = false;
		String yesOrNoString = "";

		try {
			do {
				System.out.println("\n" + message);
				System.out.println("Input \"y\" (for yes) and \"n\" (for no):");
				while (!scannerConsoleGui.hasNext()) {
					System.out.println("Please input  \"y\" or \"n\":");
					scannerConsoleGui.next();
				}

				yesOrNoString = scannerConsoleGui.next();
			} while (!(yesOrNoString.equalsIgnoreCase("y") || yesOrNoString.equalsIgnoreCase("n")));
			if (yesOrNoString.equalsIgnoreCase("y")) {
				yesOrNo = true;
			} else {
				yesOrNo = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("A failure happened while scanning the number");
		}

		return yesOrNo;
	}

	public void configFileWriter() {

		// deletes old file
		if (file.exists() && scanYesOrNo("Do you want to overwrite your previous configuration?")) {

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

		}

		// creates the file
		try {
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter writer = new FileWriter(file);

			// Writes the content to the file
			for (Path a : karaokeOMat.getPathList()) {
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

	public ArrayList<Path> configFileReader() {

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

			return pathListForSomeSeconds;

		}

		return null;
	}

	public void configFileStartupImportManager() {

		if (file.exists()) {
			System.out.println("Config file found... ");
			try {
				karaokeOMat.setPathList(configFileReader());
				System.out.print("saved music video paths were added... ");
				karaokeOMat.clearMusicVideosList();
				karaokeOMat.updateMusicVideoList();
				System.out.println("music video list was updated.\n\n");
			} catch (Exception e) {
				System.err.println("\nProblem detected. Import was not successfull.\n\n");
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private void runConsoleUi() {

		configFileStartupImportManager();

		while (guiIsRunning == true) {
			printMenu();
			actionMenu(scanNumber(1, menuText.length, ""));
		}

		System.out.println("Console GUI was closed. Karaoke Desktop Client was terminated.");
	}

}