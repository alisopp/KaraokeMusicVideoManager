package anonymerniklasistanonym.karaokemusicvideomanager.desktopclient.libaries;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ClassResourceReaderModule {

	/**
	 * Load a text file into a String array and return it
	 * 
	 * @param path
	 *            (String | path to resource file)
	 * @return content (String[])
	 */
	public static String[] getTextContent(String path) {

		ArrayList<String> cache = new ArrayList<String>();

		try {

			ClassLoader cl = ClassResourceReaderModule.class.getClassLoader();

			BufferedReader beforeTableHtmlData = new BufferedReader(new InputStreamReader(getInputStream(path)));
			String line;
			while ((line = beforeTableHtmlData.readLine()) != null) {
				cache.add(line);
			}

			return cache.toArray(new String[0]);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Load a file from the res folder (in build path)
	 * 
	 * @param path
	 *            (String | Path to file)
	 * @return input stream of the file (InputStream or null)
	 */
	public static InputStream getInputStream(String path) {

		if (path == null) {
			System.err.println("Path could not be read because it's null!");
			return null;
		}

		try {

			System.out.println(">> Get InputStream: " + path);

			ClassLoader cl = ClassResourceReaderModule.class.getClassLoader();

			InputStream resourceAsStream = cl.getResourceAsStream(path);

			if (resourceAsStream != null) {
				System.out.println("<< InputStream succsessfully loaded.");
				return resourceAsStream;
			} else {
				System.err.println("<< InputStream is null!");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("<< InputStream loading operation didn't work!");
			return null;
		}

	}

}
