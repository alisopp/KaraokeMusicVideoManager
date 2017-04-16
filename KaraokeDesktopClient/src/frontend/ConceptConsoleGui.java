package frontend;

import java.nio.file.Path;
import java.util.Scanner;

import javax.swing.JFileChooser;

import backend.ActionHandler;

public class ConceptConsoleGui {

	private ActionHandler karaokeOMat;
	private int menuNumber;
	private Scanner scan;

	public int getMenuNumber() {
		return menuNumber;
	}

	public void setMenuNumber(int menuNumber) {
		this.menuNumber = menuNumber;
	}

	public ConceptConsoleGui() {
		karaokeOMat = new ActionHandler();
		scan = new Scanner(System.in);
	}

	private void scanMenu() {

		int a = 0;
		boolean ok = false;

		do {
			try {
				a = scan.nextInt();

				if (a > 0 && a < 7) {
					ok = true;
					menuNumber = a;
				}
			} catch (Exception f) {
				f.printStackTrace();
			}

		} while (ok != true);
	}

	private int scanMusicVideo() {

		int a = 0;
		boolean ok = false;

		System.out.println("Please input your song number:");

		do {
			try {
				a = scan.nextInt();
				a++;

				if (karaokeOMat.getMusicVideosList().size() <= a && a > 0) {
					ok = true;
					return a;
				}
			} catch (Exception f) {
				f.printStackTrace();
			}

		} while (ok != true);
		return 0;
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

	private void printMenu() {
		if (menuNumber == 0) {
			System.out.println("Welcome to KaraokeOMat!\n");
		}
		System.out.println("Input through numbers what you want to do:\n" + "1.\tAdd a new music video folder\n"
				+ "2.\tShow music video list\n" + "3.\tPlay music video\n" + "4.\tPlay a random music video\n"
				+ "5.\tEdit folder paths\n" + "6.\tClose program\n");
	}

	private void playMusicVideo() {
		karaokeOMat.openMusicVideo(scanMusicVideo());
	}

	private void playRandomMusicVideo() {
		karaokeOMat.printPathList();
	}

	public Path getDirectory2() {

		System.out.println("hi");

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose your music video folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// System.out.println("getCurrentDirectory(): " +
			// chooser.getCurrentDirectory());
			// System.out.println("getSelectedFile() : " +
			// chooser.getSelectedFile());

			return chooser.getSelectedFile().toPath();

		} else {
			System.out.println("No Selection ");

			return null;
		}
	}

	private void actionMenu(int abc) {
		System.out.println("here now: abc = " + abc);
		switch (abc) {
		case 1:
			addFolder();
			break;
		case 2:
			System.out.println("Actual scanned music videos:");
			showMusicVideoList();
			break;
		case 3:
			playMusicVideo();
			break;
		case 4:
			System.out.println("Actual scanned music videos:");
			playRandomMusicVideo();
			break;
		case 5:
			System.out.println("Coming soon\nActual paths:");
			showPathList();
			break;
		case 6:
			System.exit(0);
			break;
		}
	}

	private void runConsoleUi() {
		System.out.println("Here happens the completemagic");

		while (getMenuNumber() != 6) {
			printMenu();
			scanMenu();
			actionMenu(getMenuNumber());
			System.out.println(getMenuNumber());
		}

	}

	public static void main(String[] args) {

		ConceptConsoleGui gui = new ConceptConsoleGui();
		gui.runConsoleUi();

	}

}
