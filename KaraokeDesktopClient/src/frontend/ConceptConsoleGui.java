package frontend;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import backend.ActionHandler;

public class ConceptConsoleGui {

	private ActionHandler karaokeOMat;
	private Scanner scannerConsoleGui;
	private boolean guiIsRunning;
	private boolean firstStart;
	private String[] menuText;

	public int scanNumber(int underBoundary, int upperBoundary) {

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

	public boolean scanYesOrNo() {

		boolean yesOrNo = false;
		String yesOrNoString = "";

		try {
			do {
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

	private void addFolder() {
		karaokeOMat.addToPathList(karaokeOMat.getDirectory());
		karaokeOMat.scanDirectories();
	}

	private void showMusicVideoList() {
		karaokeOMat.printMusicVideoList();
	}

	private void showPathList() {
		karaokeOMat.printPathList();
	}

	private void playMusicVideo() {
		karaokeOMat.openMusicVideo(scanNumber(1, karaokeOMat.getMusicVideosList().size()) - 1);
	}

	private void playRandomMusicVideo() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, karaokeOMat.getMusicVideosList().size() + 1);
		System.out.println("...Playing " + karaokeOMat.getMusicVideosList().get(randomNum).getName() + " by "
				+ karaokeOMat.getMusicVideosList().get(randomNum).getArtist() + "\n");

		if (scanYesOrNo()) {
			karaokeOMat.openMusicVideo(randomNum);
		}

	}

	private void actionMenu(int menuNumber) {

		System.out.println(menuText[menuNumber - 1]);
		switch (menuNumber) {
		case 1:
			System.out.println("Just choose your folder and press ok so that this directory gets\n"
					+ "scanned and the music videos can be added to your music video list");
			addFolder();
			break;
		case 2:
			showMusicVideoList();
			break;
		case 3:
			System.out.println("Just input the number of the music video and press enter to play it");
			playMusicVideo();
			break;
		case 4:
			playRandomMusicVideo();
			break;
		case 5:
			System.out.println("Coming soon\nBy now only the actual saved music video paths:");
			showPathList();
			break;
		case 6:
			guiIsRunning = false;
			break;
		}
	}

	private void runConsoleUi() {

		while (guiIsRunning == true) {
			int a = 0;
			printMenu();
			a = scanNumber(1, menuText.length);
			actionMenu(a);
		}

		System.out.println("Console GUI was closed");

	}

	public static void main(String[] args) {

		ConceptConsoleGui gui = new ConceptConsoleGui();
		gui.runConsoleUi();

	}

}
