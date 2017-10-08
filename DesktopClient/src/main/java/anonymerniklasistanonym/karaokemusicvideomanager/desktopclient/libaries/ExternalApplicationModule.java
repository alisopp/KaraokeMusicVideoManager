package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Static methods that handle calls to external native programs (Java Desktop)
 * 
 * @author AnonymerNiklasistanonym <niklas.mikeler@gmail.com> | <a href=
 *         "https://github.com/AnonymerNiklasistanonym">https://github.com/AnonymerNiklasistanonym</a>
 */
public class ExternalApplicationModule {

	/**
	 * Open any String in the default web browser of the system:
	 * 
	 * inspired by http://stackoverflow.com/a/4898607 last edited from SingleShot
	 */
	public static boolean openUrl(String urlToOpen) {

		if (!Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported - the program will not open videos on this computer!");
			return false;
		}

		if (urlToOpen == null) {
			System.err.println("The String/URL is null!");
			return false;
		}

		if (urlToOpen.isEmpty()) {
			System.err.println("The String/URL is empty!");
			return false;
		}

		System.out.println(">> Try to externaly open the URL " + urlToOpen);

		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(urlToOpen));
				return true;
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Open any file with the default external program
	 * 
	 * @param file
	 * @return
	 */
	public static boolean openFile(File file) {

		if (!Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported - the program will not open videos on this computer!");
			return false;
		}

		if (file == null) {
			System.err.println("The file is null!");
			return false;
		}

		if (!file.exists()) {
			System.err.println("The file " + file.getAbsolutePath() + " doesn't exist!");
			return false;
		}

		System.out.println(">> Try to externaly open the file " + file.getAbsolutePath());

		try {
			Desktop.getDesktop().open(file);
			System.out.println("<< Succsessfully opened \"" + file.getName() + "\"");
			return true;
		} catch (IOException e) {
			System.err.println("<< File could not be opened/read/found!");
		} catch (Exception e) {
			System.err.println("<< Unknown error!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Opens a directory when the file is one, if not it opens the parent directory
	 * where the file is within
	 * 
	 * @param file
	 * @return true if everything worked
	 */
	public static boolean openDirectory(File file) {

		if (!Desktop.isDesktopSupported()) {
			System.err.println("Desktop is not supported - the program will not open videos on this computer!");
			return false;
		}

		if (file == null) {
			System.err.println("The file is null!");
			return false;
		}

		if (!file.exists()) {
			System.err.println("The file " + file.getAbsolutePath() + " doesn't exist!");
			return false;
		}

		// check if file is a directory or not
		if (file.isDirectory()) {
			return openFile(file);
		} else {
			return openFile(file.getParentFile());
		}
	}

}
